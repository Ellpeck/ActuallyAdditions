/*
 * This file ("BlockGreenhouseGlass.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.tuple.Triple;

public class BlockGreenhouseGlass extends BlockBase {
    public BlockGreenhouseGlass() {
        super(ActuallyBlocks.defaultPickProps(0.5F, 10.0F).sound(SoundType.GLASS).randomTicks());
    }


    // TODO: [port] figure this out
    //    @Override
    //    @Deprecated
    //    @OnlyIn(Dist.CLIENT)
    //    public boolean shouldSideBeRendered(BlockState state, IBlockAccess world, BlockPos pos, Direction side) {
    //        BlockState otherState = world.getBlockState(pos.offset(side));
    //        Block block = otherState.getBlock();
    //
    //        return state != otherState || block != this && super.shouldSideBeRendered(state, world, pos, side);
    //    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        if (world.isClientSide) {
            return;
        }
        if (world.canSeeSkyFromBelowWater(pos) && world.isDay()) {
            Triple<BlockPos, BlockState, BonemealableBlock> trip = this.firstBlock(world, pos);
            boolean once = false;
            if (trip != null) {
                for (int i = 0; i < 3; i++) {
                    BlockState growState = i == 0
                        ? trip.getMiddle()
                        : world.getBlockState(trip.getLeft());
                    if (growState.getBlock() == trip.getRight() && trip.getRight().isValidBonemealTarget(world, trip.getLeft(), growState, false)) {
                        trip.getRight().performBonemeal(world, rand, trip.getLeft(), growState);
                        once = true;
                    }
                }
            }
            if (once) {
                world.levelEvent(2005, trip.getMiddle().isSolidRender(world, trip.getLeft())
                    ? trip.getLeft().above()
                    : trip.getLeft(), 0);
            }
        }
    }

    public Triple<BlockPos, BlockState, BonemealableBlock> firstBlock(Level world, BlockPos glassPos) {
        BlockPos.MutableBlockPos mut = new BlockPos(glassPos).mutable();
        while (true) {
            mut.set(mut.getX(), mut.getY() - 1, mut.getZ());
            if (mut.getY() < 0) {
                return null;
            }
            BlockState state = world.getBlockState(mut);
            if (state.isSolidRender(world, mut) || state.getBlock() instanceof BonemealableBlock || state.getBlock() == this) {
                if (state.getBlock() instanceof BonemealableBlock) {
                    return Triple.of(mut.immutable(), state, (BonemealableBlock) state.getBlock());
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.GLASS_SHAPE;
    }
}
