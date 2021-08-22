/*
 * This file ("TileEntityDisplayStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityDisplayStand extends TileEntityInventoryBase implements IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(80000, 1000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int oldEnergy;

    public TileEntityDisplayStand() {
        super(ActuallyTiles.DISPLAYSTAND_TILE.get(), 1);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.level.isClientSide) {
            if (StackUtil.isValid(this.inv.getStackInSlot(0)) && !this.isRedstonePowered) {
                IDisplayStandItem item = this.convertToDisplayStandItem(this.inv.getStackInSlot(0).getItem());
                if (item != null) {
                    int energy = item.getUsePerTick(this.inv.getStackInSlot(0), this, this.ticksElapsed);
                    if (this.storage.getEnergyStored() >= energy) {
                        if (item.update(this.inv.getStackInSlot(0), this, this.ticksElapsed)) {
                            this.storage.extractEnergyInternal(energy, false);
                        }
                    }
                }
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    private IDisplayStandItem convertToDisplayStandItem(Item item) {
        if (item instanceof IDisplayStandItem) {
            return (IDisplayStandItem) item;
        } else if (item instanceof BlockItem) {
            Block block = Block.byItem(item);
            if (block instanceof IDisplayStandItem) {
                return (IDisplayStandItem) block;
            }
        }
        return null;
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 1;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    public ItemStack getStack() {
        return this.inv.getStackInSlot(0);
    }
}
