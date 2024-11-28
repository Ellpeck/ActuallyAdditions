package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class AACrops extends CropBlock {
    Supplier<? extends Item> itemSupplier;

    public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent");
    public AACrops(Properties properties, Supplier<? extends Item> seedSupplier) {
        super(properties);
        this.itemSupplier = seedSupplier;

        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(PERSISTENT, false));
    }

    @Nonnull
    @Override
    protected ItemLike getBaseSeedId() {
        return this.itemSupplier.get();
    }

    @Override
    protected boolean mayPlaceOn(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        if (level instanceof WorldGenRegion) {
            return state.is(BlockTags.DIRT);
        }
        return super.mayPlaceOn(state, level, pos);
    }

    @Override
    protected boolean canSurvive(@Nonnull BlockState state, @Nonnull LevelReader level, @Nonnull BlockPos pos) {
        if (!(level instanceof WorldGenRegion) && state.getValue(PERSISTENT) && !level.getBlockState(pos.below()).isAir())
            return true;
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, PERSISTENT);
    }
}
