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

public class DisplayStandBlock extends ActuallyBlock {
    public DisplayStandBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape STAND_SHAPE = Stream.of(
            Block.makeCuboidShape(1, 7, 0, 15, 8, 1), Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(1, 1, 1, 15, 7, 15), Block.makeCuboidShape(6, 7, 6, 10, 9, 10),
            Block.makeCuboidShape(0, 1, 0, 1, 7, 1), Block.makeCuboidShape(15, 1, 0, 16, 7, 1),
            Block.makeCuboidShape(15, 1, 15, 16, 7, 16), Block.makeCuboidShape(0, 1, 15, 1, 7, 16),
            Block.makeCuboidShape(0, 7, 0, 1, 8, 16), Block.makeCuboidShape(15, 7, 0, 16, 8, 16),
            Block.makeCuboidShape(1, 7, 15, 15, 8, 16), Block.makeCuboidShape(5, 7, 5, 6, 9, 6),
            Block.makeCuboidShape(5, 7, 10, 6, 9, 11), Block.makeCuboidShape(10, 7, 10, 11, 9, 11),
            Block.makeCuboidShape(10, 7, 5, 11, 9, 6)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return STAND_SHAPE;
    }
}
