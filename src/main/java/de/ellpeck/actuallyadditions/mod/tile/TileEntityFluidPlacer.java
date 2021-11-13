/*
 * This file ("TileEntityFluidPlacer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;

public class TileEntityFluidPlacer extends TileEntityFluidCollector {

    public TileEntityFluidPlacer() {
        super(ActuallyBlocks.FLUID_PLACER.getTileEntityType());
        this.isPlacer = true;
    }

}
