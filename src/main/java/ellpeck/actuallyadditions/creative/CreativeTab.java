/*
 * This file ("CreativeTab.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.creative;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitForeignPaxels;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();
    private List list;

    public CreativeTab(){
        super(ModUtil.MOD_ID_LOWER);
    }

    @Override
    public ItemStack getIconItemStack(){
        return new ItemStack(this.getTabIconItem());
    }

    @Override
    public Item getTabIconItem(){
        return InitItems.itemLexicon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List list){
        this.list = list;

        add(InitItems.itemLexicon);
        add(InitBlocks.blockSmileyCloud);

        add(InitBlocks.blockPhantomface);
        add(InitBlocks.blockPhantomEnergyface);
        add(InitBlocks.blockPhantomLiquiface);
        add(InitBlocks.blockPhantomPlacer);
        add(InitBlocks.blockPhantomBreaker);
        add(InitBlocks.blockInputter);
        add(InitBlocks.blockInputterAdvanced);
        add(InitBlocks.blockPhantomBooster);
        add(InitBlocks.blockCoffeeMachine);
        add(InitBlocks.blockXPSolidifier);

        add(InitBlocks.blockOreMagnet);

        add(InitBlocks.blockGreenhouseGlass);
        add(InitBlocks.blockGrinder);
        add(InitBlocks.blockGrinderDouble);
        add(InitBlocks.blockFurnaceDouble);
        add(InitBlocks.blockLavaFactoryController);

        add(InitBlocks.blockEnergizer);
        add(InitBlocks.blockEnervator);

        add(InitBlocks.blockFurnaceSolar);
        add(InitBlocks.blockHeatCollector);
        add(InitBlocks.blockCoalGenerator);
        add(InitBlocks.blockOilGenerator);
        add(InitBlocks.blockLeafGenerator);

        add(InitBlocks.blockItemRepairer);
        add(InitBlocks.blockFishingNet);
        add(InitBlocks.blockBreaker);
        add(InitBlocks.blockDirectionalBreaker);
        add(InitBlocks.blockRangedCollector);
        add(InitBlocks.blockPlacer);
        add(InitBlocks.blockDropper);
        add(InitBlocks.blockFluidPlacer);
        add(InitBlocks.blockFluidCollector);

        add(InitBlocks.blockMisc);
        add(InitBlocks.blockFeeder);
        add(InitBlocks.blockCompost);
        add(InitBlocks.blockGiantChest);
        add(InitBlocks.blockCanolaPress);
        add(InitBlocks.blockFermentingBarrel);

        add(InitBlocks.blockTestifiBucksGreenWall);
        add(InitBlocks.blockTestifiBucksWhiteWall);
        add(InitBlocks.blockTestifiBucksGreenStairs);
        add(InitBlocks.blockTestifiBucksWhiteStairs);
        add(InitBlocks.blockTestifiBucksGreenSlab);
        add(InitBlocks.blockTestifiBucksWhiteSlab);
        add(InitBlocks.blockColoredLamp);
        add(InitBlocks.blockLampPowerer);
        add(InitBlocks.blockTreasureChest);

        add(InitItems.itemDrill);
        add(InitItems.itemDrillUpgradeSpeed);
        add(InitItems.itemDrillUpgradeSpeedII);
        add(InitItems.itemDrillUpgradeSpeedIII);
        add(InitItems.itemDrillUpgradeSilkTouch);
        add(InitItems.itemDrillUpgradeFortune);
        add(InitItems.itemDrillUpgradeFortuneII);
        add(InitItems.itemDrillUpgradeThreeByThree);
        add(InitItems.itemDrillUpgradeFiveByFive);
        add(InitItems.itemDrillUpgradeBlockPlacing);
        add(InitItems.itemBattery);
        add(InitItems.itemBatteryDouble);
        add(InitItems.itemBatteryTriple);
        add(InitItems.itemBatteryQuadruple);
        add(InitItems.itemBatteryQuintuple);
        add(InitItems.itemTeleStaff);

        add(InitItems.itemGrowthRing);
        add(InitItems.itemMagnetRing);
        add(InitItems.itemWaterRemovalRing);

        add(InitItems.itemPhantomConnector);
        add(InitItems.itemBucketCanolaOil);
        add(InitItems.itemBucketOil);

        add(InitItems.itemWingsOfTheBats);

        add(InitItems.itemCoffeeSeed);
        add(InitItems.itemCoffeeBean);
        add(InitItems.itemRiceSeed);
        add(InitItems.itemCanolaSeed);
        add(InitItems.itemFlaxSeed);
        add(InitItems.itemHairyBall);
        add(InitItems.itemMisc);
        add(InitItems.itemResonantRice);
        add(InitItems.itemFertilizer);

        add(InitItems.itemCoffee);
        add(InitItems.itemFoods);
        add(InitItems.itemKnife);
        add(InitItems.itemCrafterOnAStick);
        add(InitItems.itemDust);
        add(InitItems.itemSpecialDrop);
        add(InitItems.itemLeafBlower);
        add(InitItems.itemLeafBlowerAdvanced);

        add(InitItems.woodenPaxel);
        add(InitItems.stonePaxel);
        add(InitItems.ironPaxel);
        add(InitItems.goldPaxel);
        add(InitItems.diamondPaxel);
        add(InitItems.emeraldPaxel);
        add(InitItems.obsidianPaxel);
        add(InitItems.quartzPaxel);
        InitForeignPaxels.addToCreativeTab();

        add(InitItems.itemPickaxeQuartz);
        add(InitItems.itemSwordQuartz);
        add(InitItems.itemAxeQuartz);
        add(InitItems.itemShovelQuartz);
        add(InitItems.itemHoeQuartz);

        add(InitItems.itemHelmQuartz);
        add(InitItems.itemChestQuartz);
        add(InitItems.itemPantsQuartz);
        add(InitItems.itemBootsQuartz);

        add(InitItems.itemPickaxeEmerald);
        add(InitItems.itemSwordEmerald);
        add(InitItems.itemAxeEmerald);
        add(InitItems.itemShovelEmerald);
        add(InitItems.itemHoeEmerald);

        add(InitItems.itemHelmEmerald);
        add(InitItems.itemChestEmerald);
        add(InitItems.itemPantsEmerald);
        add(InitItems.itemBootsEmerald);

        add(InitItems.itemPickaxeObsidian);
        add(InitItems.itemSwordObsidian);
        add(InitItems.itemAxeObsidian);
        add(InitItems.itemShovelObsidian);
        add(InitItems.itemHoeObsidian);

        add(InitItems.itemHelmObsidian);
        add(InitItems.itemChestObsidian);
        add(InitItems.itemPantsObsidian);
        add(InitItems.itemBootsObsidian);

        add(InitItems.itemJams);

        add(InitItems.itemPotionRing);
        add(InitItems.itemPotionRingAdvanced);
    }

    public void add(Item item){
        item.getSubItems(item, instance, this.list);
    }

    public void add(Block block){
        block.getSubBlocks(new ItemStack(block).getItem(), instance, this.list);
    }
}
