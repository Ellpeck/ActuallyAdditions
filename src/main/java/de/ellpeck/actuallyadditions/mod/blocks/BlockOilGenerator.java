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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockOilGenerator extends DirectionalBlock.Container {

    public BlockOilGenerator() {
        super(ActuallyBlocks.defaultPickProps().randomTicks());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityOilGenerator(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityOilGenerator::clientTick : TileEntityOilGenerator::serverTick;
    }

    // TODO: Move all of these over to the client version
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityOilGenerator) {
            if (((TileEntityOilGenerator) tile).currentBurnTime > 0) {
                for (int i = 0; i < 5; i++) {
                    world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            TileEntityOilGenerator generator = (TileEntityOilGenerator) world.getBlockEntity(pos);
            if (generator != null) {
                if (!this.tryUseItemOnTank(player, hand, generator.tank)) {
                    player.openMenu(generator, pos);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return VoxelShapes.OilGeneratorShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.OilGeneratorShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.OilGeneratorShapes.SHAPE_W;
            default:
                return VoxelShapes.OilGeneratorShapes.SHAPE_N;
        }
    }*/

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.SIMPLE_GENERATOR_SHAPE;
    }
}
