/*
 * This file ("ConfigValues.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.config;

import de.ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static ConfigCrafting[] craftingConfig = ConfigCrafting.values();
    public static ConfigIntValues[] intConfig = ConfigIntValues.values();
    public static ConfigBoolValues[] boolConfig = ConfigBoolValues.values();

    public static String[] crusherRecipeExceptions;
    public static String[] mashedFoodCraftingExceptions;

    public static String[] paxelExtraMiningWhitelist;
    public static String[] drillExtraminingWhitelist;

    public static int[] oreGenDimensionBlacklist;
    public static int[] plantDimensionBlacklist;

    public static String[] minerExtraWhitelist;
    public static String[] minerBlacklist;

    public static void defineConfigValues(Configuration config){

        for(ConfigCrafting currConf : craftingConfig){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, "If the Recipe for the "+currConf.name+" is Enabled").getBoolean();
        }
        for(ConfigIntValues currConf : intConfig){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc, currConf.min, currConf.max).getInt();
        }
        for(ConfigBoolValues currConf : boolConfig){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc).getBoolean();
        }

        crusherRecipeExceptions = config.get(ConfigCategories.OTHER.name, "Crusher Recipe Exceptions", new String[]{"ingotBrick", "ingotBrickNether"}, "The Ingots, Dusts and Ores blacklisted from being auto-registered to be crushed by the Crusher. This list uses OreDictionary Names of the Inputs only.").getStringList();
        mashedFoodCraftingExceptions = config.get(ConfigCategories.ITEMS_CRAFTING.name, "Mashed Food Crafting Exceptions", new String[]{"ActuallyAdditions:itemCoffee"}, "The ItemFood, IGrowable and IPlantable Items that can not be used to craft Mashed Food. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        paxelExtraMiningWhitelist = config.get(ConfigCategories.TOOL_VALUES.name, "AIOT Extra Whitelist", new String[]{"TConstruct:GravelOre"}, "By default, the AIOT can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        drillExtraminingWhitelist = config.get(ConfigCategories.TOOL_VALUES.name, "Drill Extra Whitelist", new String[]{"TConstruct:GravelOre"}, "By default, the Drill can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        oreGenDimensionBlacklist = config.get(ConfigCategories.WORLD_GEN.name, "OreGen Dimension Blacklist", new int[0], "The IDs of the dimensions that Actually Additions OreGen (Black Quartz for example) is banned in").getIntList();
        plantDimensionBlacklist = config.get(ConfigCategories.WORLD_GEN.name, "Plant Blacklist", new int[0], "The IDs of the dimensions that Actually Additions Plants (Rice for example) are banned in").getIntList();
        minerExtraWhitelist = config.get(ConfigCategories.MACHINE_VALUES.name, "Vertical Digger Extra Whitelist", new String[0], "By default, the Vertical Digger mines everything that starts with 'ore' in the OreDictionary. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option only applies if the miner is in Ores Only Mode.").getStringList();
        minerBlacklist = config.get(ConfigCategories.MACHINE_VALUES.name, "Vertical Digger Blacklist", new String[0], "By default, the Vertical Digger mines everything that starts with 'ore' in the OreDictionary. If there is one that it can mine, but shouldn't be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option will apply in both modes.").getStringList();
    }
}
