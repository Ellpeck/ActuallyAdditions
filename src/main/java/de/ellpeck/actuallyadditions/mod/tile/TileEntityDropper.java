/*
 * This file ("TileEntityDropper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityDropper extends TileEntityInventoryBase{

    private int currentTime;

    public TileEntityDropper(){
        super(9, "dropper");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.currentTime = compound.getInteger("CurrentTime");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered && !this.isPulseMode){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        this.doWork();
                    }
                }
                else{
                    this.currentTime = 5;
                }
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    private void doWork(){
        if(this.removeFromInventory(false) != null){
            ItemStack stack = this.removeFromInventory(true);
            stack.stackSize = 1;
            WorldUtil.dropItemAtSide(WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(this.pos, this.worldObj)), this.worldObj, this.pos, stack);
        }
    }

    public ItemStack removeFromInventory(boolean actuallyDo){
        for(int i = 0; i < this.slots.length; i++){
            if(this.slots[i] != null){
                ItemStack slot = this.slots[i].copy();
                if(actuallyDo){
                    this.slots[i].stackSize--;
                    if(this.slots[i].stackSize <= 0){
                        this.slots[i] = null;
                    }
                }
                return slot;
            }
        }
        return null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }

    @Override
    public boolean isRedstoneToggle(){
        return true;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }
}
