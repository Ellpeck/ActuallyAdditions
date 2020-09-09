package de.ellpeck.actuallyadditions.config.values;

import de.ellpeck.actuallyadditions.config.ConfigCategories;

public enum ConfigIntListValues {

    ORE_GEN_DIMENSION_BLACKLIST(
            "OreGen Dimension Blacklist",
            ConfigCategories.WORLD_GEN,
            new int[0],
            "The list of IDs that Actually Additions OreGen (Ex: Black Quartz) is banned in. This also applies for other world gen like lush caves."),
    PLANT_DIMENSION_BLACKLIST(
            "Plant Blacklist",
            ConfigCategories.WORLD_GEN,
            new int[0],
            "The list of IDs of the dimensions that Actually Additions Plants (Rice for example) are banned in."),
    OIL_POWER(
            "Oil Gen: Power Values",
            ConfigCategories.MACHINE_VALUES,
            new int[] { 40, 80, 100, 120 },
            "The amount of power that the 4 tiers of oils generate in CF/t.  Ordered."),
    OIL_TIME(
            "Oil Gen: Time Values",
            ConfigCategories.MACHINE_VALUES,
            new int[] { 100, 120, 280, 400 },
            "The amount of time that the 4 tiers of oils work for in seconds.  Ordered.");

    public final String name;
    public final String category;
    public final int[] defaultValue;
    public final String desc;

    public int[] currentValue;

    ConfigIntListValues(String name, ConfigCategories category, int[] defaultValue, String desc) {
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public int[] getValue() {
        return this.currentValue;
    }

}