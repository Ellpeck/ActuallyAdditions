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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class BlockLampController extends FullyDirectionalBlock {

    public BlockLampController() {
        super(ActuallyBlocks.defaultPickProps());
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        this.updateLamp(worldIn, pos);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.updateLamp(world, pos);
    }

    private void updateLamp(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            BlockState state = world.getBlockState(pos);
            BlockPos coords = pos.relative(WorldUtil.getDirectionByPistonRotation(state));
            this.updateLampsAtPos(world, coords, world.getBestNeighborSignal(pos) > 0, new ArrayList<>());
        }
    }

    private void updateLampsAtPos(Level world, BlockPos pos, boolean powered, List<BlockPos> updatedAlready) {
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

    private void updateSurrounding(Level world, BlockPos pos, boolean powered, List<BlockPos> updatedAlready) {
        for (Direction side : Direction.values()) {
            BlockPos offset = pos.relative(side);
            if (!updatedAlready.contains(offset)) {
                updatedAlready.add(pos);
                this.updateLampsAtPos(world, offset, powered, updatedAlready);
            }
        }
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return VoxelShapes.LampPowererShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.LampPowererShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.LampPowererShapes.SHAPE_W;
            default:
                return VoxelShapes.LampPowererShapes.SHAPE_N;
        }
    }*/
}
