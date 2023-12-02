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
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityLaserRelayFluids extends TileEntityLaserRelay {

    public final ConcurrentHashMap<Direction, TileEntity> handlersAround = new ConcurrentHashMap<>();
    private final IFluidHandler[] fluidHandlers = new IFluidHandler[6];
    private Mode mode = Mode.BOTH;

    public TileEntityLaserRelayFluids() {
        super(ActuallyBlocks.LASER_RELAY_FLUIDS.getTileEntityType(), LaserType.FLUID);

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

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.level.isClientSide) {
            if (this.mode == Mode.INPUT_ONLY) {
                for (Direction side : this.handlersAround.keySet()) {
                    WorldUtil.doFluidInteraction(this.handlersAround.get(side), this, side.getOpposite(), Integer.MAX_VALUE);
                }
            }
        }
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart() {
        Map<Direction, TileEntity> old = new HashMap<>(this.handlersAround);
        boolean change = false;

        this.handlersAround.clear();
        for (Direction side : Direction.values()) {
            BlockPos pos = this.getBlockPos().relative(side);
            if (this.level.hasChunkAt(pos)) {
                TileEntity tile = this.level.getBlockEntity(pos);
                if (tile != null && !(tile instanceof TileEntityLaserRelay)) {
                    if (tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()).isPresent()) {
                        this.handlersAround.put(side, tile);

                        TileEntity oldTile = old.get(side);
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
    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return LazyOptional.of(() -> this.fluidHandlers[facing == null
            ? 0
            : facing.ordinal()]);
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
                    TileEntity relayTile = this.level.getBlockEntity(relay);
                    if (relayTile instanceof TileEntityLaserRelayFluids) {
                        TileEntityLaserRelayFluids theRelay = (TileEntityLaserRelayFluids) relayTile;
                        if (theRelay.mode != Mode.INPUT_ONLY) {
                            boolean workedOnce = false;

                            for (Direction facing : theRelay.handlersAround.keySet()) {
                                if (theRelay != this || facing != from) {
                                    TileEntity tile = theRelay.handlersAround.get(facing);
                                    Direction opp = facing.getOpposite();

                                    boolean received = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, opp).map(cap -> cap.fill(stack, IFluidHandler.FluidAction.SIMULATE) > 0).orElse(false);
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
            int amountPer = stack.getAmount() / totalReceiverAmount <= 0
                ? stack.getAmount() / totalReceiverAmount
                : stack.getAmount();

            for (TileEntityLaserRelayFluids theRelay : relaysThatWork) {
                for (Map.Entry<Direction, TileEntity> receiver : theRelay.handlersAround.entrySet()) {
                    if (receiver != null) {
                        Direction side = receiver.getKey();
                        Direction opp = side.getOpposite();
                        TileEntity tile = receiver.getValue();
                        if (!alreadyChecked.contains(tile.getBlockPos())) {
                            alreadyChecked.add(tile.getBlockPos());
                            if (theRelay != this || side != from) {
                                transmitted += tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, opp).map(cap -> {
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
    public String getExtraDisplayString() {
        return I18n.get("info." + ActuallyAdditions.MODID + ".laserRelay.fluid.extra") + ": " + TextFormatting.DARK_RED + I18n.get(this.mode.name) + TextFormatting.RESET;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getCompassDisplayString() {
        return TextFormatting.GREEN + I18n.get("info." + ActuallyAdditions.MODID + ".laserRelay.energy.display");
    }

    @Override
    public void onCompassAction(PlayerEntity player) {
        this.mode = this.mode.getNext();
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        if (type != NBTType.SAVE_BLOCK) {
            compound.putString("Mode", this.mode.toString());
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        if (type != NBTType.SAVE_BLOCK) {
            String modeStrg = compound.getString("Mode");
            if (modeStrg != null && !modeStrg.isEmpty()) {
                this.mode = Mode.valueOf(modeStrg);
            }
        }
    }
}
