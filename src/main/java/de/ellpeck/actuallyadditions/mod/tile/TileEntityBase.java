/*
 * This file ("TileEntityBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TileEntityBase extends TileEntity implements ITickable{

    public static boolean teslaLoaded;
    public final String name;
    public boolean isRedstonePowered;
    public boolean isPulseMode;
    protected int ticksElapsed;

    public TileEntityBase(String name){
        this.name = name;
    }

    public static void init(){
        ModUtil.LOGGER.info("Registering TileEntities...");

        register(TileEntityCompost.class, "Compost");
        register(TileEntityFeeder.class, "Feeder");
        register(TileEntityGiantChest.class, "GiantChest");
        register(TileEntityGiantChestMedium.class, "GiantChestMedium");
        register(TileEntityGiantChestLarge.class, "GiantChestLarge");
        register(TileEntityGrinder.class, "Grinder");
        register(TileEntityFurnaceDouble.class, "FurnaceDouble");
        register(TileEntityInputter.class, "Inputter");
        register(TileEntityFishingNet.class, "FishingNet");
        register(TileEntityFurnaceSolar.class, "FurnaceSolar");
        register(TileEntityHeatCollector.class, "HeatCollector");
        register(TileEntityItemRepairer.class, "Repairer");
        register(TileEntityGreenhouseGlass.class, "GreenhouseGlass");
        register(TileEntityBreaker.class, "Breaker");
        register(TileEntityDropper.class, "Dropper");
        register(TileEntityInputterAdvanced.class, "InputterAdvanced");
        register(TileEntityPlacer.class, "Placer");
        register(TileEntityGrinderDouble.class, "GrinderDouble");
        register(TileEntityCanolaPress.class, "CanolaPress");
        register(TileEntityFermentingBarrel.class, "FermentingBarrel");
        register(TileEntityOilGenerator.class, "OilGenerator");
        register(TileEntityCoalGenerator.class, "CoalGenerator");
        register(TileEntityPhantomItemface.class, "PhantomItemface");
        register(TileEntityPhantomLiquiface.class, "PhantomLiquiface");
        register(TileEntityPhantomEnergyface.class, "PhantomEnergyface");
        register(TileEntityPlayerInterface.class, "PlayerInterface");
        register(TileEntityPhantomPlacer.class, "PhantomPlacer");
        register(TileEntityPhantomBreaker.class, "PhantomBreaker");
        register(TileEntityFluidCollector.class, "FluidCollector");
        register(TileEntityFluidPlacer.class, "FluidPlacer");
        register(TileEntityLavaFactoryController.class, "LavaFactoryController");
        register(TileEntityCoffeeMachine.class, "CoffeeMachine");
        register(TileEntityPhantomBooster.class, "PhantomBooster");
        register(TileEntityEnergizer.class, "Energizer");
        register(TileEntityEnervator.class, "Enervator");
        register(TileEntityXPSolidifier.class, "XPSolidifier");
        register(TileEntitySmileyCloud.class, "Cloud");
        register(TileEntityLeafGenerator.class, "LeafGenerator");
        register(TileEntityDirectionalBreaker.class, "DirectionalBreaker");
        register(TileEntityRangedCollector.class, "RangedCollector");
        register(TileEntityAtomicReconstructor.class, "AtomicReconstructor");
        register(TileEntityMiner.class, "Miner");
        register(TileEntityFireworkBox.class, "FireworkBox");
        register(TileEntityPhantomRedstoneface.class, "PhantomRedstoneface");
        register(TileEntityLaserRelayItem.class, "LaserRelayItem");
        register(TileEntityLaserRelayEnergy.class, "LaserRelay");
        register(TileEntityLaserRelayEnergyAdvanced.class);
        register(TileEntityLaserRelayEnergyExtreme.class);
        register(TileEntityLaserRelayItemWhitelist.class, "LaserRelayItemWhitelist");
        register(TileEntityItemViewer.class, "ItemViewer");
        register(TileEntityBookletStand.class, "BookletStand");
        register(TileEntityDisplayStand.class, "DisplayStand");
        register(TileEntityShockSuppressor.class, "ShockSuppressor");

        if(Loader.isModLoaded("Tesla")){
            ModUtil.LOGGER.info("Tesla loaded... Activating Tesla Power System integration...");
            teslaLoaded = true;
        }
        else{
            ModUtil.LOGGER.info("Tesla not found! Skipping Tesla Power System integration.");
        }
    }

    private static void register(Class<? extends TileEntityBase> tileClass, String legacyName){
        try{
            //This is hacky and dirty but it works so whatever
            String name = ModUtil.MOD_ID+":"+tileClass.newInstance().name;

            if(legacyName != null && !legacyName.isEmpty()){
                String oldName = ModUtil.MOD_ID+":tileEntity"+legacyName;
                GameRegistry.registerTileEntityWithAlternatives(tileClass, name, oldName);
            }
            else{
                GameRegistry.registerTileEntity(tileClass, name);
            }
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Registering a TileEntity failed!", e);
        }
    }

    private static void register(Class<? extends TileEntityBase> tileClass){
        register(tileClass, null);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound = super.writeToNBT(compound);
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
        return compound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket(){
        NBTTagCompound compound = this.getUpdateTag();
        if(compound != null){
            return new SPacketUpdateTileEntity(this.pos, 0, compound);
        }
        else{
            return null;
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        if(pkt != null){
            this.receiveSyncCompound(pkt.getNbtCompound());
        }
    }

    public void receiveSyncCompound(NBTTagCompound compound){
        this.readSyncableNBT(compound, NBTType.SYNC);
    }

    @Override
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound tag = super.getUpdateTag();
        this.writeSyncableNBT(tag, NBTType.SYNC);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound){
        super.handleUpdateTag(compound);
        this.receiveSyncCompound(compound);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
    }

    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type == NBTType.SAVE_TILE){
            compound.setBoolean("Redstone", this.isRedstonePowered);
            compound.setInteger("TicksElapsed", this.ticksElapsed);
        }
        if(this.isRedstoneToggle() && (type != NBTType.SAVE_BLOCK || this.isPulseMode)){
            compound.setBoolean("IsPulseMode", this.isPulseMode);
        }
    }

    @Override
    public ITextComponent getDisplayName(){
        return new TextComponentTranslation("container."+ModUtil.MOD_ID+"."+this.name+".name");
    }

    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type == NBTType.SAVE_TILE){
            this.isRedstonePowered = compound.getBoolean("Redstone");
            this.ticksElapsed = compound.getInteger("TicksElapsed");
        }
        if(this.isRedstoneToggle()){
            this.isPulseMode = compound.getBoolean("IsPulseMode");
        }
    }

    @Override
    public void update(){
        this.updateEntity();
    }

    public void updateEntity(){
        this.ticksElapsed++;

        if(!this.worldObj.isRemote){
            if(this instanceof IEnergyReceiver || this instanceof IEnergyProvider){
                WorldUtil.doEnergyInteraction(this);
            }

            if(this instanceof net.minecraftforge.fluids.IFluidHandler || this.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)){
                WorldUtil.doFluidInteraction(this);
            }
        }
    }

    public void setRedstonePowered(boolean powered){
        this.isRedstonePowered = powered;
        this.markDirty();
    }

    public boolean canPlayerUse(EntityPlayer player){
        return player.getDistanceSq(this.getPos().getX()+0.5D, this.pos.getY()+0.5D, this.pos.getZ()+0.5D) <= 64 && !this.isInvalid() && this.worldObj.getTileEntity(this.pos) == this;
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

    public void sendUpdate(){
        if(!this.worldObj.isRemote){
            NBTTagCompound compound = this.getUpdateTag();
            if(compound != null){
                NBTTagCompound data = new NBTTagCompound();
                data.setTag("Data", compound);
                data.setInteger("X", this.pos.getX());
                data.setInteger("Y", this.pos.getY());
                data.setInteger("Z", this.pos.getZ());
                PacketHandler.theNetwork.sendToAllAround(new PacketServerToClient(data, PacketHandler.TILE_ENTITY_HANDLER), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 128));
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            IFluidHandler tank = this.getFluidHandler(facing);
            if(tank != null){
                return (T)tank;
            }
        }
        else if(teslaLoaded){
            T cap = TeslaUtil.wrapTeslaToRF(this, capability, facing);
            if(cap != null){
                return cap;
            }
        }
        return super.getCapability(capability, facing);
    }

    public IFluidHandler getFluidHandler(EnumFacing facing){
        return null;
    }

    public boolean isRedstoneToggle(){
        return false;
    }

    public void activateOnPulse(){

    }

    public enum NBTType{
        SAVE_TILE,
        SYNC,
        SAVE_BLOCK
    }
}