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

public class CanolaPressBlock extends ActuallyBlock {
    public CanolaPressBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape PRESS_SHAPE = Stream.of(
            Block.makeCuboidShape(13, 0, 1, 15, 15.5, 3), Block.makeCuboidShape(2, 0, 2, 14, 6, 14),
            Block.makeCuboidShape(2, 10, 2, 14, 15, 14), Block.makeCuboidShape(3, 6, 3, 13, 10, 13),
            Block.makeCuboidShape(1, 0, 1, 3, 15.5, 3), Block.makeCuboidShape(1, 0, 13, 3, 15.5, 15),
            Block.makeCuboidShape(13, 0, 13, 15, 15.5, 15), Block.makeCuboidShape(0.9, 0, 0.9, 3.1, 0.5, 3.1),
            Block.makeCuboidShape(0.9, 0, 12.9, 3.1, 0.5, 15.1), Block.makeCuboidShape(0.9, 5, 12.9, 3.1, 6.5, 15.1),
            Block.makeCuboidShape(0.9, 5, 0.9, 3.1, 6.5, 3.1), Block.makeCuboidShape(12.9, 5, 12.9, 15.1, 6.5, 15.1),
            Block.makeCuboidShape(12.9, 5, 0.9, 15.1, 6.5, 3.1), Block.makeCuboidShape(0.9, 9.5, 12.9, 3.1, 11, 15.1),
            Block.makeCuboidShape(0.9, 9.5, 0.9, 3.1, 11, 3.1), Block.makeCuboidShape(12.9, 9.5, 12.9, 15.1, 11, 15.1),
            Block.makeCuboidShape(12.9, 9.5, 0.9, 15.1, 11, 3.1), Block.makeCuboidShape(12.9, 0, 0.9, 15.1, 0.5, 3.1),
            Block.makeCuboidShape(12.9, 0, 12.9, 15.1, 0.5, 15.1), Block.makeCuboidShape(12.9, 15.5, 0.9, 15.1, 16, 3.1),
            Block.makeCuboidShape(12.9, 15.5, 12.9, 15.1, 16, 15.1), Block.makeCuboidShape(0.9, 15.5, 0.9, 3.1, 16, 3.1),
            Block.makeCuboidShape(0.9, 15.5, 12.9, 3.1, 16, 15.1)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return PRESS_SHAPE;
    }
}
