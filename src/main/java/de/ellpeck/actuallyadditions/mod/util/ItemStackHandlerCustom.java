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
import net.minecraftforge.items.ItemHandlerHelper;
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
        if(!StackUtil.isValid(stack)){
            return StackUtil.getNull();
        }
        this.validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        int limit = this.getStackLimit(slot, stack);
        if(StackUtil.isValid(existing)){
            if(!ItemHandlerHelper.canItemStacksStack(stack, existing)){
                return stack;
            }
            limit -= existing.getCount();
        }
        if(limit <= 0){
            return stack;
        }

        if(!this.tempIgnoreConditions && !this.canInsert(stack, slot)){
            return stack;
        }

        boolean reachedLimit = stack.getCount() > limit;
        if(!simulate){
            if(!StackUtil.isValid(existing)){
                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else{
                existing.grow(reachedLimit ? limit : stack.getCount());
            }

            this.onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()-limit) : ItemStack.EMPTY;

    }

    public ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate){
        this.tempIgnoreConditions = true;
        ItemStack result = this.insertItem(slot, stack, simulate);
        this.tempIgnoreConditions = false;
        return result;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate){
        if(amount <= 0){
            return StackUtil.getNull();
        }
        this.validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);
        if(!StackUtil.isValid(existing)){
            return StackUtil.getNull();
        }

        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if(toExtract <= 0){
            return StackUtil.getNull();
        }

        if(!this.tempIgnoreConditions && !this.canExtract(this.getStackInSlot(slot), slot)){
            return StackUtil.getNull();
        }

        if(existing.getCount() <= toExtract){
            if(!simulate){
                this.stacks.set(slot, StackUtil.getNull());
                this.onContentsChanged(slot);
                return existing;
            }
            return existing.copy();
        }
        else{
            if(!simulate){
                this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount()-toExtract));
                this.onContentsChanged(slot);
            }
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
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
