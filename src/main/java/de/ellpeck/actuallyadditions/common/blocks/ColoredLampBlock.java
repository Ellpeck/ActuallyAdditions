package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ColoredLampBlock extends Block {
    public ColoredLampBlock(boolean isLit) {
        super(Properties.create(Material.ROCK));
    }
}
