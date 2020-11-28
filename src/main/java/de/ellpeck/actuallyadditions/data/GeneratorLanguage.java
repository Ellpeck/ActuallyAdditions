package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class GeneratorLanguage extends LanguageProvider {
    public GeneratorLanguage(DataGenerator gen) {
        super(gen, ActuallyAdditions.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Blocks (kill me)
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
        addBlock(ActuallyBlocks.TREASURE_CHEST, "Treasure Chest");
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
        addBlock(ActuallyBlocks.FISHING_NET, "Fishing Net");
        addBlock(ActuallyBlocks.SOLAR_PANEL, "Solar Panel");
        addBlock(ActuallyBlocks.HEAT_COLLECTOR, "Heat Collector");
        addBlock(ActuallyBlocks.ITEM_REPAIRER, "Item Repairer");
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
        addBlock(ActuallyBlocks.SMILEY_CLOUD, "Smiley Cloud");
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
        addBlock(ActuallyBlocks.IRON_CASING_SNOW, "?");
        addBlock(ActuallyBlocks.LAVA_FACTORY_CASE, "Casing");
        addBlock(ActuallyBlocks.WOOD_CASING, "Wood Casing");

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
}
