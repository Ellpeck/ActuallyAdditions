/*
 * This file ("SlotFilter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.slot;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot{

    public SlotFilter(IInventory inv, int slot, int x, int y){
        super(inv, slot, x, y);
    }

    /**
     * Gets called when the Filter Slot is clicked
     * Needs to be called in slotClick() in the Container!
     *
     * @param player The player
     * @return Nothing, as the Item didn't really get "transferred"
     */
    public ItemStack slotClick(EntityPlayer player){
        ItemStack heldStack = player.inventory.getItemStack();

        //Delete the stack in the inventory
        if(StackUtil.isValid(this.getStack()) && !StackUtil.isValid(heldStack)){
            this.putStack(StackUtil.getNull());
        }
        //Put the current Item as a filter
        else{
            if(StackUtil.isValid(heldStack)){
                ItemStack stack = heldStack.copy();
                stack = StackUtil.setStackSize(stack, 1);
                this.putStack(stack);
            }
        }

        return StackUtil.getNull();
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        return false;
    }

    @Override
    public void putStack(ItemStack stack){
        super.putStack(StackUtil.validateCopy(stack));
    }

    @Override
    public boolean canTakeStack(EntityPlayer player){
        return false;
    }
}
