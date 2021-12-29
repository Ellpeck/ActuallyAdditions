/*
 * This file ("BlockFluidCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidCollector;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidPlacer;
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

public class BlockFluidCollector extends FullyDirectionalBlock.Container {
    private final boolean isPlacer;

    public BlockFluidCollector(boolean isPlacer) {
        super(ActuallyBlocks.defaultPickProps(0));
        this.isPlacer = isPlacer;
    }

    //@Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return this.isPlacer
            ? new TileEntityFluidPlacer()
            : new TileEntityFluidCollector();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.PASS;
        }

        return this.openGui(world, player, pos, TileEntityFluidCollector.class);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return Shapes.FluidCollectorShapes.SHAPE_U;
            case DOWN:
                return Shapes.FluidCollectorShapes.SHAPE_D;
            case EAST:
                return Shapes.FluidCollectorShapes.SHAPE_E;
            case SOUTH:
                return Shapes.FluidCollectorShapes.SHAPE_S;
            case WEST:
                return Shapes.FluidCollectorShapes.SHAPE_W;
            default:
                return Shapes.FluidCollectorShapes.SHAPE_N;
        }
    }
}
