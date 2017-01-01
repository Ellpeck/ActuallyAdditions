/*
 * This file ("TileEntityDropper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
        if(!this.world.isRemote){
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
        ItemStack theoreticalRemove = this.removeFromInventory(false);
        if(StackUtil.isValid(theoreticalRemove)){
            IBlockState state = this.world.getBlockState(this.pos);
            if(WorldUtil.dropItemAtSide(WorldUtil.getDirectionByPistonRotation(state.getBlock().getMetaFromState(state)), this.world, this.pos, StackUtil.setStackSize(theoreticalRemove.copy(), 1))){
                this.removeFromInventory(true);
            }
        }
    }

    public ItemStack removeFromInventory(boolean actuallyDo){
        for(int i = 0; i < this.slots.getSlots(); i++){
            if(StackUtil.isValid(this.slots.getStackInSlot(i))){
                ItemStack slot = this.slots.getStackInSlot(i).copy();
                if(actuallyDo){
                    this.slots.setStackInSlot(i, StackUtil.addStackSize(this.slots.getStackInSlot(i), -1));
                    this.markDirty();
                }
                return slot;
            }
        }
        return StackUtil.getNull();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
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
