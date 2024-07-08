/*
 * This file ("TileEntityCanolaPress.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.OutputOnlyFluidTank;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCanolaPress;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileEntityCanolaPress extends TileEntityInventoryBase implements MenuProvider, ISharingFluidHandler {

    public static final int ENERGY_USE = 35;
    private static final int TIME = 30;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(40000, 100, 0);
    public final OutputOnlyFluidTank tank = new OutputOnlyFluidTank(2 * FluidType.BUCKET_VOLUME);

    public int currentProcessTime;
    private int lastEnergyStored;
    private int lastTankAmount;
    private int lastProcessTime;

    public TileEntityCanolaPress(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.CANOLA_PRESS.getTileEntityType(), pos, state, 1);
    }

    
    public int getTankScaled(int i) {
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    
    public int getProcessScaled(int i) {
        return this.currentProcessTime * i / TIME;
    }

    
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("ProcessTime", this.currentProcessTime);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(lookupProvider, compound);
        super.writeSyncableNBT(compound, lookupProvider, type);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentProcessTime = compound.getInt("ProcessTime");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(lookupProvider, compound);
        super.readSyncableNBT(compound, lookupProvider, type);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCanolaPress tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCanolaPress tile) {
            tile.serverTick();

            Optional<RecipeHolder<PressingRecipe>> recipe = getRecipeForInput(tile.inv.getStackInSlot(0));
            recipe.ifPresent(h -> {
                PressingRecipe r = h.value();
                if ((FluidStack.isSameFluid(r.getOutput(), tile.tank.getFluid()) || tile.tank.isEmpty()) && r.getOutput().getAmount() <= tile.tank.getCapacity() - tile.tank.getFluidAmount()) {
                    if (tile.storage.getEnergyStored() >= ENERGY_USE) {
                        tile.currentProcessTime++;
                        tile.storage.extractEnergy(ENERGY_USE, false);
                        if (tile.currentProcessTime >= TIME) {
                            tile.currentProcessTime = 0;

                            tile.inv.setStackInSlot(0, StackUtil.shrink(tile.inv.getStackInSlot(0), 1));
                            FluidStack produced = r.getOutput().copy();
                            tile.tank.fillInternal(produced, IFluidHandler.FluidAction.EXECUTE);
                            tile.setChanged();
                        }
                    }
                }
            });
            if (!recipe.isPresent())
                tile.currentProcessTime = 0;

            if ((tile.storage.getEnergyStored() != tile.lastEnergyStored || tile.tank.getFluidAmount() != tile.lastTankAmount | tile.currentProcessTime != tile.lastProcessTime) && tile.sendUpdateWithInterval()) {
                tile.lastEnergyStored = tile.storage.getEnergyStored();
                tile.lastProcessTime = tile.currentProcessTime;
                tile.lastTankAmount = tile.tank.getFluidAmount();
            }
        }
    }

    public boolean validInput(ItemStack stack) {
        return getRecipeForInput(stack).isPresent();
    }

    public static Optional<RecipeHolder<PressingRecipe>> getRecipeForInput(ItemStack stack) {
        return ActuallyAdditionsAPI.PRESSING_RECIPES.stream().filter(recipe -> recipe.value().matches(new SingleRecipeInput(stack), null)).findFirst();
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> slot == 0 && validInput(stack);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
    }

    @Override
    public IFluidHandler getFluidHandler(Direction facing) {
        return this.tank;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return this.tank.getFluidAmount();
    }

    @Override
    public boolean doesShareFluid() {
        return true;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return Direction.values();
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.canola_press");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new ContainerCanolaPress(windowId, playerInventory, this);
    }
}
