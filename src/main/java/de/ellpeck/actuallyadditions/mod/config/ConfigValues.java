/*
 * This file ("ConfigValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static ConfigCrafting[] craftingConfig = ConfigCrafting.values();
    public static ConfigIntValues[] intConfig = ConfigIntValues.values();
    public static ConfigBoolValues[] boolConfig = ConfigBoolValues.values();

    public static String[] crusherRecipeExceptions;
    public static String[] mashedFoodCraftingExceptions;

    public static String[] paxelExtraMiningWhitelist;
    public static String[] drillExtraMiningWhitelist;

    public static int[] oreGenDimensionBlacklist;
    public static int[] plantDimensionBlacklist;

    public static String[] minerExtraWhitelist;
    public static String[] minerBlacklist;

    public static String[] repairerExtraWhitelist;

    public static String[] spawnerChangerBlacklist;

    public static boolean lessSound;
    public static boolean lessParticles;
    public static boolean lessBlockBreakingEffects;

    public static boolean caveWorld = true; //TODO Make this proper

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
        drillExtraMiningWhitelist = config.get(ConfigCategories.TOOL_VALUES.name, "Drill Extra Whitelist", new String[]{"TConstruct:GravelOre"}, "By default, the Drill can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        oreGenDimensionBlacklist = config.get(ConfigCategories.WORLD_GEN.name, "OreGen Dimension Blacklist", new int[0], "The IDs of the dimensions that Actually Additions OreGen (Black Quartz for example) is banned in").getIntList();
        plantDimensionBlacklist = config.get(ConfigCategories.WORLD_GEN.name, "Plant Blacklist", new int[0], "The IDs of the dimensions that Actually Additions Plants (Rice for example) are banned in").getIntList();
        minerExtraWhitelist = config.get(ConfigCategories.MACHINE_VALUES.name, "Vertical Digger Extra Whitelist", new String[0], "By default, the Vertical Digger mines everything that starts with 'ore' in the OreDictionary. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option only applies if the miner is in Ores Only Mode.").getStringList();
        minerBlacklist = config.get(ConfigCategories.MACHINE_VALUES.name, "Vertical Digger Blacklist", new String[0], "By default, the Vertical Digger mines everything that starts with 'ore' in the OreDictionary. If there is one that it can mine, but shouldn't be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option will apply in both modes.").getStringList();
        repairerExtraWhitelist = config.get(ConfigCategories.MACHINE_VALUES.name, "Item Repairer Extra Whitelist", new String[]{"tconstruct:pickaxe", "tconstruct:shovel", "tconstruct:hatchet", "tconstruct:mattock", "tconstruct:broadsword", "tconstruct:longsword", "tconstruct:frypan", "tconstruct:battlesign", "tconstruct:hammer", "tconstruct:excavator", "tconstruct:lumberaxe", "tconstruct:cleaver"}, "By default, the Item Repairer only repairs items which are repairable in an anvil. Add an item's REGISTRY NAME here if you want it to be repairable.").getStringList();
        spawnerChangerBlacklist = config.get(ConfigCategories.OTHER.name, "Spawner Changer Blacklist", new String[]{"VillagerGolem"}, "By default, the Spawner Changer allows every living entity to be put into a spawner. If there is one that shouldn't be able to, put its MAPPING NAME here.").getStringList();

        lessSound = config.get(ConfigCategories.PERFORMANCE.name, "Less Sound", false, "If blocks in Actually Additions should have less sounds").getBoolean();
        lessParticles = config.get(ConfigCategories.PERFORMANCE.name, "Less Particles", false, "If blocks in Actually Additions should have less particles").getBoolean();
        lessBlockBreakingEffects = config.get(ConfigCategories.PERFORMANCE.name, "Less Block Breaking Effects", false, "If there should not be a sound effect and particles when a block is being destroyed by a breaker or similar").getBoolean();
    }
}
