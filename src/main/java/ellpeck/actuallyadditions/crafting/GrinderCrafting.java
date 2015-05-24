package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
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

        grindRec.registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10));
        grindRec.registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(InitItems.itemDust, 12, TheDusts.LAPIS.ordinal()));
        grindRec.registerRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()));
        grindRec.registerRecipe(new ItemStack(Blocks.coal_block), new ItemStack(InitItems.itemDust, 9, TheDusts.COAL.ordinal()));

        grindRec.registerRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
        grindRec.registerRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
        grindRec.registerRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone));
        grindRec.registerRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.sugar, 2));

        grindRec.registerRecipe("oreNickel", "dustNickel", "dustPlatinum", 30, 2);
        grindRec.registerRecipe("oreIron", "dustIron", "dustGold", 20, 2);

        grindRecHan.searchCases.add(new SearchCase("oreNether", 6));
        grindRecHan.searchCases.add(new SearchCase("denseore", 8));
        grindRecHan.searchCases.add(new SearchCase("ingot", 1));
        grindRecHan.searchCases.add(new SearchCase("gem", 1));
        grindRecHan.searchCases.add(new SearchCase("ore", 2));

        grindRecHan.exceptions.add("ingotBrick");
        grindRecHan.exceptions.add("ingotBrickNether");

        grindRecHan.registerFinally();
    }
}
