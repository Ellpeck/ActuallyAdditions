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

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusher;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusherDouble;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCrusher extends BlockContainerBase {
    private final boolean isDouble;

    public BlockCrusher(boolean isDouble) {
        super(ActuallyBlocks.defaultPickProps(0).randomTicks());
        this.isDouble = isDouble;
        this.registerDefaultState(getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.isDouble
            ? new TileEntityCrusherDouble()
            : new TileEntityCrusher();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.isDouble) {
            return this.openGui(world, player, pos, TileEntityCrusherDouble.class);
        }

        return this.openGui(world, player, pos, TileEntityCrusher.class);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(HORIZONTAL_FACING);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(LIT)
            ? 12
            : 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(HORIZONTAL_FACING)) {
            case EAST:
                return Shapes.GrinderShapes.SHAPE_E;
            case SOUTH:
                return Shapes.GrinderShapes.SHAPE_S;
            case WEST:
                return Shapes.GrinderShapes.SHAPE_W;
            default:
                return Shapes.GrinderShapes.SHAPE_N;
        }
    }
}
