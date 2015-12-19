/*
 * This file ("TileEntityCompost.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemFertilizer;
import ellpeck.actuallyadditions.items.ItemMisc;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCompost extends TileEntityInventoryBase{

    public static final int AMOUNT = 10;
    public int conversionTime;

    public TileEntityCompost(){
        super(1, "compost");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){

            if(this.slots[0] != null && this.slots[0].stackSize > 0){
                int toSet = this.slots[0].stackSize+(this.slots[0].getItem() instanceof ItemFertilizer ? 1 : 0);
                if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != toSet){
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, toSet, 2);
                }
            }
            else{
                if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0){
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                }
            }

            boolean theFlag = this.conversionTime > 0;
            if(this.slots[0] != null && !(this.slots[0].getItem() instanceof ItemFertilizer) && this.slots[0].stackSize >= AMOUNT){
                this.conversionTime++;
                if(this.conversionTime >= 2000){
                    this.slots[0] = new ItemStack(InitItems.itemFertilizer, AMOUNT);
                    this.conversionTime = 0;
                }
            }
            if(theFlag != this.conversionTime > 0){
                this.markDirty();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        compound.setInteger("ConversionTime", this.conversionTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.conversionTime = compound.getInteger("ConversionTime");
    }

    @Override
    public int getInventoryStackLimit(){
        return AMOUNT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return stack.getItem() instanceof ItemMisc && stack.getItemDamage() == TheMiscItems.MASHED_FOOD.ordinal();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return stack.getItem() instanceof ItemFertilizer;
    }
}
