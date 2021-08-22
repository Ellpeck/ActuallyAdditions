/*
 * This file ("BlockDirectionalBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDirectionalBreaker;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockDirectionalBreaker extends FullyDirectionalBlock.Container {

    public BlockDirectionalBreaker() {
        super(ActuallyBlocks.defaultPickProps(0));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityDirectionalBreaker();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.PASS;
        }

        return this.openGui(world, player, pos, TileEntityDirectionalBreaker.class);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return Shapes.DirectionalBlockBreakerShapes.SHAPE_U;
            case DOWN:
                return Shapes.DirectionalBlockBreakerShapes.SHAPE_D;
            case EAST:
                return Shapes.DirectionalBlockBreakerShapes.SHAPE_E;
            case SOUTH:
                return Shapes.DirectionalBlockBreakerShapes.SHAPE_S;
            case WEST:
                return Shapes.DirectionalBlockBreakerShapes.SHAPE_W;
            default:
                return Shapes.DirectionalBlockBreakerShapes.SHAPE_N;
        }
    }
}
