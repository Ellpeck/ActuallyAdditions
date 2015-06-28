package ellpeck.actuallyadditions.items;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.tools.*;
import ellpeck.actuallyadditions.material.InitItemMaterials;
import ellpeck.actuallyadditions.util.CompatUtil;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
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

    public static Item itemPickaxeObsidian;
    public static Item itemAxeObsidian;
    public static Item itemShovelObsidian;
    public static Item itemSwordObsidian;
    public static Item itemHoeObsidian;

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

    public static void init(){
        Util.logInfo("Initializing Items...");

        itemDrill = new ItemDrill();
        ItemUtil.register(itemDrill);

        itemBattery = new ItemBattery();
        ItemUtil.register(itemBattery);

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
        ItemUtil.register(itemMisc, ItemMisc.allMiscItems);

        itemFoods = new ItemFoods();
        ItemUtil.register(itemFoods, ItemFoods.allFoods);

        itemJams = new ItemJams();
        ItemUtil.register(itemJams, ItemJams.allJams);

        itemKnife = new ItemKnife();
        ItemUtil.register(itemKnife);

        itemCrafterOnAStick = new ItemCrafterOnAStick();
        ItemUtil.register(itemCrafterOnAStick);

        itemDust = new ItemDust();
        ItemUtil.register(itemDust, ItemDust.allDusts);

        itemSpecialDrop = new ItemSpecialDrop();
        ItemUtil.register(itemSpecialDrop, ItemSpecialDrop.allDrops);

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

        itemRiceSeed = new ItemSeed("itemRiceSeed", InitBlocks.blockRice, itemFoods, TheFoods.RICE.ordinal());
        ItemUtil.register(itemRiceSeed);
        CompatUtil.registerMFRSeed(itemRiceSeed);

        itemCanolaSeed = new ItemSeed("itemCanolaSeed", InitBlocks.blockCanola, itemMisc, TheMiscItems.CANOLA.ordinal());
        ItemUtil.register(itemCanolaSeed);
        CompatUtil.registerMFRSeed(itemCanolaSeed);

        itemFlaxSeed = new ItemSeed("itemFlaxSeed", InitBlocks.blockFlax, Items.string, 0);
        ItemUtil.register(itemFlaxSeed);
        CompatUtil.registerMFRSeed(itemFlaxSeed);

        itemCoffeeSeed = new ItemSeed("itemCoffeeSeed", InitBlocks.blockCoffee, itemCoffeeBean, 0);
        ItemUtil.register(itemCoffeeSeed);
        CompatUtil.registerMFRSeed(itemCoffeeSeed);

        itemPickaxeEmerald = new ItemPickaxeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemPickaxeEmerald", EnumRarity.rare);
        itemAxeEmerald = new ItemAxeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemAxeEmerald", EnumRarity.rare);
        itemShovelEmerald = new ItemShovelAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemShovelEmerald", EnumRarity.rare);
        itemSwordEmerald = new ItemSwordAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemSwordEmerald", EnumRarity.rare);
        itemHoeEmerald = new ItemHoeAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "itemHoeEmerald", EnumRarity.rare);
        ItemUtil.registerItems(new Item[]{itemPickaxeEmerald, itemAxeEmerald, itemShovelEmerald, itemSwordEmerald, itemHoeEmerald});

        itemPickaxeObsidian = new ItemPickaxeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemPickaxeObsidian", EnumRarity.uncommon);
        itemAxeObsidian = new ItemAxeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemAxeObsidian", EnumRarity.uncommon);
        itemShovelObsidian = new ItemShovelAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemShovelObsidian", EnumRarity.uncommon);
        itemSwordObsidian = new ItemSwordAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemSwordObsidian", EnumRarity.uncommon);
        itemHoeObsidian = new ItemHoeAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "itemHoeObsidian", EnumRarity.uncommon);
        ItemUtil.registerItems(new Item[]{itemPickaxeObsidian, itemAxeObsidian, itemShovelObsidian, itemSwordObsidian, itemHoeObsidian});

        woodenPaxel = new ItemAllToolAA(Item.ToolMaterial.WOOD, new ItemStack(Blocks.planks), "woodenPaxel", EnumRarity.uncommon);
        stonePaxel = new ItemAllToolAA(Item.ToolMaterial.STONE, new ItemStack(Blocks.stone), "stonePaxel", EnumRarity.uncommon);
        ironPaxel = new ItemAllToolAA(Item.ToolMaterial.IRON, new ItemStack(Items.iron_ingot), "ironPaxel", EnumRarity.rare);
        goldPaxel = new ItemAllToolAA(Item.ToolMaterial.GOLD, new ItemStack(Items.gold_ingot), "goldPaxel", EnumRarity.rare);
        diamondPaxel = new ItemAllToolAA(Item.ToolMaterial.EMERALD, new ItemStack(Items.diamond), "diamondPaxel", EnumRarity.epic);
        emeraldPaxel = new ItemAllToolAA(InitItemMaterials.toolMaterialEmerald, new ItemStack(Items.emerald), "emeraldPaxel", EnumRarity.epic);
        obsidianPaxel = new ItemAllToolAA(InitItemMaterials.toolMaterialObsidian, new ItemStack(Blocks.obsidian), "obsidianPaxel", EnumRarity.epic);
        ItemUtil.registerItems(new Item[]{woodenPaxel, stonePaxel, ironPaxel, goldPaxel, diamondPaxel, emeraldPaxel, obsidianPaxel});
    }
}
