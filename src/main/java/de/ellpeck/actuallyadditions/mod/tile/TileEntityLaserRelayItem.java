/*
 * This file ("TileEntityLaserRelayItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemInterface.GenericItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityLaserRelayItem extends TileEntityLaserRelay {

    public final Map<BlockPos, SlotlessableItemHandlerWrapper> handlersAround = new ConcurrentHashMap<>();
    public int priority;

    public TileEntityLaserRelayItem(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, LaserType.ITEM);
    }

    public TileEntityLaserRelayItem(BlockPos pos, BlockState state) {
        this(ActuallyBlocks.LASER_RELAY_ITEM.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayItem tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayItem tile) {
            tile.serverTick();
        }
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isWhitelisted(ItemStack stack, boolean output) {
        return true;
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        Map<BlockPos, SlotlessableItemHandlerWrapper> old = new HashMap<>(this.handlersAround);
        boolean change = false;

        this.handlersAround.clear();
        for (int i = 0; i <= 5; i++) {
            Direction side = WorldUtil.getDirectionBySidesInOrder(i);
            BlockPos pos = this.getBlockPos().relative(side);
            if (this.level.hasChunkAt(pos)) {
                BlockEntity tile = this.level.getBlockEntity(pos);
                if (tile != null && !(tile instanceof TileEntityItemInterface) && !(tile instanceof TileEntityLaserRelay)) {
                    IItemHandler itemHandler = this.level.getCapability(Capabilities.ItemHandler.BLOCK, tile.getBlockPos(), side.getOpposite());

                    Object slotlessHandler = null;
                    // TODO: [port] add this back maybe?

                    //                    if (ActuallyAdditions.commonCapsLoaded) {
                    //                        if (tile.hasCapability(SlotlessItemHandlerConfig.CAPABILITY, side.getOpposite())) {
                    //                            slotlessHandler = tile.getCapability(SlotlessItemHandlerConfig.CAPABILITY, side.getOpposite());
                    //                        }
                    //                    }

                    SlotlessableItemHandlerWrapper handler = new SlotlessableItemHandlerWrapper(itemHandler, slotlessHandler);
                    this.handlersAround.put(pos, handler);

                    SlotlessableItemHandlerWrapper oldHandler = old.get(pos);
                    if (!handler.equals(oldHandler)) {
                        change = true;
                    }
                }
            }
        }

        if (change || old.size() != this.handlersAround.size()) {
            Network network = this.getNetwork();
            if (network != null) {
                network.changeAmount++;
            }
        }
    }

    public void getItemHandlersInNetwork(Network network, List<GenericItemHandlerInfo> storeList) {
        //Keeps track of all the Laser Relays and Item Handlers that have been checked already to make nothing run multiple times
        Set<BlockPos> alreadyChecked = new HashSet<>();

        for (IConnectionPair pair : network.connections) {
            for (BlockPos relay : pair.getPositions()) {
                if (relay != null && this.level.hasChunkAt(relay) && !alreadyChecked.contains(relay)) {
                    alreadyChecked.add(relay);
                    BlockEntity aRelayTile = this.level.getBlockEntity(relay);
                    if (aRelayTile instanceof TileEntityLaserRelayItem relayTile) {
	                    GenericItemHandlerInfo info = new GenericItemHandlerInfo(relayTile);

                        for (Map.Entry<BlockPos, SlotlessableItemHandlerWrapper> handler : relayTile.handlersAround.entrySet()) {
                            if (!alreadyChecked.contains(handler.getKey())) {
                                alreadyChecked.add(handler.getKey());

                                info.handlers.add(handler.getValue());
                            }
                        }

                        storeList.add(info);
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("Priority", this.priority);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getExtraDisplayString() {
        return Component.translatable("info.actuallyadditions.laserRelay.item.extra").append(": ").append(Component.literal(String.valueOf(this.getPriority())).withStyle(ChatFormatting.DARK_RED));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getCompassDisplayString() {
        return Component.translatable("info.actuallyadditions.laserRelay.item.display.1").append(" - ").append(Component.translatable("info.actuallyadditions.laserRelay.item.display.2")).withStyle(ChatFormatting.GREEN);
    }

    @Override
    public void onCompassAction(Player player) {
        if (player.isCrouching()) {
            this.priority--;
        } else {
            this.priority++;
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.priority = compound.getInt("Priority");
        }
    }
}
