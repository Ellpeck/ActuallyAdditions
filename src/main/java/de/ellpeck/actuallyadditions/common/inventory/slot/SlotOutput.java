package de.ellpeck.actuallyadditions.inventory.slot;

import de.ellpeck.actuallyadditions.util.ItemStackHandlerAA;
import net.minecraft.item.ItemStack;

public class SlotOutput extends SlotItemHandlerUnconditioned {

    public SlotOutput(ItemStackHandlerAA inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}
