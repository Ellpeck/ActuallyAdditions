package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import net.minecraft.block.material.Material;

public class BreakerBlock extends ActuallyBlock {
    public BreakerBlock(boolean isPlacer) {
        super(Properties.create(Material.ROCK));
    }
}
