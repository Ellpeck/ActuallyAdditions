package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;

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