package ellpeck.actuallyadditions.config.values;

import ellpeck.actuallyadditions.config.ConfigCategories;
import ellpeck.actuallyadditions.config.ConfigValues;

public enum ConfigIntValues{

    JAM_VILLAGER_ID("Jam Villager: ID", ConfigCategories.WORLD_GEN, 493827, 100, 1000000, "The ID of the Jam Villager"),

    LEAF_BLOWER_RANGE_SIDES("Leaf Blower: Side Range", ConfigCategories.TOOL_VALUES, 5, 1, 25, "The Leaf Blower's Range to the Sides"),
    LEAF_BLOWER_RANGE_UP("Leaf Blower: Range Up", ConfigCategories.TOOL_VALUES, 1, 1, 10, "The Leaf Blower's Range Up"),

    BLACK_QUARTZ_BASE_AMOUNT("Black Quartz Amount", ConfigCategories.WORLD_GEN, 3, 1, 50, "How big a Black Quartz Vein is at least"),
    BLACK_QUARTZ_ADD_CHANCE("Black Quartz Additional Chance", ConfigCategories.WORLD_GEN, 3, 0, 50, "How much bigger than the Base Amount a Black Quartz Vein can get"),
    BLACK_QUARTZ_CHANCE("Black Quartz Chance", ConfigCategories.WORLD_GEN, 25, 1, 150, "How often the Black Quartz tries to generate"),
    BLACK_QUARTZ_MIN_HEIGHT("Black Quartz Min Height", ConfigCategories.WORLD_GEN, 0, 0, 256, "How high the Black Quartz starts to generate"),
    BLACK_QUARTZ_MAX_HEIGHT("Black Quartz Max Height", ConfigCategories.WORLD_GEN, 25, 0, 256, "How high the Black Quartz stops to generate at"),

    COMPOST_AMOUNT("Compost: Amount Needed To Convert", ConfigCategories.MACHINE_VALUES, 10, 1, 64, "How many items are needed in the Compost to convert to Fertilizer"),
    COMPOST_TIME("Compost: Conversion Time Needed", ConfigCategories.MACHINE_VALUES, 1000, 30, 10000, "How long the Compost needs to convert to Fertilizer"),

    FISHER_TIME("Fishing Net: Time Needed", ConfigCategories.MACHINE_VALUES, 2000, 50, 50000, "How long it takes on Average until the Fishing Net catches a Fish"),

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

    GRINDER_CRUSH_TIME("Crusher: Crush Time", ConfigCategories.MACHINE_VALUES, 200, 10, 1000, "How long the Crusher takes to crush an item"),
    GRINDER_DOUBLE_CRUSH_TIME("Double Crusher: Crush Time", ConfigCategories.MACHINE_VALUES, 300, 10, 1000, "How long the Double Crusher takes to crush an item"),
    FURNACE_DOUBLE_SMELT_TIME("Double Furnace: Smelt Time", ConfigCategories.MACHINE_VALUES, 300, 10, 1000, "How long the Double Furnace takes to crush an item"),

    REPAIRER_SPEED_SLOWDOWN("Item Repairer: Speed Slowdown", ConfigCategories.MACHINE_VALUES, 2, 1, 100, "How much slower the Item Repairer repairs"),
    HEAT_COLLECTOR_BLOCKS("Heat Collector: Blocks Needed", ConfigCategories.MACHINE_VALUES, 4, 1, 5, "How many Blocks are needed for the Heat Collector to power Machines above it"),
    HEAT_COLLECTOR_LAVA_CHANCE("Heat Collector: Random Chance", ConfigCategories.MACHINE_VALUES, 10000, 10, 100000, "The Chance of the Heat Collector destroying a Lava Block around (Default Value 2000 meaning a 1/2000 Chance!)"),

    GLASS_TIME_NEEDED("Greenhouse Glass: Time Needed", ConfigCategories.MACHINE_VALUES, 1000, 10, 1000000, "The Time Needed for the Greenhouse Glass to grow a Plant below it"),

    BREAKER_TIME_NEEDED("Breaker and Placer: Time Needed", ConfigCategories.MACHINE_VALUES, 15, 1, 10000, "The Time Needed for the Breaker and the Placer to place or break a Block");


    public final String name;
    public final String category;
    public final int defaultValue;
    public final int min;
    public final int max;
    public final String desc;

    ConfigIntValues(String name, ConfigCategories category, int defaultValue, int min, int max, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public int getValue(){
        return ConfigValues.intValues[this.ordinal()];
    }

}
