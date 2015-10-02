/*
 * This file ("TileEntityBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityBase extends TileEntity{

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        this.readFromNBT(pkt.func_148857_g());
    }

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
        GameRegistry.registerTileEntity(TileEntityPhantomface.TileEntityPhantomItemface.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomItemface");
        GameRegistry.registerTileEntity(TileEntityPhantomface.TileEntityPhantomLiquiface.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomLiquiface");
        GameRegistry.registerTileEntity(TileEntityPhantomface.TileEntityPhantomEnergyface.class, ModUtil.MOD_ID_LOWER+":tileEntityPhantomEnergyface");
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
        GameRegistry.registerTileEntity(TileEntityOreMagnet.class, ModUtil.MOD_ID_LOWER+":tileEntityOreMagnet");
        GameRegistry.registerTileEntity(TileEntitySmileyCloud.class, ModUtil.MOD_ID_LOWER+":tileEntityCloud");
        GameRegistry.registerTileEntity(TileEntityLeafGenerator.class, ModUtil.MOD_ID_LOWER+":tileEntityLeafGenerator");
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z){
        return !(oldBlock.isAssociatedBlock(newBlock));
    }
}