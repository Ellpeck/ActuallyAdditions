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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaForgeUnitsWrapper;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityBase extends TileEntity implements ITickable{

    public final String name;
    public boolean isRedstonePowered;
    public boolean isPulseMode;
    public boolean stopFromDropping;
    protected int ticksElapsed;
    protected TileEntity[] tilesAround = new TileEntity[6];
    protected boolean hasSavedDataOnChangeOrWorldStart;

    private Object teslaWrapper;

    public TileEntityBase(String name){
        this.name = name;
    }

    public static void init(){
        ModUtil.LOGGER.info("Registering TileEntities...");

        register(TileEntityCompost.class);
        register(TileEntityFeeder.class);
        register(TileEntityGiantChest.class);
        register(TileEntityGiantChestMedium.class);
        register(TileEntityGiantChestLarge.class);
        register(TileEntityGrinder.class);
        register(TileEntityFurnaceDouble.class);
        register(TileEntityInputter.class);
        register(TileEntityFishingNet.class);
        register(TileEntityFurnaceSolar.class);
        register(TileEntityHeatCollector.class);
        register(TileEntityItemRepairer.class);
        register(TileEntityGreenhouseGlass.class);
        register(TileEntityBreaker.class);
        register(TileEntityDropper.class);
        register(TileEntityInputterAdvanced.class);
        register(TileEntityPlacer.class);
        register(TileEntityGrinderDouble.class);
        register(TileEntityCanolaPress.class);
        register(TileEntityFermentingBarrel.class);
        register(TileEntityOilGenerator.class);
        register(TileEntityCoalGenerator.class);
        register(TileEntityPhantomItemface.class);
        register(TileEntityPhantomLiquiface.class);
        register(TileEntityPhantomEnergyface.class);
        register(TileEntityPlayerInterface.class);
        register(TileEntityPhantomPlacer.class);
        register(TileEntityPhantomBreaker.class);
        register(TileEntityFluidCollector.class);
        register(TileEntityFluidPlacer.class);
        register(TileEntityLavaFactoryController.class);
        register(TileEntityCoffeeMachine.class);
        register(TileEntityPhantomBooster.class);
        register(TileEntityEnergizer.class);
        register(TileEntityEnervator.class);
        register(TileEntityXPSolidifier.class);
        register(TileEntitySmileyCloud.class);
        register(TileEntityLeafGenerator.class);
        register(TileEntityDirectionalBreaker.class);
        register(TileEntityRangedCollector.class);
        register(TileEntityAtomicReconstructor.class);
        register(TileEntityMiner.class);
        register(TileEntityFireworkBox.class);
        register(TileEntityPhantomRedstoneface.class);
        register(TileEntityLaserRelayItem.class);
        register(TileEntityLaserRelayEnergy.class);
        register(TileEntityLaserRelayEnergyAdvanced.class);
        register(TileEntityLaserRelayEnergyExtreme.class);
        register(TileEntityLaserRelayItemWhitelist.class);
        register(TileEntityItemViewer.class);
        register(TileEntityDisplayStand.class);
        register(TileEntityShockSuppressor.class);
        register(TileEntityEmpowerer.class);
        register(TileEntityLaserRelayFluids.class);
        register(TileEntityBioReactor.class);
        register(TileEntityFarmer.class);
        register(TileEntityItemViewerHopping.class);
        register(TileEntityBatteryBox.class);
    }

    private static void register(Class<? extends TileEntityBase> tileClass){
        try{
            //This is hacky and dirty but it works so whatever
            String name = ModUtil.MOD_ID+":"+tileClass.newInstance().name;
            GameRegistry.registerTileEntity(tileClass, name);
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Registering a TileEntity failed!", e);
        }
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound compound){
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
        return compound;
    }

    @Override
    public final void readFromNBT(NBTTagCompound compound){
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket(){
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return new SPacketUpdateTileEntity(this.pos, -1, compound);
    }

    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        this.readSyncableNBT(pkt.getNbtCompound(), NBTType.SYNC);
    }

    @Override
    public final NBTTagCompound getUpdateTag(){
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return compound;
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound compound){
        this.readSyncableNBT(compound, NBTType.SYNC);
    }

    public final void sendUpdate(){
        if(this.world != null && !this.world.isRemote){
            NBTTagCompound compound = new NBTTagCompound();
            this.writeSyncableNBT(compound, NBTType.SYNC);

            NBTTagCompound data = new NBTTagCompound();
            data.setTag("Data", compound);
            data.setInteger("X", this.pos.getX());
            data.setInteger("Y", this.pos.getY());
            data.setInteger("Z", this.pos.getZ());
            PacketHandler.theNetwork.sendToAllAround(new PacketServerToClient(data, PacketHandler.TILE_ENTITY_HANDLER), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 64));
        }
    }

    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            super.writeToNBT(compound);
        }

        if(type == NBTType.SAVE_TILE){
            compound.setBoolean("Redstone", this.isRedstonePowered);
            compound.setInteger("TicksElapsed", this.ticksElapsed);
            compound.setBoolean("StopDrop", this.stopFromDropping);
        }
        if(this.isRedstoneToggle() && (type != NBTType.SAVE_BLOCK || this.isPulseMode)){
            compound.setBoolean("IsPulseMode", this.isPulseMode);
        }
    }

    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            super.readFromNBT(compound);
        }

        if(type == NBTType.SAVE_TILE){
            this.isRedstonePowered = compound.getBoolean("Redstone");
            this.ticksElapsed = compound.getInteger("TicksElapsed");
            this.stopFromDropping = compound.getBoolean("StopDrop");
        }
        if(this.isRedstoneToggle()){
            this.isPulseMode = compound.getBoolean("IsPulseMode");
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
    }

    public String getDisplayedName(){
        return StringUtil.localize("container."+ModUtil.MOD_ID+"."+this.name+".name");
    }

    @Override
    public ITextComponent getDisplayName(){
        return new TextComponentString(this.getDisplayedName());
    }

    @Override
    public final void update(){
        this.updateEntity();
    }

    public int getComparatorStrength(){
        return 0;
    }

    public void updateEntity(){
        this.ticksElapsed++;

        if(!this.world.isRemote){
            if(this instanceof ISharingEnergyProvider){
                ISharingEnergyProvider provider = (ISharingEnergyProvider)this;
                if(provider.doesShareEnergy()){
                    int total = provider.getEnergyToSplitShare();
                    if(total > 0){
                        EnumFacing[] sides = provider.getEnergyShareSides();

                        int amount = total/sides.length;
                        if(amount <= 0){
                            amount = total;
                        }

                        for(EnumFacing side : sides){
                            TileEntity tile = this.tilesAround[side.ordinal()];
                            if(tile != null && provider.canShareTo(tile)){
                                WorldUtil.doEnergyInteraction(this, tile, side, amount);
                            }
                        }
                    }
                }
            }

            if(this instanceof ISharingFluidHandler){
                ISharingFluidHandler handler = (ISharingFluidHandler)this;
                if(handler.doesShareFluid()){
                    int total = handler.getMaxFluidAmountToSplitShare();
                    if(total > 0){
                        EnumFacing[] sides = handler.getFluidShareSides();

                        int amount = total/sides.length;
                        if(amount <= 0){
                            amount = total;
                        }

                        for(EnumFacing side : sides){
                            TileEntity tile = this.tilesAround[side.ordinal()];
                            if(tile != null){
                                WorldUtil.doFluidInteraction(this, tile, side, amount);
                            }
                        }
                    }
                }
            }

            if(!this.hasSavedDataOnChangeOrWorldStart){
                if(this.shouldSaveDataOnChangeOrWorldStart()){
                    this.saveDataOnChangeOrWorldStart();
                }

                this.hasSavedDataOnChangeOrWorldStart = true;
            }
        }
    }

    public void saveDataOnChangeOrWorldStart(){
        for(EnumFacing side : EnumFacing.values()){
            BlockPos pos = this.pos.offset(side);
            if(this.world.isBlockLoaded(pos)){
                this.tilesAround[side.ordinal()] = this.world.getTileEntity(pos);
            }
        }
    }

    public boolean shouldSaveDataOnChangeOrWorldStart(){
        return this instanceof ISharingEnergyProvider || this instanceof ISharingFluidHandler;
    }

    public void setRedstonePowered(boolean powered){
        this.isRedstonePowered = powered;
        this.markDirty();
    }

    public boolean canPlayerUse(EntityPlayer player){
        return player.getDistanceSq(this.getPos().getX()+0.5D, this.pos.getY()+0.5D, this.pos.getZ()+0.5D) <= 64 && !this.isInvalid() && this.world.getTileEntity(this.pos) == this;
    }

    protected boolean sendUpdateWithInterval(){
        if(this.ticksElapsed%ConfigIntValues.TILE_ENTITY_UPDATE_INTERVAL.getValue() == 0){
            this.sendUpdate();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            IItemHandler handler = this.getItemHandler(facing);
            if(handler != null){
                return (T)handler;
            }
        }
        else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            IFluidHandler tank = this.getFluidHandler(facing);
            if(tank != null){
                return (T)tank;
            }
        }
        else if(capability == CapabilityEnergy.ENERGY){
            IEnergyStorage storage = this.getEnergyStorage(facing);
            if(storage != null){
                return (T)storage;
            }
        }
        else if(ActuallyAdditions.teslaLoaded){
            if(capability == TeslaUtil.teslaConsumer || capability == TeslaUtil.teslaProducer || capability == TeslaUtil.teslaHolder){
                IEnergyStorage storage = this.getEnergyStorage(facing);
                if(storage != null){
                    if(this.teslaWrapper == null){
                        this.teslaWrapper = new TeslaForgeUnitsWrapper(storage);
                    }
                    return (T)this.teslaWrapper;
                }
            }
        }
        return super.getCapability(capability, facing);
    }

    public IFluidHandler getFluidHandler(EnumFacing facing){
        return null;
    }

    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return null;
    }

    public IItemHandler getItemHandler(EnumFacing facing){
        return null;
    }

    public boolean isRedstoneToggle(){
        return false;
    }

    public void activateOnPulse(){

    }

    public boolean respondsToPulses(){
        return this.isRedstoneToggle() && this.isPulseMode;
    }

    public enum NBTType{
        SAVE_TILE,
        SYNC,
        SAVE_BLOCK
    }
}