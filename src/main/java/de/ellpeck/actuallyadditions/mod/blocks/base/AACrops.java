package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AACrops extends CropBlock {
    Supplier<? extends Item> itemSupplier;
    public AACrops(Properties properties, Supplier<? extends Item> seedSupplier) {
        super(properties);
        this.itemSupplier = seedSupplier;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.itemSupplier.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        if (level instanceof WorldGenRegion) {
            return state.is(BlockTags.DIRT);
        }
        return super.mayPlaceOn(state, level, pos);
    }
}
