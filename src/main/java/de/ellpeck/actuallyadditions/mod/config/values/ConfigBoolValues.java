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

public enum ConfigBoolValues {

    JAM_VILLAGER_EXISTS(
            "Jam Villager: Existence",
            ConfigCategories.WORLD_GEN,
            true,
            "Should the Jam Villager and his House generate in the world?"),
    CROP_FIELD_EXISTS("Crop Field: Existence", ConfigCategories.WORLD_GEN, true, "Should Custom Crop Fields exist?"),
    ENGINEER_VILLAGER_EXISTS(
            "Engineer Villager: Existence",
            ConfigCategories.WORLD_GEN,
            true,
            "Should the Engineer Villager and his House generate in the worl?"),

    GENERATE_QUARTZ("Black Quartz", ConfigCategories.WORLD_GEN, true, "Shold Black Quartz generate in the world?"),


    DO_RICE_GEN("Rice Gen", ConfigCategories.WORLD_GEN, true, "Should Rice generate in the World?"),
    DO_CANOLA_GEN("Canola Gen", ConfigCategories.WORLD_GEN, true, "Should Canola generate in the World?"),
    DO_FLAX_GEN("Flax Gen", ConfigCategories.WORLD_GEN, true, "Should Flax generate in the World?"),
    DO_COFFEE_GEN("Coffee Gen", ConfigCategories.WORLD_GEN, true, "Should Coffee Plants generate in the World?"),
    DO_LOTUS_GEN("Black Lotus Gen", ConfigCategories.WORLD_GEN, true, "Should Black Lotus generate in the World?"),
    DO_TREASURE_CHEST_GEN(
            "Treasure Chest Gen",
            ConfigCategories.WORLD_GEN,
            true,
            "Should Treasure Chests generate in the World?"),
    DO_CRYSTAL_CLUSTERS(
            "Crystal Clusters in Lush Caves",
            ConfigCategories.WORLD_GEN,
            true,
            "If Crystal Clusters should generate in Lush Caves"),

    DO_SPIDER_DROPS("Spider Cobweb Drop", ConfigCategories.MOB_DROPS, true, "Should Cobwebs drop from spiders?"),
    DO_BAT_DROPS("Bat Wing Drop", ConfigCategories.MOB_DROPS, true, "Should Bat wings drop from Bats?"),
    DO_XP_DROPS(
            "Solidified XP Drop",
            ConfigCategories.MOB_DROPS,
            true,
            "If true, Mobs will randomly drop solidified XP occasionally."),

    SHOW_BOOKLET_INFO(
            "Booklet Quick Opening Info",
            ConfigCategories.TOOL_VALUES,
            true,
            "If true,'Press key for more information' text should show when the item has a page in the booklet"),

    GEN_LUSH_CAVES(
            "Generate Lush Caves",
            ConfigCategories.WORLD_GEN,
            true,
            "Should caves with trees and grass randomly generate underground?"),


    LASER_RELAY_LOSS(
            "Laser Relay Energy Loss",
            ConfigCategories.MACHINE_VALUES,
            true,
            "If Energy Laser Relays should have energy loss"),


    ORE_GEN_DIM_WHITELIST(
            "Ore Gen Whitelist",
            ConfigCategories.WORLD_GEN,
            false,
            "If true, the ore gen dimension blacklist will be treated as a whitelist."),
    MINING_LENS_ADAPTED_USE(
            "Mining Lens Math",
            ConfigCategories.MACHINE_VALUES,
            true,
            "If true, the mining lens uses some weird math to calculate energy costs.");

    public final String name;
    public final String category;
    public final boolean defaultValue;
    public final String desc;

    public boolean currentValue;

    ConfigBoolValues(String name, ConfigCategories category, boolean defaultValue, String desc) {
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public boolean isEnabled() {
        return this.currentValue;
    }

}
