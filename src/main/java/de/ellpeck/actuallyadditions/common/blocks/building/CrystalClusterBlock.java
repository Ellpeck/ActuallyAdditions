package de.ellpeck.actuallyadditions.common.blocks.building;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.blocks.types.Crystals;
import net.minecraft.block.material.Material;

public class CrystalClusterBlock extends ActuallyBlock {
    public CrystalClusterBlock(Crystals crystal) {
        super(Properties.create(Material.ROCK));
    }
}
