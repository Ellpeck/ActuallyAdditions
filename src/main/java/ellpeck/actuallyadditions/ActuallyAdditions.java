package ellpeck.actuallyadditions;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import ellpeck.actuallyadditions.achievement.InitAchievements;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.communication.InterModCommunications;
import ellpeck.actuallyadditions.config.ConfigurationHandler;
import ellpeck.actuallyadditions.crafting.CrusherCrafting;
import ellpeck.actuallyadditions.crafting.InitCrafting;
import ellpeck.actuallyadditions.crafting.ItemCrafting;
import ellpeck.actuallyadditions.event.InitEvents;
import ellpeck.actuallyadditions.gen.InitVillager;
import ellpeck.actuallyadditions.gen.OreGen;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemCoffee;
import ellpeck.actuallyadditions.material.InitItemMaterials;
import ellpeck.actuallyadditions.misc.DispenserHandlerEmptyBucket;
import ellpeck.actuallyadditions.misc.DispenserHandlerFertilize;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.proxy.IProxy;
import ellpeck.actuallyadditions.recipe.FuelHandler;
import ellpeck.actuallyadditions.recipe.HairyBallHandler;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.BlockDispenser;

@Mod(modid = ModUtil.MOD_ID, name = ModUtil.NAME, version = ModUtil.VERSION, canBeDeactivated = false)
public class ActuallyAdditions{

    @Instance(ModUtil.MOD_ID)
    public static ActuallyAdditions instance;

    @SidedProxy(clientSide = "ellpeck.actuallyadditions.proxy.ClientProxy", serverSide = "ellpeck.actuallyadditions.proxy.ServerProxy")
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Util.logInfo("Starting PreInitialization Phase...");

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        PacketHandler.init();
        InitItemMaterials.init();
        InitBlocks.init();
        InitItems.init();
        InitVillager.init();
        FuelHandler.init();
        proxy.preInit();

        Util.logInfo("PreInitialization Finished.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        Util.logInfo("Starting Initialization Phase...");

        InitAchievements.init();
        GuiHandler.init();
        OreGen.init();
        TileEntityBase.init();
        InitEvents.init();
        InitCrafting.init();
        FMLInterModComms.sendMessage("Waila", "register", "ellpeck.actuallyadditions.waila.WailaDataProvider.register");
        proxy.init();

        Util.logInfo("Initialization Finished.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Util.logInfo("Starting PostInitialization Phase...");

        ItemCoffee.initIngredients();
        CrusherCrafting.init();
        ItemCrafting.initMashedFoodRecipes();
        HairyBallHandler.init();
        proxy.postInit();

        Util.logInfo("PostInitialization Finished.");
    }

    @EventHandler
    public void onIMCReceived(FMLInterModComms.IMCEvent event){
        InterModCommunications.processIMC(event.getMessages());
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event){

        BlockDispenser.dispenseBehaviorRegistry.putObject(InitItems.itemBucketCanolaOil, new DispenserHandlerEmptyBucket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(InitItems.itemBucketOil, new DispenserHandlerEmptyBucket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(InitItems.itemFertilizer, new DispenserHandlerFertilize());

    }
}
