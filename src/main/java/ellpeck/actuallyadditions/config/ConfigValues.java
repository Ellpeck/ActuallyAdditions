/*
 * This file ("ConfigValues.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.config.values.ConfigFloatValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static ConfigCrafting[] craftingConfig = ConfigCrafting.values();
    public static ConfigIntValues[] intConfig = ConfigIntValues.values();
    public static ConfigFloatValues[] floatConfig = ConfigFloatValues.values();
    public static ConfigBoolValues[] boolConfig = ConfigBoolValues.values();

    public static String[] crusherRecipeExceptions;
    public static String[] mashedFoodCraftingExceptions;

    public static String[] oreMagnetExceptions;
    public static String[] oreMagnetExtraWhitelist;

    public static String[] paxelExtraMiningWhitelist;
    public static String[] drillExtraminingWhitelist;

    public static void defineConfigValues(Configuration config){

        for(ConfigCrafting currConf : craftingConfig){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, "If the "+currConf.extraText+"Crafting Recipe for the "+currConf.name+" is Enabled").getBoolean();
        }
        for(ConfigIntValues currConf : intConfig){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc, currConf.min, currConf.max).getInt();
        }
        for(ConfigFloatValues currConf : floatConfig){
            currConf.currentValue = (float)config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc, currConf.min, currConf.max).getDouble();
        }
        for(ConfigBoolValues currConf : boolConfig){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc).getBoolean();
        }

        crusherRecipeExceptions = config.get(ConfigCategories.CRUSHER_RECIPES.name, "Crusher Recipe Exceptions", new String[]{"ingotBrick", "ingotBrickNether"}, "The Ingots, Dusts and Ores blacklisted from being auto-registered to be crushed by the Crusher. This list uses OreDictionary Names of the Inputs only.").getStringList();
        mashedFoodCraftingExceptions = config.get(ConfigCategories.ITEMS_CRAFTING.name, "Mashed Food Crafting Exceptions", new String[]{"ActuallyAdditions:itemCoffee"}, "The ItemFood, IGrowable and IPlantable Items that can not be used to craft Mashed Food. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        oreMagnetExceptions = config.get(ConfigCategories.MACHINE_VALUES.name, "Ore Magnet Exceptions", new String[0], "By default, the Ore Magnet pulls up everything that is registered in the OreDictionary as a String that starts with 'ore'. If you want any Ore not to be pulled up by the Magnet, put its ORE DICTIONARY name here.").getStringList();
        oreMagnetExtraWhitelist = config.get(ConfigCategories.MACHINE_VALUES.name, "Ore Magnet Extra Whitelist", new String[]{"rftools:dimensionalShardBlock"}, "By default, the Ore Magnet pulls up everything that is registered in the OreDictionary as a String that starts with 'ore'. If you want anything else to be pulled up by the Magnet, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        paxelExtraMiningWhitelist = config.get(ConfigCategories.TOOL_VALUES.name, "AIOT Extra Whitelist", new String[]{"TConstruct:GravelOre"}, "By default, the AIOT can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
        drillExtraminingWhitelist = config.get(ConfigCategories.TOOL_VALUES.name, "Drill Extra Whitelist", new String[]{"TConstruct:GravelOre"}, "By default, the Drill can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.").getStringList();
    }
}
