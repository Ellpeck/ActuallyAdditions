/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

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
import ellpeck.actuallyadditions.items.InitForeignPaxels;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemCoffee;
import ellpeck.actuallyadditions.material.InitArmorMaterials;
import ellpeck.actuallyadditions.material.InitToolMaterials;
import ellpeck.actuallyadditions.misc.DispenserHandlerEmptyBucket;
import ellpeck.actuallyadditions.misc.DispenserHandlerFertilize;
import ellpeck.actuallyadditions.misc.DispenserHandlerFillBucket;
import ellpeck.actuallyadditions.misc.WorldData;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.ore.InitOreDict;
import ellpeck.actuallyadditions.proxy.IProxy;
import ellpeck.actuallyadditions.recipe.FuelHandler;
import ellpeck.actuallyadditions.recipe.HairyBallHandler;
import ellpeck.actuallyadditions.recipe.TreasureChestHandler;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.update.UpdateChecker;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import ellpeck.actuallyadditions.util.playerdata.PersistentServerData;
import net.minecraft.init.Items;

//                                                                           So that BuildCraft Oil always gets used
@Mod(modid = ModUtil.MOD_ID, name = ModUtil.NAME, version = ModUtil.VERSION, dependencies = "after:BuildCraft|Energy", canBeDeactivated = false, guiFactory = "ellpeck.actuallyadditions.config.GuiFactory")
public class ActuallyAdditions{

    @Instance(ModUtil.MOD_ID)
    public static ActuallyAdditions instance;

    @SidedProxy(clientSide = "ellpeck.actuallyadditions.proxy.ClientProxy", serverSide = "ellpeck.actuallyadditions.proxy.ServerProxy")
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModUtil.LOGGER.info("Starting PreInitialization Phase...");

        new ConfigurationHandler(event.getSuggestedConfigurationFile());
        PacketHandler.init();
        InitToolMaterials.init();
        InitArmorMaterials.init();
        InitBlocks.init();
        InitItems.init();
        FuelHandler.init();
        UpdateChecker.init();
        proxy.preInit(event);

        ModUtil.LOGGER.info("PreInitialization Finished.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        ModUtil.LOGGER.info("Starting Initialization Phase...");

        InitOreDict.init();
        InitAchievements.init();
        GuiHandler.init();
        OreGen.init();
        TileEntityBase.init();
        InitEvents.init();
        InitCrafting.init();
        FMLInterModComms.sendMessage("Waila", "register", "ellpeck.actuallyadditions.waila.WailaDataProvider.register");
        proxy.init(event);

        ModUtil.LOGGER.info("Initialization Finished.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("Starting PostInitialization Phase...");

        InitVillager.init();
        ItemCoffee.initIngredients();
        CrusherCrafting.init();
        ItemCrafting.initMashedFoodRecipes();
        HairyBallHandler.init();
        TreasureChestHandler.init();
        InitForeignPaxels.init();
        proxy.postInit(event);

        ModUtil.LOGGER.info("PostInitialization Finished.");
    }

    @EventHandler
    public void onIMCReceived(FMLInterModComms.IMCEvent event){
        InterModCommunications.processIMC(event.getMessages());
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event){
        Util.registerDispenserHandler(InitItems.itemBucketOil, new DispenserHandlerEmptyBucket());
        Util.registerDispenserHandler(InitItems.itemBucketCanolaOil, new DispenserHandlerEmptyBucket());
        Util.registerDispenserHandler(Items.bucket, new DispenserHandlerFillBucket());
        Util.registerDispenserHandler(InitItems.itemFertilizer, new DispenserHandlerFertilize());

        WorldData.init(event.getServer());
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event){
        //Clear Data so that it won't be carried over to other worlds
        PersistentServerData.playerSaveData.clear();
    }
}
