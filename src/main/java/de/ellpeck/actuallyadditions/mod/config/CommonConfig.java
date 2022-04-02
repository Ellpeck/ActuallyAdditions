package de.ellpeck.actuallyadditions.mod.config;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class CommonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    static {
        Other.build();
        Machines.build();
        ItemSettings.build();
        COMMON_CONFIG = BUILDER.build();
    }

    public static class ItemSettings {
        public static ForgeConfigSpec.ConfigValue<List<String>> DRILL_EXTRA_MINING_WHITELIST;

        public static void build() {
            BUILDER.comment("Item settings").push("itemsSettings");

            DRILL_EXTRA_MINING_WHITELIST = BUILDER
                    .comment("By default, the Drill can mine certain blocks. If there is one that it can't mine, but should be able to, put its REGISTRY NAME here. These are the actual registered Item Names, the ones you use, for example, when using the /give Command.")
                    .define("Drill Extra Whitelist", new ArrayList<String>() {{
                        add("TConstruct:GravelOre");
                    }});

            BUILDER.pop();
        }
    }

    public static class Machines {
        public static ForgeConfigSpec.IntValue FARMER_AREA;
        public static ForgeConfigSpec.IntValue RECONSTRUCTOR_POWER;
        public static ForgeConfigSpec.IntValue OIL_GENERATOR_TRANSFER;

        public static void build() {
            BUILDER.comment("Machine Settings").push("machineSettings");

            FARMER_AREA = BUILDER.comment("The size of the farmer's farming area.  Default is 9x9, must be an odd number.").defineInRange("farmerArea", 9, 1, Integer.MAX_VALUE);
            RECONSTRUCTOR_POWER = BUILDER.comment("The amount of power the atomic reconstructor can store.").defineInRange("reconstructorPower", 300000, 300000, Integer.MAX_VALUE);
            OIL_GENERATOR_TRANSFER = BUILDER.comment("The amount of power the oil generator can transfer per tick.").defineInRange("oilGeneratorTransfer", 500, 100, Integer.MAX_VALUE);

            BUILDER.pop();
        }
    }

    public static class Other {
        public static ForgeConfigSpec.BooleanValue SOLID_XP_ALWAYS_ORBS;
        public static ForgeConfigSpec.BooleanValue DO_UPDATE_CHECK;
        public static ForgeConfigSpec.BooleanValue UPDATE_CHECK_VERSION_SPECIFIC;
        public static ForgeConfigSpec.BooleanValue DO_CAT_DROPS;
        public static ForgeConfigSpec.IntValue FUR_CHANCE;
        public static ForgeConfigSpec.BooleanValue WORMS;
        public static ForgeConfigSpec.IntValue WORMS_DIE_TIME;
        public static ForgeConfigSpec.BooleanValue CTRL_EXTRA_INFO;
        public static ForgeConfigSpec.BooleanValue CTRL_INFO_FOR_EXTRA_INFO;
        public static ForgeConfigSpec.BooleanValue GIVE_BOOKLET_ON_FIRST_CRAFT;
        public static ForgeConfigSpec.BooleanValue DUNGEON_LOOT;
        public static ForgeConfigSpec.BooleanValue WATER_BOWL;
        public static ForgeConfigSpec.BooleanValue WATER_BOWL_LOSS;
        public static ForgeConfigSpec.BooleanValue TINY_COAL_STUFF;
        public static ForgeConfigSpec.BooleanValue SUPER_DUPER_HARD_MODE;
        public static ForgeConfigSpec.BooleanValue MOST_BLAND_PERSON_EVER;
        public static ForgeConfigSpec.IntValue ELEVEN;
        public static ForgeConfigSpec.ConfigValue<String> REDSTONECONFIGURATOR;
        public static ForgeConfigSpec.ConfigValue<String> RELAYCONFIGURATOR;
        public static Item redstoneConfigureItem = Items.AIR;
        public static Item relayConfigureItem = Items.AIR;


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

            FUR_CHANCE = BUILDER.comment("The 1/n drop chance, per tick, for a fur ball to be dropped.")
                .defineInRange("furDropChance", 5000, 1, Integer.MAX_VALUE);

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

            ELEVEN = BUILDER.comment("11?").defineInRange("whatIs11", 11, 0, 12);

            REDSTONECONFIGURATOR = BUILDER.comment("define the item used to configure Redstone Mode").define("redstoneConfigurator", Items.REDSTONE_TORCH.getRegistryName().toString(), obj -> obj instanceof String);
            RELAYCONFIGURATOR = BUILDER.comment("define the item used to configure Direction in laser relays").define("relayConfigurator", Items.COMPASS.getRegistryName().toString(), obj -> obj instanceof String);

            BUILDER.pop();
        }
    }
}
