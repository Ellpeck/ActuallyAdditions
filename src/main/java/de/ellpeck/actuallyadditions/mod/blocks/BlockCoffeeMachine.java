/*
 * This file ("BlockCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockCoffeeMachine extends DirectionalBlock.Container {
    public BlockCoffeeMachine() {
        super(ActuallyBlocks.defaultPickProps().sound(SoundType.METAL));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHitResult) {
        if (world.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        } else {
            TileEntityCoffeeMachine tile = (TileEntityCoffeeMachine) world.getBlockEntity(pos);
            if (tile != null) {
                if (!this.tryUseItemOnTank(player, hand, tile.tank)) {
                    player.openMenu(tile, pos);
                }
            }
            return ItemInteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityCoffeeMachine(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityCoffeeMachine::clientTick : TileEntityCoffeeMachine::serverTick;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return VoxelShapes.CoffeeMachineShapes.EAST;
            case SOUTH:
                return VoxelShapes.CoffeeMachineShapes.SOUTH;
            case WEST:
                return VoxelShapes.CoffeeMachineShapes.WEST;
            default:
                return VoxelShapes.CoffeeMachineShapes.NORTH;
        }
    }
}
