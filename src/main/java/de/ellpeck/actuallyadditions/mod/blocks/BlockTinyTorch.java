/*
 * This file ("BlockTinyTorch.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockTinyTorch extends BlockBase {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP);

    public BlockTinyTorch() {
        super(Properties.of().pushReaction(PushReaction.DESTROY).sound(SoundType.WOOD).strength(0.0F, 0.8F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 12;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return VoxelShapes.TinyTorchShapes.TORCH_EAST_AABB;
            case WEST:
                return VoxelShapes.TinyTorchShapes.TORCH_WEST_AABB;
            case SOUTH:
                return VoxelShapes.TinyTorchShapes.TORCH_SOUTH_AABB;
            case NORTH:
                return VoxelShapes.TinyTorchShapes.TORCH_NORTH_AABB;
            default:
                return VoxelShapes.TinyTorchShapes.STANDING_AABB;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        return !this.canSurvive(state, levelAccessor, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, levelAccessor, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        if (direction == Direction.UP) {
            return canSupportCenter(levelReader, pos.below(), Direction.UP);
        } else {
            BlockPos blockpos = pos.relative(direction.getOpposite());
            BlockState blockstate = levelReader.getBlockState(blockpos);
            return blockstate.isFaceSturdy(levelReader, blockpos, direction);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader levelreader = context.getLevel();
        BlockState state = this.defaultBlockState();
        Direction facing = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        if (this.canSurvive(state, levelreader, pos)) {
            return state.setValue(BlockTinyTorch.FACING, facing);
        } else {
            Direction[] adirection = context.getNearestLookingDirections();
            for(Direction direction : adirection) {
                if (direction.getAxis().isHorizontal()) {
                    Direction direction1 = direction.getOpposite();
                    state = state.setValue(FACING, direction1);
                    if (state.canSurvive(levelreader, pos)) {
                        return state;
                    }
                }
            }
            return state;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        if (randomSource.nextBoolean()) {
            Direction direction = state.getValue(FACING);
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + 0.4D;
            double d2 = pos.getZ() + 0.5D;

            if (direction.getAxis().isHorizontal()) {
                Direction opposite = direction.getOpposite();
                level.addParticle(ParticleTypes.SMOKE, d0 + 0.35D * opposite.getStepX(), d1 + 0.22D, d2 + 0.35D * opposite.getStepZ(), 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.FLAME, d0 + 0.35D * opposite.getStepX(), d1 + 0.22D, d2 + 0.35D * opposite.getStepZ(), 0.0D, 0.0D, 0.0D);
            } else {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }
}
