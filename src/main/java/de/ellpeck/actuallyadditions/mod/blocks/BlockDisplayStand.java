/*
 * This file ("BlockDisplayStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDisplayStand;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEmpowerer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

public class BlockDisplayStand extends BlockContainerBase {

	public BlockDisplayStand(boolean empowerer) {
		super(ActuallyBlocks.defaultPickProps());
		isEmpowerer = empowerer;
	}

	private final boolean isEmpowerer;

	public boolean isEmpowerer() {
		return this.isEmpowerer;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return isEmpowerer ? new TileEntityEmpowerer(pos, state) : new TileEntityDisplayStand(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
		return this.isEmpowerer
				? level.isClientSide? TileEntityEmpowerer::clientTick : TileEntityEmpowerer::serverTick
				: level.isClientSide? TileEntityDisplayStand::clientTick : TileEntityDisplayStand::serverTick;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack heldItem = player.getItemInHand(hand);
		if (!world.isClientSide) {
			TileEntityInventoryBase stand = (TileEntityInventoryBase) world.getBlockEntity(pos);
			if (stand != null) {
				ItemStack stackThere = stand.inv.getStackInSlot(0);
				if (!heldItem.isEmpty()) {
					if (stackThere.isEmpty() && (!isEmpowerer || TileEntityEmpowerer.isPossibleInput(heldItem))) {
						ItemStack toPut = heldItem.copy();
						toPut.setCount(1);
						stand.inv.setStackInSlot(0, toPut);
						if (!player.isCreative())
							heldItem.shrink(1);
						else
							player.swing(hand, true);
						return InteractionResult.SUCCESS;
					} else if (ItemUtil.canBeStacked(heldItem, stackThere)) {
						int maxTransfer = Math.min(stackThere.getCount(), heldItem.getMaxStackSize() - heldItem.getCount());
						if (maxTransfer > 0) {
							if (!player.isCreative())
								player.setItemInHand(hand, StackUtil.grow(heldItem, maxTransfer));
							else
								player.swing(hand, true);
							ItemStack newStackThere = stackThere.copy();
							newStackThere.shrink(maxTransfer);
							stand.inv.setStackInSlot(0, newStackThere);
							return InteractionResult.SUCCESS;
						}
					}
				} else {
					if (!stackThere.isEmpty() && hand == InteractionHand.MAIN_HAND) {
						player.setItemInHand(hand, stackThere.copy());
						stand.inv.setStackInSlot(0, ItemStack.EMPTY);
						return InteractionResult.CONSUME;
					}
				}
			}
			return InteractionResult.FAIL;
		}

		return InteractionResult.CONSUME;
	}

/*	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if (this.isEmpowerer) {
			return VoxelShapes.EMPOWERER_SHAPE;
		}
		return VoxelShapes.DISPLAY_STAND_SHAPE;
	}*/
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return VoxelShapes.SIMPLE_STAND_SHAPE;
	}
}
