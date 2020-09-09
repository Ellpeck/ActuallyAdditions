package de.ellpeck.actuallyadditions.common.blocks.base;

import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;

public class BlockBushBase extends BushBlock {
    
    public BlockBushBase(Properties properties) {
        super(properties.sound(SoundType.PLANT));
    }
}
