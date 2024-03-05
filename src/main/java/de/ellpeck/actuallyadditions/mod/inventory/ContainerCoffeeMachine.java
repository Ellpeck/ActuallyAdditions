/*
 * This file ("ContainerCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ContainerCoffeeMachine extends AbstractContainerMenu {

    public final TileEntityCoffeeMachine machine;

    public static ContainerCoffeeMachine fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new ContainerCoffeeMachine(windowId, inv, (TileEntityCoffeeMachine) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos())));
    }

    public ContainerCoffeeMachine(int windowId, Inventory inventory, TileEntityCoffeeMachine tile) {
        super(ActuallyContainers.COFFEE_MACHINE_CONTAINER.get(), windowId);
        this.machine = tile;

        this.addSlot(new SlotItemHandlerUnconditioned(this.machine.inv, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS, 37, 6));
        this.addSlot(new SlotItemHandlerUnconditioned(this.machine.inv, TileEntityCoffeeMachine.SLOT_INPUT, 80, 42));
        this.addSlot(new SlotOutput(this.machine.inv, TileEntityCoffeeMachine.SLOT_OUTPUT, 80, 73));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlot(new SlotItemHandlerUnconditioned(this.machine.inv, j + i * 2 + 3, 125 + j * 18, 6 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        int inventoryStart = 11;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(slot);

        if (theSlot != null && theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if (slot == TileEntityCoffeeMachine.SLOT_OUTPUT) {
                if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return ItemStack.EMPTY;
                }
                theSlot.onQuickCraft(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if (slot >= inventoryStart) {
                //Shift from Inventory
                if (newStack.getItem() == ActuallyItems.COFFEE_CUP.get()) {
                    if (!this.moveItemStackTo(newStack, TileEntityCoffeeMachine.SLOT_INPUT, TileEntityCoffeeMachine.SLOT_INPUT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ItemCoffee.getIngredientRecipeFromStack(newStack) != null) {
                    if (!this.moveItemStackTo(newStack, 3, 11, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (newStack.is(ActuallyTags.Items.COFFEE_BEANS)) {
                    if (!this.moveItemStackTo(newStack, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS + 1, false)) {
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

            if (!StackUtil.isValid(newStack)) {
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
    public boolean stillValid(Player player) {
        return this.machine.canPlayerUse(player);
    }
}
