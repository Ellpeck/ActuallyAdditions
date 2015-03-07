package ellpeck.someprettyrandomstuff.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockCrafting{

    public static void init(){

        //Compost
        if(ConfigValues.enableCompostRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "WFW", "WWW",
                    'W', new ItemStack(Blocks.planks, 1, Util.WILDCARD),
                    'F', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()));

        //Crusher
        if(ConfigValues.enableCrusherRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockGrinder),
                    "CFC", "CPC", "CFC",
                    'C', new ItemStack(Blocks.cobblestone),
                    'P', new ItemStack(Blocks.piston),
                    'F', new ItemStack(Items.flint));

        //Double Crusher
        if(ConfigValues.enableCrusherDoubleRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockGrinderDouble),
                    "CCC", "RPR", "CCC",
                    'C', new ItemStack(Blocks.cobblestone),
                    'R', new ItemStack(InitBlocks.blockGrinder),
                    'P', new ItemStack(Blocks.piston));

        //Double Furnace
        if(ConfigValues.enableFurnaceDoubleRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockFurnaceDouble),
                    "CCC", "RPR", "CCC",
                    'C', new ItemStack(Blocks.cobblestone),
                    'R', new ItemStack(Blocks.furnace),
                    'P', new ItemStack(Items.brick));

        //Feeder
        if(ConfigValues.enableFeederRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockFeeder),
                    "WCW", "WHW", "WCW",
                    'W', new ItemStack(Blocks.planks, 1, Util.WILDCARD),
                    'C', new ItemStack(Items.golden_carrot),
                    'H', new ItemStack(Items.wheat));

        //Giant Chest
        if(ConfigValues.enableGiantChestRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockGiantChest),
                    "CWC", "W W", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'W', new ItemStack(Blocks.planks, 1, Util.WILDCARD));

    }

}
