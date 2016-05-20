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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

public class TileEntityCompost extends TileEntityInventoryBase{

    public int conversionTime;

    public TileEntityCompost(){
        super(1, "compost");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("ConversionTime", this.conversionTime);
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.conversionTime = compound.getInteger("ConversionTime");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean theFlag = this.conversionTime > 0;

            if(this.slots[0] != null){
                CompostRecipe recipe = getRecipeForInput(this.slots[0]);
                if(recipe != null && this.slots[0].isItemEqual(recipe.input) && this.slots[0].stackSize >= recipe.input.stackSize){
                    this.conversionTime++;
                    if(this.conversionTime >= 3000){
                        this.slots[0] = recipe.output.copy();
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
    public boolean isItemValidForSlot(int i, @Nonnull ItemStack stack){
        return getRecipeForInput(stack) != null;
    }

    @Override
    public void markDirty(){
        super.markDirty();
        this.sendUpdate();
    }

    @Override
    public boolean canInsertItem(int slot, @Nonnull ItemStack stack, @Nonnull EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, @Nonnull ItemStack stack, @Nonnull EnumFacing side){
        return getRecipeForInput(stack) == null;
    }

    public static CompostRecipe getRecipeForInput(ItemStack input){
        if(input != null){
            for(CompostRecipe recipe : ActuallyAdditionsAPI.COMPOST_RECIPES){
                if(input.isItemEqual(recipe.input)){
                    return recipe;
                }
            }
        }
        return null;
    }
}
