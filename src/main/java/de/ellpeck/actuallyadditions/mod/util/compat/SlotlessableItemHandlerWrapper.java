package de.ellpeck.actuallyadditions.mod.util.compat;

import net.minecraftforge.items.IItemHandler;

public class SlotlessableItemHandlerWrapper {

    private final IItemHandler normalHandler;
    private final Object slotlessHandler;

    public SlotlessableItemHandlerWrapper(IItemHandler normalHandler, Object slotlessHandler) {
        this.normalHandler = normalHandler;
        this.slotlessHandler = slotlessHandler;
    }

    public IItemHandler getNormalHandler() {
        return this.normalHandler;
    }

    public Object getSlotlessHandler() {
        return this.slotlessHandler;
    }
}
