/*
 * This file ("ConfigBoolValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;

public enum ConfigBoolValues{

    JAM_VILLAGER_EXISTS("Jam Villager: Existence", ConfigCategories.WORLD_GEN, true, "Should the Jam Villager and his House generate in the world?"),
    CROP_FIELD_EXISTS("Crop Field: Existence", ConfigCategories.WORLD_GEN, true, "Should Custom Crop Fields exist?"),
    ENGINEER_VILLAGER_EXISTS("Engineer Villager: Existence", ConfigCategories.WORLD_GEN, true, "Should the Engineer Villager and his House generate in the worl?"),

    GENERATE_QUARTZ("Black Quartz", ConfigCategories.WORLD_GEN, true, "Shold Black Quartz generate in the world?"),

    DO_UPDATE_CHECK("Do Update Check", ConfigCategories.OTHER, true, "If true, Actually Additions Checks for updates on World Load."),
    UPDATE_CHECK_VERSION_SPECIFIC("Version Specific Update Checker", ConfigCategories.OTHER, true, "If true, Actually Additions' Update Checker searches for updates for the Minecraft Version you currently play on."),

    DO_CAT_DROPS("Do Cat Drops", ConfigCategories.OTHER, true, "If true, Cats drop Hairy Balls Occasionally."),
    WORMS("Worms", ConfigCategories.OTHER, true, "If true, worms will drop from tilling the soil."),

    DO_RICE_GEN("Rice Gen", ConfigCategories.WORLD_GEN, true, "Should Rice generate in the World?"),
    DO_CANOLA_GEN("Canola Gen", ConfigCategories.WORLD_GEN, true, "Should Canola generate in the World?"),
    DO_FLAX_GEN("Flax Gen", ConfigCategories.WORLD_GEN, true, "Should Canola generate in the World?"),
    DO_COFFEE_GEN("Coffee Gen", ConfigCategories.WORLD_GEN, true, "Should Coffee Plants generate in the World?"),
    DO_LOTUS_GEN("Black Lotus Gen", ConfigCategories.WORLD_GEN, true, "Should Black Lotus generate in the World?"),
    DO_TREASURE_CHEST_GEN("Treasure Chest Gen", ConfigCategories.WORLD_GEN, true, "Should Treasure Chests generate in the World?"),

    DO_SPIDER_DROPS("Spider Cobweb Drop", ConfigCategories.MOB_DROPS, true, "Should Cobwebs drop from spiders?"),
    DO_BAT_DROPS("Bat Wing Drop", ConfigCategories.MOB_DROPS, true, "Should Bat wings drop from Bats?"),
    DO_XP_DROPS("Solidified XP Drop", ConfigCategories.MOB_DROPS, true, "If true, Mobs will randomly drop solidified XP occasionally."),

    CTRL_EXTRA_INFO("Advanced Info", ConfigCategories.OTHER, true, "Show Advanced Item Info when holding Control on every Item."),
    CTRL_INFO_FOR_EXTRA_INFO("Advanced Info Tooltips", ConfigCategories.OTHER, true, "Show the 'Press Control for more Info'-Text on Item Tooltips"),

    SHOW_BOOKLET_INFO("Booklet Quick Opening Info", ConfigCategories.TOOL_VALUES, true, "If true,'Press key for more information' text should show when the item has a page in the booklet"),
    GIVE_BOOKLET_ON_FIRST_CRAFT("Give Booklet on First Craft", ConfigCategories.OTHER, true, "If true, the booklet should be given to the player when he first crafts something from the Mod"),

    DUNGEON_LOOT("Village and Dungeon Loot", ConfigCategories.OTHER, true, "Should Actually Additions Loot generate in dungeons? (This also disables loot in lush caves and village houses!)"),
    GEN_LUSH_CAVES("Generate Lush Caves", ConfigCategories.WORLD_GEN, true, "Should caves with trees and grass randomly generate underground?"),

    WATER_BOWL("Water Bowl", ConfigCategories.OTHER, true, "Should right-clicking a bowl on water blocks create a water bowl?"),

    LASER_RELAY_LOSS("Laser Relay Energy Loss", ConfigCategories.MACHINE_VALUES, true, "If Energy Laser Relays should have energy loss"),

    SUPER_DUPER_HARD_MODE("Super Duper Hard Recipes", ConfigCategories.OTHER, false, "Turn this on to make recipes for items from the mod really hard. (This is a joke feature poking fun at the whole FTB Infinity Expert Mode style of playing. You shouldn't really turn this on as it makes the mod completely unplayable.)"),
    MOST_BLAND_PERSON_EVER("No Colored Item Names", ConfigCategories.OTHER, false, "If you want to be really boring and lame, you can turn on this setting to disable colored names on Actually Additions items. Because why would you want things to look pretty anyways, right?"),

    ENABLE_DRILL_DIGGING_PACKET("Drill Digging Packet", ConfigCategories.OTHER, false, "If this is set to true, breaking a block with the Drill will send an additional packet to the server notifying that the block is being broken. This reduces the amount of ghost blocks being created, however it results in a higher connection load and it could also change the behavior of some blocks being broken.");

    public final String name;
    public final String category;
    public final boolean defaultValue;
    public final String desc;

    public boolean currentValue;

    ConfigBoolValues(String name, ConfigCategories category, boolean defaultValue, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public boolean isEnabled(){
        return this.currentValue;
    }

}
