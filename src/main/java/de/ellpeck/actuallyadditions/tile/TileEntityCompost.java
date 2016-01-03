/*
 * This file ("TileEntityCompost.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.tile;

import de.ellpeck.actuallyadditions.items.InitItems;
import de.ellpeck.actuallyadditions.items.ItemFertilizer;
import de.ellpeck.actuallyadditions.items.ItemMisc;
import de.ellpeck.actuallyadditions.items.metalists.TheMiscItems;
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
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("ConversionTime", this.conversionTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.conversionTime = compound.getInteger("ConversionTime");
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        super.setInventorySlotContents(i, stack);
        this.sendUpdate();
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        this.sendUpdate();
        return super.decrStackSize(i, j);
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
