package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityBase extends TileEntity{

    public static void init(){
        Util.logInfo("Registering TileEntities...");
        GameRegistry.registerTileEntity(TileEntityCompost.class, ModUtil.MOD_ID_LOWER + ":tileEntityCompost");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, ModUtil.MOD_ID_LOWER + ":tileEntityFeeder");
        GameRegistry.registerTileEntity(TileEntityGiantChest.class, ModUtil.MOD_ID_LOWER + ":tileEntityGiantChest");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, ModUtil.MOD_ID_LOWER + ":tileEntityGrinder");
        GameRegistry.registerTileEntity(TileEntityFurnaceDouble.class, ModUtil.MOD_ID_LOWER + ":tileEntityFurnaceDouble");
        GameRegistry.registerTileEntity(TileEntityInputter.class, ModUtil.MOD_ID_LOWER + ":tileEntityInputter");
        GameRegistry.registerTileEntity(TileEntityFishingNet.class, ModUtil.MOD_ID_LOWER + ":tileEntityFishingNet");
        GameRegistry.registerTileEntity(TileEntityFurnaceSolar.class, ModUtil.MOD_ID_LOWER + ":tileEntityFurnaceSolar");
        GameRegistry.registerTileEntity(TileEntityHeatCollector.class, ModUtil.MOD_ID_LOWER + ":tileEntityHeatCollector");
        GameRegistry.registerTileEntity(TileEntityItemRepairer.class, ModUtil.MOD_ID_LOWER + ":tileEntityRepairer");
        GameRegistry.registerTileEntity(TileEntityGreenhouseGlass.class, ModUtil.MOD_ID_LOWER + ":tileEntityGreenhouseGlass");
        GameRegistry.registerTileEntity(TileEntityBreaker.class, ModUtil.MOD_ID_LOWER + ":tileEntityBreaker");
        GameRegistry.registerTileEntity(TileEntityDropper.class, ModUtil.MOD_ID_LOWER + ":tileEntityDropper");
        GameRegistry.registerTileEntity(TileEntityInputter.TileEntityInputterAdvanced.class, ModUtil.MOD_ID_LOWER + ":tileEntityInputterAdvanced");
        GameRegistry.registerTileEntity(TileEntityBreaker.TileEntityPlacer.class, ModUtil.MOD_ID_LOWER + ":tileEntityPlacer");
        GameRegistry.registerTileEntity(TileEntityGrinder.TileEntityGrinderDouble.class, ModUtil.MOD_ID_LOWER + ":tileEntityGrinderDouble");
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z){
        return !(oldBlock.isAssociatedBlock(newBlock));
    }
}