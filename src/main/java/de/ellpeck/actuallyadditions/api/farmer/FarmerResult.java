/*
 * This file ("FarmerResult.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.farmer;

/**
 * Return values for IFarmerBehavior, each one has a different meaning in harvest and planting contexts.
 *
 */
public enum FarmerResult {

    /**
     * Indicates that your behavior failed to perform the action it attempted.
     */
    FAIL,

    /**
     * Indicates that your behavior succeeded doing the action it attempted.  For harvesting, this also shrinks the current stack by one, and stops processing.
     */
    SUCCESS,

    /**
     * Indicates you want the farmer to halt all further actions this tick.  This will exit the farmer behavior loop if returned.
     */
    STOP_PROCESSING
}
