/*
 * This file ("BlockCrystalCluster.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.items.metalists.Crystals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CrystalClusterBlock extends FullyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    // TODO: Remove - Duplicate
//    public static final VoxelShape CRYSTAL_SHAPE = Stream.of(
//        Block.box(5, 4, 5, 10, 19, 10), Block.box(4, 0, 4, 11, 5, 11),
//        Block.box(3, 0, 3, 5, 4, 5), Block.box(10, 0, 3, 12, 2, 5),
//        Block.box(12, 0, 4, 13, 1, 5), Block.box(11, 0, 5, 12, 1, 6),
//        Block.box(10, 0, 10, 12, 3, 12), Block.box(3, 0, 10, 5, 1, 12),
//        Block.box(9, 0, 3, 10, 3, 4), Block.box(8, 0, 2, 11, 1, 4),
//        Block.box(4, 0, 2, 5, 2, 3), Block.box(5, 0, 3, 7, 1, 4),
//        Block.box(2, 0, 4, 4, 1, 6), Block.box(3, 0, 5, 4, 3, 6.5),
//        Block.box(3, 0, 9, 4, 2, 10), Block.box(2, 0, 8, 4, 1, 10),
//        Block.box(5, 0, 11, 7, 2, 13), Block.box(7, 0, 11, 11, 1, 13),
//        Block.box(10, 0, 9, 13, 1, 11), Block.box(11, 0, 7, 12, 3, 9)
//    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR) ).get();

    public CrystalClusterBlock(Crystals crystal) {
        super(Block.Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .lightLevel((e) -> 7)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .strength(0.25f, 1.0f));
    }

    @Override
    public BlockState getBaseConstructorState() {
        return this.defaultBlockState().setValue(FACING, Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.CRYSTAL_CLUSTER_SHAPE;
    }
}
