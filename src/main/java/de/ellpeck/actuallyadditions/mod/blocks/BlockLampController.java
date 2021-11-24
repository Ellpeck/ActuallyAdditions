/*
 * This file ("BlockLampPowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockLampController extends FullyDirectionalBlock {

    public BlockLampController() {
        super(ActuallyBlocks.defaultPickProps(0));
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        this.updateLamp(worldIn, pos);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.updateLamp(world, pos);
    }

    private void updateLamp(World world, BlockPos pos) {
        if (!world.isClientSide) {
            BlockState state = world.getBlockState(pos);
            BlockPos coords = pos.relative(WorldUtil.getDirectionByPistonRotation(state));
            this.updateLampsAtPos(world, coords, world.getBestNeighborSignal(pos) > 0, new ArrayList<>());
        }
    }

    private void updateLampsAtPos(World world, BlockPos pos, boolean powered, List<BlockPos> updatedAlready) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof BlockColoredLamp) {
            if (state.getValue(BlockStateProperties.LIT) && !powered) {
                world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false));
            }

            if (!state.getValue(BlockStateProperties.LIT) && powered) {
                world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, true));
            }

            this.updateSurrounding(world, pos, powered, updatedAlready);
        }
    }

    private void updateSurrounding(World world, BlockPos pos, boolean powered, List<BlockPos> updatedAlready) {
        for (Direction side : Direction.values()) {
            BlockPos offset = pos.relative(side);
            if (!updatedAlready.contains(offset)) {
                updatedAlready.add(pos);
                this.updateLampsAtPos(world, offset, powered, updatedAlready);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return Shapes.LampPowererShapes.SHAPE_E;
            case SOUTH:
                return Shapes.LampPowererShapes.SHAPE_S;
            case WEST:
                return Shapes.LampPowererShapes.SHAPE_W;
            default:
                return Shapes.LampPowererShapes.SHAPE_N;
        }
    }
}
