/*
 * This file ("ConfigStringListValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;

public enum ConfigStringListValues{

    CRUSHER_RECIPE_EXCEPTIONS("Crusher Recipe Exceptions", ConfigCategories.OTHER, new String[]{"ingotBrick", "ingotBrickNether"}, "The Ingots, Dusts and Ores blacklisted from being auto-registered to be crushed by the Crusher. This list uses OreDictionary Names of the Inputs only."),
    MASHED_FOOD_CRAFTING_EXCEPTIONS("Mashed Food Crafting Exceptions", ConfigCategories.ITEMS_CRAFTING, new String[]{"ActuallyAdditions:itemCoffee"}, "The ItemFood, IGrowable and IPlantable Items that can not be used to craft Mashed Food. These are the actual registered Item Names, the ones you use, for example, when using the /give Command."),
    PAXEL_EXTRA_MINING_WHITELIST("AIOT Extra Whitelist", ConfigCategories.TOOL_VALUES, new String[]{"TConstruct:GravelOre"}, "By default, the AIOT can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command."),
    DRILL_EXTRA_MINING_WHITELIST("Drill Extra Whitelist", ConfigCategories.TOOL_VALUES, new String[]{"TConstruct:GravelOre"}, "By default, the Drill can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command."),
    MINER_EXTRA_WHITELIST("Vertical Digger Extra Whitelist", ConfigCategories.MACHINE_VALUES, new String[0], "By default, the Vertical Digger mines everything that starts with 'ore' in the OreDictionary. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option only applies if the miner is in Ores Only Mode."),
    MINER_BLACKLIST("Vertical Digger Blacklist", ConfigCategories.MACHINE_VALUES, new String[0], "By default, the Vertical Digger mines everything that starts with 'ore' in the OreDictionary. If there is one that it can mine, but shouldn't be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option will apply in both modes."),
    REPAIRER_EXTRA_WHITELIST("Item Repairer Extra Whitelist", ConfigCategories.MACHINE_VALUES, new String[]{"tconstruct:pickaxe", "tconstruct:shovel", "tconstruct:hatchet", "tconstruct:mattock", "tconstruct:broadsword", "tconstruct:longsword", "tconstruct:frypan", "tconstruct:battlesign", "tconstruct:hammer", "tconstruct:excavator", "tconstruct:lumberaxe", "tconstruct:cleaver", "tconstruct:rapier"}, "By default, the Item Repairer only repairs items which are repairable in an anvil. Add an item's REGISTRY NAME here if you want it to be repairable."),
    SPAWNER_CHANGER_BLACKLIST("Spawner Changer Blacklist", ConfigCategories.OTHER, new String[]{"VillagerGolem"}, "By default, the Spawner Changer allows every living entity to be put into a spawner. If there is one that shouldn't be able to, put its MAPPING NAME here.");

    public final String name;
    public final String category;
    public final String[] defaultValue;
    public final String desc;

    public String[] currentValue;

    ConfigStringListValues(String name, ConfigCategories category, String[] defaultValue, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public String[] getValue(){
        return this.currentValue;
    }

}