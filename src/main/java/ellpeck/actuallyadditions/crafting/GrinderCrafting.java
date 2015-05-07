package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.recipe.GrinderRecipeHandler;
import ellpeck.actuallyadditions.recipe.GrinderRecipeHandler.SearchCase;
import ellpeck.actuallyadditions.recipe.GrinderRecipes;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GrinderCrafting{

    private static GrinderRecipeHandler grindRecHan = GrinderRecipeHandler.instance();
    private static GrinderRecipes grindRec = GrinderRecipes.instance();

    public static void init(){
        Util.logInfo("Initializing Crusher Recipes...");

        grindRec.registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10), null, 0);
        grindRec.registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(InitItems.itemDust, 12, TheDusts.LAPIS.ordinal()), null, 0);

        grindRec.registerRecipe("cobblestone", "sand");
        grindRec.registerRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
        grindRec.registerRecipe("stone", "cobblestone");

        grindRec.registerRecipe("oreNickel", "dustNickel", "dustPlatinum", 30, 2);
        grindRec.registerRecipe("oreIron", "dustIron", "dustGold", 20, 2);

        grindRecHan.searchCases.add(new SearchCase("ore", 2));
        grindRecHan.searchCases.add(new SearchCase("oreNether", 6));
        grindRecHan.searchCases.add(new SearchCase("denseore", 8));
        grindRecHan.searchCases.add(new SearchCase("gem", 1));
        grindRecHan.searchCases.add(new SearchCase("ingot", 1));
        grindRecHan.exceptions.add("ingotBrick");
        grindRecHan.exceptions.add("ingotBrickNether");

        grindRecHan.registerFinally();
    }
}
