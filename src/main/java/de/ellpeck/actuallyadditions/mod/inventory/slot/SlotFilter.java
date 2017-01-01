/*
 * This file ("SlotFilter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.slot;

import de.ellpeck.actuallyadditions.mod.items.ItemFilter;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerCustom;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends SlotItemHandlerUnconditioned{

    public SlotFilter(ItemStackHandlerCustom inv, int slot, int x, int y){
        super(inv, slot, x, y);
    }

    public SlotFilter(FilterSettings inv, int slot, int x, int y){
        this(inv.filterInventory, slot, x, y);
    }

    public static boolean checkFilter(Container container, int slotId, EntityPlayer player){
        if(slotId >= 0 && slotId < container.inventorySlots.size()){
            Slot slot = container.getSlot(slotId);
            if(slot instanceof SlotFilter){
                ((SlotFilter)slot).slotClick(player);
                return true;
            }
        }
        return false;
    }

    public static boolean isFilter(ItemStack stack){
        return StackUtil.isValid(stack) && stack.getItem() instanceof ItemFilter;
    }

    private void slotClick(EntityPlayer player){
        ItemStack heldStack = player.inventory.getItemStack();
        ItemStack stackInSlot = this.getStack();

        if(StackUtil.isValid(stackInSlot) && !StackUtil.isValid(heldStack)){
            if(isFilter(stackInSlot)){
                player.inventory.setItemStack(stackInSlot);
            }

            this.putStack(StackUtil.getNull());
        }
        else if(StackUtil.isValid(heldStack)){
            if(!isFilter(stackInSlot)){
                this.putStack(StackUtil.setStackSize(heldStack.copy(), 1));

                if(isFilter(heldStack)){
                    player.inventory.setItemStack(StackUtil.addStackSize(heldStack, -1));
                }
            }
        }
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
