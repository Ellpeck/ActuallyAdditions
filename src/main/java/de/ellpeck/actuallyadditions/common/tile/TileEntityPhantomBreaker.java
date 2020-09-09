package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IRemover;

public class TileEntityPhantomBreaker extends TileEntityPhantomPlacer {

    public TileEntityPhantomBreaker() {
        super(9, "phantomBreaker");
        this.isBreaker = true;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation;
    }

    @Override
    public IRemover getRemover() {
        return ItemStackHandlerAA.REMOVE_TRUE;
    }

}