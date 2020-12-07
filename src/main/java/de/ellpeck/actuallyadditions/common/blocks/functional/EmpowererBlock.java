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

public class EmpowererBlock extends ActuallyBlock {
    public EmpowererBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape EMPOWERER_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16), Block.makeCuboidShape(1, 1, 1, 15, 6, 15),
            Block.makeCuboidShape(1, 6, 1, 15, 7, 15), Block.makeCuboidShape(0, 7, 0, 16, 8, 1),
            Block.makeCuboidShape(0, 7, 15, 16, 8, 16), Block.makeCuboidShape(0, 7, 1, 1, 8, 15),
            Block.makeCuboidShape(15, 7, 1, 16, 8, 15), Block.makeCuboidShape(4, 7, 4, 12, 9, 12),
            Block.makeCuboidShape(0, 1, 0, 1, 7, 1), Block.makeCuboidShape(15, 1, 0, 16, 7, 1),
            Block.makeCuboidShape(15, 1, 15, 16, 7, 16), Block.makeCuboidShape(0, 1, 15, 1, 7, 16),
            Block.makeCuboidShape(3, 7, 4, 4, 8, 5), Block.makeCuboidShape(3, 7, 11, 4, 8, 12),
            Block.makeCuboidShape(4, 7, 12, 5, 8, 13), Block.makeCuboidShape(11, 7, 12, 12, 8, 13),
            Block.makeCuboidShape(12, 7, 11, 13, 8, 12), Block.makeCuboidShape(12, 7, 4, 13, 8, 5),
            Block.makeCuboidShape(11, 7, 3, 12, 8, 4), Block.makeCuboidShape(4, 7, 3, 5, 8, 4)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return EMPOWERER_SHAPE;
    }
}
