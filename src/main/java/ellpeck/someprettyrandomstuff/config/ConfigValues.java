package ellpeck.someprettyrandomstuff.config;

import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static boolean[] enabledFoodRecipes = new boolean[TheFoods.values().length];
    public static boolean[] enabledMiscRecipes = new boolean[TheMiscItems.values().length];
    public static boolean enableCompostRecipe;
    public static boolean enableKnifeRecipe;

    public static int tileEntityCompostAmountNeededToConvert;
    public static int tileEntityCompostConversionTimeNeeded;

    public static int tileEntityFeederReach;
    public static int tileEntityFeederTimeNeeded;
    public static int tileEntityFeederThreshold;

    public static int itemKnifeMaxDamage;

    public static int toolEmeraldHarvestLevel;
    public static int toolEmeraldMaxUses;
    public static float toolEmeraldEfficiency;
    public static float toolEmeraldDamage;
    public static int toolEmeraldEnchantability;

    public static int toolObsidianHarvestLevel;
    public static int toolObsidianMaxUses;
    public static float toolObsidianEfficiency;
    public static float toolObsidianDamage;
    public static int toolObsidianEnchantability;


    public static void defineConfigValues(Configuration config){

        for(int i = 0; i < enabledFoodRecipes.length; i++){
            enabledFoodRecipes[i] = config.getBoolean("isItemFood" + TheFoods.values()[i].name + "CraftingRecipeEnabled", ConfigurationHandler.CATEGORY_FOOD_CRAFTING, true, "If the Crafting Recipe for " + TheFoods.values()[i].name +  " is Enabled");
        }
        for(int i = 0; i < enabledMiscRecipes.length; i++){
            enabledMiscRecipes[i] = config.getBoolean("isItemMisc" + TheMiscItems.values()[i].name + "CraftingRecipeEnabled", ConfigurationHandler.CATEGORY_MISC_CRAFTING, true, "If the Crafting Recipe for " + TheMiscItems.values()[i].name +  " is Enabled");
        }

        enableCompostRecipe = config.getBoolean("isCompostCraftingRecipeEnabled", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Compost is Enabled");
        enableKnifeRecipe = config.getBoolean("isKnifeCraftingRecipeEnabled", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Knife is Enabled");

        tileEntityCompostAmountNeededToConvert = config.getInt("tileEntityCompostAmountNeededToConvert", ConfigurationHandler.CATEGORY_COMPOST_VALUES, 10, 1, 64, "How many items are needed in the Compost to convert to Fertilizer");
        tileEntityCompostConversionTimeNeeded = config.getInt("tileEntityCompostConversionTimeNeeded", ConfigurationHandler.CATEGORY_COMPOST_VALUES, 1000, 30, 10000, "How long the Compost needs to convert to Fertilizer");

        tileEntityFeederReach = config.getInt("tileEntityFeederReach", ConfigurationHandler.CATEGORY_FEEDER_VALUES, 5, 1, 20, "The Radius of Action of the Feeder");
        tileEntityFeederTimeNeeded = config.getInt("tileEntityFeederTimeNeeded", ConfigurationHandler.CATEGORY_FEEDER_VALUES, 100, 50, 5000, "The time spent between feeding animals with the Feeder");
        tileEntityFeederThreshold = config.getInt("tileEntityFeederThreshold", ConfigurationHandler.CATEGORY_FEEDER_VALUES, 30, 3, 500, "How many animals need to be in the area for the Feeder to stop");

        itemKnifeMaxDamage = config.getInt("itemKnifeMaxDamage", ConfigurationHandler.CATEGORY_ITEM_DAMAGE_VALUES, 100, 5, 5000, "How often the Knife can be crafted with");
        
        toolEmeraldHarvestLevel = config.getInt("toolEmeraldHarvestLevel", ConfigurationHandler.CATEGORY_TOOL_VALUES, 3, 0, 3, "What Harvest Level Emerald Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)");
        toolEmeraldMaxUses = config.getInt("toolEmeraldMaxUses", ConfigurationHandler.CATEGORY_TOOL_VALUES, 2000, 50, 10000, "How often Emerald Tools can be used");
        toolEmeraldEfficiency = config.getFloat("toolEmeraldEfficiency", ConfigurationHandler.CATEGORY_TOOL_VALUES, 9.0F, 1.0F, 20.0F, "How fast Emerald Tools are");
        toolEmeraldDamage = config.getFloat("toolEmeraldDamage", ConfigurationHandler.CATEGORY_TOOL_VALUES, 5.0F, 0.1F, 50.0F, "How much damage an Emerald Tool deals");
        toolEmeraldEnchantability = config.getInt("toolEmeraldEnchantability", ConfigurationHandler.CATEGORY_TOOL_VALUES, 15, 1, 30, "How enchantable an Emerald Tool is");

        toolObsidianHarvestLevel = config.getInt("toolObsidianHarvestLevel", ConfigurationHandler.CATEGORY_TOOL_VALUES, 3, 0, 3, "What Harvest Level Obsidian Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)");
        toolObsidianMaxUses = config.getInt("toolObsidianMaxUses", ConfigurationHandler.CATEGORY_TOOL_VALUES, 8000, 50, 20000, "How often Obsidian Tools can be used");
        toolObsidianEfficiency = config.getFloat("toolObsidianEfficiency", ConfigurationHandler.CATEGORY_TOOL_VALUES, 4.0F, 1.0F, 20.0F, "How fast Obsidian Tools are");
        toolObsidianDamage = config.getFloat("toolObsidianDamage", ConfigurationHandler.CATEGORY_TOOL_VALUES, 2.0F, 0.1F, 50.0F, "How much damage an Obsidian Tool deals");
        toolObsidianEnchantability = config.getInt("toolObsidianEnchantability", ConfigurationHandler.CATEGORY_TOOL_VALUES, 15, 1, 30, "How enchantable an Obsidian Tool is");

    }
}
