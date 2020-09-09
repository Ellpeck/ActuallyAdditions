package de.ellpeck.actuallyadditions.common.misc.apiimpl.farmer.exu;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;

public class RedOrchidFarmerBehavior extends ExUPlantFarmerBehavior {

    @Override
    protected String getPlantName() {
        return "extrautils2:redorchid";
    }

    @Override
    protected boolean canPlaceOn(Block block) {
        return block instanceof BlockRedstoneOre;
    }

    @Override
    protected int getMaxStage() {
        return 6;
    }
}
