/*
 * This file ("InitItems.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.base.ItemArmorAA;
import de.ellpeck.actuallyadditions.mod.items.base.ItemHoeAA;
import de.ellpeck.actuallyadditions.mod.items.base.ItemSeed;
import de.ellpeck.actuallyadditions.mod.items.base.ItemSwordAA;
import de.ellpeck.actuallyadditions.mod.items.lens.ItemLens;
import de.ellpeck.actuallyadditions.mod.items.lens.Lenses;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.mod.util.CompatUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class InitItems{

    public static Item itemBooklet;

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemFoods;
    public static Item itemJams;
    public static Item itemKnife;
    public static Item itemCrafterOnAStick;
    public static Item itemDust;
    public static Item itemSolidifiedExperience;
    public static Item itemLeafBlower;
    public static Item itemLeafBlowerAdvanced;

    public static Item itemPotionRing;
    public static Item itemPotionRingAdvanced;

    public static Item itemPickaxeEmerald;
    public static Item itemAxeEmerald;
    public static Item itemShovelEmerald;
    public static Item itemSwordEmerald;
    public static Item itemHoeEmerald;

    public static Item itemHelmEmerald;
    public static Item itemChestEmerald;
    public static Item itemPantsEmerald;
    public static Item itemBootsEmerald;

    public static Item itemPickaxeObsidian;
    public static Item itemAxeObsidian;
    public static Item itemShovelObsidian;
    public static Item itemSwordObsidian;
    public static Item itemHoeObsidian;

    public static Item itemPickaxeQuartz;
    public static Item itemAxeQuartz;
    public static Item itemShovelQuartz;
    public static Item itemSwordQuartz;
    public static Item itemHoeQuartz;

    public static Item itemHelmObsidian;
    public static Item itemChestObsidian;
    public static Item itemPantsObsidian;
    public static Item itemBootsObsidian;

    public static Item itemHelmQuartz;
    public static Item itemChestQuartz;
    public static Item itemPantsQuartz;
    public static Item itemBootsQuartz;

    public static Item itemHairyBall;

    public static Item itemRiceSeed;
    public static Item itemCanolaSeed;
    public static Item itemFlaxSeed;
    public static Item itemCoffeeSeed;

    public static Item itemResonantRice;

    public static Item itemPhantomConnector;

    public static Item itemCoffeeBean;
    public static Item itemCoffee;

    public static Item woodenPaxel;
    public static Item stonePaxel;
    public static Item ironPaxel;
    public static Item diamondPaxel;
    public static Item goldPaxel;
    public static Item emeraldPaxel;
    public static Item obsidianPaxel;
    public static Item quartzPaxel;

    public static Item itemDrill;
    public static Item itemDrillUpgradeSpeed;
    public static Item itemDrillUpgradeSpeedII;
    public static Item itemDrillUpgradeSpeedIII;
    public static Item itemDrillUpgradeSilkTouch;
    public static Item itemDrillUpgradeFortune;
    public static Item itemDrillUpgradeFortuneII;
    public static Item itemDrillUpgradeThreeByThree;
    public static Item itemDrillUpgradeFiveByFive;
    public static Item itemDrillUpgradeBlockPlacing;

    public static Item itemBattery;
    public static Item itemBatteryDouble;
    public static Item itemBatteryTriple;
    public static Item itemBatteryQuadruple;
    public static Item itemBatteryQuintuple;

    public static Item itemTeleStaff;
    public static Item itemWingsOfTheBats;

    public static Item itemGrowthRing;
    public static Item itemMagnetRing;
    public static Item itemWaterRemovalRing;

    public static Item itemChestToCrateUpgrade;
    public static Item itemCrateKeeper;

    public static Item itemSpawnerChanger;

    public static Item itemLaserWrench;
    public static Item itemCrystal;
    public static Item itemColorLens;
    public static Item itemExplosionLens;
    public static Item itemDamageLens;

    public static Item itemPickaxeCrystalRed;
    public static Item itemAxeCrystalRed;
    public static Item itemShovelCrystalRed;
    public static Item itemSwordCrystalRed;
    public static Item itemHoeCrystalRed;
    public static Item itemHelmCrystalRed;
    public static Item itemChestCrystalRed;
    public static Item itemPantsCrystalRed;
    public static Item itemBootsCrystalRed;
    public static Item itemPaxelCrystalRed;

    public static Item itemPickaxeCrystalBlue;
    public static Item itemAxeCrystalBlue;
    public static Item itemShovelCrystalBlue;
    public static Item itemSwordCrystalBlue;
    public static Item itemHoeCrystalBlue;
    public static Item itemHelmCrystalBlue;
    public static Item itemChestCrystalBlue;
    public static Item itemPantsCrystalBlue;
    public static Item itemBootsCrystalBlue;
    public static Item itemPaxelCrystalBlue;

    public static Item itemPickaxeCrystalLightBlue;
    public static Item itemAxeCrystalLightBlue;
    public static Item itemShovelCrystalLightBlue;
    public static Item itemSwordCrystalLightBlue;
    public static Item itemHoeCrystalLightBlue;
    public static Item itemHelmCrystalLightBlue;
    public static Item itemChestCrystalLightBlue;
    public static Item itemPantsCrystalLightBlue;
    public static Item itemBootsCrystalLightBlue;
    public static Item itemPaxelCrystalLightBlue;

    public static Item itemPickaxeCrystalBlack;
    public static Item itemAxeCrystalBlack;
    public static Item itemShovelCrystalBlack;
    public static Item itemSwordCrystalBlack;
    public static Item itemHoeCrystalBlack;
    public static Item itemHelmCrystalBlack;
    public static Item itemChestCrystalBlack;
    public static Item itemPantsCrystalBlack;
    public static Item itemBootsCrystalBlack;
    public static Item itemPaxelCrystalBlack;

    public static Item itemPickaxeCrystalGreen;
    public static Item itemAxeCrystalGreen;
    public static Item itemShovelCrystalGreen;
    public static Item itemSwordCrystalGreen;
    public static Item itemHoeCrystalGreen;
    public static Item itemHelmCrystalGreen;
    public static Item itemChestCrystalGreen;
    public static Item itemPantsCrystalGreen;
    public static Item itemBootsCrystalGreen;
    public static Item itemPaxelCrystalGreen;

    public static Item itemPickaxeCrystalWhite;
    public static Item itemAxeCrystalWhite;
    public static Item itemShovelCrystalWhite;
    public static Item itemSwordCrystalWhite;
    public static Item itemHoeCrystalWhite;
    public static Item itemHelmCrystalWhite;
    public static Item itemChestCrystalWhite;
    public static Item itemPantsCrystalWhite;
    public static Item itemBootsCrystalWhite;
    public static Item itemPaxelCrystalWhite;

    public static Item itemRarmorModuleReconstructor;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Items...");

        itemSpawnerChanger = new ItemSpawnerChanger("itemSpawnerChanger");
        itemMisc = new ItemMisc("itemMisc");
        itemCrateKeeper = new ItemGeneric("itemCrateKeeper");
        itemColorLens = new ItemLens("itemColorLens", ActuallyAdditionsAPI.lensColor);
        itemExplosionLens = new ItemLens("itemExplosionLens", ActuallyAdditionsAPI.lensDetonation);
        itemDamageLens = new ItemLens("itemDamageLens", ActuallyAdditionsAPI.lensDeath);
        itemCrystal = new ItemCrystal("itemCrystal");
        itemLaserWrench = new ItemLaserWrench("itemLaserWrench");
        itemChestToCrateUpgrade = new ItemChestToCrateUpgrade("itemChestToCrateUpgrade");
        itemBooklet = new ItemBooklet("itemBooklet");
        itemGrowthRing = new ItemGrowthRing("itemGrowthRing");
        itemMagnetRing = new ItemMagnetRing("itemSuctionRing");
        itemWaterRemovalRing = new ItemWaterRemovalRing("itemWaterRemovalRing");
        itemHelmEmerald = new ItemArmorAA("itemHelmEmerald", InitArmorMaterials.armorMaterialEmerald, 0, new ItemStack(Items.EMERALD));
        itemChestEmerald = new ItemArmorAA("itemChestEmerald", InitArmorMaterials.armorMaterialEmerald, 1, new ItemStack(Items.EMERALD));
        itemPantsEmerald = new ItemArmorAA("itemPantsEmerald", InitArmorMaterials.armorMaterialEmerald, 2, new ItemStack(Items.EMERALD));
        itemBootsEmerald = new ItemArmorAA("itemBootsEmerald", InitArmorMaterials.armorMaterialEmerald, 3, new ItemStack(Items.EMERALD));
        itemHelmObsidian = new ItemArmorAA("itemHelmObsidian", InitArmorMaterials.armorMaterialObsidian, 0, new ItemStack(Blocks.OBSIDIAN));
        itemChestObsidian = new ItemArmorAA("itemChestObsidian", InitArmorMaterials.armorMaterialObsidian, 1, new ItemStack(Blocks.OBSIDIAN));
        itemPantsObsidian = new ItemArmorAA("itemPantsObsidian", InitArmorMaterials.armorMaterialObsidian, 2, new ItemStack(Blocks.OBSIDIAN));
        itemBootsObsidian = new ItemArmorAA("itemBootsObsidian", InitArmorMaterials.armorMaterialObsidian, 3, new ItemStack(Blocks.OBSIDIAN));
        itemHelmQuartz = new ItemArmorAA("itemHelmQuartz", InitArmorMaterials.armorMaterialQuartz, 0, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));
        itemChestQuartz = new ItemArmorAA("itemChestQuartz", InitArmorMaterials.armorMaterialQuartz, 1, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));
        itemPantsQuartz = new ItemArmorAA("itemPantsQuartz", InitArmorMaterials.armorMaterialQuartz, 2, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));
        itemBootsQuartz = new ItemArmorAA("itemBootsQuartz", InitArmorMaterials.armorMaterialQuartz, 3, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));
        itemTeleStaff = new ItemTeleStaff("itemTeleStaff");
        itemWingsOfTheBats = new ItemWingsOfTheBats("itemWingsOfTheBats");
        itemDrill = new ItemDrill("itemDrill");
        itemBattery = new ItemBattery("itemBattery", 1000000, 5000);
        itemBatteryDouble = new ItemBattery("itemBatteryDouble", 2000000, 10000);
        itemBatteryTriple = new ItemBattery("itemBatteryTriple", 3000000, 15000);
        itemBatteryQuadruple = new ItemBattery("itemBatteryQuadruple", 4000000, 20000);
        itemBatteryQuintuple = new ItemBattery("itemBatteryQuintuple", 5000000, 25000);
        itemDrillUpgradeSpeed = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED, "itemDrillUpgradeSpeed");
        itemDrillUpgradeSpeedII = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_II, "itemDrillUpgradeSpeedII");
        itemDrillUpgradeSpeedIII = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_III, "itemDrillUpgradeSpeedIII");
        itemDrillUpgradeSilkTouch = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SILK_TOUCH, "itemDrillUpgradeSilkTouch");
        itemDrillUpgradeFortune = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE, "itemDrillUpgradeFortune");
        itemDrillUpgradeFortuneII = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE_II, "itemDrillUpgradeFortuneII");
        itemDrillUpgradeThreeByThree = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.THREE_BY_THREE, "itemDrillUpgradeThreeByThree");
        itemDrillUpgradeFiveByFive = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE, "itemDrillUpgradeFiveByFive");
        itemDrillUpgradeBlockPlacing = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.PLACER, "itemDrillUpgradeBlockPlacing");
        itemFertilizer = new ItemFertilizer("itemFertilizer");
        itemCoffee = new ItemCoffee("itemCoffee");
        itemPhantomConnector = new ItemPhantomConnector("itemPhantomConnector");
        itemResonantRice = new ItemResonantRice("itemResonantRice");
        itemFoods = new ItemFoods("itemFood");
        itemJams = new ItemJams("itemJam");
        itemKnife = new ItemKnife("itemKnife");
        itemCrafterOnAStick = new ItemCrafterOnAStick("itemCrafterOnAStick");
        itemDust = new ItemDust("itemDust");
        itemSolidifiedExperience = new ItemSolidifiedExperience("itemSolidifiedExperience");
        itemLeafBlower = new ItemLeafBlower(false, "itemLeafBlower");
        itemLeafBlowerAdvanced = new ItemLeafBlower(true, "itemLeafBlowerAdvanced");
        itemPotionRing = new ItemPotionRing(false, "itemPotionRing");
        itemPotionRingAdvanced = new ItemPotionRing(true, "itemPotionRingAdvanced");
        itemHairyBall = new ItemHairyBall("itemHairyBall");
        itemCoffeeBean = new ItemCoffeeBean("itemCoffeeBeans");
        itemRiceSeed = new ItemSeed("itemRiceSeed", "seedRice", InitBlocks.blockRice, itemFoods, TheFoods.RICE.ordinal());
        CompatUtil.registerMFRSeed(itemRiceSeed);
        itemCanolaSeed = new ItemSeed("itemCanolaSeed", "seedCanola", InitBlocks.blockCanola, itemMisc, TheMiscItems.CANOLA.ordinal());
        CompatUtil.registerMFRSeed(itemCanolaSeed);
        itemFlaxSeed = new ItemSeed("itemFlaxSeed", "seedFlax", InitBlocks.blockFlax, Items.STRING, 0);
        CompatUtil.registerMFRSeed(itemFlaxSeed);
        itemCoffeeSeed = new ItemSeed("itemCoffeeSeed", "seedCoffeeBeans", InitBlocks.blockCoffee, itemCoffeeBean, 0);
        CompatUtil.registerMFRSeed(itemCoffeeSeed);
        itemPickaxeEmerald = new ItemPickaxeAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.EMERALD), "itemPickaxeEmerald", EnumRarity.EPIC);
        itemAxeEmerald = new ItemAxeAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.EMERALD), "itemAxeEmerald", EnumRarity.EPIC);
        itemShovelEmerald = new ItemShovelAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.EMERALD), "itemShovelEmerald", EnumRarity.EPIC);
        itemSwordEmerald = new ItemSwordAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.EMERALD), "itemSwordEmerald", EnumRarity.EPIC);
        itemHoeEmerald = new ItemHoeAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.EMERALD), "itemHoeEmerald", EnumRarity.EPIC);
        itemPickaxeObsidian = new ItemPickaxeAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.OBSIDIAN), "itemPickaxeObsidian", EnumRarity.UNCOMMON);
        itemAxeObsidian = new ItemAxeAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.OBSIDIAN), "itemAxeObsidian", EnumRarity.UNCOMMON);
        itemShovelObsidian = new ItemShovelAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.OBSIDIAN), "itemShovelObsidian", EnumRarity.UNCOMMON);
        itemSwordObsidian = new ItemSwordAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.OBSIDIAN), "itemSwordObsidian", EnumRarity.UNCOMMON);
        itemHoeObsidian = new ItemHoeAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.OBSIDIAN), "itemHoeObsidian", EnumRarity.UNCOMMON);
        itemPickaxeQuartz = new ItemPickaxeAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemPickaxeQuartz", EnumRarity.RARE);
        itemAxeQuartz = new ItemAxeAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemAxeQuartz", EnumRarity.RARE);
        itemShovelQuartz = new ItemShovelAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemShovelQuartz", EnumRarity.RARE);
        itemSwordQuartz = new ItemSwordAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemSwordQuartz", EnumRarity.RARE);
        itemHoeQuartz = new ItemHoeAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemHoeQuartz", EnumRarity.RARE);
        woodenPaxel = new ItemAllToolAA(Item.ToolMaterial.WOOD, "plankWood", "woodenPaxel", EnumRarity.UNCOMMON, 5192733);
        stonePaxel = new ItemAllToolAA(Item.ToolMaterial.STONE, new ItemStack(Blocks.COBBLESTONE), "stonePaxel", EnumRarity.UNCOMMON, 7040621);
        ironPaxel = new ItemAllToolAA(Item.ToolMaterial.IRON, new ItemStack(Items.IRON_INGOT), "ironPaxel", EnumRarity.RARE, 10920613);
        goldPaxel = new ItemAllToolAA(Item.ToolMaterial.GOLD, new ItemStack(Items.GOLD_INGOT), "goldPaxel", EnumRarity.RARE, 16770048);
        diamondPaxel = new ItemAllToolAA(Item.ToolMaterial.DIAMOND, new ItemStack(Items.DIAMOND), "diamondPaxel", EnumRarity.EPIC, 3250376);
        emeraldPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.EMERALD), "emeraldPaxel", EnumRarity.EPIC, 7723338);
        obsidianPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.OBSIDIAN), "obsidianPaxel", EnumRarity.EPIC, 4166);
        quartzPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "quartzPaxel", EnumRarity.RARE, 1710103);

        itemPickaxeCrystalRed = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemPickaxeCrystalRed", Util.CRYSTAL_RED_RARITY);
        itemAxeCrystalRed = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemAxeCrystalRed", Util.CRYSTAL_RED_RARITY);
        itemShovelCrystalRed = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemShovelCrystalRed", Util.CRYSTAL_RED_RARITY);
        itemSwordCrystalRed = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemSwordCrystalRed", Util.CRYSTAL_RED_RARITY);
        itemHoeCrystalRed = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemHoeCrystalRed", Util.CRYSTAL_RED_RARITY);
        itemHelmCrystalRed = new ItemArmorAA("itemHelmCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), Util.CRYSTAL_RED_RARITY);
        itemChestCrystalRed = new ItemArmorAA("itemChestCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), Util.CRYSTAL_RED_RARITY);
        itemPantsCrystalRed = new ItemArmorAA("itemPantsCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), Util.CRYSTAL_RED_RARITY);
        itemBootsCrystalRed = new ItemArmorAA("itemBootsCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), Util.CRYSTAL_RED_RARITY);
        itemPaxelCrystalRed = new ItemAllToolAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemPaxelCrystalRed", Util.CRYSTAL_RED_RARITY, 16711689);

        itemPickaxeCrystalBlue = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemPickaxeCrystalBlue", Util.CRYSTAL_BLUE_RARITY);
        itemAxeCrystalBlue = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemAxeCrystalBlue", Util.CRYSTAL_BLUE_RARITY);
        itemShovelCrystalBlue = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemShovelCrystalBlue", Util.CRYSTAL_BLUE_RARITY);
        itemSwordCrystalBlue = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemSwordCrystalBlue", Util.CRYSTAL_BLUE_RARITY);
        itemHoeCrystalBlue = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemHoeCrystalBlue", Util.CRYSTAL_BLUE_RARITY);
        itemHelmCrystalBlue = new ItemArmorAA("itemHelmCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), Util.CRYSTAL_BLUE_RARITY);
        itemChestCrystalBlue = new ItemArmorAA("itemChestCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), Util.CRYSTAL_BLUE_RARITY);
        itemPantsCrystalBlue = new ItemArmorAA("itemPantsCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), Util.CRYSTAL_BLUE_RARITY);
        itemBootsCrystalBlue = new ItemArmorAA("itemBootsCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), Util.CRYSTAL_BLUE_RARITY);
        itemPaxelCrystalBlue = new ItemAllToolAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemPaxelCrystalBlue", Util.CRYSTAL_BLUE_RARITY, 3014911);

        itemPickaxeCrystalLightBlue = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemPickaxeCrystalLightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemAxeCrystalLightBlue = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemAxeCrystalLightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemShovelCrystalLightBlue = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemShovelCrystalLightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemSwordCrystalLightBlue = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemSwordCrystalLightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemHoeCrystalLightBlue = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemHoeCrystalLightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemHelmCrystalLightBlue = new ItemArmorAA("itemHelmCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemChestCrystalLightBlue = new ItemArmorAA("itemChestCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemPantsCrystalLightBlue = new ItemArmorAA("itemPantsCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemBootsCrystalLightBlue = new ItemArmorAA("itemBootsCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), Util.CRYSTAL_LIGHT_BLUE_RARITY);
        itemPaxelCrystalLightBlue = new ItemAllToolAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemPaxelCrystalLightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY, 4093108);

        itemPickaxeCrystalBlack = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemPickaxeCrystalBlack", Util.CRYSTAL_BLACK_RARITY);
        itemAxeCrystalBlack = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemAxeCrystalBlack", Util.CRYSTAL_BLACK_RARITY);
        itemShovelCrystalBlack = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemShovelCrystalBlack", Util.CRYSTAL_BLACK_RARITY);
        itemSwordCrystalBlack = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemSwordCrystalBlack", Util.CRYSTAL_BLACK_RARITY);
        itemHoeCrystalBlack = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemHoeCrystalBlack", Util.CRYSTAL_BLACK_RARITY);
        itemHelmCrystalBlack = new ItemArmorAA("itemHelmCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), Util.CRYSTAL_BLACK_RARITY);
        itemChestCrystalBlack = new ItemArmorAA("itemChestCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), Util.CRYSTAL_BLACK_RARITY);
        itemPantsCrystalBlack = new ItemArmorAA("itemPantsCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), Util.CRYSTAL_BLACK_RARITY);
        itemBootsCrystalBlack = new ItemArmorAA("itemBootsCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), Util.CRYSTAL_BLACK_RARITY);
        itemPaxelCrystalBlack = new ItemAllToolAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemPaxelCrystalBlack", Util.CRYSTAL_BLACK_RARITY, 2631982);

        itemPickaxeCrystalGreen = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemPickaxeCrystalGreen", Util.CRYSTAL_GREEN_RARITY);
        itemAxeCrystalGreen = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemAxeCrystalGreen", Util.CRYSTAL_GREEN_RARITY);
        itemShovelCrystalGreen = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemShovelCrystalGreen", Util.CRYSTAL_GREEN_RARITY);
        itemSwordCrystalGreen = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemSwordCrystalGreen", Util.CRYSTAL_GREEN_RARITY);
        itemHoeCrystalGreen = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemHoeCrystalGreen", Util.CRYSTAL_GREEN_RARITY);
        itemHelmCrystalGreen = new ItemArmorAA("itemHelmCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), Util.CRYSTAL_GREEN_RARITY);
        itemChestCrystalGreen = new ItemArmorAA("itemChestCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), Util.CRYSTAL_GREEN_RARITY);
        itemPantsCrystalGreen = new ItemArmorAA("itemPantsCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), Util.CRYSTAL_GREEN_RARITY);
        itemBootsCrystalGreen = new ItemArmorAA("itemBootsCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), Util.CRYSTAL_GREEN_RARITY);
        itemPaxelCrystalGreen = new ItemAllToolAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemPaxelCrystalGreen", Util.CRYSTAL_GREEN_RARITY, 46848);

        itemPickaxeCrystalWhite = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemPickaxeCrystalWhite", Util.CRYSTAL_WHITE_RARITY);
        itemAxeCrystalWhite = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemAxeCrystalWhite", Util.CRYSTAL_WHITE_RARITY);
        itemShovelCrystalWhite = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemShovelCrystalWhite", Util.CRYSTAL_WHITE_RARITY);
        itemSwordCrystalWhite = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemSwordCrystalWhite", Util.CRYSTAL_WHITE_RARITY);
        itemHoeCrystalWhite = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemHoeCrystalWhite", Util.CRYSTAL_WHITE_RARITY);
        itemHelmCrystalWhite = new ItemArmorAA("itemHelmCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), Util.CRYSTAL_WHITE_RARITY);
        itemChestCrystalWhite = new ItemArmorAA("itemChestCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), Util.CRYSTAL_WHITE_RARITY);
        itemPantsCrystalWhite = new ItemArmorAA("itemPantsCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), Util.CRYSTAL_WHITE_RARITY);
        itemBootsCrystalWhite = new ItemArmorAA("itemBootsCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), Util.CRYSTAL_WHITE_RARITY);
        itemPaxelCrystalWhite = new ItemAllToolAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemPaxelCrystalWhite", Util.CRYSTAL_WHITE_RARITY, 14606302);

        if(Loader.isModLoaded("rarmor")){
            itemRarmorModuleReconstructor = new ItemRarmorModuleReconstructor("itemRarmorModuleReconstructor");
        }
    }
}
