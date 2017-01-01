/*
 * This file ("IAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.util.EnumFacing;

/**
 * This is a helper interface for Lens' invoke() method.
 * <p>
 * This is not supposed to be implemented.
 * Can be cast to TileEntity.
 */
public interface IAtomicReconstructor extends IEnergyTile{

    Lens getLens();

    EnumFacing getOrientation();
}
