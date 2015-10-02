/*
 * This file ("SlotFilter.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.slot;

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
     * @param button The button pressed (1 is right mouse button!)
     * @return Nothing, as the Item didn't really get "transferred"
     */
    public ItemStack slotClick(EntityPlayer player, int button){
        ItemStack heldStack = player.inventory.getItemStack();

        //Delete the stack in the inventory
        if(this.getStack() != null && heldStack == null){
            this.putStack(null);
        }
        //Put the current Item as a filter
        else{
            if(heldStack != null){
                ItemStack stack = heldStack.copy();
                stack.stackSize = 1;
                this.putStack(stack);
            }
        }

        return null;
    }

    @Override
    public void putStack(ItemStack stack){
        ItemStack theStack = (stack != null ? stack.copy() : null);
        super.putStack(theStack);
    }

}
