/*
 * This file ("TileEntityDisplayStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityDisplayStand extends TileEntityInventoryBase implements IEnergyDisplay, IEnergyReceiver{

    private EnergyStorage storage = new EnergyStorage(300000);
    private int oldEnergy;

    public TileEntityDisplayStand(){
        super(1, "displayStand");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(this.slots[0] != null && !this.isRedstonePowered){
                IDisplayStandItem item = this.convertToDisplayStandItem(this.slots[0].getItem());
                if(item != null){
                    int energy = item.getUsePerTick(this.slots[0], this, this.ticksElapsed);
                    if(this.storage.getEnergyStored() >= energy){
                        if(item.update(this.slots[0], this, this.ticksElapsed)){
                            this.storage.extractEnergy(energy, false);
                        }
                    }
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void markDirty(){
        super.markDirty();
        this.sendUpdate();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.writeSyncableNBT(compound, isForSync);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound, isForSync);
        this.storage.readFromNBT(compound);
    }

    private IDisplayStandItem convertToDisplayStandItem(Item item){
        if(item instanceof IDisplayStandItem){
            return (IDisplayStandItem)item;
        }
        else if(item instanceof ItemBlock){
            Block block = Block.getBlockFromItem(item);
            if(block instanceof IDisplayStandItem){
                return (IDisplayStandItem)block;
            }
        }
        return null;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return true;
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return from != EnumFacing.UP ? this.storage.receiveEnergy(maxReceive, simulate) : 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return from != EnumFacing.UP ? this.storage.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return from != EnumFacing.UP ? this.storage.getMaxEnergyStored() : 0;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return from != EnumFacing.UP;
    }

    @Override
    public int getInventoryStackLimit(){
        return 1;
    }
}
