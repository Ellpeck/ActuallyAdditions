/*
 * This file ("TileEntityItemViewer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.network.packet.SpawnLaserParticlePacket;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityItemInterface extends TileEntityBase {

    public final List<GenericItemHandlerInfo> genericInfos = new ArrayList<>();
    public final Map<Integer, IItemHandlerInfo> itemHandlerInfos = new HashMap<>();
    public final List<SlotlessItemHandlerInfo> slotlessInfos = new ArrayList<>();
    protected final SlotlessableItemHandlerWrapper itemHandler;
    public TileEntityLaserRelayItem connectedRelay;
    private int lastNetworkChangeAmount = -1;
    private int slotCount;

    public TileEntityItemInterface(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        IItemHandler normalHandler = new IItemHandler() {
            @Override
            public int getSlots() {
                return TileEntityItemInterface.this.getSlotCount();
            }

            @Nonnull
            @Override
            public ItemStack getStackInSlot(int slot) {
                IItemHandlerInfo handler = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                if (handler != null && handler.isLoaded()) {
                    return handler.handler.getStackInSlot(handler.switchedIndex);
                }
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                IItemHandlerInfo info = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                if (info != null && info.isLoaded() && TileEntityItemInterface.this.isWhitelisted(info, stack, false)) {
                    ItemStack remain = info.handler.insertItem(info.switchedIndex, stack, simulate);
                    if (!ItemStack.isSameItem(remain, stack) && !simulate) {
                        TileEntityItemInterface.this.setChanged();
                        TileEntityItemInterface.this.doItemParticle(stack, info.relayInQuestion.getBlockPos(), TileEntityItemInterface.this.connectedRelay.getBlockPos());
                    }
                    return remain;
                }
                return stack;
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                ItemStack stackIn = this.getStackInSlot(slot);
                if (!stackIn.isEmpty()) {
                    IItemHandlerInfo info = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                    if (info != null && info.isLoaded() && TileEntityItemInterface.this.isWhitelisted(info, stackIn, true)) {
                        ItemStack extracted = info.handler.extractItem(info.switchedIndex, amount, simulate);
                        if (!extracted.isEmpty() && !simulate) {
                            TileEntityItemInterface.this.setChanged();
                            TileEntityItemInterface.this.doItemParticle(extracted, TileEntityItemInterface.this.connectedRelay.getBlockPos(), info.relayInQuestion.getBlockPos());
                        }
                        return extracted;
                    }
                }
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                IItemHandlerInfo info = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                if (info != null && info.isLoaded()) {
                    return info.handler.getSlotLimit(info.switchedIndex);
                } else {
                    return 0;
                }
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }
        };

        Object slotlessHandler = null;
        //        if (ActuallyAdditions.commonCapsLoaded) {
        //            slotlessHandler = CommonCapsUtil.createSlotlessItemViewerHandler(this, normalHandler);
        //        }

        this.itemHandler = new SlotlessableItemHandlerWrapper(normalHandler, slotlessHandler);
    }

    public TileEntityItemInterface(BlockPos pos, BlockState state) {
        this(ActuallyBlocks.ITEM_INTERFACE.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityItemInterface tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityItemInterface tile) {
            tile.serverTick();
        }
    }

    @Override
    public IItemHandler getItemHandler(Direction facing) {
        return this.itemHandler.getNormalHandler();
    }

    //    TODO: [port] Maybe add back
    //    @SuppressWarnings("unchecked")
    //    @Override
    //    public <T> T getCapability(Capability<T> capability, Direction facing) {
    //        if (ActuallyAdditions.commonCapsLoaded) {
    //            if (capability == SlotlessItemHandlerConfig.CAPABILITY) {
    //                Object handler = this.itemHandler.getSlotlessHandler();
    //                if (handler != null) {
    //                    return (T) handler;
    //                }
    //            }
    //        }
    //        return super.getCapability(capability, facing);
    //    }

    private int getSlotCount() {
        this.queryAndSaveData();
        return this.slotCount;
    }

    public void doItemParticle(ItemStack stack, BlockPos input, BlockPos output) {
        if (!this.level.isClientSide) {
            int rangeSq = 16 * 16;
            for (Player player : this.level.players()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (player.distanceToSqr(input.getX(), input.getY(), input.getZ()) <= rangeSq || player.distanceToSqr(output.getX(), output.getY(), output.getZ()) <= rangeSq) {
                        serverPlayer.connection.send(new SpawnLaserParticlePacket(stack, input, output));
                    }
                }
            }
        }
    }

    private void queryAndSaveData() {
        if (this.connectedRelay != null) {
            Network network = this.connectedRelay.getNetwork();
            if (network != null) {
                if (this.lastNetworkChangeAmount != network.changeAmount) {
                    this.clearInfos();

                    this.connectedRelay.getItemHandlersInNetwork(network, this.genericInfos);
                    if (!this.genericInfos.isEmpty()) {
                        Collections.sort(this.genericInfos);

                        int slotsQueried = 0;
                        for (GenericItemHandlerInfo info : this.genericInfos) {
                            if (!info.isLoaded()) continue;
                            for (SlotlessableItemHandlerWrapper handler : info.handlers) {
                                IItemHandler normalHandler = handler.getNormalHandler();
                                if (normalHandler != null) {
                                    for (int i = 0; i < normalHandler.getSlots(); i++) {
                                        this.itemHandlerInfos.put(slotsQueried, new IItemHandlerInfo(normalHandler, i, info.relayInQuestion));
                                        slotsQueried++;
                                    }
                                }
                                // TODO: [port] add back

                                //                                if (ActuallyAdditions.commonCapsLoaded) {
                                //                                    Object slotlessHandler = handler.getSlotlessHandler();
                                //                                    if (slotlessHandler instanceof ISlotlessItemHandler) {
                                //                                        this.slotlessInfos.add(new SlotlessItemHandlerInfo(slotlessHandler, info.relayInQuestion));
                                //                                    }
                                //                                }
                            }
                        }
                        this.slotCount = slotsQueried;
                    }
                    this.lastNetworkChangeAmount = network.changeAmount;
                }

                return;
            }
        }

        this.clearInfos();
        this.lastNetworkChangeAmount = -1;
    }

    private void clearInfos() {
        if (!this.genericInfos.isEmpty()) {
            this.genericInfos.clear();
        }

        if (!this.itemHandlerInfos.isEmpty()) {
            this.itemHandlerInfos.clear();
        }

        if (!this.slotlessInfos.isEmpty()) {
            this.slotlessInfos.clear();
        }
    }

    private IItemHandlerInfo getSwitchedIndexHandler(int i) {
        this.queryAndSaveData();
        return this.itemHandlerInfos.get(i);
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        TileEntityLaserRelayItem tileFound = null;
        if (this.level != null) { //Why is that even possible..?
            for (int i = 0; i <= 5; i++) {
                Direction side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = this.getBlockPos().relative(side);

                if (this.level.isLoaded(pos)) {
                    BlockEntity tile = this.level.getBlockEntity(pos);

                    if (tile instanceof TileEntityLaserRelayItem) {
                        if (tileFound != null) {
                            this.connectedRelay = null;
                            return;
                        } else {
                            tileFound = (TileEntityLaserRelayItem) tile;
                        }
                    }
                }
            }
        }
        this.connectedRelay = tileFound;
    }

    public boolean isWhitelisted(SpecificItemHandlerInfo handler, ItemStack stack, boolean output) {
        boolean whitelisted = handler.relayInQuestion.isWhitelisted(stack, output);
        TileEntityLaserRelayItem connected = this.connectedRelay;
        if (connected != null && connected != handler.relayInQuestion) {
            return whitelisted && connected.isWhitelisted(stack, output);
        } else {
            return whitelisted;
        }
    }

    public static class SlotlessItemHandlerInfo extends SpecificItemHandlerInfo {

        public final Object handler;

        public SlotlessItemHandlerInfo(Object handler, TileEntityLaserRelayItem relayInQuestion) {
            super(relayInQuestion);
            this.handler = handler;
        }
    }

    public static class IItemHandlerInfo extends SpecificItemHandlerInfo {

        public final IItemHandler handler;
        public final int switchedIndex;

        public IItemHandlerInfo(IItemHandler handler, int switchedIndex, TileEntityLaserRelayItem relayInQuestion) {
            super(relayInQuestion);
            this.handler = handler;
            this.switchedIndex = switchedIndex;
        }
    }

    public static class SpecificItemHandlerInfo {

        public final TileEntityLaserRelayItem relayInQuestion;

        public SpecificItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion) {
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded() {
            return this.relayInQuestion.hasLevel() && this.relayInQuestion.getLevel().isLoaded(this.relayInQuestion.getBlockPos());
        }
    }

    public static class GenericItemHandlerInfo implements Comparable<GenericItemHandlerInfo> {

        public final List<SlotlessableItemHandlerWrapper> handlers = new ArrayList<>();
        public final TileEntityLaserRelayItem relayInQuestion;

        public GenericItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion) {
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded() {
            return this.relayInQuestion.hasLevel() && this.relayInQuestion.getLevel().isLoaded(this.relayInQuestion.getBlockPos());
        }

        @Override
        public int compareTo(GenericItemHandlerInfo other) {
            int thisPrio = this.relayInQuestion.getPriority();
            int otherPrio = other.relayInQuestion.getPriority();

            if (thisPrio == otherPrio) {
                return 0;
            } else if (thisPrio > otherPrio) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
