package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

public class Shapes {
    static final VoxelShape CANOLA_PRESS_SHAPE = Stream.of(
        Block.makeCuboidShape(13, 0, 1, 15, 15.5, 3), Block.makeCuboidShape(2, 0, 2, 14, 6, 14),
        Block.makeCuboidShape(2, 10, 2, 14, 15, 14), Block.makeCuboidShape(3, 6, 3, 13, 10, 13),
        Block.makeCuboidShape(1, 0, 1, 3, 15.5, 3), Block.makeCuboidShape(1, 0, 13, 3, 15.5, 15),
        Block.makeCuboidShape(13, 0, 13, 15, 15.5, 15), Block.makeCuboidShape(0.9, 0, 0.9, 3.1, 0.5, 3.1),
        Block.makeCuboidShape(0.9, 0, 12.9, 3.1, 0.5, 15.1), Block.makeCuboidShape(0.9, 5, 12.9, 3.1, 6.5, 15.1),
        Block.makeCuboidShape(0.9, 5, 0.9, 3.1, 6.5, 3.1), Block.makeCuboidShape(12.9, 5, 12.9, 15.1, 6.5, 15.1),
        Block.makeCuboidShape(12.9, 5, 0.9, 15.1, 6.5, 3.1), Block.makeCuboidShape(0.9, 9.5, 12.9, 3.1, 11, 15.1),
        Block.makeCuboidShape(0.9, 9.5, 0.9, 3.1, 11, 3.1), Block.makeCuboidShape(12.9, 9.5, 12.9, 15.1, 11, 15.1),
        Block.makeCuboidShape(12.9, 9.5, 0.9, 15.1, 11, 3.1), Block.makeCuboidShape(12.9, 0, 0.9, 15.1, 0.5, 3.1),
        Block.makeCuboidShape(12.9, 0, 12.9, 15.1, 0.5, 15.1), Block.makeCuboidShape(12.9, 15.5, 0.9, 15.1, 16, 3.1),
        Block.makeCuboidShape(12.9, 15.5, 12.9, 15.1, 16, 15.1), Block.makeCuboidShape(0.9, 15.5, 0.9, 3.1, 16, 3.1),
        Block.makeCuboidShape(0.9, 15.5, 12.9, 3.1, 16, 15.1)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    static final class CoalGeneratorShapes {
        static final VoxelShape NORTH = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

        static final VoxelShape EAST = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

        static final VoxelShape SOUTH = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

        static final VoxelShape WEST = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }

    static class CoffeeMachineShapes {
        static final VoxelShape NORTH = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

        static final VoxelShape EAST = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

        static final VoxelShape SOUTH = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

        static final VoxelShape WEST = Stream.of(
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
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }
}
