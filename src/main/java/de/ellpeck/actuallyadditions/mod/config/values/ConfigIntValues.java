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
    LIQUID_BANNING_RING_ENERGY_USE("Ring Of Liquid Banning: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 150, 1, 1000000000, "Amount of energy Ring Of Liquid Banning uses to remove liquid block."),
    TELEPORT_STAFF_ENERGY_CAPACITY("Teleport Staff: Energy Capacity", ConfigCategories.TOOL_ENERGY_VALUES, 250000, 1000, 1000000000, "Amount of energy Teleport Staff can store."),
    TELEPORT_STAFF_ENERGY_TRANSFER("Teleport Staff: Energy Transfer Rate", ConfigCategories.TOOL_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Teleport Staff can receive per tick."),
    TELEPORT_STAFF_ENERGY_USE("Teleport Staff: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 200, 1, 1000000000, "Amount of energy Teleport Staff uses to teleport per block."),
    LEAF_BLOWER_ENERGY_USE("Leaf Blower: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 60, 1, 1000000, "Amount of energy Leaf Blower uses per tick while placed on Display Stand."),
    ADVANCED_LEAF_BLOWER_ENERGY_USE("Advanced Leaf Blower: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 60, 1, 1000000, "Amount of energy Advanced Leaf Blower uses per tick while placed on Display Stand."),
    POTION_RINGS_ENERGY_USE("Potion Rings: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 325, 1, 1000000, "Amount of energy Potion Rings use per tick while placed on Display Stand."),
    ADVANCED_POTION_RINGS_ENERGY_USE("Advanced Potion Rings: Energy Use", ConfigCategories.TOOL_ENERGY_VALUES, 325, 1, 1000000, "Amount of energy Advanced Potion Rings use per tick while placed on Display Stand."),

    ATOMIC_RECONSTRUCTOR_ENERGY_CAPACITY("Atomic Reconstructor: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 300000, 1000, 1000000000, "Amount of energy Atomic Reconstructor can store."),
    ATOMIC_RECONSTRUCTOR_ENERGY_RECEIVE("Atomic Reconstructor: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 5000, 1, 1000000000, "Amount of energy Atomic Reconstructor can receive per tick."),
    ATOMIC_RECONSTRUCTOR_ENERGY_USE("Atomic Reconstructor: Energy Use", ConfigCategories.MACHINE_ENERGY_VALUES, 1000, 1, 1000000000, "Basic amount of energy Atomic Reconstructor uses per craft."),
    BIO_REACTOR_ENERGY_CAPACITY("Bio Reactor: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 200000, 1000, 1000000000, "Amount of energy Bio Reactor can store."),
    BIO_REACTOR_ENERGY_SEND("Bio Reactor: Energy Send Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 800, 1, 1000000000, "Amount of energy Bio Reactor can send per tick."),
    BIO_REACTOR_ENERGY_PRODUCTION("Bio Reactor: Energy Production Per Type", ConfigCategories.MACHINE_ENERGY_VALUES, 2, 1, 10000, "Amount of energy Bio Reactor produces per item time.\nResulting energy production is: (this value * amount of different items) ^ 2."),
    BIO_REACTOR_BURN_TIME("Bio Reactor: Maximum Burn Time", ConfigCategories.MACHINE_RECIPE_COSTS, 200, 1, 72000, "Maximum time in ticks Bio Reactor can burn one item.\nLow values can result in nonoperability of Bio Reactor."),
    CANOLA_PRESS_ENERGY_CAPACITY("Canola Press: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 40000, 1000, 1000000000, "Amount of energy Canola Press can store."),
    CANOLA_PRESS_ENERGY_RECEIVE("Canola Press: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 100, 1, 1000000000, "Amount of energy Canola Press can receive per tick."),
    CANOLA_PRESS_RECIPE_COST("Canola Press: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 35, 1, 1000000000, "Amount of energy Canola Press uses to extract Canola Oil from one item."),
    CANOLA_PRESS_RECIPE_DURATION("Canola Press: Processing Duration", ConfigCategories.MACHINE_RECIPE_COSTS, 30, 1, 72000, "Time in ticks required to process one Canola in Canola Press."),
    CANOLA_PRESS_PRODUCTION_AMOUNT("Canola Press: Production Amount", ConfigCategories.MACHINE_RECIPE_COSTS, 80, 1, 2000, "Amount of Canola Oil (in mB) Canola Press produces from one Canola."),
    COAL_GENERATOR_ENERGY_CAPACITY("Coal Generator: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 60000, 1000, 1000000000, "Amount of energy Coal Generator can store."),
    COAL_GENERATOR_ENERGY_SEND("Coal Generator: Energy Send Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 80, 1, 1000000000, "Amount of energy Coal Generator can send per tick."),
    COAL_GENERATOR_ENERGY_PRODUCTION("Coal Generator: Energy Production", ConfigCategories.MACHINE_ENERGY_VALUES, 30, 1, 1000000000, "Amount of energy Coal Generator can produces per tick."),
    CRUSHER_ENERGY_CAPACITY("Crusher: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 60000, 1000, 1000000000, "Amount of energy Crusher and Double Crusher can store."),
    CRUSHER_ENERGY_RECEIVE("Crusher: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 100, 1, 1000000000, "Amount of energy Crusher and Double Crusher can receive per tick."),
    DISPLAY_STAND_ENERGY_CAPACITY("Display Stand: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 80000, 1000, 1000000000, "Amount of energy Display Stand can store."),
    DISPLAY_STAND_ENERGY_RECEIVE("Display Stand: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Display Stand can receive per tick."),
    ENERGIZER_ENERGY_CAPACITY("Energizer: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 50000, 1000, 1000000000, "Amount of energy Energizer can store."),
    ENERGIZER_ENERGY_RECEIVE("Energizer: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Energizer can receive per tick"),
    ENERVATOR_ENERGY_CAPACITY("Enervator: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 50000, 1000, 1000000000, "Amount of energy Enervator can store."),
    ENERVATOR_ENERGY_SEND("Enervator: Energy Send Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 1000, 1, 1000000000, "Amount of energy Enervator can send per tick"),
    FERMENTING_BARREL_RECIPE_DURATION("Fermenting Barrel: Processing Duration", ConfigCategories.MACHINE_RECIPE_COSTS, 100, 1, 72000, "Time in ticks required for one operation in Fermenting Barrel."),
    FERMENTING_BARREL_CONSUMPTION_AMOUNT("Fermenting Barrel: Consumption Amount", ConfigCategories.MACHINE_RECIPE_COSTS, 80, 1, 2000, "Amount of Canola Oil (in mB) Fermenting Barrel uses per operation."),
    FERMENTING_BARREL_PRODUCTION_AMOUNT("Fermenting Barrel: Production Amount", ConfigCategories.MACHINE_RECIPE_COSTS, 80, 1, 2000, "Amount of Refined Canola Oil Fermenting Barrel produces per operation."),
    ITEM_REPAIRER_ENERGY_CAPACITY("Item Repairer: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 300000, 1000, 1000000000, "Amount of energy Item Repairer can store."),
    ITEM_REPAIRER_ENERGY_RECEIVE("Item Repairer: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 6000, 1, 1000000000, "Amount of energy Item Repairer can receive per tick."),
    ITEM_REPAIRER_ENERGY_USE("Item Repairer: Energy Use", ConfigCategories.MACHINE_ENERGY_VALUES, 2500, 1, 1000000000, "Amount of energy Item Repairer uses per tick."),
    LASER_RELAY_MAXIMUM_DISTANCE("Laser Relay: Maximum Distance", ConfigCategories.MACHINE_VALUES, 15, 1, 48, "Maximum distance in blocks between two Laser Relays"),
    LASER_RELAY_MAXIMUM_UPGRADED_DISTANCE("Laser Relay: Maximum Distance With Range Upgrade", ConfigCategories.MACHINE_VALUES, 35, 1, 96, "Maximum distance in blocks between two Laser Relays with Range upgrade"),
    LAVA_FACTORY_ENERGY_CAPACITY("Lava Factory: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 300000, 1000, 1000000000, "Amount of energy Lava Factory can store."),
    LAVA_FACTORY_ENERGY_RECEIVE("Lava Factory: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 2000, 1, 1000000000, "Amount of energy Lava Factory can receive per tick."),
    LAVA_FACTORY_ENERGY_USE("Lava Factory: Energy Use", ConfigCategories.MACHINE_ENERGY_VALUES, 150000, 1, 1000000000, "Amount of energy Lava Factory uses to create block of lava."),
    LAVA_FACTORY_PRODUCTION_DURATION("Lava Factory: Production Duration", ConfigCategories.MACHINE_RECIPE_COSTS, 200, 1, 72000, "Time in ticks required by Lava Factory to produce block of lava."),
    LEAF_GENERATOR_ENERGY_CAPACITY("Leaf-Eating Generator: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 35000, 1000, 1000000000, "Amount of energy Leaf-Eating Generator can store."),
    LEAF_GENERATOR_ENERGY_SEND("Leaf-Eating Generator: Energy Send Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 450, 1, 1000000000, "Amount of energy Leaf-Eating Generator can send per tick."),
    LEAF_GENERATOR_ENERGY_PRODUCTION("Leaf-Eating Generator: Energy Production", ConfigCategories.MACHINE_ENERGY_VALUES, 300, 1, 1000000000, "Amount of energy Leaf-Eating Generator produces per leaf block."),
    LEAF_GENERATOR_WORK_RANGE("Leaf-Eating Generator: Work Range", ConfigCategories.MACHINE_VALUES, 7, 1, 16, "Radius (in blocks) in which Leaf-Eating Generator consumes leaves."),
    OIL_GENERATOR_ENERGY_CAPACITY("Oil Generator: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 50000, 1000, 1000000000, "Amount of energy Oil Generator can store."),
    OIL_GENERATOR_ENERGY_SEND("Oil Generator: Energy Send Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 150, 1, 1000000000, "Amount of energy Oil Generator can send per tick."),
    PHANTOMFACE_WORK_RANGE("Phantomface: Work Range", ConfigCategories.MACHINE_VALUES, 16, 1, 32, "Distance in block in which Phantomface, Phantom Itemface, Phantom Liquidface, Phantom Energyface and Phantom Redstoneface work."),
    PHANTOM_PLACER_WORK_RANGE("Phantom Breaker/Placer: Work Range", ConfigCategories.MACHINE_VALUES, 3, 1, 8, "Distance in block in which Phantom Breaker and Placer work."),
    PLAYER_INTERFACE_ENERGY_CAPACITY("Player Interface: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 30000, 1000, 1000000000, "Amount of energy Player Interface can store."),
    PLAYER_INTERFACE_ENERGY_RECEIVE("Player Interface: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 50, 1, 1000000000, "Amount of energy Player Interface can receive per tick.\nThis option defines rate of charging items in player's inventory."),
    PLAYER_INTERFACE_WORK_RANGE("Player Interface: Work Range", ConfigCategories.MACHINE_VALUES, 32, 1, 64, "Distance in block in which Player Interface works."),
    POWERED_FURNACE_ENERGY_CAPACITY("Powered Furnace: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 30000, 1000, 1000000000, "Amount of energy Powered Furnace can store."),
    POWERED_FURNACE_ENERGY_RECEIVE("Powered Furnace: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 150, 1, 1000000000, "Amount of energy Powered Furnace can receive per tick."),
    POWERED_FURNACE_ENERGY_USE("Powered Furnace: Energy Use", ConfigCategories.MACHINE_RECIPE_COSTS, 25, 1, 1000000000, "Amount of energy Powered Furnace uses per tick."),
    POWERED_FURNACE_PROCESSING_DURATION("Powered Furnace: Processing Time", ConfigCategories.MACHINE_RECIPE_COSTS, 80, 1, 72000, "Time in ticks required to smelt one item in Powered Furnace."),
    SHOCK_ABSORBER_ENERGY_CAPACITY("Shock Absorber: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 300000, 1000, 1000000000, "Amount of energy Shock Absorber can store."),
    SHOCK_ABSORBER_ENERGY_RECEIVE("Shock Absorber: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 400, 1, 1000000000, "Amount of energy Shock Absorber can receive per tick."),
    SHOCK_ABSORBER_ENERGY_USE("Shock Absorber: Energy Use", ConfigCategories.MACHINE_ENERGY_VALUES, 300, 1, 1000000000, "Amount of energy Shock Absorber uses to protect block or entity."),
    SHOCK_ABSORBER_WORK_RANGE("Shock Absorber: Work Range", ConfigCategories.MACHINE_VALUES, 5, 1, 16, "Distance in block in which Shock Absorber protects blocks and entities."),
    VERTICAL_DIGGER_ENERGY_CAPACITY("Vertical Digger: Energy Capacity", ConfigCategories.MACHINE_ENERGY_VALUES, 200000, 1000, 1000000000, "Amount of energy Vertical Digger can store."),
    VERTICAL_DIGGER_ENERGY_RECEIVE("Vertical Digger: Energy Receive Rate", ConfigCategories.MACHINE_ENERGY_VALUES, 2000, 1, 1000000000, "Amount of energy Vertical Digger can receive per tick."),
    VERTICAL_DIGGER_ENERGY_USE_PER_BLOCK("Vertical Digger: Energy Use Per Block", ConfigCategories.MACHINE_ENERGY_VALUES, 650, 1, 1000000000, "Amount of energy Vertical Digger uses to mine block."),
    VERTICAL_DIGGER_ENERGY_USE_PER_ORE("Vertical Digger: Energy Use Per Ore", ConfigCategories.MACHINE_ENERGY_VALUES, 1950, 1, 1000000000, "Amount of energy Vertical Digger uses to mine block in mode 'Only Ores'"),
    VERTICAL_DIGGER_WORK_RANGE("Vertical Digger: Work Range", ConfigCategories.MACHINE_VALUES, 2, 1, 8, "Distance in blocks in which Vertical Digger can mine blocks.");

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
