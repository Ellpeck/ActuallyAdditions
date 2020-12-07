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

public class FireworkBoxBlock extends ActuallyBlock {
    public FireworkBoxBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape FIREWORKS_BOX_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(10, 14, 9, 12, 15, 10), Block.makeCuboidShape(7, 14, 9, 9, 15, 10),
            Block.makeCuboidShape(4, 14, 9, 6, 15, 10), Block.makeCuboidShape(10, 14, 6, 12, 15, 7),
            Block.makeCuboidShape(7, 14, 6, 9, 15, 7), Block.makeCuboidShape(4, 14, 6, 6, 15, 7),
            Block.makeCuboidShape(6, 14, 4, 7, 15, 12), Block.makeCuboidShape(9, 14, 4, 10, 15, 12),
            Block.makeCuboidShape(4, 14, 12, 12, 15, 14), Block.makeCuboidShape(4, 14, 2, 12, 15, 4),
            Block.makeCuboidShape(12, 14, 2, 14, 15, 14), Block.makeCuboidShape(2, 14, 2, 4, 15, 14),
            Block.makeCuboidShape(2, 13, 2, 14, 14, 14), Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(1, 0, 2, 2, 15, 14), Block.makeCuboidShape(14, 0, 2, 15, 15, 14),
            Block.makeCuboidShape(1, 0, 1, 15, 15, 2), Block.makeCuboidShape(1, 0, 14, 15, 15, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return FIREWORKS_BOX_SHAPE;
    }
}
