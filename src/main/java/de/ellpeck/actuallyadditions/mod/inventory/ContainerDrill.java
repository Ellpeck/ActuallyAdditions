/*
 * This file ("ContainerDrill.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.items.ItemDrillUpgrade;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ContainerDrill extends Container {

    public static final int SLOT_AMOUNT = 5;

    private final ItemStackHandlerAA drillInventory = new ItemStackHandlerAA(SLOT_AMOUNT);
    private final PlayerInventory inventory;

    public static ContainerDrill fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerDrill(windowId, inv);
    }

    public ContainerDrill(int windowId, PlayerInventory inventory) {
        super(ActuallyContainers.DRILL_CONTAINER.get(), windowId);
        this.inventory = inventory;

        for (int i = 0; i < SLOT_AMOUNT; i++) {
            this.addSlot(new SlotItemHandlerUnconditioned(this.drillInventory, i, 44 + i * 18, 19) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() instanceof ItemDrillUpgrade;
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 58 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == inventory.currentItem) {
                this.addSlot(new SlotImmovable(inventory, i, 8 + i * 18, 116));
            } else {
                this.addSlot(new Slot(inventory, i, 8 + i * 18, 116));
            }
        }

        ItemStack stack = inventory.getCurrentItem();
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemDrill) {
            ItemDrill.loadSlotsFromNBT(this.drillInventory, inventory.getCurrentItem());
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slot) {
        int inventoryStart = 5;
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
                if (newStack.getItem() instanceof ItemDrillUpgrade) {
                    if (!this.mergeItemStack(newStack, 0, 5, false)) {
                        return StackUtil.getEmpty();
                    }
                }
                //

                else if (slot >= inventoryStart && slot <= inventoryEnd) {
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
        if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.currentItem) {
            return ItemStack.EMPTY;
        } else {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        ItemStack stack = this.inventory.getCurrentItem();
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemDrill) {
            ItemDrill.writeSlotsToNBT(this.drillInventory, this.inventory.getCurrentItem());
        }
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }
}
