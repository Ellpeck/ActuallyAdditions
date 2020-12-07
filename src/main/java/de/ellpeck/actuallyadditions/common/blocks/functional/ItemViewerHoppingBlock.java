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

public class ItemViewerHoppingBlock extends ActuallyBlock {
    public ItemViewerHoppingBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape HOPPING_ITEM_VIEWER_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 10, 0, 16, 11, 16), Block.makeCuboidShape(1, 11, 1, 2, 15, 15),
            Block.makeCuboidShape(14, 11, 1, 15, 15, 15), Block.makeCuboidShape(2, 11, 1, 14, 15, 2),
            Block.makeCuboidShape(2, 11, 14, 14, 15, 15), Block.makeCuboidShape(4, 4, 4, 12, 10, 12),
            Block.makeCuboidShape(6, 0, 6, 10, 4, 10), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(0, 11, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 11, 15, 1, 15, 16), Block.makeCuboidShape(15, 11, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 11, 0, 16, 15, 1)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return HOPPING_ITEM_VIEWER_SHAPE;
    }
}
