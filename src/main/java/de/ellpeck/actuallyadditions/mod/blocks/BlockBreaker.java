/*
 * This file ("BlockBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBreaker;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockBreaker extends FullyDirectionalBlock.Container {

    private final boolean isPlacer;

    public BlockBreaker(boolean isPlacer) {
        super(ActuallyBlocks.defaultPickProps());
        this.isPlacer = isPlacer;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.isPlacer
            ? new TileEntityPlacer(pos, state)
            : new TileEntityBreaker(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return this.isPlacer
                ? level.isClientSide? TileEntityPlacer::clientTick : TileEntityPlacer::serverTick
                : level.isClientSide? TileEntityBreaker::clientTick : TileEntityBreaker::serverTick;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return InteractionResult.SUCCESS;
        }

        return this.openGui(world, player, pos, TileEntityBreaker.class);
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return VoxelShapes.BlockBreakerShapes.SHAPE_U;
            case DOWN:
                return VoxelShapes.BlockBreakerShapes.SHAPE_D;
            case EAST:
                return VoxelShapes.BlockBreakerShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.BlockBreakerShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.BlockBreakerShapes.SHAPE_W;
            default:
                return VoxelShapes.BlockBreakerShapes.SHAPE_N;
        }
    }*/
}
