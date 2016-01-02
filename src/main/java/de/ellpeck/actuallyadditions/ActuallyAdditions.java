/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import de.ellpeck.actuallyadditions.achievement.InitAchievements;
import de.ellpeck.actuallyadditions.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.crafting.InitCrafting;
import de.ellpeck.actuallyadditions.crafting.ItemCrafting;
import de.ellpeck.actuallyadditions.event.InitEvents;
import de.ellpeck.actuallyadditions.gen.InitVillager;
import de.ellpeck.actuallyadditions.gen.OreGen;
import de.ellpeck.actuallyadditions.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.items.InitForeignPaxels;
import de.ellpeck.actuallyadditions.items.InitItems;
import de.ellpeck.actuallyadditions.items.ItemCoffee;
import de.ellpeck.actuallyadditions.items.lens.LensNoneRecipeHandler;
import de.ellpeck.actuallyadditions.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.misc.*;
import de.ellpeck.actuallyadditions.network.PacketHandler;
import de.ellpeck.actuallyadditions.ore.InitOreDict;
import de.ellpeck.actuallyadditions.proxy.IProxy;
import de.ellpeck.actuallyadditions.recipe.FuelHandler;
import de.ellpeck.actuallyadditions.recipe.HairyBallHandler;
import de.ellpeck.actuallyadditions.recipe.TreasureChestHandler;
import de.ellpeck.actuallyadditions.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.update.UpdateChecker;
import de.ellpeck.actuallyadditions.util.FakePlayerUtil;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.Util;
import de.ellpeck.actuallyadditions.util.compat.minetweaker.MineTweaker;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;

import java.util.Locale;

//                                                                           So that BuildCraft Oil always gets used
@Mod(modid = ModUtil.MOD_ID, name = ModUtil.NAME, version = ModUtil.VERSION, dependencies = "after:BuildCraft|Energy", guiFactory = "de.ellpeck.actuallyadditions.config.GuiFactory")
public class ActuallyAdditions{

    @Instance(ModUtil.MOD_ID)
    public static ActuallyAdditions instance;

    @SidedProxy(clientSide = "de.ellpeck.actuallyadditions.proxy.ClientProxy", serverSide = "de.ellpeck.actuallyadditions.proxy.ServerProxy")
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
        MineTweaker.init();

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
