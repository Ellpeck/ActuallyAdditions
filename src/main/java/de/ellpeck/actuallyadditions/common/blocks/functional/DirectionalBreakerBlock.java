package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.blocks.FullyDirectionalBlock;
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

public class DirectionalBreakerBlock extends FullyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public DirectionalBreakerBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape SHAPE_U = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(15, 0, 1, 16, 1, 15),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(15, 3, 9, 16, 13, 13),
            Block.makeCuboidShape(15, 3, 3, 16, 13, 7), Block.makeCuboidShape(0, 3, 9, 1, 13, 13),
            Block.makeCuboidShape(0, 3, 3, 1, 13, 7), Block.makeCuboidShape(5, 15, 5, 11, 16, 11),
            Block.makeCuboidShape(3, 15, 6, 5, 16, 10), Block.makeCuboidShape(11, 15, 6, 13, 16, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_D = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(15, 3, 3, 16, 13, 7),
            Block.makeCuboidShape(15, 3, 9, 16, 13, 13), Block.makeCuboidShape(0, 3, 3, 1, 13, 7),
            Block.makeCuboidShape(0, 3, 9, 1, 13, 13), Block.makeCuboidShape(5, 0, 5, 11, 1, 11),
            Block.makeCuboidShape(3, 0, 6, 5, 1, 10), Block.makeCuboidShape(11, 0, 6, 13, 1, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(15, 9, 3, 16, 13, 13),
            Block.makeCuboidShape(15, 3, 3, 16, 7, 13), Block.makeCuboidShape(0, 9, 3, 1, 13, 13),
            Block.makeCuboidShape(0, 3, 3, 1, 7, 13), Block.makeCuboidShape(5, 5, 0, 11, 11, 1),
            Block.makeCuboidShape(3, 6, 0, 5, 10, 1), Block.makeCuboidShape(11, 6, 0, 13, 10, 1)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(3, 9, 15, 13, 13, 16),
            Block.makeCuboidShape(3, 3, 15, 13, 7, 16), Block.makeCuboidShape(3, 9, 0, 13, 13, 1),
            Block.makeCuboidShape(3, 3, 0, 13, 7, 1), Block.makeCuboidShape(15, 5, 5, 16, 11, 11),
            Block.makeCuboidShape(15, 6, 3, 16, 10, 5), Block.makeCuboidShape(15, 6, 11, 16, 10, 13)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(0, 9, 3, 1, 13, 13),
            Block.makeCuboidShape(0, 3, 3, 1, 7, 13), Block.makeCuboidShape(15, 9, 3, 16, 13, 13),
            Block.makeCuboidShape(15, 3, 3, 16, 7, 13), Block.makeCuboidShape(5, 5, 15, 11, 11, 16),
            Block.makeCuboidShape(11, 6, 15, 13, 10, 16), Block.makeCuboidShape(3, 6, 15, 5, 10, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(3, 9, 0, 13, 13, 1),
            Block.makeCuboidShape(3, 3, 0, 13, 7, 1), Block.makeCuboidShape(3, 9, 15, 13, 13, 16),
            Block.makeCuboidShape(3, 3, 15, 13, 7, 16), Block.makeCuboidShape(0, 5, 5, 1, 11, 11),
            Block.makeCuboidShape(0, 6, 11, 1, 10, 13), Block.makeCuboidShape(0, 6, 3, 1, 10, 5)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case UP:
                return SHAPE_U;
            case DOWN:
                return SHAPE_D;
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
