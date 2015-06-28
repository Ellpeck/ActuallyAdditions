package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.recipe.GrinderRecipeAutoRegistry;
import ellpeck.actuallyadditions.recipe.GrinderRecipeAutoRegistry.SearchCase;
import ellpeck.actuallyadditions.recipe.GrinderRecipeManualRegistry;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GrinderCrafting{

    public static void init(){
        Util.logInfo("Initializing Crusher Recipes...");

        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10));
        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(InitItems.itemDust, 12, TheDusts.LAPIS.ordinal()));
        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()));
        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.coal_block), new ItemStack(InitItems.itemDust, 9, TheDusts.COAL.ordinal()));

        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone));
        GrinderRecipeManualRegistry.registerRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.sugar, 2));

        GrinderRecipeManualRegistry.registerRecipe("oreNickel", "dustNickel", "dustPlatinum", 30, 2);
        GrinderRecipeManualRegistry.registerRecipe("oreIron", "dustIron", "dustGold", 20, 2);

        GrinderRecipeAutoRegistry.searchCases.add(new SearchCase("oreNether", 6));
        GrinderRecipeAutoRegistry.searchCases.add(new SearchCase("denseore", 8));
        GrinderRecipeAutoRegistry.searchCases.add(new SearchCase("gem", 1));
        GrinderRecipeAutoRegistry.searchCases.add(new SearchCase("ingot", 1));
        GrinderRecipeAutoRegistry.searchCases.add(new SearchCase("ore", 2));

        GrinderRecipeAutoRegistry.exceptions.add("ingotBrick");
        GrinderRecipeAutoRegistry.exceptions.add("ingotBrickNether");

        GrinderRecipeAutoRegistry.registerFinally();
    }
}
