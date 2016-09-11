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

import java.util.HashMap;
import java.util.Map;

public class TileEntityDistributorItem extends TileEntityInventoryBase{

    private int putSide;
    private final Map<EnumFacing, IItemHandler> handlersAround = new HashMap<EnumFacing, IItemHandler>();

    public TileEntityDistributorItem(){
        super(1, "distributorItem");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(!this.handlersAround.isEmpty() && this.slots[0] != null){
                EnumFacing[] allFacings = EnumFacing.values();
                do{
                    this.putSide++;

                    if(this.putSide >= 6){
                        this.putSide = 0;
                    }
                }
                while(!this.handlersAround.containsKey(allFacings[this.putSide]));

                EnumFacing putFacing = allFacings[this.putSide];
                IItemHandler handler = this.handlersAround.get(putFacing);
                if(handler != null){
                    boolean shouldMarkDirty = false;

                    int amount = this.slots[0].stackSize/this.handlersAround.size();
                    if(amount <= 0){
                        amount = this.slots[0].stackSize;
                    }

                    if(amount > 0){
                        ItemStack toInsert = this.slots[0].copy();
                        toInsert.stackSize = amount;

                        for(int i = 0; i < handler.getSlots(); i++){
                            ItemStack notInserted = handler.insertItem(i, toInsert.copy(), false);
                            if(notInserted == null){
                                this.slots[0].stackSize -= amount;

                                shouldMarkDirty = true;
                                break;
                            }
                            else if(notInserted.stackSize != this.slots[0].stackSize){
                                this.slots[0].stackSize -= notInserted.stackSize;
                                toInsert = notInserted;

                                shouldMarkDirty = true;
                            }
                        }

                        if(this.slots[0].stackSize <= 0){
                            this.slots[0] = null;
                            shouldMarkDirty = true;
                        }

                        if(shouldMarkDirty){
                            this.markDirty();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveAllHandlersAround(){
        this.handlersAround.clear();

        for(EnumFacing side : EnumFacing.values()){
            if(side != EnumFacing.UP){
                TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(side));
                if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())){
                    IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                    if(cap != null){
                        this.handlersAround.put(side, cap);
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
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
