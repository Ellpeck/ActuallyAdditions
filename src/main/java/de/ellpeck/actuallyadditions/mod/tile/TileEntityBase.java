/*
 * This file ("TileEntityBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.VanillaPacketDispatcher;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends BlockEntity {

    public boolean isRedstonePowered;
    public boolean isPulseMode;
    public boolean stopFromDropping;
    protected int ticksElapsed;
    protected BlockEntity[] tilesAround = new BlockEntity[6];
    protected boolean hasSavedDataOnChangeOrWorldStart;

    public TileEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    // TODO: [port] remove if the above is correct
    //    @Override
    //    public final CompoundNBT writeToNBT(CompoundNBT compound) {
    //        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
    //        return compound;
    //    }

    @Override
    public void load(CompoundTag compound) {
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }


    // TODO: [port] remove if the above is correct
    //    @Override
    //    public final void readFromNBT(CompoundNBT compound) {
    //        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    //    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag compound = new CompoundTag();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt.getTag() != null) //TODO: pkt.getTag() is nullable. Hopping Item Interface will throw in the log when placed because of this
            this.readSyncableNBT(pkt.getTag(), NBTType.SYNC);
    }

    @Override
    public final CompoundTag getUpdateTag() {
        CompoundTag compound = new CompoundTag();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return compound;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.readSyncableNBT(tag, NBTType.SYNC);
    }

    public final void sendUpdate() {
        if (this.level != null && !this.level.isClientSide) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }
        /*
        if(this.world != null && !this.world.isRemote){
            CompoundNBT compound = new CompoundNBT();
            this.writeSyncableNBT(compound, NBTType.SYNC);

            CompoundNBT data = new CompoundNBT();
            data.setTag("Data", compound);
            data.setInteger("X", this.pos.getX());
            data.setInteger("Y", this.pos.getY());
            data.setInteger("Z", this.pos.getZ());
            PacketHandler.theNetwork.sendToAllTracking(new PacketServerToClient(data, PacketHandler.TILE_ENTITY_HANDLER), new TargetPoint(this.world.provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 0));
        }*/
    }

    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            super.saveAdditional(compound);
        }

        if (type == NBTType.SAVE_TILE) {
            compound.putBoolean("Redstone", this.isRedstonePowered);
            compound.putInt("TicksElapsed", this.ticksElapsed);
            compound.putBoolean("StopDrop", this.stopFromDropping);
        } else if (type == NBTType.SYNC && this.stopFromDropping) {
            compound.putBoolean("StopDrop", this.stopFromDropping);
        }

        if (this.isRedstoneToggle() && (type != NBTType.SAVE_BLOCK || this.isPulseMode)) {
            compound.putBoolean("IsPulseMode", this.isPulseMode);
        }
    }

    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            super.load(compound); // FIXME: [port] flag as possible crash source
        }

        if (type == NBTType.SAVE_TILE) {
            this.isRedstonePowered = compound.getBoolean("Redstone");
            this.ticksElapsed = compound.getInt("TicksElapsed");
            this.stopFromDropping = compound.getBoolean("StopDrop");
        } else if (type == NBTType.SYNC) {
            this.stopFromDropping = compound.getBoolean("StopDrop");
        }

        if (this.isRedstoneToggle() && compound.contains("IsPulseMode")) {
            this.isPulseMode = compound.getBoolean("IsPulseMode");
        }
    }

    // TODO: [port] eval if still required in some way
    //    @Override
    //    public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
    //        return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
    //    }

    @Deprecated
    public String getNameForTranslation() {
        return "removeme";// "container.actuallyadditions." + this.name + ".name";
    }

    //    @Override
    //    public ITextComponent getDisplayName() {
    //        return new TranslationTextComponent(this.getNameForTranslation());
    //    }

    public int getComparatorStrength() {
        return 0;
    }

    private boolean shareEnergy = this instanceof ISharingEnergyProvider;
    private boolean shareFluid = this instanceof ISharingFluidHandler;

    protected void clientTick() {
        this.ticksElapsed++;
    }

    protected void serverTick() {
        this.ticksElapsed++;

        if (this.shareEnergy) {
            ISharingEnergyProvider provider = (ISharingEnergyProvider) this;
            if (provider.doesShareEnergy()) {
                int total = provider.getEnergyToSplitShare();
                if (total > 0) {
                    Direction[] sides = provider.getEnergyShareSides();

                    int amount = total / sides.length;
                    if (amount <= 0) {
                        amount = total;
                    }

                    for (Direction side : sides) {
                        BlockEntity tile = this.tilesAround[side.ordinal()];
                        if (tile != null && provider.canShareTo(tile)) {
                            WorldUtil.doEnergyInteraction(this.level, this.getBlockPos(), tile.getBlockPos(), side, amount);
                        }
                    }
                }
            }
        }

        if (this.shareFluid) {
            ISharingFluidHandler handler = (ISharingFluidHandler) this;
            if (handler.doesShareFluid()) {
                int total = handler.getMaxFluidAmountToSplitShare();
                if (total > 0) {
                    Direction[] sides = handler.getFluidShareSides();

                    int amount = total / sides.length;
                    if (amount <= 0) {
                        amount = total;
                    }

                    for (Direction side : sides) {
                        BlockEntity tile = this.tilesAround[side.ordinal()];
                        if (tile != null) {
                            WorldUtil.doFluidInteraction(this.level, this.getBlockPos(), tile.getBlockPos(), side, amount);
                        }
                    }
                }
            }
        }

        if (!this.hasSavedDataOnChangeOrWorldStart) {
            if (this.shouldSaveDataOnChangeOrWorldStart()) {
                this.saveDataOnChangeOrWorldStart();
            }

            this.hasSavedDataOnChangeOrWorldStart = true;
        }
    }

    public void saveDataOnChangeOrWorldStart() {
        for (Direction side : Direction.values()) {
            BlockPos pos = this.worldPosition.relative(side);
            if (this.level.hasChunkAt(pos)) {
                this.tilesAround[side.ordinal()] = this.level.getBlockEntity(pos);
            }
        }
    }

    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return this instanceof ISharingEnergyProvider || this instanceof ISharingFluidHandler;
    }

    public void setRedstonePowered(boolean powered) {
        this.isRedstonePowered = powered;
        this.setChanged();
    }

    public boolean canPlayerUse(Player player) {
        return player.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) <= 64 && !this.isRemoved() && this.level.getBlockEntity(this.worldPosition) == this;
    }

    protected boolean sendUpdateWithInterval() {
        if (this.ticksElapsed % 5 == 0) { //TODO was a config TILE_ENTITY_UPDATE_INTERVAL
            this.sendUpdate();
            return true;
        } else {
            return false;
        }
    }

    public IFluidHandler getFluidHandler(Direction facing) {
        return null;
    }

    public IEnergyStorage getEnergyStorage(Direction facing) {
        return null;
    }

    public IItemHandler getItemHandler(Direction facing) {
        return null;
    }

    public boolean isRedstoneToggle() {
        return false;
    }

    public void activateOnPulse() {

    }

    public boolean respondsToPulses() {
        return this.isRedstoneToggle() && this.isPulseMode;
    }

    public enum NBTType {
        /**
         * Use when normal writeToNBT/readToNBT is expected.
         */
        SAVE_TILE,
        /**
         * Use when data needs to be sent to the client.
         */
        SYNC,
        /**
         * Wat
         */
        SAVE_BLOCK
    }
}
