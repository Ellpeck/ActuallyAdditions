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

public class PhantomBoosterBlock extends ActuallyBlock {
    public PhantomBoosterBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape BOOSTER_SHAPE = Stream.of(
            Block.makeCuboidShape(5, 12, 12, 11, 13, 13), Block.makeCuboidShape(6, 0, 6, 10, 16, 10),
            Block.makeCuboidShape(5, 2, 5, 11, 3, 11), Block.makeCuboidShape(5, 4, 12, 11, 5, 13),
            Block.makeCuboidShape(5, 6, 12, 11, 7, 13), Block.makeCuboidShape(5, 8, 12, 11, 9, 13),
            Block.makeCuboidShape(5, 10, 12, 11, 11, 13), Block.makeCuboidShape(5, 4, 3, 11, 5, 4),
            Block.makeCuboidShape(5, 6, 3, 11, 7, 4), Block.makeCuboidShape(5, 8, 3, 11, 9, 4),
            Block.makeCuboidShape(5, 10, 3, 11, 11, 4), Block.makeCuboidShape(5, 12, 3, 11, 13, 4),
            Block.makeCuboidShape(3, 4, 5, 4, 5, 11), Block.makeCuboidShape(3, 6, 5, 4, 7, 11),
            Block.makeCuboidShape(3, 8, 5, 4, 9, 11), Block.makeCuboidShape(3, 10, 5, 4, 11, 11),
            Block.makeCuboidShape(3, 12, 5, 4, 13, 11), Block.makeCuboidShape(12, 4, 5, 13, 5, 11),
            Block.makeCuboidShape(12, 6, 5, 13, 7, 11), Block.makeCuboidShape(12, 8, 5, 13, 9, 11),
            Block.makeCuboidShape(12, 10, 5, 13, 11, 11), Block.makeCuboidShape(12, 12, 5, 13, 13, 11),
            Block.makeCuboidShape(5, 14, 5, 11, 15, 11), Block.makeCuboidShape(4, 4, 11, 5, 5, 12),
            Block.makeCuboidShape(4, 6, 11, 5, 7, 12), Block.makeCuboidShape(4, 8, 11, 5, 9, 12),
            Block.makeCuboidShape(4, 10, 11, 5, 11, 12), Block.makeCuboidShape(4, 12, 11, 5, 13, 12),
            Block.makeCuboidShape(4, 4, 4, 5, 5, 5), Block.makeCuboidShape(4, 6, 4, 5, 7, 5),
            Block.makeCuboidShape(4, 8, 4, 5, 9, 5), Block.makeCuboidShape(4, 10, 4, 5, 11, 5),
            Block.makeCuboidShape(4, 12, 4, 5, 13, 5), Block.makeCuboidShape(11, 4, 4, 12, 5, 5),
            Block.makeCuboidShape(11, 6, 4, 12, 7, 5), Block.makeCuboidShape(11, 8, 4, 12, 9, 5),
            Block.makeCuboidShape(11, 10, 4, 12, 11, 5), Block.makeCuboidShape(11, 12, 4, 12, 13, 5),
            Block.makeCuboidShape(11, 4, 11, 12, 5, 12), Block.makeCuboidShape(11, 6, 11, 12, 7, 12),
            Block.makeCuboidShape(11, 8, 11, 12, 9, 12), Block.makeCuboidShape(11, 10, 11, 12, 11, 12),
            Block.makeCuboidShape(11, 12, 11, 12, 13, 12)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOOSTER_SHAPE;
    }
}
