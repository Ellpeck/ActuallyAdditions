/*
 * This file ("ConfigIntValues.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.config.values;

import de.ellpeck.actuallyadditions.config.ConfigCategories;

public enum ConfigIntValues{

    JAM_VILLAGER_ID("Jam Villager: ID", ConfigCategories.WORLD_GEN, 493827, 100, 1000000, "The ID of the Jam Villager"),

    RICE_AMOUNT("Rice: Amount", ConfigCategories.WORLD_GEN, 15, 1, 100, "The Amount of Rice generating"),
    CANOLA_AMOUNT("Canola: Amount", ConfigCategories.WORLD_GEN, 10, 1, 50, "The Amount of Canola generating"),
    FLAX_AMOUNT("Flax: Amount", ConfigCategories.WORLD_GEN, 8, 1, 50, "The Amount of Flax generating"),
    COFFEE_AMOUNT("Coffee: Amount", ConfigCategories.WORLD_GEN, 6, 1, 50, "The Amount of Coffee generating"),
    BLACK_LOTUS_AMOUNT("Black Lotus: Amount", ConfigCategories.WORLD_GEN, 14, 1, 50, "The Amount of Black Lotus generating"),

    LASER_RELAY_LOSS("Laser Relay: Loss", ConfigCategories.MACHINE_VALUES, 5, 0, 80, "The Energy Loss of the Laser Relay per Transfer in Percent"),
    LASER_RELAY_MAX_TRANSFER("Laser Relay: Max Transfer", ConfigCategories.MACHINE_VALUES, 10000, 100, 1000000, "The max amount of RF a Laser Relay can receive and try to transfer (if it's given 100 RF and can only transfer 50, it will only accept 50, it won't waste any power!)"),

    TILE_ENTITY_UPDATE_INTERVAL("Tile Entities: Update Interval", ConfigCategories.OTHER, 5, 1, 100, "The amount of ticks waited before a TileEntity sends an additional Update to the Client");

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
