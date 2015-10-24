/*
 * This file ("ConfigBoolValues.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.config.values;

import ellpeck.actuallyadditions.config.ConfigCategories;
import ellpeck.actuallyadditions.config.ConfigurationHandler;

public enum ConfigBoolValues{

    LEAF_BLOWER_SOUND("Leaf Blower: Sound", ConfigCategories.TOOL_VALUES, true, "If the Leaf Blower makes Sounds"),

    JAM_VILLAGER_EXISTS("Jam Villager: Existence", ConfigCategories.WORLD_GEN, true, "If the Jam Villager and his House exist"),
    CROP_FIELD_EXISTS("Crop Field: Existence", ConfigCategories.WORLD_GEN, true, "If the Custom Crop Fields exist"),

    GENERATE_QUARTZ("Black Quartz", ConfigCategories.WORLD_GEN, true, "If the Black Quartz generates in the world"),

    EXPERIENCE_DROP("Solidified Experience", ConfigCategories.MOB_DROPS, true, "If the Solidified Experience drops from Mobs"),
    BLOOD_DROP("Blood Fragments", ConfigCategories.MOB_DROPS, false, "If the Blood Fragments drop from Mobs"),
    HEART_DROP("Heart Parts", ConfigCategories.MOB_DROPS, false, "If the Heart Parts drop from Mobs"),
    SUBSTANCE_DROP("Unknown Substance", ConfigCategories.MOB_DROPS, false, "If the Unknown Substance drops from Mobs"),
    PEARL_SHARD_DROP("Ender Pearl Shard", ConfigCategories.MOB_DROPS, true, "If the Ender Pearl Shard drops from Mobs"),
    EMERALD_SHARD_CROP("Emerald Shard", ConfigCategories.MOB_DROPS, true, "If the Emerald Shard drops from Mobs"),

    DO_UPDATE_CHECK("Do Update Check", ConfigCategories.OTHER, true, "If Actually Additions should check for an Update on joining a World"),
    DO_CAT_DROPS("Do Cat Drops", ConfigCategories.OTHER, true, "If Cats drop Hairy Balls on Occasion"),

    TF_PAXELS("Thermal Foundation Paxels", ConfigCategories.OTHER, true, "If Paxels made of Thermal Foundation Materials should exist"),
    MT_PAXELS("MekanismTools Paxels", ConfigCategories.OTHER, true, "If Paxels made of MekanismTools Materials should exist"),
    DUPLICATE_PAXELS("Allow Duplicate Paxels", ConfigCategories.OTHER, true, "If Paxels are allowed to have Duplicates (for Example ActuallyAdditions' Obsidian and MekanismTools' Obsidian)"),

    DO_RICE_GEN("Rice Gen", ConfigCategories.WORLD_GEN, true, "If Rice should generate in the World"),
    DO_CANOLA_GEN("Canola Gen", ConfigCategories.WORLD_GEN, true, "If Canola should generate in the World"),
    DO_FLAX_GEN("Flax Gen", ConfigCategories.WORLD_GEN, true, "If Flax should generate in the World"),
    DO_COFFEE_GEN("Coffee Gen", ConfigCategories.WORLD_GEN, true, "If Coffee should generate in the World"),
    DO_TREASURE_CHEST_GEN("Treasure Chest Gen", ConfigCategories.WORLD_GEN, true, "If Treasure Chests should generate in the World"),

    DO_SPIDER_DROPS("Spider Cobweb Drop", ConfigCategories.MOB_DROPS, true, "If Cobwebs should sometimes drop from Spiders"),
    DO_BAT_DROPS("Bat Wing Drop", ConfigCategories.MOB_DROPS, true, "If Wings should sometimes drop from Bats"),

    PREVENT_OIL_OVERRIDE("Oil Fluid Override", ConfigCategories.FLUIDS, false, "If not registering Oil Fluids from Actually Additions if other Oil is already registered should be prevented"+ConfigurationHandler.ISSUES_WARNING),
    PREVENT_CANOLA_OVERRIDE("Canola Oil Fluid Override", ConfigCategories.FLUIDS, false, "If not registering Canola Oil Fluids from Actually Additions if other Canola Oil is already registered should be prevented"+ConfigurationHandler.ISSUES_WARNING),
    PREVENT_OIL_BLOCK_OVERRIDE("Oil Block Override", ConfigCategories.FLUIDS, false, "If not registering Oil Blocks from Actually Additions if other Oil is already registered should be prevented"+ConfigurationHandler.ISSUES_WARNING),
    PREVENT_CANOLA_BLOCK_OVERRIDE("Canola Oil Block Override", ConfigCategories.FLUIDS, false, "If not registering Canola Oil Blocks from Actually Additions if other Canola Oil is already registered should be prevented"+ConfigurationHandler.ISSUES_WARNING),

    CTRL_EXTRA_INFO("Advanced Info", ConfigCategories.OTHER, true, "Show Advanced Item Info when holding Control on every Item"),
    CTRL_INFO_FOR_EXTRA_INFO("Advanced Info Tooltips", ConfigCategories.OTHER, true, "Show the 'Press Control for more Info'-Text on Item Tooltips"),

    SHOW_BOOKLET_INFO("Booklet Quick Opening Info", ConfigCategories.TOOL_VALUES, true, "If the 'Press key for more information'-text should show when the item has a page in the booklet"),
    GIVE_BOOKLET_ON_FIRST_CRAFT("Give Booklet on First Craft", ConfigCategories.OTHER, true, "If the booklet should be given to the player when he first crafts something from the Mod"),

    ENABLE_SEASONAL("Seasonal Mode", ConfigCategories.OTHER, true, "If Seasonal Mode is enabled");

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
