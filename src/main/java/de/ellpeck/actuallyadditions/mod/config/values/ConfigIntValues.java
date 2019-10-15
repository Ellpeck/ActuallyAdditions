/*
 * This file ("ConfigIntValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;

public enum ConfigIntValues {

    RICE_AMOUNT("Rice: Amount", ConfigCategories.WORLD_GEN, 15, 1, 100, "The Amount of Rice generating"),
    CANOLA_AMOUNT("Canola: Amount", ConfigCategories.WORLD_GEN, 10, 1, 50, "The Amount of Canola generating"),
    FLAX_AMOUNT("Flax: Amount", ConfigCategories.WORLD_GEN, 8, 1, 50, "The Amount of Flax generating"),
    COFFEE_AMOUNT("Coffee: Amount", ConfigCategories.WORLD_GEN, 6, 1, 50, "The Amount of Coffee generating"),
    BLACK_LOTUS_AMOUNT(
            "Black Lotus: Amount",
            ConfigCategories.WORLD_GEN,
            14,
            1,
            50,
            "The Amount of Black Lotus generating"),
    LUSH_CAVE_CHANCE(
            "Lush Caves: Chance",
            ConfigCategories.WORLD_GEN,
            20,
            1,
            100,
            "The chances for lush caves to generate. The lower the number, the higher the chances."),
    WORMS_DIE_TIME(
            "Worm Death Time",
            ConfigCategories.OTHER,
            0,
            0,
            10000000,
            "The amount of ticks it takes for a worm to die. When at 0 ticks, it will not die."),

    TILE_ENTITY_UPDATE_INTERVAL(
            "Tile Entities: Update Interval",
            ConfigCategories.OTHER,
            5,
            1,
            100,
            "The amount of ticks waited before a TileEntity sends an additional Update to the Client"),
    CTRL_INFO_NBT_CHAR_LIMIT(
            "Advanced Info NBT Character Limit",
            ConfigCategories.OTHER,
            1000,
            0,
            100000000,
            "The maximum amount of characters that is displayed by the NBT view of the CTRL Advanced Info. Set to a zero to have no limit"),

    FONT_SIZE_SMALL(
            "Booklet Small Font Size",
            ConfigCategories.OTHER,
            0,
            0,
            500,
            "The size of the booklet's small font in percent. Set to 0 to use defaults from the lang file."),
    FONT_SIZE_MEDIUM(
            "Booklet Medium Font Size",
            ConfigCategories.OTHER,
            0,
            0,
            500,
            "The size of the booklet's medium font in percent. Set to 0 to use defaults from the lang file."),
    FONT_SIZE_LARGE(
            "Booklet Large Font Size",
            ConfigCategories.OTHER,
            0,
            0,
            500,
            "The size of the booklet's large font in percent. Set to 0 to use defaults from the lang file."),

    ELEVEN("What is 11", ConfigCategories.OTHER, 11, 0, 12, "11?"),
    FUR_CHANCE(
            "Fur Drop Chance",
            ConfigCategories.OTHER,
            5000,
            1,
            Integer.MAX_VALUE,
            "The 1/n drop chance, per tick, for a fur ball to be dropped."),
    RECONSTRUCTOR_POWER(
            "Atomic Reconstructor Power",
            ConfigCategories.MACHINE_VALUES,
            300000,
            300000,
            Integer.MAX_VALUE,
            "The amount of power the atomic reconstructor can store."),
    FARMER_AREA(
            "Farmer Area",
            ConfigCategories.MACHINE_VALUES,
            9,
            1,
            Integer.MAX_VALUE,
            "The size of the farmer's farming area.  Default is 9x9, must be an odd number."),
    MINING_LENS_USE(
            "Mining Lens Energy",
            ConfigCategories.MACHINE_VALUES,
            60000,
                    1,
            Integer.MAX_VALUE,
            "The energy use of the Atomic Reconstructor's Mining Lens."),
    COAL_GENERATOR_CF_PRODUCTION(
            "Coal Generator Energy Production",
            ConfigCategories.MACHINE_VALUES,
            30,
            1,
            Integer.MAX_VALUE,
            "The Coal Generator's Energy Production in CF/tick while burning."),
    LEAF_GENERATOR_COOLDOWN(
            "Leaf Generator Cooldown",
            ConfigCategories.MACHINE_VALUES,
            5,
            0,
            Integer.MAX_VALUE,
            "The cooldown between two generation cycles of the Leaf Generator, in ticks"),
    LEAF_GENERATOR_CF_PER_LEAF(
            "Leaf Generator Energy Production",
            ConfigCategories.MACHINE_VALUES,
            300,
            1,
            Integer.MAX_VALUE,
            "The Leaf Generator's Energy Production in CF/Leaf"),
    LEAF_GENERATOR_AREA(
            "Leaf Generator Area",
            ConfigCategories.MACHINE_VALUES,
            7,
            1,
            Integer.MAX_VALUE,
            "The size of the Leaf Generator's harvesting area.  Default is 7x7x7, must be an odd number.");

    public final String name;
    public final String category;
    public final int defaultValue;
    public final int min;
    public final int max;
    public final String desc;

    public int currentValue;

    ConfigIntValues(String name, ConfigCategories category, int defaultValue, int min, int max, String desc) {
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public int getValue() {
        return this.currentValue;
    }
}
