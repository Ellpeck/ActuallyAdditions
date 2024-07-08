/*
 * This file ("BlockFluidCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidCollector;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockFluidCollector extends FullyDirectionalBlock.Container {
    private final boolean isPlacer;

    public BlockFluidCollector(boolean isPlacer) {
        super(ActuallyBlocks.defaultPickProps());
        this.isPlacer = isPlacer;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.isPlacer
            ? new TileEntityFluidPlacer(pos, state)
            : new TileEntityFluidCollector(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return this.isPlacer
                ? level.isClientSide? TileEntityFluidPlacer::clientTick : TileEntityFluidPlacer::serverTick
                : level.isClientSide? TileEntityFluidCollector::clientTick : TileEntityFluidCollector::serverTick;
    }

    @Nonnull
    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHitResult) {
        if (world.isClientSide)
            return ItemInteractionResult.SUCCESS;

        if (this.tryToggleRedstone(world, pos, player)) {
            return ItemInteractionResult.CONSUME;
        }
        if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, pHitResult.getDirection())) {
            return ItemInteractionResult.SUCCESS;
        }

        return this.openGui2(world, player, pos, TileEntityFluidCollector.class);
    }

/*    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return VoxelShapes.FluidCollectorShapes.SHAPE_U;
            case DOWN:
                return VoxelShapes.FluidCollectorShapes.SHAPE_D;
            case EAST:
                return VoxelShapes.FluidCollectorShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.FluidCollectorShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.FluidCollectorShapes.SHAPE_W;
            default:
                return VoxelShapes.FluidCollectorShapes.SHAPE_N;
        }
    }*/
}
