/*
 * This file ("BlockBushBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;

public class BlockBushBase extends BushBlock {
    public static final MapCodec<BlockBushBase> CODEC = simpleCodec(BlockBushBase::new);
    
    public BlockBushBase(Properties properties) {
        super(properties.sound(SoundType.GRASS));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
