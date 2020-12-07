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

public class BatteryBoxBlock extends ActuallyBlock {
    public BatteryBoxBlock() {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape BATTERYBOX_SHAPE = Stream.of(
            Block.makeCuboidShape(2, 1, 1, 14, 2, 2), Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(2, 2, 2, 14, 5, 14), Block.makeCuboidShape(5, 5, 5, 6, 7, 6),
            Block.makeCuboidShape(10, 5, 10, 11, 7, 11), Block.makeCuboidShape(5, 5, 10, 6, 7, 11),
            Block.makeCuboidShape(1, 1, 14, 2, 6, 15), Block.makeCuboidShape(14, 1, 14, 15, 6, 15),
            Block.makeCuboidShape(1, 1, 1, 2, 6, 2), Block.makeCuboidShape(14, 1, 1, 15, 6, 2),
            Block.makeCuboidShape(1, 5, 2, 2, 6, 14), Block.makeCuboidShape(14, 1, 2, 15, 2, 14),
            Block.makeCuboidShape(14, 5, 2, 15, 6, 14), Block.makeCuboidShape(1, 1, 2, 2, 2, 14),
            Block.makeCuboidShape(2, 5, 14, 14, 6, 15), Block.makeCuboidShape(2, 5, 1, 14, 6, 2),
            Block.makeCuboidShape(2, 1, 14, 14, 2, 15), Block.makeCuboidShape(10, 5, 5, 11, 7, 6),
            Block.makeCuboidShape(5.5, 6, 6, 6, 7, 10), Block.makeCuboidShape(10, 6, 6, 10.5, 7, 10),
            Block.makeCuboidShape(6, 6, 5.5, 10, 7, 6), Block.makeCuboidShape(6, 6, 10, 10, 7, 10.5),
            Block.makeCuboidShape(6, 5, 6, 10, 7, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BATTERYBOX_SHAPE;
    }
}
