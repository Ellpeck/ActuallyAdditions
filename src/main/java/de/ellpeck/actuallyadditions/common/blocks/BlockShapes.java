package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class just contains messy code for the block shapes.
 */
public final class BlockShapes {
    public static class ShapeBuilder {
        Stream.Builder<VoxelShape> shapes = Stream.builder();

        public ShapeBuilder add(double x1, double y1, double z1, double x2, double y2, double z2) {
            shapes.add(Block.makeCuboidShape(x1, y1, z1, x2, y2, z2));
            return this;
        }

        public Stream<VoxelShape> build() {
            return shapes.build();
        }

        public Optional<VoxelShape> standardReduceBuild() {
            return shapes.build().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR));
        }

        public static ShapeBuilder get() {
            return new ShapeBuilder();
        }
    }

    public static final VoxelShape BATTERYBOX_SHAPE = ShapeBuilder.get()
            .add(2, 1, 1, 14, 2, 2).add(0, 0, 0, 16, 1, 16).add(2, 2, 2, 14, 5, 14)
            .add(5, 5, 5, 6, 7, 6).add(10, 5, 10, 11, 7, 11).add(5, 5, 10, 6, 7, 11)
            .add(1, 1, 14, 2, 6, 15).add(14, 1, 14, 15, 6, 15).add(1, 1, 1, 2, 6, 2)
            .add(14, 1, 1, 15, 6, 2).add(1, 5, 2, 2, 6, 14).add(14, 1, 2, 15, 2, 14)
            .add(14, 5, 2, 15, 6, 14).add(1, 1, 2, 2, 2, 14).add(2, 5, 14, 14, 6, 15)
            .add(2, 5, 1, 14, 6, 2).add(2, 1, 14, 14, 2, 15).add(10, 5, 5, 11, 7, 6)
            .add(5.5, 6, 6, 6, 7, 10).add(10, 6, 6, 10.5, 7, 10).add(6, 6, 5.5, 10, 7, 6)
            .add(6, 6, 10, 10, 7, 10.5).add(6, 5, 6, 10, 7, 10)
            .standardReduceBuild().get();

    public static final VoxelShape FEEDER_SHAPE = ShapeBuilder.get()
            .add(0, 15, 0, 1, 16, 16).add(15, 15, 0, 16, 16, 16)
            .add(1, 15, 0, 15, 16, 1).add(1, 15, 15, 15, 16, 16).add(1, 0, 15, 15, 1, 16)
            .add(1, 0, 0, 15, 1, 1).add(15, 0, 0, 16, 1, 16).add(0, 0, 0, 1, 1, 16)
            .add(0, 1, 15, 1, 15, 16).add(15, 1, 15, 16, 15, 16).add(15, 1, 0, 16, 15, 1)
            .add(0, 1, 0, 1, 15, 1).add(11, 14, 11, 12, 15, 12).add(11, 14, 4, 12, 15, 5)
            .add(4, 14, 11, 5, 15, 12).add(4, 14, 4, 5, 15, 5).add(4, 13, 4, 12, 14, 12)
            .add(4, 14, 12, 12, 15, 15).add(4, 14, 1, 12, 15, 4).add(1, 14, 1, 4, 15, 15)
            .add(12, 14, 1, 15, 15, 15).add(1, 1, 1, 15, 2, 15).add(14, 2, 1, 15, 14, 15)
            .add(1, 2, 1, 2, 14, 15).add(2, 2, 14, 14, 14, 15).add(2, 2, 1, 14, 14, 2)
            .standardReduceBuild().get();

    public static final VoxelShape SOLIDIFIER_SHAPE = ShapeBuilder.get()
            .add(15, 0, 1, 16, 1, 15).add(1, 1, 1, 15, 15, 15).add(0, 0, 0, 16, 1, 1)
            .add(0, 0, 15, 16, 1, 16).add(0, 15, 0, 16, 16, 1).add(0, 15, 15, 16, 16, 16)
            .add(0, 0, 1, 1, 1, 15).add(0, 15, 1, 1, 16, 15).add(15, 15, 1, 16, 16, 15)
            .add(0, 1, 0, 1, 15, 1).add(0, 1, 15, 1, 15, 16).add(15, 1, 15, 16, 15, 16)
            .add(15, 1, 0, 16, 15, 1)
            .standardReduceBuild().get();

    public static class BioReactorShape {
        public static final VoxelShape SHAPE_N = ShapeBuilder.get()
                .add(15, 1, 15, 16, 15, 16).add(0, 0, 0, 1, 1, 16).add(1, 0, 15, 15, 1, 16)
                .add(15, 0, 0, 16, 1, 16).add(1, 0, 0, 15, 1, 1).add(0, 15, 0, 1, 16, 16)
                .add(15, 15, 0, 16, 16, 16).add(1, 15, 0, 15, 16, 1).add(1, 15, 15, 15, 16, 16)
                .add(0, 1, 15, 1, 15, 16).add(15, 1, 0, 16, 15, 1).add(0, 1, 0, 1, 15, 1)
                .add(15, 11, 5, 16, 12, 11).add(0, 11, 5, 1, 12, 11).add(15, 4, 5, 16, 5, 11)
                .add(0, 4, 5, 1, 5, 11).add(15, 4, 11, 16, 12, 12).add(0, 4, 11, 1, 12, 12)
                .add(15, 4, 4, 16, 12, 5).add(0, 4, 4, 1, 12, 5).add(15, 12, 4, 16, 13, 12)
                .add(0, 12, 4, 1, 13, 12).add(15, 3, 4, 16, 4, 12).add(0, 3, 4, 1, 4, 12)
                .add(15, 3, 12, 16, 13, 13).add(0, 3, 12, 1, 13, 13).add(15, 3, 3, 16, 13, 4)
                .add(0, 3, 3, 1, 13, 4).add(4, 13, 4, 12, 14, 12).add(11, 14, 4, 12, 15, 5)
                .add(4, 14, 4, 5, 15, 5).add(4, 14, 11, 5, 15, 12).add(11, 14, 11, 12, 15, 12)
                .add(2, 14, 2, 4, 15, 14).add(4, 14, 2, 12, 15, 4).add(4, 14, 12, 12, 15, 14)
                .add(12, 14, 2, 14, 15, 14).add(2, 0, 2, 14, 1, 14).add(1, 0, 2, 2, 15, 14)
                .add(14, 0, 2, 15, 15, 14).add(1, 0, 14, 15, 15, 15).add(1, 0, 1, 15, 15, 2)
                .add(0, 8, 6, 1, 9, 7).add(15, 8, 6, 16, 9, 7).add(0, 9, 7, 1, 10, 8)
                .add(15, 9, 7, 16, 10, 8).add(0, 6, 9, 1, 7, 10).add(15, 6, 9, 16, 7, 10)
                .standardReduceBuild().get();

        public static final VoxelShape SHAPE_E = ShapeBuilder.get()
                .add(0, 1, 15, 1, 15, 16).add(0, 0, 0, 16, 1, 1).add(0, 0, 1, 1, 1, 15)
                .add(0, 0, 15, 16, 1, 16).add(15, 0, 1, 16, 1, 15).add(0, 15, 0, 16, 16, 1)
                .add(0, 15, 15, 16, 16, 16).add(15, 15, 1, 16, 16, 15).add(0, 15, 1, 1, 16, 15)
                .add(0, 1, 0, 1, 15, 1).add(15, 1, 15, 16, 15, 16).add(15, 1, 0, 16, 15, 1)
                .add(5, 11, 15, 11, 12, 16).add(5, 11, 0, 11, 12, 1).add(5, 4, 15, 11, 5, 16)
                .add(5, 4, 0, 11, 5, 1).add(4, 4, 15, 5, 12, 16).add(4, 4, 0, 5, 12, 1)
                .add(11, 4, 15, 12, 12, 16).add(11, 4, 0, 12, 12, 1).add(4, 12, 15, 12, 13, 16)
                .add(4, 12, 0, 12, 13, 1).add(4, 3, 15, 12, 4, 16).add(4, 3, 0, 12, 4, 1)
                .add(3, 3, 15, 4, 13, 16).add(3, 3, 0, 4, 13, 1).add(12, 3, 15, 13, 13, 16)
                .add(12, 3, 0, 13, 13, 1).add(4, 13, 4, 12, 14, 12).add(11, 14, 11, 12, 15, 12)
                .add(11, 14, 4, 12, 15, 5).add(4, 14, 4, 5, 15, 5).add(4, 14, 11, 5, 15, 12)
                .add(2, 14, 2, 14, 15, 4).add(12, 14, 4, 14, 15, 12).add(2, 14, 4, 4, 15, 12)
                .add(2, 14, 12, 14, 15, 14).add(2, 0, 2, 14, 1, 14).add(2, 0, 1, 14, 15, 2)
                .add(2, 0, 14, 14, 15, 15).add(1, 0, 1, 2, 15, 15).add(14, 0, 1, 15, 15, 15)
                .add(9, 8, 0, 10, 9, 1).add(9, 8, 15, 10, 9, 16).add(8, 9, 0, 9, 10, 1)
                .add(8, 9, 15, 9, 10, 16).add(6, 6, 0, 7, 7, 1).add(6, 6, 15, 7, 7, 16)
                .standardReduceBuild().get();

        public static final VoxelShape SHAPE_S = ShapeBuilder.get()
                .add(0, 1, 0, 1, 15, 1).add(15, 0, 0, 16, 1, 16).add(1, 0, 0, 15, 1, 1)
                .add(0, 0, 0, 1, 1, 16).add(1, 0, 15, 15, 1, 16).add(15, 15, 0, 16, 16, 16)
                .add(0, 15, 0, 1, 16, 16).add(1, 15, 15, 15, 16, 16).add(1, 15, 0, 15, 16, 1)
                .add(15, 1, 0, 16, 15, 1).add(0, 1, 15, 1, 15, 16).add(15, 1, 15, 16, 15, 16)
                .add(0, 11, 5, 1, 12, 11).add(15, 11, 5, 16, 12, 11).add(0, 4, 5, 1, 5, 11)
                .add(15, 4, 5, 16, 5, 11).add(0, 4, 4, 1, 12, 5).add(15, 4, 4, 16, 12, 5)
                .add(0, 4, 11, 1, 12, 12).add(15, 4, 11, 16, 12, 12).add(0, 12, 4, 1, 13, 12)
                .add(15, 12, 4, 16, 13, 12).add(0, 3, 4, 1, 4, 12).add(15, 3, 4, 16, 4, 12)
                .add(0, 3, 3, 1, 13, 4).add(15, 3, 3, 16, 13, 4).add(0, 3, 12, 1, 13, 13)
                .add(15, 3, 12, 16, 13, 13).add(4, 13, 4, 12, 14, 12).add(4, 14, 11, 5, 15, 12)
                .add(11, 14, 11, 12, 15, 12).add(11, 14, 4, 12, 15, 5).add(4, 14, 4, 5, 15, 5)
                .add(12, 14, 2, 14, 15, 14).add(4, 14, 12, 12, 15, 14).add(4, 14, 2, 12, 15, 4)
                .add(2, 14, 2, 4, 15, 14).add(2, 0, 2, 14, 1, 14).add(14, 0, 2, 15, 15, 14)
                .add(1, 0, 2, 2, 15, 14).add(1, 0, 1, 15, 15, 2).add(1, 0, 14, 15, 15, 15)
                .add(15, 8, 9, 16, 9, 10).add(0, 8, 9, 1, 9, 10).add(15, 9, 8, 16, 10, 9)
                .add(0, 9, 8, 1, 10, 9).add(15, 6, 6, 16, 7, 7).add(0, 6, 6, 1, 7, 7)
                .standardReduceBuild().get();

        public static final VoxelShape SHAPE_W = ShapeBuilder.get()
                .add(15, 1, 0, 16, 15, 1).add(0, 0, 15, 16, 1, 16).add(15, 0, 1, 16, 1, 15)
                .add(0, 0, 0, 16, 1, 1).add(0, 0, 1, 1, 1, 15).add(0, 15, 15, 16, 16, 16)
                .add(0, 15, 0, 16, 16, 1).add(0, 15, 1, 1, 16, 15).add(15, 15, 1, 16, 16, 15)
                .add(15, 1, 15, 16, 15, 16).add(0, 1, 0, 1, 15, 1).add(0, 1, 15, 1, 15, 16)
                .add(5, 11, 0, 11, 12, 1).add(5, 11, 15, 11, 12, 16).add(5, 4, 0, 11, 5, 1)
                .add(5, 4, 15, 11, 5, 16).add(11, 4, 0, 12, 12, 1).add(11, 4, 15, 12, 12, 16)
                .add(4, 4, 0, 5, 12, 1).add(4, 4, 15, 5, 12, 16).add(4, 12, 0, 12, 13, 1)
                .add(4, 12, 15, 12, 13, 16).add(4, 3, 0, 12, 4, 1).add(4, 3, 15, 12, 4, 16)
                .add(12, 3, 0, 13, 13, 1).add(12, 3, 15, 13, 13, 16).add(3, 3, 0, 4, 13, 1)
                .add(3, 3, 15, 4, 13, 16).add(4, 13, 4, 12, 14, 12).add(4, 14, 4, 5, 15, 5)
                .add(4, 14, 11, 5, 15, 12).add(11, 14, 11, 12, 15, 12).add(11, 14, 4, 12, 15, 5)
                .add(2, 14, 12, 14, 15, 14).add(2, 14, 4, 4, 15, 12).add(12, 14, 4, 14, 15, 12)
                .add(2, 14, 2, 14, 15, 4).add(2, 0, 2, 14, 1, 14).add(2, 0, 14, 14, 15, 15)
                .add(2, 0, 1, 14, 15, 2).add(14, 0, 1, 15, 15, 15).add(1, 0, 1, 2, 15, 15)
                .add(6, 8, 15, 7, 9, 16).add(6, 8, 0, 7, 9, 1).add(7, 9, 15, 8, 10, 16)
                .add(7, 9, 0, 8, 10, 1).add(9, 6, 15, 10, 7, 16).add(9, 6, 0, 10, 7, 1)
                .standardReduceBuild().get();
    }
}
