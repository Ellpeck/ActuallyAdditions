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

import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCanolaPress;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityCanolaPress extends TileEntityInventoryBase implements INamedContainerProvider, ISharingFluidHandler {

    public static final int PRODUCE = 80;
    public static final int ENERGY_USE = 35;
    private static final int TIME = 30;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(40000, 100, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    public final FluidTank tank = new FluidTank(2 * Util.BUCKET) {
        // TODO: [port] ensure this is the correct replacement for canFill
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return false;
        }
    };
    public final LazyOptional<IFluidHandler> lazyFluid = LazyOptional.of(() -> this.tank);

    public int currentProcessTime;
    private int lastEnergyStored;
    private int lastTankAmount;
    private int lastProcessTime;

    public TileEntityCanolaPress() {
        super(ActuallyTiles.CANOLAPRESS_TILE.get(), 1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getTankScaled(int i) {
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @OnlyIn(Dist.CLIENT)
    public int getProcessScaled(int i) {
        return this.currentProcessTime * i / TIME;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("ProcessTime", this.currentProcessTime);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.currentProcessTime = compound.getInt("ProcessTime");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (isCanola(this.inv.getStackInSlot(0)) && PRODUCE <= this.tank.getCapacity() - this.tank.getFluidAmount()) {
                if (this.storage.getEnergyStored() >= ENERGY_USE) {
                    this.currentProcessTime++;
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                    if (this.currentProcessTime >= TIME) {
                        this.currentProcessTime = 0;

                        this.inv.setStackInSlot(0, StackUtil.shrink(this.inv.getStackInSlot(0), 1));

                        this.tank.fill(new FluidStack(InitFluids.fluidCanolaOil.get(), PRODUCE), IFluidHandler.FluidAction.EXECUTE);
                        this.setChanged();
                    }
                }
            } else {
                this.currentProcessTime = 0;
            }

            if ((this.storage.getEnergyStored() != this.lastEnergyStored || this.tank.getFluidAmount() != this.lastTankAmount | this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()) {
                this.lastEnergyStored = this.storage.getEnergyStored();
                this.lastProcessTime = this.currentProcessTime;
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> slot == 0 && isCanola(stack);
    }

    public static boolean isCanola(ItemStack stack) {
        return stack.getItem() == ActuallyItems.CANOLA.get();
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
    }

    @Override
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return this.lazyFluid;
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
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerCanolaPress(windowId, playerInventory, this);
    }
}
