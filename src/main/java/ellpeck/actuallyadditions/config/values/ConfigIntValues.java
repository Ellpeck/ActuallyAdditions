/*
 * This file ("ConfigIntValues.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.config.values;

import ellpeck.actuallyadditions.config.ConfigCategories;
import net.minecraftforge.fluids.FluidContainerRegistry;

public enum ConfigIntValues{

    JAM_VILLAGER_ID("Jam Villager: ID", ConfigCategories.WORLD_GEN, 493827, 100, 1000000, "The ID of the Jam Villager"),

    LEAF_BLOWER_RANGE_SIDES("Leaf Blower: Side Range", ConfigCategories.TOOL_VALUES, 5, 1, 25, "The Leaf Blower's Range to the Sides"),
    LEAF_BLOWER_RANGE_UP("Leaf Blower: Range Up", ConfigCategories.TOOL_VALUES, 1, 1, 10, "The Leaf Blower's Range Up"),

    BLACK_QUARTZ_BASE_AMOUNT("Black Quartz: Amount", ConfigCategories.WORLD_GEN, 5, 1, 50, "How big a Black Quartz Vein is at least"),
    BLACK_QUARTZ_ADD_CHANCE("Black Quartz: Additional Chance", ConfigCategories.WORLD_GEN, 3, 0, 50, "How much bigger than the Base Amount a Black Quartz Vein can get"),
    BLACK_QUARTZ_CHANCE("Black Quartz: Chance", ConfigCategories.WORLD_GEN, 10, 1, 150, "How often the Black Quartz tries to generate"),
    BLACK_QUARTZ_MIN_HEIGHT("Black Quartz: Min Height", ConfigCategories.WORLD_GEN, 0, 0, 256, "How high the Black Quartz starts to generate"),
    BLACK_QUARTZ_MAX_HEIGHT("Black Quartz: Max Height", ConfigCategories.WORLD_GEN, 45, 0, 256, "How high the Black Quartz stops to generate at"),

    COMPOST_AMOUNT("Compost: Amount Needed To Convert", ConfigCategories.MACHINE_VALUES, 10, 1, 64, "How many items are needed in the Compost to convert to Fertilizer"),
    COMPOST_TIME("Compost: Conversion Time Needed", ConfigCategories.MACHINE_VALUES, 1000, 30, 10000, "How long the Compost needs to convert to Fertilizer"),

    FISHER_TIME("Fishing Net: Time Needed", ConfigCategories.MACHINE_VALUES, 15000, 50, 500000, "How long it takes on Average until the Fishing Net catches a Fish"),

    FEEDER_REACH("Feeder: Reach", ConfigCategories.MACHINE_VALUES, 5, 1, 20, "The Radius of Action of the Feeder"),
    FEEDER_TIME("Feeder: Time Needed", ConfigCategories.MACHINE_VALUES, 100, 50, 5000, "The time spent between feeding animals with the Feeder"),
    FEEDER_THRESHOLD("Feeder: Threshold", ConfigCategories.MACHINE_VALUES, 30, 3, 500, "How many animals need to be in the area for the Feeder to stop"),

    KNIFE_DAMAGE("Knife: Max Uses", ConfigCategories.TOOL_VALUES, 100, 5, 5000, "How often the Knife can be crafted with"),

    EMERALD_HARVEST_LEVEL("Emerald: Harvest Level", ConfigCategories.TOOL_VALUES, 3, 0, 3, "What Harvest Level Emerald Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)"),
    EMERALD_USES("Emerald: Max Uses", ConfigCategories.TOOL_VALUES, 2000, 50, 10000, "How often Emerald Tools can be used"),
    EMERALD_ENCHANTABILITY("Emerald: Enchantability", ConfigCategories.TOOL_VALUES, 15, 1, 30, "How enchantable an Emerald Tool is"),

    OBSIDIAN_HARVEST_LEVEL("Obsidian: Harvest Level", ConfigCategories.TOOL_VALUES, 3, 0, 3, "What Harvest Level Obsidian Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)"),
    OBSIDIAN_USES("Obsidian: Max Uses", ConfigCategories.TOOL_VALUES, 8000, 50, 20000, "How often Obsidian Tools can be used"),
    OBSIDIAN_ENCHANTABILITY("Obsidian: Enchantability", ConfigCategories.TOOL_VALUES, 15, 1, 30, "How enchantable an Obsidian Tool is"),

    QUARTZ_HARVEST_LEVEL("Quartz: Harvest Level", ConfigCategories.TOOL_VALUES, 2, 0, 3, "What Harvest Level Quartz Tools have (0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond)"),
    QUARTZ_USES("Quartz: Max Uses", ConfigCategories.TOOL_VALUES, 280, 50, 20000, "How often Quartz Tools can be used"),
    QUARTZ_ENCHANTABILITY("Quartz: Enchantability", ConfigCategories.TOOL_VALUES, 10, 1, 30, "How enchantable an Quartz Tool is"),

    EMERALD_ARMOR_DURABILITY("Emerald Armor: Durability", ConfigCategories.ARMOR_VALUES, 1500, 10, 10000, "The Durability of Emerald Armor"),
    EMERALD_ARMOR_HEAD_DAMAGE("Emerald Helmets: Damage Reduction", ConfigCategories.ARMOR_VALUES, 5, 0, 10, "The Damage Reduction of Emerald Helmets"),
    EMERALD_ARMOR_CHEST_DAMAGE("Emerald Chests: Damage Reduction", ConfigCategories.ARMOR_VALUES, 9, 0, 10, "The Damage Reduction of Emerald Chests"),
    EMERALD_ARMOR_LEGS_DAMAGE("Emerald Legs: Damage Reduction", ConfigCategories.ARMOR_VALUES, 8, 0, 10, "The Damage Reduction of Emerald Legs"),
    EMERALD_ARMOR_BOOTS_DAMAGE("Emerald Boots: Damage Reduction", ConfigCategories.ARMOR_VALUES, 5, 0, 10, "The Damage Reduction of Emerald Boots"),
    EMERALD_ARMOR_ENCHANTABILITY("Emerald Armor: Enchantability", ConfigCategories.ARMOR_VALUES, 15, 1, 30, "The Enchantability of Emerald Armor"),

    QUARTZ_ARMOR_DURABILITY("Quartz Armor: Durability", ConfigCategories.ARMOR_VALUES, 200, 10, 10000, "The Durability of Quartz Armor"),
    QUARTZ_ARMOR_HEAD_DAMAGE("Quartz Helmets: Damage Reduction", ConfigCategories.ARMOR_VALUES, 3, 0, 10, "The Damage Reduction of Quartz Helmets"),
    QUARTZ_ARMOR_CHEST_DAMAGE("Quartz Chests: Damage Reduction", ConfigCategories.ARMOR_VALUES, 6, 0, 10, "The Damage Reduction of Quartz Chests"),
    QUARTZ_ARMOR_LEGS_DAMAGE("Quartz Legs: Damage Reduction", ConfigCategories.ARMOR_VALUES, 5, 0, 10, "The Damage Reduction of Quartz Legs"),
    QUARTZ_ARMOR_BOOTS_DAMAGE("Quartz Boots: Damage Reduction", ConfigCategories.ARMOR_VALUES, 3, 0, 10, "The Damage Reduction of Quartz Boots"),
    QUARTZ_ARMOR_ENCHANTABILITY("Quartz Armor: Enchantability", ConfigCategories.ARMOR_VALUES, 8, 1, 30, "The Enchantability of Quartz Armor"),

    OBSIDIAN_ARMOR_DURABILITY("Obsidian Armor: Durability", ConfigCategories.ARMOR_VALUES, 7000, 10, 10000, "The Durability of Obsidian Armor"),
    OBSIDIAN_ARMOR_HEAD_DAMAGE("Obsidian Helmets: Damage Reduction", ConfigCategories.ARMOR_VALUES, 3, 0, 10, "The Damage Reduction of Obsidian Helmets"),
    OBSIDIAN_ARMOR_CHEST_DAMAGE("Obsidian Chests: Damage Reduction", ConfigCategories.ARMOR_VALUES, 4, 0, 10, "The Damage Reduction of Obsidian Chests"),
    OBSIDIAN_ARMOR_LEGS_DAMAGE("Obsidian Legs: Damage Reduction", ConfigCategories.ARMOR_VALUES, 3, 0, 10, "The Damage Reduction of Obsidian Legs"),
    OBSIDIAN_ARMOR_BOOTS_DAMAGE("Obsidian Boots: Damage Reduction", ConfigCategories.ARMOR_VALUES, 1, 0, 10, "The Damage Reduction of Obsidian Boots"),
    OBSIDIAN_ARMOR_ENCHANTABILITY("Obsidian Armor: Enchantability", ConfigCategories.ARMOR_VALUES, 10, 1, 30, "The Enchantability of Obsidian Armor"),

    GRINDER_CRUSH_TIME("Crusher: Time", ConfigCategories.MACHINE_VALUES, 100, 10, 1000, "How long the Crusher takes to crush an item"),
    GRINDER_DOUBLE_CRUSH_TIME("Double Crusher: Time", ConfigCategories.MACHINE_VALUES, 150, 10, 1000, "How long the Double Crusher takes to crush an item"),
    FURNACE_DOUBLE_SMELT_TIME("Double Furnace: Time", ConfigCategories.MACHINE_VALUES, 80, 10, 1000, "How long the Double Furnace takes to crush an item"),

    REPAIRER_SPEED_SLOWDOWN("Repairer: Speed Slowdown", ConfigCategories.MACHINE_VALUES, 2, 1, 100, "How much slower the Item Repairer repairs"),
    HEAT_COLLECTOR_BLOCKS("Heat Collector: Blocks Needed", ConfigCategories.MACHINE_VALUES, 4, 1, 5, "How many Blocks are needed for the Heat Collector to power Machines above it"),
    HEAT_COLLECTOR_LAVA_CHANCE("Heat Collector: Random Chance", ConfigCategories.MACHINE_VALUES, 15000, 10, 100000, "The Chance of the Heat Collector destroying a Lava Block around (Default Value 2000 meaning a 1/2000 Chance!)"),

    GLASS_TIME_NEEDED("Greenhouse Glass: Time", ConfigCategories.MACHINE_VALUES, 300, 1, 10000, "Time Needed for the Greenhouse Glass to grow a Plant below it"),

    BREAKER_TIME_NEEDED("Breaker and Placer: Time Needed", ConfigCategories.MACHINE_VALUES, 15, 1, 10000, "The Time Needed for the Breaker and the Placer to place or break a Block"),
    DROPPER_TIME_NEEDED("Dropper: Time Needed", ConfigCategories.MACHINE_VALUES, 10, 1, 10000, "The Time Needed for the Dropper to drop an Item"),

    CAT_DROP_CHANCE("Cat Drops: Chance", ConfigCategories.OTHER, 5000, 5, 10000000, "The 1 in X chance for a Hairy Ball to Drop from a Cat with X being this value"),
    SPIDER_DROP_CHANCE("Cobweb Drop from Spider: Chance", ConfigCategories.MOB_DROPS, 500, 1, 1000000000, "The 1 in X chance for a Cobweb to drop from a Spider"),
    BAT_DROP_CHANCE("Wings from Bats: Chance", ConfigCategories.MOB_DROPS, 10, 1, 1000000000, "The 1 in X chance for a Wing to drop from a Bat"),

    RICE_AMOUNT("Rice Amount", ConfigCategories.WORLD_GEN, 15, 1, 100, "The Chance of Rice generating"),
    CANOLA_AMOUNT("Canola Amount", ConfigCategories.WORLD_GEN, 10, 1, 50, "The Chance of Canola generating"),
    FLAX_AMOUNT("Flax Amount", ConfigCategories.WORLD_GEN, 8, 1, 50, "The Chance of Flax generating"),
    COFFEE_AMOUNT("Coffee Amount", ConfigCategories.WORLD_GEN, 6, 1, 50, "The Chance of Coffee generating"),
    RICE_CHANCE("Rice Chance", ConfigCategories.WORLD_GEN, 50, 1, 3000, "The 1 in X chance for Rice to generate"),
    NORMAL_PLANT_CHANCE("Plant Chance", ConfigCategories.WORLD_GEN, 400, 1, 3000, "The 1 in X chance for Flax, Coffee and Canola to generate"),
    TREASURE_CHEST_CHANCE("Treasure Chest Chance", ConfigCategories.WORLD_GEN, 300, 1, 3000, "The 1 in X chance for a Treasure Chest to generate in an Ocean"),
    TREASURE_CHEST_MIN_HEIGHT("Treasure Chest Min Height", ConfigCategories.WORLD_GEN, 25, 0, 65, "The Min Height for a Treasure Chest to generate"),
    TREASURE_CHEST_MAX_HEIGHT("Treasure Chest Max Height", ConfigCategories.WORLD_GEN, 45, 0, 65, "The Max Height for a Treasure Chest to generate"),

    GRINDER_ENERGY_USED("Energy Use: Crusher", ConfigCategories.MACHINE_VALUES, 40, 1, 500, "The Amount of Energy used by the Crusher per Tick"),
    GRINDER_DOUBLE_ENERGY_USED("Energy Use: Double Crusher", ConfigCategories.MACHINE_VALUES, 60, 1, 500, "The Amount of Energy used by the Double Crusher per Tick"),
    FURNACE_SOLAR_ENERGY_PRODUCED("Energy Production: Furnace Solar", ConfigCategories.MACHINE_VALUES, 15, 1, 500, "The Amount of Energy produced by the Solar per Tick"),
    HEAT_COLLECTOR_ENERGY_PRODUCED("Energy Production: Heat Collectors", ConfigCategories.MACHINE_VALUES, 60, 1, 500, "The Amount of Energy produced by the Heat Collector per Tick"),
    REPAIRER_ENERGY_USED("Energy Use: Repairer", ConfigCategories.MACHINE_VALUES, 1250, 1, 5000, "The Amount of Energy used by the Repairer per Tick"),
    FURNACE_ENERGY_USED("Energy Use: Double Furnace", ConfigCategories.MACHINE_VALUES, 25, 1, 500, "The Amount of Energy used by the Double Furnace per Tick"),

    PRESS_PROCESSING_TIME("Canola Press: Processing Time", ConfigCategories.MACHINE_VALUES, 30, 1, 1000, "The Amount of time it takes to process one Canola"),
    PRESS_MB_PRODUCED("Canola Press: mB Produced", ConfigCategories.MACHINE_VALUES, 100, 1, 5000, "The Amount of Canola Oil produced from one Canola"),
    PRESS_ENERGY_USED("Energy Use: Canola Press", ConfigCategories.MACHINE_VALUES, 35, 10, 500, "The Amount of Energy used by the Canola Press per Tick"),

    BARREL_MB_PRODUCED("Fermenting Barrel: mB Produced", ConfigCategories.MACHINE_VALUES, 50, 1, 3000, "The Amount of mB produced by the Barrel per cycle"),
    BARREL_PROCESSING_TIME("Fermenting Barrel: Processing Time", ConfigCategories.MACHINE_VALUES, 100, 1, 5000, "The Amount of time it takes to process one Canola Oil to Oil"),
    COAL_GEN_ENERGY_PRODUCED("Coal Generator: Energy Produced", ConfigCategories.MACHINE_VALUES, 30, 1, 500, "The Amount of Energy generated by the Coal Generator"),

    PHANTOMFACE_RANGE("Phantomface: Default Range", ConfigCategories.MACHINE_VALUES, 16, 3, 100, "The Default Range of the Phantomface"),

    OIL_GEN_ENERGY_PRODUCED("Oil Generator: Energy Produced", ConfigCategories.MACHINE_VALUES, 76, 1, 500, "The Amount of Energy generated by the Oil Generator"),
    OIL_GEN_FUEL_USED("Oil Generator: Fuel Usage", ConfigCategories.MACHINE_VALUES, 50, 1, 300, "The Amount of Fuel used per Burnup in the Oil Generator"),
    OIL_GEN_BURN_TIME("Oil Generator: Burn Time", ConfigCategories.MACHINE_VALUES, 100, 1, 1000, "The Amount of Time Fuel keeps burning for"),

    PHANTOM_PLACER_TIME("Phantom Placer and Breaker: Time Needed", ConfigCategories.MACHINE_VALUES, 30, 1, 500, "The Amount of Time a Phantom Placer/Breaker needs"),
    PHANTOM_PLACER_RANGE("Phantom Placer and Breaker: Range", ConfigCategories.MACHINE_VALUES, 3, 1, 100, "The Default Range of the Phantom Placer/Breaker"),

    LAVA_FACTORY_ENERGY_USED("Lava Factory: Energy Used", ConfigCategories.MACHINE_VALUES, 150000, 10, 3000000, "The amount of Energy used by the Lava Factory per Bucket of Lava produced"),
    LAVA_FACTORY_TIME("Lava Factory: Production Time", ConfigCategories.MACHINE_VALUES, 200, 5, 10000, "The amount of time it takes for the Lava Factory to produce one Bucket"),

    COFFEE_MACHINE_ENERGY_USED("Coffee Machine: Energy Use", ConfigCategories.MACHINE_VALUES, 150, 10, 3000, "The amount of Energy used by the Coffee Machine per Tick"),
    COFFEE_CACHE_ADDED_PER_ITEM("Coffee Machine: Coffee added per Cup", ConfigCategories.MACHINE_VALUES, 1, 1, 300, "The amount of Coffee added by one Coffee Item in the Coffee Machine"),
    COFFEE_CACHE_USED_PER_ITEM("Coffee Machine: Coffee used per Cup", ConfigCategories.MACHINE_VALUES, 10, 1, 300, "The amount of Coffee used to brew one Coffee in the Coffee Machine"),
    COFFEE_MACHINE_TIME_USED("Coffee Machine: Time to Brew", ConfigCategories.MACHINE_VALUES, 500, 10, 10000, "The amount of time the Coffee Machine takes to brew a Coffee"),
    COFFEE_MACHINE_WATER_USED("Coffee Machine: Water Used per Cup", ConfigCategories.MACHINE_VALUES, 500, 1, 4*FluidContainerRegistry.BUCKET_VOLUME, "The amount of Water the Coffee Machine uses per Cup"),

    COFFEE_DRINK_AMOUNT("Coffee: Drink Amount", ConfigCategories.OTHER, 4, 1, 100, "How often a Coffee can be drunk from"),
    DRILL_ENERGY_USE("Drill: Energy Use Per Block or Hit", ConfigCategories.DRILL_VALUES, 100, 5, 10000, "How much Energy the Drill uses per Block"),

    DRILL_SPEED_EXTRA_USE("Speed Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 50, 0, 10000, "How much extra Energy the Speed Upgrade uses"),
    DRILL_SPEED_II_EXTRA_USE("Speed II Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 75, 0, 10000, "How much extra Energy the Speed II Upgrade uses"),
    DRILL_SPEED_III_EXTRA_USE("Speed III Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 175, 0, 10000, "How much extra Energy the Speed III Upgrade uses"),
    DRILL_SILK_EXTRA_USE("Silk Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 30, 0, 10000, "How much extra Energy the Silk Upgrade uses"),
    DRILL_FORTUNE_EXTRA_USE("Fortune Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 40, 0, 10000, "How much extra Energy the Fortune Upgrade uses"),
    DRILL_FORTUNE_II_EXTRA_USE("Fortune II Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 60, 0, 10000, "How much extra Energy the Fortune II Upgrade uses"),
    DRILL_THREE_BY_THREE_EXTRA_USE("3x3 Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 10, 0, 10000, "How much extra Energy the 3x3 Upgrade uses"),
    DRILL_FIVE_BY_FIVE_EXTRA_USE("5x5 Upgrade: Extra Energy Use", ConfigCategories.DRILL_VALUES, 30, 0, 10000, "How much extra Energy the 5x5 Upgrade uses"),

    TELE_STAFF_REACH("TeleStaff: Range", ConfigCategories.MACHINE_VALUES, 100, 5, 200, "How far the TeleStaff can teleport you"),
    TELE_STAFF_ENERGY_USE("TeleStaff: Energy Use per Block", ConfigCategories.MACHINE_VALUES, 200, 1, 5000, "How much energy the TeleStaff uses per Block you teleport"),
    TELE_STAFF_WAIT_TIME("TeleStaff: Wait Time", ConfigCategories.MACHINE_VALUES, 30, 0, 500, "The time the TeleStaff takes between Teleports"),

    GROWTH_RING_RANGE("Growth Ring: Range", ConfigCategories.MACHINE_VALUES, 3, 1, 30, "The Range the Growth Ring has"),
    GROWTH_RING_COOLDOWN("Growth Ring: Cooldown Time", ConfigCategories.MACHINE_VALUES, 30, 0, 1000, "The Time between Growth Bursts"),
    GROWTH_RING_ENERGY_USE("Growth Ring: Energy Used", ConfigCategories.MACHINE_VALUES, 550, 10, 6000, "The Amount of Energy used per Tick"),
    GROWTH_RING_GROWTH_PER_CYCLE("Growth Ring: Growth Ticks per Cycle", ConfigCategories.MACHINE_VALUES, 45, 1, 200, "The Amount of plants that get ticked per cycle"),

    MAGNET_RING_RANGE("Magnet Ring: Range", ConfigCategories.MACHINE_VALUES, 5, 3, 30, "The Range of the Magnet Ring"),
    MAGNET_RING_ENERGY_USE("Magnet Ring: Energy Used", ConfigCategories.MACHINE_VALUES, 30, 0, 500, "The Amount of Energy the Magnet Ring uses per tick"),

    WATER_RING_RANGE("Water Ring: Range", ConfigCategories.MACHINE_VALUES, 3, 1, 10, "The Range of the Water Ring"),
    WATER_RING_ENERGY_USE("Magnet Ring: Energy Used", ConfigCategories.MACHINE_VALUES, 30, 0, 500, "The Amount of Energy the Water Ring uses per Block"),

    ORE_MAGNET_MAX_TIMER("Ore Magnet: Max Timer", ConfigCategories.MACHINE_VALUES, 20, 1, 2000, "The approximate Time it takes for the Ore Magnet to search for a new block to mine"),
    ORE_MAGNET_RANGE("Ore Magnet: Range", ConfigCategories.MACHINE_VALUES, 10, 1, 60, "The range of the Ore Magnet"),
    ORE_MAGNET_OIL_USE("Ore Magnet: Oil Use", ConfigCategories.MACHINE_VALUES, 30, 0, 5000, "The amount of oil the Ore Magnet uses every Block"),
    ORE_MAGNET_ENERGY_USE("Ore Magnet: Energy USe", ConfigCategories.MACHINE_VALUES, 250, 10, 10000, "The amount of Energy the Ore Magnet uses every tick"),

    LEAF_GENERATOR_ENERGY_PRODUCED("Leaf Generator: Energy Produce", ConfigCategories.MACHINE_VALUES, 120, 1, 10000, "How much Energy the Leaf Generator produces per Leaf broken"),
    LEAF_GENERATOR_COOLDOWN_TIME("Leaf Generator: Cooldown Time", ConfigCategories.MACHINE_VALUES, 5, 0, 100, "The amount of ticks that it takes util another Leaf gets proken"),
    LEAF_GENERATOR_RANGE("Leaf Generator: Range", ConfigCategories.MACHINE_VALUES, 7, 1, 100, "The radius of a leaf generator"),

    DIRECTIONAL_BREAKER_RF_PER_BLOCK("Directional Breaker: RF per Block", ConfigCategories.MACHINE_VALUES, 5, 0, 1000, "The amount of RF the Directional Breaker uses to break each block"),
    DIRECTIONAL_BREAKER_RANGE("Directional Breaker: Range", ConfigCategories.MACHINE_VALUES, 8, 1, 1000, "The range of the Directional Breaker"),

    RANGED_COLLECTOR_RANGE("Ranged Collector: Range", ConfigCategories.MACHINE_VALUES, 6, 1, 30, "The range of the Ranged Collector");

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
