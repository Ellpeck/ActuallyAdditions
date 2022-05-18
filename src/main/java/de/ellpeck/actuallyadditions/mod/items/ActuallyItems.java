/*
 * This file ("InitItems.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.AABlockItem;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.base.ItemArmorAA;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.base.ItemHoeAA;
import de.ellpeck.actuallyadditions.mod.items.base.ItemSwordAA;
import de.ellpeck.actuallyadditions.mod.items.lens.ItemLens;
import de.ellpeck.actuallyadditions.mod.material.ArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.ToolMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Supplier;

public final class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MODID);

    // MISC ITEMS
    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", ItemBase::new);
    public static final RegistryObject<Item> COFFEE_CUP = ITEMS.register("coffee_cup", ItemBase::new);
    public static final RegistryObject<Item> PAPER_CONE = ITEMS.register("paper_cone", ItemBase::new);
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough", ItemBase::new);
    public static final RegistryObject<Item> RING = ITEMS.register("ring", ItemBase::new);
    public static final RegistryObject<Item> BASIC_COIL = ITEMS.register("basic_coil", ItemBase::new);
    public static final RegistryObject<Item> ADVANCED_COIL = ITEMS.register("advanced_coil", ItemBase::new);
    public static final RegistryObject<Item> RICE = ITEMS.register("rice", ItemBase::new);
    public static final RegistryObject<Item> RICE_DOUGH = ITEMS.register("rice_dough", ItemBase::new);
    public static final RegistryObject<Item> TINY_COAL = ITEMS.register("tiny_coal", () -> new ItemBase() {
        @Override
        public int getBurnTime(ItemStack stack) {
            return 200;
        }
    });
    public static final RegistryObject<Item> TINY_CHARCOAL = ITEMS.register("tiny_charcoal", () -> new ItemBase() {
        @Override
        public int getBurnTime(ItemStack stack) {
            return 200;
        }
    });
    public static final RegistryObject<Item> RICE_SLIMEBALL = ITEMS.register("rice_slimeball", ItemBase::new);
    public static final RegistryObject<Item> EMPTY_CUP = ITEMS.register("empty_cup", ItemBase::new);
    public static final RegistryObject<Item> BATS_WING = ITEMS.register("bats_wing", ItemBase::new);
    public static final RegistryObject<Item> DRILL_CORE = ITEMS.register("drill_core", ItemBase::new);
    public static final RegistryObject<Item> LENS = ITEMS.register("lens", ItemBase::new);
    public static final RegistryObject<Item> ENDER_STAR = ITEMS.register("ender_star", ItemBase::new);
    public static final RegistryObject<Item> CRYSTALLIZED_CANOLA_SEED = ITEMS.register("crystallized_canola_seed", () -> new CanolaSeed(false));
    public static final RegistryObject<Item> EMPOWERED_CANOLA_SEED = ITEMS.register("empowered_canola_seed", () -> new CanolaSeed(true));

    // SHARDS
    public static final RegistryObject<Item> RESTONIA_CRYSTAL_SHARD = ITEMS.register("restonia_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> PALIS_CRYSTAL_SHARD = ITEMS.register("palis_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL_SHARD = ITEMS.register("diamatine_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> VOID_CRYSTAL_SHARD = ITEMS.register("void_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> EMERADIC_CRYSTAL_SHARD = ITEMS.register("emeradic_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> ENORI_CRYSTAL_SHARD = ITEMS.register("enori_crystal_shard", ItemBase::new);

    // CRYSTALS
    public static final RegistryObject<Item> RESTONIA_CRYSTAL = ITEMS.register("restonia_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> PALIS_CRYSTAL = ITEMS.register("palis_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL = ITEMS.register("diamatine_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> EMERADIC_CRYSTAL = ITEMS.register("emeradic_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> ENORI_CRYSTAL = ITEMS.register("enori_crystal", ItemCrystal::new);

    public static final RegistryObject<Item> EMPOWERED_RESTONIA_CRYSTAL = ITEMS.register("empowered_restonia_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_PALIS_CRYSTAL = ITEMS.register("empowered_palis_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_DIAMATINE_CRYSTAL = ITEMS.register("empowered_diamatine_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_VOID_CRYSTAL = ITEMS.register("empowered_void_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_EMERADIC_CRYSTAL = ITEMS.register("empowered_emeradic_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_ENORI_CRYSTAL = ITEMS.register("empowered_enori_crystal", () -> new ItemCrystal(true));

    // BLACK QUARTZ
    public static final RegistryObject<Item> BLACK_QUARTZ = ITEMS.register("black_quartz", ItemBase::new);

    public static final RegistryObject<Item> ENGINEERS_GOGGLES_ADVANCED = ITEMS.register("engineers_goggles_advanced", () -> new ItemEngineerGoggles(true));
    public static final RegistryObject<Item> ENGINEERS_GOGGLES = ITEMS.register("engineers_goggles", () -> new ItemEngineerGoggles(false));
    public static final RegistryObject<Item> LASER_UPGRADE_RANGE = ITEMS.register("laser_upgrade_range", ItemBase::new);
    public static final RegistryObject<Item> LASER_UPGRADE_INVISIBILITY = ITEMS.register("laser_upgrade_invisibility", ItemBase::new);
    public static final Supplier<Item> HANDHELD_FILLER = ITEMS.register("handheld_filler", ItemFillingWand::new);
    public static final RegistryObject<Item> TRAVELERS_SACK = ITEMS.register("travelers_sack", () -> new ItemBag(false));
    public static final RegistryObject<Item> VOID_SACK = ITEMS.register("void_sack", () -> new ItemBag(true));
    public static final RegistryObject<Item> WORM = ITEMS.register("worm", ItemWorm::new);
    public static final RegistryObject<Item> PLAYER_PROBE = ITEMS.register("player_probe", ItemPlayerProbe::new);
    public static final RegistryObject<Item> FILTER = ITEMS.register("filter", ItemFilter::new);
    public static final RegistryObject<Item> WATER_BOWL = ITEMS.register("water_bowl", ItemWaterBowl::new);
    public static final RegistryObject<Item> CRATE_KEEPER = ITEMS.register("crate_keeper", () -> new ItemGeneric(defaultProps().stacksTo(1)));
    public static final RegistryObject<Item> LENS_OF_COLOR = ITEMS.register("lens_of_color", () -> new ItemLens(ActuallyAdditionsAPI.lensColor));
    public static final RegistryObject<Item> LENS_OF_DETONATION = ITEMS.register("lens_of_detonation", () -> new ItemLens(ActuallyAdditionsAPI.lensDetonation));
    public static final RegistryObject<Item> LENS_OF_CERTAIN_DEATH = ITEMS.register("lens_of_certain_death", () -> new ItemLens(ActuallyAdditionsAPI.lensDeath));
    public static final RegistryObject<Item> LENS_OF_THE_KILLER = ITEMS.register("lens_of_the_killer", () -> new ItemLens(ActuallyAdditionsAPI.lensEvenMoarDeath));
    public static final RegistryObject<Item> LENS_OF_DISENCHANTING = ITEMS.register("lens_of_disenchanting", () -> new ItemLens(ActuallyAdditionsAPI.lensDisenchanting));
    public static final RegistryObject<Item> LENS_OF_THE_MINER = ITEMS.register("lens_of_the_miner", () -> new ItemLens(ActuallyAdditionsAPI.lensMining));
    public static final RegistryObject<Item> LASER_WRENCH = ITEMS.register("laser_wrench", ItemLaserWrench::new);
    //    public static final RegistryObject<Item> itemChestToCrateUpgrade = ITEMS.register("", new ItemChestToCrateUpgrade("chest_to_crate_upgrade", TileEntityChest.class, InitBlocks.blockGiantChest.getDefaultState()));
    //    public static final RegistryObject<Item> itemSmallToMediumCrateUpgrade = ITEMS.register("", new ItemChestToCrateUpgrade("small_to_medium_crate_upgrade", TileEntityGiantChest.class, InitBlocks.blockGiantChestMedium.getDefaultState()));
    //    public static final RegistryObject<Item> itemMediumToLargeCrateUpgrade = ITEMS.register("", new ItemChestToCrateUpgrade("medium_to_large_crate_upgrade", TileEntityGiantChestMedium.class, InitBlocks.blockGiantChestLarge.getDefaultState()));
    public static final RegistryObject<Item> ITEM_BOOKLET = ITEMS.register("booklet", ItemBooklet::new);
    public static final RegistryObject<Item> RING_OF_GROWTH = ITEMS.register("ring_of_growth", ItemGrowthRing::new);
    public static final RegistryObject<Item> RING_OF_MAGNETIZING = ITEMS.register("ring_of_magnetizing", ItemMagnetRing::new);
    public static final RegistryObject<Item> TELEPORT_STAFF = ITEMS.register("teleport_staff", ItemTeleStaff::new);
    public static final RegistryObject<Item> WINGS_OF_THE_BATS = ITEMS.register("wings_of_the_bats", ItemWingsOfTheBats::new);

    public static final RegistryObject<Item> DRILL_MAIN = ITEMS.register("drill_light_blue", DrillItem::new);
    public static final RegistryObject<Item> DRILL_BLACK = ITEMS.register("drill_black", DrillItem::new);
    public static final RegistryObject<Item> DRILL_BLUE = ITEMS.register("drill_blue", DrillItem::new);
    public static final RegistryObject<Item> DRILL_BROWN = ITEMS.register("drill_brown", DrillItem::new);
    public static final RegistryObject<Item> DRILL_CYAN = ITEMS.register("drill_cyan", DrillItem::new);
    public static final RegistryObject<Item> DRILL_GRAY = ITEMS.register("drill_gray", DrillItem::new);
    public static final RegistryObject<Item> DRILL_GREEN = ITEMS.register("drill_green", DrillItem::new);
    public static final RegistryObject<Item> DRILL_LIGHT_GRAY = ITEMS.register("drill_light_gray", DrillItem::new);
    public static final RegistryObject<Item> DRILL_LIME = ITEMS.register("drill_lime", DrillItem::new);
    public static final RegistryObject<Item> DRILL_MAGENTA = ITEMS.register("drill_magenta", DrillItem::new);
    public static final RegistryObject<Item> DRILL_ORANGE = ITEMS.register("drill_orange", DrillItem::new);
    public static final RegistryObject<Item> DRILL_PINK = ITEMS.register("drill_pink", DrillItem::new);
    public static final RegistryObject<Item> DRILL_PURPLE = ITEMS.register("drill_purple", DrillItem::new);
    public static final RegistryObject<Item> DRILL_RED = ITEMS.register("drill_red", DrillItem::new);
    public static final RegistryObject<Item> DRILL_WHITE = ITEMS.register("drill_white", DrillItem::new);
    public static final RegistryObject<Item> DRILL_YELLOW = ITEMS.register("drill_yellow", DrillItem::new);


    public static final RegistryObject<Item> SINGLE_BATTERY = ITEMS.register("single_battery", () -> new ItemBattery(200000, 1000));
    public static final RegistryObject<Item> DOUBLE_BATTERY = ITEMS.register("double_battery", () -> new ItemBattery(350000, 5000));
    public static final RegistryObject<Item> TRIPLE_BATTERY = ITEMS.register("triple_battery", () -> new ItemBattery(600000, 10000));
    public static final RegistryObject<Item> QUADRUPLE_BATTERY = ITEMS.register("quadruple_battery", () -> new ItemBattery(1000000, 30000));
    public static final RegistryObject<Item> QUINTUPLE_BATTERY = ITEMS.register("quintuple_battery", () -> new ItemBattery(2000000, 100000));
    public static final RegistryObject<Item> DRILL_UPGRADE_SPEED = ITEMS.register("drill_upgrade_speed", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED));
    public static final RegistryObject<Item> DRILL_UPGRADE_SPEED_II = ITEMS.register("drill_upgrade_speed_ii", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_II));
    public static final RegistryObject<Item> DRILL_UPGRADE_SPEED_III = ITEMS.register("drill_upgrade_speed_iii", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_III));
    public static final RegistryObject<Item> DRILL_UPGRADE_SILK_TOUCH = ITEMS.register("drill_upgrade_silk_touch", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SILK_TOUCH));
    public static final RegistryObject<Item> DRILL_UPGRADE_FORTUNE = ITEMS.register("drill_upgrade_fortune", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE));
    public static final RegistryObject<Item> DRILL_UPGRADE_FORTUNE_II = ITEMS.register("drill_upgrade_fortune_ii", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE_II));
    public static final RegistryObject<Item> DRILL_UPGRADE_THREE_BY_THREE = ITEMS.register("drill_upgrade_three_by_three", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.THREE_BY_THREE));
    public static final RegistryObject<Item> DRILL_UPGRADE_FIVE_BY_FIVE = ITEMS.register("drill_upgrade_five_by_five", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE));
    public static final RegistryObject<Item> DRILL_UPGRADE_BLOCK_PLACING = ITEMS.register("drill_upgrade_block_placing", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.PLACER));
    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", ItemCoffee::new); //TODO flatten
    public static final RegistryObject<Item> PHANTOM_CONNECTOR = ITEMS.register("phantom_connector", ItemPhantomConnector::new);
    //public static final RegistryObject<Item> FOOD = ITEMS.register("food", ItemBase::new); //just... food? //TODO
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", ItemKnife::new);
    public static final RegistryObject<Item> CRAFTER_ON_A_STICK = ITEMS.register("crafter_on_a_stick", ItemCrafterOnAStick::new);
    //public static final RegistryObject<Item> DUST = ITEMS.register("dust", ItemDust::new); //TODO flatten
    public static final RegistryObject<Item> SOLIDIFIED_EXPERIENCE = ITEMS.register("solidified_experience", ItemSolidifiedExperience::new);
    public static final RegistryObject<Item> LEAF_BLOWER = ITEMS.register("leaf_blower", () -> new ItemLeafBlower(false));
    public static final RegistryObject<Item> ADVANCED_LEAF_BLOWER = ITEMS.register("advanced_leaf_blower", () -> new ItemLeafBlower(true));

    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", ItemCoffeeBean::new);
    public static final RegistryObject<Item> FLAX = ITEMS.register("flax", ItemBase::new);

    public static final RegistryObject<Item> RICE_SEEDS = ITEMS.register("rice_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.RICE.get(), ActuallyItems.defaultProps())); //() -> new ItemSeed("seedRice", ActuallyBlocks.RICE.get(), FOOD.get(), TheFoods.RICE.ordinal()));
    public static final RegistryObject<Item> CANOLA_SEEDS = ITEMS.register("canola_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.CANOLA.get(), ActuallyItems.defaultProps())); //() -> new ItemFoodSeed("seedCanola", ActuallyBlocks.CANOLA, itemMisc, 0, 1, 0.01F, 10).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1000, 0), 0.2F));
    public static final RegistryObject<Item> FLAX_SEEDS = ITEMS.register("flax_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.FLAX.get(), ActuallyItems.defaultProps())); //() -> new ItemSeed("seedFlax", ActuallyBlocks.FLAX, Items.STRING, 0));
    public static final RegistryObject<Item> COFFEE_SEEDS = ITEMS.register("coffee_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.COFFEE.get(), ActuallyItems.defaultProps())); //() -> new ItemSeed("seedCoffeeBeans", ActuallyBlocks.COFFEE, COFFEE_BEANS, 0));

    // TOOLS & ARMOR
    public static final RegistryObject<Item> WOODEN_AIOT = ITEMS.register("wooden_aiot", () -> new AllInOneTool(ItemTier.WOOD));
    public static final RegistryObject<Item> STONE_AIOT = ITEMS.register("stone_aiot", () -> new AllInOneTool(ItemTier.STONE));
    public static final RegistryObject<Item> IRON_AIOT = ITEMS.register("iron_aiot", () -> new AllInOneTool(ItemTier.IRON));
    public static final RegistryObject<Item> GOLD_AIOT = ITEMS.register("gold_aiot", () -> new AllInOneTool(ItemTier.GOLD));
    public static final RegistryObject<Item> DIAMOND_AIOT = ITEMS.register("diamond_aiot", () -> new AllInOneTool(ItemTier.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_AIOT = ITEMS.register("netherite_aiot", () -> new AllInOneTool(ItemTier.NETHERITE));

    public static final Set<Supplier<Item>> SIMPLE_ITEMS = ImmutableSet.of(
        // Crystals
        BLACK_QUARTZ, RESTONIA_CRYSTAL, PALIS_CRYSTAL, DIAMATINE_CRYSTAL,
        VOID_CRYSTAL, EMERADIC_CRYSTAL, ENORI_CRYSTAL, EMPOWERED_RESTONIA_CRYSTAL,
        EMPOWERED_PALIS_CRYSTAL, EMPOWERED_DIAMATINE_CRYSTAL, EMPOWERED_VOID_CRYSTAL, EMPOWERED_EMERADIC_CRYSTAL,
        EMPOWERED_ENORI_CRYSTAL,
        // All in one tools
        WOODEN_AIOT, STONE_AIOT, IRON_AIOT, GOLD_AIOT, DIAMOND_AIOT, NETHERITE_AIOT,
        // The rest?
        RESTONIA_CRYSTAL_SHARD, PALIS_CRYSTAL_SHARD, DIAMATINE_CRYSTAL_SHARD, VOID_CRYSTAL_SHARD, EMERADIC_CRYSTAL_SHARD,
        ENORI_CRYSTAL_SHARD, ENGINEERS_GOGGLES, ENGINEERS_GOGGLES_ADVANCED, LASER_UPGRADE_RANGE, LASER_UPGRADE_INVISIBILITY,
        HANDHELD_FILLER, TRAVELERS_SACK, VOID_SACK, WORM, PLAYER_PROBE, FILTER, WATER_BOWL, PAPER_CONE, DOUGH,
        RING, BASIC_COIL, ADVANCED_COIL, RICE_DOUGH, TINY_COAL, TINY_CHARCOAL, RICE_SLIMEBALL, CANOLA, EMPTY_CUP,
        BATS_WING, DRILL_CORE, LENS, ENDER_STAR, CRYSTALLIZED_CANOLA_SEED, EMPOWERED_CANOLA_SEED, LENS_OF_COLOR,
        LENS_OF_DETONATION, LENS_OF_CERTAIN_DEATH, LENS_OF_THE_KILLER, LENS_OF_DISENCHANTING, LENS_OF_THE_MINER,
        LASER_WRENCH, TELEPORT_STAFF, WINGS_OF_THE_BATS, SINGLE_BATTERY, DOUBLE_BATTERY, TRIPLE_BATTERY, QUADRUPLE_BATTERY, QUINTUPLE_BATTERY,
        DRILL_BLACK, DRILL_BLUE, DRILL_BROWN, DRILL_CYAN, DRILL_GRAY, DRILL_GREEN, DRILL_MAIN, DRILL_LIGHT_GRAY,
        DRILL_LIME, DRILL_MAGENTA, DRILL_ORANGE, DRILL_PINK, DRILL_PURPLE, DRILL_RED, DRILL_WHITE, DRILL_YELLOW,
        DRILL_UPGRADE_SPEED, DRILL_UPGRADE_SPEED_II, DRILL_UPGRADE_SPEED_III, DRILL_UPGRADE_SILK_TOUCH,
        DRILL_UPGRADE_FORTUNE, DRILL_UPGRADE_FORTUNE_II, DRILL_UPGRADE_THREE_BY_THREE, DRILL_UPGRADE_FIVE_BY_FIVE, DRILL_UPGRADE_BLOCK_PLACING,
        COFFEE_CUP, PHANTOM_CONNECTOR, RICE,
        KNIFE, CRAFTER_ON_A_STICK,
/*        CRUSHED_IRON, CRUSHED_GOLD, CRUSHED_DIAMOND, CRUSHED_EMERALD, CRUSHED_LAPIS,
        CRUSHED_QUARTZ, CRUSHED_COAL, CRUSHED_BLACK_QUARTZ, */
        SOLIDIFIED_EXPERIENCE, LEAF_BLOWER, ADVANCED_LEAF_BLOWER,
        RING_OF_GROWTH, RING_OF_MAGNETIZING,
        COFFEE_BEANS, RICE_SEEDS, CANOLA_SEEDS, FLAX_SEEDS, COFFEE_SEEDS ,
        WOODEN_AIOT, STONE_AIOT, IRON_AIOT, GOLD_AIOT, DIAMOND_AIOT, NETHERITE_AIOT
    );

    private static Supplier<Item> basicItem() {
        return () -> new Item(defaultProps());
    }
    public static Item.Properties defaultProps() {
        return new Item.Properties().tab(ActuallyAdditions.GROUP);
    }

    public static Item.Properties defaultNonStacking() {
        return defaultProps().stacksTo(1);
    }

    public static void init(IEventBus evt) {
        ITEMS.register(evt);
    }
}
