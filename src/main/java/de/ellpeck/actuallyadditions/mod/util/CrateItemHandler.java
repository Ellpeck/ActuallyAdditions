package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.blocks.Crate;
import net.minecraft.world.item.ItemStack;

public class CrateItemHandler extends ItemStackHandlerAA {
    private Crate.Size size;
    public CrateItemHandler(Crate.Size size) {
        super(size.getSlots());

        this.size = size;
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return Math.min(this.getSlotLimit(slot), stack.getMaxStackSize() * this.size.getStackBoost());
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64 * this.size.getStackBoost();
    }
}
