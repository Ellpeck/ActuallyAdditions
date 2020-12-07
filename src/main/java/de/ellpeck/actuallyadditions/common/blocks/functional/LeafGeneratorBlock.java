package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.blocks.HorizontallyDirectionalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class LeafGeneratorBlock extends HorizontallyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public LeafGeneratorBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(1, 0, 14, 15, 15, 15), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(5, 11, 0, 11, 12, 1),
            Block.makeCuboidShape(0, 11, 5, 1, 12, 11), Block.makeCuboidShape(15, 11, 5, 16, 12, 11),
            Block.makeCuboidShape(5, 4, 0, 11, 5, 1), Block.makeCuboidShape(0, 4, 5, 1, 5, 11),
            Block.makeCuboidShape(15, 4, 5, 16, 5, 11), Block.makeCuboidShape(4, 4, 0, 5, 12, 1),
            Block.makeCuboidShape(0, 4, 4, 1, 12, 5), Block.makeCuboidShape(15, 4, 4, 16, 12, 5),
            Block.makeCuboidShape(11, 4, 0, 12, 12, 1), Block.makeCuboidShape(0, 4, 11, 1, 12, 12),
            Block.makeCuboidShape(15, 4, 11, 16, 12, 12), Block.makeCuboidShape(4, 13, 4, 12, 14, 12),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(11, 14, 11, 12, 15, 12),
            Block.makeCuboidShape(11, 14, 4, 12, 15, 5), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(12, 14, 2, 14, 15, 14), Block.makeCuboidShape(4, 14, 12, 12, 15, 14),
            Block.makeCuboidShape(4, 14, 2, 12, 15, 4), Block.makeCuboidShape(2, 14, 2, 4, 15, 14),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(1, 0, 1, 15, 15, 2),
            Block.makeCuboidShape(1, 0, 2, 2, 5, 14), Block.makeCuboidShape(2, 1, 2, 3, 14, 14),
            Block.makeCuboidShape(1, 11, 2, 2, 15, 14), Block.makeCuboidShape(1, 5, 2, 2, 11, 5),
            Block.makeCuboidShape(1, 5, 11, 2, 11, 14), Block.makeCuboidShape(13, 1, 2, 14, 14, 14),
            Block.makeCuboidShape(14, 11, 2, 15, 15, 14), Block.makeCuboidShape(14, 5, 11, 15, 11, 14),
            Block.makeCuboidShape(14, 5, 2, 15, 11, 5), Block.makeCuboidShape(14, 0, 2, 15, 5, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(1, 0, 1, 2, 15, 15), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 11, 5, 16, 12, 11),
            Block.makeCuboidShape(5, 11, 0, 11, 12, 1), Block.makeCuboidShape(5, 11, 15, 11, 12, 16),
            Block.makeCuboidShape(15, 4, 5, 16, 5, 11), Block.makeCuboidShape(5, 4, 0, 11, 5, 1),
            Block.makeCuboidShape(5, 4, 15, 11, 5, 16), Block.makeCuboidShape(15, 4, 4, 16, 12, 5),
            Block.makeCuboidShape(11, 4, 0, 12, 12, 1), Block.makeCuboidShape(11, 4, 15, 12, 12, 16),
            Block.makeCuboidShape(15, 4, 11, 16, 12, 12), Block.makeCuboidShape(4, 4, 0, 5, 12, 1),
            Block.makeCuboidShape(4, 4, 15, 5, 12, 16), Block.makeCuboidShape(4, 13, 4, 12, 14, 12),
            Block.makeCuboidShape(4, 14, 4, 5, 15, 5), Block.makeCuboidShape(4, 14, 11, 5, 15, 12),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(2, 14, 12, 14, 15, 14), Block.makeCuboidShape(2, 14, 4, 4, 15, 12),
            Block.makeCuboidShape(12, 14, 4, 14, 15, 12), Block.makeCuboidShape(2, 14, 2, 14, 15, 4),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(14, 0, 1, 15, 15, 15),
            Block.makeCuboidShape(2, 0, 1, 14, 5, 2), Block.makeCuboidShape(2, 1, 2, 14, 14, 3),
            Block.makeCuboidShape(2, 11, 1, 14, 15, 2), Block.makeCuboidShape(11, 5, 1, 14, 11, 2),
            Block.makeCuboidShape(2, 5, 1, 5, 11, 2), Block.makeCuboidShape(2, 1, 13, 14, 14, 14),
            Block.makeCuboidShape(2, 11, 14, 14, 15, 15), Block.makeCuboidShape(2, 5, 14, 5, 11, 15),
            Block.makeCuboidShape(11, 5, 14, 14, 11, 15), Block.makeCuboidShape(2, 0, 14, 14, 5, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(1, 0, 1, 15, 15, 2), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(5, 11, 15, 11, 12, 16),
            Block.makeCuboidShape(15, 11, 5, 16, 12, 11), Block.makeCuboidShape(0, 11, 5, 1, 12, 11),
            Block.makeCuboidShape(5, 4, 15, 11, 5, 16), Block.makeCuboidShape(15, 4, 5, 16, 5, 11),
            Block.makeCuboidShape(0, 4, 5, 1, 5, 11), Block.makeCuboidShape(11, 4, 15, 12, 12, 16),
            Block.makeCuboidShape(15, 4, 11, 16, 12, 12), Block.makeCuboidShape(0, 4, 11, 1, 12, 12),
            Block.makeCuboidShape(4, 4, 15, 5, 12, 16), Block.makeCuboidShape(15, 4, 4, 16, 12, 5),
            Block.makeCuboidShape(0, 4, 4, 1, 12, 5), Block.makeCuboidShape(4, 13, 4, 12, 14, 12),
            Block.makeCuboidShape(11, 14, 4, 12, 15, 5), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(11, 14, 11, 12, 15, 12),
            Block.makeCuboidShape(2, 14, 2, 4, 15, 14), Block.makeCuboidShape(4, 14, 2, 12, 15, 4),
            Block.makeCuboidShape(4, 14, 12, 12, 15, 14), Block.makeCuboidShape(12, 14, 2, 14, 15, 14),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(1, 0, 14, 15, 15, 15),
            Block.makeCuboidShape(14, 0, 2, 15, 5, 14), Block.makeCuboidShape(13, 1, 2, 14, 14, 14),
            Block.makeCuboidShape(14, 11, 2, 15, 15, 14), Block.makeCuboidShape(14, 5, 11, 15, 11, 14),
            Block.makeCuboidShape(14, 5, 2, 15, 11, 5), Block.makeCuboidShape(2, 1, 2, 3, 14, 14),
            Block.makeCuboidShape(1, 11, 2, 2, 15, 14), Block.makeCuboidShape(1, 5, 2, 2, 11, 5),
            Block.makeCuboidShape(1, 5, 11, 2, 11, 14), Block.makeCuboidShape(1, 0, 2, 2, 5, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(14, 0, 1, 15, 15, 15), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 11, 5, 1, 12, 11),
            Block.makeCuboidShape(5, 11, 15, 11, 12, 16), Block.makeCuboidShape(5, 11, 0, 11, 12, 1),
            Block.makeCuboidShape(0, 4, 5, 1, 5, 11), Block.makeCuboidShape(5, 4, 15, 11, 5, 16),
            Block.makeCuboidShape(5, 4, 0, 11, 5, 1), Block.makeCuboidShape(0, 4, 11, 1, 12, 12),
            Block.makeCuboidShape(4, 4, 15, 5, 12, 16), Block.makeCuboidShape(4, 4, 0, 5, 12, 1),
            Block.makeCuboidShape(0, 4, 4, 1, 12, 5), Block.makeCuboidShape(11, 4, 15, 12, 12, 16),
            Block.makeCuboidShape(11, 4, 0, 12, 12, 1), Block.makeCuboidShape(4, 13, 4, 12, 14, 12),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 4, 5, 15, 5), Block.makeCuboidShape(4, 14, 11, 5, 15, 12),
            Block.makeCuboidShape(2, 14, 2, 14, 15, 4), Block.makeCuboidShape(12, 14, 4, 14, 15, 12),
            Block.makeCuboidShape(2, 14, 4, 4, 15, 12), Block.makeCuboidShape(2, 14, 12, 14, 15, 14),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(1, 0, 1, 2, 15, 15),
            Block.makeCuboidShape(2, 0, 14, 14, 5, 15), Block.makeCuboidShape(2, 1, 13, 14, 14, 14),
            Block.makeCuboidShape(2, 11, 14, 14, 15, 15), Block.makeCuboidShape(2, 5, 14, 5, 11, 15),
            Block.makeCuboidShape(11, 5, 14, 14, 11, 15), Block.makeCuboidShape(2, 1, 2, 14, 14, 3),
            Block.makeCuboidShape(2, 11, 1, 14, 15, 2), Block.makeCuboidShape(11, 5, 1, 14, 11, 2),
            Block.makeCuboidShape(2, 5, 1, 5, 11, 2), Block.makeCuboidShape(2, 0, 1, 14, 5, 2)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }
}
