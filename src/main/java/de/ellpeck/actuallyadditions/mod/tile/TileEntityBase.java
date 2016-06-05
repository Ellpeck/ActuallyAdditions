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

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketUpdateTileEntity;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TileEntityBase extends TileEntity implements ITickable{

    public final String name;
    public boolean isRedstonePowered;
    protected int ticksElapsed;

    public TileEntityBase(String name){
        this.name = "container."+ModUtil.MOD_ID+"."+name;
    }

    //TODO Change for next major update to use the name variable automatically
    public static void init(){
        ModUtil.LOGGER.info("Registering TileEntities...");

        GameRegistry.registerTileEntity(TileEntityCompost.class, ModUtil.MOD_ID+":tileEntityCompost");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, ModUtil.MOD_ID+":tileEntityFeeder");
        GameRegistry.registerTileEntity(TileEntityGiantChest.class, ModUtil.MOD_ID+":tileEntityGiantChest");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, ModUtil.MOD_ID+":tileEntityGrinder");
        GameRegistry.registerTileEntity(TileEntityFurnaceDouble.class, ModUtil.MOD_ID+":tileEntityFurnaceDouble");
        GameRegistry.registerTileEntity(TileEntityInputter.class, ModUtil.MOD_ID+":tileEntityInputter");
        GameRegistry.registerTileEntity(TileEntityFishingNet.class, ModUtil.MOD_ID+":tileEntityFishingNet");
        GameRegistry.registerTileEntity(TileEntityFurnaceSolar.class, ModUtil.MOD_ID+":tileEntityFurnaceSolar");
        GameRegistry.registerTileEntity(TileEntityHeatCollector.class, ModUtil.MOD_ID+":tileEntityHeatCollector");
        GameRegistry.registerTileEntity(TileEntityItemRepairer.class, ModUtil.MOD_ID+":tileEntityRepairer");
        GameRegistry.registerTileEntity(TileEntityGreenhouseGlass.class, ModUtil.MOD_ID+":tileEntityGreenhouseGlass");
        GameRegistry.registerTileEntity(TileEntityBreaker.class, ModUtil.MOD_ID+":tileEntityBreaker");
        GameRegistry.registerTileEntity(TileEntityDropper.class, ModUtil.MOD_ID+":tileEntityDropper");
        GameRegistry.registerTileEntity(TileEntityInputterAdvanced.class, ModUtil.MOD_ID+":tileEntityInputterAdvanced");
        GameRegistry.registerTileEntity(TileEntityPlacer.class, ModUtil.MOD_ID+":tileEntityPlacer");
        GameRegistry.registerTileEntity(TileEntityGrinderDouble.class, ModUtil.MOD_ID+":tileEntityGrinderDouble");
        GameRegistry.registerTileEntity(TileEntityCanolaPress.class, ModUtil.MOD_ID+":tileEntityCanolaPress");
        GameRegistry.registerTileEntity(TileEntityFermentingBarrel.class, ModUtil.MOD_ID+":tileEntityFermentingBarrel");
        GameRegistry.registerTileEntity(TileEntityOilGenerator.class, ModUtil.MOD_ID+":tileEntityOilGenerator");
        GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, ModUtil.MOD_ID+":tileEntityCoalGenerator");
        GameRegistry.registerTileEntity(TileEntityPhantomItemface.class, ModUtil.MOD_ID+":tileEntityPhantomItemface");
        GameRegistry.registerTileEntity(TileEntityPhantomLiquiface.class, ModUtil.MOD_ID+":tileEntityPhantomLiquiface");
        GameRegistry.registerTileEntity(TileEntityPhantomEnergyface.class, ModUtil.MOD_ID+":tileEntityPhantomEnergyface");
        GameRegistry.registerTileEntity(TileEntityPlayerInterface.class, ModUtil.MOD_ID+":tileEntityPlayerInterface");
        GameRegistry.registerTileEntity(TileEntityPhantomPlacer.class, ModUtil.MOD_ID+":tileEntityPhantomPlacer");
        GameRegistry.registerTileEntity(TileEntityPhantomBreaker.class, ModUtil.MOD_ID+":tileEntityPhantomBreaker");
        GameRegistry.registerTileEntity(TileEntityFluidCollector.class, ModUtil.MOD_ID+":tileEntityFluidCollector");
        GameRegistry.registerTileEntity(TileEntityFluidPlacer.class, ModUtil.MOD_ID+":tileEntityFluidPlacer");
        GameRegistry.registerTileEntity(TileEntityLavaFactoryController.class, ModUtil.MOD_ID+":tileEntityLavaFactoryController");
        GameRegistry.registerTileEntity(TileEntityCoffeeMachine.class, ModUtil.MOD_ID+":tileEntityCoffeeMachine");
        GameRegistry.registerTileEntity(TileEntityPhantomBooster.class, ModUtil.MOD_ID+":tileEntityPhantomBooster");
        GameRegistry.registerTileEntity(TileEntityEnergizer.class, ModUtil.MOD_ID+":tileEntityEnergizer");
        GameRegistry.registerTileEntity(TileEntityEnervator.class, ModUtil.MOD_ID+":tileEntityEnervator");
        GameRegistry.registerTileEntity(TileEntityXPSolidifier.class, ModUtil.MOD_ID+":tileEntityXPSolidifier");
        GameRegistry.registerTileEntity(TileEntitySmileyCloud.class, ModUtil.MOD_ID+":tileEntityCloud");
        GameRegistry.registerTileEntity(TileEntityLeafGenerator.class, ModUtil.MOD_ID+":tileEntityLeafGenerator");
        GameRegistry.registerTileEntity(TileEntityDirectionalBreaker.class, ModUtil.MOD_ID+":tileEntityDirectionalBreaker");
        GameRegistry.registerTileEntity(TileEntityRangedCollector.class, ModUtil.MOD_ID+":tileEntityRangedCollector");
        GameRegistry.registerTileEntity(TileEntityAtomicReconstructor.class, ModUtil.MOD_ID+":tileEntityAtomicReconstructor");
        GameRegistry.registerTileEntity(TileEntityMiner.class, ModUtil.MOD_ID+":tileEntityMiner");
        GameRegistry.registerTileEntity(TileEntityFireworkBox.class, ModUtil.MOD_ID+":tileEntityFireworkBox");
        GameRegistry.registerTileEntity(TileEntityPhantomRedstoneface.class, ModUtil.MOD_ID+":tileEntityPhantomRedstoneface");
        GameRegistry.registerTileEntity(TileEntityLaserRelayItem.class, ModUtil.MOD_ID+":tileEntityLaserRelayItem");
        GameRegistry.registerTileEntity(TileEntityLaserRelayEnergy.class, ModUtil.MOD_ID+":tileEntityLaserRelay");
        GameRegistry.registerTileEntity(TileEntityLaserRelayItemWhitelist.class, ModUtil.MOD_ID+":tileEntityLaserRelayItemWhitelist");
        GameRegistry.registerTileEntity(TileEntityItemViewer.class, ModUtil.MOD_ID+":tileItemViewer");
        GameRegistry.registerTileEntity(TileEntityBookletStand.class, ModUtil.MOD_ID+":tileEntityBookletStand");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.readSyncableNBT(compound, false);
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound = super.writeToNBT(compound);
        this.writeSyncableNBT(compound, false);
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

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return !(oldState.getBlock().isAssociatedBlock(newState.getBlock()));
    }

    public void receiveSyncCompound(NBTTagCompound compound){
        this.readSyncableNBT(compound, true);
    }

    @Override
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound tag = super.getUpdateTag();
        this.writeSyncableNBT(tag, true);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound){
        this.receiveSyncCompound(compound);
    }

    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        if(!isForSync){
            compound.setBoolean("Redstone", this.isRedstonePowered);
        }
        if(this instanceof IRedstoneToggle){
            compound.setBoolean("IsPulseMode", ((IRedstoneToggle)this).isPulseMode());
        }
    }

    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        if(!isForSync){
            this.isRedstonePowered = compound.getBoolean("Redstone");
        }
        if(this instanceof IRedstoneToggle){
            ((IRedstoneToggle)this).toggle(compound.getBoolean("IsPulseMode"));
        }
    }

    @Override
    public void update(){
        this.updateEntity();
    }

    public void updateEntity(){
        this.ticksElapsed++;
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
                PacketHandler.theNetwork.sendToAllAround(new PacketUpdateTileEntity(compound, this.getPos()), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 64));
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
        return super.getCapability(capability, facing);
    }

    public IFluidHandler getFluidHandler(EnumFacing facing){
        return null;
    }
}