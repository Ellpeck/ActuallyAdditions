package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import de.ellpeck.actuallyadditions.common.items.ToolSet;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class GeneratorLanguage extends LanguageProvider {
    public GeneratorLanguage(DataGenerator gen) {
        super(gen, ActuallyAdditions.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Blocks
        addBlock(ActuallyBlocks.RICE, "Rice");
        addBlock(ActuallyBlocks.CANOLA, "Canola");
        addBlock(ActuallyBlocks.FLAX, "Flax");
        addBlock(ActuallyBlocks.COFFEE, "Coffee");
        addBlock(ActuallyBlocks.BATTERY_BOX, "Battery Box");
        addBlock(ActuallyBlocks.HOPPING_ITEM_INTERFACE, "Hopping Item Interface");
        addBlock(ActuallyBlocks.FARMER, "Farmer");
        addBlock(ActuallyBlocks.BIO_REACTOR, "Bio Reactor");
        addBlock(ActuallyBlocks.EMPOWERER, "Empowerer");
        addBlock(ActuallyBlocks.TINY_TORCH, "Tiny Torch");
        addBlock(ActuallyBlocks.SHOCK_SUPPRESSOR, "Shock");
        addBlock(ActuallyBlocks.DISPLAY_STAND, "Display Stand");
        addBlock(ActuallyBlocks.PLAYER_INTERFACE, "Player Interface");
        addBlock(ActuallyBlocks.ITEM_INTERFACE, "Item Interface");
        addBlock(ActuallyBlocks.FIREWORK_BOX, "Firework Box");
        addBlock(ActuallyBlocks.MINER, "Miner");
        addBlock(ActuallyBlocks.ATOMIC_RECONSTRUCTOR, "Atomic Reconstructor");
        addBlock(ActuallyBlocks.ENERGIZER, "Energizer");
        addBlock(ActuallyBlocks.ENERVATOR, "Enervator");
        addBlock(ActuallyBlocks.LAVA_FACTORY_CONTROLLER, "Lava Factory Controller");
        addBlock(ActuallyBlocks.CANOLA_PRESS, "Conola Press");
        addBlock(ActuallyBlocks.PHANTOMFACE, "Phantomface");
        addBlock(ActuallyBlocks.PHANTOM_PLACER, "Phantom Placer");
        addBlock(ActuallyBlocks.PHANTOM_LIQUIFACE, "Phantom Liquiface");
        addBlock(ActuallyBlocks.PHANTOM_ENERGYFACE, "Phantom Energyface");
        addBlock(ActuallyBlocks.PHANTOM_REDSTONEFACE, "Phantom Restoneface");
        addBlock(ActuallyBlocks.PHANTOM_BREAKER, "Phantom Breaker");
        addBlock(ActuallyBlocks.COAL_GENERATOR, "Coal Generator");
        addBlock(ActuallyBlocks.OIL_GENERATOR, "Oil Generator");
        addBlock(ActuallyBlocks.FERMENTING_BARREL, "Fermenting Barrel");
        addBlock(ActuallyBlocks.FEEDER, "Feeder");
        addBlock(ActuallyBlocks.CRUSHER, "Crusher");
        addBlock(ActuallyBlocks.CRUSHER_DOUBLE, "Double Crusher");
        addBlock(ActuallyBlocks.POWERED_FURNACE, "Powered Furnace");
        addBlock(ActuallyBlocks.ESD, "ESD");
        addBlock(ActuallyBlocks.ESD_ADVANCED, "Advanced ASD");
        addBlock(ActuallyBlocks.HEAT_COLLECTOR, "Heat Collector");
        addBlock(ActuallyBlocks.GREENHOUSE_GLASS, "Greenhouse Glass");
        addBlock(ActuallyBlocks.BREAKER, "Auto-Breaker");
        addBlock(ActuallyBlocks.PLACER, "Auto-Placer");
        addBlock(ActuallyBlocks.DROPPER, "Precision Dropper");
        addBlock(ActuallyBlocks.FLUID_PLACER, "Fluid Placer");
        addBlock(ActuallyBlocks.FLUID_COLLECTOR, "Fluid Collector");
        addBlock(ActuallyBlocks.COFFEE_MACHINE, "Coffee Machine");
        addBlock(ActuallyBlocks.PHANTOM_BOOSTER, "Phantom Booster");
        addBlock(ActuallyBlocks.CRYSTAL_ENORI, "Block of Enori Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_RESTONIA, "Block of Restonia Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_PALIS, "Block of Palis Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_DIAMATINE, "Block of Diamatine Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_VOID, "Block of Void Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMERADIC, "Block of Emeradic Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_ENORI, "Block of Empowered Enori Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_RESTONIA, "Block of Empowered Restonia Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_PALIS, "Block of Empowered Palis Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_DIAMATINE, "Block of Empowered Diamatine Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_VOID, "Block of Empowered Void Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC, "Block of Empowered Emeradic Crystals");
        addBlock(ActuallyBlocks.CRYSTAL_CLUSTER_RESTONIA, "Restonia Crystal Cluster");
        addBlock(ActuallyBlocks.CRYSTAL_CLUSTER_PALIS, "Palis Crystal Cluster");
        addBlock(ActuallyBlocks.CRYSTAL_CLUSTER_DIAMATINE, "Diamatine Crystal Cluster");
        addBlock(ActuallyBlocks.CRYSTAL_CLUSTER_VOID, "Void Crystal Cluster");
        addBlock(ActuallyBlocks.CRYSTAL_CLUSTER_EMERADIC, "Emeradic Crystal Cluster");
        addBlock(ActuallyBlocks.CRYSTAL_CLUSTER_ENORI, "Enori Crystal Cluster");
        addBlock(ActuallyBlocks.ENERGY_LASER_RELAY, "Energy Laser relay");
        addBlock(ActuallyBlocks.ENERGY_LASER_RELAY_ADVANCED, "Advanced Energy Laser Relay");
        addBlock(ActuallyBlocks.ENERGY_LASER_RELAY_EXTREME, "Extreme Energy Laser Relay");
        addBlock(ActuallyBlocks.FLUIDS_LASER_RELAY, "Fluid Laser Relay");
        addBlock(ActuallyBlocks.ITEM_LASER_RELAY, "Item Laser Relay");
        addBlock(ActuallyBlocks.ADVANCED_ITEM_LASER_RELAY, "Advanced Item Laser Relay");
        addBlock(ActuallyBlocks.RANGED_COLLECTOR, "Ranged Collector");
        addBlock(ActuallyBlocks.DIRECTIONAL_BREAKER, "Directional Breaker");
        addBlock(ActuallyBlocks.LEAF_GENERATOR, "Leaf Generator");
        addBlock(ActuallyBlocks.XP_SOLIDIFIER, "XP Solidifier");
        addBlock(ActuallyBlocks.GREEN_BLOCK, "Ethentic Green Wall");
        addBlock(ActuallyBlocks.WHITE_BLOCK, "Ethentic Quartz Wall");
        addBlock(ActuallyBlocks.GREEN_STAIRS, "Ethentic Green Stairs");
        addBlock(ActuallyBlocks.WHITE_STAIRS, "Ethentic Quartz Stairs");
        addBlock(ActuallyBlocks.GREEN_SLAB, "Ethentic Green Slab");
        addBlock(ActuallyBlocks.WHITE_SLAB, "Ethentic Quartz Slab");
        addBlock(ActuallyBlocks.GREEN_WALL, "Ethentic Green Wall");
        addBlock(ActuallyBlocks.WHITE_WALL, "Ethentic Quartz Wall");
        addBlock(ActuallyBlocks.BLACK_QUARTZ, "Block of Black Quartz");
        addBlock(ActuallyBlocks.BLACK_QUARTZ_CHISELED, "Chiseled Black Quartz");
        addBlock(ActuallyBlocks.BLACK_QUARTZ_PILLAR, "Black Quartz Pillar");
        addBlock(ActuallyBlocks.BLACK_QUARTZ_WALL, "Black Quartz Wall");
        addBlock(ActuallyBlocks.BLACK_CHISELED_QUARTZ_WALL, "Chiseled Black Quartz Wall");
        addBlock(ActuallyBlocks.BLACK_PILLAR_QUARTZ_WALL, "Black Quartz Wall Pillar");
        addBlock(ActuallyBlocks.BLACK_QUARTZ_STAIR, "Black Quartz Stairs");
        addBlock(ActuallyBlocks.BLACK_CHISELED_QUARTZ_STAIR, "Chiseled Black Quartz Stairs");
        addBlock(ActuallyBlocks.BLACK_PILLAR_QUARTZ_STAIR, "Black Quartz Pillar Stairs");
        addBlock(ActuallyBlocks.BLACK_QUARTZ_SLAB, "Black Quartz Slab");
        addBlock(ActuallyBlocks.BLACK_CHISELED_QUARTZ_SLAB, "Chiseled Black Quartz Slab");
        addBlock(ActuallyBlocks.BLACK_PILLAR_QUARTZ_SLAB, "Black Quartz Pillar Slab");
        addBlock(ActuallyBlocks.LAMP_WHITE, "White Lamp");
        addBlock(ActuallyBlocks.LAMP_ORANGE, "Orange Lamp");
        addBlock(ActuallyBlocks.LAMP_MAGENTA, "Magenta Lamp");
        addBlock(ActuallyBlocks.LAMP_LIGHT_BLUE, "Light Blue Lamp");
        addBlock(ActuallyBlocks.LAMP_YELLOW, "Yellow Lamp");
        addBlock(ActuallyBlocks.LAMP_LIME, "Lime Lamp");
        addBlock(ActuallyBlocks.LAMP_PINK, "Pink Lamp");
        addBlock(ActuallyBlocks.LAMP_GRAY, "Gray Lamp");
        addBlock(ActuallyBlocks.LAMP_LIGHT_GRAY, "Light Gray Lamp");
        addBlock(ActuallyBlocks.LAMP_CYAN, "Cyan Lamp");
        addBlock(ActuallyBlocks.LAMP_PURPLE, "Purple Lamp");
        addBlock(ActuallyBlocks.LAMP_BLUE, "Blue Lamp");
        addBlock(ActuallyBlocks.LAMP_BROWN, "Brown Lamp");
        addBlock(ActuallyBlocks.LAMP_GREEN, "Green Lamp");
        addBlock(ActuallyBlocks.LAMP_RED, "Red Lamp");
        addBlock(ActuallyBlocks.LAMP_BLACK, "Black Lamp");
        addBlock(ActuallyBlocks.LAMP_CONTROLLER, "Lamp Controller");
        addBlock(ActuallyBlocks.ENDERPEARL, "Block of Ender Pearls");
        addBlock(ActuallyBlocks.CHARCOAL, "Charcoal");
        addBlock(ActuallyBlocks.ORE_BLACK_QUARTZ, "Black Quartz Ore");
        addBlock(ActuallyBlocks.ENDER_CASING, "Ender Casing");
        addBlock(ActuallyBlocks.IRON_CASING, "Iron Casing");
        addBlock(ActuallyBlocks.LAVA_FACTORY_CASE, "Casing");
        addBlock(ActuallyBlocks.WOOD_CASING, "Wood Casing");

        // -- Items
        // Tools
        ActuallyItems.ALL_TOOL_SETS.forEach(this::addToolSet);

        // AIOT's
        addItem(ActuallyItems.WOODEN_AIOT, "Wooden AIOT");
        addItem(ActuallyItems.STONE_AIOT, "Stone AIOT");
        addItem(ActuallyItems.IRON_AIOT, "Iron AIOT");
        addItem(ActuallyItems.GOLD_AIOT, "Gold AIOT");
        addItem(ActuallyItems.DIAMOND_AIOT, "Diamond AIOT");
        addItem(ActuallyItems.NETHERITE_AIOT, "Netherite AIOT");
        addItem(ActuallyItems.QUARTZ_AIOT, "Black Quartz AIOT");
        addItem(ActuallyItems.ENORI_AIOT, "Enori AIOT");
        addItem(ActuallyItems.EMERADIC_AIOT, "Emeradic AIOT");
        addItem(ActuallyItems.VOID_AIOT, "Void AIOT");
        addItem(ActuallyItems.DIAMATINE_AIOT, "Diamatine AIOT");
        addItem(ActuallyItems.PALIS_AIOT, "Palis AIOT");
        addItem(ActuallyItems.RESTONIA_AIOT, "Restonia AIOT");
        addItem(ActuallyItems.BLACK_QUARTZ, "Black Quartz");

        // Crystals
        addItem(ActuallyItems.RESTONIA_CRYSTAL, "Restonia Crystal");
        addItem(ActuallyItems.PALIS_CRYSTAL, "Palis Crystal");
        addItem(ActuallyItems.DIAMATINE_CRYSTAL, "Diamatine Crystal");
        addItem(ActuallyItems.VOID_CRYSTAL, "Void Crystal");
        addItem(ActuallyItems.EMERADIC_CRYSTAL, "Emeradic Crystal");
        addItem(ActuallyItems.ENORI_CRYSTAL, "Enori Crystal");

        addItem(ActuallyItems.RESTONIA_EMPOWERED_CRYSTAL, "Empowered Restonia Crystal");
        addItem(ActuallyItems.PALIS_EMPOWERED_CRYSTAL, "Empowered Palis Crystal");
        addItem(ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL, "Empowered Diamatine Crystal");
        addItem(ActuallyItems.VOID_EMPOWERED_CRYSTAL, "Empowered Void Crystal");
        addItem(ActuallyItems.EMERADIC_EMPOWERED_CRYSTAL, "Empowered Emeradic Crystal");
        addItem(ActuallyItems.ENORI_EMPOWERED_CRYSTAL, "Empowered Enori Crystal");

        // Remaining Items
        addItem(ActuallyItems.RED_CRYSTAL_SHARD, "Red Crystal Shard");
        addItem(ActuallyItems.BLUE_CRYSTAL_SHARD, "Blue Crystal Shard");
        addItem(ActuallyItems.LIGHT_BLUE_CRYSTAL_SHARD, "Light Blue Crystal");
        addItem(ActuallyItems.BLACK_CRYSTAL_SHARD, "Black Crystal Shard");
        addItem(ActuallyItems.GREEN_CRYSTAL_SHARD, "Green Crystal Shard");
        addItem(ActuallyItems.WHITE_CRYSTAL_SHARD, "White Crystal Shard");
        addItem(ActuallyItems.ENGINEERS_GOGGLES, "Engineer's Goggles");
        addItem(ActuallyItems.ENGINEERS_GOGGLES_INFRARED, "Engineer's Infrared Goggles");
        addItem(ActuallyItems.LASER_RELAY_MODIFIER_RANGE, "Laser Relay Modifier: Range");
        addItem(ActuallyItems.LASER_RELAY_MODIFIER_INVISIBILITY, "Laser Relay Modifier: Invisibility");
        addItem(ActuallyItems.HANDHELD_FILLER, "Handheld Filler");
        addItem(ActuallyItems.TRAVELERS_SACK, "Traveler's Sack");
        addItem(ActuallyItems.VOID_SACK, "Void Sack");
        addItem(ActuallyItems.WORM, "Worm");
        addItem(ActuallyItems.PLAYER_PROBE, "Player Probe");
        addItem(ActuallyItems.ITEM_FILTER, "Item Filter");
        addItem(ActuallyItems.BOWL_OF_WATER, "Bowl of Water");
        addItem(ActuallyItems.PAPER_CONE, "Paper Cone");
        addItem(ActuallyItems.DOUGH, "Dough");
        addItem(ActuallyItems.RING, "Ring");
        addItem(ActuallyItems.BASIC_COIL, "Basic Coil");
        addItem(ActuallyItems.ADVANCED_COIL, "Advanced Coil");
        addItem(ActuallyItems.RICE_DOUGH, "Rice Dough");
        addItem(ActuallyItems.TINY_COAL, "Tiny Coal");
        addItem(ActuallyItems.TINY_CHARCOAL, "Tiny Charcoal");
        addItem(ActuallyItems.RICE_SLIMEBALL, "Rice Slimeball");
        addItem(ActuallyItems.CANOLA, "Canola");
        addItem(ActuallyItems.EMPTY_CUP, "Empty Cup");
        addItem(ActuallyItems.BATS_WING, "Bat's Wing");
        addItem(ActuallyItems.DRILL_CORE, "Drill Core");
        addItem(ActuallyItems.LENS, "Lens");
        addItem(ActuallyItems.ENDER_STAR, "Ender Star");
        addItem(ActuallyItems.CRYSTALLIZED_CANOLA_SEED, "Crystallized Canola Seed");
        addItem(ActuallyItems.EMPOWERED_CANOLA_SEED, "Empowered Canola Seed");
        addItem(ActuallyItems.LENS_OF_COLOR, "Lens of Color");
        addItem(ActuallyItems.LENS_OF_DETONATION, "Lens of Detonation");
        addItem(ActuallyItems.LENS_OF_CERTAIN_DEATH, "Lens of Certain Death");
        addItem(ActuallyItems.LENS_OF_THE_KILLER, "Lens of the Killer");
        addItem(ActuallyItems.LENS_OF_DISENCHANTING, "Lens of Disenchanting");
        addItem(ActuallyItems.LENS_OF_THE_MINER, "Lens of the Miner");
        addItem(ActuallyItems.LASER_WRENCH, "Laser Wrench");
        addItem(ActuallyItems.TELEPORT_STAFF, "Teleport Staff");
        addItem(ActuallyItems.WINGS_OF_THE_BATS, "Wings Of The Bats");
        addItem(ActuallyItems.SINGLE_BATTERY, "Single Battery");
        addItem(ActuallyItems.DOUBLE_BATTERY, "Double Battery");
        addItem(ActuallyItems.TRIPLE_BATTERY, "Triple Battery");
        addItem(ActuallyItems.QUADRUPLE_BATTERY, "Quadruple Battery");
        addItem(ActuallyItems.QUINTUPLE_BATTERY, "Quintuple Battery");
        addItem(ActuallyItems.DRILL_MAIN, "Drill");
        addItem(ActuallyItems.DRILL_BLACK, "Black Drill");
        addItem(ActuallyItems.DRILL_BLUE, "Blue Drill");
        addItem(ActuallyItems.DRILL_BROWN, "Brown Drill");
        addItem(ActuallyItems.DRILL_CYAN, "Cyan Drill");
        addItem(ActuallyItems.DRILL_GRAY, "Gray Drill");
        addItem(ActuallyItems.DRILL_GREEN, "Green Drill");
        addItem(ActuallyItems.DRILL_LIGHT_GRAY, "Light Gray Drill");
        addItem(ActuallyItems.DRILL_LIME, "Lime Drill");
        addItem(ActuallyItems.DRILL_MAGENTA, "Magenta Drill");
        addItem(ActuallyItems.DRILL_ORANGE, "Orange Drill");
        addItem(ActuallyItems.DRILL_PINK, "Pink Drill");
        addItem(ActuallyItems.DRILL_PURPLE, "Purple Drill");
        addItem(ActuallyItems.DRILL_RED, "Red Drill");
        addItem(ActuallyItems.DRILL_WHITE, "White Drill");
        addItem(ActuallyItems.DRILL_YELLOW, "Yellow Drill");
        addItem(ActuallyItems.DRILL_SPEED_AUGMENT_I, "Drill Speed Augment I");
        addItem(ActuallyItems.DRILL_SPEED_AUGMENT_II, "Drill Speed Augment II");
        addItem(ActuallyItems.DRILL_SPEED_AUGMENT_III, "Drill Speed Augment III");
        addItem(ActuallyItems.DRILL_SILK_TOUCH_AUGMENT, "Drill Silk Touch Augment");
        addItem(ActuallyItems.DRILL_FORTUNE_AUGMENT_I, "Drill Fortune Augment I");
        addItem(ActuallyItems.DRILL_FORTUNE_AUGMENT_II, "Drill Fortune Augment II (Gives Fortune III!)");
        addItem(ActuallyItems.DRILL_MINING_AUGMENT_I, "Drill Mining Augment I");
        addItem(ActuallyItems.DRILL_MINING_AUGMENT_II, "Drill Mining Augment II");
        addItem(ActuallyItems.DRILL_BLOCK_PLACING_AUGMENT, "Drill Block Placing Augment");
        addItem(ActuallyItems.FERTILIZER, "Fertilizer");
        addItem(ActuallyItems.CUP_WITH_COFFEE, "Cup with Coffee");
        addItem(ActuallyItems.PHANTOM_CONNECTOR, "Phantom Connector");
        addItem(ActuallyItems.RESONANT_RICE, "Resonant Rice");
        addItem(ActuallyItems.FOOD_CHEESE, "Cheese");
        addItem(ActuallyItems.FOOD_PUMPKIN_STEW, "Pumpkin Stew");
        addItem(ActuallyItems.FOOD_CARROT_JUICE, "Carrot Juice");
        addItem(ActuallyItems.FOOD_FISH_N_CHIPS, "Fish 'N' Chips");
        addItem(ActuallyItems.FOOD_FRENCH_FRIES, "French Fries");
        addItem(ActuallyItems.FOOD_FRENCH_FRY, "French Fry");
        addItem(ActuallyItems.FOOD_SPAGHETTI, "Spaghetti");
        addItem(ActuallyItems.FOOD_NOODLE, "Noodle");
        addItem(ActuallyItems.FOOD_CHOCOLATE_CAKE, "Chocolate Cake");
        addItem(ActuallyItems.FOOD_CHOCOLATE, "Chocolate");
        addItem(ActuallyItems.FOOD_TOAST, "Toast");
        addItem(ActuallyItems.FOOD_SUBMARINE_SANDWICH, "Submarine Sandwich");
        addItem(ActuallyItems.FOOD_BIG_COOKIE, "Big Cookie");
        addItem(ActuallyItems.FOOD_HAMBURGER, "Hamburger");
        addItem(ActuallyItems.FOOD_PIZZA, "Pizza");
        addItem(ActuallyItems.FOOD_BAGUETTE, "Baguette");
        addItem(ActuallyItems.FOOD_RICE, "Rice");
        addItem(ActuallyItems.FOOD_RICE_BREAD, "Rice Bread");
        addItem(ActuallyItems.FOOD_DOUGHNUT, "Doughnut");
        addItem(ActuallyItems.FOOD_TOAST_O_CHOCOLATE, "Toast o' Chocolate");
        addItem(ActuallyItems.FOOD_BACON, "Bacon");
        addItem(ActuallyItems.CU_BA_RA_JAM, "CuBaRa-Jam");
        addItem(ActuallyItems.GRA_KI_BA_JAM, "GraKiBa-Jam");
        addItem(ActuallyItems.PL_AP_LE_JAM, "PlApLe-Jam");
        addItem(ActuallyItems.CH_AP_CI_JAM, "ChApCi-Jam");
        addItem(ActuallyItems.HO_ME_KI_JAM, "HoMeKi-Jam");
        addItem(ActuallyItems.PI_CO_JAM, "PiCo-Jam");
        addItem(ActuallyItems.HO_ME_CO_JAM, "HoMeCo-Jam");
        addItem(ActuallyItems.KNIFE, "Knife");
        addItem(ActuallyItems.CRAFTING_TABLE_ON_A_STICK, "Crafting Table On A Stick");
        addItem(ActuallyItems.CRUSHED_IRON, "Crushed Iron");
        addItem(ActuallyItems.CRUSHED_GOLD, "Crushed Gold");
        addItem(ActuallyItems.CRUSHED_DIAMOND, "Crushed Diamond");
        addItem(ActuallyItems.CRUSHED_EMERALD, "Crushed Emerald");
        addItem(ActuallyItems.CRUSHED_LAPIS, "Crushed Lapis");
        addItem(ActuallyItems.CRUSHED_QUARTZ, "Crushed Quartz");
        addItem(ActuallyItems.CRUSHED_COAL, "Crushed Coal");
        addItem(ActuallyItems.CRUSHED_BLACK_QUARTZ, "Crushed Black Quartz");
        addItem(ActuallyItems.SOLIDIFIED_EXPERIENCE, "Solidified Experience");
        addItem(ActuallyItems.LEAF_BLOWER, "Leaf Blower");
        addItem(ActuallyItems.ADVANCED_LEAF_BLOWER, "Advanced Leaf Blower");
        addItem(ActuallyItems.RING_OF_GROWTH, "Ring of Growth");
        addItem(ActuallyItems.RING_OF_MAGNETIZING, "Ring of Magnetizing");
        addItem(ActuallyItems.RING_OF_SPEED, "Ring of Speed");
        addItem(ActuallyItems.RING_OF_HASTE, "Ring of Haste");
        addItem(ActuallyItems.RING_OF_STRENGTH, "Ring of Strength");
        addItem(ActuallyItems.RING_OF_JUMP_BOOST, "Ring of Jump Boost");
        addItem(ActuallyItems.RING_OF_REGENERATION, "Ring of Regeneration");
        addItem(ActuallyItems.RING_OF_RESISTANCE, "Ring of Resistance");
        addItem(ActuallyItems.RING_OF_FIRE_RESISTANCE, "Ring of Fire Resistance");
        addItem(ActuallyItems.RING_OF_WATER_BREATHING, "Ring of Water Breathing");
        addItem(ActuallyItems.RING_OF_INVISIBILITY, "Ring of Invisibility");
        addItem(ActuallyItems.RING_OF_NIGHT_VISION, "Ring of Night Vision");
        addItem(ActuallyItems.ADVANCED_RING_OF_SPEED, "Advanced Ring of Speed");
        addItem(ActuallyItems.ADVANCED_RING_OF_HASTE, "Advanced Ring of Haste");
        addItem(ActuallyItems.ADVANCED_RING_OF_STRENGTH, "Advanced Ring of Strength");
        addItem(ActuallyItems.ADVANCED_RING_OF_JUMP_BOOST, "Advanced Ring of Jump Boost");
        addItem(ActuallyItems.ADVANCED_RING_OF_REGENERATION, "Advanced Ring of Regeneration");
        addItem(ActuallyItems.ADVANCED_RING_OF_RESISTANCE, "Advanced Ring of Resistance");
        addItem(ActuallyItems.ADVANCED_RING_OF_FIRE_RESISTANCE, "Advanced Ring of Fire Resistance");
        addItem(ActuallyItems.ADVANCED_RING_OF_WATER_BREATHING, "Advanced Ring of Water Breathing");
        addItem(ActuallyItems.ADVANCED_RING_OF_INVISIBILITY, "Advanced Ring of Invisibility");
        addItem(ActuallyItems.ADVANCED_RING_OF_NIGHT_VISION, "Advanced Ring of Night Vision");
        addItem(ActuallyItems.FUR_BALL, "Fur Ball");
        addItem(ActuallyItems.COFFEE_BEANS, "Coffee Beans");
        addItem(ActuallyItems.RICE_SEEDS, "Rice Seeds");
        addItem(ActuallyItems.CANOLA_SEEDS, "Canola Seeds");
        addItem(ActuallyItems.FLAX_SEEDS, "Flax Seeds");
        addItem(ActuallyItems.COFFEE_SEEDS, "Coffee Seeds");

        // Booklet
        addItem(ActuallyItems.BOOKLET, "Actually Additions Manual");
        addPrefixed("tooltip.booklet.manual.one", "Or \"Booklet\", if you will");
        addPrefixed("tooltip.booklet.manual.two", "This book guides you through all of the feature Actually Additions has to over.");
        addPrefixed("tooltip.booklet.manual.three", "Use while holding to open.");

        // Storage
        addPrefixed("storage.crystal-flux", "%s/%s Crystal Flux");

        add("itemGroup.actuallyadditions", "Actually Additions");

        // Mics
        add("misc.message.so_cute", "So cute!");
    }

    /**
     * Very simply, prefixes all the keys with the mod_id.{key} instead of
     * having to input it manually
     */
    private void addPrefixed(String key, String text) {
        add(String.format("%s.%s", ActuallyAdditions.MOD_ID, key), text);
    }

    private void addToolSet(ToolSet set) {
        set.items.forEach(e -> addItem(e::getItem, String.format("%s %s", set.name.substring(0, 1).toUpperCase() + set.name.substring(1), e.getPretty())));
    }
}
