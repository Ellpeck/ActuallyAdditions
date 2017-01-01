/*
 * This file ("SlotItemHandlerUnconditioned.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.slot;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerCustom;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotItemHandlerUnconditioned extends SlotItemHandler{

    private final ItemStackHandlerCustom handler;

    public SlotItemHandlerUnconditioned(ItemStackHandlerCustom handler, int index, int xPosition, int yPosition){
        super(handler, index, xPosition, yPosition);
        this.handler = handler;
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        if(StackUtil.isValid(stack)){
            ItemStack currentStack = this.handler.getStackInSlot(this.getSlotIndex());
            this.handler.setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);
            ItemStack remainder = this.handler.insertItemInternal(this.getSlotIndex(), stack, true);
            this.handler.setStackInSlot(this.getSlotIndex(), currentStack);
            return remainder == null || remainder.getCount() < stack.getCount();
        }
        return false;
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    @Override
    @Nonnull
    public ItemStack getStack(){
        return this.handler.getStackInSlot(this.getSlotIndex());
    }

    @Override
    public void putStack(ItemStack stack){
        this.handler.setStackInSlot(this.getSlotIndex(), stack);
        this.onSlotChanged();
    }

    @Override
    public int getItemStackLimit(ItemStack stack){
        ItemStack maxAdd = stack.copy();
        maxAdd.setCount(stack.getMaxStackSize());
        ItemStack currentStack = this.handler.getStackInSlot(this.getSlotIndex());
        this.handler.setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);
        ItemStack remainder = this.handler.insertItemInternal(this.getSlotIndex(), maxAdd, true);
        this.handler.setStackInSlot(this.getSlotIndex(), currentStack);
        return stack.getMaxStackSize()-remainder.getCount();
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn){
        return !this.handler.extractItemInternal(this.getSlotIndex(), 1, true).isEmpty();
    }

    @Override
    public ItemStack decrStackSize(int amount){
        return this.handler.extractItemInternal(this.getSlotIndex(), amount, false);
    }
}
