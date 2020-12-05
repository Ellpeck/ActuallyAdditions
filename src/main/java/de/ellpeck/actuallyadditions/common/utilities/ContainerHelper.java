package de.ellpeck.actuallyadditions.common.utilities;

import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.Consumer;

public class ContainerHelper {
    public static final int DEFAULT_SLOTS_X = 8;
    public static final int SLOT_SPACING = 18;

    /**
     * This value assumes you have used the {@link ContainerHelper#setupPlayerInventory(IItemHandler, int, int, int, Consumer)}
     * method to generate your inventories slots. Do not use if otherwise
     */
    public static final int PLAYER_INVENTORY_END_SLOT = 35;

    /**
     *
     * @param handler   the inventory handler (should always be the players)
     * @param index     the starting index, should always be 0 logically. If otherwise, do not use {@link ContainerHelper#PLAYER_INVENTORY_END_SLOT}
     * @param x         x offset for the inventory, for a full width inventory, this is typically 8
     * @param y         y offset for the inventory, no default sorry
     * @param consumer  Typically a containers the Containers#addSlot(Slot) method
     */
    public static void setupPlayerInventory(IItemHandler handler, int index, int x, int y, Consumer<Slot> consumer) {
        // Build the players inventory first, building from bottom to top, right to left. The (i>0) magic handles the
        // space between the hotbar inventory and the players remaining inventory.
        for (int i = 0; i < 4; i++) {
            boolean isHotbar = i < 1;
            for (int j = 0; j < 9; j++) {
                consumer.accept(new SlotItemHandler(handler, index, x + (j * SLOT_SPACING), isHotbar ? y : ((y - 76) + (i * SLOT_SPACING))));
                index++;
            }
        }
    }

    public static ItemStackHandler getHandlerFromItem(ItemStack itemStack, String key, int size) {
        ItemStackHandler handler = new ItemStackHandler(size);
        handler.deserializeNBT(itemStack.getOrCreateChildTag(key));
        return handler;
    }

}
