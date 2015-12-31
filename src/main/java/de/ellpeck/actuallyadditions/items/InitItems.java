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
import de.ellpeck.actuallyadditions.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.util.CompatUtil;
import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.init.Blocks;
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

    public static Item itemPickaxeCrystalRed;
    public static Item itemAxeCrystalRed;
    public static Item itemShovelCrystalRed;
    public static Item itemSwordCrystalRed;
    public static Item itemHoeCrystalRed;
    public static Item itemHelmCrystalRed;
    public static Item itemChestCrystalRed;
    public static Item itemPantsCrystalRed;
    public static Item itemBootsCrystalRed;

    public static Item itemPickaxeCrystalBlue;
    public static Item itemAxeCrystalBlue;
    public static Item itemShovelCrystalBlue;
    public static Item itemSwordCrystalBlue;
    public static Item itemHoeCrystalBlue;
    public static Item itemHelmCrystalBlue;
    public static Item itemChestCrystalBlue;
    public static Item itemPantsCrystalBlue;
    public static Item itemBootsCrystalBlue;

    public static Item itemPickaxeCrystalLightBlue;
    public static Item itemAxeCrystalLightBlue;
    public static Item itemShovelCrystalLightBlue;
    public static Item itemSwordCrystalLightBlue;
    public static Item itemHoeCrystalLightBlue;
    public static Item itemHelmCrystalLightBlue;
    public static Item itemChestCrystalLightBlue;
    public static Item itemPantsCrystalLightBlue;
    public static Item itemBootsCrystalLightBlue;
    
    public static Item itemPickaxeCrystalBlack;
    public static Item itemAxeCrystalBlack;
    public static Item itemShovelCrystalBlack;
    public static Item itemSwordCrystalBlack;
    public static Item itemHoeCrystalBlack;
    public static Item itemHelmCrystalBlack;
    public static Item itemChestCrystalBlack;
    public static Item itemPantsCrystalBlack;
    public static Item itemBootsCrystalBlack;

    public static Item itemPickaxeCrystalGreen;
    public static Item itemAxeCrystalGreen;
    public static Item itemShovelCrystalGreen;
    public static Item itemSwordCrystalGreen;
    public static Item itemHoeCrystalGreen;
    public static Item itemHelmCrystalGreen;
    public static Item itemChestCrystalGreen;
    public static Item itemPantsCrystalGreen;
    public static Item itemBootsCrystalGreen;

    public static Item itemPickaxeCrystalWhite;
    public static Item itemAxeCrystalWhite;
    public static Item itemShovelCrystalWhite;
    public static Item itemSwordCrystalWhite;
    public static Item itemHoeCrystalWhite;
    public static Item itemHelmCrystalWhite;
    public static Item itemChestCrystalWhite;
    public static Item itemPantsCrystalWhite;
    public static Item itemBootsCrystalWhite;
    
    public static void init(){
        ModUtil.LOGGER.info("Initializing Items...");

        itemMisc = new ItemMisc("itemMisc");
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
        itemHelmEmerald = new ItemArmorAA("itemHelmEmerald", InitArmorMaterials.armorMaterialEmerald, 0, new ItemStack(Items.emerald), "armorEmerald");
        itemChestEmerald = new ItemArmorAA("itemChestEmerald", InitArmorMaterials.armorMaterialEmerald, 1, new ItemStack(Items.emerald), "armorEmerald");
        itemPantsEmerald = new ItemArmorAA("itemPantsEmerald", InitArmorMaterials.armorMaterialEmerald, 2, new ItemStack(Items.emerald), "armorEmerald");
        itemBootsEmerald = new ItemArmorAA("itemBootsEmerald", InitArmorMaterials.armorMaterialEmerald, 3, new ItemStack(Items.emerald), "armorEmerald");
        itemHelmObsidian = new ItemArmorAA("itemHelmObsidian", InitArmorMaterials.armorMaterialObsidian, 0, new ItemStack(Blocks.obsidian), "armorObsidian");
        itemChestObsidian = new ItemArmorAA("itemChestObsidian", InitArmorMaterials.armorMaterialObsidian, 1, new ItemStack(Blocks.obsidian), "armorObsidian");
        itemPantsObsidian = new ItemArmorAA("itemPantsObsidian", InitArmorMaterials.armorMaterialObsidian, 2, new ItemStack(Blocks.obsidian), "armorObsidian");
        itemBootsObsidian = new ItemArmorAA("itemBootsObsidian", InitArmorMaterials.armorMaterialObsidian, 3, new ItemStack(Blocks.obsidian), "armorObsidian");
        itemHelmQuartz = new ItemArmorAA("itemHelmQuartz", InitArmorMaterials.armorMaterialQuartz, 0, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "armorQuartz");
        itemChestQuartz = new ItemArmorAA("itemChestQuartz", InitArmorMaterials.armorMaterialQuartz, 1, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "armorQuartz");
        itemPantsQuartz = new ItemArmorAA("itemPantsQuartz", InitArmorMaterials.armorMaterialQuartz, 2, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "armorQuartz");
        itemBootsQuartz = new ItemArmorAA("itemBootsQuartz", InitArmorMaterials.armorMaterialQuartz, 3, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "armorQuartz");
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
        itemPickaxeEmerald = new ItemPickaxeAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemPickaxeEmerald", EnumRarity.epic);
        itemAxeEmerald = new ItemAxeAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemAxeEmerald", EnumRarity.epic);
        itemShovelEmerald = new ItemShovelAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemShovelEmerald", EnumRarity.epic);
        itemSwordEmerald = new ItemSwordAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemSwordEmerald", EnumRarity.epic);
        itemHoeEmerald = new ItemHoeAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemHoeEmerald", EnumRarity.epic);
        itemPickaxeObsidian = new ItemPickaxeAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemHoeObsidian", EnumRarity.uncommon);
        itemPickaxeQuartz = new ItemPickaxeAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemPickaxeQuartz", EnumRarity.rare);
        itemAxeQuartz = new ItemAxeAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemAxeQuartz", EnumRarity.rare);
        itemShovelQuartz = new ItemShovelAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemShovelQuartz", EnumRarity.rare);
        itemSwordQuartz = new ItemSwordAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemSwordQuartz", EnumRarity.rare);
        itemHoeQuartz = new ItemHoeAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "itemHoeQuartz", EnumRarity.rare);
        woodenPaxel = new ItemAllToolAA(Item.ToolMaterial.WOOD, "plankWood", "woodenPaxel", EnumRarity.uncommon, 5192733);
        stonePaxel = new ItemAllToolAA(Item.ToolMaterial.STONE, new ItemStack(Blocks.cobblestone), "stonePaxel", EnumRarity.uncommon, 7040621);
        ironPaxel = new ItemAllToolAA(Item.ToolMaterial.IRON, new ItemStack(Items.iron_ingot), "ironPaxel", EnumRarity.rare, 10920613);
        goldPaxel = new ItemAllToolAA(Item.ToolMaterial.GOLD, new ItemStack(Items.gold_ingot), "goldPaxel", EnumRarity.rare, 16770048);
        diamondPaxel = new ItemAllToolAA(Item.ToolMaterial.EMERALD, new ItemStack(Items.diamond), "diamondPaxel", EnumRarity.epic, 3250376);
        emeraldPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "emeraldPaxel", EnumRarity.epic, 7723338);
        obsidianPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "obsidianPaxel", EnumRarity.epic, 4166);
        quartzPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialQuartz, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), "quartzPaxel", EnumRarity.rare, 1710103);

        itemPickaxeCrystalRed = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemPickaxeCrystalRed", EnumRarity.uncommon);
        itemAxeCrystalRed = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemAxeCrystalRed", EnumRarity.uncommon);
        itemShovelCrystalRed = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemShovelCrystalRed", EnumRarity.uncommon);
        itemSwordCrystalRed = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemSwordCrystalRed", EnumRarity.uncommon);
        itemHoeCrystalRed = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalRed, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "itemHoeCrystalRed", EnumRarity.uncommon);
        itemHelmCrystalRed = new ItemArmorAA("itemHelmCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "armorCrystalRed");
        itemChestCrystalRed = new ItemArmorAA("itemChestCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "armorCrystalRed");
        itemPantsCrystalRed = new ItemArmorAA("itemPantsCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "armorCrystalRed");
        itemBootsCrystalRed = new ItemArmorAA("itemBootsCrystalRed", InitArmorMaterials.armorMaterialCrystalRed, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), "armorCrystalRed");
        
        itemPickaxeCrystalBlue = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemPickaxeCrystalBlue", EnumRarity.uncommon);
        itemAxeCrystalBlue = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemAxeCrystalBlue", EnumRarity.uncommon);
        itemShovelCrystalBlue = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemShovelCrystalBlue", EnumRarity.uncommon);
        itemSwordCrystalBlue = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemSwordCrystalBlue", EnumRarity.uncommon);
        itemHoeCrystalBlue = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "itemHoeCrystalBlue", EnumRarity.uncommon);
        itemHelmCrystalBlue = new ItemArmorAA("itemHelmCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "armorCrystalBlue");
        itemChestCrystalBlue = new ItemArmorAA("itemChestCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "armorCrystalBlue");
        itemPantsCrystalBlue = new ItemArmorAA("itemPantsCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "armorCrystalBlue");
        itemBootsCrystalBlue = new ItemArmorAA("itemBootsCrystalBlue", InitArmorMaterials.armorMaterialCrystalBlue, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), "armorCrystalBlue");

        itemPickaxeCrystalLightBlue = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemPickaxeCrystalLightBlue", EnumRarity.uncommon);
        itemAxeCrystalLightBlue = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemAxeCrystalLightBlue", EnumRarity.uncommon);
        itemShovelCrystalLightBlue = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemShovelCrystalLightBlue", EnumRarity.uncommon);
        itemSwordCrystalLightBlue = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemSwordCrystalLightBlue", EnumRarity.uncommon);
        itemHoeCrystalLightBlue = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalLightBlue, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "itemHoeCrystalLightBlue", EnumRarity.uncommon);
        itemHelmCrystalLightBlue = new ItemArmorAA("itemHelmCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "armorCrystalLightBlue");
        itemChestCrystalLightBlue = new ItemArmorAA("itemChestCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "armorCrystalLightBlue");
        itemPantsCrystalLightBlue = new ItemArmorAA("itemPantsCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "armorCrystalLightBlue");
        itemBootsCrystalLightBlue = new ItemArmorAA("itemBootsCrystalLightBlue", InitArmorMaterials.armorMaterialCrystalLightBlue, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), "armorCrystalLightBlue");

        itemPickaxeCrystalBlack = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemPickaxeCrystalBlack", EnumRarity.uncommon);
        itemAxeCrystalBlack = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemAxeCrystalBlack", EnumRarity.uncommon);
        itemShovelCrystalBlack = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemShovelCrystalBlack", EnumRarity.uncommon);
        itemSwordCrystalBlack = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemSwordCrystalBlack", EnumRarity.uncommon);
        itemHoeCrystalBlack = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalBlack, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "itemHoeCrystalBlack", EnumRarity.uncommon);
        itemHelmCrystalBlack = new ItemArmorAA("itemHelmCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "armorCrystalBlack");
        itemChestCrystalBlack = new ItemArmorAA("itemChestCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "armorCrystalBlack");
        itemPantsCrystalBlack = new ItemArmorAA("itemPantsCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "armorCrystalBlack");
        itemBootsCrystalBlack = new ItemArmorAA("itemBootsCrystalBlack", InitArmorMaterials.armorMaterialCrystalBlack, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), "armorCrystalBlack");

        itemPickaxeCrystalGreen = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemPickaxeCrystalGreen", EnumRarity.uncommon);
        itemAxeCrystalGreen = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemAxeCrystalGreen", EnumRarity.uncommon);
        itemShovelCrystalGreen = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemShovelCrystalGreen", EnumRarity.uncommon);
        itemSwordCrystalGreen = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemSwordCrystalGreen", EnumRarity.uncommon);
        itemHoeCrystalGreen = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalGreen, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "itemHoeCrystalGreen", EnumRarity.uncommon);
        itemHelmCrystalGreen = new ItemArmorAA("itemHelmCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "armorCrystalGreen");
        itemChestCrystalGreen = new ItemArmorAA("itemChestCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "armorCrystalGreen");
        itemPantsCrystalGreen = new ItemArmorAA("itemPantsCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "armorCrystalGreen");
        itemBootsCrystalGreen = new ItemArmorAA("itemBootsCrystalGreen", InitArmorMaterials.armorMaterialCrystalGreen, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), "armorCrystalGreen");

        itemPickaxeCrystalWhite = new ItemPickaxeAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemPickaxeCrystalWhite", EnumRarity.uncommon);
        itemAxeCrystalWhite = new ItemAxeAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemAxeCrystalWhite", EnumRarity.uncommon);
        itemShovelCrystalWhite = new ItemShovelAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemShovelCrystalWhite", EnumRarity.uncommon);
        itemSwordCrystalWhite = new ItemSwordAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemSwordCrystalWhite", EnumRarity.uncommon);
        itemHoeCrystalWhite = new ItemHoeAA(InitToolMaterials.toolMaterialCrystalWhite, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "itemHoeCrystalWhite", EnumRarity.uncommon);
        itemHelmCrystalWhite = new ItemArmorAA("itemHelmCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 0, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "armorCrystalWhite");
        itemChestCrystalWhite = new ItemArmorAA("itemChestCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 1, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "armorCrystalWhite");
        itemPantsCrystalWhite = new ItemArmorAA("itemPantsCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "armorCrystalWhite");
        itemBootsCrystalWhite = new ItemArmorAA("itemBootsCrystalWhite", InitArmorMaterials.armorMaterialCrystalWhite, 3, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), "armorCrystalWhite");

    }
}
