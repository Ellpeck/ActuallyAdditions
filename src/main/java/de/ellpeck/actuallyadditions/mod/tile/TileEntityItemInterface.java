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
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TileEntityItemInterface extends TileEntityBase {

    public final List<GenericItemHandlerInfo> genericInfos = new ArrayList<>();
    public final Map<Integer, IItemHandlerInfo> itemHandlerInfos = new HashMap<>();
    public final List<SlotlessItemHandlerInfo> slotlessInfos = new ArrayList<>();
    protected final SlotlessableItemHandlerWrapper itemHandler;
    private final LazyOptional<IItemHandler> lazyHandlers;
    public TileEntityLaserRelayItem connectedRelay;
    private int lastNetworkChangeAmount = -1;
    private int slotCount;

    public TileEntityItemInterface(TileEntityType<?> type) {
        super(type);

        IItemHandler normalHandler = new IItemHandler() {
            @Override
            public int getSlots() {
                return TileEntityItemInterface.this.getSlotCount();
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                IItemHandlerInfo handler = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                if (handler != null && handler.isLoaded()) {
                    return handler.handler.getStackInSlot(handler.switchedIndex);
                }
                return StackUtil.getEmpty();
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                IItemHandlerInfo info = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                if (info != null && info.isLoaded() && TileEntityItemInterface.this.isWhitelisted(info, stack, false)) {
                    ItemStack remain = info.handler.insertItem(info.switchedIndex, stack, simulate);
                    if (!ItemStack.areItemStacksEqual(remain, stack) && !simulate) {
                        TileEntityItemInterface.this.markDirty();
                        TileEntityItemInterface.this.doItemParticle(stack, info.relayInQuestion.getPos(), TileEntityItemInterface.this.connectedRelay.getPos());
                    }
                    return remain;
                }
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                ItemStack stackIn = this.getStackInSlot(slot);
                if (StackUtil.isValid(stackIn)) {
                    IItemHandlerInfo info = TileEntityItemInterface.this.getSwitchedIndexHandler(slot);
                    if (info != null && info.isLoaded() && TileEntityItemInterface.this.isWhitelisted(info, stackIn, true)) {
                        ItemStack extracted = info.handler.extractItem(info.switchedIndex, amount, simulate);
                        if (StackUtil.isValid(extracted) && !simulate) {
                            TileEntityItemInterface.this.markDirty();
                            TileEntityItemInterface.this.doItemParticle(extracted, TileEntityItemInterface.this.connectedRelay.getPos(), info.relayInQuestion.getPos());
                        }
                        return extracted;
                    }
                }
                return StackUtil.getEmpty();
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

        this.lazyHandlers = LazyOptional.of(() -> normalHandler);
        this.itemHandler = new SlotlessableItemHandlerWrapper(this.lazyHandlers, slotlessHandler);
    }

    public TileEntityItemInterface() {
        this(ActuallyTiles.ITEMVIEWER_TILE.get());
    }

    @Override
    public LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        return this.itemHandler.getNormalHandler();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return super.getCapability(capability, side);
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
        if (!this.world.isRemote) {
            CompoundNBT compound = new CompoundNBT();
            stack.write(compound);

            compound.putDouble("InX", input.getX());
            compound.putDouble("InY", input.getY());
            compound.putDouble("InZ", input.getZ());

            compound.putDouble("OutX", output.getX());
            compound.putDouble("OutY", output.getY());
            compound.putDouble("OutZ", output.getZ());

            int rangeSq = 16 * 16;
            for (PlayerEntity player : this.world.getPlayers()) {
                if (player instanceof ServerPlayerEntity) {
                    if (player.getDistanceSq(input.getX(), input.getY(), input.getZ()) <= rangeSq || player.getDistanceSq(output.getX(), output.getY(), output.getZ()) <= rangeSq) {
                        PacketHandler.sendTo(new PacketServerToClient(compound, PacketHandler.LASER_PARTICLE_HANDLER), (ServerPlayerEntity) player);
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
                            for (SlotlessableItemHandlerWrapper handler : info.handlers) {
                                LazyOptional<IItemHandler> normalHandler = handler.getNormalHandler();
                                slotsQueried += normalHandler.map(cap -> {
                                    int queried = 0;
                                    for (int i = 0; i < cap.getSlots(); i++) {
                                        this.itemHandlerInfos.put(queried, new IItemHandlerInfo(cap, i, info.relayInQuestion));
                                        queried++;
                                    }
                                    return queried;
                                }).orElse(0);
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
        if (this.world != null) { //Why is that even possible..?
            for (int i = 0; i <= 5; i++) {
                Direction side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = this.getPos().offset(side);

                if (this.world.isBlockLoaded(pos)) {
                    TileEntity tile = this.world.getTileEntity(pos);

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

    private static class IItemHandlerInfo extends SpecificItemHandlerInfo {

        public final IItemHandler handler;
        public final int switchedIndex;

        public IItemHandlerInfo(IItemHandler handler, int switchedIndex, TileEntityLaserRelayItem relayInQuestion) {
            super(relayInQuestion);
            this.handler = handler;
            this.switchedIndex = switchedIndex;
        }
    }

    private static class SpecificItemHandlerInfo {

        public final TileEntityLaserRelayItem relayInQuestion;

        public SpecificItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion) {
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded() {
            return this.relayInQuestion.hasWorld() && this.relayInQuestion.getWorld().isBlockLoaded(this.relayInQuestion.getPos());
        }
    }

    public static class GenericItemHandlerInfo implements Comparable<GenericItemHandlerInfo> {

        public final List<SlotlessableItemHandlerWrapper> handlers = new ArrayList<>();
        public final TileEntityLaserRelayItem relayInQuestion;

        public GenericItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion) {
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded() {
            return this.relayInQuestion.hasWorld() && this.relayInQuestion.getWorld().isBlockLoaded(this.relayInQuestion.getPos());
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
