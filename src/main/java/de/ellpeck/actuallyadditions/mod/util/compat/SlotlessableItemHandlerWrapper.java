/*
 * This file ("SlotlessableItemHandlerWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class SlotlessableItemHandlerWrapper {

    private final LazyOptional<IItemHandler> normalHandler;
    private final Object slotlessHandler;

    public SlotlessableItemHandlerWrapper(LazyOptional<IItemHandler> normalHandler, Object slotlessHandler) {
        this.normalHandler = normalHandler;
        this.slotlessHandler = slotlessHandler;
    }

    public LazyOptional<IItemHandler> getNormalHandler() {
        return this.normalHandler;
    }

    public Object getSlotlessHandler() {
        return this.slotlessHandler;
    }
}
