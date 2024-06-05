/*
 * This file ("BlockGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusher;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusherDouble;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT;

public class BlockCrusher extends BlockContainerBase {
    private final boolean isDouble;

    public BlockCrusher(boolean isDouble) {
        super(ActuallyBlocks.defaultPickProps().randomTicks());
        this.isDouble = isDouble;
        this.registerDefaultState(getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.isDouble
            ? new TileEntityCrusherDouble(pos, state)
            : new TileEntityCrusher(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return this.isDouble
            ? level.isClientSide? TileEntityCrusherDouble::clientTick : TileEntityCrusherDouble::serverTick
            : level.isClientSide? TileEntityCrusher::clientTick : TileEntityCrusher::serverTick;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        if (state.getValue(BlockStateProperties.LIT)) {
            for (int i = 0; i < 5; i++) {
                double xRand = rand.nextDouble() / 0.75D - 0.5D;
                double zRand = rand.nextDouble() / 0.75D - 0.5D;
                world.addParticle(ParticleTypes.CRIT, (double) pos.getX() + 0.4F, (double) pos.getY() + 0.8F, (double) pos.getZ() + 0.4F, xRand, 0.5D, zRand);
            }
            world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (this.isDouble) {
            return this.openGui(world, player, pos, TileEntityCrusherDouble.class);
        }

        return this.openGui(world, player, pos, TileEntityCrusher.class);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(HORIZONTAL_FACING);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT)
                ? 12
                : 0;
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(HORIZONTAL_FACING)) {
            case EAST:
                return VoxelShapes.GrinderShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.GrinderShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.GrinderShapes.SHAPE_W;
            default:
                return VoxelShapes.GrinderShapes.SHAPE_N;
        }
    }*/
}
