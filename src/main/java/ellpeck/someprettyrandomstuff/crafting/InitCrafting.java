package ellpeck.someprettyrandomstuff.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class InitCrafting {

    public static void init(){
        Util.logInfo("Initializing Crafting Recipes...");

        //Baguette
        if(ConfigValues.enabledFoodRecipes[TheFoods.BAGUETTE.ordinal()])
            GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1,
                    TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 1F);

        //Dough
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.DOUGH.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                    new ItemStack(Items.wheat), new ItemStack(Items.wheat));

        //Pizza
        if(ConfigValues.enabledFoodRecipes[TheFoods.PIZZA.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()),
                "HKH", "MCF", " D ",
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                'M', new ItemStack(Blocks.brown_mushroom),
                'C', new ItemStack(Items.carrot),
                'F', new ItemStack(Items.fish, 1, Util.WILDCARD),
                'K', new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD),
                'H', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()));

        //Hamburger
        if(ConfigValues.enabledFoodRecipes[TheFoods.HAMBURGER.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()),
                "KT ", "CBS", " T ",
                'T', new ItemStack(InitItems.itemFoods, 1, TheFoods.TOAST.ordinal()),
                'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                'S', new ItemStack(Blocks.leaves, 1, Util.WILDCARD),
                'K', new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD),
                'B', new ItemStack(Items.cooked_beef));

        //Big Cookie
        if(ConfigValues.enabledFoodRecipes[TheFoods.BIG_COOKIE.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.BIG_COOKIE.ordinal()),
                "DCD", "CDC", "DCD",
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                'C', new ItemStack(Items.dye, 1, 3));

        //Sub Sandwich
        if(ConfigValues.enabledFoodRecipes[TheFoods.SUBMARINE_SANDWICH.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()),
                "KCP", "FBS", "PCP",
                'P', new ItemStack(Items.paper),
                'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                'F', new ItemStack(Items.fish, 1, Util.WILDCARD),
                'B', new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()),
                'K', new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD),
                'S', new ItemStack(Blocks.leaves, 1, Util.WILDCARD));

        //Paper Cone
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.PAPER_CONE.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    "P P", " P ",
                    'P', new ItemStack(Items.paper));

        //French Fry
        if(ConfigValues.enabledFoodRecipes[TheFoods.FRENCH_FRY.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()),
                    new ItemStack(Items.baked_potato),
                    new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));

        //Knife Handle
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.KNIFE_HANDLE.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()),
                    new ItemStack(Items.stick),
                    new ItemStack(Items.slime_ball));

        //Knife Blade
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.KNIFE_BLADE.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    new ItemStack(Items.iron_ingot),
                    new ItemStack(Items.slime_ball));

        //Knife
        if(ConfigValues.enableKnifeRecipe)
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemKnife),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()));

        //French Fries
        if(ConfigValues.enabledFoodRecipes[TheFoods.FRENCH_FRIES.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()),
                    "FFF", " P ",
                    'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()));

        //Fish N Chips
        if(ConfigValues.enabledFoodRecipes[TheFoods.FISH_N_CHIPS.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()),
                    "FIF", " P ",
                    'I', new ItemStack(Items.cooked_fished, 1, Util.WILDCARD),
                    'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()));

        //Compost
        if(ConfigValues.enableCompostRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "WFW", "WWW",
                    'W', new ItemStack(Blocks.planks, 1, Util.WILDCARD),
                    'F', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()));

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
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.CARROT_JUICE.ordinal()),
                    new ItemStack(Items.glass_bottle), new ItemStack(Items.carrot), new ItemStack(InitItems.itemKnife));

        //Spaghetti
        if(ConfigValues.enabledFoodRecipes[TheFoods.SPAGHETTI.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SPAGHETTI.ordinal()),
                    "NNN", "B",
                    'N', new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()),
                    'B', new ItemStack(Items.bowl));

        //Noodle
        if(ConfigValues.enabledFoodRecipes[TheFoods.NOODLE.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.NOODLE.ordinal()),
                    new ItemStack(Items.wheat), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemKnife));

        //Mashed Food
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.MASHED_FOOD.ordinal()])
        initMashedFoodRecipes();
    }

    public static void initMashedFoodRecipes(){

        for(Object nextIterator : Item.itemRegistry){
            if(nextIterator instanceof ItemFood){
                ItemStack ingredient = new ItemStack((Item)nextIterator, 1, Util.WILDCARD);
                GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
            }
        }
    }

}
