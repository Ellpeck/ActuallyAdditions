package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class FeederBlock extends ActuallyBlock {

    public FeederBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape FEEDER_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(4, 13, 4, 12, 14, 12), Block.makeCuboidShape(4, 14, 12, 12, 15, 15),
            Block.makeCuboidShape(4, 14, 1, 12, 15, 4), Block.makeCuboidShape(1, 14, 1, 4, 15, 15),
            Block.makeCuboidShape(12, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(14, 2, 1, 15, 14, 15), Block.makeCuboidShape(1, 2, 1, 2, 14, 15),
            Block.makeCuboidShape(2, 2, 14, 14, 14, 15), Block.makeCuboidShape(2, 2, 1, 14, 14, 2)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return FEEDER_SHAPE;
    }
}
