package ellpeck.someprettyrandomstuff.crafting;

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
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitCrafting {

    public static void init(){
        Util.logInfo("Initializing Crafting Recipes...");

        if(ConfigValues.enabledFoodRecipes[TheFoods.BAGUETTE.ordinal()])
            GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1,
                    TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 1F);

        if(ConfigValues.enabledMiscRecipes[TheMiscItems.DOUGH.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                    new ItemStack(Items.wheat), new ItemStack(Items.wheat));

        if(ConfigValues.enabledFoodRecipes[TheFoods.PIZZA.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()),
                "HHH", "MCF", " D ",
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                'M', new ItemStack(Blocks.brown_mushroom),
                'C', new ItemStack(Items.carrot),
                'F', new ItemStack(Items.fish, 1, Util.WILDCARD),
                'H', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()));

        if(ConfigValues.enabledFoodRecipes[TheFoods.HAMBURGER.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()),
                " T ", "CBS", " T ",
                'T', new ItemStack(InitItems.itemFoods, 1, TheFoods.TOAST.ordinal()),
                'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                'S', new ItemStack(Blocks.leaves, 1, Util.WILDCARD),
                'B', new ItemStack(Items.cooked_beef));

        if(ConfigValues.enabledFoodRecipes[TheFoods.BIG_COOKIE.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.BIG_COOKIE.ordinal()),
                "DCD", "CDC", "DCD",
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                'C', new ItemStack(Items.dye, 1, 3));

        if(ConfigValues.enabledFoodRecipes[TheFoods.SUBMARINE_SANDWICH.ordinal()])
        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()),
                "PCP", "FBS", "PCP",
                'P', new ItemStack(Items.paper),
                'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                'F', new ItemStack(Items.fish, 1, Util.WILDCARD),
                'B', new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()),
                'S', new ItemStack(Blocks.leaves, 1, Util.WILDCARD));

        if(ConfigValues.enabledMiscRecipes[TheMiscItems.PAPER_CONE.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    "P P", " P ",
                    'P', new ItemStack(Items.paper));

        Items.iron_sword.setContainerItem(Items.iron_sword);
        if(ConfigValues.enabledFoodRecipes[TheFoods.FRENCH_FRY.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()),
                    new ItemStack(Items.baked_potato),
                    new ItemStack(Items.iron_sword, 1, Util.WILDCARD));
        Items.iron_sword.setContainerItem(null);

        if(ConfigValues.enabledFoodRecipes[TheFoods.FRENCH_FRIES.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()),
                    "FFF", " P ",
                    'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()));

        if(ConfigValues.enabledFoodRecipes[TheFoods.FISH_N_CHIPS.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()),
                    "FIF", " P ",
                    'I', new ItemStack(Items.cooked_fish, 1, Util.WILDCARD),
                    'P', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    'F', new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()));

        if(ConfigValues.enableCompostRecipe)
            GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCompost),
                    "W W", "WFW", "WWW",
                    'W', new ItemStack(Blocks.planks, 1, Util.WILDCARD),
                    'F', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()));

        if(ConfigValues.enabledMiscRecipes[TheMiscItems.MASHED_FOOD.ordinal()])
        initMashedFoodRecipes();
    }

    public static void initMashedFoodRecipes(){

        for(Object nextIterator : Item.itemRegistry){
            if(nextIterator instanceof ItemFood){
                ItemStack ingredient = new ItemStack((Item)nextIterator, 1, Util.WILDCARD);
                GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient);
            }
        }
    }

}
