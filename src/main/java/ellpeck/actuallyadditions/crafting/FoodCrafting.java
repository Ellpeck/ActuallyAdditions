/*
 * This file ("FoodCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class FoodCrafting{

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

    public static void init(){

        ItemStack knifeStack = new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD);

        //Rice Bread
        if(ConfigCrafting.RICE_BREAD.isEnabled()){
            GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RICE_DOUGH.ordinal()),
                    new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal()), 1F);
        }

        //Baguette
        if(ConfigCrafting.BAGUETTE.isEnabled()){
            GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1,
                    TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 1F);
        }

        //Pizza
        if(ConfigCrafting.PIZZA.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()),
                    "HKH", "MCF", " D ",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                    'M', new ItemStack(Blocks.brown_mushroom),
                    'C', "cropCarrot",
                    'F', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'K', knifeStack,
                    'H', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal())));
            recipePizza = Util.lastIRecipe();
        }

        //Hamburger
        if(ConfigCrafting.HAMBURGER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()),
                    "KT ", "CB ", " T ",
                    'T', new ItemStack(InitItems.itemFoods, 1, TheFoods.TOAST.ordinal()),
                    'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                    'K', knifeStack,
                    'B', new ItemStack(Items.cooked_beef)));
            recipeHamburger = Util.lastIRecipe();
        }

        //Big Cookie
        if(ConfigCrafting.BIG_COOKIE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.BIG_COOKIE.ordinal()),
                    "DCD", "CDC", "DCD",
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                    'C', new ItemStack(Items.dye, 1, 3)));
            recipeBigCookie = Util.lastIRecipe();
        }

        //Sub Sandwich
        if(ConfigCrafting.SUB.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()),
                    "KCP", "FB ", "PCP",
                    'P', new ItemStack(Items.paper),
                    'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                    'F', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'B', new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()),
                    'K', knifeStack));
            recipeSubSandwich = Util.lastIRecipe();
        }

        //French Fry
        if(ConfigCrafting.FRENCH_FRY.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemFoods, 2, TheFoods.FRENCH_FRY.ordinal()),
                    new ItemStack(Items.baked_potato),
                    knifeStack));
            recipeFrenchFry = Util.lastIRecipe();
        }

        //French Fries
        if(ConfigCrafting.FRENCH_FRIES.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()),
                    "FFF", " P ",
                    'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal())));
            recipeFrenchFries = Util.lastIRecipe();
        }

        //Fish N Chips
        if(ConfigCrafting.FISH_N_CHIPS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()),
                    "FIF", " P ",
                    'I', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal())));
            recipeFishNChips = Util.lastIRecipe();
        }

        //Cheese
        if(ConfigCrafting.CHEESE.isEnabled()){
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                    new ItemStack(Items.milk_bucket));
            recipeCheese = Util.lastIRecipe();
        }

        //Pumpkin Stew
        if(ConfigCrafting.PUMPKIN_STEW.isEnabled()){
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PUMPKIN_STEW.ordinal()),
                    "P", "B",
                    'P', new ItemStack(Blocks.pumpkin),
                    'B', new ItemStack(Items.bowl));
            recipePumpkinStew = Util.lastIRecipe();
        }

        //Carrot Juice
        if(ConfigCrafting.CARROT_JUICE.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CARROT_JUICE.ordinal()),
                    new ItemStack(Items.glass_bottle), "cropCarrot", knifeStack));
            recipeCarrotJuice = Util.lastIRecipe();
        }

        //Spaghetti
        if(ConfigCrafting.SPAGHETTI.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SPAGHETTI.ordinal()),
                    "NNN", " B ",
                    'N', new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()),
                    'B', new ItemStack(Items.bowl)));
            recipeSpaghetti = Util.lastIRecipe();
        }

        //Noodle
        if(ConfigCrafting.NOODLE.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()),
                    "cropWheat", knifeStack));
            recipeNoodle = Util.lastIRecipe();
        }

        //Chocolate
        if(ConfigCrafting.CHOCOLATE.isEnabled()){
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 3, TheFoods.CHOCOLATE.ordinal()),
                    "C C", "CMC", "C C",
                    'C', new ItemStack(Items.dye, 1, 3),
                    'M', new ItemStack(Items.milk_bucket));
            recipeChocolate = Util.lastIRecipe();
        }

        //Chocolate Cake
        if(ConfigCrafting.CHOCOLATE_CAKE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHOCOLATE_CAKE.ordinal()),
                    "MMM", "CCC", "EDS",
                    'M', new ItemStack(Items.milk_bucket),
                    'E', new ItemStack(Items.egg),
                    'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                    'S', new ItemStack(Items.sugar),
                    'C', new ItemStack(Items.dye, 1, 3)));
            recipeChocolateCake = Util.lastIRecipe();
        }

        //Toast
        if(ConfigCrafting.TOAST.isEnabled()){
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 2, TheFoods.TOAST.ordinal()),
                    new ItemStack(Items.bread));
            recipeToast = Util.lastIRecipe();
        }
    }

}
