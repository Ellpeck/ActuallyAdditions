/*
 * This file ("TileEntityCompost.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
        if(!this.world.isRemote){
            boolean theFlag = this.conversionTime > 0;

            if(StackUtil.isValid(this.slots.getStackInSlot(0))){
                CompostRecipe recipe = getRecipeForInput(this.slots.getStackInSlot(0));
                if(recipe != null){
                    this.conversionTime++;
                    if(this.conversionTime >= 3000){
                        ItemStack output = recipe.output.copy();
                        this.slots.setStackInSlot(0, StackUtil.setStackSize(output, StackUtil.getStackSize(this.slots.getStackInSlot(0))));
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
    public boolean canExtractItem(int slot, ItemStack stack){
        return getRecipeForInput(stack) == null;
    }
}
