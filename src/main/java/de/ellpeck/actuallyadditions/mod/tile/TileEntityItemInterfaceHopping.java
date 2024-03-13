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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;
import java.util.Optional;

public class TileEntityItemInterfaceHopping extends TileEntityItemInterface {

    private SlotlessableItemHandlerWrapper handlerToPullFrom;
    private SlotlessableItemHandlerWrapper handlerToPushTo;

    public TileEntityItemInterfaceHopping(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.ITEM_INTERFACE_HOPPING.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityItemInterfaceHopping tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityItemInterfaceHopping tile) {
            tile.serverTick();

            // TODO: [port] validate tile is the correct way to get total game time getGameTime
            if (level.getLevelData().getGameTime() % 10 == 0) {
                if (tile.handlerToPullFrom != null) {
                    WorldUtil.doItemInteraction(tile.handlerToPullFrom, tile.itemHandler, 4);
                } else {
                    if (level.getLevelData().getGameTime() % 20 == 0) {
                        //TODO hmm?
                        AABB axisAlignedBB = new AABB(pos.getX(), pos.getY() + 0.5, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
                        List<ItemEntity> items = level.getEntities(EntityType.ITEM, axisAlignedBB, EntitySelector.ENTITY_STILL_ALIVE);
                        if (items != null && !items.isEmpty()) {
                            for (ItemEntity item : items) {
                                if (item != null) {
                                    if (ActuallyAdditions.commonCapsLoaded) {
                                        Object slotless = tile.itemHandler.getSlotlessHandler();
                                        // TODO: [port] add back?
                                        //                                    if (slotless instanceof ISlotlessItemHandler) {
                                        //                                        ItemStack left = ((ISlotlessItemHandler) slotless).insertItem(item.getItem(), false);
                                        //                                        item.setItem(left);
                                        //
                                        //                                        if (!StackUtil.isValid(left)) {
                                        //                                            item.remove();
                                        //                                            continue;
                                        //                                        }
                                        //                                    }
                                    }

                                    Optional<IItemHandler> handler = Optional.ofNullable(tile.itemHandler.getNormalHandler());
                                    handler.ifPresent(cap -> {
                                        System.out.println(cap.getSlots());
                                        for (int i = 0; i < cap.getSlots(); i++) {
                                            ItemStack left = cap.insertItem(i, item.getItem(), false);
                                            item.setItem(left);

                                            if (left.isEmpty()) {
                                                item.discard();
                                                break;
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }

                if (tile.handlerToPushTo != null) {
                    WorldUtil.doItemInteraction(tile.itemHandler, tile.handlerToPushTo, 4);
                }
            }
        }
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        super.saveDataOnChangeOrWorldStart();

        this.handlerToPullFrom = null;
        this.handlerToPushTo = null;

        BlockEntity from = this.level.getBlockEntity(this.getBlockPos().relative(Direction.UP));
        if (from != null && !(from instanceof TileEntityItemInterface)) {
            IItemHandler normal = this.level.getCapability(Capabilities.ItemHandler.BLOCK, from.getBlockPos(), Direction.DOWN);

            Object slotless = null;
            // TODO: [port] add back

            //            if (ActuallyAdditions.commonCapsLoaded) {
            //                if (from.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, Direction.DOWN)) {
            //                    slotless = from.getCapability(SlotlessItemHandlerConfig.CAPABILITY, Direction.DOWN);
            //                }
            //            }

            this.handlerToPullFrom = new SlotlessableItemHandlerWrapper(normal, slotless);
        }

        BlockState state = this.level.getBlockState(this.getBlockPos());
        Direction facing = state.getValue(BlockStateProperties.FACING_HOPPER);

        BlockPos toPos = this.getBlockPos().relative(facing);
        if (this.level.isLoaded(toPos)) {
            BlockEntity to = this.level.getBlockEntity(toPos);
            if (to != null && !(to instanceof TileEntityItemInterface)) {
                IItemHandler normal = this.level.getCapability(Capabilities.ItemHandler.BLOCK, to.getBlockPos(), facing.getOpposite());

                Object slotless = null;
                //                TODO: [port] Add back
                //                if (ActuallyAdditions.commonCapsLoaded) {
                //                    if (to.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, facing.getOpposite())) {
                //                        slotless = to.getCapability(SlotlessItemHandlerConfig.CAPABILITY, facing.getOpposite());
                //                    }
                //                }

                this.handlerToPushTo = new SlotlessableItemHandlerWrapper(normal, slotless);
            }
        }
    }
}
