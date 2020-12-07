package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.FullyDirectionalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.stream.Stream;

public class AtomicReconstructorBlock extends FullyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public AtomicReconstructorBlock() {
        super(Properties.create(Material.ROCK)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(0)
                .hardnessAndResistance(10f, 80f)
                .sound(SoundType.STONE));
    }

    private static final VoxelShape SHAPE_U = Stream.of(
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(0, 0, 15, 1, 16, 16),
            Block.makeCuboidShape(15, 0, 15, 16, 16, 16), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(15, 0, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 0, 0, 1, 16, 1), Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(1, 1, 14, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 15, 2),
            Block.makeCuboidShape(10, 14, 10, 11, 15, 11), Block.makeCuboidShape(10, 14, 5, 11, 15, 6),
            Block.makeCuboidShape(5, 14, 10, 6, 15, 11), Block.makeCuboidShape(5, 14, 5, 6, 15, 6),
            Block.makeCuboidShape(5, 14, 11, 11, 15, 14), Block.makeCuboidShape(5, 14, 2, 11, 15, 5),
            Block.makeCuboidShape(2, 14, 2, 5, 15, 14), Block.makeCuboidShape(11, 14, 2, 14, 15, 14),
            Block.makeCuboidShape(14, 1, 2, 15, 15, 14), Block.makeCuboidShape(1, 1, 2, 2, 15, 14),
            Block.makeCuboidShape(2, 1, 2, 14, 2, 14), Block.makeCuboidShape(2, 13, 2, 14, 14, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_D = Stream.of(
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 0, 0, 1, 16, 1),
            Block.makeCuboidShape(15, 0, 0, 16, 16, 1), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(15, 0, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 15, 1, 16, 16), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 15, 2), Block.makeCuboidShape(1, 1, 14, 15, 15, 15),
            Block.makeCuboidShape(10, 1, 5, 11, 2, 6), Block.makeCuboidShape(10, 1, 10, 11, 2, 11),
            Block.makeCuboidShape(5, 1, 5, 6, 2, 6), Block.makeCuboidShape(5, 1, 10, 6, 2, 11),
            Block.makeCuboidShape(5, 1, 2, 11, 2, 5), Block.makeCuboidShape(5, 1, 11, 11, 2, 14),
            Block.makeCuboidShape(2, 1, 2, 5, 2, 14), Block.makeCuboidShape(11, 1, 2, 14, 2, 14),
            Block.makeCuboidShape(14, 1, 2, 15, 15, 14), Block.makeCuboidShape(1, 1, 2, 2, 15, 14),
            Block.makeCuboidShape(2, 14, 2, 14, 15, 14), Block.makeCuboidShape(2, 2, 2, 14, 3, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16), Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1), Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(10, 10, 1, 11, 11, 2), Block.makeCuboidShape(10, 5, 1, 11, 6, 2),
            Block.makeCuboidShape(5, 10, 1, 6, 11, 2), Block.makeCuboidShape(5, 5, 1, 6, 6, 2),
            Block.makeCuboidShape(5, 11, 1, 11, 14, 2), Block.makeCuboidShape(5, 2, 1, 11, 5, 2),
            Block.makeCuboidShape(2, 2, 1, 5, 14, 2), Block.makeCuboidShape(11, 2, 1, 14, 14, 2),
            Block.makeCuboidShape(14, 2, 1, 15, 14, 15), Block.makeCuboidShape(1, 2, 1, 2, 14, 15),
            Block.makeCuboidShape(2, 2, 14, 14, 14, 15), Block.makeCuboidShape(2, 2, 2, 14, 14, 3)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15), Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(14, 10, 10, 15, 11, 11), Block.makeCuboidShape(14, 5, 10, 15, 6, 11),
            Block.makeCuboidShape(14, 10, 5, 15, 11, 6), Block.makeCuboidShape(14, 5, 5, 15, 6, 6),
            Block.makeCuboidShape(14, 11, 5, 15, 14, 11), Block.makeCuboidShape(14, 2, 5, 15, 5, 11),
            Block.makeCuboidShape(14, 2, 2, 15, 14, 5), Block.makeCuboidShape(14, 2, 11, 15, 14, 14),
            Block.makeCuboidShape(1, 2, 14, 15, 14, 15), Block.makeCuboidShape(1, 2, 1, 15, 14, 2),
            Block.makeCuboidShape(1, 2, 2, 2, 14, 14), Block.makeCuboidShape(13, 2, 2, 14, 14, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(5, 10, 14, 6, 11, 15), Block.makeCuboidShape(5, 5, 14, 6, 6, 15),
            Block.makeCuboidShape(10, 10, 14, 11, 11, 15), Block.makeCuboidShape(10, 5, 14, 11, 6, 15),
            Block.makeCuboidShape(5, 11, 14, 11, 14, 15), Block.makeCuboidShape(5, 2, 14, 11, 5, 15),
            Block.makeCuboidShape(11, 2, 14, 14, 14, 15), Block.makeCuboidShape(2, 2, 14, 5, 14, 15),
            Block.makeCuboidShape(1, 2, 1, 2, 14, 15), Block.makeCuboidShape(14, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(2, 2, 1, 14, 14, 2), Block.makeCuboidShape(2, 2, 13, 14, 14, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(15, 0, 1, 16, 1, 15),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15), Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(1, 10, 5, 2, 11, 6), Block.makeCuboidShape(1, 5, 5, 2, 6, 6),
            Block.makeCuboidShape(1, 10, 10, 2, 11, 11), Block.makeCuboidShape(1, 5, 10, 2, 6, 11),
            Block.makeCuboidShape(1, 11, 5, 2, 14, 11), Block.makeCuboidShape(1, 2, 5, 2, 5, 11),
            Block.makeCuboidShape(1, 2, 11, 2, 14, 14), Block.makeCuboidShape(1, 2, 2, 2, 14, 5),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 2), Block.makeCuboidShape(1, 2, 14, 15, 14, 15),
            Block.makeCuboidShape(14, 2, 2, 15, 14, 14), Block.makeCuboidShape(2, 2, 2, 3, 14, 14)
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
