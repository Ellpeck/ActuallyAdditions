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

public class MinerBlock extends HorizontallyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MinerBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(15, 3, 3, 16, 13, 13), Block.makeCuboidShape(0, 3, 3, 1, 13, 13),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 4, 5, 15, 5), Block.makeCuboidShape(3, 12, 5, 4, 14, 11),
            Block.makeCuboidShape(12, 12, 5, 13, 14, 11), Block.makeCuboidShape(5, 12, 3, 11, 14, 4),
            Block.makeCuboidShape(5, 12, 12, 11, 14, 13), Block.makeCuboidShape(11, 12, 11, 12, 14, 12),
            Block.makeCuboidShape(11, 12, 4, 12, 14, 5), Block.makeCuboidShape(4, 12, 4, 5, 14, 5),
            Block.makeCuboidShape(4, 12, 11, 5, 14, 12), Block.makeCuboidShape(4, 14, 11, 5, 15, 12),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(2, 14, 2, 4, 15, 14),
            Block.makeCuboidShape(4, 14, 2, 12, 15, 4), Block.makeCuboidShape(4, 14, 12, 12, 15, 14),
            Block.makeCuboidShape(12, 14, 2, 14, 15, 14), Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(1, 0, 2, 2, 15, 14), Block.makeCuboidShape(14, 0, 2, 15, 15, 14),
            Block.makeCuboidShape(1, 0, 1, 15, 15, 2), Block.makeCuboidShape(1, 0, 14, 15, 15, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(3, 3, 15, 13, 13, 16), Block.makeCuboidShape(3, 3, 0, 13, 13, 1),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12), Block.makeCuboidShape(11, 14, 11, 12, 15, 12),
            Block.makeCuboidShape(11, 14, 4, 12, 15, 5), Block.makeCuboidShape(5, 12, 3, 11, 14, 4),
            Block.makeCuboidShape(5, 12, 12, 11, 14, 13), Block.makeCuboidShape(12, 12, 5, 13, 14, 11),
            Block.makeCuboidShape(3, 12, 5, 4, 14, 11), Block.makeCuboidShape(4, 12, 11, 5, 14, 12),
            Block.makeCuboidShape(11, 12, 11, 12, 14, 12), Block.makeCuboidShape(11, 12, 4, 12, 14, 5),
            Block.makeCuboidShape(4, 12, 4, 5, 14, 5), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(2, 14, 2, 14, 15, 4),
            Block.makeCuboidShape(12, 14, 4, 14, 15, 12), Block.makeCuboidShape(2, 14, 4, 4, 15, 12),
            Block.makeCuboidShape(2, 14, 12, 14, 15, 14), Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(2, 0, 1, 14, 15, 2), Block.makeCuboidShape(2, 0, 14, 14, 15, 15),
            Block.makeCuboidShape(14, 0, 1, 15, 15, 15), Block.makeCuboidShape(1, 0, 1, 2, 15, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(0, 3, 3, 1, 13, 13), Block.makeCuboidShape(15, 3, 3, 16, 13, 13),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12), Block.makeCuboidShape(4, 14, 11, 5, 15, 12),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(12, 12, 5, 13, 14, 11),
            Block.makeCuboidShape(3, 12, 5, 4, 14, 11), Block.makeCuboidShape(5, 12, 12, 11, 14, 13),
            Block.makeCuboidShape(5, 12, 3, 11, 14, 4), Block.makeCuboidShape(4, 12, 4, 5, 14, 5),
            Block.makeCuboidShape(4, 12, 11, 5, 14, 12), Block.makeCuboidShape(11, 12, 11, 12, 14, 12),
            Block.makeCuboidShape(11, 12, 4, 12, 14, 5), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 4, 5, 15, 5), Block.makeCuboidShape(12, 14, 2, 14, 15, 14),
            Block.makeCuboidShape(4, 14, 12, 12, 15, 14), Block.makeCuboidShape(4, 14, 2, 12, 15, 4),
            Block.makeCuboidShape(2, 14, 2, 4, 15, 14), Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(14, 0, 2, 15, 15, 14), Block.makeCuboidShape(1, 0, 2, 2, 15, 14),
            Block.makeCuboidShape(1, 0, 14, 15, 15, 15), Block.makeCuboidShape(1, 0, 1, 15, 15, 2)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(3, 3, 0, 13, 13, 1), Block.makeCuboidShape(3, 3, 15, 13, 13, 16),
            Block.makeCuboidShape(4, 11, 4, 12, 12, 12), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(5, 12, 12, 11, 14, 13),
            Block.makeCuboidShape(5, 12, 3, 11, 14, 4), Block.makeCuboidShape(3, 12, 5, 4, 14, 11),
            Block.makeCuboidShape(12, 12, 5, 13, 14, 11), Block.makeCuboidShape(11, 12, 4, 12, 14, 5),
            Block.makeCuboidShape(4, 12, 4, 5, 14, 5), Block.makeCuboidShape(4, 12, 11, 5, 14, 12),
            Block.makeCuboidShape(11, 12, 11, 12, 14, 12), Block.makeCuboidShape(11, 14, 11, 12, 15, 12),
            Block.makeCuboidShape(11, 14, 4, 12, 15, 5), Block.makeCuboidShape(2, 14, 12, 14, 15, 14),
            Block.makeCuboidShape(2, 14, 4, 4, 15, 12), Block.makeCuboidShape(12, 14, 4, 14, 15, 12),
            Block.makeCuboidShape(2, 14, 2, 14, 15, 4), Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(2, 0, 14, 14, 15, 15), Block.makeCuboidShape(2, 0, 1, 14, 15, 2),
            Block.makeCuboidShape(1, 0, 1, 2, 15, 15), Block.makeCuboidShape(14, 0, 1, 15, 15, 15)
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
