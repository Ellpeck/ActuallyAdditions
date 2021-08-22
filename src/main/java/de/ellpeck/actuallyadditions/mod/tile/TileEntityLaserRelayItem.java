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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.GenericItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityLaserRelayItem extends TileEntityLaserRelay {

    public final Map<BlockPos, SlotlessableItemHandlerWrapper> handlersAround = new ConcurrentHashMap<>();
    public int priority;

    public TileEntityLaserRelayItem(TileEntityType<?> type) {
        super(type, LaserType.ITEM);
    }

    public TileEntityLaserRelayItem() {
        this(ActuallyTiles.LASERRELAYITEM_TILE.get());
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
                TileEntity tile = this.level.getBlockEntity(pos);
                if (tile != null && !(tile instanceof TileEntityItemViewer) && !(tile instanceof TileEntityLaserRelay)) {
                    LazyOptional<IItemHandler> itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());

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
                    TileEntity aRelayTile = this.level.getBlockEntity(relay);
                    if (aRelayTile instanceof TileEntityLaserRelayItem) {
                        TileEntityLaserRelayItem relayTile = (TileEntityLaserRelayItem) aRelayTile;
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
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("Priority", this.priority);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getExtraDisplayString() {
        return StringUtil.localize("info." + ActuallyAdditions.MODID + ".laserRelay.item.extra") + ": " + TextFormatting.DARK_RED + this.getPriority() + TextFormatting.RESET;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getCompassDisplayString() {
        return TextFormatting.GREEN + StringUtil.localize("info." + ActuallyAdditions.MODID + ".laserRelay.item.display.1") + "\n" + StringUtil.localize("info." + ActuallyAdditions.MODID + ".laserRelay.item.display.2");
    }

    @Override
    public void onCompassAction(PlayerEntity player) {
        if (player.isShiftKeyDown()) {
            this.priority--;
        } else {
            this.priority++;
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.priority = compound.getInt("Priority");
        }
    }
}
