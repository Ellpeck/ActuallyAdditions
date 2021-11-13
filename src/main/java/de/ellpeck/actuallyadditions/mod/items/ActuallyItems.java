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

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.base.*;
import de.ellpeck.actuallyadditions.mod.items.lens.ItemLens;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.material.ArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.ToolMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MODID);

    // REMOVE ME
    @Deprecated
    public static final RegistryObject<Item> itemMisc = ITEMS.register("misc", ItemBase::new);

    // MISC ITEMS
    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", ItemBase::new);
    public static final RegistryObject<Item> COFFEE_CUP = ITEMS.register("coffee_cup", ItemBase::new);
    public static final RegistryObject<Item> PAPER_CONE = ITEMS.register("paper_cone", ItemBase::new);
    public static final RegistryObject<Item> MASHED_FOOD = ITEMS.register("mashed_food", ItemBase::new);
    public static final RegistryObject<Item> KNIFE_BLADE = ITEMS.register("knife_blade", ItemBase::new);
    public static final RegistryObject<Item> KNIFE_HANDLE = ITEMS.register("knife_handle", ItemBase::new);
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough", ItemBase::new);
    public static final RegistryObject<Item> RING = ITEMS.register("ring", ItemBase::new);
    public static final RegistryObject<Item> COIL = ITEMS.register("coil", ItemBase::new);
    public static final RegistryObject<Item> COIL_ADVANCED = ITEMS.register("coil_advanced", ItemBase::new);
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
    public static final RegistryObject<Item> RICE_SLIME = ITEMS.register("rice_slime", ItemBase::new);
    public static final RegistryObject<Item> CUP = ITEMS.register("cup", ItemBase::new);
    public static final RegistryObject<Item> BAT_WING = ITEMS.register("bat_wing", ItemBase::new);
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

    public static final RegistryObject<Item> EMPOWERED_RESTONIA_CRYSTAL = ITEMS.register("restonia_empowered_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_PALIS_CRYSTAL = ITEMS.register("palis_empowered_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_DIAMATINE_CRYSTAL = ITEMS.register("diamatine_empowered_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_VOID_CRYSTAL = ITEMS.register("void_empowered_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_EMERADIC_CRYSTAL = ITEMS.register("emeradic_empowered_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_ENORI_CRYSTAL = ITEMS.register("enori_empowered_crystal", () -> new ItemCrystal(true));

    // BLACK QUARTZ
    public static final RegistryObject<Item> BLACK_QUARTZ = ITEMS.register("black_quartz", ItemBase::new);

    public static final RegistryObject<Item> ENGINEER_GOGGLES_ADVANCED = ITEMS.register("engineer_goggles_advanced", () -> new ItemEngineerGoggles(true));
    public static final RegistryObject<Item> ENGINEER_GOGGLES = ITEMS.register("engineer_goggles", () -> new ItemEngineerGoggles(false));
    public static final RegistryObject<Item> LASER_UPGRADE_RANGE = ITEMS.register("laser_upgrade_range", ItemBase::new);
    public static final RegistryObject<Item> LASER_UPGRADE_INVISIBILITY = ITEMS.register("laser_upgrade_invisibility", ItemBase::new);
    public static final RegistryObject<Item> FILLING_WAND = ITEMS.register("filling_wand", ItemFillingWand::new);
    public static final RegistryObject<Item> BAG = ITEMS.register("bag", () -> new ItemBag(false));
    public static final RegistryObject<Item> VOID_BAG = ITEMS.register("void_bag", () -> new ItemBag(true));
    public static final RegistryObject<Item> WORM = ITEMS.register("worm", ItemWorm::new);
    public static final RegistryObject<Item> PLAYER_PROBE = ITEMS.register("player_probe", ItemPlayerProbe::new);
    public static final RegistryObject<Item> FILTER = ITEMS.register("filter", ItemFilter::new);
    public static final RegistryObject<Item> WATER_BOWL = ITEMS.register("water_bowl", ItemWaterBowl::new);
    public static final RegistryObject<Item> CRATE_KEEPER = ITEMS.register("crate_keeper", () -> new ItemGeneric(defaultProps().stacksTo(1)));
    public static final RegistryObject<Item> COLOR_LENS = ITEMS.register("color_lens", () -> new ItemLens(ActuallyAdditionsAPI.lensColor));
    public static final RegistryObject<Item> EXPLOSION_LENS = ITEMS.register("explosion_lens", () -> new ItemLens(ActuallyAdditionsAPI.lensDetonation));
    public static final RegistryObject<Item> DAMAGE_LENS = ITEMS.register("damage_lens", () -> new ItemLens(ActuallyAdditionsAPI.lensDeath));
    public static final RegistryObject<Item> MORE_DAMAGE_LENS = ITEMS.register("more_damage_lens", () -> new ItemLens(ActuallyAdditionsAPI.lensEvenMoarDeath));
    public static final RegistryObject<Item> DISENCHANTING_LENS = ITEMS.register("disenchanting_lens", () -> new ItemLens(ActuallyAdditionsAPI.lensDisenchanting));
    public static final RegistryObject<Item> MINING_LENS = ITEMS.register("mining_lens", () -> new ItemLens(ActuallyAdditionsAPI.lensMining));
    public static final RegistryObject<Item> LASER_WRENCH = ITEMS.register("laser_wrench", ItemLaserWrench::new);
    //    public static final RegistryObject<Item> itemChestToCrateUpgrade = ITEMS.register("", new ItemChestToCrateUpgrade("chest_to_crate_upgrade", TileEntityChest.class, InitBlocks.blockGiantChest.getDefaultState()));
    //    public static final RegistryObject<Item> itemSmallToMediumCrateUpgrade = ITEMS.register("", new ItemChestToCrateUpgrade("small_to_medium_crate_upgrade", TileEntityGiantChest.class, InitBlocks.blockGiantChestMedium.getDefaultState()));
    //    public static final RegistryObject<Item> itemMediumToLargeCrateUpgrade = ITEMS.register("", new ItemChestToCrateUpgrade("medium_to_large_crate_upgrade", TileEntityGiantChestMedium.class, InitBlocks.blockGiantChestLarge.getDefaultState()));
    public static final RegistryObject<Item> ITEM_BOOKLET = ITEMS.register("booklet", ItemBooklet::new);
    public static final RegistryObject<Item> GROWTH_RING = ITEMS.register("growth_ring", ItemGrowthRing::new);
    public static final RegistryObject<Item> MAGNET_RING = ITEMS.register("suction_ring", ItemMagnetRing::new);
    public static final RegistryObject<Item> WATER_REMOVAL_RING = ITEMS.register("water_removal_ring", ItemWaterRemovalRing::new);
    public static final RegistryObject<Item> TELE_STAFF = ITEMS.register("tele_staff", ItemTeleStaff::new);
    public static final RegistryObject<Item> WINGS_OF_THE_BATS = ITEMS.register("wings_of_the_bats", ItemWingsOfTheBats::new);
    public static final RegistryObject<Item> DRILL = ITEMS.register("drill", ItemDrill::new);
    public static final RegistryObject<Item> BATTERY = ITEMS.register("battery", () -> new ItemBattery(200000, 1000));
    public static final RegistryObject<Item> BATTERY_DOUBLE = ITEMS.register("battery_double", () -> new ItemBattery(350000, 5000));
    public static final RegistryObject<Item> BATTERY_TRIPLE = ITEMS.register("battery_triple", () -> new ItemBattery(600000, 10000));
    public static final RegistryObject<Item> BATTERY_QUADRUPLE = ITEMS.register("battery_quadruple", () -> new ItemBattery(1000000, 30000));
    public static final RegistryObject<Item> BATTERY_QUINTUPLE = ITEMS.register("battery_quintuple", () -> new ItemBattery(2000000, 100000));
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
    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", ItemCoffee::new);
    public static final RegistryObject<Item> PHANTOM_CONNECTOR = ITEMS.register("phantom_connector", ItemPhantomConnector::new);
    public static final RegistryObject<Item> RESONANT_RICE = ITEMS.register("resonant_rice", ItemResonantRice::new);
    public static final RegistryObject<Item> FOOD = ITEMS.register("food", ItemFoods::new); //just... food?
    public static final RegistryObject<Item> JAM = ITEMS.register("jam", ItemJams::new);
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", ItemKnife::new);
    public static final RegistryObject<Item> CRAFTER_ON_A_STICK = ITEMS.register("crafter_on_a_stick", ItemCrafterOnAStick::new);
    public static final RegistryObject<Item> DUST = ITEMS.register("dust", ItemDust::new);
    public static final RegistryObject<Item> SOLIDIFIED_EXPERIENCE = ITEMS.register("solidified_experience", ItemSolidifiedExperience::new);
    public static final RegistryObject<Item> LEAF_BLOWER = ITEMS.register("leaf_blower", () -> new ItemLeafBlower(false));
    public static final RegistryObject<Item> LEAF_BLOWER_ADVANCED = ITEMS.register("leaf_blower_advanced", () -> new ItemLeafBlower(true));

    // TODO [port] unflatten
    public static final RegistryObject<Item> POTION_RING = ITEMS.register("potion_ring", () -> new ItemPotionRing(false));
    public static final RegistryObject<Item> POTION_RING_ADVANCED = ITEMS.register("potion_ring_advanced", () -> new ItemPotionRing(true));

    public static final RegistryObject<Item> HAIRY_BALL = ITEMS.register("hairy_ball", ItemHairBall::new);
    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", ItemCoffeeBean::new);

    public static final RegistryObject<Item> RICE_SEED = ITEMS.register("rice_seed", () -> new ItemSeed("seedRice", ActuallyBlocks.RICE.get(), FOOD.get(), TheFoods.RICE.ordinal()));
    public static final RegistryObject<Item> CANOLA_SEED = ITEMS.register("canola_seed", () -> new ItemFoodSeed("seedCanola", ActuallyBlocks.CANOLA, itemMisc, 0, 1, 0.01F, 10).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1000, 0), 0.2F));
    public static final RegistryObject<Item> FLAX_SEED = ITEMS.register("flax_seed", () -> new ItemSeed("seedFlax", ActuallyBlocks.FLAX, Items.STRING, 0));
    public static final RegistryObject<Item> COFFEE_SEED = ITEMS.register("coffee_seed", () -> new ItemSeed("seedCoffeeBeans", ActuallyBlocks.COFFEE, COFFEE_BEANS, 0));

    // TOOLS & ARMOR
    public static final RegistryObject<Item> HELM_QUARTZ = ITEMS.register("helm_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_QUARTZ = ITEMS.register("chest_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_QUARTZ = ITEMS.register("pants_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_QUARTZ = ITEMS.register("boots_quartz", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, EquipmentSlotType.FEET));

    public static final RegistryObject<Item> PICKAXE_QUARTZ = ITEMS.register("pickaxe_quartz", () -> new ItemPickaxeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> AXE_QUARTZ = ITEMS.register("axe_quartz", () -> new ItemAxeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> SHOVEL_QUARTZ = ITEMS.register("shovel_quartz", () -> new ItemShovelAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> SWORD_QUARTZ = ITEMS.register("sword_quartz", () -> new ItemSwordAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> HOE_QUARTZ = ITEMS.register("hoe_quartz", () -> new ItemHoeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> WOODEN_PAXEL = ITEMS.register("wooden_paxel", () -> new AllInOneTool(ItemTier.WOOD));
    public static final RegistryObject<Item> STONE_PAXEL = ITEMS.register("stone_paxel", () -> new AllInOneTool(ItemTier.STONE));
    public static final RegistryObject<Item> IRON_PAXEL = ITEMS.register("iron_paxel", () -> new AllInOneTool(ItemTier.IRON));
    public static final RegistryObject<Item> GOLD_PAXEL = ITEMS.register("gold_paxel", () -> new AllInOneTool(ItemTier.GOLD));
    public static final RegistryObject<Item> DIAMOND_PAXEL = ITEMS.register("diamond_paxel", () -> new AllInOneTool(ItemTier.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_PAXEL = ITEMS.register("netherite_paxel", () -> new AllInOneTool(ItemTier.NETHERITE));
    public static final RegistryObject<Item> QUARTZ_PAXEL = ITEMS.register("quartz_paxel", () -> new AllInOneTool(ToolMaterials.BLACK_QUARTZ));

    public static final RegistryObject<Item> PICKAXE_CRYSTAL_RESTONIA = ITEMS.register("pickaxe_crystal_restonia", () -> new ItemPickaxeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> AXE_CRYSTAL_RESTONIA = ITEMS.register("axe_crystal_restonia", () -> new ItemAxeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> SHOVEL_CRYSTAL_RESTONIA = ITEMS.register("shovel_crystal_restonia", () -> new ItemShovelAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> SWORD_CRYSTAL_RESTONIA = ITEMS.register("sword_crystal_restonia", () -> new ItemSwordAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> HOE_CRYSTAL_RESTONIA = ITEMS.register("hoe_crystal_restonia", () -> new ItemHoeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> HELM_CRYSTAL_RESTONIA = ITEMS.register("helm_crystal_restonia", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_CRYSTAL_RESTONIA = ITEMS.register("chest_crystal_restonia", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_CRYSTAL_RESTONIA = ITEMS.register("pants_crystal_restonia", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_CRYSTAL_RESTONIA = ITEMS.register("boots_crystal_restonia", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PAXEL_CRYSTAL_RESTONIA = ITEMS.register("paxel_crystal_restonia", () -> new AllInOneTool(ToolMaterials.RESTONIA));

    public static final RegistryObject<Item> PICKAXE_CRYSTAL_PALIS = ITEMS.register("pickaxe_crystal_palis", () -> new ItemPickaxeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> AXE_CRYSTAL_PALIS = ITEMS.register("axe_crystal_palis", () -> new ItemAxeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> SHOVEL_CRYSTAL_PALIS = ITEMS.register("shovel_crystal_palis", () -> new ItemShovelAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> SWORD_CRYSTAL_PALIS = ITEMS.register("sword_crystal_palis", () -> new ItemSwordAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> HOE_CRYSTAL_PALIS = ITEMS.register("hoe_crystal_palis", () -> new ItemHoeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> HELM_CRYSTAL_PALIS = ITEMS.register("helm_crystal_palis", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_CRYSTAL_PALIS = ITEMS.register("chest_crystal_palis", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_CRYSTAL_PALIS = ITEMS.register("pants_crystal_palis", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_CRYSTAL_PALIS = ITEMS.register("boots_crystal_palis", () -> new ItemArmorAA(ArmorMaterials.PALIS, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PAXEL_CRYSTAL_PALIS = ITEMS.register("paxel_crystal_palis", () -> new AllInOneTool(ToolMaterials.PALIS));

    public static final RegistryObject<Item> PICKAXE_CRYSTAL_DIAMATINE = ITEMS.register("pickaxe_crystal_diamatine", () -> new ItemPickaxeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> AXE_CRYSTAL_DIAMATINE = ITEMS.register("axe_crystal_diamatine", () -> new ItemAxeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> SHOVEL_CRYSTAL_DIAMATINE = ITEMS.register("shovel_crystal_diamatine", () -> new ItemShovelAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> SWORD_CRYSTAL_DIAMATINE = ITEMS.register("sword_crystal_diamatine", () -> new ItemSwordAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> HOE_CRYSTAL_DIAMATINE = ITEMS.register("hoe_crystal_diamatine", () -> new ItemHoeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> HELM_CRYSTAL_DIAMATINE = ITEMS.register("helm_crystal_diamatine", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_CRYSTAL_DIAMATINE = ITEMS.register("chest_crystal_diamatine", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_CRYSTAL_DIAMATINE = ITEMS.register("pants_crystal_diamatine", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_CRYSTAL_DIAMATINE = ITEMS.register("boots_crystal_diamatine", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PAXEL_CRYSTAL_DIAMATINE = ITEMS.register("paxel_crystal_diamatine", () -> new AllInOneTool(ToolMaterials.DIAMATINE));

    public static final RegistryObject<Item> PICKAXE_CRYSTAL_VOID = ITEMS.register("pickaxe_crystal_void", () -> new ItemPickaxeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> AXE_CRYSTAL_VOID = ITEMS.register("axe_crystal_void", () -> new ItemAxeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> SHOVEL_CRYSTAL_VOID = ITEMS.register("shovel_crystal_void", () -> new ItemShovelAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> SWORD_CRYSTAL_VOID = ITEMS.register("sword_crystal_void", () -> new ItemSwordAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> HOE_CRYSTAL_VOID = ITEMS.register("hoe_crystal_void", () -> new ItemHoeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> HELM_CRYSTAL_VOID = ITEMS.register("helm_crystal_void", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_CRYSTAL_VOID = ITEMS.register("chest_crystal_void", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_CRYSTAL_VOID = ITEMS.register("pants_crystal_void", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_CRYSTAL_VOID = ITEMS.register("boots_crystal_void", () -> new ItemArmorAA(ArmorMaterials.VOID, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PAXEL_CRYSTAL_VOID = ITEMS.register("paxel_crystal_void", () -> new AllInOneTool(ToolMaterials.VOID));

    public static final RegistryObject<Item> PICKAXE_CRYSTAL_EMERADIC = ITEMS.register("pickaxe_crystal_emeradic", () -> new ItemPickaxeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> AXE_CRYSTAL_EMERADIC = ITEMS.register("axe_crystal_emeradic", () -> new ItemAxeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> SHOVEL_CRYSTAL_EMERADIC = ITEMS.register("shovel_crystal_emeradic", () -> new ItemShovelAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> SWORD_CRYSTAL_EMERADIC = ITEMS.register("sword_crystal_emeradic", () -> new ItemSwordAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> HOE_CRYSTAL_EMERADIC = ITEMS.register("hoe_crystal_emeradic", () -> new ItemHoeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> HELM_CRYSTAL_EMERADIC = ITEMS.register("helm_crystal_emeradic", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_CRYSTAL_EMERADIC = ITEMS.register("chest_crystal_emeradic", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_CRYSTAL_EMERADIC = ITEMS.register("pants_crystal_emeradic", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_CRYSTAL_EMERADIC = ITEMS.register("boots_crystal_emeradic", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PAXEL_CRYSTAL_EMERADIC = ITEMS.register("paxel_crystal_emeradic", () -> new AllInOneTool(ToolMaterials.EMERADIC));

    public static final RegistryObject<Item> PICKAXE_CRYSTAL_ENORI = ITEMS.register("pickaxe_crystal_enori", () -> new ItemPickaxeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> AXE_CRYSTAL_ENORI = ITEMS.register("axe_crystal_enori", () -> new ItemAxeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> SHOVEL_CRYSTAL_ENORI = ITEMS.register("shovel_crystal_enori", () -> new ItemShovelAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> SWORD_CRYSTAL_ENORI = ITEMS.register("sword_crystal_enori", () -> new ItemSwordAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> HOE_CRYSTAL_ENORI = ITEMS.register("hoe_crystal_enori", () -> new ItemHoeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> HELM_CRYSTAL_ENORI = ITEMS.register("helm_crystal_enori", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> CHEST_CRYSTAL_ENORI = ITEMS.register("chest_crystal_enori", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> PANTS_CRYSTAL_ENORI = ITEMS.register("pants_crystal_enori", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> BOOTS_CRYSTAL_ENORI = ITEMS.register("boots_crystal_enori", () -> new ItemArmorAA(ArmorMaterials.ENORI, EquipmentSlotType.FEET));
    public static final RegistryObject<Item> PAXEL_CRYSTAL_ENORI = ITEMS.register("paxel_crystal_enori", () -> new AllInOneTool(ToolMaterials.ENORI));

    public static Item.Properties defaultProps() {
        return new Item.Properties().tab(ActuallyAdditions.GROUP);
    }

    public static Item.Properties defaultNonStacking() {
        return defaultProps().stacksTo(1);
    }
}
