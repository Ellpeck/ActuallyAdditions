/*
 * This file ("TileEntityPhantomBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

public class TileEntityPhantomBreaker extends TileEntityPhantomPlacer{

    public TileEntityPhantomBreaker(){
        super(9, "phantomBreaker");
        this.isBreaker = true;
    }

}
