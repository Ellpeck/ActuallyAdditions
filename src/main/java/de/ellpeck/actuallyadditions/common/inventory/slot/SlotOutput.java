package de.ellpeck.actuallyadditions.common.inventory.slot;

import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
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
