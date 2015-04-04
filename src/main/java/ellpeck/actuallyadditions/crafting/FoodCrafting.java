package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class FoodCrafting{

    public static void init(){

        String knifeStack = ((INameableItem)InitItems.itemKnife).getOredictName();

        //Baguette
        if(ConfigValues.enabledFoodRecipes[TheFoods.BAGUETTE.ordinal()])
            GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1,
                    TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 1F);

        //Pizza
        if(ConfigValues.enabledFoodRecipes[TheFoods.PIZZA.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()),
                    "HKH", "MCF", " D ",
                    'D', TheMiscItems.DOUGH.getOredictName(),
                    'M', new ItemStack(Blocks.brown_mushroom),
                    'C', "cropCarrot",
                    'F', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'K', knifeStack,
                    'H', TheFoods.CHEESE.getOredictName()));

        //Hamburger
        if(ConfigValues.enabledFoodRecipes[TheFoods.HAMBURGER.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()),
                    "KT ", "CB ", " T ",
                    'T', TheFoods.TOAST.getOredictName(),
                    'C', TheFoods.CHEESE.getOredictName(),
                    'K', knifeStack,
                    'B', new ItemStack(Items.cooked_beef)));

        //Big Cookie
        if(ConfigValues.enabledFoodRecipes[TheFoods.BIG_COOKIE.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.BIG_COOKIE.ordinal()),
                    "DCD", "CDC", "DCD",
                    'D', TheMiscItems.DOUGH.getOredictName(),
                    'C', new ItemStack(Items.dye, 1, 3)));

        //Sub Sandwich
        if(ConfigValues.enabledFoodRecipes[TheFoods.SUBMARINE_SANDWICH.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()),
                    "KCP", "FB ", "PCP",
                    'P', new ItemStack(Items.paper),
                    'C', TheFoods.CHEESE.getOredictName(),
                    'F', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'B', TheFoods.BAGUETTE.getOredictName(),
                    'K', knifeStack));

        //French Fry
        if(ConfigValues.enabledFoodRecipes[TheFoods.FRENCH_FRY.ordinal()])
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemFoods, 2, TheFoods.FRENCH_FRY.ordinal()),
                    new ItemStack(Items.baked_potato),
                    knifeStack));

        //French Fries
        if(ConfigValues.enabledFoodRecipes[TheFoods.FRENCH_FRIES.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()),
                    "FFF", " P ",
                    'P', TheMiscItems.PAPER_CONE.getOredictName(),
                    'F', TheFoods.FRENCH_FRY.getOredictName()));

        //Fish N Chips
        if(ConfigValues.enabledFoodRecipes[TheFoods.FISH_N_CHIPS.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()),
                    "FIF", " P ",
                    'I', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'P', TheMiscItems.PAPER_CONE.getOredictName(),
                    'F', TheFoods.FRENCH_FRY.getOredictName()));

        //Cheese
        if(ConfigValues.enabledFoodRecipes[TheFoods.CHEESE.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                    new ItemStack(Items.milk_bucket));

        //Pumpkin Stew
        if(ConfigValues.enabledFoodRecipes[TheFoods.PUMPKIN_STEW.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PUMPKIN_STEW.ordinal()),
                    "P", "B",
                    'P', new ItemStack(Blocks.pumpkin),
                    'B', new ItemStack(Items.bowl));

        //Carrot Juice
        if(ConfigValues.enabledFoodRecipes[TheFoods.CARROT_JUICE.ordinal()])
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CARROT_JUICE.ordinal()),
                    new ItemStack(Items.glass_bottle), "cropCarrot", knifeStack));

        //Spaghetti
        if(ConfigValues.enabledFoodRecipes[TheFoods.SPAGHETTI.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SPAGHETTI.ordinal()),
                    "NNN", " B ",
                    'N', TheFoods.NOODLE.getOredictName(),
                    'B', new ItemStack(Items.bowl)));

        //Noodle
        if(ConfigValues.enabledFoodRecipes[TheFoods.NOODLE.ordinal()])
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()),
                    "cropWheat", knifeStack));

        //Chocolate
        if(ConfigValues.enabledFoodRecipes[TheFoods.CHOCOLATE.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHOCOLATE.ordinal()),
                    "C C", "CMC", "C C",
                    'C', new ItemStack(Items.dye, 1, 3),
                    'M', new ItemStack(Items.milk_bucket));

        //Chocolate Cake
        if(ConfigValues.enabledFoodRecipes[TheFoods.CHOCOLATE_CAKE.ordinal()])
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CHOCOLATE_CAKE.ordinal()),
                    "MMM", "CCC", "EDS",
                    'M', new ItemStack(Items.milk_bucket),
                    'E', new ItemStack(Items.egg),
                    'D', TheMiscItems.DOUGH.getOredictName(),
                    'S', new ItemStack(Items.sugar),
                    'C', new ItemStack(Items.dye, 1, 3)));

        //Toast
        if(ConfigValues.enabledFoodRecipes[TheFoods.TOAST.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 2, TheFoods.TOAST.ordinal()),
                    new ItemStack(Items.bread));
    }

}
