/*
 * This file ("ConfigIntListValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;

public enum ConfigIntListValues{

    ORE_GEN_DIMENSION_BLACKLIST("OreGen Dimension Blacklist", ConfigCategories.WORLD_GEN, new int[0], "The list of IDs that Actually Additions OreGen (Ex: Black Quartz) is banned in. This also applies for other world gen like lush caves."),
    PLANT_DIMENSION_BLACKLIST("Plant Blacklist", ConfigCategories.WORLD_GEN, new int[0], "The list of IDs of the dimensions that Actually Additions Plants (Rice for example) are banned in."),
    OIL_POWER("Oil Gen: Power Values", ConfigCategories.MACHINE_VALUES, new int[]{40, 80, 100, 120}, "The amount of power that the 4 tiers of oils generate (in order)"),
    OIL_TIME("Oil Gen: Time Values", ConfigCategories.MACHINE_VALUES, new int[]{100, 120, 280, 400}, "The amount of time that the 4 tiers of oils work for (in order)"),

    DRILL_AUGMENTS_ENERGY_USE("Drill: Energy Use Per Augment", ConfigCategories.TOOL_ENERGY_VALUES, new int[]{50, 75, 175, 100, 40, 80, 10, 30, 0}, "Additional amount of energy Drill Augments use per mined block. Order of augments: Speed I, Speed II, Speed III, Silk Touch, Fortune I, Fortune II, Mining I, Mining II, Block Placing"),
    BATTERIES_ENERGY_CAPACITY("Batteries: Energy Capacity Per Tier", ConfigCategories.TOOL_ENERGY_VALUES, new int[]{200000, 350000, 600000, 1000000, 2000000}, "Amount of energy each tier of Batteries can store."),
    BATTERIES_ENERGY_TRANSFER("Batteries: Energy Transfer Rate Per Tier", ConfigCategories.TOOL_ENERGY_VALUES, new int[]{1000, 5000, 10000, 30000, 100000}, "Amount of energy each tier of Batteries can send or receive.");

    public final String name;
    public final String category;
    public final int[] defaultValue;
    public final String desc;

    public int[] currentValue;

    ConfigIntListValues(String name, ConfigCategories category, int[] defaultValue, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public int[] getValue(){
        return this.currentValue;
    }

}