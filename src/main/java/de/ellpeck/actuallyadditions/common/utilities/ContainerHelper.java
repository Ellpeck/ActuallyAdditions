package de.ellpeck.actuallyadditions.common.utilities;

import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.Consumer;

public class ContainerHelper {
    public static final int DEFAULT_SLOTS_X = 8;
    public static final int SLOT_SPACING = 18;

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

}
