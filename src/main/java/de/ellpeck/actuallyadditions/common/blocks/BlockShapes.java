package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

/**
 * This class just contains messy code for the block shapes.
 */
public final class BlockShapes {
    public static final VoxelShape BATTERYBOX_SHAPE = Stream.of(
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
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape FEEDER_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16), Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16), Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16), Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1), Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(11, 14, 11, 12, 15, 12), Block.makeCuboidShape(11, 14, 4, 12, 15, 5),
            Block.makeCuboidShape(4, 14, 11, 5, 15, 12), Block.makeCuboidShape(4, 14, 4, 5, 15, 5),
            Block.makeCuboidShape(4, 13, 4, 12, 14, 12), Block.makeCuboidShape(4, 14, 12, 12, 15, 15),
            Block.makeCuboidShape(4, 14, 1, 12, 15, 4), Block.makeCuboidShape(1, 14, 1, 4, 15, 15),
            Block.makeCuboidShape(12, 14, 1, 15, 15, 15), Block.makeCuboidShape(1, 1, 1, 15, 2, 15),
            Block.makeCuboidShape(14, 2, 1, 15, 14, 15), Block.makeCuboidShape(1, 2, 1, 2, 14, 15),
            Block.makeCuboidShape(2, 2, 14, 14, 14, 15), Block.makeCuboidShape(2, 2, 1, 14, 14, 2)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
}
