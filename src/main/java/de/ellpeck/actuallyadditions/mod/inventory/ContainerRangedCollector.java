/*
 * This file ("ContainerRangedCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityRangedCollector;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ContainerRangedCollector extends AbstractContainerMenu {

    public final TileEntityRangedCollector collector;

    public static ContainerRangedCollector fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new ContainerRangedCollector(windowId, inv, (TileEntityRangedCollector) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos())));
    }

    public ContainerRangedCollector(int windowId, Inventory inventory, TileEntityRangedCollector tile) {
        super(ActuallyContainers.RANGED_COLLECTOR_CONTAINER.get(), windowId);
        this.collector = tile;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SlotItemHandlerUnconditioned(this.collector.inv, j + i * 3, 96 + j * 18, 24 + i * 18));
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new SlotFilter(this.collector.filter, j + i * 3, 20 + j * 18, 6 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 90 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 148));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int slot) {
        int inventoryStart = 18;
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
                if (!this.moveItemStackTo(newStack, 0, 6, false)) {
                    //
                    if (slot >= inventoryStart && slot <= inventoryEnd) {
                        if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
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
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return; //TODO: Check if this is correct, used to return ItemStack.EMPTY
        } else {
            super.clicked(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return this.collector.canPlayerUse(player);
    }
}
