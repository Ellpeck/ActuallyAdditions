package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BlockCrafting{

    public static void init(){

        //Compost
        if(ConfigCrafting.COMPOST.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "W W", "WCW",
                    'W', "plankWood",
                    'C', TheMiscBlocks.WOOD_CASING.getOredictName()));

        //Charcoal Block
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()),
                "CCC", "CCC", "CCC",
                'C', new ItemStack(Items.coal, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.coal, 9, 1),
                new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.CHARCOAL_BLOCK.ordinal()));

        //Wood Casing
        if(ConfigCrafting.WOOD_CASING.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "plankWood",
                    'R', "dustRedstone",
                    'S', "stickWood"));

        //Canola Press
        if(ConfigCrafting.CANOLA_PRESS.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCanolaPress),
                    "CHC", "CDC", "CRC",
                    'C', "cobblestone",
                    'H', Blocks.hopper,
                    'R', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'D', TheMiscItems.CANOLA.getOredictName()));

        //Fermenting Barrel
        if(ConfigCrafting.FERMENTING_BARREL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFermentingBarrel),
                    "CHC", "CDC", "CRC",
                    'C', "logWood",
                    'H', Blocks.hopper,
                    'R', TheMiscBlocks.WOOD_CASING.getOredictName(),
                    'D', TheMiscItems.CANOLA.getOredictName()));

        //Phantomface
        if(ConfigCrafting.PHANTOMFACE.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPhantomface),
                    "ECE", "EBE", "ESE",
                    'E', Items.ender_eye,
                    'C', Blocks.chest,
                    'S', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'B', TheMiscBlocks.ENDERPEARL_BLOCK.getOredictName()));

        //Oil Generator
        if(ConfigCrafting.OIL_GENERATOR.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockOilGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'B', InitItems.itemBucketOil));

        //Coal Generator
        if(ConfigCrafting.COAL_GENERATOR.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCoalGenerator),
                    "CRC", "CBC", "CRC",
                    'C', "cobblestone",
                    'R', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'B', "coal"));

        //Enderpearl Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                "EE", "EE",
                'E', Items.ender_pearl));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.ender_pearl, 4),
                TheMiscBlocks.ENDERPEARL_BLOCK.getOredictName()));

        //Stone Casing
        if(ConfigCrafting.STONE_CASING.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()),
                    "WSW", "SRS", "WSW",
                    'W', "cobblestone",
                    'R', "dustRedstone",
                    'S', "stickWood"));

        //Quartz Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()),
                "QQ", "QQ",
                'Q', TheMiscItems.QUARTZ.getOredictName()));

        //Fishing Net
        if(ConfigCrafting.FISHING_NET.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFishingNet),
                    "SSS", "SDS", "SSS",
                    'D', "gemDiamond",
                    'S', Items.string));

        //Repairer
        if(ConfigCrafting.REPAIRER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockItemRepairer),
                    "DID", "OCO", "DID",
                    'D', "gemDiamond",
                    'I', "ingotIron",
                    'O', TheMiscItems.COIL.getOredictName(),
                    'C', TheMiscBlocks.STONE_CASING.getOredictName()));

        //Solar Panel
        if(ConfigCrafting.SOLAR_PANEL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceSolar),
                    "IQI", "CDC", "IBI",
                    'D', "blockDiamond",
                    'I', "ingotIron",
                    'Q', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'B', new ItemStack(Blocks.iron_bars)));

        //Heat Collector
        if(ConfigCrafting.HEAT_COLLECTOR.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockHeatCollector),
                    "BRB", "CDC", "BQB",
                    'D', "gemDiamond",
                    'R', new ItemStack(Items.repeater),
                    'Q', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'L', new ItemStack(Items.lava_bucket),
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'B', new ItemStack(Blocks.iron_bars)));

        //Quartz Pillar
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()),
                "Q", "Q",
                'Q', TheMiscItems.QUARTZ.getOredictName()));

        //Chiseled Quartz
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 2, TheMiscBlocks.QUARTZ_CHISELED.ordinal()),
                "Q", "Q",
                'Q', TheMiscBlocks.QUARTZ.getOredictName()));

        //Inputter
        if(ConfigCrafting.INPUTTER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockInputter),
                    "WWW", "CHC", "WWW",
                    'W', "plankWood",
                    'C', TheMiscBlocks.WOOD_CASING.getOredictName(),
                    'H', new ItemStack(Blocks.hopper)));

            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockInputterAdvanced),
                    ((INameableItem)InitBlocks.blockInputter).getOredictName(),
                    TheMiscItems.COIL_ADVANCED.getOredictName(),
                    TheMiscItems.QUARTZ.getOredictName(),
                    "dustRedstone"));
        }

        //Crusher
        if(ConfigCrafting.CRUSHER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "CFC", "DQD", "CFC",
                    'C', "cobblestone",
                    'D', TheMiscItems.COIL.getOredictName(),
                    'Q', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint)));

        //Double Crusher
        if(ConfigCrafting.DOUBLE_CRUSHER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinderDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'R', ((INameableItem)InitBlocks.blockGrinder).getOredictName(),
                    'F', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'P', new ItemStack(Blocks.piston)));

        //Double Furnace
        if(ConfigCrafting.COMPOST.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', TheMiscItems.COIL.getOredictName(),
                    'R', new ItemStack(Blocks.furnace),
                    'F', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'P', "ingotBrick"));

        //Feeder
        if(ConfigCrafting.DOUBLE_FURNACE.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "DHD", "WCW",
                    'W', "plankWood",
                    'D', TheMiscItems.COIL.getOredictName(),
                    'C', new ItemStack(Items.golden_carrot),
                    'H', TheMiscBlocks.WOOD_CASING.getOredictName()));

        //Giant Chest
        if(ConfigCrafting.GIANT_CHEST.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "WDW", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'D', TheMiscBlocks.WOOD_CASING.getOredictName(),
                    'W', "plankWood"));

        //Greenhouse Glass
        if(ConfigCrafting.GREENHOUSE_GLASS.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGreenhouseGlass),
                    "GSG", "SDS", "GSG",
                    'G', "blockGlass",
                    'D', "gemDiamond",
                    'S', "treeSapling"));

        //Placer
        if(ConfigCrafting.PLACER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockPlacer),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', TheMiscItems.COIL.getOredictName(),
                    'P', Blocks.piston));

        //Breaker
        if(ConfigCrafting.BREAKER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockBreaker),
                    "CCC", "CRP", "CCC",
                    'C', "cobblestone",
                    'R', TheMiscItems.COIL.getOredictName(),
                    'P', Items.diamond_pickaxe));

        //Dropper
        if(ConfigCrafting.DROPPER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockDropper),
                    "CCC", "CDR", "CCC",
                    'C', "cobblestone",
                    'D', Blocks.dropper,
                    'R', TheMiscItems.COIL_ADVANCED.getOredictName()));

    }

}
