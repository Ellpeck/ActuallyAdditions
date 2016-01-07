/*
 * This file ("TileEntityDropper.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityDropper extends TileEntityInventoryBase implements IRedstoneToggle{

    private int currentTime;
    private boolean activateOnceWithSignal;

    public TileEntityDropper(){
        super(9, "dropper");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered && !this.activateOnceWithSignal){
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
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
    }

    private void doWork(){
        if(this.removeFromInventory(false) != null){
            ItemStack stack = this.removeFromInventory(true);
            stack.stackSize = 1;
            WorldUtil.dropItemAtSide(WorldUtil.getDirectionByPistonRotation(Position.fromTileEntity(this).getMetadata(worldObj)), worldObj, Position.fromTileEntity(this), stack);
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
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }

    @Override
    public void toggle(boolean to){
        this.activateOnceWithSignal = to;
    }

    @Override
    public boolean isPulseMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }
}
