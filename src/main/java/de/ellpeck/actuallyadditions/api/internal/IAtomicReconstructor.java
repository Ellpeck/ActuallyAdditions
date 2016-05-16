/*
 * This file ("IAtomicReconstructor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.world.World;

/**
 * This is a helper interface for Lens' invoke() method.
 * This is not supposed to be implemented.
 * <p>
 * Can be cast to TileEntity.
 */
public interface IAtomicReconstructor{

    /**
     * Returns the x coord of the reconstructor
     */
    int getX();

    /**
     * Returns the y coord of the reconstructor
     */
    int getY();

    /**
     * Returns the z coord of the reconstructor
     */
    int getZ();

    /**
     * Returns the world of the reconstructor
     */
    World getWorldObject();

    /**
     * Extracts a specified amount of energy from the Reconstructor's RF storage
     */
    void extractEnergy(int amount);

    /**
     * Gets the amount of energy the Reconstructor has stored in its RF storage
     */
    int getEnergy();

    Lens getLens();
}
