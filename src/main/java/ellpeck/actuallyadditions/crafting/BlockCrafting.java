/*
 * This file ("BlockCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.BlockColoredLamp;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheCrystals;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
    public static IRecipe recipeStoneCase;
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
    public static IRecipe recipeBookStand;

    public static void init(){

        //Book Stand
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockBookletStand), new ItemStack(InitItems.itemLexicon), "plankWood"));
        recipeBookStand = Util.GetRecipes.lastIRecipe();

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
                    'R', "blockRedstone",
                    'I', "ingotIron",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal())));
            recipeAtomicReconstructor = Util.GetRecipes.lastIRecipe();
        }

        //Laser Relay
        if(ConfigCrafting.LASER_RELAY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLaserRelay, 2),
                    "OBO", "RCR", "OBO",
                    'B', new ItemStack(Blocks.redstone_block),
                    'O', new ItemStack(Blocks.obsidian),
                    'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeLaserRelay = Util.GetRecipes.lastIRecipe();
        }

        //Ranged Collector
        if(ConfigCrafting.RANGED_COLLECTOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockRangedCollector),
                    " A ", "EHE", " C ",
                    'E', new ItemStack(Items.ender_pearl),
                    'H', new ItemStack(Blocks.hopper),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'A', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal())));
            recipeRangedCollector = Util.GetRecipes.lastIRecipe();
        }

        //Directional Breaker
        if(ConfigCrafting.DIRECTIONAL_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockDirectionalBreaker),
                    "BBB", " C ",
                    'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()),
                    'B', new ItemStack(InitBlocks.blockBreaker)));
            recipeDirectionalBreaker = Util.GetRecipes.lastIRecipe();
        }

        //Smiley Cloud
        if(ConfigCrafting.CLOUD.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockSmileyCloud),
                    " W ", "WXW", " W ",
                    'W', new ItemStack(Blocks.wool, 1, Util.WILDCARD),
                    'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal())));
            recipeSmileyCloud = Util.GetRecipes.lastIRecipe();
        }

        //Compost
        if(ConfigCrafting.COMPOST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "W W", "WCW",
                    'W', "plankWood",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal())));
            recipeCompost = Util.GetRecipes.lastIRecipe();
        }

        //XP Solidifier
        if(ConfigCrafting.XP_SOLIDIFIER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockXPSolidifier),
                    "XXX", "DCD", "XXX",
                    'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()),
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeSolidifier = Util.GetRecipes.lastIRecipe();
        }

        //Charcoal Block
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()),
                "CCC", "CCC", "CCC",
                'C', new ItemStack(Items.coal, 1, 1));
        recipeBlockChar = Util.GetRecipes.lastIRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(Items.coal, 9, 1),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()));

        //Wood Casing
        if(ConfigCrafting.WOOD_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "plankWood",
                    'R', "logWood",
                    'S', "stickWood"));
            recipeCase = Util.GetRecipes.lastIRecipe();
        }

        //Iron Casing
        if(ConfigCrafting.IRON_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    "WSW", "SQS", "WSW",
                    'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    'W', "ingotIron",
                    'S', "stickWood"));
            recipeIronCase = Util.GetRecipes.lastIRecipe();
        }

        //Ender Casing
        if(ConfigCrafting.ENDER_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                    'S', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal())));
            recipeEnderCase = Util.GetRecipes.lastIRecipe();
        }

        //Phantom Booster
        if(ConfigCrafting.PHANTOM_BOOSTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomBooster),
                    "RDR", "DCD", "RDR",
                    'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
            recipePhantomBooster = Util.GetRecipes.lastIRecipe();
        }

        //Coffee Machine
        if(ConfigCrafting.COFFEE_MACHINE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoffeeMachine),
                    " C ", " S ", "AMA",
                    'M', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()),
                    'C', InitItems.itemCoffeeBean,
                    'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal())));
            recipeCoffeeMachine = Util.GetRecipes.lastIRecipe();
        }

        //Energizer
        if(ConfigCrafting.ENERGIZER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockEnergizer),
                    "I I", "CAC", "I I",
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal())));
            recipeEnergizer = Util.GetRecipes.lastIRecipe();
        }

        //Enervator
        if(ConfigCrafting.ENERVATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockEnervator),
                    " I ", "CAC", " I ",
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal())));
            recipeEnervator = Util.GetRecipes.lastIRecipe();
        }

        //Lava Factory
        if(ConfigCrafting.LAVA_FACTORY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLavaFactoryController),
                    "SCS", "ISI", "LLL",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'L', Items.lava_bucket));
            recipeLavaFactory = Util.GetRecipes.lastIRecipe();
        }

        //Casing
        if(ConfigCrafting.CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 32, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal()),
                    "ICI",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()),
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeCasing = Util.GetRecipes.lastIRecipe();
        }

        //Canola Press
        if(ConfigCrafting.CANOLA_PRESS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCanolaPress),
                    "CHC", "CDC", "CRC",
                    'C', "cobblestone",
                    'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
            recipeCanolaPress = Util.GetRecipes.lastIRecipe();
        }

        //Fermenting Barrel
        if(ConfigCrafting.FERMENTING_BARREL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFermentingBarrel),
                    "CHC", "CDC", "CRC",
                    'C', "logWood",
                    'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
            recipeFermentingBarrel = Util.GetRecipes.lastIRecipe();
        }

        //Phantomface
        if(ConfigCrafting.PHANTOMFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomface),
                    " C ", "EBE", " S ",
                    'E', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', Blocks.chest,
                    'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal())));
            recipePhantomface = Util.GetRecipes.lastIRecipe();
        }

        //Phantom Placer
        if(ConfigCrafting.PHANTOM_PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockPhantomPlacer),
                    InitBlocks.blockPlacer,
                    InitBlocks.blockPhantomface));
            recipePhantomPlacer = Util.GetRecipes.lastIRecipe();
        }

        //Phantom Breaker
        if(ConfigCrafting.PHANTOM_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockPhantomBreaker),
                    InitBlocks.blockBreaker,
                    InitBlocks.blockPhantomface));
            recipePhantomBreaker = Util.GetRecipes.lastIRecipe();
        }

        //Phantom Energyface
        if(ConfigCrafting.PHANTOM_ENERGYFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomEnergyface),
                    " R ", "RFR", " R ",
                    'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'F', InitBlocks.blockPhantomface));
            recipeEnergyface = Util.GetRecipes.lastIRecipe();
        }

        //Phantom Liquiface
        if(ConfigCrafting.PHANTOM_LIQUIFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomLiquiface),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockPhantomface));
            recipeLiquiface = Util.GetRecipes.lastIRecipe();
        }

        //Liquid Placer
        if(ConfigCrafting.LIQUID_PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFluidPlacer),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockPlacer));
            recipeLiquidPlacer = Util.GetRecipes.lastIRecipe();
        }

        //Liquid Breaker
        if(ConfigCrafting.LIQUID_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFluidCollector),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockBreaker));
            recipeLiquidCollector = Util.GetRecipes.lastIRecipe();
        }

        //Oil Generator
        if(ConfigCrafting.OIL_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockOilGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'B', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
            recipeOilGen = Util.GetRecipes.lastIRecipe();
        }

        //Coal Generator
        if(ConfigCrafting.COAL_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoalGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'B', new ItemStack(Items.coal, 1, Util.WILDCARD)));
            recipeCoalGen = Util.GetRecipes.lastIRecipe();
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
            recipeLeafGen = Util.GetRecipes.lastIRecipe();
        }

        //Enderpearl Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                "EE", "EE",
                'E', Items.ender_pearl));
        recipeEnderPearlBlock = Util.GetRecipes.lastIRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.ender_pearl, 4),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal())));

        //Stone Casing
        if(ConfigCrafting.STONE_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    'S', "stickWood"));
            recipeStoneCase = Util.GetRecipes.lastIRecipe();
        }

        //Quartz Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                "QQ", "QQ",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeQuartzBlock = Util.GetRecipes.lastIRecipe();

        //Fishing Net
        if(ConfigCrafting.FISHING_NET.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFishingNet),
                    "SSS", "SDS", "SSS",
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'S', Items.string));
            recipeFisher = Util.GetRecipes.lastIRecipe();
        }

        //Repairer
        if(ConfigCrafting.REPAIRER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockItemRepairer),
                    "DID", "OCO", "DID",
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
            recipeRepairer = Util.GetRecipes.lastIRecipe();
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
            recipeSolar = Util.GetRecipes.lastIRecipe();
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
            recipeHeatCollector = Util.GetRecipes.lastIRecipe();
        }

        //Quartz Pillar
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()),
                "Q", "Q",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeQuartzPillar = Util.GetRecipes.lastIRecipe();

        //Chiseled Quartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 2, TheMiscBlocks.QUARTZ_CHISELED.ordinal()),
                "Q", "Q",
                'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal())));
        recipeQuartzChiseled = Util.GetRecipes.lastIRecipe();

        //Inputter
        if(ConfigCrafting.INPUTTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockInputter),
                    "WWW", "CHC", "WWW",
                    'W', "plankWood",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeESD = Util.GetRecipes.lastIRecipe();

            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockInputterAdvanced),
                    InitBlocks.blockInputter,
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal())));
            recipeAdvancedESD = Util.GetRecipes.lastIRecipe();
        }

        //Crusher
        if(ConfigCrafting.CRUSHER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "MFC", "DQD", "CFM",
                    'M', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint)));
            recipeCrusher = Util.GetRecipes.lastIRecipe();
        }

        //Double Crusher
        if(ConfigCrafting.DOUBLE_CRUSHER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinderDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'R', InitBlocks.blockGrinder,
                    'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'P', new ItemStack(Blocks.piston)));
            recipeDoubleCrusher = Util.GetRecipes.lastIRecipe();
        }

        //Double Furnace
        if(ConfigCrafting.DOUBLE_FURNACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "PDC", "RFR", "CDP",
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'R', new ItemStack(Blocks.furnace),
                    'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
            recipeFurnace = Util.GetRecipes.lastIRecipe();
        }

        //Feeder
        if(ConfigCrafting.FEEDER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "DHD", "WCW",
                    'W', "plankWood",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'C', new ItemStack(Items.golden_carrot),
                    'H', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal())));
            recipeFeeder = Util.GetRecipes.lastIRecipe();
        }

        //Giant Chest
        if(ConfigCrafting.GIANT_CHEST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "WDW", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'D', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'W', "plankWood"));
            recipeCrate = Util.GetRecipes.lastIRecipe();
        }

        //Greenhouse Glass
        if(ConfigCrafting.GREENHOUSE_GLASS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGreenhouseGlass, 3),
                    "GSG", "SDS", "GSG",
                    'G', "blockGlass",
                    'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()),
                    'S', "treeSapling"));
            recipeGlass = Util.GetRecipes.lastIRecipe();
        }

        //Placer
        if(ConfigCrafting.PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPlacer),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal())));
            recipePlacer = Util.GetRecipes.lastIRecipe();
        }

        //Breaker
        if(ConfigCrafting.BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockBreaker),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal())));
            recipeBreaker = Util.GetRecipes.lastIRecipe();
        }

        //Dropper
        if(ConfigCrafting.DROPPER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockDropper),
                    "CBC", "CDR", "CBC",
                    'B', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                    'C', "cobblestone",
                    'D', Blocks.dropper,
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeDropper = Util.GetRecipes.lastIRecipe();
        }

        if(ConfigCrafting.LAMPS.isEnabled()){
            for(int i = 0; i < BlockColoredLamp.allLampTypes.length; i++){
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockColoredLamp, 6, i),
                        "GCG", "DQD", "GCG",
                        'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                        'G', "glowstone",
                        'D', "dye"+BlockColoredLamp.allLampTypes[i].name,
                        'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
                recipesLamps[i] = Util.GetRecipes.lastIRecipe();
            }

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLampPowerer, 4),
                    "XXX", "XLX", "XXX",
                    'X', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                    'L', new ItemStack(InitBlocks.blockColoredLamp, 1, Util.WILDCARD)));
            recipePowerer = Util.GetRecipes.lastIRecipe();
        }

    }

}
