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

public class LampPowererBlock extends HorizontallyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public LampPowererBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16), Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(3, 3, 15, 13, 13, 16), Block.makeCuboidShape(1, 14, 1, 15, 15, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 2, 15), Block.makeCuboidShape(14, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(1, 2, 1, 2, 14, 15), Block.makeCuboidShape(2, 2, 14, 14, 14, 15),
            Block.makeCuboidShape(2, 2, 1, 14, 14, 2), Block.makeCuboidShape(0, 7, 7, 1, 9, 10),
            Block.makeCuboidShape(15, 7, 6, 16, 9, 9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 3, 3, 1, 13, 13), Block.makeCuboidShape(1, 14, 1, 15, 15, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 2, 15), Block.makeCuboidShape(1, 2, 14, 15, 14, 15),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 2), Block.makeCuboidShape(1, 2, 2, 2, 14, 14),
            Block.makeCuboidShape(14, 2, 2, 15, 14, 14), Block.makeCuboidShape(6, 7, 0, 9, 9, 1),
            Block.makeCuboidShape(7, 7, 15, 10, 9, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(3, 3, 0, 13, 13, 1), Block.makeCuboidShape(1, 14, 1, 15, 15, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 2, 15), Block.makeCuboidShape(1, 2, 1, 2, 14, 15),
            Block.makeCuboidShape(14, 2, 1, 15, 14, 15), Block.makeCuboidShape(2, 2, 1, 14, 14, 2),
            Block.makeCuboidShape(2, 2, 14, 14, 14, 15), Block.makeCuboidShape(15, 7, 6, 16, 9, 9),
            Block.makeCuboidShape(0, 7, 7, 1, 9, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(15, 0, 1, 16, 1, 15),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 3, 3, 16, 13, 13), Block.makeCuboidShape(1, 14, 1, 15, 15, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 2, 15), Block.makeCuboidShape(1, 2, 1, 15, 14, 2),
            Block.makeCuboidShape(1, 2, 14, 15, 14, 15), Block.makeCuboidShape(14, 2, 2, 15, 14, 14),
            Block.makeCuboidShape(1, 2, 2, 2, 14, 14), Block.makeCuboidShape(7, 7, 15, 10, 9, 16),
            Block.makeCuboidShape(6, 7, 0, 9, 9, 1)
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
