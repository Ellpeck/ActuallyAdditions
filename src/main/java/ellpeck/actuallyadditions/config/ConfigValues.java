package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static boolean[] enabledFoodRecipes = new boolean[TheFoods.values().length];
    public static boolean[] enabledMiscRecipes = new boolean[TheMiscItems.values().length];
    public static boolean enableCompostRecipe;
    public static boolean enableKnifeRecipe;
    public static boolean enableCrusherRecipe;
    public static boolean enableCrusherDoubleRecipe;
    public static boolean enableFurnaceDoubleRecipe;
    public static boolean enableGiantChestRecipe;
    public static boolean enableFeederRecipe;
    public static boolean enableCrafterRecipe;

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
    public static boolean enableToolEmeraldRecipe;

    public static int toolObsidianHarvestLevel;
    public static int toolObsidianMaxUses;
    public static float toolObsidianEfficiency;
    public static float toolObsidianDamage;
    public static int toolObsidianEnchantability;
    public static boolean enableToolObsidianRecipe;

    public static int furnaceDoubleSmeltTime;
    public static int grinderDoubleCrushTime;
    public static int grinderCrushTime;

    public static boolean enableExperienceDrop;
    public static boolean enableBloodDrop;
    public static boolean enableHeartDrop;
    public static boolean enableSubstanceDrop;
    public static boolean enablePearlShardDrop;
    public static boolean enableEmeraldShardDrop;

    public static void defineConfigValues(Configuration config){

        for(int i = 0; i < enabledFoodRecipes.length; i++){
            enabledFoodRecipes[i] = config.getBoolean(TheFoods.values()[i].name, ConfigurationHandler.CATEGORY_FOOD_CRAFTING, true, "If the Crafting Recipe for " + TheFoods.values()[i].name +  " is Enabled");
        }
        for(int i = 0; i < enabledMiscRecipes.length; i++){
            if(i != TheMiscItems.QUARTZ.ordinal()){
                enabledMiscRecipes[i] = config.getBoolean(TheMiscItems.values()[i].name, ConfigurationHandler.CATEGORY_MISC_CRAFTING, true, "If the Crafting Recipe for " + TheMiscItems.values()[i].name +  " is Enabled");
            }
        }

        //TODO CHANGE TO BE TRUE BY DEFAULT
        enableExperienceDrop = config.getBoolean("Solidified Experience", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Solidified Experience drops from Mobs");
        enableBloodDrop = config.getBoolean("Blood Fragments", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Blood Fragments drop from Mobs");
        enableHeartDrop = config.getBoolean("Heart Parts", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Heart Parts drop from Mobs");
        enableSubstanceDrop = config.getBoolean("Unknown Substance", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Unknown Substance drops from Mobs");
        enablePearlShardDrop = config.getBoolean("Ender Pearl Shard", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Ender Pearl Shard drops from Mobs");
        enableEmeraldShardDrop = config.getBoolean("Emerald Shard", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Emerald Shard drops from Mobs");

        enableCompostRecipe = config.getBoolean("Compost", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Compost is Enabled");
        enableKnifeRecipe = config.getBoolean("Knife", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Knife is Enabled");
        enableCrusherDoubleRecipe = config.getBoolean("Double Crusher", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Double Crusher is Enabled");
        enableCrusherRecipe = config.getBoolean("Crusher", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Crusher is Enabled");
        enableFurnaceDoubleRecipe = config.getBoolean("Double Furnace", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Double Furnace is Enabled");
        enableGiantChestRecipe = config.getBoolean("Giant Chest", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Giant Chest is Enabled");
        enableFeederRecipe = config.getBoolean("Feeder", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Feeder is Enabled");
        enableCrafterRecipe = config.getBoolean("Crafting Table On A Stick", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Crafting Table On A Stick is Enabled");

        tileEntityCompostAmountNeededToConvert = config.getInt("Compost: Amount Needed To Convert", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 10, 1, 64, "How many items are needed in the Compost to convert to Fertilizer");
        tileEntityCompostConversionTimeNeeded = config.getInt("Compost: Conversion Time Needed", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 1000, 30, 10000, "How long the Compost needs to convert to Fertilizer");

        tileEntityFeederReach = config.getInt("Feeder: Reach", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 5, 1, 20, "The Radius of Action of the Feeder");
        tileEntityFeederTimeNeeded = config.getInt("Feeder: Time Needed", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 100, 50, 5000, "The time spent between feeding animals with the Feeder");
        tileEntityFeederThreshold = config.getInt("Feeder: Threshold", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 30, 3, 500, "How many animals need to be in the area for the Feeder to stop");

        itemKnifeMaxDamage = config.getInt("Knife: Max Uses", ConfigurationHandler.CATEGORY_TOOL_VALUES, 100, 5, 5000, "How often the Knife can be crafted with");
        
        toolEmeraldHarvestLevel = config.getInt("Emerald: Harvest Level", ConfigurationHandler.CATEGORY_TOOL_VALUES, 3, 0, 3, "What Harvest Level Emerald Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)");
        toolEmeraldMaxUses = config.getInt("Emerald: Max Uses", ConfigurationHandler.CATEGORY_TOOL_VALUES, 2000, 50, 10000, "How often Emerald Tools can be used");
        toolEmeraldEfficiency = config.getFloat("Emerald: Efficiency", ConfigurationHandler.CATEGORY_TOOL_VALUES, 9.0F, 1.0F, 20.0F, "How fast Emerald Tools are");
        toolEmeraldDamage = config.getFloat("Emerald: Damage", ConfigurationHandler.CATEGORY_TOOL_VALUES, 5.0F, 0.1F, 50.0F, "How much damage an Emerald Tool deals");
        toolEmeraldEnchantability = config.getInt("Emerald: Enchantability", ConfigurationHandler.CATEGORY_TOOL_VALUES, 15, 1, 30, "How enchantable an Emerald Tool is");
        enableToolEmeraldRecipe = config.getBoolean("Emerald Tools", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for Emerald Tools is Enabled");

        toolObsidianHarvestLevel = config.getInt("Obsidian: Harvest Level", ConfigurationHandler.CATEGORY_TOOL_VALUES, 3, 0, 3, "What Harvest Level Obsidian Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)");
        toolObsidianMaxUses = config.getInt("Obsidian: Max Uses", ConfigurationHandler.CATEGORY_TOOL_VALUES, 8000, 50, 20000, "How often Obsidian Tools can be used");
        toolObsidianEfficiency = config.getFloat("Obsidian: Efficiency", ConfigurationHandler.CATEGORY_TOOL_VALUES, 4.0F, 1.0F, 20.0F, "How fast Obsidian Tools are");
        toolObsidianDamage = config.getFloat("Obsidian: Damage", ConfigurationHandler.CATEGORY_TOOL_VALUES, 2.0F, 0.1F, 50.0F, "How much damage an Obsidian Tool deals");
        toolObsidianEnchantability = config.getInt("Obsidian: Enchantability", ConfigurationHandler.CATEGORY_TOOL_VALUES, 15, 1, 30, "How enchantable an Obsidian Tool is");
        enableToolObsidianRecipe = config.getBoolean("Obsidian Tools", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for Obsidian Tools is Enabled");

        grinderCrushTime = config.getInt("Crusher: Crush Time", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 200, 10, 1000, "How long the Crusher takes to crush an item");
        grinderDoubleCrushTime = config.getInt("Double Crusher: Crush Time", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 300, 10, 1000, "How long the Double Crusher takes to crush an item");
        furnaceDoubleSmeltTime = config.getInt("Double Furnace: Smelt Time", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 300, 10, 1000, "How long the Double Furnace takes to crush an item");
    }
}
