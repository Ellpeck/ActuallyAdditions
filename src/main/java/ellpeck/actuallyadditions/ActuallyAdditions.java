package ellpeck.actuallyadditions;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.actuallyadditions.achievement.InitAchievements;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.ConfigurationHandler;
import ellpeck.actuallyadditions.crafting.GrinderCrafting;
import ellpeck.actuallyadditions.crafting.InitCrafting;
import ellpeck.actuallyadditions.event.InitEvents;
import ellpeck.actuallyadditions.gen.OreGen;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.material.InitItemMaterials;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.proxy.IProxy;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;

@Mod(modid = ModUtil.MOD_ID, name = ModUtil.NAME, version = ModUtil.VERSION)
public class ActuallyAdditions{

    @Instance(ModUtil.MOD_ID)
    public static ActuallyAdditions instance;

    @SidedProxy(clientSide = "ellpeck.actuallyadditions.proxy.ClientProxy", serverSide = "ellpeck.actuallyadditions.proxy.ServerProxy")
    public static IProxy proxy;

    @EventHandler()
    public void preInit(FMLPreInitializationEvent event){
        Util.logInfo("Starting PreInitialization Phase...");

        PacketHandler.init();
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        InitItemMaterials.init();
        InitBlocks.init();
        InitItems.init();
        proxy.preInit();

        Util.logInfo("PreInitialization Finished.");
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void init(FMLInitializationEvent event){
        Util.logInfo("Starting Initialization Phase...");

        InitAchievements.init();
        GuiHandler.init();
        OreGen.init();
        TileEntityBase.init();
        InitEvents.init();
        InitCrafting.init();
        proxy.init();

        Util.logInfo("Initialization Finished.");
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        Util.logInfo("Starting PostInitialization Phase...");

        GrinderCrafting.init();
        proxy.postInit();

        Util.logInfo("PostInitialization Finished.");
    }
}
