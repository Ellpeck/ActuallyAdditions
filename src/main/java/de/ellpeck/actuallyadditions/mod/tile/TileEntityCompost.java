/*
 * This file ("TileEntityCompost.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityCompost extends TileEntityInventoryBase{

    public int conversionTime;

    public TileEntityCompost(){
        super(1, "compost");
    }

    public static CompostRecipe getRecipeForInput(ItemStack input){
        if(StackUtil.isValid(input)){
            for(CompostRecipe recipe : ActuallyAdditionsAPI.COMPOST_RECIPES){
                if(input.isItemEqual(recipe.input)){
                    return recipe;
                }
            }
        }
        return null;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("ConversionTime", this.conversionTime);
        }
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.conversionTime = compound.getInteger("ConversionTime");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean theFlag = this.conversionTime > 0;

            if(StackUtil.isValid(this.slots[0])){
                CompostRecipe recipe = getRecipeForInput(this.slots[0]);
                if(recipe != null){
                    this.conversionTime++;
                    if(this.conversionTime >= 3000){
                        ItemStack copy = recipe.output.copy();
                        this.slots[0] = StackUtil.setStackSize(copy, StackUtil.getStackSize(this.slots[0]));
                        this.conversionTime = 0;
                        this.markDirty();
                    }
                }
                else{
                    this.conversionTime = 0;
                }
            }

            if(theFlag != this.conversionTime > 0){
                this.markDirty();
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return getRecipeForInput(stack) != null;
    }

    @Override
    public void markDirty(){
        super.markDirty();
        this.sendUpdate();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return getRecipeForInput(stack) == null;
    }
}
