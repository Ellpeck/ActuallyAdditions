/*
 * This file ("TileEntityLaserRelayEnergy.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityLaserRelayEnergy extends TileEntityLaserRelay {

    public static final int CAP = 1000;
    public final ConcurrentHashMap<Direction, BlockEntity> receiversAround = new ConcurrentHashMap<>();
    private final IEnergyStorage[] energyStorages = new IEnergyStorage[6];
    private Mode mode = Mode.BOTH;

    public TileEntityLaserRelayEnergy(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, LaserType.ENERGY);

        for (int i = 0; i < this.energyStorages.length; i++) {
            Direction facing = Direction.values()[i];
            this.energyStorages[i] = new IEnergyStorage() {
                @Override
                public int receiveEnergy(int amount, boolean simulate) {
                    return TileEntityLaserRelayEnergy.this.transmitEnergy(facing, amount, simulate);
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate) {
                    return 0;
                }

                @Override
                public int getEnergyStored() {
                    return 0;
                }

                @Override
                public int getMaxEnergyStored() {
                    return TileEntityLaserRelayEnergy.this.getEnergyCap();
                }

                @Override
                public boolean canExtract() {
                    return false;
                }

                @Override
                public boolean canReceive() {
                    return true;
                }
            };
        }
    }

    public TileEntityLaserRelayEnergy(BlockPos pos, BlockState state) {
        this(ActuallyBlocks.LASER_RELAY.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayEnergy tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayEnergy tile) {
            tile.serverTick();
        }
    }

    private int transmitEnergy(Direction from, int maxTransmit, boolean simulate) {
        int transmitted = 0;
        if (maxTransmit > 0 && this.mode != Mode.OUTPUT_ONLY) {
            Network network = this.getNetwork();
            if (network != null) {
                transmitted = this.transferEnergyToReceiverInNeed(from, network, maxTransmit, simulate);
            }
        }
        return transmitted;
    }

    // TODO: [port] this is super hacky, review and fix up 
    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.energyStorages[facing == null
                ? 0
                : facing.ordinal()];
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        Map<Direction, BlockEntity> old = new HashMap<>(this.receiversAround);
        boolean change = false;

        this.receiversAround.clear();
        for (Direction side : Direction.values()) {
            BlockPos pos = this.getBlockPos().relative(side);
            if (this.level.hasChunkAt(pos)) {
                BlockEntity tile = this.level.getBlockEntity(pos);
                if (tile != null && !(tile instanceof TileEntityLaserRelay)) {
                    if (this.level.getCapability(Capabilities.EnergyStorage.BLOCK, tile.getBlockPos(), side.getOpposite()) != null) {
                        this.receiversAround.put(side, tile);

                        BlockEntity oldTile = old.get(side);
                        if (oldTile == null || !tile.equals(oldTile)) {
                            change = true;
                        }
                    }
                }
            }
        }

        if (change || old.size() != this.receiversAround.size()) {
            Network network = this.getNetwork();
            if (network != null) {
                network.changeAmount++;
            }
        }
    }

    private int transferEnergyToReceiverInNeed(Direction from, Network network, int maxTransfer, boolean simulate) {
        int transmitted = 0;
        //Keeps track of all the Laser Relays and Energy Acceptors that have been checked already to make nothing run multiple times
        Set<BlockPos> alreadyChecked = new ObjectOpenHashSet<>();

        Set<TileEntityLaserRelayEnergy> relaysThatWork = new ObjectOpenHashSet<>();
        int totalReceiverAmount = 0;

        for (IConnectionPair pair : network.connections) {
            for (BlockPos relay : pair.getPositions()) {
                if (relay != null && this.level.hasChunkAt(relay) && !alreadyChecked.contains(relay)) {
                    alreadyChecked.add(relay);
                    BlockEntity relayTile = this.level.getBlockEntity(relay);
                    if (relayTile instanceof TileEntityLaserRelayEnergy theRelay) {
	                    if (theRelay.mode != Mode.INPUT_ONLY) {
                            boolean workedOnce = false;

                            for (Direction facing : theRelay.receiversAround.keySet()) {
                                if (theRelay != this || facing != from) {
                                    BlockEntity tile = theRelay.receiversAround.get(facing);

                                    Direction opp = facing.getOpposite();
                                    if (tile != null) {
                                        Boolean received = Optional.ofNullable(this.level.getCapability(Capabilities.EnergyStorage.BLOCK, tile.getBlockPos(), opp)).map(cap -> cap.receiveEnergy(maxTransfer, true) > 0).orElse(false);
                                        if (received) {
                                            totalReceiverAmount++;
                                            workedOnce = true;
                                        }
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

        if (totalReceiverAmount > 0 && !relaysThatWork.isEmpty()) {
            int amountPer = maxTransfer / totalReceiverAmount <= 0
                ? maxTransfer
                : maxTransfer / totalReceiverAmount;

            for (TileEntityLaserRelayEnergy theRelay : relaysThatWork) {
                double highestLoss = Math.max(theRelay.getLossPercentage(), this.getLossPercentage());
                int lowestCap = Math.min(theRelay.getEnergyCap(), this.getEnergyCap());
                for (Map.Entry<Direction, BlockEntity> receiver : theRelay.receiversAround.entrySet()) {
                    if (receiver != null) {
                        Direction side = receiver.getKey();
                        Direction opp = side.getOpposite();
                        BlockEntity tile = receiver.getValue();
                        if (!alreadyChecked.contains(tile.getBlockPos())) {
                            alreadyChecked.add(tile.getBlockPos());
                            if (theRelay != this || side != from) {
                                transmitted += Optional.ofNullable(this.level.getCapability(Capabilities.EnergyStorage.BLOCK, tile.getBlockPos(), opp)).map(cap -> {
                                    int trans = 0;
                                    int theoreticalReceived = cap.receiveEnergy(Math.min(amountPer, lowestCap), true);
                                    if (theoreticalReceived > 0) {
                                        int deduct = this.calcDeduction(theoreticalReceived, highestLoss);
                                        if (deduct >= theoreticalReceived) { //Happens with small numbers
                                            deduct = 0;
                                        }

                                        trans += cap.receiveEnergy(theoreticalReceived - deduct, simulate);
                                        trans += deduct;
                                    }

                                    return trans;
                                }).orElse(0);

                                //If everything that could be transmitted was transmitted
                                if (transmitted >= maxTransfer) {
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

    private int calcDeduction(int theoreticalReceived, double highestLoss) {
        return CommonConfig.Machines.LASER_RELAY_LOSS.get()
            ? Mth.ceil(theoreticalReceived * (highestLoss / 100))
            : 0;
    }

    public int getEnergyCap() {
        return CAP;
    }

    public double getLossPercentage() {
        return 5;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getExtraDisplayString() {
        return Component.translatable("info.actuallyadditions.laserRelay.energy.extra").append(": ").append(Component.translatable(this.mode.name).withStyle(ChatFormatting.DARK_RED));
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

    public enum Mode {
        BOTH("info.actuallyadditions.laserRelay.mode.both"),
        OUTPUT_ONLY("info.actuallyadditions.laserRelay.mode.outputOnly"),
        INPUT_ONLY("info.actuallyadditions.laserRelay.mode.inputOnly");

        public final String name;

        Mode(String name) {
            this.name = name;
        }

        public Mode getNext() {
            int ordinal = this.ordinal() + 1;

            if (ordinal >= values().length) {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }
}
