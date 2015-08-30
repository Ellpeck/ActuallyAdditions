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

    public static void init(){

        //Smiley Cloud
        if(ConfigCrafting.CLOUD.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockSmileyCloud),
                    " W ", "WXW", " W ",
                    'W', new ItemStack(Blocks.wool, 1, Util.WILDCARD),
                    'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal())));
            recipeSmileyCloud = Util.lastIRecipe();
        }

        //Compost
        if(ConfigCrafting.COMPOST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "W W", "WCW",
                    'W', "plankWood",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal())));
        }

        //XP Solidifier
        if(ConfigCrafting.XP_SOLIDIFIER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockXPSolidifier),
                    "XXX", "DCD", "XXX",
                    'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()),
                    'D', "blockDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        }

        //Charcoal Block
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()),
                "CCC", "CCC", "CCC",
                'C', new ItemStack(Items.coal, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.coal, 9, 1),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()));

        //Wood Casing
        if(ConfigCrafting.WOOD_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "plankWood",
                    'R', "logWood",
                    'S', "stickWood"));
            recipeCase = Util.lastIRecipe();
        }

        //Ender Casing
        if(ConfigCrafting.ENDER_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                    'S', Blocks.obsidian));
            recipeEnderCase = Util.lastIRecipe();
        }

        //Phantom Booster
        if(ConfigCrafting.PHANTOM_BOOSTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomBooster),
                    "RDR", "DCD", "RDR",
                    'R', "dustRedstone",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
            recipePhantomBooster = Util.lastIRecipe();
        }

        //Coffee Machine
        if(ConfigCrafting.COFFEE_MACHINE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoffeeMachine),
                    " C ", " S ", "A A",
                    'C', InitItems.itemCoffeeBean,
                    'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal())));
            recipeCoffeeMachine = Util.lastIRecipe();
        }

        //Energizer
        if(ConfigCrafting.ENERGIZER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockEnergizer),
                    "I I", "CAC", "I I",
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal())));
        }

        //Energizer
        if(ConfigCrafting.ENERVATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockEnervator),
                    " I ", "CAC", " I ",
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal())));
        }

        //Lava Factory
        if(ConfigCrafting.LAVA_FACTORY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLavaFactoryController),
                    "SCS", "ISI", "LLL",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'I', "blockIron",
                    'L', Items.lava_bucket));
        }

        //Casing
        if(ConfigCrafting.CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 32, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal()),
                    "ICI",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'I', "blockIron"));
        }

        //Canola Press
        if(ConfigCrafting.CANOLA_PRESS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCanolaPress),
                    "CHC", "CDC", "CRC",
                    'C', "cobblestone",
                    'H', Blocks.hopper,
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
        }

        //Ore Magnet
        if(ConfigCrafting.ORE_MAGNET.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockOreMagnet),
                    "SSS", "CBC", "ISI",
                    'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal()),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'B', new ItemStack(InitItems.itemBatteryDouble),
                    'I', new ItemStack(Blocks.iron_block)));
        }

        //Fermenting Barrel
        if(ConfigCrafting.FERMENTING_BARREL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFermentingBarrel),
                    "CHC", "CDC", "CRC",
                    'C', "logWood",
                    'H', Blocks.hopper,
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
        }

        //Phantomface
        if(ConfigCrafting.PHANTOMFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomface),
                    " C ", "EBE", " S ",
                    'E', Items.ender_eye,
                    'C', Blocks.chest,
                    'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal())));
            recipePhantomface = Util.lastIRecipe();
        }

        //Phantom Placer
        if(ConfigCrafting.PHANTOM_PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockPhantomPlacer),
                    InitBlocks.blockPlacer,
                    InitBlocks.blockPhantomface));
            recipePhantomPlacer = Util.lastIRecipe();
        }

        //Phantom Breaker
        if(ConfigCrafting.PHANTOM_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockPhantomBreaker),
                    InitBlocks.blockBreaker,
                    InitBlocks.blockPhantomface));
            recipePhantomBreaker = Util.lastIRecipe();
        }

        //Phantom Energyface
        if(ConfigCrafting.PHANTOM_ENERGYFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomEnergyface),
                    " R ", "RFR", " R ",
                    'R', "dustRedstone",
                    'F', InitBlocks.blockPhantomface));
            recipeEnergyface = Util.lastIRecipe();
        }

        //Phantom Liquiface
        if(ConfigCrafting.PHANTOM_LIQUIFACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomLiquiface),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockPhantomface));
            recipeLiquiface = Util.lastIRecipe();
        }

        //Liquid Placer
        if(ConfigCrafting.LIQUID_PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFluidPlacer),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockPlacer));
            recipeLiquidPlacer = Util.lastIRecipe();
        }

        //Liquid Breaker
        if(ConfigCrafting.LIQUID_BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFluidCollector),
                    "RFR",
                    'R', Items.bucket,
                    'F', InitBlocks.blockBreaker));
            recipeLiquidCollector = Util.lastIRecipe();
        }

        //Oil Generator
        if(ConfigCrafting.OIL_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockOilGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'B', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())));
        }

        //Coal Generator
        if(ConfigCrafting.COAL_GENERATOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoalGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'B', new ItemStack(Items.coal, 1, Util.WILDCARD)));
        }

        //Enderpearl Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                "EE", "EE",
                'E', Items.ender_pearl));
        recipeEnderPearlBlock = Util.lastIRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.ender_pearl, 4),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal())));

        //Stone Casing
        if(ConfigCrafting.STONE_CASING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    'S', "stickWood"));
            recipeStoneCase = Util.lastIRecipe();
        }

        //Quartz Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                "QQ", "QQ",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));

        //Fishing Net
        if(ConfigCrafting.FISHING_NET.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFishingNet),
                    "SSS", "SDS", "SSS",
                    'D', "gemDiamond",
                    'S', Items.string));
        }

        //Repairer
        if(ConfigCrafting.REPAIRER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockItemRepairer),
                    "DID", "OCO", "DID",
                    'D', "gemDiamond",
                    'I', "ingotIron",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
        }

        //Solar Panel
        if(ConfigCrafting.SOLAR_PANEL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceSolar),
                    "IQI", "CDC", "IBI",
                    'D', "blockDiamond",
                    'I', "ingotIron",
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(Blocks.iron_bars)));
        }

        //Heat Collector
        if(ConfigCrafting.HEAT_COLLECTOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockHeatCollector),
                    "BRB", "CDC", "BQB",
                    'D', "gemDiamond",
                    'R', new ItemStack(Items.repeater),
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'L', new ItemStack(Items.lava_bucket),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'B', new ItemStack(Blocks.iron_bars)));
        }

        //Quartz Pillar
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()),
                "Q", "Q",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));

        //Chiseled Quartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 2, TheMiscBlocks.QUARTZ_CHISELED.ordinal()),
                "Q", "Q",
                'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal())));

        //Inputter
        if(ConfigCrafting.INPUTTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockInputter),
                    "WWW", "CHC", "WWW",
                    'W', "plankWood",
                    'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'H', new ItemStack(Blocks.hopper)));
            recipeESD = Util.lastIRecipe();

            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockInputterAdvanced),
                    InitBlocks.blockInputter,
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    "dustRedstone"));
            recipeAdvancedESD = Util.lastIRecipe();
        }

        //Crusher
        if(ConfigCrafting.CRUSHER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "CFC", "DQD", "CFC",
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint)));
            recipeCrusher = Util.lastIRecipe();
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
            recipeDoubleCrusher = Util.lastIRecipe();
        }

        //Double Furnace
        if(ConfigCrafting.COMPOST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'R', new ItemStack(Blocks.furnace),
                    'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    'P', "ingotBrick"));
            recipeFurnace = Util.lastIRecipe();
        }

        //Feeder
        if(ConfigCrafting.DOUBLE_FURNACE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "DHD", "WCW",
                    'W', "plankWood",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'C', new ItemStack(Items.golden_carrot),
                    'H', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal())));
        }

        //Giant Chest
        if(ConfigCrafting.GIANT_CHEST.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "WDW", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'D', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    'W', "plankWood"));
        }

        //Greenhouse Glass
        if(ConfigCrafting.GREENHOUSE_GLASS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGreenhouseGlass, 3),
                    "GSG", "SDS", "GSG",
                    'G', "blockGlass",
                    'D', Blocks.obsidian,
                    'S', "treeSapling"));
        }

        //Placer
        if(ConfigCrafting.PLACER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPlacer),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'P', Blocks.piston));
            recipePlacer = Util.lastIRecipe();
        }

        //Breaker
        if(ConfigCrafting.BREAKER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockBreaker),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'P', Items.iron_pickaxe));
            recipeBreaker = Util.lastIRecipe();
        }

        //Dropper
        if(ConfigCrafting.DROPPER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockDropper),
                    "CCC", "CDR", "CCC",
                    'C', "cobblestone",
                    'D', Blocks.dropper,
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        }

        if(ConfigCrafting.LAMPS.isEnabled()){
            for(int i = 0; i < BlockColoredLamp.allLampTypes.length; i++){
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockColoredLamp, 6, i),
                        "GGG", "DQD", "GGG",
                        'G', "glowstone",
                        'D', "dye"+BlockColoredLamp.allLampTypes[i].name,
                        'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
            }

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockLampPowerer, 4),
                    "XXX", "XLX", "XXX",
                    'X', "dustRedstone",
                    'L', new ItemStack(InitBlocks.blockColoredLamp, 1, Util.WILDCARD)));
        }

    }

}
