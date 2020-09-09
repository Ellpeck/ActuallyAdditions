package de.ellpeck.actuallyadditions.common.misc.apiimpl.farmer.exu;

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
