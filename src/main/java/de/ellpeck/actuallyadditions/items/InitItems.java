/*
 * This file ("InitItems.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items;

import de.ellpeck.actuallyadditions.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.items.base.*;
import de.ellpeck.actuallyadditions.items.lens.ItemLens;
import de.ellpeck.actuallyadditions.items.lens.Lenses;
import de.ellpeck.actuallyadditions.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.util.CompatUtil;
import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

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
    public static Item itemBucketOil;
    public static Item itemBucketCanolaOil;

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

    public static Item itemLaserWrench;
    public static Item itemCrystal;
    public static Item itemColorLens;
    public static Item itemExplosionLens;
    public static Item itemDamageLens;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Items...");

        itemCrateKeeper = new ItemGeneric("itemCrateKeeper");
        itemColorLens = new ItemLens("itemColorLens", Lenses.LENS_COLOR);
        itemExplosionLens = new ItemLens("itemExplosionLens", Lenses.LENS_DETONATION);
        itemDamageLens = new ItemLens("itemDamageLens", Lenses.LENS_DEATH);
        itemCrystal = new ItemCrystal("itemCrystal");
        itemLaserWrench = new ItemLaserWrench("itemLaserWrench");
        itemChestToCrateUpgrade = new ItemChestToCrateUpgrade("itemChestToCrateUpgrade");
        itemBooklet = new ItemBooklet("itemBooklet");
        itemGrowthRing = new ItemGrowthRing("itemGrowthRing");
        itemMagnetRing = new ItemMagnetRing("itemSuctionRing");
        itemWaterRemovalRing = new ItemWaterRemovalRing("itemWaterRemovalRing");
        itemHelmEmerald = new ItemArmorAA("itemHelmEmerald", InitArmorMaterials.armorMaterialEmerald, 0, "gemEmerald", "armorEmerald");
        itemChestEmerald = new ItemArmorAA("itemChestEmerald", InitArmorMaterials.armorMaterialEmerald, 1, "gemEmerald", "armorEmerald");
        itemPantsEmerald = new ItemArmorAA("itemPantsEmerald", InitArmorMaterials.armorMaterialEmerald, 2, "gemEmerald", "armorEmerald");
        itemBootsEmerald = new ItemArmorAA("itemBootsEmerald", InitArmorMaterials.armorMaterialEmerald, 3, "gemEmerald", "armorEmerald");
        itemHelmObsidian = new ItemArmorAA("itemHelmObsidian", InitArmorMaterials.armorMaterialObsidian, 0, "obsidian", "armorObsidian");
        itemChestObsidian = new ItemArmorAA("itemChestObsidian", InitArmorMaterials.armorMaterialObsidian, 1, "obsidian", "armorObsidian");
        itemPantsObsidian = new ItemArmorAA("itemPantsObsidian", InitArmorMaterials.armorMaterialObsidian, 2, "obsidian", "armorObsidian");
        itemBootsObsidian = new ItemArmorAA("itemBootsObsidian", InitArmorMaterials.armorMaterialObsidian, 3, "obsidian", "armorObsidian");
        itemHelmQuartz = new ItemArmorAA("itemHelmQuartz", InitArmorMaterials.armorMaterialQuartz, 0, "gemQuartzBlack", "armorQuartz");
        itemChestQuartz = new ItemArmorAA("itemChestQuartz", InitArmorMaterials.armorMaterialQuartz, 1, "gemQuartzBlack", "armorQuartz");
        itemPantsQuartz = new ItemArmorAA("itemPantsQuartz", InitArmorMaterials.armorMaterialQuartz, 2, "gemQuartzBlack", "armorQuartz");
        itemBootsQuartz = new ItemArmorAA("itemBootsQuartz", InitArmorMaterials.armorMaterialQuartz, 3, "gemQuartzBlack", "armorQuartz");
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
        itemBucketOil = new ItemBucketAA(InitBlocks.blockOil, "itemBucketOil");
        FluidContainerRegistry.registerFluidContainer(InitBlocks.fluidOil, new ItemStack(itemBucketOil), FluidContainerRegistry.EMPTY_BUCKET);
        itemBucketCanolaOil = new ItemBucketAA(InitBlocks.blockCanolaOil, "itemBucketCanolaOil");
        FluidContainerRegistry.registerFluidContainer(InitBlocks.fluidCanolaOil, new ItemStack(itemBucketCanolaOil), FluidContainerRegistry.EMPTY_BUCKET);
        itemFertilizer = new ItemFertilizer("itemFertilizer");
        itemCoffee = new ItemCoffee("itemCoffee");
        itemPhantomConnector = new ItemPhantomConnector("itemPhantomConnector");
        itemResonantRice = new ItemResonantRice("itemResonantRice");
        itemMisc = new ItemMisc("itemMisc");
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
        itemFlaxSeed = new ItemSeed("itemFlaxSeed", "seedFlax", InitBlocks.blockFlax, Items.string, 0);
        CompatUtil.registerMFRSeed(itemFlaxSeed);
        itemCoffeeSeed = new ItemSeed("itemCoffeeSeed", "seedCoffeeBeans", InitBlocks.blockCoffee, itemCoffeeBean, 0);
        CompatUtil.registerMFRSeed(itemCoffeeSeed);
        itemPickaxeEmerald = new ItemPickaxeAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemPickaxeEmerald", EnumRarity.epic);
        itemAxeEmerald = new ItemAxeAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemAxeEmerald", EnumRarity.epic);
        itemShovelEmerald = new ItemShovelAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemShovelEmerald", EnumRarity.epic);
        itemSwordEmerald = new ItemSwordAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemSwordEmerald", EnumRarity.epic);
        itemHoeEmerald = new ItemHoeAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemHoeEmerald", EnumRarity.epic);
        itemPickaxeObsidian = new ItemPickaxeAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemHoeObsidian", EnumRarity.uncommon);
        itemPickaxeQuartz = new ItemPickaxeAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemPickaxeQuartz", EnumRarity.rare);
        itemAxeQuartz = new ItemAxeAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemAxeQuartz", EnumRarity.rare);
        itemShovelQuartz = new ItemShovelAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemShovelQuartz", EnumRarity.rare);
        itemSwordQuartz = new ItemSwordAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemSwordQuartz", EnumRarity.rare);
        itemHoeQuartz = new ItemHoeAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemHoeQuartz", EnumRarity.rare);
        woodenPaxel = new ItemAllToolAA(Item.ToolMaterial.WOOD, "plankWood", "woodenPaxel", EnumRarity.uncommon, 5192733);
        stonePaxel = new ItemAllToolAA(Item.ToolMaterial.STONE, "stone", "stonePaxel", EnumRarity.uncommon, 7040621);
        ironPaxel = new ItemAllToolAA(Item.ToolMaterial.IRON, "ingotIron", "ironPaxel", EnumRarity.rare, 10920613);
        goldPaxel = new ItemAllToolAA(Item.ToolMaterial.GOLD, "ingotGold", "goldPaxel", EnumRarity.rare, 16770048);
        diamondPaxel = new ItemAllToolAA(Item.ToolMaterial.EMERALD, "gemDiamond", "diamondPaxel", EnumRarity.epic, 3250376);
        emeraldPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "emeraldPaxel", EnumRarity.epic, 7723338);
        obsidianPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "obsidianPaxel", EnumRarity.epic, 4166);
        quartzPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "quartzPaxel", EnumRarity.rare, 1710103);
    }
}
