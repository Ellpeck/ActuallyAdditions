/*
 * This file ("BlockEnergizer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnergizer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import net.minecraft.core.BlockPos;
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

public class BlockEnergizer extends BlockContainerBase {
    private final boolean isEnergizer;

    public BlockEnergizer(boolean isEnergizer) {
        super(ActuallyBlocks.defaultPickProps(2.0F, 10.0F));
        this.isEnergizer = isEnergizer;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.isEnergizer
            ? new TileEntityEnergizer(pos, state)
            : new TileEntityEnervator(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return this.isEnergizer
                ? level.isClientSide? TileEntityEnergizer::clientTick : TileEntityEnergizer::serverTick
                : level.isClientSide? TileEntityEnervator::clientTick : TileEntityEnervator::serverTick;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (this.isEnergizer) {
            return this.openGui(world, player, pos, TileEntityEnergizer.class);
        } else {
            return this.openGui(world, player, pos, TileEntityEnervator.class);
        }
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (this.isEnergizer) {
            return VoxelShapes.ENERGIZER_SHAPE;
        }
        return VoxelShapes.ENERVATOR_SHAPE;
    }*/
}
