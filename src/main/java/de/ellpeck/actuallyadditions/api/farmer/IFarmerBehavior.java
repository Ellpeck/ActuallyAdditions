/*
 * This file ("IFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.farmer;

import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFarmerBehavior{

    /**
     * Try to plant a seed with this behavior
     * If this method return true, the seed ItemStack will have one item deducted from it.
     *
     * @param seed   The seed stack to plant
     * @param world  The world
     * @param pos    The position to plant the seed on
     * @param farmer The Farmer doing this action. Can be used to query and extract energy and add items to the slots
     * @return If planting was successful
     */
    boolean tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer);

    /**
     * Try to harvest a plant with this behavior
     *
     * @param world  The world
     * @param pos    The position of the plant
     * @param farmer The Farmer doing this action. Can be used to query and extract energy and add items to the slots
     * @return If harvesting was successful
     */
    boolean tryHarvestPlant(World world, BlockPos pos, IFarmer farmer);


}
