/*
 * This file ("ContainerBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.items.Sack;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.UUID;

public class SackContainer extends AbstractContainerMenu implements IButtonReactor {

    public final FilterSettings filter = new FilterSettings(4, false,false, false, false);
    private final ItemStackHandlerAA bagInventory;
    private final Inventory inventory;
    public boolean autoInsert;
    private boolean oldAutoInsert;

    public static final int SIZE = 28;

    public static SackContainer fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new SackContainer(windowId, inv, data.readUUID(), new ItemStackHandlerAA(28));
    }

    public SackContainer(int windowId, Inventory playerInventory, UUID uuid, ItemStackHandlerAA handler) {
        super(ActuallyContainers.SACK_CONTAINER.get(), windowId);

        this.inventory = playerInventory;
        this.bagInventory = handler;

        for (int row = 0; row < 4; row++) {
            this.addSlot(new SlotFilter(this.filter, row, 155, 10 + row * 18));
        }

        // Sack inventory
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                this.addSlot(new SlotItemHandlerUnconditioned(this.bagInventory, col + row * 7, 10 + col * 18, 10 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !stack.is(ActuallyTags.Items.HOLDS_ITEMS) && SackContainer.this.filter.check(stack);
                    }
                });
            }
        }

        // Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 94 + row * 18));
            }
        }

        // Player Hotbar
        for (int i = 0; i < 9; i++) {
            if (i == playerInventory.selected) {
                this.addSlot(new SlotImmovable(playerInventory, i, 8 + i * 18, 152));
            } else {
                this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 152));
            }
        }

        ItemStack stack = playerInventory.getSelected();
        if (!stack.isEmpty() && stack.getItem() instanceof Sack) {
            if (stack.hasTag()) {
                CompoundTag compound = stack.getOrCreateTag();
                this.filter.readFromNBT(compound, "Filter");
                this.autoInsert = compound.getBoolean("AutoInsert");
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int slot) {
        int inventoryStart = this.bagInventory.getSlots() + 4;
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
                if (!this.filter.check(newStack) || !this.moveItemStackTo(newStack, 4, 32, false)) {
                    if (slot <= inventoryEnd) {
                        if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                //

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
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return; //TODO: Check if this is correct, used to return ItemStack.EMPTY
        } else if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.selected) {
            return; //TODO: Check if this is correct, used to return ItemStack.EMPTY
        } else {
            super.clicked(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void removed(@Nonnull Player player) {
        ItemStack stack = this.inventory.getSelected();
        if (!stack.isEmpty() && stack.getItem() instanceof Sack) {
            CompoundTag compound = stack.getOrCreateTag();
            this.filter.writeToNBT(compound, "Filter");
            compound.putBoolean("AutoInsert", this.autoInsert);
        }
        super.removed(player);
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true;
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        if (buttonID == 0) {
            this.autoInsert = !this.autoInsert;
        } else {
            this.filter.onButtonPressed(buttonID - 1);
        }
    }
}
