package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.types.PhantomType;
import net.minecraft.block.material.Material;

public class PhantomBlock extends ActuallyBlock {
    public PhantomBlock(PhantomType type) {
        super(Properties.create(Material.ROCK));
    }
}
