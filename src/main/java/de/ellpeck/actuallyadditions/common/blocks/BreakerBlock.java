package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BreakerBlock extends Block {
    public BreakerBlock(boolean isPlacer) {
        super(Properties.create(Material.ROCK));
    }
}
