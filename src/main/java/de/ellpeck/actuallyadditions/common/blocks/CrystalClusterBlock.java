package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.types.Crystals;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class CrystalClusterBlock extends Block {
    public CrystalClusterBlock(Crystals crystal) {
        super(Properties.create(Material.ROCK));
    }
}
