package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.ConfigValues;
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
        if(ConfigValues.enableCompostRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "W W", "WCW",
                    'W', "plankWood",
                    'C', TheMiscBlocks.WOOD_CASING.getOredictName()));

        //Wood Casing
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()),
                "WSW", "SRS", "WSW",
                'W', "plankWood",
                'R', "dustRedstone",
                'S', "stickWood"));

        //Stone Casing
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
        if(ConfigValues.enableFishingNetRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFishingNet),
                    "SSS", "SDS", "SSS",
                    'D', "gemDiamond",
                    'S', Items.string));

        //Repairer
        if(ConfigValues.enableRepairerRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockItemRepairer),
                    "DID", "OCO", "DID",
                    'D', "gemDiamond",
                    'I', "ingotIron",
                    'O', TheMiscItems.COIL.getOredictName(),
                    'C', TheMiscBlocks.STONE_CASING.getOredictName()));

        //Solar Panel
        if(ConfigValues.enableSolarRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceSolar),
                    "IQI", "CDC", "IBI",
                    'D', "blockDiamond",
                    'I', "ingotIron",
                    'Q', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'B', new ItemStack(Blocks.iron_bars)));

        //Heat Collector
        if(ConfigValues.enableHeatCollectorRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockHeatCollector),
                    "BRB", "CDC", "BQB",
                    'D', "blockDiamond",
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
        if(ConfigValues.enableInputterRecipe){
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
        if(ConfigValues.enableCrusherRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "CFC", "DQD", "CFC",
                    'C', "cobblestone",
                    'D', TheMiscItems.COIL.getOredictName(),
                    'Q', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint)));

        //Double Crusher
        if(ConfigValues.enableCrusherDoubleRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinderDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'R', ((INameableItem)InitBlocks.blockGrinder).getOredictName(),
                    'F', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'P', new ItemStack(Blocks.piston)));

        //Double Furnace
        if(ConfigValues.enableFurnaceDoubleRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "CDC", "RFR", "CDC",
                    'C', "cobblestone",
                    'D', TheMiscItems.COIL.getOredictName(),
                    'R', new ItemStack(Blocks.furnace),
                    'F', TheMiscBlocks.STONE_CASING.getOredictName(),
                    'P', "ingotBrick"));

        //Feeder
        if(ConfigValues.enableFeederRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "DHD", "WCW",
                    'W', "plankWood",
                    'D', TheMiscItems.COIL.getOredictName(),
                    'C', new ItemStack(Items.golden_carrot),
                    'H', TheMiscBlocks.WOOD_CASING.getOredictName()));

        //Giant Chest
        if(ConfigValues.enableGiantChestRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "WDW", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'D', TheMiscBlocks.WOOD_CASING.getOredictName(),
                    'W', "plankWood"));

    }

}
