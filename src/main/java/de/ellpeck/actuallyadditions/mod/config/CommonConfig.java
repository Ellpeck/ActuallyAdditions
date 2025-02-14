package de.ellpeck.actuallyadditions.mod.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class CommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec COMMON_CONFIG;

    static {
        Other.build();
        Machines.build();
        Worldgen.build();
        ItemSettings.build();
        COMMON_CONFIG = BUILDER.build();
    }

    public static class ItemSettings {
        public static ModConfigSpec.ConfigValue<List<? extends String>> DRILL_EXTRA_MINING_WHITELIST;

        public static void build() {
            BUILDER.comment("Item settings").push("itemsSettings");

            // TODO: Tags!
            DRILL_EXTRA_MINING_WHITELIST = BUILDER
                    .comment("By default, the Drill can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.")
                    .defineListAllowEmpty("drillExtraWhitelist", List.of("TConstruct:GravelOre"), String::new, (o) -> o instanceof String);

            BUILDER.pop();
        }
    }

    public static class Machines {
        public static ModConfigSpec.IntValue FARMER_AREA;
        public static ModConfigSpec.IntValue RECONSTRUCTOR_POWER;
        public static ModConfigSpec.IntValue OIL_GENERATOR_TRANSFER;
        public static ModConfigSpec.IntValue MINER_LENS_ENERGY;
        public static ModConfigSpec.BooleanValue LASER_RELAY_LOSS;
        public static ModConfigSpec.IntValue LEAF_GENERATOR_COOLDOWN;
        public static ModConfigSpec.IntValue LEAF_GENERATOR_CF_PER_LEAF;
        public static ModConfigSpec.IntValue LEAF_GENERATOR_AREA;
        public static ModConfigSpec.IntValue FLUID_LASER_TRANSFER_RATE;
        public static ModConfigSpec.ConfigValue<List<? extends String>> MINER_EXTRA_WHITELIST;
        public static ModConfigSpec.ConfigValue<List<? extends String>> MINER_BLACKLIST;

        public static void build() {
            BUILDER.comment("Machine Settings").push("machineSettings");

            FARMER_AREA = BUILDER.comment("The size of the farmer's farming area.  Default is 9x9, must be an odd number.").defineInRange("farmerArea", 9, 1, Integer.MAX_VALUE);
            RECONSTRUCTOR_POWER = BUILDER.comment("The amount of power the atomic reconstructor can store.").defineInRange("reconstructorPower", 300000, 300000, Integer.MAX_VALUE);
            OIL_GENERATOR_TRANSFER = BUILDER.comment("The amount of power the oil generator can transfer per tick.").defineInRange("oilGeneratorTransfer", 500, 100, Integer.MAX_VALUE);
            MINER_LENS_ENERGY = BUILDER.comment("The energy use of the Atomic Reconstructor's Mining Lens.").defineInRange("minerLensEnergy", 60000, 1, Integer.MAX_VALUE);
            LASER_RELAY_LOSS = BUILDER.comment("If Energy Laser Relays should have energy loss.").define("laserRelayLoss", true);
            LEAF_GENERATOR_COOLDOWN = BUILDER.comment("The cooldown between two generation cycles of the Leaf Generator, in ticks").defineInRange("leafGeneratorCooldown", 5, 1, Integer.MAX_VALUE);
            LEAF_GENERATOR_CF_PER_LEAF = BUILDER.comment("The Leaf Generator's Energy Production in CF/Leaf").defineInRange("leafGeneratorCPPerLeaf", 300, 1, Integer.MAX_VALUE);
            LEAF_GENERATOR_AREA = BUILDER.comment("The size of the Leaf Generator's harvesting area.  Default is 7x7x7, must be an odd number.").defineInRange("leafGeneratorArea", 7, 1, Integer.MAX_VALUE);
            FLUID_LASER_TRANSFER_RATE = BUILDER.comment("The amount of fluid the Fluid Laser Relay can transfer per tick.").defineInRange("fluidLaserTransferRate", 10000, 1, Integer.MAX_VALUE);
            MINER_EXTRA_WHITELIST = BUILDER
                    .comment("By default, the Vertical Digger mines everything that is in the 'forge:ores' block/item tags. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option only applies if the miner is in Ores Only Mode.")
                    .defineListAllowEmpty("verticalDiggerExtraWhitelist", new ArrayList<>(), String::new, (o) -> o instanceof String);
            MINER_BLACKLIST = BUILDER
                    .comment("By default, the Vertical Digger mines everything that is in the 'forge:ores' block/item tags. If there is one that it can mine, but shouldn't be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command. This Config Option will apply in both modes.")
                    .defineListAllowEmpty("verticalDiggerBlacklist", new ArrayList<>(), String::new, (o) -> o instanceof String);

            BUILDER.pop();
        }
    }

    public static class Worldgen {
        public static ModConfigSpec.BooleanValue GENERATE_QUARTZ;
        public static ModConfigSpec.BooleanValue GENERATE_CANOLA;
        public static ModConfigSpec.BooleanValue GENERATE_FLAX;
        public static ModConfigSpec.BooleanValue GENERATE_COFFEE;

        public static void build() {
            BUILDER.comment("Worldgen Settings").push("worldgenSettings");

            GENERATE_QUARTZ = BUILDER.comment("Should Black Quartz generate in the world?").define("generateQuartz", true);
            GENERATE_CANOLA = BUILDER.comment("Should Canola generate in the World?").define("generateCanola", true);
            GENERATE_FLAX = BUILDER.comment("Should Flax generate in the world?").define("generateFlax", true);
            GENERATE_COFFEE = BUILDER.comment("Should Coffee generate in the world?").define("generateCoffee", true);

            BUILDER.pop();
        }
    }

    public static class Other {
        public static ModConfigSpec.BooleanValue SOLID_XP_ALWAYS_ORBS;
        public static ModConfigSpec.BooleanValue DO_UPDATE_CHECK;
        public static ModConfigSpec.BooleanValue UPDATE_CHECK_VERSION_SPECIFIC;
        public static ModConfigSpec.BooleanValue DO_CAT_DROPS;
        public static ModConfigSpec.BooleanValue DO_BAT_DROPS;
        public static ModConfigSpec.BooleanValue WORMS;
        public static ModConfigSpec.IntValue WORMS_DIE_TIME;
        public static ModConfigSpec.BooleanValue CTRL_EXTRA_INFO;
        public static ModConfigSpec.BooleanValue CTRL_INFO_FOR_EXTRA_INFO;
        public static ModConfigSpec.BooleanValue GIVE_BOOKLET_ON_FIRST_CRAFT;
        public static ModConfigSpec.BooleanValue DUNGEON_LOOT;
        public static ModConfigSpec.BooleanValue WATER_BOWL;
        public static ModConfigSpec.BooleanValue WATER_BOWL_LOSS;
        public static ModConfigSpec.BooleanValue TINY_COAL_STUFF;
        public static ModConfigSpec.BooleanValue SUPER_DUPER_HARD_MODE;
        public static ModConfigSpec.BooleanValue MOST_BLAND_PERSON_EVER;
        public static ModConfigSpec.ConfigValue<String> REDSTONECONFIGURATOR;
        public static ModConfigSpec.ConfigValue<String> RELAYCONFIGURATOR;
        public static ModConfigSpec.ConfigValue<String> FARMERCONFIG;
        public static Item redstoneConfigureItem = Items.AIR;
        public static Item relayConfigureItem = Items.AIR;
        public static Item farmerConfigureItem = Items.AIR;


        public static void build() {

            BUILDER.comment("Everything else").push("other");

            SOLID_XP_ALWAYS_ORBS = BUILDER.comment("If true, Solidified Experience will always spawn orbs, even for regular players.")
                .define("solidXPOrbs", false);

            DO_UPDATE_CHECK = BUILDER.comment("If true, Actually Additions Checks for updates on World Load.")
                .define("doUpdateCheck", true);

            UPDATE_CHECK_VERSION_SPECIFIC = BUILDER.comment("If true, Actually Additions' Update Checker searches for updates for the Minecraft Version you currently play on.")
                .define("versionSpecificUpdateChecker", true);

            DO_CAT_DROPS = BUILDER.comment("If true, Cats drop Hairy Balls Occasionally.")
                .define("doCatDrops", true);

            DO_BAT_DROPS = BUILDER.comment("Should Bat wings drop from Bats?")
                .define("doBatDrops", true);

            WORMS = BUILDER.comment("If true, worms will drop from tilling the soil.")
                .define("tillingWorms", true);

            WORMS_DIE_TIME = BUILDER.comment("The amount of ticks it takes for a worm to die. When at 0 ticks, it will not die.")
                .defineInRange("wormDeathTime", 0, 0, 10000000);

            CTRL_EXTRA_INFO = BUILDER.comment("Show Advanced Item Info when holding Control on every Item.")
                .define("advancedInfo", true);

            CTRL_INFO_FOR_EXTRA_INFO = BUILDER.comment("Show the 'Press Control for more Info'-Text on Item Tooltips")
                .define("advancedInfoTooltips", true);

            GIVE_BOOKLET_ON_FIRST_CRAFT = BUILDER.comment("If true, the booklet should be given to the player when he first crafts something from the Mod")
                .define("giveBookletOnFirstCraft", true);

            DUNGEON_LOOT = BUILDER.comment("Should Actually Additions Loot generate in dungeons?")
                .define("villageAndDungeonLoot", true);

            WATER_BOWL = BUILDER.comment("Should right-clicking a bowl on water blocks create a water bowl?")
                .define("waterBowl", true);

            WATER_BOWL_LOSS = BUILDER.comment("Should the water bowl spill if you don't sneak while using it?")
                .define("waterBowlSpilling", true);

            TINY_COAL_STUFF = BUILDER.comment("Should Tiny Coal and Tiny Charcoal be craftable").define("tinyCoal", true); //TODO conditionalRecipe

            SUPER_DUPER_HARD_MODE = BUILDER.comment("Turn this on to make recipes for items from the mod really hard. (This is a joke feature poking fun at the whole FTB Infinity Expert Mode style of playing. You shouldn't really turn this on as it makes the mod completely unplayable.)")
                .define("superDuperHardRecipes", false); //TODO what did this do?

            MOST_BLAND_PERSON_EVER = BUILDER.comment("If you want to be really boring and lame, you can turn on this setting to disable colored names on Actually Additions items. Because why would you want things to look pretty anyways, right?")
                .define("noColoredItemNames", false); //TODO is this still needed?

            REDSTONECONFIGURATOR = BUILDER.comment("define the item used to configure Redstone Mode").define("redstoneConfigurator", "minecraft:redstone_torch");
            RELAYCONFIGURATOR = BUILDER.comment("define the item used to configure Direction in laser relays").define("relayConfigurator", "minecraft:compass");
            FARMERCONFIG = BUILDER.comment("define the item used to configure the area in a farmer").define("farmerConfigurator", "minecraft:compass");

            BUILDER.pop();
        }
    }
}
