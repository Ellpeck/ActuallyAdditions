/*
 * This file ("BlockCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.blocks.BlockColoredLamp;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BlockCrafting{

    public static IRecipe recipeSmileyCloud;
    public static IRecipe recipePhantomface;
    public static IRecipe recipeLiquiface;
    public static IRecipe recipeEnergyface;
    public static IRecipe recipePhantomBreaker;
    public static IRecipe recipePhantomPlacer;
    public static IRecipe recipeBreaker;
    public static IRecipe recipePlacer;
    public static IRecipe recipeLiquidPlacer;
    public static IRecipe recipeLiquidCollector;
    public static IRecipe recipeCase;
    public static IRecipe recipeIronCase;
    public static IRecipe recipeEnderCase;
    public static IRecipe recipeEnderPearlBlock;
    public static IRecipe recipeESD;
    public static IRecipe recipeAdvancedESD;
    public static IRecipe recipePhantomBooster;
    public static IRecipe recipeCoffeeMachine;
    public static IRecipe recipeCrusher;
    public static IRecipe recipeDoubleCrusher;
    public static IRecipe recipeFurnace;
    public static IRecipe recipeSolidifier;
    public static IRecipe recipeCasing;
    public static IRecipe recipeGlass;
    public static IRecipe recipeLavaFactory;
    public static IRecipe recipeEnergizer;
    public static IRecipe recipeEnervator;
    public static IRecipe recipeSolar;
    public static IRecipe recipeHeatCollector;
    public static IRecipe recipeCoalGen;
    public static IRecipe recipeOilGen;
    public static IRecipe recipeRepairer;
    public static IRecipe recipeFisher;
    public static IRecipe recipeQuartzBlock;
    public static IRecipe recipeQuartzChiseled;
    public static IRecipe recipeQuartzPillar;
    public static IRecipe recipeBlockChar;
    public static IRecipe recipeFeeder;
    public static IRecipe recipeCompost;
    public static IRecipe recipeCrate;
    public static IRecipe recipeFermentingBarrel;
    public static IRecipe recipeCanolaPress;
    public static IRecipe[] recipesLamps = new IRecipe[BlockColoredLamp.allLampTypes.length];
    public static IRecipe recipePowerer;
    public static IRecipe recipeLeafGen;
    public static IRecipe recipeDirectionalBreaker;
    public static IRecipe recipeDropper;
    public static IRecipe recipeRangedCollector;
    public static IRecipe recipeLaserRelay;
    public static IRecipe recipeAtomicReconstructor;
    public static IRecipe recipeMiner;
    public static IRecipe recipeFireworkBox;

    public static void init(){

        //Firework Box
        if(ConfigCrafting.FIREWORK_BOX.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFireworkBox),
                    "GGG", "SSS", "CCC",
                    'G', new ItemStack(Items.gunpowder),
                    'S', new ItemStack(Items.stick),
                    'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeFireworkBox = RecipeUtil.lastIRecipe();
        }

        //Miner
        if(ConfigCrafting.MINER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMiner),
                    "IRI", "RCR", "IDI",
                    'R', "blockRedstone",
                    'I', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'C', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()),
                    'D', new ItemStack(InitItems.itemDrill, 1, Util.WILDCARD)));
            recipeMiner = RecipeUtil.lastIRecipe();
        }

        //Quartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockQuartzWall, 6),
                "XXX", "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockQuartzSlab, 6),
                "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockQuartzStair, 6),
                "X  ", "XX ", "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal())));

        //PillarQuartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPillarQuartzWall, 6),
                "XXX", "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPillarQuartzSlab, 6),
                "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPillarQuartzStair, 6),
                "X  ", "XX ", "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal())));

        //ChiseledQuartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockChiseledQuartzWall, 6),
                "XXX", "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_CHISELED.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockChiseledQuartzSlab, 6),
                "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_CHISELED.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockChiseledQuartzStair, 6),
                "X  ", "XX ", "XXX",
                'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_CHISELED.ordinal())));

        //White Ethetic Blocks
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockTestifiBucksWhiteFence, 6),
                "XXX", "XXX",
                'X', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockTestifiBucksWhiteSlab, 6),
                "XXX",
                'X', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockTestifiBucksWhiteStairs, 6),
                "X  ", "XX ", "XXX",
                'X', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall)));

        //Green Ethetic Blocks
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockTestifiBucksGreenFence, 6),
                "XXX", "XXX",
                'X', new ItemStack(InitBlocks.blockTestifiBucksGreenWall)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockTestifiBucksGreenSlab, 6),
                "XXX",
                'X', new ItemStack(InitBlocks.blockTestifiBucksGreenWall)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockTestifiBucksGreenStairs, 6),
                "X  ", "XX ", "XXX",
                'X', new ItemStack(InitBlocks.blockTestifiBucksGreenWall)));

        //Atomic Reconstructor
        if(ConfigCrafting.ATOMIC_RECONSTRUCTOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockAtomicReconstructor),
                    "IRI", "RCR", "IRI",
                    'R', "dustRedstone",
                    'I', "ingotIron",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal())));
            recipeAtomicReconstructor = RecipeUtil.lastIRecipe();
        }

        //Laser Relay
        if(ConfigCrafting.LASER_RELAY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLaserRelay, 2),
                    "OBO", "RCR", "OBO",
                    'B', new ItemStack(Blocks.redstone_block),
                    'O', new ItemStack(Blocks.obsidian),
                    'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeLaserRelay = RecipeUtil.lastIRecipe();
        }

        //Ranged Collector
        if(ConfigCrafting.RANGED_COLLECTOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockRangedCollector),
                    " A ", "EHE", " C ",
                    'E', new ItemStack(Items.ender_pearl),
                    'H', new ItemStack(Blocks.hopper),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'A', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal())));
            recipeRangedCollector = RecipeUtil.lastIRecipe();
        }

        //Directional Breaker
        if(ConfigCrafting.DIRECTIONAL_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockDirectionalBreaker),
                    "BBB", " C ",
                    'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()),
                    'B', new ItemStack(InitBlocks.blockBreaker)));
            recipeDirectionalBreaker = RecipeUtil.lastIRecipe();
        }

        //Smiley Cloud
        if(ConfigCrafting.CLOUD.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockSmileyCloud),
                    " W ", "WXW", " W ",
                    'W', new ItemStack(Blocks.wool, 1, Util.WILDCARD),
                    'X', new ItemStack(InitItems.itemSolidifiedExperience)));
            recipeSmileyCloud = RecipeUtil.lastIRecipe();
        }

        //Compost
        if(ConfigCrafting.COMPOST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "W W", "WCW",
                    'W', "plankWood",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal())));
            recipeCompost = RecipeUtil.lastIRecipe();
        }

        //XP Solidifier
        if(ConfigCrafting.XP_SOLIDIFIER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockXPSolidifier),
                    "XXX", "DCD", "XXX",
                    'X', new ItemStack(InitItems.itemSolidifiedExperience),
                    'D', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeSolidifier = RecipeUtil.lastIRecipe();
        }

        //Charcoal Block
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()),
                "CCC", "CCC", "CCC",
                'C', new ItemStack(Items.coal, 1, 1));
        recipeBlockChar = RecipeUtil.lastIRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(Items.coal, 9, 1),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()));

        //Wood Casing
        if(ConfigCrafting.WOOD_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "plankWood",
                    'R', "logWood",
                    'S', "stickWood"));
            recipeCase = RecipeUtil.lastIRecipe();
        }

        //Iron Casing
        if(ConfigCrafting.IRON_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    "WSW", "SQS", "WSW",
                    'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    'W', "ingotIron",
                    'S', "stickWood"));
            recipeIronCase = RecipeUtil.lastIRecipe();
        }

        //Ender Casing
        if(ConfigCrafting.ENDER_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', new ItemStack(Items.ender_pearl),
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                    'S', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal())));
            recipeEnderCase = RecipeUtil.lastIRecipe();
        }

        //Phantom Booster
        if(ConfigCrafting.PHANTOM_BOOSTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomBooster),
                    "RDR", "DCD", "RDR",
                    'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
            recipePhantomBooster = RecipeUtil.lastIRecipe();
        }

        //Coffee Machine
        if(ConfigCrafting.COFFEE_MACHINE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoffeeMachine),
                    " C ", " S ", "AMA",
                    'M', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()),
                    'C', InitItems.itemCoffeeBean,
                    'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal())));
            recipeCoffeeMachine = RecipeUtil.lastIRecipe();
        }

        //Energizer
        if(ConfigCrafting.ENERGIZER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockEnergizer),
                    "I I", "CAC", "I I",
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal())));
            recipeEnergizer = RecipeUtil.lastIRecipe();
        }

        //Enervator
        if(ConfigCrafting.ENERVATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockEnervator),
                    " I ", "CAC", " I ",
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal())));
            recipeEnervator = RecipeUtil.lastIRecipe();
        }

        //Lava Factory
        if(ConfigCrafting.LAVA_FACTORY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLavaFactoryController),
                    "SCS", "ISI", "LLL",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()),
                    'L', Items.lava_bucket));
            recipeLavaFactory = RecipeUtil.lastIRecipe();
        }

        //Casing
        if(ConfigCrafting.CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 32, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal()),
                    "ICI",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeCasing = RecipeUtil.lastIRecipe();
        }

        //Canola Press
        if(ConfigCrafting.CANOLA_PRESS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCanolaPress),
                    "CHC", "CDC", "CRC",
                    'C', "cobblestone",
                    'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
            recipeCanolaPress = RecipeUtil.lastIRecipe();
        }

        //Fermenting Barrel
        if(ConfigCrafting.FERMENTING_BARREL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFermentingBarrel),
                    "CHC", "CDC", "CRC",
                    'C', "logWood",
                    'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
            recipeFermentingBarrel = RecipeUtil.lastIRecipe();
        }

        //Phantomface
        if(ConfigCrafting.PHANTOMFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomface),
                    " C ", "EBE", " S ",
                    'E', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', Blocks.chest,
                    'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal())));
            recipePhantomface = RecipeUtil.lastIRecipe();
        }

        //Phantom Placer
        if(ConfigCrafting.PHANTOM_PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockPhantomPlacer),
                    InitBlocks.blockPlacer,
                    InitBlocks.blockPhantomface));
            recipePhantomPlacer = RecipeUtil.lastIRecipe();
        }

        //Phantom Breaker
        if(ConfigCrafting.PHANTOM_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockPhantomBreaker),
                    InitBlocks.blockBreaker,
                    InitBlocks.blockPhantomface));
            recipePhantomBreaker = RecipeUtil.lastIRecipe();
        }

        //Phantom Energyface
        if(ConfigCrafting.PHANTOM_ENERGYFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomEnergyface),
                    " R ", "RFR", " R ",
                    'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'F', InitBlocks.blockPhantomface));
            recipeEnergyface = RecipeUtil.lastIRecipe();
        }

        //Phantom Liquiface
        if(ConfigCrafting.PHANTOM_LIQUIFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomLiquiface),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockPhantomface));
            recipeLiquiface = RecipeUtil.lastIRecipe();
        }

        //Liquid Placer
        if(ConfigCrafting.LIQUID_PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFluidPlacer),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockPlacer));
            recipeLiquidPlacer = RecipeUtil.lastIRecipe();
        }

        //Liquid Breaker
        if(ConfigCrafting.LIQUID_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFluidCollector),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockBreaker));
            recipeLiquidCollector = RecipeUtil.lastIRecipe();
        }

        //Oil Generator
        if(ConfigCrafting.OIL_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockOilGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'B', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
            recipeOilGen = RecipeUtil.lastIRecipe();
        }

        //Coal Generator
        if(ConfigCrafting.COAL_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoalGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'R', new ItemStack(Items.coal, 1, Util.WILDCARD)));
            recipeCoalGen = RecipeUtil.lastIRecipe();
        }

        //Leaf Generator
        if(ConfigCrafting.LEAF_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLeafGenerator),
                    "IEI", "GLG", "ICI",
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'G', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'E', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'L', "treeLeaves",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeLeafGen = RecipeUtil.lastIRecipe();
        }

        //Enderpearl Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                "EE", "EE",
                'E', Items.ender_pearl));
        recipeEnderPearlBlock = RecipeUtil.lastIRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.ender_pearl, 4),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal())));

        //Quartz Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                "QQ", "QQ",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeQuartzBlock = RecipeUtil.lastIRecipe();

        //Fishing Net
        if(ConfigCrafting.FISHING_NET.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFishingNet),
                    "SSS", "SDS", "SSS",
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'S', Items.string));
            recipeFisher = RecipeUtil.lastIRecipe();
        }

        //Repairer
        if(ConfigCrafting.REPAIRER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockItemRepairer),
                    "DID", "OCO", "DID",
                    'D', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
            recipeRepairer = RecipeUtil.lastIRecipe();
        }

        //Solar Panel
        if(ConfigCrafting.SOLAR_PANEL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceSolar),
                    "IQI", "CDC", "IBI",
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(Blocks.iron_bars)));
            recipeSolar = RecipeUtil.lastIRecipe();
        }

        //Heat Collector
        if(ConfigCrafting.HEAT_COLLECTOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockHeatCollector),
                    "BRB", "CDC", "BQB",
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'R', new ItemStack(Items.repeater),
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'L', new ItemStack(Items.lava_bucket),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(Blocks.iron_bars)));
            recipeHeatCollector = RecipeUtil.lastIRecipe();
        }

        //Quartz Pillar
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()),
                "Q", "Q",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeQuartzPillar = RecipeUtil.lastIRecipe();

        //Chiseled Quartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 2, TheMiscBlocks.QUARTZ_CHISELED.ordinal()),
                "Q", "Q",
                'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal())));
        recipeQuartzChiseled = RecipeUtil.lastIRecipe();

        //Inputter
        if(ConfigCrafting.INPUTTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockInputter),
                    "WWW", "CHC", "WWW",
                    'W', "plankWood",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeESD = RecipeUtil.lastIRecipe();

            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockInputterAdvanced),
                    InitBlocks.blockInputter,
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal())));
            recipeAdvancedESD = RecipeUtil.lastIRecipe();
        }

        //Crusher
        if(ConfigCrafting.CRUSHER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "MFC", "DQD", "CFM",
                    'M', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint)));
            recipeCrusher = RecipeUtil.lastIRecipe();
        }

        //Double Crusher
        if(ConfigCrafting.DOUBLE_CRUSHER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinderDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'R', InitBlocks.blockGrinder,
                    'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'P', new ItemStack(Blocks.piston)));
            recipeDoubleCrusher = RecipeUtil.lastIRecipe();
        }

        //Double Furnace
        if(ConfigCrafting.DOUBLE_FURNACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "PDC", "RFR", "CDP",
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'R', new ItemStack(Blocks.furnace),
                    'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeFurnace = RecipeUtil.lastIRecipe();
        }

        //Feeder
        if(ConfigCrafting.FEEDER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "DHD", "WCW",
                    'W', "plankWood",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'C', new ItemStack(Items.golden_carrot),
                    'H', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal())));
            recipeFeeder = RecipeUtil.lastIRecipe();
        }

        //Giant Chest
        if(ConfigCrafting.GIANT_CHEST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "WDW", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'D', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'W', "plankWood"));
            recipeCrate = RecipeUtil.lastIRecipe();
        }

        //Greenhouse Glass
        if(ConfigCrafting.GREENHOUSE_GLASS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGreenhouseGlass, 3),
                    "GSG", "SDS", "GSG",
                    'G', "blockGlass",
                    'D', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.LAPIS.ordinal()),
                    'S', "treeSapling"));
            recipeGlass = RecipeUtil.lastIRecipe();
        }

        //Placer
        if(ConfigCrafting.PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPlacer),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal())));
            recipePlacer = RecipeUtil.lastIRecipe();
        }

        //Breaker
        if(ConfigCrafting.BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockBreaker),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal())));
            recipeBreaker = RecipeUtil.lastIRecipe();
        }

        //Dropper
        if(ConfigCrafting.DROPPER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockDropper),
                    "CBC", "CDR", "CBC",
                    'B', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()),
                    'C', "cobblestone",
                    'D', Blocks.dropper,
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeDropper = RecipeUtil.lastIRecipe();
        }

        if(ConfigCrafting.LAMPS.isEnabled()){
            for(int i = 0; i < BlockColoredLamp.allLampTypes.length; i++){
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockColoredLamp, 6, i),
                        "GCG", "DQD", "GCG",
                        'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()),
                        'G', "glowstone",
                        'D', "dye"+BlockColoredLamp.allLampTypes[i].name,
                        'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
                recipesLamps[i] = RecipeUtil.lastIRecipe();
            }

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLampPowerer, 4),
                    "XXX", "XLX", "XXX",
                    'X', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'L', new ItemStack(InitBlocks.blockColoredLamp, 1, Util.WILDCARD)));
            recipePowerer = RecipeUtil.lastIRecipe();
        }

    }

}