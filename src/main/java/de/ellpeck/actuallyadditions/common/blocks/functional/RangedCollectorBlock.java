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

public class RangedCollectorBlock extends ActuallyBlock {
    public RangedCollectorBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape COLLECTOR_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(1, 1, 4, 2, 4, 12), Block.makeCuboidShape(14, 1, 4, 15, 4, 12),
            Block.makeCuboidShape(4, 1, 1, 12, 4, 2), Block.makeCuboidShape(4, 1, 14, 12, 4, 15),
            Block.makeCuboidShape(4, 14, 12, 12, 15, 14), Block.makeCuboidShape(1, 12, 4, 2, 15, 12),
            Block.makeCuboidShape(14, 12, 4, 15, 15, 12), Block.makeCuboidShape(4, 12, 1, 12, 15, 2),
            Block.makeCuboidShape(4, 12, 14, 12, 15, 15), Block.makeCuboidShape(4, 14, 2, 12, 15, 4),
            Block.makeCuboidShape(1, 1, 12, 2, 15, 14), Block.makeCuboidShape(14, 1, 2, 15, 15, 4),
            Block.makeCuboidShape(1, 1, 1, 4, 15, 2), Block.makeCuboidShape(12, 1, 14, 15, 15, 15),
            Block.makeCuboidShape(12, 14, 2, 14, 15, 14), Block.makeCuboidShape(1, 1, 2, 2, 15, 4),
            Block.makeCuboidShape(14, 1, 12, 15, 15, 14), Block.makeCuboidShape(12, 1, 1, 15, 15, 2),
            Block.makeCuboidShape(1, 1, 14, 4, 15, 15), Block.makeCuboidShape(2, 14, 2, 4, 15, 14),
            Block.makeCuboidShape(2, 2, 2, 14, 14, 14), Block.makeCuboidShape(1, 0, 1, 15, 1, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return COLLECTOR_SHAPE;
    }
}
