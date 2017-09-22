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
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public enum ConfigIntValues{

    JAM_VILLAGER_ID("Jam Villager: ID", ConfigCategories.WORLD_GEN, 493827, 100, 1000000, "The ID of the Jam Villager"),

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

    DRILL_ENERGY_CAPACITY("Drill: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 250000, 1000, 1000000000, "Amount of energy Drills can store"),
    DRILL_ENERGY_TRANSFER("Drill: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Drill can receive per tick"),
    DRILL_ENERGY_USE("Drill: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 100, 1, 1000000000, "Base amount energy used by Drill per mined block"),

    SINGLE_BATTERY_ENERGY_CAPACITY("Single Battery: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 200000, 1000, 1000000000, "Amount of energy Single Battery can store"),
    SINGLE_BATTERY_ENERGY_TRANSFER("Single Battery: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Single Battery can send or receive per tick"),
    DOUBLE_BATTERY_ENERGY_CAPACITY("Double Battery: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 350000, 1000, 1000000000, "Amount of energy Double Battery can store"),
    DOUBLE_BATTERY_ENERGY_TRANSFER("Double Battery: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 5000, 1, 1000000000, "Amount of energy Double Battery can send or receive per tick"),
    TRIPLE_BATTERY_ENERGY_CAPACITY("Triple Battery: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 600000, 1000, 1000000000, "Amount of energy Triple Battery can store"),
    TRIPLE_BATTERY_ENERGY_TRANSFER("Triple Battery: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 10000, 1, 1000000000, "Amount of energy Triple Battery can send or receive per tick"),
    QUADRUPLE_BATTERY_ENERGY_CAPACITY("Quadruple Battery: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 1000000, 1000, 1000000000, "Amount of energy Quadruple Battery can store"),
    QUADRUPLE_BATTERY_ENERGY_TRANSFER("Quadruple Battery: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 30000, 1, 1000000000, "Amount of energy Quadruple Battery can send or receive per tick"),
    QUINTUPLE_BATTERY_ENERGY_CAPACITY("Quintuple Battery: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 2000000, 1000, 1000000000, "Amount of energy Quintuple Battery can store"),
    QUINTUPLE_BATTERY_ENERGY_TRANSFER("Quintuple Battery: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 100000, 1, 1000000000, "Amount of energy Quintuple Battery can send or receive per tick"),

    FILLER_ENERGY_CAPACITY("Handheld Filler: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 500000, 1000, 1000000000, "Amount of energy Handheld Filler can store"),
    FILLER_ENERGY_TRANSFER("Handheld Filler: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Handheld Filler can receive per tick"),
    FILLER_ENERGY_USE("Handheld Filler: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 1500, 1, 1000000000, "Amount of energy used by Handheld Filler to place block"),

    GROWTH_RING_ENERGY_CAPACITY("Growth Ring: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 1000000, 1000, 1000000000, "Amount of energy Growth Ring can store"),
    GROWTH_RING_ENERGY_TRANSFER("Growth Ring: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 2000, 1, 1000000000, "Amount of energy Growth Ring can receive per tick"),
    GROWTH_RING_ENERGY_USE("Growth Ring: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 300, 1, 1000000000, "Amount of energy used by Growth Ring to fertilize crop"),
    GROWTH_RING_FERTILIZE_ATTEMPTS("Growth Ring: Fertilize Attempts", ConfigCategories.TOOL_VALUES, 45, 1, 100, "Amount of attempts to fertilize crops; Growth Ring can fertilize up to this number of crops every 30 ticks"),

    MAGNETIC_RING_ENERGY_CAPACITY("Magnetic Ring: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 200000, 1000, 1000000000, "Amount of energy Magnetic Ring can store"),
    MAGNETIC_RING_ENERGY_TRANSFER("Magnetic Ring: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Magnetic Ring can receive per tick"),
    MAGNETIC_RING_ENERGY_USE("Magnetic Ring: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 50, 1, 1000000000, "Amount of energy used by Magnetic Ring to suck item"),

    TELEPORT_STAFF_ENERGY_CAPACITY("Teleport Staff: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 250000, 1000, 1000000000, "Amount of energy Teleport Staff can store"),
    TELEPORT_STAFF_ENERGY_TRANSFER("Teleport Staff: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Teleport Staff can receive per tick"),
    TELEPORT_STAFF_ENERGY_USE("Teleport Staff: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 200, 1, 1000000000, "Base amount of energy used by Teleport Staff to teleport player"),

    LIQUID_BANNING_RING_ENERGY_CAPACITY("Ring of Liquid Banning: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 800000, 1000, 1000000000, "Amount of energy Ring of Liquid Banning can store"),
    LIQUID_BANNING_RING_ENERGY_TRANSFER("Ring of Liquid Banning: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Ring of Liquid Banning can receive per tick"),
    LIQUID_BANNING_RING_ENERGY_USE("Ring of Liquid Banning: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 150, 1, 1000000000, "Base amount of energy used by Ring of Liquid Banning to remove liquid block"),

    ATOMIC_RECONSTRUCTOR_ENERGY_CAPACITY("Atomic Reconstructor: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 300000, 1000, 1000000000, "Amount of energy Atomic Reconstructor can store"),
    ATOMIC_RECONSTRUCTOR_ENERGY_RECEIVE("Atomic Reconstructor: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 5000, 1, 1000000000, "Amount of energy Atomic Reconstructor can receive per tick"),
    ATOMIC_RECONSTRUCTOR_ENERGY_USE("Atomic Reconstructor: Energy Use", ConfigCategories.MACHINE_ENERGY_VALUES, 1000, 1, 1000000000, "Base amount of energy used by Atomic Reconstructor to perform action"),
    ATOMIC_RECONSTRUCTOR_RESTONIA_CRYSTAL_COST("Atomic Reconstructor: Restonia Crystal Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 40, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Restonia Crystal"),
    ATOMIC_RECONSTRUCTOR_PALIS_CRYSTAL_COST("Atomic Reconstructor: Palis Crystal Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 40, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Palis Crystal"),
    ATOMIC_RECONSTRUCTOR_DIAMATINE_CRYSTAL_COST("Atomic Reconstructor: Diamatine Crystal Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 60, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Diamatine Crystal"),
    ATOMIC_RECONSTRUCTOR_EMERADIC_CRYSTAL_COST("Atomic Reconstructor: Emeradic Crystal Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 100, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Emeradic Crystal"),
    ATOMIC_RECONSTRUCTOR_VOID_CRYSTAL_COST("Atomic Reconstructor: Void Crystal Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 60, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Void Crystal"),
    ATOMIC_RECONSTRUCTOR_ENORI_CRYSTAL_COST("Atomic Reconstructor: Enori Crystal Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 80, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Enori Crystal"),
    ATOMIC_RECONSTRUCTOR_RESTONIA_BLOCK_COST("Atomic Reconstructor: Restonia Crystal Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 400, 1, 10000000, "Amount of energy used by Atomic Reconstructor to create Restonia Crystal Block"),
    ATOMIC_RECONSTRUCTOR_PALIS_BLOCK_COST("Atomic Reconstructor: Palis Crystal Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 400, 1, 10000000, "Amount of energy used by Atomic Reconstructor to create Palis Crystal Block"),
    ATOMIC_RECONSTRUCTOR_DIAMATINE_BLOCK_COST("Atomic Reconstructor: Diamatine Crystal Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 600, 1, 10000000, "Amount of energy used by Atomic Reconstructor to create Diamatine Crystal Block"),
    ATOMIC_RECONSTRUCTOR_EMERADIC_BLOCK_COST("Atomic Reconstructor: Emeradic Crystal Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 1000, 1, 10000000, "Amount of energy used by Atomic Reconstructor to create Emeradic Crystal Block"),
    ATOMIC_RECONSTRUCTOR_VOID_BLOCK_COST("Atomic Reconstructor: Void Crystal Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 600, 1, 10000000, "Amount of energy used by Atomic Reconstructor to create Void Crystal Block"),
    ATOMIC_RECONSTRUCTOR_ENORI_BLOCK_COST("Atomic Reconstructor: Enori Crystal Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 800, 1, 10000000, "Amount of energy used by Atomic Reconstructor to create Enori Crystal Block"),
    ATOMIC_RECONSTRUCTOR_LENS_COST("Atomic Reconstructor: Lens Conversion Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 5000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to change type of Lens"),
    ATOMIC_RECONSTRUCTOR_LASER_RELAY_COST("Atomic Reconstructor: Laser Relay Conversion Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 2000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to change type of Laser Relay"),
    ATOMIC_RECONSTRUCTOR_SOUL_SAND_COST("Atomic Reconstructor: Soul Sand Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 20000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Soul Sand"),
    ATOMIC_RECONSTRUCTOR_LEATHER_COST("Atomic Reconstructor: Leather Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 8000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Leather"),
    ATOMIC_RECONSTRUCTOR_NETHER_WART_COST("Atomic Reconstructor: Nether Wart Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 150000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Nether Wart"),
    ATOMIC_RECONSTRUCTOR_PRISMARINE_COST("Atomic Reconstructor: Prismarine Shard Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 30000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Prismarine Shard"),
    ATOMIC_RECONSTRUCTOR_CRYSTALLIZED_CANOLA_COST("Atomic Reconstructor: Crystallized Canola Seed Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 2000, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Crystallized Canola Seed"),
    ATOMIC_RECONSTRUCTOR_ETHETIC_QUARTZ_COST("Atomic Reconstructor: Ethetic Quartz Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 10, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Ethetic Quartz"),
    ATOMIC_RECONSTRUCTOR_ETHETIC_GREEN_BLOCK_COST("Atomic Reconstructor: Ethetic Green Block Creation Cost", ConfigCategories.MACHINE_RECIPE_COSTS, 10, 1, 1000000, "Amount of energy used by Atomic Reconstructor to create Ethetic Green Block"),
    LENS_COLOR_ENERGY_USE("Lens of Color: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 200, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Color to perform action"),
    LENS_DEATH_ENERGY_USE("Lens of Certain Death: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 350, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Certain Death to perform action"),
    LENS_DETONATION_ENERGY_USE("Lens of Detonation: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 250000, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Detonation to perform action"),
    LENS_KILLER_ENERGY_USE("Lens of Killer: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 2500, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Killer to perform action"),
    LENS_DISENCHANTING_ENERGY_USE("Lens of Disenchanting: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 250000, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Disenchanting to perform action"),
    LENS_MINER_ENERGY_USE("Lens of Miner: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 60000, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Miner to mine ore"),
    LENS_MINER_NETHER_ORES_ENERGY_USE("Lens of Miner: Energy Use For Nether Ores", ConfigCategories.MACHINE_RECIPE_COSTS, 70000, 1, 1000000000, "Amount of energy used by Atomic Reconstructor with Lens of Miner to mine nether ore"),
    LENS_DISRUPTION_ENERGY_USE("Lens of Disruption: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 150000, 1, 1000000000, "What?!... Is it 11?");

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

    public void initializeValue(Configuration config){
        Property property = config.get(this.category, this.name, this.defaultValue, this.desc, this.min, this.max);
        int value = property.getInt();
        if (value < this.min)
        {
            value = this.min;
            property.set(this.min);
        }
        if (value > this.max)
        {
            value = this.max;
            property.set(this.max);
        }
        this.currentValue = value;
    }

    public int getValue(){
        return this.currentValue;
    }
}
