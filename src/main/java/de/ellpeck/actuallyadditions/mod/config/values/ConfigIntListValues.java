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

    DRILL_AUGMENTS_ENERGY_USE("Drill: Energy Use Per Augment", ConfigCategories.TOOL_ENERGY_VALUES, new int[]{50, 75, 175, 100, 40, 80, 10, 30, 0}, "Additional amount of energy Drill Augments use per mined block.\nOrder of augments: Speed I, Speed II, Speed III, Silk Touch, Fortune I, Fortune II, Mining I, Mining II, Block Placing"),
    BATTERIES_ENERGY_CAPACITY("Batteries: Energy Capacity Per Tier", ConfigCategories.TOOL_ENERGY_VALUES, new int[]{200000, 350000, 600000, 1000000, 2000000}, "Amount of energy each tier of Batteries can store."),
    BATTERIES_ENERGY_TRANSFER("Batteries: Energy Transfer Rate Per Tier", ConfigCategories.TOOL_ENERGY_VALUES, new int[]{1000, 5000, 10000, 30000, 100000}, "Amount of energy each tier of Batteries can send or receive."),

    ATOMIC_RECONSTRUCTOR_LENSES_ENERGY_USE("Atomic Reconstructor: Lenses Energy Use", ConfigCategories.MACHINE_ENERGY_VALUES, new int[]{200, 350, 250000, 250000, 2500, 60000, 70000, 150000}, "Amount of energy used by each Lens type.\nOrder of lenses: Color, Certain Death, Detonation, Disenchanting, Killer, Miner, Miner (for nether ores), Disruption."),
    ATOMIC_RECONSTRUCTOR_CRYSTALLIZATION_RECIPE_COSTS("Atomic Reconstructor: Crystallization Energy Cost", ConfigCategories.MACHINE_RECIPE_COSTS, new int[]{40, 40, 60, 100, 60, 80, 400, 400, 600, 1000, 600, 800, 2000}, "Amount of energy used to crystallize item.\nOrder of items: Restonia Crystal, Palis Crystal, Diamatine Crystal, Emeradic Crystal, Void Crystal, Enori Crystal, Restonia Block, Palis Block, Diamatine Block, Emeradic Block, Void Block, Enori Block, Crystallized Canola Seed"),
    ATOMIC_RECONSTRUCTOR_CONVERSION_RECIPE_COSTS("Atomic Reconstructor: Conversion Energy Cost", ConfigCategories.MACHINE_RECIPE_COSTS, new int[]{5000, 2000, 20000, 8000, 150000, 30000, 10, 10}, "Amount of energy used to create item from another one by conversion.\nOrder of items: Lenses, Relays, Soul Sand, Leather, Nether Wart, Prismarine, Ethetic Quartz, Ethetic Green Block"),
    EMPOWERER_RECIPE_COSTS("Empowerer: Recipe Energy Cost", ConfigCategories.MACHINE_RECIPE_COSTS, new int[]{5000, 50000, 1000}, "Amount of energy per Display Stand used per tick to craft item in Empowerer.\nOrder of items: Empowered Crystals, Empowered Crystal Blocks, Empowered Canola Seed"),
    EMPOWERER_RECIPE_DURATIONS("Empowerer: Recipe Duration", ConfigCategories.MACHINE_RECIPE_COSTS, new int[]{50, 500, 30}, "Time in ticks required to craft item in Empowerer.\nOrder of items: Empowered Crystals, Empowered Crystal Blocks, Empowered Canola Seed"),
    OIL_GENERATOR_ENERGY_PRODUCTION("Oil Generator: Energy Production", ConfigCategories.MACHINE_ENERGY_VALUES, new int[]{40, 80, 100, 120}, "Amound of energy Oil Generator produces for each oil type.\nThese values change energy density of each type of oil.\nConfig option 'Oil Gen: Power Values' is obsolete, please remove it."),
    OIL_GENERATOR_BURN_TIME("Oil Generator: Production Time", ConfigCategories.MACHINE_VALUES, new int[]{100, 120, 280, 400}, "Time in seconds each type of oil burns in Oil Generator.\nThese values change burn time of each type of oil.\nConfig option 'Oil Gen: Time Values' is obsolete, please remove it."),
    LASER_RELAY_ENERGY_TRANSFER("Energy Laser Relay: Energy Transfer Rate Per Tier", ConfigCategories.MACHINE_ENERGY_VALUES, new int[]{1000, 10000, 100000}, "Amount of energy each tier of Energy Laser Relay can transfer per tick (per side)."),
    LASER_RELAY_ENERGY_LOSS_PERCENTAGE("Energy Laser Relay: Energy Loss Percentage Per Tier", ConfigCategories.MACHINE_ENERGY_VALUES, new int[]{5, 8, 10}, "Energy loss in percents for each tier of Energy Laser Relays, if enabled."),
    CRUSHER_ENERGY_USE("Crusher: Energy Use Per Tier", ConfigCategories.MACHINE_RECIPE_COSTS, new int[]{40, 40}, "Amount of energy used by Crusher and Double Crusher per tick."),
    CRUSHER_PROCESSING_TIME("Crusher: Processing Time Per Tier", ConfigCategories.MACHINE_RECIPE_COSTS, new int[]{100, 150}, "Time in ticks required to perform one operation in Crusher and Double Crusher.");

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