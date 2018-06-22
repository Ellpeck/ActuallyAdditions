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

import java.util.ArrayList;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheDusts;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

public final class CrusherCrafting{

    public static final ArrayList<CrusherRecipe> MISC_RECIPES = new ArrayList<>();
    public static CrusherRecipe recipeIronHorseArmor;
    public static CrusherRecipe recipeGoldHorseArmor;
    public static CrusherRecipe recipeDiamondHorseArmor;

    public static void init(){
        ActuallyAdditions.LOGGER.info("Initializing Crusher Recipes...");
        
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 6, 15), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.REEDS), new ItemStack(Items.SUGAR, 3), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 4), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Items.DYE, 3, 11), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Items.DYE, 3, 1), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 1), new ItemStack(Items.DYE, 3, 12), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.DYE, 3, 13), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 3), new ItemStack(Items.DYE, 3, 7), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 4), new ItemStack(Items.DYE, 3, 1), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 5), new ItemStack(Items.DYE, 3, 14), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 6), new ItemStack(Items.DYE, 3, 7), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 7), new ItemStack(Items.DYE, 3, 9), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 8), new ItemStack(Items.DYE, 3, 7), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Items.DYE, 4, 11), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Items.DYE, 4, 13), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Items.DYE, 4, 1), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Items.DYE, 4, 9), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("oreRedstone"), new ItemStack(Items.REDSTONE, 10), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("oreLapis"), new ItemStack(Items.DYE, 12, 4), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("coal"), new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("oreCoal"), new ItemStack(Items.COAL,3), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("blockCoal"), new ItemStack(Items.COAL,9), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("oreQuartz"), new ItemStack(Items.QUARTZ,3), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new OreIngredient("cobblestone"), new ItemStack(Blocks.SAND), StackUtil.getEmpty(), 0);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Items.FLINT), new ItemStack(Items.FLINT), 50);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("stone", false), OreDictionary.getOres("cobblestone", false), 1, NonNullList.withSize(1, StackUtil.getEmpty()), 0, 0);

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.SUGAR, 2), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4), StackUtil.getEmpty(), 0);
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreNickel", false), OreDictionary.getOres("dustNickel", false), 2, OreDictionary.getOres("dustPlatinum", false), 1, 15);
        ActuallyAdditionsAPI.addCrusherRecipes(OreDictionary.getOres("oreIron", false), OreDictionary.getOres("dustIron", false), 2, OreDictionary.getOres("dustGold", false), 1, 20);

        ItemStack temp = OreDictionary.getOres("dustIron").get(0).copy();
        temp.setCount(6);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.IRON_HORSE_ARMOR), temp, StackUtil.getEmpty(), 0);
        recipeIronHorseArmor = RecipeUtil.lastCrusherRecipe();

        temp = OreDictionary.getOres("dustGold").get(0).copy();
        temp.setCount(6);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.GOLDEN_HORSE_ARMOR), temp, StackUtil.getEmpty(), 0);
        recipeGoldHorseArmor = RecipeUtil.lastCrusherRecipe();

        temp = OreDictionary.getOres("dustDiamond").get(0).copy();
        temp.setCount(6);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.DIAMOND_HORSE_ARMOR), temp, StackUtil.getEmpty(), 0);
        recipeDiamondHorseArmor = RecipeUtil.lastCrusherRecipe();

        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("oreNether", 6));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("orePoor", 4, "nugget"));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("denseore", 8));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("gem", 1));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ingot", 1));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ore", 2, "gem")); //Search for gems first so removeDuplicates doesn't clear gem recipes.
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ore", 2));

        CrusherRecipeRegistry.registerFinally();
    }
}
