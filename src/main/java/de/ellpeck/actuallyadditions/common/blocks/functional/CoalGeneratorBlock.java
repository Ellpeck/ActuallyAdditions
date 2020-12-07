package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.blocks.HorizontallyDirectionalBlock;
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

public class CoalGeneratorBlock extends HorizontallyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CoalGeneratorBlock() {
        super(Properties.create(Material.ROCK)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(0)
                .hardnessAndResistance(10f, 80f)
                .sound(SoundType.STONE));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(5, 14, 6, 11, 15, 7),
            Block.makeCuboidShape(5, 14, 8, 11, 15, 9), Block.makeCuboidShape(5, 14, 10, 11, 15, 14),
            Block.makeCuboidShape(5, 14, 2, 11, 15, 5), Block.makeCuboidShape(11, 14, 2, 14, 15, 14),
            Block.makeCuboidShape(2, 14, 2, 5, 15, 14), Block.makeCuboidShape(1, 0, 2, 2, 15, 14),
            Block.makeCuboidShape(14, 0, 2, 15, 15, 14), Block.makeCuboidShape(1, 0, 14, 15, 15, 15),
            Block.makeCuboidShape(3, 11, 0, 13, 12, 1), Block.makeCuboidShape(5, 3, 1, 6, 8, 2),
            Block.makeCuboidShape(10, 3, 1, 11, 8, 2), Block.makeCuboidShape(3, 8, 1, 13, 15, 2),
            Block.makeCuboidShape(3, 0, 1, 13, 3, 2), Block.makeCuboidShape(1, 0, 1, 3, 15, 2),
            Block.makeCuboidShape(13, 0, 1, 15, 15, 2), Block.makeCuboidShape(5, 13, 5, 11, 14, 10),
            Block.makeCuboidShape(2, 3, 2, 14, 8, 3), Block.makeCuboidShape(0, 0, 15, 1, 1, 16),
            Block.makeCuboidShape(15, 0, 15, 16, 1, 16), Block.makeCuboidShape(15, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 1)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1), Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15), Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(9, 14, 5, 10, 15, 11),
            Block.makeCuboidShape(7, 14, 5, 8, 15, 11), Block.makeCuboidShape(2, 14, 5, 6, 15, 11),
            Block.makeCuboidShape(11, 14, 5, 14, 15, 11), Block.makeCuboidShape(2, 14, 11, 14, 15, 14),
            Block.makeCuboidShape(2, 14, 2, 14, 15, 5), Block.makeCuboidShape(2, 0, 1, 14, 15, 2),
            Block.makeCuboidShape(2, 0, 14, 14, 15, 15), Block.makeCuboidShape(1, 0, 1, 2, 15, 15),
            Block.makeCuboidShape(15, 11, 3, 16, 12, 13), Block.makeCuboidShape(14, 3, 5, 15, 8, 6),
            Block.makeCuboidShape(14, 3, 10, 15, 8, 11), Block.makeCuboidShape(14, 8, 3, 15, 15, 13),
            Block.makeCuboidShape(14, 0, 3, 15, 3, 13), Block.makeCuboidShape(14, 0, 1, 15, 15, 3),
            Block.makeCuboidShape(14, 0, 13, 15, 15, 15), Block.makeCuboidShape(6, 13, 5, 11, 14, 11),
            Block.makeCuboidShape(13, 3, 2, 14, 8, 14), Block.makeCuboidShape(0, 0, 0, 1, 1, 1),
            Block.makeCuboidShape(0, 0, 15, 1, 1, 16), Block.makeCuboidShape(15, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 1)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16), Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(5, 14, 9, 11, 15, 10),
            Block.makeCuboidShape(5, 14, 7, 11, 15, 8), Block.makeCuboidShape(5, 14, 2, 11, 15, 6),
            Block.makeCuboidShape(5, 14, 11, 11, 15, 14), Block.makeCuboidShape(2, 14, 2, 5, 15, 14),
            Block.makeCuboidShape(11, 14, 2, 14, 15, 14), Block.makeCuboidShape(14, 0, 2, 15, 15, 14),
            Block.makeCuboidShape(1, 0, 2, 2, 15, 14), Block.makeCuboidShape(1, 0, 1, 15, 15, 2),
            Block.makeCuboidShape(3, 11, 15, 13, 12, 16), Block.makeCuboidShape(10, 3, 14, 11, 8, 15),
            Block.makeCuboidShape(5, 3, 14, 6, 8, 15), Block.makeCuboidShape(3, 8, 14, 13, 15, 15),
            Block.makeCuboidShape(3, 0, 14, 13, 3, 15), Block.makeCuboidShape(13, 0, 14, 15, 15, 15),
            Block.makeCuboidShape(1, 0, 14, 3, 15, 15), Block.makeCuboidShape(5, 13, 6, 11, 14, 11),
            Block.makeCuboidShape(2, 3, 13, 14, 8, 14), Block.makeCuboidShape(15, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 1), Block.makeCuboidShape(0, 0, 15, 1, 1, 16),
            Block.makeCuboidShape(15, 0, 15, 16, 1, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16), Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15), Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16), Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1), Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14), Block.makeCuboidShape(6, 14, 5, 7, 15, 11),
            Block.makeCuboidShape(8, 14, 5, 9, 15, 11), Block.makeCuboidShape(10, 14, 5, 14, 15, 11),
            Block.makeCuboidShape(2, 14, 5, 5, 15, 11), Block.makeCuboidShape(2, 14, 2, 14, 15, 5),
            Block.makeCuboidShape(2, 14, 11, 14, 15, 14), Block.makeCuboidShape(2, 0, 14, 14, 15, 15),
            Block.makeCuboidShape(2, 0, 1, 14, 15, 2), Block.makeCuboidShape(14, 0, 1, 15, 15, 15),
            Block.makeCuboidShape(0, 11, 3, 1, 12, 13), Block.makeCuboidShape(1, 3, 10, 2, 8, 11),
            Block.makeCuboidShape(1, 3, 5, 2, 8, 6), Block.makeCuboidShape(1, 8, 3, 2, 15, 13),
            Block.makeCuboidShape(1, 0, 3, 2, 3, 13), Block.makeCuboidShape(1, 0, 13, 2, 15, 15),
            Block.makeCuboidShape(1, 0, 1, 2, 15, 3), Block.makeCuboidShape(5, 13, 5, 10, 14, 11),
            Block.makeCuboidShape(2, 3, 2, 3, 8, 14), Block.makeCuboidShape(15, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 1), Block.makeCuboidShape(0, 0, 0, 1, 1, 1),
            Block.makeCuboidShape(0, 0, 15, 1, 1, 16)
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
