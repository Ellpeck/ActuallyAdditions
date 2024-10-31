/*
 * This file ("BlockBioReactor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBioReactor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockBioReactor extends DirectionalBlock.Container {

    public BlockBioReactor() {
        super(ActuallyBlocks.defaultPickProps(2.0F, 10.0F).sound(SoundType.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityBioReactor(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityBioReactor::clientTick : TileEntityBioReactor::serverTick;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level world, BlockPos pos, Player player, BlockHitResult pHitResult) {
        return this.openGui(world, player, pos, TileEntityBioReactor.class);
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(HORIZONTAL_FACING)) {
            case EAST:
                return VoxelShapes.BioReactorShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.BioReactorShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.BioReactorShapes.SHAPE_W;
            default:
                return VoxelShapes.BioReactorShapes.SHAPE_N;
        }
    }*/
}
