/*
 * This file ("CrusherCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;

public final class CrusherCrafting{

    public static final ArrayList<CrusherRecipe> MISC_RECIPES = new ArrayList<CrusherRecipe>();
    public static CrusherRecipe recipeIronHorseArmor;
    public static CrusherRecipe recipeGoldHorseArmor;
    public static CrusherRecipe recipeDiamondHorseArmor;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crusher Recipes...");

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 6, 15), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.REEDS), new ItemStack(Items.SUGAR, 3), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Items.DYE, 3, 11), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Items.DYE, 3, 1), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 1), new ItemStack(Items.DYE, 3, 12), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.DYE, 3, 13), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 3), new ItemStack(Items.DYE, 3, 7), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 4), new ItemStack(Items.DYE, 3, 1), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 5), new ItemStack(Items.DYE, 3, 14), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 6), new ItemStack(Items.DYE, 3, 7), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 7), new ItemStack(Items.DYE, 3, 9), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 8), new ItemStack(Items.DYE, 3, 7), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Items.DYE, 4, 11), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Items.DYE, 4, 13), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Items.DYE, 4, 1), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Items.DYE, 4, 9), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreRedstone", false), OreDictionary.getOres("dustRedstone", false), 10, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreLapis", false), OreDictionary.getOres("gemLapis", false), 12, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("coal", false), OreDictionary.getOres("dustCoal", false), 1, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreCoal", false), OreDictionary.getOres("coal", false), 3, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("blockCoal", false), OreDictionary.getOres("coal", false), 9, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreQuartz", false), OreDictionary.getOres("gemQuartz", false), 3, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("cobblestone", false), OreDictionary.getOres("sand", false), 1, null, 0, 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Items.FLINT), new ItemStack(Items.FLINT), 50);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("stone", false), OreDictionary.getOres("cobblestone", false), 1, null, 0, 0);

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.SUGAR, 2), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4), null, 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreNickel", false), OreDictionary.getOres("dustNickel", false), 2, OreDictionary.getOres("dustPlatinum", false), 1, 15);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreIron", false), OreDictionary.getOres("dustIron", false), 2, OreDictionary.getOres("dustGold", false), 1, 20);

        ActuallyAdditionsAPI.addCrusherRecipes(Collections.singletonList(new ItemStack(Items.IRON_HORSE_ARMOR)), OreDictionary.getOres("dustIron", false), 8, null, 0, 0);
        recipeIronHorseArmor = RecipeUtil.lastCrusherRecipe();

        ActuallyAdditionsAPI.addCrusherRecipes(Collections.singletonList(new ItemStack(Items.GOLDEN_HORSE_ARMOR)), OreDictionary.getOres("dustGold"), 8, null, 0, 0);
        recipeGoldHorseArmor = RecipeUtil.lastCrusherRecipe();

        ActuallyAdditionsAPI.addCrusherRecipes(Collections.singletonList(new ItemStack(Items.DIAMOND_HORSE_ARMOR)), OreDictionary.getOres("dustDiamond"), 8, null, 0, 0);
        recipeDiamondHorseArmor = RecipeUtil.lastCrusherRecipe();

        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("oreNether", 6));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("orePoor", 4, "nugget"));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("denseore", 8));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("gem", 1));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ingot", 1));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ore", 2));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ore", 2, "gem")); //If no dust is found for certain ores, make gems directly

        CrusherRecipeRegistry.registerFinally();
    }
}
