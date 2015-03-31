package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.ThePotionRings;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static boolean[] enabledFoodRecipes = new boolean[TheFoods.values().length];
    public static boolean[] enabledMiscRecipes = new boolean[TheMiscItems.values().length];
    public static boolean[] enablePotionRingRecipes = new boolean[ThePotionRings.values().length];
    public static boolean enableCompostRecipe;
    public static boolean enableKnifeRecipe;
    public static boolean enableCrusherRecipe;
    public static boolean enableCrusherDoubleRecipe;
    public static boolean enableFurnaceDoubleRecipe;
    public static boolean enableGiantChestRecipe;
    public static boolean enableFeederRecipe;
    public static boolean enableCrafterRecipe;
    public static boolean enableInputterRecipe;

    public static int tileEntityCompostAmountNeededToConvert;
    public static int tileEntityCompostConversionTimeNeeded;

    public static int tileEntityFeederReach;
    public static int tileEntityFeederTimeNeeded;
    public static int tileEntityFeederThreshold;

    public static int tileFishingNetTime;

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

    public static boolean generateBlackQuartz;
    public static int blackQuartzBaseAmount;
    public static int blackQuartzAdditionalChance;
    public static int blackQuartzChance;
    public static int blackQuartzMinHeight;
    public static int blackQuartzMaxHeight;

    public static boolean enableLeafBlowerRecipe;
    public static boolean enableLeafBlowerAdvancedRecipe;
    public static int leafBlowerRangeSides;
    public static int leafBlowerRangeUp;
    public static boolean leafBlowerDropItems;
    public static boolean leafBlowerParticles;
    public static boolean leafBlowerHasSound;

    public static boolean enableSolarRecipe;
    public static boolean enableFishingNetRecipe;
    public static boolean enableHeatCollectorRecipe;

    public static int heatCollectorRandomChance;
    public static int heatCollectorBlocksNeeded;

    public static void defineConfigValues(Configuration config){

        for(int i = 0; i < enabledFoodRecipes.length; i++){
            enabledFoodRecipes[i] = config.getBoolean(TheFoods.values()[i].name, ConfigurationHandler.CATEGORY_FOOD_CRAFTING, true, "If the Crafting Recipe for " + TheFoods.values()[i].name +  " is Enabled");
        }
        for(int i = 0; i < enabledMiscRecipes.length; i++){
            enabledMiscRecipes[i] = config.getBoolean(TheMiscItems.values()[i].name, ConfigurationHandler.CATEGORY_MISC_CRAFTING, true, "If the Crafting Recipe for " + TheMiscItems.values()[i].name +  " is Enabled");
        }

        for(int i = 0; i < enablePotionRingRecipes.length; i++){
            enablePotionRingRecipes[i] = config.getBoolean(ThePotionRings.values()[i].name, ConfigurationHandler.CATEGORY_POTION_RING_CRAFTING, i != ThePotionRings.SATURATION.ordinal(), "If the Crafting Recipe for the Ring of " + ThePotionRings.values()[i].name +  " is Enabled");
        }

        enableLeafBlowerRecipe = config.getBoolean("Leaf Blower", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Leaf Blower is Enabled");
        enableLeafBlowerAdvancedRecipe = config.getBoolean("Advanced Leaf Blower", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Advanced Leaf Blower is Enabled");
        leafBlowerDropItems = config.getBoolean("Leaf Blower: Drops Items", ConfigurationHandler.CATEGORY_TOOL_VALUES, true, "If the Leaf Blower lets destroyed Blocks' Drops drop");
        leafBlowerParticles = config.getBoolean("Leaf Blower: Particles", ConfigurationHandler.CATEGORY_TOOL_VALUES, true, "If the Leaf Blower lets destroyed Blocks have particles when getting destroyed");
        leafBlowerHasSound = config.getBoolean("Leaf Blower: Sound", ConfigurationHandler.CATEGORY_TOOL_VALUES, true, "If the Leaf Blower makes Sounds");
        leafBlowerRangeSides = config.getInt("Leaf Blower: Side Range", ConfigurationHandler.CATEGORY_TOOL_VALUES, 5, 1, 25, "The Leaf Blower's Range to the Sides");
        leafBlowerRangeUp = config.getInt("Leaf Blower: Height Range", ConfigurationHandler.CATEGORY_TOOL_VALUES, 1, 1, 10, "The Leaf Blower's Range to the Top and Bottom");

        generateBlackQuartz = config.getBoolean("Black Quartz", ConfigurationHandler.CATEGORY_WORLD_GEN, true, "If the Black Quartz generates in the world");
        blackQuartzBaseAmount = config.getInt("Black Quartz Amount", ConfigurationHandler.CATEGORY_WORLD_GEN, 3, 1, 50, "How big a Black Quartz Vein is at least");
        blackQuartzAdditionalChance = config.getInt("Black Quartz Additional Chance", ConfigurationHandler.CATEGORY_WORLD_GEN, 3, 0, 50, "How much bigger than the Base Amount a Black Quartz Vein can get");
        blackQuartzChance = config.getInt("Black Quartz Chance", ConfigurationHandler.CATEGORY_WORLD_GEN, 25, 1, 150, "How often the Black Quartz tries to generate");
        blackQuartzMinHeight = config.getInt("Black Quartz Min Height", ConfigurationHandler.CATEGORY_WORLD_GEN, 0, 0, 256, "How high the Black Quartz starts to generate");
        blackQuartzMaxHeight = config.getInt("Black Quartz Max Height", ConfigurationHandler.CATEGORY_WORLD_GEN, 25, 0, 256, "How high the Black Quartz stops to generate at");

        enableExperienceDrop = config.getBoolean("Solidified Experience", ConfigurationHandler.CATEGORY_MOB_DROPS, true, "If the Solidified Experience drops from Mobs");
        enableBloodDrop = config.getBoolean("Blood Fragments", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Blood Fragments drop from Mobs");
        enableHeartDrop = config.getBoolean("Heart Parts", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Heart Parts drop from Mobs");
        enableSubstanceDrop = config.getBoolean("Unknown Substance", ConfigurationHandler.CATEGORY_MOB_DROPS, false, "If the Unknown Substance drops from Mobs");
        enablePearlShardDrop = config.getBoolean("Ender Pearl Shard", ConfigurationHandler.CATEGORY_MOB_DROPS, true, "If the Ender Pearl Shard drops from Mobs");
        enableEmeraldShardDrop = config.getBoolean("Emerald Shard", ConfigurationHandler.CATEGORY_MOB_DROPS, true, "If the Emerald Shard drops from Mobs");

        enableCompostRecipe = config.getBoolean("Compost", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Compost is Enabled");
        enableKnifeRecipe = config.getBoolean("Knife", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Knife is Enabled");
        enableCrusherDoubleRecipe = config.getBoolean("Double Crusher", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Double Crusher is Enabled");
        enableCrusherRecipe = config.getBoolean("Crusher", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Crusher is Enabled");
        enableFurnaceDoubleRecipe = config.getBoolean("Double Furnace", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Double Furnace is Enabled");
        enableGiantChestRecipe = config.getBoolean("Giant Chest", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Giant Chest is Enabled");
        enableInputterRecipe = config.getBoolean("ESD", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the ESD is Enabled");
        enableFeederRecipe = config.getBoolean("Feeder", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Feeder is Enabled");
        enableCrafterRecipe = config.getBoolean("Crafting Table On A Stick", ConfigurationHandler.CATEGORY_ITEMS_CRAFTING, true, "If the Crafting Recipe for the Crafting Table On A Stick is Enabled");

        enableSolarRecipe = config.getBoolean("Solar Panel", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Solar Panel is Enabled");
        enableFishingNetRecipe = config.getBoolean("Fishing Net", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Fishing Net is Enabled");
        enableHeatCollectorRecipe = config.getBoolean("Heat Collector", ConfigurationHandler.CATEGORY_BLOCKS_CRAFTING, true, "If the Crafting Recipe for the Heat Collector is Enabled");

        tileEntityCompostAmountNeededToConvert = config.getInt("Compost: Amount Needed To Convert", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 10, 1, 64, "How many items are needed in the Compost to convert to Fertilizer");
        tileEntityCompostConversionTimeNeeded = config.getInt("Compost: Conversion Time Needed", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 1000, 30, 10000, "How long the Compost needs to convert to Fertilizer");

        tileFishingNetTime = config.getInt("Fishing Net: Time Needed", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 2000, 50, 50000, "How long it takes on Average until the Fishing Net catches a Fish");

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

        heatCollectorBlocksNeeded = config.getInt("Heat Collector: Blocks Needed", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 4, 1, 5, "How many Blocks are needed for the Heat Collector to power Machines above it");
        heatCollectorRandomChance = config.getInt("Heat Collector: Random Chance", ConfigurationHandler.CATEGORY_MACHINE_VALUES, 2000, 10, 100000, "The Chance of the Heat Collector destroying a Lava Block around (Default Value 2000 meaning a 1/2000 Chance!)");
    }
}
