package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class CrystalBlock extends Block {
    public CrystalBlock(boolean isEmpowered) {
        super(Properties.create(Material.ROCK));
    }
}
