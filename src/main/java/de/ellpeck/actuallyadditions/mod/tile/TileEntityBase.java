/*
 * This file ("TileEntityBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.network.VanillaPacketSyncer;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TileEntityBase extends TileEntity implements ITickable{

    protected int ticksElapsed;
    public boolean isRedstonePowered;

    public static void init(){
        ModUtil.LOGGER.info("Registering TileEntities...");

        GameRegistry.registerTileEntity(TileEntityCompost.class, ModUtil.MOD_ID_LOWER+":tileEntityCompost");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, ModUtil.MOD_ID_LOWER+":tileEntityFeeder");
        GameRegistry.registerTileEntity(TileEntityGiantChest.class, ModUtil.MOD_ID_LOWER+":tileEntityGiantChest");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, ModUtil.MOD_ID_LOWER+":tileEntityGrinder");
        GameRegistry.registerTileEntity(TileEntityFurnaceDouble.class, ModUtil.MOD_ID_LOWER+":tileEntityFurnaceDouble");
        GameRegistry.registerTileEntity(TileEntityInputter.class, ModUtil.MOD_ID_LOWER+":tileEntityInputter");
        GameRegistry.registerTileEntity(TileEntityFishingNet.class, ModUtil.MOD_ID_LOWER+":tileEntityFishingNet");
        GameRegistry.registerTileEntity(TileEntityFurnaceSolar.class, ModUtil.MOD_ID_LOWER+":tileEntityFurnaceSolar");
        GameRegistry.registerTileEntity(TileEntityHeatCollector.class, ModUtil.MOD_ID_LOWER+":tileEntityHeatCollector");
        GameRegistry.registerTileEntity(TileEntityItemRepairer.class, ModUtil.MOD_ID_LOWER+":tileEntityRepairer");
        GameRegistry.registerTileEntity(TileEntityGreenhouseGlass.class, ModUtil.MOD_ID_LOWER+":tileEntityGreenhouseGlass");
        GameRegistry.registerTileEntity(TileEntityBreaker.class, ModUtil.MOD_ID_LOWER+":tileEntityBreaker");
        GameRegistry.registerTileEntity(TileEntityDropper.class, ModUtil.MOD_ID_LOWER+":tileEntityDropper");
        GameRegistry.registerTileEntity(TileEntityInputter.TileEntityInputterAdvanced.class, ModUtil.MOD_ID_LOWER+":tileEntityInputterAdvanced");
        GameRegistry.registerTileEntity(TileEntityBreaker.TileEntityPlacer.class, ModUtil.MOD_ID_LOWER+":tileEntityPlacer");
        GameRegistry.registerTileEntity(TileEntityGrinder.TileEntityGrinderDouble.class, ModUtil.MOD_ID_LOWER+":tileEntityGrinderDouble");
        GameRegistry.registerTileEntity(TileEntityCanolaPress.class, ModUtil.MOD_ID_LOWER+":tileEntityCanolaPress");
        GameRegistry.registerTileEntity(TileEntityFermentingBarrel.class, ModUtil.MOD_ID_LOWER+":tileEntityFermentingBarrel");
        GameRegistry.registerTileEntity(TileEntityOilGenerator.class, ModUtil.MOD_ID_LOWER+":tileEntityOilGenerator");
        GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, ModUtil.MOD_ID_LOWER+":tileEntityCoalGenerator");
        GameRegistry.registerTileEntity(TileEntityPhantomItemface.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomItemface");
        GameRegistry.registerTileEntity(TileEntityPhantomLiquiface.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomLiquiface");
        GameRegistry.registerTileEntity(TileEntityPhantomEnergyface.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomEnergyface");
        GameRegistry.registerTileEntity(TileEntityPhantomPlacer.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomPlacer");
        GameRegistry.registerTileEntity(TileEntityPhantomPlacer.TileEntityPhantomBreaker.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomBreaker");
        GameRegistry.registerTileEntity(TileEntityFluidCollector.class, ModUtil.MOD_ID_LOWER+":tileEntityFluidCollector");
        GameRegistry.registerTileEntity(TileEntityFluidCollector.TileEntityFluidPlacer.class, ModUtil.MOD_ID_LOWER+":tileEntityFluidPlacer");
        GameRegistry.registerTileEntity(TileEntityLavaFactoryController.class, ModUtil.MOD_ID_LOWER+":tileEntityLavaFactoryController");
        GameRegistry.registerTileEntity(TileEntityCoffeeMachine.class, ModUtil.MOD_ID_LOWER+":tileEntityCoffeeMachine");
        GameRegistry.registerTileEntity(TileEntityPhantomBooster.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomBooster");
        GameRegistry.registerTileEntity(TileEntityEnergizer.class, ModUtil.MOD_ID_LOWER+":tileEntityEnergizer");
        GameRegistry.registerTileEntity(TileEntityEnervator.class, ModUtil.MOD_ID_LOWER+":tileEntityEnervator");
        GameRegistry.registerTileEntity(TileEntityXPSolidifier.class, ModUtil.MOD_ID_LOWER+":tileEntityXPSolidifier");
        GameRegistry.registerTileEntity(TileEntitySmileyCloud.class, ModUtil.MOD_ID_LOWER+":tileEntityCloud");
        GameRegistry.registerTileEntity(TileEntityLeafGenerator.class, ModUtil.MOD_ID_LOWER+":tileEntityLeafGenerator");
        GameRegistry.registerTileEntity(TileEntityDirectionalBreaker.class, ModUtil.MOD_ID_LOWER+":tileEntityDirectionalBreaker");
        GameRegistry.registerTileEntity(TileEntityRangedCollector.class, ModUtil.MOD_ID_LOWER+":tileEntityRangedCollector");
        GameRegistry.registerTileEntity(TileEntityLaserRelay.class, ModUtil.MOD_ID_LOWER+":tileEntityLaserRelay");
        GameRegistry.registerTileEntity(TileEntityAtomicReconstructor.class, ModUtil.MOD_ID_LOWER+":tileEntityAtomicReconstructor");
        GameRegistry.registerTileEntity(TileEntityBookletStand.class, ModUtil.MOD_ID_LOWER+":tileEntityBookletStand");
        GameRegistry.registerTileEntity(TileEntityMiner.class, ModUtil.MOD_ID_LOWER+":tileEntityMiner");
        GameRegistry.registerTileEntity(TileEntityFireworkBox.class, ModUtil.MOD_ID_LOWER+":tileEntityFireworkBox");
    }

    @Override
    public final void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.isRedstonePowered = compound.getBoolean("Redstone");
        this.readSyncableNBT(compound, false);
    }

    @Override
    public final void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setBoolean("Redstone", this.isRedstonePowered);
        this.writeSyncableNBT(compound, false);
    }

    @Override
    public void update(){
        this.updateEntity();
    }

    public void updateEntity(){
        this.ticksElapsed++;
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeSyncableNBT(tag, true);
        return new S35PacketUpdateTileEntity(this.pos, 3, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        this.readSyncableNBT(pkt.getNbtCompound(), true);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return !(oldState.getBlock().isAssociatedBlock(newState.getBlock()));
    }

    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        if(this instanceof IRedstoneToggle){
            compound.setBoolean("IsPulseMode", ((IRedstoneToggle)this).isPulseMode());
        }
    }

    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        if(this instanceof IRedstoneToggle){
            ((IRedstoneToggle)this).toggle(compound.getBoolean("IsPulseMode"));
        }
    }

    public void setRedstonePowered(boolean powered){
        this.isRedstonePowered = powered;
        this.markDirty();
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
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
    }
}