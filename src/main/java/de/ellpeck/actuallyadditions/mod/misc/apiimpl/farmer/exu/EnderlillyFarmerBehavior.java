/*
 * This file ("EnderlillyFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;

public class EnderlillyFarmerBehavior extends ExUPlantFarmerBehavior {

    @Override
    protected String getPlantName() {
        return "extrautils2:enderlilly";
    }

    @Override
    protected boolean canPlaceOn(Block block) {
        return block == Blocks.END_STONE || block instanceof BlockDirt || block instanceof BlockGrass;
    }

    @Override
    protected int getMaxStage() {
        return 7;
    }
}
