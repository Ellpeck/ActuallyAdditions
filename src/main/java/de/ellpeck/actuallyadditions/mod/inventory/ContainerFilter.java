/*
 * This file ("ContainerFilter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ContainerFilter extends Container {

    public static final int SLOT_AMOUNT = 24;

    private final ItemStackHandlerAA filterInventory = new ItemStackHandlerAA(SLOT_AMOUNT);
    private final PlayerInventory inventory;

    public static ContainerFilter fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerFilter(windowId, inv);
    }

    public ContainerFilter(int windowId, PlayerInventory inventory) {
        super(ActuallyContainers.FILTER_CONTAINER.get(), windowId);
        this.inventory = inventory;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                this.addSlot(new SlotFilter(this.filterInventory, j + i * 6, 35 + j * 18, 10 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == inventory.currentItem) {
                this.addSlot(new SlotImmovable(inventory, i, 8 + i * 18, 152));
            } else {
                this.addSlot(new Slot(inventory, i, 8 + i * 18, 152));
            }
        }

        ItemStack stack = inventory.getCurrentItem();
        if (SlotFilter.isFilter(stack)) {
            ItemDrill.loadSlotsFromNBT(this.filterInventory, inventory.getCurrentItem());
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slot) {
        int inventoryStart = SLOT_AMOUNT;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()) {
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if (slot >= inventoryStart) {
                //Shift from Inventory
                //
                if (slot >= inventoryStart && slot <= inventoryEnd) {
                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return StackUtil.getEmpty();
                    }
                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return StackUtil.getEmpty();
                }
            } else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return StackUtil.getEmpty();
            }

            if (!StackUtil.isValid(newStack)) {
                theSlot.putStack(StackUtil.getEmpty());
            } else {
                theSlot.onSlotChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return StackUtil.getEmpty();
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getEmpty();
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return StackUtil.getEmpty();
        } else if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.currentItem) {
            return ItemStack.EMPTY;
        } else {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        ItemStack stack = this.inventory.getCurrentItem();
        if (SlotFilter.isFilter(stack)) {
            ItemDrill.writeSlotsToNBT(this.filterInventory, this.inventory.getCurrentItem());
        }
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }
}
