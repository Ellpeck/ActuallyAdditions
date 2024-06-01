/*
 * This file ("TileEntityLaserRelayFluids.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy.Mode;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityLaserRelayFluids extends TileEntityLaserRelay {

    public final ConcurrentHashMap<Direction, BlockEntity> handlersAround = new ConcurrentHashMap<>();
    private final IFluidHandler[] fluidHandlers = new IFluidHandler[6];
    private Mode mode = Mode.BOTH;

    public TileEntityLaserRelayFluids(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LASER_RELAY_FLUIDS.getTileEntityType(), pos, state, LaserType.FLUID);

        for (int i = 0; i < this.fluidHandlers.length; i++) {
            Direction facing = Direction.values()[i];

            // TODO: [port] this might just not work due to the new contract
            this.fluidHandlers[i] = new IFluidHandler() {
                @Override
                public int getTanks() {
                    return 0;
                }

                @Nonnull
                @Override
                public FluidStack getFluidInTank(int tank) {
                    return FluidStack.EMPTY;
                }

                @Override
                public int getTankCapacity(int tank) {
                    return 0;
                }

                @Override
                public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
                    return false;
                }

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    return TileEntityLaserRelayFluids.this.transmitFluid(facing, resource, action);
                }

                @Nonnull
                @Override
                public FluidStack drain(FluidStack resource, FluidAction action) {
                    return FluidStack.EMPTY;
                }

                @Nonnull
                @Override
                public FluidStack drain(int maxDrain, FluidAction action) {
                    return FluidStack.EMPTY;
                }
            };
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayFluids tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayFluids tile) {
            tile.serverTick();
        }
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.mode == Mode.INPUT_ONLY) {
            for (Direction side : this.handlersAround.keySet()) {
                WorldUtil.doFluidInteraction(this.level, this.handlersAround.get(side).getBlockPos(), this.getBlockPos(), side.getOpposite(), Integer.MAX_VALUE);
            }
        }
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        Map<Direction, BlockEntity> old = new HashMap<>(this.handlersAround);
        boolean change = false;

        this.handlersAround.clear();
        for (Direction side : Direction.values()) {
            BlockPos pos = this.getBlockPos().relative(side);
            if (this.level.hasChunkAt(pos)) {
                BlockEntity tile = this.level.getBlockEntity(pos);
                if (tile != null && !(tile instanceof TileEntityLaserRelay)) {
                    if (this.level.getCapability(Capabilities.FluidHandler.BLOCK, tile.getBlockPos(), side.getOpposite()) != null) {
                        this.handlersAround.put(side, tile);

                        BlockEntity oldTile = old.get(side);
                        if (oldTile == null || !tile.equals(oldTile)) {
                            change = true;
                        }
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

    // TODO: [port] super hacky, find better way of handling this.
    @Override
    public IFluidHandler getFluidHandler(Direction facing) {
        return this.fluidHandlers[facing == null
            ? 0
            : facing.ordinal()];
    }

    private int transmitFluid(Direction from, FluidStack stack, IFluidHandler.FluidAction action) {
        int transmitted = 0;
        if (stack != null && this.mode != Mode.OUTPUT_ONLY) {
            Network network = this.getNetwork();
            if (network != null) {
                transmitted = this.transferFluidToReceiverInNeed(from, network, stack, action);
            }
        }
        return transmitted;
    }

    private int transferFluidToReceiverInNeed(Direction from, Network network, FluidStack stack, IFluidHandler.FluidAction action) {
        int transmitted = 0;
        //Keeps track of all the Laser Relays and Energy Acceptors that have been checked already to make nothing run multiple times
        Set<BlockPos> alreadyChecked = new HashSet<>();

        Set<TileEntityLaserRelayFluids> relaysThatWork = new HashSet<>();
        int totalReceiverAmount = 0;

        for (IConnectionPair pair : network.connections) {
            for (BlockPos relay : pair.getPositions()) {
                if (relay != null && this.level.hasChunkAt(relay) && !alreadyChecked.contains(relay)) {
                    alreadyChecked.add(relay);
                    BlockEntity relayTile = this.level.getBlockEntity(relay);
                    if (relayTile instanceof TileEntityLaserRelayFluids theRelay) {
	                    if (theRelay.mode != Mode.INPUT_ONLY) {
                            boolean workedOnce = false;

                            for (Direction facing : theRelay.handlersAround.keySet()) {
                                if (theRelay != this || facing != from) {
                                    BlockEntity tile = theRelay.handlersAround.get(facing);
                                    Direction opp = facing.getOpposite();

                                    boolean received = Optional.ofNullable(this.level.getCapability(Capabilities.FluidHandler.BLOCK, tile.getBlockPos(), opp))
                                            .map(cap -> cap.fill(stack, IFluidHandler.FluidAction.SIMULATE) > 0).orElse(false);
                                    if (received) {
                                        totalReceiverAmount++;
                                        workedOnce = true;
                                    }
                                }
                            }

                            if (workedOnce) {
                                relaysThatWork.add(theRelay);
                            }
                        }
                    }
                }
            }
        }
        //TODO dont send the entire tank at once, gg
        if (totalReceiverAmount > 0 && !relaysThatWork.isEmpty()) {
            int amountPer = stack.getAmount() / totalReceiverAmount > 0
                ? stack.getAmount() / totalReceiverAmount
                : stack.getAmount();

            for (TileEntityLaserRelayFluids theRelay : relaysThatWork) {
                for (Map.Entry<Direction, BlockEntity> receiver : theRelay.handlersAround.entrySet()) {
                    if (receiver != null) {
                        Direction side = receiver.getKey();
                        Direction opp = side.getOpposite();
                        BlockEntity tile = receiver.getValue();
                        if (!alreadyChecked.contains(tile.getBlockPos())) {
                            alreadyChecked.add(tile.getBlockPos());
                            if (theRelay != this || side != from) {
                                transmitted += Optional.ofNullable(this.level.getCapability(Capabilities.FluidHandler.BLOCK, tile.getBlockPos(), opp))
                                        .map(cap -> {
                                    int trans = 0;
                                    FluidStack copy = stack.copy();
                                    copy.setAmount(amountPer);
                                    trans += cap.fill(copy, action);
                                    return trans;
                                }).orElse(0);

                                //If everything that could be transmitted was transmitted
                                if (transmitted >= stack.getAmount()) {
                                    return transmitted;
                                }
                            }
                        }
                    }
                }
            }
        }

        return transmitted;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getExtraDisplayString() {
        return Component.translatable("info.actuallyadditions.laserRelay.fluid.extra").append(": ").append(Component.translatable(this.mode.name).withStyle(ChatFormatting.DARK_RED));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getCompassDisplayString() {
        return Component.translatable("info.actuallyadditions.laserRelay.energy.display").withStyle(ChatFormatting.GREEN);
    }

    @Override
    public void onCompassAction(Player player) {
        this.mode = this.mode.getNext();
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        if (type != NBTType.SAVE_BLOCK) {
            compound.putString("Mode", this.mode.toString());
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        if (type != NBTType.SAVE_BLOCK) {
            String modeStrg = compound.getString("Mode");
            if (modeStrg != null && !modeStrg.isEmpty()) {
                this.mode = Mode.valueOf(modeStrg);
            }
        }
    }
}
