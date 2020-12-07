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

public class ShockSuppressorBlock extends ActuallyBlock {
    public ShockSuppressorBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape SUPPRESSOR_SHAPE = Stream.of(
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(9, 13, 9, 13, 16, 13), Block.makeCuboidShape(9, 2, 9, 13, 3, 13),
            Block.makeCuboidShape(3, 13, 9, 7, 16, 13), Block.makeCuboidShape(3, 2, 9, 7, 3, 13),
            Block.makeCuboidShape(9, 13, 3, 13, 16, 7), Block.makeCuboidShape(9, 2, 3, 13, 3, 7),
            Block.makeCuboidShape(3, 13, 3, 7, 16, 7), Block.makeCuboidShape(3, 2, 3, 7, 3, 7),
            Block.makeCuboidShape(4, 3, 10, 6, 13, 12), Block.makeCuboidShape(10, 3, 10, 12, 13, 12),
            Block.makeCuboidShape(10, 3, 4, 12, 13, 6), Block.makeCuboidShape(4, 3, 4, 6, 13, 6)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SUPPRESSOR_SHAPE;
    }
}
