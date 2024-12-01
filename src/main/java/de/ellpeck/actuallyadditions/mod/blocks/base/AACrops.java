package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;
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

    @Nonnull
    @Override
    protected ItemInteractionResult useItemOn(@Nonnull ItemStack pStack, @Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult pHitResult) {
        if (this.getAge(state) < 7) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!world.isClientSide) {
            List<ItemStack> drops = Block.getDrops(state, (ServerLevel) world, pos, null, player, pStack);
            boolean deductedSeedSize = false;
            for (ItemStack drop : drops) {
                if (!drop.isEmpty()) {
                    if (drop.getItem() == this.itemSupplier.get() && !deductedSeedSize) {
                        drop.shrink(1);
                        deductedSeedSize = true;
                    }
                    if (!drop.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(player, drop);
                    }
                }
            }

            world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, 0));
        }

        return super.useItemOn(pStack, state, world, pos, player, hand, pHitResult);
    }
}
