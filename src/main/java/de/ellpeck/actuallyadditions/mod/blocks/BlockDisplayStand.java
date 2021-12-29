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

    public BlockDisplayStand() {
        super(ActuallyBlocks.defaultPickProps(0));
    }

    @Nullable
    //@Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityDisplayStand();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!world.isClientSide) {
            TileEntityDisplayStand stand = (TileEntityDisplayStand) world.getBlockEntity(pos);
            if (stand != null) {
                ItemStack display = stand.inv.getStackInSlot(0);
                if (StackUtil.isValid(heldItem)) {
                    if (!StackUtil.isValid(display)) {
                        ItemStack toPut = heldItem.copy();
                        toPut.setCount(1);
                        stand.inv.setStackInSlot(0, toPut);
                        if (!player.isCreative()) {
                            heldItem.shrink(1);
                        }
                        return ActionResultType.PASS;
                    } else if (ItemUtil.canBeStacked(heldItem, display)) {
                        int maxTransfer = Math.min(display.getCount(), heldItem.getMaxStackSize() - heldItem.getCount());
                        if (maxTransfer > 0) {
                            heldItem.grow(maxTransfer);
                            ItemStack newDisplay = display.copy();
                            newDisplay.shrink(maxTransfer);
                            stand.inv.setStackInSlot(0, newDisplay);
                            return ActionResultType.PASS;
                        }
                    }
                } else {
                    if (StackUtil.isValid(display)) {
                        player.setItemInHand(hand, display.copy());
                        stand.inv.setStackInSlot(0, StackUtil.getEmpty());
                        return ActionResultType.PASS;
                    }
                }
            }
            return ActionResultType.FAIL;
        }

        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.DISPLAY_STAND_SHAPE;
    }
}
