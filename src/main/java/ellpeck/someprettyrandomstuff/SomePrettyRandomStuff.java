package ellpeck.someprettyrandomstuff;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.someprettyrandomstuff.achievement.AchievementEvent;
import ellpeck.someprettyrandomstuff.achievement.InitAchievements;
import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.config.ConfigurationHandler;
import ellpeck.someprettyrandomstuff.crafting.InitCrafting;
import ellpeck.someprettyrandomstuff.gen.OreGen;
import ellpeck.someprettyrandomstuff.inventory.GuiHandler;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.material.InitItemMaterials;
import ellpeck.someprettyrandomstuff.network.PacketHandler;
import ellpeck.someprettyrandomstuff.proxy.IProxy;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.util.Util;

@Mod(modid = Util.MOD_ID, name = Util.NAME, version = Util.VERSION)
public class SomePrettyRandomStuff{

    @Instance(Util.MOD_ID)
    public static SomePrettyRandomStuff instance;

    @SidedProxy(clientSide = "ellpeck.someprettyrandomstuff.proxy.ClientProxy", serverSide = "ellpeck.someprettyrandomstuff.proxy.ServerProxy")
    public static IProxy proxy;

    @SuppressWarnings("unused")
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
        InitCrafting.init();
        GuiHandler.init();
        OreGen.init();
        TileEntityBase.init();
        AchievementEvent.init();
        proxy.init();

        Util.logInfo("Initialization Finished.");
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        Util.logInfo("Starting PostInitialization Phase...");

        proxy.postInit();

        Util.logInfo("PostInitialization Finished.");
    }
}
