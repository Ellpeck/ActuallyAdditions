package de.ellpeck.actuallyadditions.common.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.useables.AllInOneTool;
import de.ellpeck.actuallyadditions.common.items.useables.LeafBlowerItem;
import de.ellpeck.actuallyadditions.common.items.useables.ManualItem;
import de.ellpeck.actuallyadditions.common.items.useables.TeleportStaffItem;
import de.ellpeck.actuallyadditions.common.materials.ArmorMaterials;
import de.ellpeck.actuallyadditions.common.materials.ToolMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Supplier;

public final class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MOD_ID);

    // tools
    // If these ever need registry object referencing then I might be shit out of luck but it shouldn't be that complex to fix.
    public static final ToolSet QUARTZ_SET = new ToolSet("quartz", ToolMaterials.QUARTZ, ArmorMaterials.QUARTZ, ActuallyItems::defaultProps);
    public static final ToolSet ENORI_SET = new ToolSet("enori", ToolMaterials.ENORI, ArmorMaterials.ENORI, ActuallyItems::defaultProps);
    public static final ToolSet EMERADIC_SET = new ToolSet("emeradic", ToolMaterials.EMERADIC, ArmorMaterials.EMERADIC, ActuallyItems::defaultProps);
    public static final ToolSet VOID_SET = new ToolSet("void", ToolMaterials.VOID, ArmorMaterials.VOID, ActuallyItems::defaultProps);
    public static final ToolSet DIAMATINE_SET = new ToolSet("diamatine", ToolMaterials.DIAMATINE, ArmorMaterials.DIAMATINE, ActuallyItems::defaultProps);
    public static final ToolSet PALIS_SET = new ToolSet("palis", ToolMaterials.PALIS, ArmorMaterials.PALIS, ActuallyItems::defaultProps);
    public static final ToolSet RESTONIA_SET = new ToolSet("restonia", ToolMaterials.RESTONIA, ArmorMaterials.RESTONIA, ActuallyItems::defaultProps);

    // Paxels :D
    public static final RegistryObject<Item> WOODEN_PAXEL = ITEMS.register("wooden_paxel", () -> new AllInOneTool(ItemTier.WOOD));
    public static final RegistryObject<Item> STONE_PAXEL = ITEMS.register("stone_paxel", () -> new AllInOneTool(ItemTier.STONE));
    public static final RegistryObject<Item> IRON_PAXEL = ITEMS.register("iron_paxel", () -> new AllInOneTool(ItemTier.IRON));
    public static final RegistryObject<Item> GOLD_PAXEL = ITEMS.register("gold_paxel", () -> new AllInOneTool(ItemTier.GOLD));
    public static final RegistryObject<Item> DIAMOND_PAXEL = ITEMS.register("diamond_paxel", () -> new AllInOneTool(ItemTier.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_PAXEL = ITEMS.register("netherite_paxel", () -> new AllInOneTool(ItemTier.NETHERITE));
    public static final RegistryObject<Item> QUARTZ_PAXEL = ITEMS.register("quartz_paxel", () -> new AllInOneTool(ToolMaterials.QUARTZ));
    public static final RegistryObject<Item> ENORI_PAXEL = ITEMS.register("enori_paxel", () -> new AllInOneTool(ToolMaterials.ENORI));
    public static final RegistryObject<Item> EMERADIC_PAXEL = ITEMS.register("emeradic_paxel", () -> new AllInOneTool(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> VOID_PAXEL = ITEMS.register("void_paxel", () -> new AllInOneTool(ToolMaterials.VOID));
    public static final RegistryObject<Item> DIAMATINE_PAXEL = ITEMS.register("diamatine_paxel", () -> new AllInOneTool(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> PALIS_PAXEL = ITEMS.register("palis_paxel", () -> new AllInOneTool(ToolMaterials.PALIS));
    public static final RegistryObject<Item> RESTONIA_PAXEL = ITEMS.register("restonia_paxel", () -> new AllInOneTool(ToolMaterials.RESTONIA));

    public static final Set<ToolSet> ALL_TOOL_SETS = ImmutableSet.of(QUARTZ_SET, ENORI_SET, EMERADIC_SET, VOID_SET, DIAMATINE_SET, PALIS_SET, RESTONIA_SET);

    // Resources
    public static final RegistryObject<Item> BLACK_QUARTS = ITEMS.register("black_quartz", basicItem());

    public static final RegistryObject<Item> RESTONIA_CRYSTAL = ITEMS.register("restonia_crystal", basicItem());
    public static final RegistryObject<Item> PALIS_CRYSTAL = ITEMS.register("palis_crystal", basicItem());
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL = ITEMS.register("diamatine_crystal", basicItem());
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", basicItem());
    public static final RegistryObject<Item> EMERADIC_CRYSTAL = ITEMS.register("emeradic_crystal", basicItem());
    public static final RegistryObject<Item> ENORI_CRYSTAL = ITEMS.register("enori_crystal", basicItem());

    public static final RegistryObject<Item> RESTONIA_EMPOWERED_CRYSTAL = ITEMS.register("restonia_empowered_crystal", basicItem());
    public static final RegistryObject<Item> PALIS_EMPOWERED_CRYSTAL = ITEMS.register("palis_empowered_crystal", basicItem());
    public static final RegistryObject<Item> DIAMATINE_EMPOWERED_CRYSTAL = ITEMS.register("diamatine_empowered_crystal", basicItem());
    public static final RegistryObject<Item> VOID_EMPOWERED_CRYSTAL = ITEMS.register("void_empowered_crystal", basicItem());
    public static final RegistryObject<Item> EMERADIC_EMPOWERED_CRYSTAL = ITEMS.register("emeradic_empowered_crystal", basicItem());
    public static final RegistryObject<Item> ENORI_EMPOWERED_CRYSTAL = ITEMS.register("enori_empowered_crystal", basicItem());

    public static final RegistryObject<Item> RED_CRYSTAL_SHARD = ITEMS.register("red_crystal_shard", basicItem());
    public static final RegistryObject<Item> BLUE_CRYSTAL_SHARD = ITEMS.register("blue_crystal_shard", basicItem());
    public static final RegistryObject<Item> LIGHT_BLUE_CRYSTAL_SHARD = ITEMS.register("light_blue_crystal_shard", basicItem());
    public static final RegistryObject<Item> BLACK_CRYSTAL_SHARD = ITEMS.register("black_crystal_shard", basicItem());
    public static final RegistryObject<Item> GREEN_CRYSTAL_SHARD = ITEMS.register("green_crystal_shard", basicItem());
    public static final RegistryObject<Item> WHITE_CRYSTAL_SHARD = ITEMS.register("white_crystal_shard", basicItem());
    public static final RegistryObject<Item> ENGINEERS_GOGGLES = ITEMS.register("engineers_goggles", basicItem());
    public static final RegistryObject<Item> ENGINEERS_GOGGLES_INFRARED = ITEMS.register("engineers_goggles_infrared", basicItem());
    public static final RegistryObject<Item> LASER_RELAY_MODIFIER_RANGE = ITEMS.register("laser_relay_modifier_range", basicItem());
    public static final RegistryObject<Item> LASER_RELAY_MODIFIER_INVISIBILITY = ITEMS.register("laser_relay_modifier_invisibility", basicItem());
    public static final RegistryObject<Item> HANDHELD_FILLER = ITEMS.register("handheld_filler", basicItem());
    public static final RegistryObject<Item> TRAVELERS_SACK = ITEMS.register("travelers_sack", basicItem());
    public static final RegistryObject<Item> VOID_SACK = ITEMS.register("void_sack", basicItem());
    public static final RegistryObject<Item> WORM = ITEMS.register("worm", basicItem());
    public static final RegistryObject<Item> PLAYER_PROBE = ITEMS.register("player_probe", basicItem());
    public static final RegistryObject<Item> ITEM_FILTER = ITEMS.register("item_filter", basicItem());
    public static final RegistryObject<Item> BOWL_OF_WATER = ITEMS.register("bowl_of_water", basicItem());
    public static final RegistryObject<Item> PAPER_CONE = ITEMS.register("paper_cone", basicItem());
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough", basicItem());
    public static final RegistryObject<Item> RING = ITEMS.register("ring", basicItem());
    public static final RegistryObject<Item> BASIC_COIL = ITEMS.register("basic_coil", basicItem());
    public static final RegistryObject<Item> ADVANCED_COIL = ITEMS.register("advanced_coil", basicItem());
    public static final RegistryObject<Item> RICE_DOUGH = ITEMS.register("rice_dough", basicItem());
    public static final RegistryObject<Item> TINY_COAL = ITEMS.register("tiny_coal", basicItem());
    public static final RegistryObject<Item> TINY_CHARCOAL = ITEMS.register("tiny_charcoal", basicItem());
    public static final RegistryObject<Item> RICE_SLIMEBALL = ITEMS.register("rice_slimeball", basicItem());
    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", basicItem());
    public static final RegistryObject<Item> EMPTY_CUP = ITEMS.register("empty_cup", basicItem());
    public static final RegistryObject<Item> BATS_WING = ITEMS.register("bats_wing", basicItem());
    public static final RegistryObject<Item> DRILL_CORE = ITEMS.register("drill_core", basicItem());
    public static final RegistryObject<Item> LENS = ITEMS.register("lens", basicItem());
    public static final RegistryObject<Item> ENDER_STAR = ITEMS.register("ender_star", basicItem());
    public static final RegistryObject<Item> CRYSTALLIZED_CANOLA_SEED = ITEMS.register("crystallized_canola_seed", basicItem());
    public static final RegistryObject<Item> EMPOWERED_CANOLA_SEED = ITEMS.register("empowered_canola_seed", basicItem());
    public static final RegistryObject<Item> LENS_OF_COLOR = ITEMS.register("lens_of_color", basicItem());
    public static final RegistryObject<Item> LENS_OF_DETONATION = ITEMS.register("lens_of_detonation", basicItem());
    public static final RegistryObject<Item> LENS_OF_CERTAIN_DEATH = ITEMS.register("lens_of_certain_death", basicItem());
    public static final RegistryObject<Item> LENS_OF_THE_KILLER = ITEMS.register("lens_of_the_killer", basicItem());
    public static final RegistryObject<Item> LENS_OF_DISENCHANTING = ITEMS.register("lens_of_disenchanting", basicItem());
    public static final RegistryObject<Item> LENS_OF_THE_MINER = ITEMS.register("lens_of_the_miner", basicItem());
    public static final RegistryObject<Item> LASER_WRENCH = ITEMS.register("laser_wrench", basicItem());
    public static final RegistryObject<Item> TELEPORT_STAFF = ITEMS.register("teleport_staff", TeleportStaffItem::new);
    public static final RegistryObject<Item> WINGS_OF_THE_BATS = ITEMS.register("wings_of_the_bats", basicItem());
    public static final RegistryObject<Item> SINGLE_BATTERY = ITEMS.register("single_battery", basicItem());
    public static final RegistryObject<Item> DOUBLE_BATTERY = ITEMS.register("double_battery", basicItem());
    public static final RegistryObject<Item> TRIPLE_BATTERY = ITEMS.register("triple_battery", basicItem());
    public static final RegistryObject<Item> QUADRUPLE_BATTERY = ITEMS.register("quadruple_battery", basicItem());
    public static final RegistryObject<Item> QUINTUPLE_BATTERY = ITEMS.register("quintuple_battery", basicItem());
    public static final RegistryObject<Item> DRILL_MAIN = ITEMS.register("drill_light_blue", basicItem());
    public static final RegistryObject<Item> DRILL_BLACK = ITEMS.register("drill_black", basicItem());
    public static final RegistryObject<Item> DRILL_BLUE = ITEMS.register("drill_blue", basicItem());
    public static final RegistryObject<Item> DRILL_BROWN = ITEMS.register("drill_brown", basicItem());
    public static final RegistryObject<Item> DRILL_CYAN = ITEMS.register("drill_cyan", basicItem());
    public static final RegistryObject<Item> DRILL_GRAY = ITEMS.register("drill_gray", basicItem());
    public static final RegistryObject<Item> DRILL_GREEN = ITEMS.register("drill_green", basicItem());
    public static final RegistryObject<Item> DRILL_LIGHT_GRAY = ITEMS.register("drill_light_gray", basicItem());
    public static final RegistryObject<Item> DRILL_LIME = ITEMS.register("drill_lime", basicItem());
    public static final RegistryObject<Item> DRILL_MAGENTA = ITEMS.register("drill_magenta", basicItem());
    public static final RegistryObject<Item> DRILL_ORANGE = ITEMS.register("drill_orange", basicItem());
    public static final RegistryObject<Item> DRILL_PINK = ITEMS.register("drill_pink", basicItem());
    public static final RegistryObject<Item> DRILL_PURPLE = ITEMS.register("drill_purple", basicItem());
    public static final RegistryObject<Item> DRILL_RED = ITEMS.register("drill_red", basicItem());
    public static final RegistryObject<Item> DRILL_WHITE = ITEMS.register("drill_white", basicItem());
    public static final RegistryObject<Item> DRILL_YELLOW = ITEMS.register("drill_yellow", basicItem());
    public static final RegistryObject<Item> DRILL_SPEED_AUGMENT_I = ITEMS.register("drill_speed_augment_i", basicItem());
    public static final RegistryObject<Item> DRILL_SPEED_AUGMENT_II = ITEMS.register("drill_speed_augment_ii", basicItem());
    public static final RegistryObject<Item> DRILL_SPEED_AUGMENT_III = ITEMS.register("drill_speed_augment_iii", basicItem());
    public static final RegistryObject<Item> DRILL_SILK_TOUCH_AUGMENT = ITEMS.register("drill_silk_touch_augment", basicItem());
    public static final RegistryObject<Item> DRILL_FORTUNE_AUGMENT_I = ITEMS.register("drill_fortune_augment_i", basicItem());
    public static final RegistryObject<Item> DRILL_FORTUNE_AUGMENT_II = ITEMS.register("drill_fortune_augment_ii", basicItem());
    public static final RegistryObject<Item> DRILL_MINING_AUGMENT_I = ITEMS.register("drill_mining_augment_i", basicItem());
    public static final RegistryObject<Item> DRILL_MINING_AUGMENT_II = ITEMS.register("drill_mining_augment_ii", basicItem());
    public static final RegistryObject<Item> DRILL_BLOCK_PLACING_AUGMENT = ITEMS.register("drill_block_placing_augment", basicItem());
    public static final RegistryObject<Item> FERTILIZER = ITEMS.register("fertilizer", basicItem());
    public static final RegistryObject<Item> CUP_WITH_COFFEE = ITEMS.register("cup_with_coffee", basicItem());
    public static final RegistryObject<Item> PHANTOM_CONNECTOR = ITEMS.register("phantom_connector", basicItem());
    public static final RegistryObject<Item> RESONANT_RICE = ITEMS.register("resonant_rice", basicItem());
    public static final RegistryObject<Item> FOOD_CHEESE = ITEMS.register("food_cheese", basicItem());
    public static final RegistryObject<Item> FOOD_PUMPKIN_STEW = ITEMS.register("food_pumpkin_stew", basicItem());
    public static final RegistryObject<Item> FOOD_CARROT_JUICE = ITEMS.register("food_carrot_juice", basicItem());
    public static final RegistryObject<Item> FOOD_FISH_N_CHIPS = ITEMS.register("food_fish_n_chips", basicItem());
    public static final RegistryObject<Item> FOOD_FRENCH_FRIES = ITEMS.register("food_french_fries", basicItem());
    public static final RegistryObject<Item> FOOD_FRENCH_FRY = ITEMS.register("food_french_fry", basicItem());
    public static final RegistryObject<Item> FOOD_SPAGHETTI = ITEMS.register("food_spaghetti", basicItem());
    public static final RegistryObject<Item> FOOD_NOODLE = ITEMS.register("food_noodle", basicItem());
    public static final RegistryObject<Item> FOOD_CHOCOLATE_CAKE = ITEMS.register("food_chocolate_cake", basicItem());
    public static final RegistryObject<Item> FOOD_CHOCOLATE = ITEMS.register("food_chocolate", basicItem());
    public static final RegistryObject<Item> FOOD_TOAST = ITEMS.register("food_toast", basicItem());
    public static final RegistryObject<Item> FOOD_SUBMARINE_SANDWICH = ITEMS.register("food_submarine_sandwich", basicItem());
    public static final RegistryObject<Item> FOOD_BIG_COOKIE = ITEMS.register("food_big_cookie", basicItem());
    public static final RegistryObject<Item> FOOD_HAMBURGER = ITEMS.register("food_hamburger", basicItem());
    public static final RegistryObject<Item> FOOD_PIZZA = ITEMS.register("food_pizza", basicItem());
    public static final RegistryObject<Item> FOOD_BAGUETTE = ITEMS.register("food_baguette", basicItem());
    public static final RegistryObject<Item> FOOD_RICE = ITEMS.register("food_rice", basicItem());
    public static final RegistryObject<Item> FOOD_RICE_BREAD = ITEMS.register("food_rice_bread", basicItem());
    public static final RegistryObject<Item> FOOD_DOUGHNUT = ITEMS.register("food_doughnut", basicItem());
    public static final RegistryObject<Item> FOOD_TOAST_O_CHOCOLATE = ITEMS.register("food_toast_o_chocolate", basicItem());
    public static final RegistryObject<Item> FOOD_BACON = ITEMS.register("food_bacon", basicItem());
    public static final RegistryObject<Item> CU_BA_RA_JAM = ITEMS.register("jam_cu_ba_ra", basicItem());
    public static final RegistryObject<Item> GRA_KI_BA_JAM = ITEMS.register("jam_gra_ki_ba", basicItem());
    public static final RegistryObject<Item> PL_AP_LE_JAM = ITEMS.register("jam_pl_ap_le", basicItem());
    public static final RegistryObject<Item> CH_AP_CI_JAM = ITEMS.register("jam_ch_ap_ci", basicItem());
    public static final RegistryObject<Item> HO_ME_KI_JAM = ITEMS.register("jam_ho_me_ki", basicItem());
    public static final RegistryObject<Item> PI_CO_JAM = ITEMS.register("jam_pi_co", basicItem());
    public static final RegistryObject<Item> HO_ME_CO_JAM = ITEMS.register("jam_ho_me_co", basicItem());
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", basicItem());
    public static final RegistryObject<Item> CRAFTING_TABLE_ON_A_STICK = ITEMS.register("crafting_table_on_a_stick", basicItem());
    public static final RegistryObject<Item> CRUSHED_IRON = ITEMS.register("crushed_iron", basicItem());
    public static final RegistryObject<Item> CRUSHED_GOLD = ITEMS.register("crushed_gold", basicItem());
    public static final RegistryObject<Item> CRUSHED_DIAMOND = ITEMS.register("crushed_diamond", basicItem());
    public static final RegistryObject<Item> CRUSHED_EMERALD = ITEMS.register("crushed_emerald", basicItem());
    public static final RegistryObject<Item> CRUSHED_LAPIS = ITEMS.register("crushed_lapis", basicItem());
    public static final RegistryObject<Item> CRUSHED_QUARTZ = ITEMS.register("crushed_quartz", basicItem());
    public static final RegistryObject<Item> CRUSHED_COAL = ITEMS.register("crushed_coal", basicItem());
    public static final RegistryObject<Item> CRUSHED_BLACK_QUARTZ = ITEMS.register("crushed_black_quartz", basicItem());
    public static final RegistryObject<Item> SOLIDIFIED_EXPERIENCE = ITEMS.register("solidified_experience", basicItem());
    public static final RegistryObject<Item> LEAF_BLOWER = ITEMS.register("leaf_blower", () -> new LeafBlowerItem(false));
    public static final RegistryObject<Item> ADVANCED_LEAF_BLOWER = ITEMS.register("advanced_leaf_blower", () -> new LeafBlowerItem(true));
    public static final RegistryObject<Item> RING_OF_GROWTH = ITEMS.register("ring_of_growth", basicItem());
    public static final RegistryObject<Item> RING_OF_MAGNETIZING = ITEMS.register("ring_of_magnetizing", basicItem());
    public static final RegistryObject<Item> RING_OF_SPEED = ITEMS.register("ring_of_speed", basicItem());
    public static final RegistryObject<Item> RING_OF_HASTE = ITEMS.register("ring_of_haste", basicItem());
    public static final RegistryObject<Item> RING_OF_STRENGTH = ITEMS.register("ring_of_strength", basicItem());
    public static final RegistryObject<Item> RING_OF_JUMP_BOOST = ITEMS.register("ring_of_jump_boost", basicItem());
    public static final RegistryObject<Item> RING_OF_REGENERATION = ITEMS.register("ring_of_regeneration", basicItem());
    public static final RegistryObject<Item> RING_OF_RESISTANCE = ITEMS.register("ring_of_resistance", basicItem());
    public static final RegistryObject<Item> RING_OF_FIRE_RESISTANCE = ITEMS.register("ring_of_fire_resistance", basicItem());
    public static final RegistryObject<Item> RING_OF_WATER_BREATHING = ITEMS.register("ring_of_water_breathing", basicItem());
    public static final RegistryObject<Item> RING_OF_INVISIBILITY = ITEMS.register("ring_of_invisibility", basicItem());
    public static final RegistryObject<Item> RING_OF_NIGHT_VISION = ITEMS.register("ring_of_night_vision", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_SPEED = ITEMS.register("advanced_ring_of_speed", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_HASTE = ITEMS.register("advanced_ring_of_haste", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_STRENGTH = ITEMS.register("advanced_ring_of_strength", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_JUMP_BOOST = ITEMS.register("advanced_ring_of_jump_boost", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_REGENERATION = ITEMS.register("advanced_ring_of_regeneration", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_RESISTANCE = ITEMS.register("advanced_ring_of_resistance", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_FIRE_RESISTANCE = ITEMS.register("advanced_ring_of_fire_resistance", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_WATER_BREATHING = ITEMS.register("advanced_ring_of_water_breathing", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_INVISIBILITY = ITEMS.register("advanced_ring_of_invisibility", basicItem());
    public static final RegistryObject<Item> ADVANCED_RING_OF_NIGHT_VISION = ITEMS.register("advanced_ring_of_night_vision", basicItem());
    public static final RegistryObject<Item> FUR_BALL = ITEMS.register("fur_ball", basicItem());
    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", basicItem());
    public static final RegistryObject<Item> RICE_SEEDS = ITEMS.register("rice_seeds", basicItem());
    public static final RegistryObject<Item> CANOLA_SEEDS = ITEMS.register("canola_seeds", basicItem());
    public static final RegistryObject<Item> FLAX_SEEDS = ITEMS.register("flax_seeds", basicItem());
    public static final RegistryObject<Item> COFFEE_SEEDS = ITEMS.register("coffee_seeds", basicItem());

    public static final RegistryObject<Item> BOOKLET = ITEMS.register("booklet", ManualItem::new);

    public static final Set<RegistryObject<Item>> SIMPLE_ITEMS = ImmutableSet.of(
            // Crystals
            BLACK_QUARTS, RESTONIA_CRYSTAL, PALIS_CRYSTAL, DIAMATINE_CRYSTAL,
            VOID_CRYSTAL, EMERADIC_CRYSTAL, ENORI_CRYSTAL, RESTONIA_EMPOWERED_CRYSTAL,
            PALIS_EMPOWERED_CRYSTAL, DIAMATINE_EMPOWERED_CRYSTAL, VOID_EMPOWERED_CRYSTAL, EMERADIC_EMPOWERED_CRYSTAL,
            ENORI_EMPOWERED_CRYSTAL,
            // All in one tools
            WOODEN_PAXEL, STONE_PAXEL, IRON_PAXEL, GOLD_PAXEL, DIAMOND_PAXEL, NETHERITE_PAXEL, QUARTZ_PAXEL, ENORI_PAXEL,
            EMERADIC_PAXEL, VOID_PAXEL, DIAMATINE_PAXEL, PALIS_PAXEL, RESTONIA_PAXEL,
            // The rest?
            RED_CRYSTAL_SHARD, BLUE_CRYSTAL_SHARD, LIGHT_BLUE_CRYSTAL_SHARD, BLACK_CRYSTAL_SHARD, GREEN_CRYSTAL_SHARD,
            WHITE_CRYSTAL_SHARD, ENGINEERS_GOGGLES, ENGINEERS_GOGGLES_INFRARED, LASER_RELAY_MODIFIER_RANGE, LASER_RELAY_MODIFIER_INVISIBILITY,
            HANDHELD_FILLER, TRAVELERS_SACK, VOID_SACK, WORM, PLAYER_PROBE, ITEM_FILTER, BOWL_OF_WATER, PAPER_CONE, DOUGH,
            RING, BASIC_COIL, ADVANCED_COIL, RICE_DOUGH, TINY_COAL, TINY_CHARCOAL, RICE_SLIMEBALL, CANOLA, EMPTY_CUP,
            BATS_WING, DRILL_CORE, LENS, ENDER_STAR, CRYSTALLIZED_CANOLA_SEED, EMPOWERED_CANOLA_SEED, LENS_OF_COLOR,
            LENS_OF_DETONATION, LENS_OF_CERTAIN_DEATH, LENS_OF_THE_KILLER, LENS_OF_DISENCHANTING, LENS_OF_THE_MINER, LASER_WRENCH,
            TELEPORT_STAFF, WINGS_OF_THE_BATS, SINGLE_BATTERY, DOUBLE_BATTERY, TRIPLE_BATTERY, QUADRUPLE_BATTERY, QUINTUPLE_BATTERY,
            DRILL_BLACK, DRILL_BLUE, DRILL_BROWN, DRILL_CYAN, DRILL_GRAY, DRILL_GREEN, DRILL_MAIN, DRILL_LIGHT_GRAY,
            DRILL_LIME, DRILL_MAGENTA, DRILL_ORANGE, DRILL_PINK, DRILL_PURPLE, DRILL_RED, DRILL_WHITE, DRILL_YELLOW, DRILL_SPEED_AUGMENT_I,
            DRILL_SPEED_AUGMENT_II, DRILL_SPEED_AUGMENT_III, DRILL_SILK_TOUCH_AUGMENT, DRILL_FORTUNE_AUGMENT_I, DRILL_FORTUNE_AUGMENT_II,
            DRILL_MINING_AUGMENT_I, DRILL_MINING_AUGMENT_II, DRILL_BLOCK_PLACING_AUGMENT, FERTILIZER, CUP_WITH_COFFEE, PHANTOM_CONNECTOR,
            RESONANT_RICE, FOOD_CHEESE, FOOD_PUMPKIN_STEW, FOOD_CARROT_JUICE, FOOD_FISH_N_CHIPS, FOOD_FRENCH_FRIES, FOOD_FRENCH_FRY,
            FOOD_SPAGHETTI, FOOD_NOODLE, FOOD_CHOCOLATE_CAKE, FOOD_CHOCOLATE, FOOD_TOAST, FOOD_SUBMARINE_SANDWICH, FOOD_BIG_COOKIE,
            FOOD_HAMBURGER, FOOD_PIZZA, FOOD_BAGUETTE, FOOD_RICE, FOOD_RICE_BREAD, FOOD_DOUGHNUT, FOOD_TOAST_O_CHOCOLATE,
            FOOD_BACON, CU_BA_RA_JAM, GRA_KI_BA_JAM, PL_AP_LE_JAM, CH_AP_CI_JAM, HO_ME_KI_JAM, PI_CO_JAM, HO_ME_CO_JAM,
            KNIFE, CRAFTING_TABLE_ON_A_STICK, CRUSHED_IRON, CRUSHED_GOLD, CRUSHED_DIAMOND, CRUSHED_EMERALD, CRUSHED_LAPIS,
            CRUSHED_QUARTZ, CRUSHED_COAL, CRUSHED_BLACK_QUARTZ, SOLIDIFIED_EXPERIENCE, LEAF_BLOWER, ADVANCED_LEAF_BLOWER,
            RING_OF_GROWTH, RING_OF_MAGNETIZING, RING_OF_SPEED, RING_OF_HASTE, RING_OF_STRENGTH, RING_OF_JUMP_BOOST,
            RING_OF_REGENERATION, RING_OF_RESISTANCE, RING_OF_FIRE_RESISTANCE, RING_OF_WATER_BREATHING, RING_OF_INVISIBILITY,
            RING_OF_NIGHT_VISION, ADVANCED_RING_OF_SPEED, ADVANCED_RING_OF_HASTE, ADVANCED_RING_OF_STRENGTH,
            ADVANCED_RING_OF_JUMP_BOOST, ADVANCED_RING_OF_REGENERATION, ADVANCED_RING_OF_RESISTANCE, ADVANCED_RING_OF_FIRE_RESISTANCE,
            ADVANCED_RING_OF_WATER_BREATHING, ADVANCED_RING_OF_INVISIBILITY, ADVANCED_RING_OF_NIGHT_VISION, FUR_BALL,
            COFFEE_BEANS, RICE_SEEDS, CANOLA_SEEDS, FLAX_SEEDS, COFFEE_SEEDS
    );

    private static Supplier<Item> basicItem() {
        return () -> new Item(defaultProps());
    }

    private static Item.Properties defaultProps() {
        return new Item.Properties().group(ActuallyAdditions.ACTUALLY_GROUP);
    }

    static {
        ALL_TOOL_SETS.forEach(e -> e.register(ITEMS));
    }
}
