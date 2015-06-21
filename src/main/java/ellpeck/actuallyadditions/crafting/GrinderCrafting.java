package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.recipe.GrinderRecipeRegistry;
import ellpeck.actuallyadditions.recipe.GrinderRecipeRegistry.SearchCase;
import ellpeck.actuallyadditions.recipe.GrinderRecipes;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GrinderCrafting{

    public static void init(){
        Util.logInfo("Initializing Crusher Recipes...");

        GrinderRecipes.registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10));
        GrinderRecipes.registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(InitItems.itemDust, 12, TheDusts.LAPIS.ordinal()));
        GrinderRecipes.registerRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()));
        GrinderRecipes.registerRecipe(new ItemStack(Blocks.coal_block), new ItemStack(InitItems.itemDust, 9, TheDusts.COAL.ordinal()));

        GrinderRecipes.registerRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
        GrinderRecipes.registerRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
        GrinderRecipes.registerRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone));
        GrinderRecipes.registerRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.sugar, 2));

        GrinderRecipes.registerRecipe("oreNickel", "dustNickel", "dustPlatinum", 30, 2);
        GrinderRecipes.registerRecipe("oreIron", "dustIron", "dustGold", 20, 2);

        GrinderRecipeRegistry.searchCases.add(new SearchCase("oreNether", 6));
        GrinderRecipeRegistry.searchCases.add(new SearchCase("denseore", 8));
        GrinderRecipeRegistry.searchCases.add(new SearchCase("gem", 1));
        GrinderRecipeRegistry.searchCases.add(new SearchCase("ingot", 1));
        GrinderRecipeRegistry.searchCases.add(new SearchCase("ore", 2));

        GrinderRecipeRegistry.exceptions.add("ingotBrick");
        GrinderRecipeRegistry.exceptions.add("ingotBrickNether");

        GrinderRecipeRegistry.registerFinally();
    }
}
