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

public class HeatCollectorBlock extends ActuallyBlock {
    public HeatCollectorBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape HEAT_COLLECTOR_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(4, 13, 4, 12, 14, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 4, 5, 15, 5), Block.makeCuboidShape(4, 14, 11, 5, 15, 12),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(15, 11, 4, 16, 12, 12),
            Block.makeCuboidShape(15, 5, 4, 16, 6, 12), Block.makeCuboidShape(15, 7, 4, 16, 8, 12),
            Block.makeCuboidShape(15, 9, 4, 16, 10, 12), Block.makeCuboidShape(4, 11, 15, 12, 12, 16),
            Block.makeCuboidShape(4, 9, 15, 12, 10, 16), Block.makeCuboidShape(4, 7, 15, 12, 8, 16),
            Block.makeCuboidShape(4, 5, 15, 12, 6, 16), Block.makeCuboidShape(0, 11, 4, 1, 12, 12),
            Block.makeCuboidShape(0, 5, 4, 1, 6, 12), Block.makeCuboidShape(0, 7, 4, 1, 8, 12),
            Block.makeCuboidShape(0, 9, 4, 1, 10, 12), Block.makeCuboidShape(4, 11, 0, 12, 12, 1),
            Block.makeCuboidShape(4, 9, 0, 12, 10, 1), Block.makeCuboidShape(4, 7, 0, 12, 8, 1),
            Block.makeCuboidShape(4, 5, 0, 12, 6, 1), Block.makeCuboidShape(2, 14, 2, 4, 15, 14),
            Block.makeCuboidShape(4, 14, 2, 12, 15, 4), Block.makeCuboidShape(4, 14, 12, 12, 15, 14),
            Block.makeCuboidShape(12, 14, 2, 14, 15, 14), Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(1, 0, 2, 2, 15, 14), Block.makeCuboidShape(14, 0, 2, 15, 15, 14),
            Block.makeCuboidShape(1, 0, 1, 15, 15, 2), Block.makeCuboidShape(1, 0, 14, 15, 15, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return HEAT_COLLECTOR_SHAPE;
    }
}
