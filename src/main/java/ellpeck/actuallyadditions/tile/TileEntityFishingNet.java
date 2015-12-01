/*
 * This file ("TileEntityFishingNet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFishingNet extends TileEntityBase{

    public int timeUntilNextDrop;

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(worldObj.getBlock(xCoord, yCoord-1, zCoord).getMaterial() == Material.water){
                    if(this.timeUntilNextDrop > 0){
                        this.timeUntilNextDrop--;
                        if(timeUntilNextDrop <= 0){
                            ItemStack fishable = FishingHooks.getRandomFishable(Util.RANDOM, Util.RANDOM.nextFloat());
                            TileEntity tile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
                            if(tile != null && tile instanceof IInventory){
                                this.insertIntoInventory((IInventory)tile, fishable);
                            }
                            else{
                                EntityItem item = new EntityItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, fishable);
                                item.lifespan = 2000;
                                worldObj.spawnEntityInWorld(item);
                            }
                        }
                    }
                    else{
                        int time = 15000;
                        this.timeUntilNextDrop = time+Util.RANDOM.nextInt(time/2);
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        compound.setInteger("TimeUntilNextDrop", this.timeUntilNextDrop);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.timeUntilNextDrop = compound.getInteger("TimeUntilNextDrop");
    }

    public void insertIntoInventory(IInventory inventory, ItemStack stack){
        for(int i = 0; i < inventory.getSizeInventory(); i++){
            if(inventory.isItemValidForSlot(i, stack)){
                if(!(inventory instanceof ISidedInventory) || ((ISidedInventory)inventory).canInsertItem(i, stack, ForgeDirection.DOWN.flag)){
                    ItemStack slot = inventory.getStackInSlot(i);
                    if(slot == null){
                        inventory.setInventorySlotContents(i, stack);
                        return;
                    }
                    if(slot.isItemEqual(stack) && slot.stackSize <= slot.getMaxStackSize()-stack.stackSize && slot.stackSize <= inventory.getInventoryStackLimit()-stack.stackSize){
                        slot.stackSize += stack.stackSize;
                        return;
                    }
                }
            }
        }
    }
}
