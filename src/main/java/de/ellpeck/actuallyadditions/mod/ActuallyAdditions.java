/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import de.ellpeck.actuallyadditions.mod.achievement.InitAchievements;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.mod.crafting.InitCrafting;
import de.ellpeck.actuallyadditions.mod.crafting.ItemCrafting;
import de.ellpeck.actuallyadditions.mod.event.InitEvents;
import de.ellpeck.actuallyadditions.mod.gen.InitVillager;
import de.ellpeck.actuallyadditions.mod.gen.OreGen;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.InitForeignPaxels;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.lens.LensNoneRecipeHandler;
import de.ellpeck.actuallyadditions.mod.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.mod.misc.*;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.ore.InitOreDict;
import de.ellpeck.actuallyadditions.mod.proxy.IProxy;
import de.ellpeck.actuallyadditions.mod.recipe.FuelHandler;
import de.ellpeck.actuallyadditions.mod.recipe.HairyBallHandler;
import de.ellpeck.actuallyadditions.mod.recipe.TreasureChestHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.FakePlayerUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;

import java.util.Locale;

//                                                                           So that BuildCraft Oil always gets used
@Mod(modid = ModUtil.MOD_ID, name = ModUtil.NAME, version = ModUtil.VERSION, dependencies = "after:BuildCraft|Energy", guiFactory = "GuiFactory")
public class ActuallyAdditions{

    @Instance(ModUtil.MOD_ID)
    public static ActuallyAdditions instance;

    @SidedProxy(clientSide = "ClientProxy", serverSide = "ServerProxy")
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
        DungeonLoot.init();
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
        LensNoneRecipeHandler.init();
        InitForeignPaxels.init();

        InitBooklet.init();
        proxy.postInit(event);

        ModUtil.LOGGER.info("PostInitialization Finished.");
        FakePlayerUtil.info();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event){
        Util.registerDispenserHandler(InitItems.itemBucketOil, new DispenserHandlerEmptyBucket());
        Util.registerDispenserHandler(InitItems.itemBucketCanolaOil, new DispenserHandlerEmptyBucket());
        Util.registerDispenserHandler(Items.bucket, new DispenserHandlerFillBucket());
        Util.registerDispenserHandler(InitItems.itemFertilizer, new DispenserHandlerFertilize());
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event){
        if(LaserRelayConnectionHandler.getInstance() == null){
            LaserRelayConnectionHandler.setInstance(new LaserRelayConnectionHandler());
        }

        WorldData.init(MinecraftServer.getServer());
    }

    @EventHandler
    public void missingMapping(FMLMissingMappingsEvent event){
        for(FMLMissingMappingsEvent.MissingMapping mapping : event.get()){
            //Ignore removal of foreign paxels
            if(mapping.name != null && mapping.name.toLowerCase(Locale.ROOT).startsWith(ModUtil.MOD_ID_LOWER+":")){
                if(mapping.name.contains("paxel") || mapping.name.contains("itemSpecial")){
                    mapping.ignore();
                    ModUtil.LOGGER.info("Missing Mapping "+mapping.name+" is getting ignored. This is intentional.");
                }
            }
        }
    }
}
