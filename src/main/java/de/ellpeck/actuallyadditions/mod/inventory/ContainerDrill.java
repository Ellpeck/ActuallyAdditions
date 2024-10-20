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
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.items.ItemDrillUpgrade;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerDrill extends AbstractContainerMenu {

    public static final int SLOT_AMOUNT = 5;

    private final ItemStackHandlerAA drillInventory = new ItemStackHandlerAA(SLOT_AMOUNT);
    private final Inventory inventory;

    public static ContainerDrill fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new ContainerDrill(windowId, inv);
    }

    public ContainerDrill(int windowId, Inventory inventory) {
        super(ActuallyContainers.DRILL_CONTAINER.get(), windowId);
        this.inventory = inventory;

        for (int i = 0; i < SLOT_AMOUNT; i++) {
            this.addSlot(new SlotItemHandlerUnconditioned(this.drillInventory, i, 44 + i * 18, 19) {
                @Override
                public boolean mayPlace(ItemStack stack) {
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
            if (i == inventory.selected) {
                this.addSlot(new SlotImmovable(inventory, i, 8 + i * 18, 116));
            } else {
                this.addSlot(new Slot(inventory, i, 8 + i * 18, 116));
            }
        }

        ItemStack stack = inventory.getSelected();
        if (!stack.isEmpty() && stack.getItem() instanceof DrillItem) {
            DrillItem.loadSlotsFromNBT(this.drillInventory, inventory.getSelected());
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int slot) {
        int inventoryStart = 5;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(slot);

        if (theSlot != null && theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if (slot >= inventoryStart) {
                //Shift from Inventory
                if (newStack.getItem() instanceof ItemDrillUpgrade) {
                    if (!this.moveItemStackTo(newStack, 0, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                //

                else if (slot >= inventoryStart && slot <= inventoryEnd) {
                    if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return ItemStack.EMPTY;
            }

            if (newStack.isEmpty()) {
                theSlot.set(ItemStack.EMPTY);
            } else {
                theSlot.setChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return ItemStack.EMPTY;
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void clicked(int slotId, int dragType, @Nonnull ClickType clickTypeIn, @Nonnull Player player) {
        if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.selected) {
            return; //TODO: Check if this is correct, used to return ItemStack.EMPTY
        } else {
            super.clicked(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void removed(@Nonnull Player player) {
        ItemStack stack = this.inventory.getSelected();
        if (!stack.isEmpty() && stack.getItem() instanceof DrillItem) {
            DrillItem.writeSlotsToNBT(this.drillInventory, this.inventory.getSelected());
        }
        super.removed(player);
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true;
    }
}
