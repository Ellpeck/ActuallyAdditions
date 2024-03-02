/*
 * This file ("IConnectionPair.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.laser;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public interface IConnectionPair {

    void writeToNBT(CompoundTag compound);

    void readFromNBT(CompoundTag compound);

    BlockPos[] getPositions();

    boolean doesSuppressRender();

    LaserType getType();

    boolean contains(BlockPos pos);
}
