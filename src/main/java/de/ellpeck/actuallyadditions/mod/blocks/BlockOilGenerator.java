/*
 * This file ("BlockOilGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityOilGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class BlockOilGenerator extends DirectionalBlock.Container {

    public BlockOilGenerator() {
        super(ActuallyBlocks.defaultPickProps(0).randomTicks());
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityOilGenerator();
    }

    // TODO: Move all of these over to the client version
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityOilGenerator) {
            if (((TileEntityOilGenerator) tile).currentBurnTime > 0) {
                for (int i = 0; i < 5; i++) {
                    world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide) {
            TileEntityOilGenerator generator = (TileEntityOilGenerator) world.getBlockEntity(pos);
            if (generator != null) {
                if (!this.tryUseItemOnTank(player, hand, generator.tank)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, generator, pos);
                }
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return Shapes.OilGeneratorShapes.SHAPE_E;
            case SOUTH:
                return Shapes.OilGeneratorShapes.SHAPE_S;
            case WEST:
                return Shapes.OilGeneratorShapes.SHAPE_W;
            default:
                return Shapes.OilGeneratorShapes.SHAPE_N;
        }
    }
}
