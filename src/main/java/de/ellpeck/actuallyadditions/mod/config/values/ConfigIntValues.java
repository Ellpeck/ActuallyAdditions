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

public enum ConfigIntValues{
	
    RICE_AMOUNT("Rice: Amount", ConfigCategories.WORLD_GEN, 15, 1, 100, "The Amount of Rice generating"),
    CANOLA_AMOUNT("Canola: Amount", ConfigCategories.WORLD_GEN, 10, 1, 50, "The Amount of Canola generating"),
    FLAX_AMOUNT("Flax: Amount", ConfigCategories.WORLD_GEN, 8, 1, 50, "The Amount of Flax generating"),
    COFFEE_AMOUNT("Coffee: Amount", ConfigCategories.WORLD_GEN, 6, 1, 50, "The Amount of Coffee generating"),
    BLACK_LOTUS_AMOUNT("Black Lotus: Amount", ConfigCategories.WORLD_GEN, 14, 1, 50, "The Amount of Black Lotus generating"),
    LUSH_CAVE_CHANCE("Lush Caves: Chance", ConfigCategories.WORLD_GEN, 20, 1, 100, "The chances for lush caves to generate. The lower the number, the higher the chances."),
    WORMS_DIE_TIME("Worm Death Time", ConfigCategories.OTHER, 0, 0, 10000000, "The amount of ticks it takes for a worm to die. When at 0 ticks, it will not die."),

    TILE_ENTITY_UPDATE_INTERVAL("Tile Entities: Update Interval", ConfigCategories.OTHER, 5, 1, 100, "The amount of ticks waited before a TileEntity sends an additional Update to the Client"),
    CTRL_INFO_NBT_CHAR_LIMIT("Advanced Info NBT Character Limit", ConfigCategories.OTHER, 1000, 0, 100000000, "The maximum amount of characters that is displayed by the NBT view of the CTRL Advanced Info. Set to a zero to have no limit"),

    FONT_SIZE_SMALL("Booklet Small Font Size", ConfigCategories.OTHER, 0, 0, 500, "The size of the booklet's small font in percent. Set to 0 to use defaults from the lang file."),
    FONT_SIZE_MEDIUM("Booklet Medium Font Size", ConfigCategories.OTHER, 0, 0, 500, "The size of the booklet's medium font in percent. Set to 0 to use defaults from the lang file."),
    FONT_SIZE_LARGE("Booklet Large Font Size", ConfigCategories.OTHER, 0, 0, 500, "The size of the booklet's large font in percent. Set to 0 to use defaults from the lang file."),
	
    ELEVEN("What is 11", ConfigCategories.OTHER, 11, 0, 12, "11?"),

    DRILL_ENERGY_CAPACITY("Drill: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 250000, 1000, 1000000000, "Amount of energy Drill can store."),
    DRILL_ENERGY_TRANSFER("Drill: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Drill can receive per tick."),
    DRILL_ENERGY_USE("Drill: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 100, 1, 1000000000, "Amount of energy Drill uses to mine block."),
    HANDHELD_FILLER_ENERGY_CAPACITY("Handheld Filler: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 500000, 1000, 1000000000, "Amount of energy Handheld Filler can store."),
    HANDHELD_FILLER_ENERGY_TRANSFER("Handheld Filler: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Handheld Filler can receive per tick."),
    HANDHELD_FILLER_ENERGY_USE("Handheld Filler: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 1500, 1, 1000000000, "Amount of energy Handheld Filler uses to place block."),
    GROWTH_RING_ENERGY_CAPACITY("Ring Of Growth: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 1000000, 1000, 1000000000, "Amount of energy Ring Of Growth can store."),
    GROWTH_RING_ENERGY_TRANSFER("Ring Of Growth: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 2000, 1, 1000000000, "Amount of energy Ring Of Growth can receive per tick."),
    GROWTH_RING_ENERGY_USE("Ring Of Growth: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 300, 1, 1000000000, "Amount of energy Ring Of Growth uses to fertilize plant."),
    MAGNETISM_RING_ENERGY_CAPACITY("Ring Of Magnetism: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 200000, 1000, 1000000000, "Amount of energy Ring Of Magnetism can store."),
    MAGNETISM_RING_ENERGY_TRANSFER("Ring Of Magnetism: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Ring Of Magnetism can receive per tick."),
    MAGNETISM_RING_ENERGY_USE("Ring Of Magnetism: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 50, 1, 1000000000, "Amount of energy Ring Of Magnetism uses to pick up item."),
    LIQUID_BANNING_RING_ENERGY_CAPACITY("Ring Of Liquid Banning: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 800000, 1000, 1000000000, "Amount of energy Ring Of Liquid Banning can store."),
    LIQUID_BANNING_RING_ENERGY_TRANSFER("Ring Of Liquid Banning: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Ring Of Liquid Banning can receive per tick."),
    LIQUID_BANNING_RING_ENERGY_USE("Ring Of Liquid Banning: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 150, 1, 1000000000, "Amount of energy Ring Of Liquid Banning uses to remove liquid block.");

    public final String name;
    public final String category;
    public final int defaultValue;
    public final int min;
    public final int max;
    public final String desc;

    public int currentValue;

    ConfigIntValues(String name, ConfigCategories category, int defaultValue, int min, int max, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public int getValue(){
        return this.currentValue;
    }
}
