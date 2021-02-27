/*
 * This file ("TileEntityItemViewerHopping.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import java.util.List;

import org.cyclops.commoncapabilities.api.capability.itemhandler.ISlotlessItemHandler;
import org.cyclops.commoncapabilities.capability.itemhandler.SlotlessItemHandlerConfig;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityItemViewerHopping extends TileEntityItemViewer {

    private SlotlessableItemHandlerWrapper handlerToPullFrom;
    private SlotlessableItemHandlerWrapper handlerToPushTo;

    public TileEntityItemViewerHopping() {
        super("itemViewerHopping");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.world.isRemote && this.world.getTotalWorldTime() % 10 == 0) {
            if (this.handlerToPullFrom != null) {
                WorldUtil.doItemInteraction(this.handlerToPullFrom, this.itemHandler, 4);
            } else {
                if (this.world.getTotalWorldTime() % 20 == 0) {
                    List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos.getX(), this.pos.getY() + 0.5, this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 2, this.pos.getZ() + 1));
                    if (items != null && !items.isEmpty()) {
                        for (EntityItem item : items) {
                            if (item != null && !item.isDead) {
                                if (ActuallyAdditions.commonCapsLoaded) {
                                    Object slotless = this.itemHandler.getSlotlessHandler();
                                    if (slotless instanceof ISlotlessItemHandler) {
                                        ItemStack left = ((ISlotlessItemHandler) slotless).insertItem(item.getItem(), false);
                                        item.setItem(left);

                                        if (!StackUtil.isValid(left)) {
                                            item.setDead();
                                            continue;
                                        }
                                    }
                                }

                                IItemHandler handler = this.itemHandler.getNormalHandler();
                                if (handler != null) {
                                    for (int i = 0; i < handler.getSlots(); i++) {
                                        ItemStack left = handler.insertItem(i, item.getItem(), false);
                                        item.setItem(left);

                                        if (!StackUtil.isValid(left)) {
                                            item.setDead();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.handlerToPushTo != null) {
                WorldUtil.doItemInteraction(this.itemHandler, this.handlerToPushTo, 4);
            }
        }
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        super.saveDataOnChangeOrWorldStart();

        this.handlerToPullFrom = null;
        this.handlerToPushTo = null;

        TileEntity from = this.world.getTileEntity(this.pos.offset(Direction.UP));
        if (from != null && !(from instanceof TileEntityItemViewer)) {
            IItemHandler normal = null;
            if (from.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN)) {
                normal = from.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN);
            }

            Object slotless = null;
            if (ActuallyAdditions.commonCapsLoaded) {
                if (from.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, Direction.DOWN)) {
                    slotless = from.getCapability(SlotlessItemHandlerConfig.CAPABILITY, Direction.DOWN);
                }
            }

            this.handlerToPullFrom = new SlotlessableItemHandlerWrapper(normal, slotless);
        }

        BlockState state = this.world.getBlockState(this.pos);
        Direction facing = state.getValue(BlockHopper.FACING);

        BlockPos toPos = this.pos.offset(facing);
        if (this.world.isBlockLoaded(toPos)) {
            TileEntity to = this.world.getTileEntity(toPos);
            if (to != null && !(to instanceof TileEntityItemViewer)) {
                IItemHandler normal = null;
                if (to.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
                    normal = to.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
                }

                Object slotless = null;
                if (ActuallyAdditions.commonCapsLoaded) {
                    if (to.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, facing.getOpposite())) {
                        slotless = to.getCapability(SlotlessItemHandlerConfig.CAPABILITY, facing.getOpposite());
                    }
                }

                this.handlerToPushTo = new SlotlessableItemHandlerWrapper(normal, slotless);
            }
        }
    }
}
