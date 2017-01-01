/*
 * This file ("ILensItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.lens;

/**
 * This is the base class for a Reconstructor Lens Item
 */
public interface ILensItem{

    /**
     * Returns the lens type that belongs to this lens item
     */
    Lens getLens();
}
