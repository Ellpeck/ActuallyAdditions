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

public class CoffeeMachineBlock extends HorizontallyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CoffeeMachineBlock() {
        super(Properties.create(Material.ROCK)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(0)
                .hardnessAndResistance(10f, 80f)
                .sound(SoundType.STONE));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(8, 11, 7, 13, 14, 8), Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(7, 1, 8, 14, 9, 14), Block.makeCuboidShape(6, 9, 3, 15, 11, 15),
            Block.makeCuboidShape(8, 11, 8, 13, 13, 13), Block.makeCuboidShape(10, 8, 3.5, 11, 9, 4.5),
            Block.makeCuboidShape(9, 1, 2, 12, 2, 5), Block.makeCuboidShape(9, 2, 5, 12, 7, 6),
            Block.makeCuboidShape(9, 2, 1, 12, 7, 2), Block.makeCuboidShape(12, 2, 2, 13, 7, 5),
            Block.makeCuboidShape(8, 2, 2, 9, 7, 5), Block.makeCuboidShape(13, 2, 3, 14, 3, 4),
            Block.makeCuboidShape(13, 5, 3, 14, 6, 4), Block.makeCuboidShape(14, 3, 3, 15, 5, 4),
            Block.makeCuboidShape(13, 10.2, 4.2, 14, 11.2, 5.2), Block.makeCuboidShape(11, 10.2, 4.2, 12, 11.2, 5.2),
            Block.makeCuboidShape(13, 11, 7, 14, 14, 14), Block.makeCuboidShape(7, 11, 7, 8, 14, 14),
            Block.makeCuboidShape(8, 11, 13, 13, 14, 14), Block.makeCuboidShape(14, 1, 14, 15, 9, 15),
            Block.makeCuboidShape(6, 1, 14, 7, 9, 15), Block.makeCuboidShape(14, 1, 7, 15, 9, 8),
            Block.makeCuboidShape(6, 1, 7, 7, 9, 8), Block.makeCuboidShape(6.8, 1.9, 11.9, 7, 3.1, 13.1),
            Block.makeCuboidShape(6.8, 1.9, 9.9, 7, 3.1, 11.1), Block.makeCuboidShape(3, 3, 10, 4, 5, 11),
            Block.makeCuboidShape(2.9, 4.8, 9.9, 4.1, 5, 11.1), Block.makeCuboidShape(2.9, 4.8, 11.9, 4.1, 5, 13.1),
            Block.makeCuboidShape(3, 2, 12, 7, 3, 13), Block.makeCuboidShape(3, 2, 10, 7, 3, 11),
            Block.makeCuboidShape(3, 3, 12, 4, 5, 13), Block.makeCuboidShape(2, 5, 9, 5, 11, 14),
            Block.makeCuboidShape(2, 11, 11, 4, 12, 13), Block.makeCuboidShape(1, 1, 11, 2, 12, 13)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(8, 11, 8, 9, 14, 13), Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(2, 1, 7, 8, 9, 14), Block.makeCuboidShape(1, 9, 6, 13, 11, 15),
            Block.makeCuboidShape(3, 11, 8, 8, 13, 13), Block.makeCuboidShape(11.5, 8, 10, 12.5, 9, 11),
            Block.makeCuboidShape(11, 1, 9, 14, 2, 12), Block.makeCuboidShape(10, 2, 9, 11, 7, 12),
            Block.makeCuboidShape(14, 2, 9, 15, 7, 12), Block.makeCuboidShape(11, 2, 12, 14, 7, 13),
            Block.makeCuboidShape(11, 2, 8, 14, 7, 9), Block.makeCuboidShape(12, 2, 13, 13, 3, 14),
            Block.makeCuboidShape(12, 5, 13, 13, 6, 14), Block.makeCuboidShape(12, 3, 14, 13, 5, 15),
            Block.makeCuboidShape(10.8, 10.2, 13, 11.8, 11.2, 14), Block.makeCuboidShape(10.8, 10.2, 11, 11.8, 11.2, 12),
            Block.makeCuboidShape(2, 11, 13, 9, 14, 14), Block.makeCuboidShape(2, 11, 7, 9, 14, 8),
            Block.makeCuboidShape(2, 11, 8, 3, 14, 13), Block.makeCuboidShape(1, 1, 14, 2, 9, 15),
            Block.makeCuboidShape(1, 1, 6, 2, 9, 7), Block.makeCuboidShape(8, 1, 14, 9, 9, 15),
            Block.makeCuboidShape(8, 1, 6, 9, 9, 7), Block.makeCuboidShape(2.9000000000000004, 1.9, 6.8, 4.1, 3.1, 7),
            Block.makeCuboidShape(4.9, 1.9, 6.8, 6.1, 3.1, 7), Block.makeCuboidShape(5, 3, 3, 6, 5, 4),
            Block.makeCuboidShape(4.9, 4.8, 2.9000000000000004, 6.1, 5, 4.1), Block.makeCuboidShape(2.9000000000000004, 4.8, 2.9000000000000004, 4.1, 5, 4.1),
            Block.makeCuboidShape(3, 2, 3, 4, 3, 7), Block.makeCuboidShape(5, 2, 3, 6, 3, 7),
            Block.makeCuboidShape(3, 3, 3, 4, 5, 4), Block.makeCuboidShape(2, 5, 2, 7, 11, 5),
            Block.makeCuboidShape(3, 11, 2, 5, 12, 4), Block.makeCuboidShape(3, 1, 1, 5, 12, 2)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(3, 11, 8, 8, 14, 9), Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(2, 1, 2, 9, 9, 8), Block.makeCuboidShape(1, 9, 1, 10, 11, 13),
            Block.makeCuboidShape(3, 11, 3, 8, 13, 8), Block.makeCuboidShape(5, 8, 11.5, 6, 9, 12.5),
            Block.makeCuboidShape(4, 1, 11, 7, 2, 14), Block.makeCuboidShape(4, 2, 10, 7, 7, 11),
            Block.makeCuboidShape(4, 2, 14, 7, 7, 15), Block.makeCuboidShape(3, 2, 11, 4, 7, 14),
            Block.makeCuboidShape(7, 2, 11, 8, 7, 14), Block.makeCuboidShape(2, 2, 12, 3, 3, 13),
            Block.makeCuboidShape(2, 5, 12, 3, 6, 13), Block.makeCuboidShape(1, 3, 12, 2, 5, 13),
            Block.makeCuboidShape(2, 10.2, 10.8, 3, 11.2, 11.8), Block.makeCuboidShape(4, 10.2, 10.8, 5, 11.2, 11.8),
            Block.makeCuboidShape(2, 11, 2, 3, 14, 9), Block.makeCuboidShape(8, 11, 2, 9, 14, 9),
            Block.makeCuboidShape(3, 11, 2, 8, 14, 3), Block.makeCuboidShape(1, 1, 1, 2, 9, 2),
            Block.makeCuboidShape(9, 1, 1, 10, 9, 2), Block.makeCuboidShape(1, 1, 8, 2, 9, 9),
            Block.makeCuboidShape(9, 1, 8, 10, 9, 9), Block.makeCuboidShape(9, 1.9, 2.9000000000000004, 9.2, 3.1, 4.1),
            Block.makeCuboidShape(9, 1.9, 4.9, 9.2, 3.1, 6.1), Block.makeCuboidShape(12, 3, 5, 13, 5, 6),
            Block.makeCuboidShape(11.9, 4.8, 4.9, 13.1, 5, 6.1), Block.makeCuboidShape(11.9, 4.8, 2.9000000000000004, 13.1, 5, 4.1),
            Block.makeCuboidShape(9, 2, 3, 13, 3, 4), Block.makeCuboidShape(9, 2, 5, 13, 3, 6),
            Block.makeCuboidShape(12, 3, 3, 13, 5, 4), Block.makeCuboidShape(11, 5, 2, 14, 11, 7),
            Block.makeCuboidShape(12, 11, 3, 14, 12, 5), Block.makeCuboidShape(14, 1, 3, 15, 12, 5)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(7, 11, 3, 8, 14, 8), Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(8, 1, 2, 14, 9, 9), Block.makeCuboidShape(3, 9, 1, 15, 11, 10),
            Block.makeCuboidShape(8, 11, 3, 13, 13, 8), Block.makeCuboidShape(3.5, 8, 5, 4.5, 9, 6),
            Block.makeCuboidShape(2, 1, 4, 5, 2, 7), Block.makeCuboidShape(5, 2, 4, 6, 7, 7),
            Block.makeCuboidShape(1, 2, 4, 2, 7, 7), Block.makeCuboidShape(2, 2, 3, 5, 7, 4),
            Block.makeCuboidShape(2, 2, 7, 5, 7, 8), Block.makeCuboidShape(3, 2, 2, 4, 3, 3),
            Block.makeCuboidShape(3, 5, 2, 4, 6, 3), Block.makeCuboidShape(3, 3, 1, 4, 5, 2),
            Block.makeCuboidShape(4.199999999999999, 10.2, 2, 5.199999999999999, 11.2, 3), Block.makeCuboidShape(4.199999999999999, 10.2, 4, 5.199999999999999, 11.2, 5),
            Block.makeCuboidShape(7, 11, 2, 14, 14, 3), Block.makeCuboidShape(7, 11, 8, 14, 14, 9),
            Block.makeCuboidShape(13, 11, 3, 14, 14, 8), Block.makeCuboidShape(14, 1, 1, 15, 9, 2),
            Block.makeCuboidShape(14, 1, 9, 15, 9, 10), Block.makeCuboidShape(7, 1, 1, 8, 9, 2),
            Block.makeCuboidShape(7, 1, 9, 8, 9, 10), Block.makeCuboidShape(11.9, 1.9, 9, 13.1, 3.1, 9.2),
            Block.makeCuboidShape(9.9, 1.9, 9, 11.1, 3.1, 9.2), Block.makeCuboidShape(10, 3, 12, 11, 5, 13),
            Block.makeCuboidShape(9.9, 4.8, 11.9, 11.1, 5, 13.1), Block.makeCuboidShape(11.9, 4.8, 11.9, 13.1, 5, 13.1),
            Block.makeCuboidShape(12, 2, 9, 13, 3, 13), Block.makeCuboidShape(10, 2, 9, 11, 3, 13),
            Block.makeCuboidShape(12, 3, 12, 13, 5, 13), Block.makeCuboidShape(9, 5, 11, 14, 11, 14),
            Block.makeCuboidShape(11, 11, 12, 13, 12, 14), Block.makeCuboidShape(11, 1, 14, 13, 12, 15)
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
