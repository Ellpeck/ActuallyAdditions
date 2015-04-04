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
                    "W W", "WFW", "WWW",
                    'W', "plankWood",
                    'F', TheMiscItems.MASHED_FOOD.getOredictName()));

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
                    "DID", "DCD", "DID",
                    'D', "gemDiamond",
                    'I', "ingotIron",
                    'C', Blocks.crafting_table));

        //Solar Panel
        if(ConfigValues.enableSolarRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceSolar),
                    "IBI", "BDB", "IBI",
                    'D', "blockDiamond",
                    'I', "ingotIron",
                    'B', new ItemStack(Blocks.iron_bars)));

        //Heat Collector
        if(ConfigValues.enableHeatCollectorRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockHeatCollector),
                    "BRB", "LDL", "BRB",
                    'D', "blockDiamond",
                    'R', new ItemStack(Items.repeater),
                    'L', new ItemStack(Items.lava_bucket),
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
                    "WWW", "WHW", "WWW",
                    'W', "plankWood",
                    'H', new ItemStack(Blocks.hopper)));

            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.blockInputterAdvanced),
                    ((INameableItem)InitBlocks.blockInputter).getOredictName(),
                    "gemQuartz",
                    TheMiscItems.QUARTZ.getOredictName(),
                    "dustRedstone"));
        }

        //Crusher
        if(ConfigValues.enableCrusherRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "CFC", "CPC", "CFC",
                    'C', "cobblestone",
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint)));

        //Double Crusher
        if(ConfigValues.enableCrusherDoubleRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGrinderDouble),
                    "CCC", "RPR", "CCC",
                    'C', "cobblestone",
                    'R', ((INameableItem)InitBlocks.blockGrinder).getOredictName(),
                    'P', new ItemStack(Blocks.piston)));

        //Double Furnace
        if(ConfigValues.enableFurnaceDoubleRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "CCC", "RPR", "CCC",
                    'C', "cobblestone",
                    'R', new ItemStack(Blocks.furnace),
                    'P', "ingotBrick"));

        //Feeder
        if(ConfigValues.enableFeederRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "WHW", "WCW",
                    'W', "plankWood",
                    'C', new ItemStack(Items.golden_carrot),
                    'H', "cropWheat"));

        //Giant Chest
        if(ConfigValues.enableGiantChestRecipe)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "W W", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'W', "plankWood"));

    }

}
