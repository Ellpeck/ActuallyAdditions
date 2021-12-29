/*
 * This file ("BlockCoalGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoalGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCoalGenerator extends DirectionalBlock.Container {
    public BlockCoalGenerator() {
        super(ActuallyBlocks.defaultPickProps(0).randomTicks());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCoalGenerator();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityCoalGenerator) {
            if (((TileEntityCoalGenerator) tile).currentBurnTime > 0) {
                for (int i = 0; i < 5; i++) {
                    world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        return this.openGui(world, player, pos, TileEntityCoalGenerator.class);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return Shapes.CoalGeneratorShapes.EAST;
            case SOUTH:
                return Shapes.CoalGeneratorShapes.SOUTH;
            case WEST:
                return Shapes.CoalGeneratorShapes.WEST;
            default:
                return Shapes.CoalGeneratorShapes.NORTH;
        }
    }
}
