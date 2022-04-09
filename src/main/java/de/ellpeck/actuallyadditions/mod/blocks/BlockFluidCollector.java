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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDropper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFermentingBarrel;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidCollector;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockFluidCollector extends FullyDirectionalBlock.Container {
    private final boolean isPlacer;

    public BlockFluidCollector(boolean isPlacer) {
        super(ActuallyBlocks.defaultPickProps(0));
        this.isPlacer = isPlacer;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.isPlacer
            ? new TileEntityFluidPlacer()
            : new TileEntityFluidCollector();
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (world.isClientSide)
            return ActionResultType.SUCCESS;

        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.CONSUME;
        }
        if (FluidUtil.interactWithFluidHandler(player, handIn, world, pos, hit.getDirection())) {
            return ActionResultType.SUCCESS;
        }

        return this.openGui(world, player, pos, TileEntityFluidCollector.class);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
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
