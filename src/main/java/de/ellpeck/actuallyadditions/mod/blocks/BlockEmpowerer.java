/*
 * This file ("BlockEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEmpowerer;
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

public class BlockEmpowerer extends BlockContainerBase {
    public BlockEmpowerer() {
        super(ActuallyBlocks.defaultPickProps(0));
    }

    @Nullable
    //@Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityEmpowerer();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!world.isClientSide) {
            TileEntityEmpowerer empowerer = (TileEntityEmpowerer) world.getBlockEntity(pos);
            if (empowerer != null) {
                ItemStack stackThere = empowerer.inv.getStackInSlot(0);
                if (StackUtil.isValid(heldItem)) {
                    if (!StackUtil.isValid(stackThere) && TileEntityEmpowerer.isPossibleInput(heldItem)) {
                        ItemStack toPut = heldItem.copy();
                        toPut.setCount(1);
                        empowerer.inv.setStackInSlot(0, toPut);
                        if (!player.isCreative()) {
                            heldItem.shrink(1);
                        }
                        return ActionResultType.PASS;
                    } else if (ItemUtil.canBeStacked(heldItem, stackThere)) {
                        int maxTransfer = Math.min(stackThere.getCount(), heldItem.getMaxStackSize() - heldItem.getCount());
                        if (maxTransfer > 0) {
                            player.setItemInHand(hand, StackUtil.grow(heldItem, maxTransfer));
                            ItemStack newStackThere = stackThere.copy();
                            newStackThere = StackUtil.shrink(newStackThere, maxTransfer);
                            empowerer.inv.setStackInSlot(0, newStackThere);
                            return ActionResultType.PASS;
                        }
                    }
                } else {
                    if (StackUtil.isValid(stackThere)) {
                        player.setItemInHand(hand, stackThere.copy());
                        empowerer.inv.setStackInSlot(0, StackUtil.getEmpty());
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
        return Shapes.EMPOWERER_SHAPE;
    }
}
