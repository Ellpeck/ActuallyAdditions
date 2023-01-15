/*
 * This file ("BlockBatteryBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoalGenerator;
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

public class BlockBatteryBox extends BlockContainerBase {

    public BlockBatteryBox() {
        super(ActuallyBlocks.defaultPickProps(0));
    }

    //    @Override
    //    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
    //        return BlockSlabs.AABB_BOTTOM_HALF;
    //    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.BATBOX_SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityBatteryBox();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityBatteryBox) {
            TileEntityBatteryBox box = (TileEntityBatteryBox) tile;
            ItemStack stack = player.getItemInHand(hand);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemBattery && box.inv.getStackInSlot(0).isEmpty()) {
                    box.inv.setStackInSlot(0, stack.copy());
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    return ActionResultType.SUCCESS;
                }
            } else {
                ItemStack inSlot = box.inv.getStackInSlot(0);
                if (!inSlot.isEmpty()) {
                    player.setItemInHand(hand, inSlot.copy());
                    box.inv.setStackInSlot(0, ItemStack.EMPTY);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }
}
