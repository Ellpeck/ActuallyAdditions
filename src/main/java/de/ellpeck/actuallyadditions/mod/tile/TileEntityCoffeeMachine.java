/*
 * This file ("TileEntityCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.AASounds;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.crafting.CoffeeIngredientRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.AATank;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityCoffeeMachine extends TileEntityInventoryBase implements MenuProvider, IButtonReactor, ISharingFluidHandler {

    public static final int SLOT_COFFEE_BEANS = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int CACHE_USE = 35;
    public static final int ENERGY_USED = 150;
    public static final int WATER_USE = 500;
    public static final int COFFEE_CACHE_MAX_AMOUNT = 300;
    public static final int TIME_USED = 500;

    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 250, 0);

    public final AATank tank = new AATank(4 * FluidType.BUCKET_VOLUME) {
        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Override
        public boolean isFluidValid(FluidStack fluid) {
            return fluid.getFluid() == Fluids.WATER;
        }
    };

    public int coffeeCacheAmount;
    public int brewTime;
    private int lastEnergy;
    private int lastTank;
    private int lastCoffeeAmount;
    private int lastBrewTime;

    public TileEntityCoffeeMachine(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.COFFEE_MACHINE.getTileEntityType(), pos, state, 11);
    }

    
    public int getCoffeeScaled(int i) {
        return this.coffeeCacheAmount * i / COFFEE_CACHE_MAX_AMOUNT;
    }

    
    public int getWaterScaled(int i) {
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    
    public int getBrewScaled(int i) {
        return this.brewTime * i / TIME_USED;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.writeSyncableNBT(compound, lookupProvider, type);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(lookupProvider, compound);
        compound.putInt("Cache", this.coffeeCacheAmount);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("Time", this.brewTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.readSyncableNBT(compound, lookupProvider, type);
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(lookupProvider, compound);
        this.coffeeCacheAmount = compound.getInt("Cache");
        if (type != NBTType.SAVE_BLOCK) {
            this.brewTime = compound.getInt("Time");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCoffeeMachine tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCoffeeMachine tile) {
            tile.serverTick();

            tile.storeCoffee();

            if (tile.brewTime > 0 || tile.isRedstonePowered) {
                tile.brew();
            }

            if ((tile.coffeeCacheAmount != tile.lastCoffeeAmount || tile.storage.getEnergyStored() != tile.lastEnergy || tile.tank.getFluidAmount() != tile.lastTank || tile.brewTime != tile.lastBrewTime) && tile.sendUpdateWithInterval()) {
                tile.lastCoffeeAmount = tile.coffeeCacheAmount;
                tile.lastEnergy = tile.storage.getEnergyStored();
                tile.lastTank = tile.tank.getFluidAmount();
                tile.lastBrewTime = tile.brewTime;
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot >= 3 && ItemCoffee.getIngredientRecipeFromStack(stack) != null || slot == SLOT_COFFEE_BEANS && stack.is(ActuallyTags.Items.COFFEE_BEANS) || slot == SLOT_INPUT && stack.getItem() == ActuallyItems.EMPTY_CUP.get();
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT || slot >= 3 && slot < this.inv.getSlots() && ItemCoffee.getIngredientRecipeFromStack(this.inv.getStackInSlot(slot)) == null;
    }

    public void storeCoffee() {
        if (!this.inv.getStackInSlot(SLOT_COFFEE_BEANS).isEmpty() && this.inv.getStackInSlot(SLOT_COFFEE_BEANS).is(ActuallyTags.Items.COFFEE_BEANS)) {
            int toAdd = 2;
            if (toAdd <= COFFEE_CACHE_MAX_AMOUNT - this.coffeeCacheAmount) {
                this.inv.setStackInSlot(SLOT_COFFEE_BEANS, StackUtil.shrink(this.inv.getStackInSlot(SLOT_COFFEE_BEANS), 1));
                this.coffeeCacheAmount += toAdd;
            }
        }
    }

    public void brew() {
        if (this.level.isClientSide) {
            return;
        }

        ItemStack input = this.inv.getStackInSlot(SLOT_INPUT);
        if (!input.isEmpty() && input.is(ActuallyItems.EMPTY_CUP) && this.inv.getStackInSlot(SLOT_OUTPUT).isEmpty() && this.coffeeCacheAmount >= CACHE_USE && this.tank.getFluid().getFluid() == Fluids.WATER && this.tank.getFluidAmount() >= WATER_USE) {
            if (this.storage.getEnergyStored() >= ENERGY_USED) {
                if (this.brewTime % 30 == 0) {
                    this.level.playSound(null, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), AASounds.COFFEE_MACHINE.get(), SoundSource.BLOCKS, 0.1F, 1.0F);
                }

                this.brewTime++;
                this.storage.extractEnergyInternal(ENERGY_USED, false);
                if (this.brewTime >= TIME_USED) {
                    this.brewTime = 0;
                    ItemStack output = new ItemStack(ActuallyItems.COFFEE_CUP.get());
                    for (int i = 3; i < this.inv.getSlots(); i++) {
                        if (!this.inv.getStackInSlot(i).isEmpty()) {
                            RecipeHolder<CoffeeIngredientRecipe> recipeHolder = ItemCoffee.getIngredientRecipeFromStack(this.inv.getStackInSlot(i));
                            if (recipeHolder != null) {
                                if (recipeHolder.value().effect(output)) {
                                    this.inv.setStackInSlot(i, StackUtil.shrinkForContainer(this.inv.getStackInSlot(i), 1));
                                }
                            }
                        }
                    }
                    this.inv.setStackInSlot(SLOT_OUTPUT, output.copy());
                    this.inv.getStackInSlot(SLOT_INPUT).shrink(1);
                    this.coffeeCacheAmount -= CACHE_USE;
                    this.tank.drainInternal(WATER_USE, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        } else {
            this.brewTime = 0;
        }
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        if (buttonID == 0 && this.brewTime <= 0) {
            this.brew();
        }
    }

    @Override
    public IFluidHandler getFluidHandler(Direction facing) {
        return this.tank;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return 0;
    }

    @Override
    public boolean doesShareFluid() {
        return false;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return null;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.coffeeMachine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerCoffeeMachine(windowId, playerInventory, this);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);

        storage.setEnergyStored(componentInput.getOrDefault(ActuallyComponents.ENERGY_STORAGE, 0));
        tank.setFluid(componentInput.getOrDefault(ActuallyComponents.FLUID_A, ActuallyComponents.FluidContents.EMPTY).get());
        this.coffeeCacheAmount = componentInput.getOrDefault(ActuallyComponents.MISC_INT, 0);
    }

    @Override
    protected void collectImplicitComponents(@Nonnull DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);

        builder.set(ActuallyComponents.ENERGY_STORAGE, storage.getEnergyStored());
        builder.set(ActuallyComponents.FLUID_A, ActuallyComponents.FluidContents.of(tank.getFluid()));
        builder.set(ActuallyComponents.MISC_INT, this.coffeeCacheAmount);
    }
}
