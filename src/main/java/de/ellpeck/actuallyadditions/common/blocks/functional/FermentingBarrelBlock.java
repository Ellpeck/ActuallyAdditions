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

public class FermentingBarrelBlock extends ActuallyBlock {
    public FermentingBarrelBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape BARREL_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 12, 0, 16, 14, 1), Block.makeCuboidShape(1, 0.5, 1, 15, 15, 15),
            Block.makeCuboidShape(0, 2, 15, 16, 4, 16), Block.makeCuboidShape(0, 7, 15, 16, 9, 16),
            Block.makeCuboidShape(0, 12, 15, 16, 14, 16), Block.makeCuboidShape(0, 2, 0, 16, 4, 1),
            Block.makeCuboidShape(0, 7, 0, 16, 9, 1), Block.makeCuboidShape(0, 2, 1, 1, 4, 15),
            Block.makeCuboidShape(0, 7, 1, 1, 9, 15), Block.makeCuboidShape(0, 12, 1, 1, 14, 15),
            Block.makeCuboidShape(15, 12, 1, 16, 14, 15), Block.makeCuboidShape(15, 7, 1, 16, 9, 15),
            Block.makeCuboidShape(15, 2, 1, 16, 4, 15), Block.makeCuboidShape(7, 0, 0.5, 9, 16, 1.5),
            Block.makeCuboidShape(0.5, 0, 7, 1.5, 16, 9), Block.makeCuboidShape(7, 0, 14.5, 9, 16, 15.5),
            Block.makeCuboidShape(14.5, 0, 7, 15.5, 16, 9), Block.makeCuboidShape(2, 0, 0.5, 5, 16, 1.5),
            Block.makeCuboidShape(0.5, 0, 11, 1.5, 16, 14), Block.makeCuboidShape(2, 0, 14.5, 5, 16, 15.5),
            Block.makeCuboidShape(14.5, 0, 11, 15.5, 16, 14), Block.makeCuboidShape(11, 0, 0.5, 14, 16, 1.5),
            Block.makeCuboidShape(0.5, 0, 2, 1.5, 16, 5), Block.makeCuboidShape(11, 0, 14.5, 14, 16, 15.5),
            Block.makeCuboidShape(14.5, 0, 2, 15.5, 16, 5), Block.makeCuboidShape(4, 15, 7, 6, 15.3, 9),
            Block.makeCuboidShape(2, 15, 4, 3, 15.3, 12), Block.makeCuboidShape(4, 15, 13, 12, 15.3, 14),
            Block.makeCuboidShape(4, 15, 2, 12, 15.3, 3), Block.makeCuboidShape(13, 15, 4, 14, 15.3, 12),
            Block.makeCuboidShape(3, 15, 3, 4, 15.3, 4), Block.makeCuboidShape(3, 15, 12, 4, 15.3, 13),
            Block.makeCuboidShape(12, 15, 3, 13, 15.3, 4), Block.makeCuboidShape(12, 15, 12, 13, 15.3, 13)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BARREL_SHAPE;
    }
}
