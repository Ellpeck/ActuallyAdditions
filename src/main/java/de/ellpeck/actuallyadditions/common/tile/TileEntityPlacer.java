package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IAcceptor;

public class TileEntityPlacer extends TileEntityBreaker {

    public TileEntityPlacer() {
        super(9, "placer");
        this.isPlacer = true;
    }

    @Override
    public IAcceptor getAcceptor() {
        return ItemStackHandlerAA.ACCEPT_TRUE;
    }

}
