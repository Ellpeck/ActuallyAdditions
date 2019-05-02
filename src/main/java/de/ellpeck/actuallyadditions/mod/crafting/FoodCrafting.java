/*
 * This file ("FoodCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.crafting.RecipeHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class FoodCrafting {

    public static IRecipe recipePizza;
    public static IRecipe recipeHamburger;
    public static IRecipe recipeBigCookie;
    public static IRecipe recipeSubSandwich;
    public static IRecipe recipeFrenchFry;
    public static IRecipe recipeFrenchFries;
    public static IRecipe recipeFishNChips;
    public static IRecipe recipeCheese;
    public static IRecipe recipePumpkinStew;
    public static IRecipe recipeCarrotJuice;
    public static IRecipe recipeSpaghetti;
    public static IRecipe recipeNoodle;
    public static IRecipe recipeChocolate;
    public static IRecipe recipeChocolateCake;
    public static IRecipe recipeToast;
    public static IRecipe recipeChocolateToast;
    public static IRecipe recipeBacon;

    public static void init() {

        Ingredient knife = Ingredient.fromItem(InitItems.itemKnife);

        //Rice Bread
        GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RICE_DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal()), 1F);

        //Bacon
        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemFoods, 3, TheFoods.BACON.ordinal()), knife, new ItemStack(Items.COOKED_PORKCHOP));
        recipeBacon = RecipeUtil.lastIRecipe();

        //Baguette
        GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 1F);

        //Pizza
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()), "HKH", "MCF", " D ", 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), 'M', new ItemStack(Blocks.BROWN_MUSHROOM), 'C', "cropCarrot", 'F', new ItemStack(Items.COOKED_FISH, 1, Util.WILDCARD), 'K', knife, 'H', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()));
        recipePizza = RecipeUtil.lastIRecipe();

        //Hamburger
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()), "KT ", "CB ", " T ", 'T', new ItemStack(InitItems.itemFoods, 1, TheFoods.TOAST.ordinal()), 'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()), 'K', knife, 'B', new ItemStack(Items.COOKED_BEEF));
        recipeHamburger = RecipeUtil.lastIRecipe();

        //Big Cookie
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.BIG_COOKIE.ordinal()), "DCD", "CDC", "DCD", 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), 'C', new ItemStack(Items.DYE, 1, 3));
        recipeBigCookie = RecipeUtil.lastIRecipe();

        //Sub Sandwich
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()), "KCP", "FB ", "PCP", 'P', new ItemStack(Items.PAPER), 'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()), 'F', new ItemStack(Items.COOKED_BEEF, 1, Util.WILDCARD), 'B', new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 'K', knife);
        recipeSubSandwich = RecipeUtil.lastIRecipe();

        //French Fry
        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemFoods, 2, TheFoods.FRENCH_FRY.ordinal()), new ItemStack(Items.BAKED_POTATO), knife);
        recipeFrenchFry = RecipeUtil.lastIRecipe();

        //French Fries
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()), "FFF", " P ", 'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()), 'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()));
        recipeFrenchFries = RecipeUtil.lastIRecipe();

        //Fish N Chips
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()), "FIF", " P ", 'I', new ItemStack(Items.COOKED_FISH, 1, Util.WILDCARD), 'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()), 'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()));
        recipeFishNChips = RecipeUtil.lastIRecipe();

        //Cheese
        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()), new ItemStack(Items.MILK_BUCKET), new ItemStack(Items.EGG)); //I don't know if this makes any actual sense, but whatever
        recipeCheese = RecipeUtil.lastIRecipe();

        //Pumpkin Stew
        RecipeHandler.addShapedRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PUMPKIN_STEW.ordinal()), "P", "B", 'P', new ItemStack(Blocks.PUMPKIN), 'B', new ItemStack(Items.BOWL));
        recipePumpkinStew = RecipeUtil.lastIRecipe();

        //Carrot Juice
        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CARROT_JUICE.ordinal()), new ItemStack(Items.GLASS_BOTTLE), "cropCarrot", knife);
        recipeCarrotJuice = RecipeUtil.lastIRecipe();

        //Spaghetti
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SPAGHETTI.ordinal()), "NNN", " B ", 'N', new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()), 'B', new ItemStack(Items.BOWL));
        recipeSpaghetti = RecipeUtil.lastIRecipe();

        //Noodle
        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()), "cropWheat", knife);
        recipeNoodle = RecipeUtil.lastIRecipe();

        //Chocolate
        RecipeHandler.addShapedRecipe(new ItemStack(InitItems.itemFoods, 3, TheFoods.CHOCOLATE.ordinal()), "C C", "CMC", "C C", 'C', new ItemStack(Items.DYE, 1, 3), 'M', new ItemStack(Items.MILK_BUCKET));
        recipeChocolate = RecipeUtil.lastIRecipe();

        //Chocolate Cake
        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHOCOLATE_CAKE.ordinal()), "MMM", "CCC", "EDS", 'M', new ItemStack(Items.MILK_BUCKET), 'E', new ItemStack(Items.EGG), 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), 'S', new ItemStack(Items.SUGAR), 'C', new ItemStack(Items.DYE, 1, 3));
        recipeChocolateCake = RecipeUtil.lastIRecipe();

        //Toast
        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 2, TheFoods.TOAST.ordinal()), new ItemStack(Items.BREAD));
        recipeToast = RecipeUtil.lastIRecipe();

        //Chocolate Toast
        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHOCOLATE_TOAST.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.TOAST.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.CHOCOLATE.ordinal()));
        recipeChocolateToast = RecipeUtil.lastIRecipe();
    }

}
