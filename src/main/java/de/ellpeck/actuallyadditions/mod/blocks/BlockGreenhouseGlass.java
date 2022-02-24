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
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Random;

public class BlockGreenhouseGlass extends BlockBase {
    public BlockGreenhouseGlass() {
        super(ActuallyBlocks.defaultPickProps(0, 0.5F, 10.0F).sound(SoundType.GLASS).randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (world.isClientSide) {
            return;
        }
        if (world.canSeeSkyFromBelowWater(pos) && world.isDay()) {
            Triple<BlockPos, BlockState, IGrowable> trip = this.firstBlock(world, pos);
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

    public Triple<BlockPos, BlockState, IGrowable> firstBlock(World world, BlockPos glassPos) {
        BlockPos.Mutable mut = new BlockPos(glassPos).mutable();
        while (true) {
            mut.set(mut.getX(), mut.getY() - 1, mut.getZ());
            if (mut.getY() < 0) {
                return null;
            }
            BlockState state = world.getBlockState(mut);
            if (state.isSolidRender(world, mut) || state.getBlock() instanceof IGrowable || state.getBlock() == this) {
                if (state.getBlock() instanceof IGrowable) {
                    return Triple.of(mut.immutable(), state, (IGrowable) state.getBlock());
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.GLASS_SHAPE;
    }
}
