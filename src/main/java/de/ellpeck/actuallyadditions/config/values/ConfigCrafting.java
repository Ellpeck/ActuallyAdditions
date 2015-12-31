/*
 * This file ("ConfigCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.config.values;

import de.ellpeck.actuallyadditions.config.ConfigCategories;

public enum ConfigCrafting{

    COMPOST("Compost", ConfigCategories.BLOCKS_CRAFTING),
    CLOUD("Smiley Cloud", ConfigCategories.BLOCKS_CRAFTING),
    XP_SOLIDIFIER("Experience Solidifier", ConfigCategories.BLOCKS_CRAFTING),
    WOOD_CASING("Wood Casing", ConfigCategories.BLOCKS_CRAFTING),
    IRON_CASING("Iron Casing", ConfigCategories.BLOCKS_CRAFTING),
    FISHING_NET("Fishing Net", ConfigCategories.BLOCKS_CRAFTING),
    REPAIRER("Repairer", ConfigCategories.BLOCKS_CRAFTING),
    SOLAR_PANEL("Solar Panel", ConfigCategories.BLOCKS_CRAFTING),
    HEAT_COLLECTOR("Heat Collector", ConfigCategories.BLOCKS_CRAFTING),
    INPUTTER("ESD", ConfigCategories.BLOCKS_CRAFTING),
    CRUSHER("Crusher", ConfigCategories.BLOCKS_CRAFTING),
    DOUBLE_CRUSHER("Double Crusher", ConfigCategories.BLOCKS_CRAFTING),
    DOUBLE_FURNACE("Double Furnace", ConfigCategories.BLOCKS_CRAFTING),
    FEEDER("Feeder", ConfigCategories.BLOCKS_CRAFTING),
    GIANT_CHEST("Storage Crate", ConfigCategories.BLOCKS_CRAFTING),

    GREENHOUSE_GLASS("Greenhouse Glass", ConfigCategories.BLOCKS_CRAFTING),
    BREAKER("Breaker", ConfigCategories.BLOCKS_CRAFTING),
    PLACER("Placer", ConfigCategories.BLOCKS_CRAFTING),
    DROPPER("Dropper", ConfigCategories.BLOCKS_CRAFTING),
    SPEED_UPGRADE("Speed Upgrade", ConfigCategories.BLOCKS_CRAFTING),

    BAGUETTE("Baguette", ConfigCategories.FOOD_CRAFTING),
    PIZZA("Pizza", ConfigCategories.FOOD_CRAFTING),
    HAMBURGER("Hamburger", ConfigCategories.FOOD_CRAFTING),
    BIG_COOKIE("Big Cookie", ConfigCategories.FOOD_CRAFTING),
    SUB("Sub Sandwich", ConfigCategories.FOOD_CRAFTING),
    FRENCH_FRY("French Fry", ConfigCategories.FOOD_CRAFTING),
    FRENCH_FRIES("French Fries", ConfigCategories.FOOD_CRAFTING),
    FISH_N_CHIPS("Fish And Chips", ConfigCategories.FOOD_CRAFTING),
    CHEESE("Cheese", ConfigCategories.FOOD_CRAFTING),
    PUMPKIN_STEW("Pumpkin Stew", ConfigCategories.FOOD_CRAFTING),
    CARROT_JUICE("Carrot Juice", ConfigCategories.FOOD_CRAFTING),
    SPAGHETTI("Spaghetti", ConfigCategories.FOOD_CRAFTING),
    NOODLE("Noodle", ConfigCategories.FOOD_CRAFTING),
    CHOCOLATE("Chocolate", ConfigCategories.FOOD_CRAFTING),
    CHOCOLATE_CAKE("Chocolate Cake", ConfigCategories.FOOD_CRAFTING),
    TOAST("Toast", ConfigCategories.FOOD_CRAFTING),
    CHOCOLATE_TOAST("Chocolate Toast", ConfigCategories.FOOD_CRAFTING),

    LEAF_BLOWER("Leaf Blower", ConfigCategories.ITEMS_CRAFTING),
    LEAF_BLOWER_ADVANCED("Advanced Leaf Blower", ConfigCategories.ITEMS_CRAFTING),
    COIL("Coil", ConfigCategories.ITEMS_CRAFTING),
    ADV_COIL("Advanced Coil", ConfigCategories.ITEMS_CRAFTING),
    KNIFE("Knife", ConfigCategories.ITEMS_CRAFTING),
    STICK_CRAFTER("Crafting Table On A Stick", ConfigCategories.ITEMS_CRAFTING),
    MASHED_FOOD("Mashed Food", ConfigCategories.ITEMS_CRAFTING),

    RING_SPEED("Speed Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_HASTE("Haste Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_STRENGTH("Strength Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_JUMP_BOOST("Jump Boost Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_REGEN("Regen Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_RESISTANCE("Resistance Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_FIRE_RESISTANCE("Fire Resistance Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_WATER_BREATHING("Water Breathing Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_INVISIBILITY("Invisibility Ring", ConfigCategories.POTION_RING_CRAFTING),
    RING_NIGHT_VISION("Night Vision Ring", ConfigCategories.POTION_RING_CRAFTING),

    DOUGH("Dough", ConfigCategories.ITEMS_CRAFTING),
    PAPER_CONE("Paper Cone", ConfigCategories.ITEMS_CRAFTING),
    KNIFE_HANDLE("Knife Handle", ConfigCategories.ITEMS_CRAFTING),
    KNIFE_BLADE("Knife Blade", ConfigCategories.ITEMS_CRAFTING),

    TOOL_EMERALD("Emerald Tools", ConfigCategories.ITEMS_CRAFTING),
    TOOL_OBSIDIAN("Obsidian Tools", ConfigCategories.ITEMS_CRAFTING),
    TOOL_QUARTZ("Obsidian Tools", ConfigCategories.ITEMS_CRAFTING),
    TOOL_CRYSTALS("Crystal Tools", ConfigCategories.ITEMS_CRAFTING),
    RICE_BREAD("Rice Bread", ConfigCategories.FOOD_CRAFTING),
    RICE_DOUGH("Rice Dough", ConfigCategories.FOOD_CRAFTING),

    RICE_GADGETS("Rice Gadgets", ConfigCategories.ITEMS_CRAFTING),
    RESONANT_RICE("Resonant Rice", ConfigCategories.ITEMS_CRAFTING),

    CANOLA_PRESS("Canola Press", ConfigCategories.BLOCKS_CRAFTING),
    FERMENTING_BARREL("Fermenting Barrel", ConfigCategories.BLOCKS_CRAFTING),
    COAL_GENERATOR("Coal Generator", ConfigCategories.BLOCKS_CRAFTING),
    LEAF_GENERATOR("Leaf Generator", ConfigCategories.BLOCKS_CRAFTING),
    OIL_GENERATOR("Oil Generator", ConfigCategories.BLOCKS_CRAFTING),
    PHANTOMFACE("Phantomface", ConfigCategories.BLOCKS_CRAFTING),
    PHANTOM_CONNECTOR("Phantom Connector", ConfigCategories.ITEMS_CRAFTING),

    PHANTOM_ENERGYFACE("Phantom Energyface", ConfigCategories.BLOCKS_CRAFTING),
    PHANTOM_LIQUIFACE("Phantom Liquiface", ConfigCategories.BLOCKS_CRAFTING),
    PHANTOM_PLACER("Phantom Placer", ConfigCategories.BLOCKS_CRAFTING),
    PHANTOM_BREAKER("Phantom Breaker", ConfigCategories.BLOCKS_CRAFTING),
    LIQUID_PLACER("Liquid Placer", ConfigCategories.BLOCKS_CRAFTING),
    LIQUID_BREAKER("Liquid Collector", ConfigCategories.BLOCKS_CRAFTING),

    CUP("Cup", ConfigCategories.ITEMS_CRAFTING),
    PAXELS("Paxels", ConfigCategories.ITEMS_CRAFTING),


    ENDER_CASING("Ender Casing", ConfigCategories.BLOCKS_CRAFTING),
    PHANTOM_BOOSTER("Phantom Booster", ConfigCategories.BLOCKS_CRAFTING),
    COFFEE_MACHINE("Coffee Machine", ConfigCategories.BLOCKS_CRAFTING),
    LAVA_FACTORY("Lava Factory", ConfigCategories.BLOCKS_CRAFTING),

    DRILL("Drill", ConfigCategories.ITEMS_CRAFTING),
    DRILL_SPEED("Drill Speed Upgrades", ConfigCategories.ITEMS_CRAFTING),
    DRILL_FORTUNE("Drill Fortune Upgrades", ConfigCategories.ITEMS_CRAFTING),
    DRILL_SIZE("Drill Size Upgrades", ConfigCategories.ITEMS_CRAFTING),
    DRILL_PLACING("Drill Placing Upgrade", ConfigCategories.ITEMS_CRAFTING),
    DRILL_SILK_TOUCH("Drill Silk Touch Upgrade", ConfigCategories.ITEMS_CRAFTING),
    BATTERY("Battery", ConfigCategories.ITEMS_CRAFTING),
    DOUBLE_BATTERY("Double Battery", ConfigCategories.ITEMS_CRAFTING),
    TRIPLE_BATTERY("Triple Battery", ConfigCategories.ITEMS_CRAFTING),
    QUADRUPLE_BATTERY("Quadruple Battery", ConfigCategories.ITEMS_CRAFTING),
    QUINTUPLE_BATTERY("Quintuple Battery", ConfigCategories.ITEMS_CRAFTING),
    BAT_WINGS("Wings Of The Bats", ConfigCategories.ITEMS_CRAFTING),

    ENERGIZER("Energizer", ConfigCategories.BLOCKS_CRAFTING),
    ENERVATOR("Enervator", ConfigCategories.BLOCKS_CRAFTING),

    QUARTZ("Black Quartz in a Crafting Table", ConfigCategories.ITEMS_CRAFTING),
    LAMPS("Lamps", ConfigCategories.BLOCKS_CRAFTING),

    HORSE_ARMORS("Horse Armor -> Raw Materials (Crusher)", ConfigCategories.OTHER),

    RECONSTRUCTOR_MISC("Misc. Recipes like Soul Sand (Reconstructor)", ConfigCategories.OTHER),
    RECONSTRUCTOR_EXPLOSION_LENS("Lens of Detonation (Reconstructor)", ConfigCategories.OTHER),

    TELE_STAFF("Tele Staff", ConfigCategories.ITEMS_CRAFTING),
    CASING("Casing", ConfigCategories.BLOCKS_CRAFTING),

    MAGNET_RING("Magnet Ring", ConfigCategories.ITEMS_CRAFTING),
    WATER_RING("Water Ring", ConfigCategories.ITEMS_CRAFTING),
    GROWTH_RING("Growth Ring", ConfigCategories.ITEMS_CRAFTING),
    DIRECTIONAL_BREAKER("Long-Range Breaker", ConfigCategories.BLOCKS_CRAFTING),
    RANGED_COLLECTOR("Ranged Collector", ConfigCategories.BLOCKS_CRAFTING),
    LASER_RELAY("Laser Relay", ConfigCategories.BLOCKS_CRAFTING),
    LASER_WRENCH("Laser Wrench", ConfigCategories.ITEMS_CRAFTING),

    CHEST_TO_CRATE_UPGRADE("Chest To Crate Upgrade", ConfigCategories.ITEMS_CRAFTING),
    CRATE_KEEPER("Crate Keeper", ConfigCategories.ITEMS_CRAFTING),
    DRILL_CORE("Drill Core", ConfigCategories.ITEMS_CRAFTING),
    ATOMIC_RECONSTRUCTOR("Atomic Reconstructor", ConfigCategories.BLOCKS_CRAFTING),
    MINER("Miner", ConfigCategories.BLOCKS_CRAFTING),
    FIREWORK_BOX("Firework Box", ConfigCategories.BLOCKS_CRAFTING);

    public final String name;
    public final String category;
    public final boolean defaultValue;

    public boolean currentValue;

    ConfigCrafting(String name, ConfigCategories category){
        this(name, category, true);
    }

    ConfigCrafting(String name, ConfigCategories category, boolean defaultValue){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
    }

    public boolean isEnabled(){
        return this.currentValue;
    }
}
