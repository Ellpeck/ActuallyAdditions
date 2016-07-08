/*
 * This file ("ConfigBoolValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;

public enum ConfigBoolValues{

    JAM_VILLAGER_EXISTS("Jam Villager: Existence", ConfigCategories.WORLD_GEN, true, "If the Jam Villager and his House exist"),
    CROP_FIELD_EXISTS("Crop Field: Existence", ConfigCategories.WORLD_GEN, true, "If the Custom Crop Fields exist"),

    GENERATE_QUARTZ("Black Quartz", ConfigCategories.WORLD_GEN, true, "If the Black Quartz generates in the world"),

    DO_UPDATE_CHECK("Do Update Check", ConfigCategories.OTHER, true, "If Actually Additions should check for an Update on joining a World"),
    UPDATE_CHECK_VERSION_SPECIFIC("Version Specific Update Checker", ConfigCategories.OTHER, true, "If Actually Additions' Update Check should only search for updates for the Minecraft Version you currently have"),

    DO_CAT_DROPS("Do Cat Drops", ConfigCategories.OTHER, true, "If Cats drop Hairy Balls on Occasion"),

    TF_PAXELS("Thermal Foundation Paxels", ConfigCategories.OTHER, true, "If Paxels made of Thermal Foundation Materials should exist"),
    MT_PAXELS("MekanismTools Paxels", ConfigCategories.OTHER, true, "If Paxels made of MekanismTools Materials should exist"),
    SO_PAXELS("Simpleores Paxels", ConfigCategories.OTHER, true, "If Paxels made of SimpleOres Materials should exist"),

    DO_RICE_GEN("Rice Gen", ConfigCategories.WORLD_GEN, true, "If Rice should generate in the World"),
    DO_CANOLA_GEN("Canola Gen", ConfigCategories.WORLD_GEN, true, "If Canola should generate in the World"),
    DO_FLAX_GEN("Flax Gen", ConfigCategories.WORLD_GEN, true, "If Flax should generate in the World"),
    DO_COFFEE_GEN("Coffee Gen", ConfigCategories.WORLD_GEN, true, "If Coffee should generate in the World"),
    DO_LOTUS_GEN("Black Lotus Gen", ConfigCategories.WORLD_GEN, true, "If the Black Lotus should generate in the World"),
    DO_TREASURE_CHEST_GEN("Treasure Chest Gen", ConfigCategories.WORLD_GEN, true, "If Treasure Chests should generate in the World"),

    DO_SPIDER_DROPS("Spider Cobweb Drop", ConfigCategories.MOB_DROPS, true, "If Cobwebs should sometimes drop from Spiders"),
    DO_BAT_DROPS("Bat Wing Drop", ConfigCategories.MOB_DROPS, true, "If Wings should sometimes drop from Bats"),
    DO_XP_DROPS("Solidified XP Drop", ConfigCategories.MOB_DROPS, true, "If Mobs should randomly drop solidified XP occasionally"),

    CTRL_EXTRA_INFO("Advanced Info", ConfigCategories.OTHER, true, "Show Advanced Item Info when holding Control on every Item"),
    CTRL_INFO_FOR_EXTRA_INFO("Advanced Info Tooltips", ConfigCategories.OTHER, true, "Show the 'Press Control for more Info'-Text on Item Tooltips"),

    SHOW_BOOKLET_INFO("Booklet Quick Opening Info", ConfigCategories.TOOL_VALUES, true, "If the 'Press key for more information'-text should show when the item has a page in the booklet"),
    GIVE_BOOKLET_ON_FIRST_CRAFT("Give Booklet on First Craft", ConfigCategories.OTHER, true, "If the booklet should be given to the player when he first crafts something from the Mod"),

    ENABLE_SEASONAL("Seasonal Mode", ConfigCategories.OTHER, true, "If Seasonal Mode is enabled"),

    DUNGEON_LOOT("Dungeon Loot", ConfigCategories.OTHER, true, "Should Actually Additions Loot spawn in Dungeons"),
    GEN_LUSH_CAVES("Generate Lush Caves", ConfigCategories.WORLD_GEN, true, "Should caves with trees and grass randomly generate underground"),

    BOOKLET_TEXT_TO_FILE("Booklet Text to File", ConfigCategories.OTHER, false, "Should the entire text of the booklet be put into a new file in the Minecraft Folder on startup or resource reload. This is for debug purposes only and shouldn't really ever be needed."),

    WATER_BOWL("Water Bowl", ConfigCategories.OTHER, true, "If right-clicking a bowl on water should create a water bowl"),

    LESS_SOUND("Less Sound", ConfigCategories.PERFORMANCE, false, "If blocks in Actually Additions should have less sounds"),
    LESS_PARTICLES("Less Particles", ConfigCategories.PERFORMANCE, false, "If blocks in Actually Additions should have less particles"),
    LESS_BLOCK_BREAKING_EFFECTS("Less Block Breaking Effects", ConfigCategories.PERFORMANCE, false, "If there should not be a sound effect and particles when a block is being destroyed by a breaker or similar");

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