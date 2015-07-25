package ellpeck.actuallyadditions.items;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.tools.*;
import ellpeck.actuallyadditions.material.InitArmorMaterials;
import ellpeck.actuallyadditions.material.InitToolMaterials;
import ellpeck.actuallyadditions.util.CompatUtil;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class InitItems{

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemFoods;
    public static Item itemJams;
    public static Item itemKnife;
    public static Item itemCrafterOnAStick;
    public static Item itemDust;
    public static Item itemSpecialDrop;
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

    public static void init(){
        ModUtil.LOGGER.info("Initializing Items...");

        itemGrowthRing = new ItemGrowthRing();
        ItemUtil.register(itemGrowthRing);

        itemHelmEmerald = new ItemArmorAA("itemHelmEmerald", InitArmorMaterials.armorMaterialEmerald, 0, "gemEmerald", "armorEmerald");
        itemChestEmerald = new ItemArmorAA("itemChestEmerald", InitArmorMaterials.armorMaterialEmerald, 1, "gemEmerald", "armorEmerald");
        itemPantsEmerald = new ItemArmorAA("itemPantsEmerald", InitArmorMaterials.armorMaterialEmerald, 2, "gemEmerald", "armorEmerald");
        itemBootsEmerald = new ItemArmorAA("itemBootsEmerald", InitArmorMaterials.armorMaterialEmerald, 3, "gemEmerald", "armorEmerald");
        ItemUtil.registerItems(new Item[]{itemHelmEmerald, itemChestEmerald, itemPantsEmerald, itemBootsEmerald});

        itemHelmObsidian = new ItemArmorAA("itemHelmObsidian", InitArmorMaterials.armorMaterialObsidian, 0, "obsidian", "armorObsidian");
        itemChestObsidian = new ItemArmorAA("itemChestObsidian", InitArmorMaterials.armorMaterialObsidian, 1, "obsidian", "armorObsidian");
        itemPantsObsidian = new ItemArmorAA("itemPantsObsidian", InitArmorMaterials.armorMaterialObsidian, 2, "obsidian", "armorObsidian");
        itemBootsObsidian = new ItemArmorAA("itemBootsObsidian", InitArmorMaterials.armorMaterialObsidian, 3, "obsidian", "armorObsidian");
        ItemUtil.registerItems(new Item[]{itemHelmObsidian, itemChestObsidian, itemPantsObsidian, itemBootsObsidian});

        itemHelmQuartz = new ItemArmorAA("itemHelmQuartz", InitArmorMaterials.armorMaterialQuartz, 0, "gemQuartzBlack", "armorQuartz");
        itemChestQuartz = new ItemArmorAA("itemChestQuartz", InitArmorMaterials.armorMaterialQuartz, 1, "gemQuartzBlack", "armorQuartz");
        itemPantsQuartz = new ItemArmorAA("itemPantsQuartz", InitArmorMaterials.armorMaterialQuartz, 2, "gemQuartzBlack", "armorQuartz");
        itemBootsQuartz = new ItemArmorAA("itemBootsQuartz", InitArmorMaterials.armorMaterialQuartz, 3, "gemQuartzBlack", "armorQuartz");
        ItemUtil.registerItems(new Item[]{itemHelmQuartz, itemChestQuartz, itemPantsQuartz, itemBootsQuartz});

        itemTeleStaff = new ItemTeleStaff();
        ItemUtil.register(itemTeleStaff);

        itemWingsOfTheBats = new ItemWingsOfTheBats();
        ItemUtil.register(itemWingsOfTheBats);

        itemDrill = new ItemDrill();
        ItemUtil.register(itemDrill);

        itemBattery = new ItemBattery("itemBattery", 1000000, 5000);
        itemBatteryDouble = new ItemBattery("itemBatteryDouble", 2000000, 10000);
        itemBatteryTriple = new ItemBattery("itemBatteryTriple", 3000000, 15000);
        itemBatteryQuadruple = new ItemBattery("itemBatteryQuadruple", 4000000, 20000);
        itemBatteryQuintuple = new ItemBattery("itemBatteryQuintuple", 5000000, 25000);
        ItemUtil.registerItems(new Item[]{itemBattery, itemBatteryDouble, itemBatteryTriple, itemBatteryQuadruple, itemBatteryQuintuple});

        itemDrillUpgradeSpeed = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED, "itemDrillUpgradeSpeed");
        itemDrillUpgradeSpeedII = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_II, "itemDrillUpgradeSpeedII");
        itemDrillUpgradeSpeedIII = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_III, "itemDrillUpgradeSpeedIII");
        itemDrillUpgradeSilkTouch = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SILK_TOUCH, "itemDrillUpgradeSilkTouch");
        itemDrillUpgradeFortune = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE, "itemDrillUpgradeFortune");
        itemDrillUpgradeFortuneII = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE_II, "itemDrillUpgradeFortuneII");
        itemDrillUpgradeThreeByThree = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.THREE_BY_THREE, "itemDrillUpgradeThreeByThree");
        itemDrillUpgradeFiveByFive = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE, "itemDrillUpgradeFiveByFive");
        itemDrillUpgradeBlockPlacing = new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.PLACER, "itemDrillUpgradeBlockPlacing");
        ItemUtil.registerItems(new Item[]{itemDrillUpgradeSpeed, itemDrillUpgradeSpeedII, itemDrillUpgradeSpeedIII, itemDrillUpgradeSilkTouch, itemDrillUpgradeFortune, itemDrillUpgradeFortuneII, itemDrillUpgradeThreeByThree, itemDrillUpgradeFiveByFive, itemDrillUpgradeBlockPlacing});

        itemBucketOil = new ItemBucketAA(InitBlocks.blockOil, "itemBucketOil");
        ItemUtil.register(itemBucketOil);
        FluidContainerRegistry.registerFluidContainer(InitBlocks.fluidOil, new ItemStack(itemBucketOil), FluidContainerRegistry.EMPTY_BUCKET);

        itemBucketCanolaOil = new ItemBucketAA(InitBlocks.blockCanolaOil, "itemBucketCanolaOil");
        ItemUtil.register(itemBucketCanolaOil);
        FluidContainerRegistry.registerFluidContainer(InitBlocks.fluidCanolaOil, new ItemStack(itemBucketCanolaOil), FluidContainerRegistry.EMPTY_BUCKET);

        itemFertilizer = new ItemFertilizer();
        ItemUtil.register(itemFertilizer);

        itemCoffee = new ItemCoffee();
        ItemUtil.register(itemCoffee);

        itemPhantomConnector = new ItemPhantomConnector();
        ItemUtil.register(itemPhantomConnector);

        itemResonantRice = new ItemResonantRice();
        ItemUtil.register(itemResonantRice);

        itemMisc = new ItemMisc();
        ItemUtil.register(itemMisc);

        itemFoods = new ItemFoods();
        ItemUtil.register(itemFoods);

        itemJams = new ItemJams();
        ItemUtil.register(itemJams);

        itemKnife = new ItemKnife();
        ItemUtil.register(itemKnife);

        itemCrafterOnAStick = new ItemCrafterOnAStick();
        ItemUtil.register(itemCrafterOnAStick);

        itemDust = new ItemDust();
        ItemUtil.register(itemDust);

        itemSpecialDrop = new ItemSpecialDrop();
        ItemUtil.register(itemSpecialDrop);

        itemLeafBlower = new ItemLeafBlower(false);
        ItemUtil.register(itemLeafBlower);

        itemLeafBlowerAdvanced = new ItemLeafBlower(true);
        ItemUtil.register(itemLeafBlowerAdvanced);

        itemPotionRing = new ItemPotionRing(false);
        ItemUtil.register(itemPotionRing);

        itemPotionRingAdvanced = new ItemPotionRing(true);
        ItemUtil.register(itemPotionRingAdvanced);

        itemHairyBall = new ItemHairyBall();
        ItemUtil.register(itemHairyBall);

        itemCoffeeBean = new ItemCoffeeBean();
        ItemUtil.register(itemCoffeeBean);

        itemRiceSeed = new ItemSeed("itemRiceSeed", "seedRice", InitBlocks.blockRice, itemFoods, TheFoods.RICE.ordinal());
        ItemUtil.register(itemRiceSeed);
        CompatUtil.registerMFRSeed(itemRiceSeed);

        itemCanolaSeed = new ItemSeed("itemCanolaSeed", "seedCanola", InitBlocks.blockCanola, itemMisc, TheMiscItems.CANOLA.ordinal());
        ItemUtil.register(itemCanolaSeed);
        CompatUtil.registerMFRSeed(itemCanolaSeed);

        itemFlaxSeed = new ItemSeed("itemFlaxSeed", "seedFlax", InitBlocks.blockFlax, Items.string, 0);
        ItemUtil.register(itemFlaxSeed);
        CompatUtil.registerMFRSeed(itemFlaxSeed);

        itemCoffeeSeed = new ItemSeed("itemCoffeeSeed", "seedCoffeeBeans", InitBlocks.blockCoffee, itemCoffeeBean, 0);
        ItemUtil.register(itemCoffeeSeed);
        CompatUtil.registerMFRSeed(itemCoffeeSeed);

        itemPickaxeEmerald = new ItemPickaxeAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemPickaxeEmerald", EnumRarity.epic);
        itemAxeEmerald = new ItemAxeAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemAxeEmerald", EnumRarity.epic);
        itemShovelEmerald = new ItemShovelAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemShovelEmerald", EnumRarity.epic);
        itemSwordEmerald = new ItemSwordAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemSwordEmerald", EnumRarity.epic);
        itemHoeEmerald = new ItemHoeAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "itemHoeEmerald", EnumRarity.epic);
        ItemUtil.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "itemHoeObsidian", EnumRarity.uncommon);
        ItemUtil.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

        itemPickaxeQuartz = new ItemPickaxeAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemPickaxeQuartz", EnumRarity.rare);
        itemAxeQuartz = new ItemAxeAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemAxeQuartz", EnumRarity.rare);
        itemShovelQuartz = new ItemShovelAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemShovelQuartz", EnumRarity.rare);
        itemSwordQuartz = new ItemSwordAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemSwordQuartz", EnumRarity.rare);
        itemHoeQuartz = new ItemHoeAA(InitToolMaterials.toolMaterialQuartz, "gemQuartzBlack", "itemHoeQuartz", EnumRarity.rare);
        ItemUtil.registerItems(new Item[]{itemPickaxeQuartz, itemAxeQuartz, itemShovelQuartz, itemSwordQuartz, itemHoeQuartz});

        woodenPaxel = new ItemAllToolAA(Item.ToolMaterial.WOOD, "plankWood", "woodenPaxel", EnumRarity.uncommon);
        stonePaxel = new ItemAllToolAA(Item.ToolMaterial.STONE, "stone", "stonePaxel", EnumRarity.uncommon);
        ironPaxel = new ItemAllToolAA(Item.ToolMaterial.IRON, "ingotIron", "ironPaxel", EnumRarity.rare);
        goldPaxel = new ItemAllToolAA(Item.ToolMaterial.GOLD, "ingotGold", "goldPaxel", EnumRarity.rare);
        diamondPaxel = new ItemAllToolAA(Item.ToolMaterial.EMERALD, "gemDiamond", "diamondPaxel", EnumRarity.epic);
        emeraldPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialEmerald, "gemEmerald", "emeraldPaxel", EnumRarity.epic);
        obsidianPaxel = new ItemAllToolAA(InitToolMaterials.toolMaterialObsidian, "obsidian", "obsidianPaxel", EnumRarity.epic);
        ItemUtil.registerItems(new Item[]{woodenPaxel, stonePaxel, ironPaxel, goldPaxel, diamondPaxel, emeraldPaxel, obsidianPaxel});
    }
}
