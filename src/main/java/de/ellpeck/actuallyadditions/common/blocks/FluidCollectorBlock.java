package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class FluidCollectorBlock extends Block {
    public FluidCollectorBlock(boolean isPlacer) {
        super(Properties.create(Material.ROCK));
    }
}
