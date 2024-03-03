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

public class BlockBatteryBox extends BlockContainerBase {

    public BlockBatteryBox() {
        super(ActuallyBlocks.defaultPickProps());
    }

    //    @Override
    //    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
    //        return BlockSlabs.AABB_BOTTOM_HALF;
    //    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.BATBOX_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityBatteryBox(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityBatteryBox::clientTick : TileEntityBatteryBox::serverTick;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityBatteryBox box) {
	        ItemStack stack = player.getItemInHand(hand);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemBattery && box.inv.getStackInSlot(0).isEmpty()) {
                    box.inv.setStackInSlot(0, stack.copy());
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
            } else {
                ItemStack inSlot = box.inv.getStackInSlot(0);
                if (!inSlot.isEmpty()) {
                    player.setItemInHand(hand, inSlot.copy());
                    box.inv.setStackInSlot(0, ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }
}
