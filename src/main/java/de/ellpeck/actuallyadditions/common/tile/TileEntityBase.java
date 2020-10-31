package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.common.util.VanillaPacketDispatcher;
import de.ellpeck.actuallyadditions.common.util.WorldUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityBase<T extends TileEntity> extends TileEntity implements ITickableTileEntity {

    public final String name;
    public boolean isRedstonePowered;
    public boolean isPulseMode;
    public boolean stopFromDropping;
    protected int ticksElapsed;
    protected TileEntity[] tilesAround = new TileEntity[6];
    protected boolean hasSavedDataOnChangeOrWorldStart;

    public TileEntityBase(TileEntityType<T> type, String name) {
        super(type);
        this.name = name;
    }

//    public static void init() {
//        ActuallyAdditions.LOGGER.info("Registering TileEntities...");
//
//        register(TileEntityCompost.class);
//        register(TileEntityFeeder.class);
//        register(TileEntityGiantChest.class);
//        register(TileEntityGiantChestMedium.class);
//        register(TileEntityGiantChestLarge.class);
//        register(TileEntityGrinder.class);
//        register(TileEntityFurnaceDouble.class);
//        register(TileEntityInputter.class);
//        register(TileEntityFishingNet.class);
//        register(TileEntityFurnaceSolar.class);
//        register(TileEntityHeatCollector.class);
//        register(TileEntityItemRepairer.class);
//        register(TileEntityBreaker.class);
//        register(TileEntityDropper.class);
//        register(TileEntityInputterAdvanced.class);
//        register(TileEntityPlacer.class);
//        register(TileEntityGrinderDouble.class);
//        register(TileEntityCanolaPress.class);
//        register(TileEntityFermentingBarrel.class);
//        register(TileEntityOilGenerator.class);
//        register(TileEntityCoalGenerator.class);
//        register(TileEntityPhantomItemface.class);
//        register(TileEntityPhantomLiquiface.class);
//        register(TileEntityPhantomEnergyface.class);
//        register(TileEntityPlayerInterface.class);
//        register(TileEntityPhantomPlacer.class);
//        register(TileEntityPhantomBreaker.class);
//        register(TileEntityFluidCollector.class);
//        register(TileEntityFluidPlacer.class);
//        register(TileEntityLavaFactoryController.class);
//        register(TileEntityCoffeeMachine.class);
//        register(TileEntityPhantomBooster.class);
//        register(TileEntityEnergizer.class);
//        register(TileEntityEnervator.class);
//        register(TileEntityXPSolidifier.class);
//        register(TileEntitySmileyCloud.class);
//        register(TileEntityLeafGenerator.class);
//        register(TileEntityDirectionalBreaker.class);
//        register(TileEntityRangedCollector.class);
//        register(TileEntityAtomicReconstructor.class);
//        register(TileEntityMiner.class);
//        register(TileEntityFireworkBox.class);
//        register(TileEntityPhantomRedstoneface.class);
//        register(TileEntityLaserRelayItem.class);
//        register(TileEntityLaserRelayEnergy.class);
//        register(TileEntityLaserRelayEnergyAdvanced.class);
//        register(TileEntityLaserRelayEnergyExtreme.class);
//        register(TileEntityLaserRelayItemWhitelist.class);
//        register(TileEntityItemViewer.class);
//        register(TileEntityDisplayStand.class);
//        register(TileEntityShockSuppressor.class);
//        register(TileEntityEmpowerer.class);
//        register(TileEntityLaserRelayFluids.class);
//        register(TileEntityBioReactor.class);
//        register(TileEntityFarmer.class);
//        register(TileEntityItemViewerHopping.class);
//        register(TileEntityBatteryBox.class);
//    }

//    private static void register(Class<? extends TileEntityBase> tileClass) {
//        try {
//            //This is hacky and dirty but it works so whatever
//            ResourceLocation name = new ResourceLocation(ActuallyAdditions.MODID, tileClass.newInstance().name);
//            GameRegistry.registerTileEntity(tileClass, name);
//        } catch (Exception e) {
//            ActuallyAdditions.LOGGER.fatal("Registering a TileEntity failed!", e);
//        }
//    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compound = new CompoundNBT();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return new SUpdateTileEntityPacket(this.pos, -1, compound);
    }

    @Override
    public final void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.readSyncableNBT(pkt.getNbtCompound(), NBTType.SYNC);
    }

    @Override
    public final CompoundNBT getUpdateTag() {
        CompoundNBT compound = new CompoundNBT();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return compound;
    }

    @Override
    public final void handleUpdateTag(CompoundNBT compound) {
        this.readSyncableNBT(compound, NBTType.SYNC);
    }

    public final void sendUpdate() {
        if (this.world != null && !this.world.isRemote) VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        /*
        todo: this wan't me
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

    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) super.write(compound);

        if (type == NBTType.SAVE_TILE) {
            compound.putBoolean("Redstone", this.isRedstonePowered);
            compound.putInt("TicksElapsed", this.ticksElapsed);
            compound.putBoolean("StopDrop", this.stopFromDropping);
        } else if (type == NBTType.SYNC && this.stopFromDropping) compound.putBoolean("StopDrop", this.stopFromDropping);

        if (this.isRedstoneToggle() && (type != NBTType.SAVE_BLOCK || this.isPulseMode)) {
            compound.putBoolean("IsPulseMode", this.isPulseMode);
        }
    }

    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) super.read(compound);

        if (type == NBTType.SAVE_TILE) {
            this.isRedstonePowered = compound.getBoolean("Redstone");
            this.ticksElapsed = compound.getInt("TicksElapsed");
            this.stopFromDropping = compound.getBoolean("StopDrop");
        } else if (type == NBTType.SYNC) this.stopFromDropping = compound.getBoolean("StopDrop");

        if (this.isRedstoneToggle()) {
            this.isPulseMode = compound.getBoolean("IsPulseMode");
        }
    }

// todo: re-eval
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
//        return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
//    }

    public String getNameForTranslation() {
        return "container." + ActuallyAdditions.MODID + "." + this.name + ".name";
    }

    @Override
    public void tick() {
        this.updateEntity();
    }

    public int getComparatorStrength() {
        return 0;
    }

    private boolean shareEnergy = this instanceof ISharingEnergyProvider;
    private boolean shareFluid = this instanceof ISharingFluidHandler;

    public void updateEntity() {
        this.ticksElapsed++;

        if (!this.world.isRemote) {
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
                            TileEntity tile = this.tilesAround[side.ordinal()];
                            if (tile != null && provider.canShareTo(tile)) {
                                WorldUtil.doEnergyInteraction(this, tile, side, amount);
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
                            TileEntity tile = this.tilesAround[side.ordinal()];
                            if (tile != null) {
                                WorldUtil.doFluidInteraction(this, tile, side, amount);
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
    }

    public void saveDataOnChangeOrWorldStart() {
        for (Direction side : Direction.values()) {
            BlockPos pos = this.pos.offset(side);
            if (this.world.isBlockLoaded(pos)) {
                this.tilesAround[side.ordinal()] = this.world.getTileEntity(pos);
            }
        }
    }

    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return this instanceof ISharingEnergyProvider || this instanceof ISharingFluidHandler;
    }

    public void setRedstonePowered(boolean powered) {
        this.isRedstonePowered = powered;
        this.markDirty();
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return player.getDistanceSq(this.getPos().getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64 && !this.isRemoved() && this.world.getTileEntity(this.pos) == this;
    }

    protected boolean sendUpdateWithInterval() {
        if (this.ticksElapsed % ConfigIntValues.TILE_ENTITY_UPDATE_INTERVAL.getValue() == 0) {
            this.sendUpdate();
            return true;
        } else {
            return false;
        }
    }

    @Nonnull
    @Override
    public <CAP> LazyOptional<CAP> getCapability(@Nonnull Capability<CAP> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.getItemHandler(side).cast();
        }

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return this.getFluidHandler(side).cast();
        }

        if (cap == CapabilityEnergy.ENERGY) {
            return this.getEnergyStorage(side).cast();
        }

        return super.getCapability(cap, side);
    }

    public LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        return null;
    }

    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return null;
    }

    public LazyOptional<IItemHandler> getItemHandler(Direction facing) {
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