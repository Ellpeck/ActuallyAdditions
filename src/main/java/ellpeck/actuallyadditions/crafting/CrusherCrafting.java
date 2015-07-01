package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.recipe.CrusherRecipeAutoRegistry;
import ellpeck.actuallyadditions.recipe.CrusherRecipeAutoRegistry.SearchCase;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CrusherCrafting{

    public static void init(){
        Util.logInfo("Initializing Crusher Recipes...");

        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10));
        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(InitItems.itemDust, 12, TheDusts.LAPIS.ordinal()));
        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()));
        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.coal_block), new ItemStack(InitItems.itemDust, 9, TheDusts.COAL.ordinal()));

        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone));
        CrusherRecipeManualRegistry.registerRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.sugar, 2));

        CrusherRecipeManualRegistry.registerRecipe("oreNickel", "dustNickel", "dustPlatinum", 15, 2, 1);
        CrusherRecipeManualRegistry.registerRecipe("oreIron", "dustIron", "dustGold", 20, 2, 1);

        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("oreNether", 6));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("denseore", 8));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("gem", 1));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("ingot", 1));
        CrusherRecipeAutoRegistry.searchCases.add(new SearchCase("ore", 2));

        CrusherRecipeAutoRegistry.exceptions.add("ingotBrick");
        CrusherRecipeAutoRegistry.exceptions.add("ingotBrickNether");

        CrusherRecipeAutoRegistry.registerFinally();
    }
}
