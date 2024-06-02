/*
 * This file ("BlockFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFireworkBox;
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

public class BlockFireworkBox extends BlockContainerBase {
    public BlockFireworkBox() {
        super(ActuallyBlocks.defaultPickProps());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return InteractionResult.SUCCESS;
        }

        return this.openGui(world, player, pos, TileEntityFireworkBox.class);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityFireworkBox(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityFireworkBox::clientTick : TileEntityFireworkBox::serverTick;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.FIREWORKS_BOX_SHAPE;
    }
}
