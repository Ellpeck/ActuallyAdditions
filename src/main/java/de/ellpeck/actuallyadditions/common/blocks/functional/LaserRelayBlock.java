package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import de.ellpeck.actuallyadditions.common.blocks.types.LaserRelays;
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

public class LaserRelayBlock extends ActuallyBlock {
    public LaserRelayBlock(LaserRelays relayType) {
        super(Properties.create(Material.ROCK));
    }

    private static final VoxelShape RELAY_SHAPE = Stream.of(
            Block.makeCuboidShape(6, 4, 6, 7, 6, 7), Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(4, 2, 4, 12, 4, 12), Block.makeCuboidShape(9, 4, 9, 10, 6, 10),
            Block.makeCuboidShape(6, 4, 9, 7, 6, 10), Block.makeCuboidShape(3, 1, 12, 4, 5, 13),
            Block.makeCuboidShape(12, 1, 12, 13, 5, 13), Block.makeCuboidShape(3, 1, 3, 4, 5, 4),
            Block.makeCuboidShape(12, 1, 3, 13, 5, 4), Block.makeCuboidShape(3, 4, 4, 4, 5, 12),
            Block.makeCuboidShape(3, 1, 4, 4, 2, 12), Block.makeCuboidShape(12, 4, 4, 13, 5, 12),
            Block.makeCuboidShape(12, 1, 4, 13, 2, 12), Block.makeCuboidShape(4, 4, 12, 12, 5, 13),
            Block.makeCuboidShape(4, 4, 3, 12, 5, 4), Block.makeCuboidShape(4, 1, 12, 12, 2, 13),
            Block.makeCuboidShape(4, 1, 3, 12, 2, 4), Block.makeCuboidShape(9, 4, 6, 10, 6, 7),
            Block.makeCuboidShape(7, 4, 7, 9, 6, 9), Block.makeCuboidShape(7, 6, 7, 9, 10, 9),
            Block.makeCuboidShape(6.5, 5, 7, 7, 6, 9), Block.makeCuboidShape(6.5, 7, 7, 7, 7.5, 9),
            Block.makeCuboidShape(6.5, 9, 7, 7, 9.5, 9), Block.makeCuboidShape(9, 5, 7, 9.5, 6, 9),
            Block.makeCuboidShape(9, 7, 7, 9.5, 7.5, 9), Block.makeCuboidShape(9, 9, 7, 9.5, 9.5, 9),
            Block.makeCuboidShape(7, 5, 6.5, 9, 6, 7), Block.makeCuboidShape(7, 7, 6.5, 9, 7.5, 7),
            Block.makeCuboidShape(7, 9, 6.5, 9, 9.5, 7), Block.makeCuboidShape(7, 5, 9, 9, 6, 9.5),
            Block.makeCuboidShape(7, 7, 9, 9, 7.5, 9.5), Block.makeCuboidShape(7, 9, 9, 9, 9.5, 9.5)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return RELAY_SHAPE;
    }
}
