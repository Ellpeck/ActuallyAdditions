/*
 * This file ("ItemStackHandlerCustom.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerCustom extends ItemStackHandler{

    private boolean tempIgnoreConditions;

    public ItemStackHandlerCustom(int slots){
        super(slots);
    }

    public void decrStackSize(int slot, int amount){
        this.setStackInSlot(slot, StackUtil.addStackSize(this.getStackInSlot(slot), -amount));
    }

    public NonNullList<ItemStack> getItems(){
        return this.stacks;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
        if(!this.tempIgnoreConditions && !this.canInsert(stack, slot)){
            return stack;
        }

        return super.insertItem(slot, stack, simulate);
    }

    public ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate){
        this.tempIgnoreConditions = true;
        ItemStack result = this.insertItem(slot, stack, simulate);
        this.tempIgnoreConditions = false;
        return result;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate){
        if(!this.tempIgnoreConditions && !this.canExtract(this.getStackInSlot(slot), slot)){
            return StackUtil.getNull();
        }

        return super.extractItem(slot, amount, simulate);
    }

    public ItemStack extractItemInternal(int slot, int amount, boolean simulate){
        this.tempIgnoreConditions = true;
        ItemStack result = this.extractItem(slot, amount, simulate);
        this.tempIgnoreConditions = false;
        return result;
    }

    public boolean canInsert(ItemStack stack, int slot){
        return true;
    }

    public boolean canExtract(ItemStack stack, int slot){
        return true;
    }
}
