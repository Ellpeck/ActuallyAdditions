/*
 * This file ("IEnergyTile.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is not supposed to be implemented.
 * Can be cast to TileEntity.
 */
public interface IEnergyTile{

    BlockPos getPosition();

    int getX();

    int getY();

    int getZ();

    World getWorldObject();

    void extractEnergy(int amount);

    int getEnergy();
}
