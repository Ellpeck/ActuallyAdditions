package ellpeck.someprettyrandomstuff.config;

import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static boolean[] enabledFoodRecipes = new boolean[TheFoods.values().length];
    public static boolean[] enabledMiscRecipes = new boolean[TheMiscItems.values().length];
    public static boolean enableCompostRecipe;

    public static int tileEntityCompostAmountNeededToConvert;
    public static int tileEntityCompostConversionTimeNeeded;

    public static void defineConfigValues(Configuration config){

        for(int i = 0; i < enabledFoodRecipes.length; i++){
            enabledFoodRecipes[i] = config.getBoolean("isItemFood" + TheFoods.values()[i].name + "CraftingRecipeEnabled", ConfigurationHandler.CATEGORY_FOOD_CRAFTING, true, "If the Crafting Recipe for " + TheFoods.values()[i].name +  " is Enabled");
        }
        for(int i = 0; i < enabledMiscRecipes.length; i++){
            enabledMiscRecipes[i] = config.getBoolean("isItemMisc" + TheMiscItems.values()[i].name + "CraftingRecipeEnabled", ConfigurationHandler.CATEGORY_MISC_CRAFTING, true, "If the Crafting Recipe for " + TheMiscItems.values()[i].name +  " is Enabled");
        }

        enableCompostRecipe = config.getBoolean("isCompostCraftingRecipeEnabled", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Compost is Enabled");

        tileEntityCompostAmountNeededToConvert = config.getInt("tileEntityCompostAmountNeededToConvert", ConfigurationHandler.CATEGORY_COMPOST_VALUES, 10, 1, 64, "How many items are needed in the Compost to convert to Fertilizer");
        tileEntityCompostConversionTimeNeeded = config.getInt("tileEntityCompostConversionTimeNeeded", ConfigurationHandler.CATEGORY_COMPOST_VALUES, 1000, 30, 10000, "How long the Compost needs to convert to Fertilizer");
    }
}
