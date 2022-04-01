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
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDisplayStand extends BlockContainerBase {

    public BlockDisplayStand(boolean empowerer) {
        super(ActuallyBlocks.defaultPickProps(0));
        isEmpowerer = empowerer;
    }

    private final boolean isEmpowerer;

    public boolean isEmpowerer() {
        return this.isEmpowerer;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return isEmpowerer? new TileEntityEmpowerer(): new TileEntityDisplayStand();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
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
                        if (!player.isCreative()) {
                            heldItem.shrink(1);
                        }
                        return ActionResultType.CONSUME;
                    } else if (ItemUtil.canBeStacked(heldItem, stackThere)) {
                        int maxTransfer = Math.min(stackThere.getCount(), heldItem.getMaxStackSize() - heldItem.getCount());
                        if (maxTransfer > 0) {
                            if (!player.isCreative())
                                player.setItemInHand(hand, StackUtil.grow(heldItem, maxTransfer));
                            ItemStack newStackThere = stackThere.copy();
                            newStackThere.shrink(maxTransfer);
                            stand.inv.setStackInSlot(0, newStackThere);
                            return ActionResultType.CONSUME;
                        }
                    }
                } else {
                    if (!stackThere.isEmpty() && hand == Hand.MAIN_HAND) {
                        player.setItemInHand(hand, stackThere.copy());
                        stand.inv.setStackInSlot(0, ItemStack.EMPTY);
                        return ActionResultType.CONSUME;
                    }
                }
            }
            return ActionResultType.FAIL;
        }

        return ActionResultType.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.DISPLAY_STAND_SHAPE;
    }
}
