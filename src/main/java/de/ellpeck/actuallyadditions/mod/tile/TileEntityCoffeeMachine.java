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
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileEntityCoffeeMachine extends TileEntityInventoryBase implements INamedContainerProvider, IButtonReactor, ISharingFluidHandler {

    public static final int SLOT_COFFEE_BEANS = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int CACHE_USE = 35;
    public static final int ENERGY_USED = 150;
    public static final int WATER_USE = 500;
    public static final int COFFEE_CACHE_MAX_AMOUNT = 300;
    public static final int TIME_USED = 500;

    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 250, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    public final FluidTank tank = new FluidTank(4 * Util.BUCKET) {
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
    public final LazyOptional<IFluidHandler> lazyTank = LazyOptional.of(() -> this.tank);

    public int coffeeCacheAmount;
    public int brewTime;
    private int lastEnergy;
    private int lastTank;
    private int lastCoffeeAmount;
    private int lastBrewTime;

    public TileEntityCoffeeMachine() {
        super(ActuallyBlocks.COFFEE_MACHINE.getTileEntityType(),  11);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCoffeeScaled(int i) {
        return this.coffeeCacheAmount * i / COFFEE_CACHE_MAX_AMOUNT;
    }

    @OnlyIn(Dist.CLIENT)
    public int getWaterScaled(int i) {
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrewScaled(int i) {
        return this.brewTime * i / TIME_USED;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        compound.putInt("Cache", this.coffeeCacheAmount);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("Time", this.brewTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        this.coffeeCacheAmount = compound.getInt("Cache");
        if (type != NBTType.SAVE_BLOCK) {
            this.brewTime = compound.getInt("Time");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            this.storeCoffee();

            if (this.brewTime > 0 || this.isRedstonePowered) {
                this.brew();
            }

            if ((this.coffeeCacheAmount != this.lastCoffeeAmount || this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.brewTime != this.lastBrewTime) && this.sendUpdateWithInterval()) {
                this.lastCoffeeAmount = this.coffeeCacheAmount;
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBrewTime = this.brewTime;
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot >= 3 && ItemCoffee.getIngredientFromStack(stack) != null || slot == SLOT_COFFEE_BEANS && ActuallyTags.Items.COFFEE_BEANS.contains(stack.getItem()) || slot == SLOT_INPUT && stack.getItem() == ActuallyItems.COFFEE_CUP.get();
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT || slot >= 3 && slot < this.inv.getSlots() && ItemCoffee.getIngredientFromStack(this.inv.getStackInSlot(slot)) == null;
    }

    public void storeCoffee() {
        if (StackUtil.isValid(this.inv.getStackInSlot(SLOT_COFFEE_BEANS)) && ActuallyTags.Items.COFFEE_BEANS.contains(this.inv.getStackInSlot(SLOT_COFFEE_BEANS).getItem())) {
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
        if (StackUtil.isValid(input) && input.getItem() == ActuallyItems.COFFEE_CUP.get() && !StackUtil.isValid(this.inv.getStackInSlot(SLOT_OUTPUT)) && this.coffeeCacheAmount >= CACHE_USE && this.tank.getFluid().getFluid() == Fluids.WATER && this.tank.getFluidAmount() >= WATER_USE) {
            if (this.storage.getEnergyStored() >= ENERGY_USED) {
                if (this.brewTime % 30 == 0) {
                    this.level.playSound(null, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), SoundHandler.coffeeMachine, SoundCategory.BLOCKS, 0.1F, 1.0F);
                }

                this.brewTime++;
                this.storage.extractEnergyInternal(ENERGY_USED, false);
                if (this.brewTime >= TIME_USED) {
                    this.brewTime = 0;
                    ItemStack output = new ItemStack(ActuallyItems.COFFEE.get());
                    for (int i = 3; i < this.inv.getSlots(); i++) {
                        if (StackUtil.isValid(this.inv.getStackInSlot(i))) {
                            CoffeeIngredient ingredient = ItemCoffee.getIngredientFromStack(this.inv.getStackInSlot(i));
                            if (ingredient != null) {
                                if (ingredient.effect(output)) {
                                    this.inv.setStackInSlot(i, StackUtil.shrinkForContainer(this.inv.getStackInSlot(i), 1));
                                }
                            }
                        }
                    }
                    this.inv.setStackInSlot(SLOT_OUTPUT, output.copy());
                    this.inv.getStackInSlot(SLOT_INPUT).shrink(1);
                    this.coffeeCacheAmount -= CACHE_USE;
                    this.tank.drain(WATER_USE, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        } else {
            this.brewTime = 0;
        }
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID == 0 && this.brewTime <= 0) {
            this.brew();
        }
    }

    @Override
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return this.lazyTank;
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
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerCoffeeMachine(windowId, playerInventory, this);
    }
}
