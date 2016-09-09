/*
 * This file ("TileEntityDistributorItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityDistributorItem extends TileEntityInventoryBase{

    private int sidePutTo;
    private int lastSlotAmount;

    public TileEntityDistributorItem(){
        super(1, "distributorItem");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(this.slots[0] != null){
                TileEntity tile = this.tilesAround[this.sidePutTo];

                if(tile != null){
                    EnumFacing side = EnumFacing.values()[this.sidePutTo].getOpposite();
                    if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)){
                        IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
                        if(cap != null){
                            int amountPer = this.slots[0].stackSize/5;
                            if(amountPer <= 0){
                                amountPer = this.slots[0].stackSize;
                            }
                            ItemStack stackToPut = this.slots[0].copy();
                            stackToPut.stackSize = amountPer;

                            for(int i = 0; i < cap.getSlots(); i++){
                                stackToPut = cap.insertItem(i, stackToPut.copy(), false);
                                if(stackToPut == null){
                                    this.slots[0].stackSize -= amountPer;
                                    break;
                                }
                                else{
                                    this.slots[0].stackSize -= stackToPut.stackSize;
                                }
                            }
                        }
                    }
                }

                this.sidePutTo++;
                if(this.sidePutTo == 1){
                    this.sidePutTo++;
                }
                else if(this.sidePutTo >= 6){
                    this.sidePutTo = 0;
                }
            }

            int stackSize = this.slots[0] == null ? 0 : this.slots[0].stackSize;
            if(stackSize != this.lastSlotAmount && this.sendUpdateWithInterval()){
                this.lastSlotAmount = stackSize;
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        compound.setInteger("PutSide", this.sidePutTo);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.sidePutTo = compound.getInteger("PutSide");
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public boolean shouldSaveHandlersAround(){
        return true;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return direction == EnumFacing.UP && this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
    }
}
