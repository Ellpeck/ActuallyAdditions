package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.types.PhantomType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class PhantomBlock extends Block {
    public PhantomBlock(PhantomType type) {
        super(Properties.create(Material.ROCK));
    }
}
