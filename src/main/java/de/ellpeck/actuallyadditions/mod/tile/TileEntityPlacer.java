/*
 * This file ("TileEntityPlacer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;

public class TileEntityPlacer extends TileEntityBreaker {

    public TileEntityPlacer() {
        super(ActuallyTiles.PLACER_TILE.get(), 9);
        this.isPlacer = true;
    }

    @Override
    public IAcceptor getAcceptor() {
        return ItemStackHandlerAA.ACCEPT_TRUE;
    }

}
