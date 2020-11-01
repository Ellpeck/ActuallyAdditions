package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;

public class PlantBlock extends CropsBlock {
    public PlantBlock(int minDropAmount, int maxDropAmount) {
        super(Properties.create(Material.ROCK));
    }
}
