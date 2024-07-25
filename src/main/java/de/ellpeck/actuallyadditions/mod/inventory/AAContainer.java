package de.ellpeck.actuallyadditions.mod.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class AAContainer extends AbstractContainerMenu {
    public final int numSlots;
    public final Function<ItemStack, Boolean> VALIDATOR;

    protected AAContainer(@Nullable MenuType<?> menuType, int containerId, int numSlots, Function<ItemStack, Boolean> validator) {
        super(menuType, containerId);
        this.numSlots = numSlots;
        VALIDATOR = validator;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        int inventoryEnd = numSlots + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(index);

        if (theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            if (index >= numSlots) { //Shift from inventory to container.
                if (numSlots > 0 && VALIDATOR.apply(newStack)) { //Check if the item is valid.
                    if (!this.moveItemStackTo(newStack, 0, numSlots, false)) { // try to move to container.
                        return ItemStack.EMPTY;
                    }
                }
                else if (index <= inventoryEnd) { //Shift from inventory to hotbar.
                    if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) { // try to move to hotbar.
                        return ItemStack.EMPTY;
                    }
                } else if (index >= inventoryEnd + 1 && index < hotbarEnd + 1 && !this.moveItemStackTo(newStack, numSlots, inventoryEnd + 1, false)) { //Shift from hotbar to inventory.
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(newStack, numSlots, hotbarEnd + 1, false)) { //Shift from container to inventory.
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
}
