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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public final class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MODID);

    // REMOVE ME
    @Deprecated
    public static final RegistryObject<Item> itemMisc = ITEMS.register("misc", ItemBase::new);

    // MISC ITEMS
//    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", ItemBase::new);
    public static final RegistryObject<Item> COFFEE_CUP = ITEMS.register("coffee_cup", ItemBase::new);
    public static final RegistryObject<Item> PAPER_CONE = ITEMS.register("paper_cone", ItemBase::new);
    public static final RegistryObject<Item> MASHED_FOOD = ITEMS.register("mashed_food", ItemBase::new);
    public static final RegistryObject<Item> KNIFE_BLADE = ITEMS.register("knife_blade", ItemBase::new);
    public static final RegistryObject<Item> KNIFE_HANDLE = ITEMS.register("knife_handle", ItemBase::new);
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough", ItemBase::new);
    public static final RegistryObject<Item> RING = ITEMS.register("ring", ItemBase::new);
    public static final RegistryObject<Item> BASIC_COIL = ITEMS.register("basic_coil", ItemBase::new);
    public static final RegistryObject<Item> ADVANCED_COIL = ITEMS.register("advanced_coil", ItemBase::new);
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
    public static final RegistryObject<Item> BLACK_DYE = ITEMS.register("black_dye", ItemBase::new);
    public static final RegistryObject<Item> LENS = ITEMS.register("lens", ItemBase::new);
    public static final RegistryObject<Item> ENDER_STAR = ITEMS.register("ender_star", ItemBase::new);
    public static final RegistryObject<Item> SPAWNER_SHARD = ITEMS.register("spawner_shard", ItemBase::new);
    public static final RegistryObject<Item> BIOMASS = ITEMS.register("biomass", ItemBase::new);
    public static final RegistryObject<Item> BIOCOAL = ITEMS.register("biocoal", ItemBase::new);
    public static final RegistryObject<Item> CRYSTALLIZED_CANOLA_SEED = ITEMS.register("crystallized_canola_seed", ItemBase::new);
    public static final RegistryObject<Item> EMPOWERED_CANOLA_SEED = ITEMS.register("empowered_canola_seed", ItemBase::new);

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
    public static final RegistryObject<Item> HANDHELD_FILLER = ITEMS.register("handheld_filler", ItemFillingWand::new);
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
    //public static final RegistryObject<Item> WATER_REMOVAL_RING = ITEMS.register("water_removal_ring", ItemWaterRemovalRing::new);
    public static final RegistryObject<Item> TELEPORT_STAFF = ITEMS.register("teleport_staff", ItemTeleStaff::new);
    public static final RegistryObject<Item> WINGS_OF_THE_BATS = ITEMS.register("wings_of_the_bats", ItemWingsOfTheBats::new);
    public static final RegistryObject<Item> DRILL = ITEMS.register("drill", ItemDrill::new);
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
    public static final RegistryObject<Item> FERTILIZER = ITEMS.register("fertilizer", ItemFertilizer::new);
    //    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", ItemCoffee::new);
    public static final RegistryObject<Item> PHANTOM_CONNECTOR = ITEMS.register("phantom_connector", ItemPhantomConnector::new);
    public static final RegistryObject<Item> RESONANT_RICE = ITEMS.register("resonant_rice", ItemResonantRice::new);
    public static final RegistryObject<Item> FOOD = ITEMS.register("food", ItemBase::new); //just... food? //TODO
    public static final RegistryObject<Item> JAM = ITEMS.register("jam", ItemJams::new);
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", ItemKnife::new);
    public static final RegistryObject<Item> CRAFTER_ON_A_STICK = ITEMS.register("crafter_on_a_stick", ItemCrafterOnAStick::new);
    public static final RegistryObject<Item> DUST = ITEMS.register("dust", ItemDust::new);
    public static final RegistryObject<Item> SOLIDIFIED_EXPERIENCE = ITEMS.register("solidified_experience", ItemSolidifiedExperience::new);
    public static final RegistryObject<Item> LEAF_BLOWER = ITEMS.register("leaf_blower", () -> new ItemLeafBlower(false));
    public static final RegistryObject<Item> ADVANCED_LEAF_BLOWER = ITEMS.register("advanced_leaf_blower", () -> new ItemLeafBlower(true));

    // TODO [port] unflatten
    public static final RegistryObject<Item> POTION_RING = ITEMS.register("potion_ring", () -> new ItemPotionRing(false));
    public static final RegistryObject<Item> POTION_RING_ADVANCED = ITEMS.register("potion_ring_advanced", () -> new ItemPotionRing(true));

    public static final RegistryObject<Item> HAIRY_BALL = ITEMS.register("hairy_ball", ItemHairBall::new);
    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", ItemCoffeeBean::new);

    public static final RegistryObject<Item> RICE_SEEDS = ITEMS.register("rice_seeds", ItemBase::new); //() -> new ItemSeed("seedRice", ActuallyBlocks.RICE.get(), FOOD.get(), TheFoods.RICE.ordinal()));
    public static final RegistryObject<Item> CANOLA_SEEDS = ITEMS.register("canola_seeds", ItemBase::new); //() -> new ItemFoodSeed("seedCanola", ActuallyBlocks.CANOLA, itemMisc, 0, 1, 0.01F, 10).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1000, 0), 0.2F));
    public static final RegistryObject<Item> FLAX_SEEDS = ITEMS.register("flax_seeds", ItemBase::new); //() -> new ItemSeed("seedFlax", ActuallyBlocks.FLAX, Items.STRING, 0));
    public static final RegistryObject<Item> COFFEE_SEEDS = ITEMS.register("coffee_seeds", ItemBase::new); //() -> new ItemSeed("seedCoffeeBeans", ActuallyBlocks.COFFEE, COFFEE_BEANS, 0));

    // TOOLS & ARMOR
    public static final RegistryObject<Item> HELM_QUARTZ = ITEMS.register("helm_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_QUARTZ = ITEMS.register("chest_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_QUARTZ = ITEMS.register("pants_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_QUARTZ = ITEMS.register("boots_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.FEET));

    public static final RegistryObject<Item> PICKAXE_QUARTZ = ITEMS.register("pickaxe_quartz", () -> new ItemPickaxeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> AXE_QUARTZ = ITEMS.register("axe_quartz", () -> new ItemAxeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> SHOVEL_QUARTZ = ITEMS.register("shovel_quartz", ItemBase::new); //() -> new ItemShovelAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> SWORD_QUARTZ = ITEMS.register("sword_quartz", () -> new ItemSwordAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> HOE_QUARTZ = ITEMS.register("hoe_quartz", () -> new ItemHoeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> WOODEN_AIOT = ITEMS.register("wooden_aiot", () -> new AllInOneTool(ItemTier.WOOD));
    public static final RegistryObject<Item> STONE_AIOT = ITEMS.register("stone_aiot", () -> new AllInOneTool(ItemTier.STONE));
    public static final RegistryObject<Item> IRON_AIOT = ITEMS.register("iron_aiot", () -> new AllInOneTool(ItemTier.IRON));
    public static final RegistryObject<Item> GOLD_AIOT = ITEMS.register("gold_aiot", () -> new AllInOneTool(ItemTier.GOLD));
    public static final RegistryObject<Item> DIAMOND_AIOT = ITEMS.register("diamond_aiot", () -> new AllInOneTool(ItemTier.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_AIOT = ITEMS.register("netherite_aiot", () -> new AllInOneTool(ItemTier.NETHERITE));
    public static final RegistryObject<Item> QUARTZ_AIOT = ITEMS.register("quartz_aiot", () -> new AllInOneTool(ToolMaterials.BLACK_QUARTZ));

    public static final RegistryObject<Item> RESTONIA_PICKAXE = ITEMS.register("restonia_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_AXE = ITEMS.register("restonia_axe", () -> new ItemAxeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_SHOVEL = ITEMS.register("restonia_shovel", ItemBase::new); // () -> new ItemShovelAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_SWORD = ITEMS.register("restonia_sword", () -> new ItemSwordAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_HOE = ITEMS.register("restonia_hoe", () -> new ItemHoeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_HELMET = ITEMS.register("restonia_helmet", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> RESTONIA_CHEST = ITEMS.register("restonia_chest", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> RESTONIA_LEGGINGS = ITEMS.register("restonia_leggings", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> RESTONIA_BOOTS = ITEMS.register("restonia_boots", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> RESTONIA_AIOT = ITEMS.register("restonia_aiot", () -> new AllInOneTool(ToolMaterials.RESTONIA));

    public static final RegistryObject<Item> PALIS_PICKAXE = ITEMS.register("palis_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_AXE = ITEMS.register("palis_axe", () -> new ItemAxeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_SHOVEL = ITEMS.register("palis_shovel", ItemBase::new); //() -> new ItemShovelAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_SWORD = ITEMS.register("palis_sword", () -> new ItemSwordAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_HOE = ITEMS.register("palis_hoe", () -> new ItemHoeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_HELMET = ITEMS.register("palis_helmet", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> PALIS_CHEST = ITEMS.register("palis_chest", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PALIS_LEGGINGS = ITEMS.register("palis_leggings", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> PALIS_BOOTS = ITEMS.register("palis_boots", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PALIS_AIOT = ITEMS.register("palis_aiot", () -> new AllInOneTool(ToolMaterials.PALIS));

    public static final RegistryObject<Item> DIAMATINE_PICKAXE = ITEMS.register("diamatine_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_AXE = ITEMS.register("diamatine_axe", () -> new ItemAxeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_SHOVEL = ITEMS.register("diamatine_shovel", ItemBase::new); //() -> new ItemShovelAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_SWORD = ITEMS.register("diamatine_sword", () -> new ItemSwordAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_HOE = ITEMS.register("diamatine_hoe", () -> new ItemHoeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_HELMET = ITEMS.register("diamatine_helmet", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> DIAMATINE_CHEST = ITEMS.register("diamatine_chest", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> DIAMATINE_LEGGINGS = ITEMS.register("diamatine_leggings", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> DIAMATINE_BOOTS = ITEMS.register("diamatine_boots", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> DIAMATINE_AIOT = ITEMS.register("diamatine_aiot", () -> new AllInOneTool(ToolMaterials.DIAMATINE));

    public static final RegistryObject<Item> VOID_PICKAXE = ITEMS.register("void_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_AXE = ITEMS.register("void_axe", () -> new ItemAxeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_SHOVEL = ITEMS.register("void_shovel", ItemBase::new); // () -> new ItemShovelAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_SWORD = ITEMS.register("void_sword", () -> new ItemSwordAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_HOE = ITEMS.register("void_hoe", () -> new ItemHoeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_HELMET = ITEMS.register("void_helmet", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> VOID_CHEST = ITEMS.register("void_chest", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> VOID_LEGGINGS = ITEMS.register("void_leggings", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> VOID_BOOTS = ITEMS.register("void_boots", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> VOID_AIOT = ITEMS.register("void_aiot", () -> new AllInOneTool(ToolMaterials.VOID));

    public static final RegistryObject<Item> EMERADIC_PICKAXE = ITEMS.register("emeradic_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_AXE = ITEMS.register("emeradic_axe", () -> new ItemAxeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_SHOVEL = ITEMS.register("emeradic_shovel", ItemBase::new); //() -> new ItemShovelAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_SWORD = ITEMS.register("emeradic_sword", () -> new ItemSwordAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_HOE = ITEMS.register("emeradic_hoe", () -> new ItemHoeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_HELMET = ITEMS.register("emeradic_helmet", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> EMERADIC_CHEST = ITEMS.register("emeradic_chest", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> EMERADIC_LEGGINGS = ITEMS.register("emeradic_leggings", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> EMERADIC_BOOTS = ITEMS.register("emeradic_boots", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> EMERADIC_AIOT = ITEMS.register("emeradic_aiot", () -> new AllInOneTool(ToolMaterials.EMERADIC));

    public static final RegistryObject<Item> ENORI_PICKAXE = ITEMS.register("enori_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_AXE = ITEMS.register("enori_axe", () -> new ItemAxeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_SHOVEL = ITEMS.register("enori_shovel", ItemBase::new); //() -> new ItemShovelAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_SWORD = ITEMS.register("enori_sword", () -> new ItemSwordAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_HOE = ITEMS.register("enori_hoe", () -> new ItemHoeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_HELMET = ITEMS.register("enori_helmet", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> ENORI_CHEST = ITEMS.register("enori_chest", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> ENORI_LEGGINGS = ITEMS.register("enori_leggings", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> ENORI_BOOTS = ITEMS.register("enori_boots", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> ENORI_AIOT = ITEMS.register("enori_aiot", () -> new AllInOneTool(ToolMaterials.ENORI));

    public static final Set<RegistryObject<Item>> SIMPLE_ITEMS = ImmutableSet.of(
        // Crystals
        BLACK_QUARTZ, RESTONIA_CRYSTAL, PALIS_CRYSTAL, DIAMATINE_CRYSTAL,
        VOID_CRYSTAL, EMERADIC_CRYSTAL, ENORI_CRYSTAL, EMPOWERED_RESTONIA_CRYSTAL,
        EMPOWERED_PALIS_CRYSTAL, EMPOWERED_DIAMATINE_CRYSTAL, EMPOWERED_VOID_CRYSTAL, EMPOWERED_EMERADIC_CRYSTAL,
        EMPOWERED_ENORI_CRYSTAL,
        // All in one tools
        WOODEN_AIOT, STONE_AIOT, IRON_AIOT, GOLD_AIOT, DIAMOND_AIOT, NETHERITE_AIOT, QUARTZ_AIOT, ENORI_AIOT,
        EMERADIC_AIOT, VOID_AIOT, DIAMATINE_AIOT, PALIS_AIOT, RESTONIA_AIOT,
        // The rest?
        RESTONIA_CRYSTAL_SHARD, PALIS_CRYSTAL_SHARD, DIAMATINE_CRYSTAL_SHARD, VOID_CRYSTAL_SHARD, EMERADIC_CRYSTAL_SHARD,
        ENORI_CRYSTAL_SHARD, ENGINEERS_GOGGLES, ENGINEERS_GOGGLES_ADVANCED, LASER_UPGRADE_RANGE, LASER_UPGRADE_INVISIBILITY,
        HANDHELD_FILLER, TRAVELERS_SACK, VOID_SACK, WORM, PLAYER_PROBE, FILTER, WATER_BOWL, PAPER_CONE, DOUGH,
        RING, BASIC_COIL, ADVANCED_COIL, RICE_DOUGH, TINY_COAL, TINY_CHARCOAL, RICE_SLIMEBALL, /*CANOLA,*/ EMPTY_CUP,
        BATS_WING, DRILL_CORE, LENS, ENDER_STAR, CRYSTALLIZED_CANOLA_SEED, EMPOWERED_CANOLA_SEED, LENS_OF_COLOR,
/*        LENS_OF_DETONATION, LENS_OF_CERTAIN_DEATH, LENS_OF_THE_KILLER, LENS_OF_DISENCHANTING, LENS_OF_THE_MINER,*/
        LASER_WRENCH, TELEPORT_STAFF, WINGS_OF_THE_BATS, SINGLE_BATTERY, DOUBLE_BATTERY, TRIPLE_BATTERY, QUADRUPLE_BATTERY, QUINTUPLE_BATTERY,
/*        DRILL_BLACK, DRILL_BLUE, DRILL_BROWN, DRILL_CYAN, DRILL_GRAY, DRILL_GREEN, DRILL_MAIN, DRILL_LIGHT_GRAY,
        DRILL_LIME, DRILL_MAGENTA, DRILL_ORANGE, DRILL_PINK, DRILL_PURPLE, DRILL_RED, DRILL_WHITE, DRILL_YELLOW, DRILL_SPEED_AUGMENT_I,
        DRILL_SPEED_AUGMENT_II, DRILL_SPEED_AUGMENT_III, DRILL_SILK_TOUCH_AUGMENT, DRILL_FORTUNE_AUGMENT_I, DRILL_FORTUNE_AUGMENT_II,
        DRILL_MINING_AUGMENT_I, DRILL_MINING_AUGMENT_II, DRILL_BLOCK_PLACING_AUGMENT, */
        FERTILIZER, COFFEE_CUP, PHANTOM_CONNECTOR,
        RESONANT_RICE,
/*        FOOD_CHEESE, FOOD_PUMPKIN_STEW, FOOD_CARROT_JUICE, FOOD_FISH_N_CHIPS, FOOD_FRENCH_FRIES, FOOD_FRENCH_FRY,
        FOOD_SPAGHETTI, FOOD_NOODLE, FOOD_CHOCOLATE_CAKE, FOOD_CHOCOLATE, FOOD_TOAST, FOOD_SUBMARINE_SANDWICH, FOOD_BIG_COOKIE,
        FOOD_HAMBURGER, FOOD_PIZZA, FOOD_BAGUETTE, FOOD_RICE, FOOD_RICE_BREAD, FOOD_DOUGHNUT, FOOD_TOAST_O_CHOCOLATE,
        FOOD_BACON, CU_BA_RA_JAM, GRA_KI_BA_JAM, PL_AP_LE_JAM, CH_AP_CI_JAM, HO_ME_KI_JAM, PI_CO_JAM, HO_ME_CO_JAM,*/
        KNIFE, CRAFTER_ON_A_STICK,
/*        CRUSHED_IRON, CRUSHED_GOLD, CRUSHED_DIAMOND, CRUSHED_EMERALD, CRUSHED_LAPIS,
        CRUSHED_QUARTZ, CRUSHED_COAL, CRUSHED_BLACK_QUARTZ, */
        SOLIDIFIED_EXPERIENCE, LEAF_BLOWER, ADVANCED_LEAF_BLOWER,
        RING_OF_GROWTH, RING_OF_MAGNETIZING,
/*        RING_OF_SPEED, RING_OF_HASTE, RING_OF_STRENGTH, RING_OF_JUMP_BOOST,
        RING_OF_REGENERATION, RING_OF_RESISTANCE, RING_OF_FIRE_RESISTANCE, RING_OF_WATER_BREATHING, RING_OF_INVISIBILITY,
        RING_OF_NIGHT_VISION, ADVANCED_RING_OF_SPEED, ADVANCED_RING_OF_HASTE, ADVANCED_RING_OF_STRENGTH,
        ADVANCED_RING_OF_JUMP_BOOST, ADVANCED_RING_OF_REGENERATION, ADVANCED_RING_OF_RESISTANCE, ADVANCED_RING_OF_FIRE_RESISTANCE,
        ADVANCED_RING_OF_WATER_BREATHING, ADVANCED_RING_OF_INVISIBILITY, ADVANCED_RING_OF_NIGHT_VISION,*/
        HAIRY_BALL,
        COFFEE_BEANS, RICE_SEEDS, CANOLA_SEEDS, FLAX_SEEDS, COFFEE_SEEDS
    );

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
