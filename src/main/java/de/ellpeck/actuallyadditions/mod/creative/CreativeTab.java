/*
 * This file ("CreativeTab.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.creative;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs{

    public static final CreativeTab INSTANCE = new CreativeTab();
    private NonNullList<ItemStack> list;

    public CreativeTab(){
        super(ModUtil.MOD_ID);
        this.setBackgroundImageName(ModUtil.MOD_ID+".png");
    }

    @Override
    public boolean hasSearchBar(){
        return true;
    }

    @Override
    public int getSearchbarWidth(){
        return 70;
    }

    @Override
    public ItemStack getTabIconItem(){
        return new ItemStack(InitItems.itemBooklet);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list){
        this.list = list;

        this.add(InitItems.itemBooklet);
        this.add(InitBlocks.blockSmileyCloud);
        this.add(InitBlocks.blockTinyTorch);

        this.add(InitBlocks.blockFireworkBox);
        this.add(InitBlocks.blockLaserRelay);
        this.add(InitBlocks.blockLaserRelayAdvanced);
        this.add(InitBlocks.blockLaserRelayExtreme);
        this.add(InitBlocks.blockLaserRelayFluids);
        this.add(InitBlocks.blockLaserRelayItem);
        this.add(InitBlocks.blockLaserRelayItemWhitelist);
        this.add(InitBlocks.blockItemViewer);
        this.add(InitBlocks.blockItemViewerHopping);
        this.add(InitBlocks.blockAtomicReconstructor);
        this.add(InitBlocks.blockEmpowerer);
        this.add(InitBlocks.blockPhantomface);
        this.add(InitBlocks.blockPhantomEnergyface);
        this.add(InitBlocks.blockPhantomLiquiface);
        this.add(InitBlocks.blockPhantomRedstoneface);
        this.add(InitBlocks.blockPhantomPlacer);
        this.add(InitBlocks.blockPhantomBreaker);
        this.add(InitBlocks.blockPlayerInterface);
        this.add(InitBlocks.blockInputter);
        this.add(InitBlocks.blockInputterAdvanced);
        this.add(InitBlocks.blockPhantomBooster);
        this.add(InitBlocks.blockCoffeeMachine);
        this.add(InitBlocks.blockXPSolidifier);
        this.add(InitBlocks.blockDisplayStand);

        this.add(InitBlocks.blockFarmer);
        this.add(InitBlocks.blockShockSuppressor);
        this.add(InitBlocks.blockMiner);
        this.add(InitBlocks.blockGreenhouseGlass);
        this.add(InitBlocks.blockGrinder);
        this.add(InitBlocks.blockGrinderDouble);
        this.add(InitBlocks.blockFurnaceDouble);
        this.add(InitBlocks.blockLavaFactoryController);

        this.add(InitBlocks.blockEnergizer);
        this.add(InitBlocks.blockEnervator);

        this.add(InitBlocks.blockFurnaceSolar);
        this.add(InitBlocks.blockHeatCollector);
        this.add(InitBlocks.blockCoalGenerator);
        this.add(InitBlocks.blockOilGenerator);
        this.add(InitBlocks.blockLeafGenerator);
        this.add(InitBlocks.blockBioReactor);

        this.add(InitBlocks.blockItemRepairer);
        this.add(InitBlocks.blockFishingNet);
        this.add(InitBlocks.blockBreaker);
        this.add(InitBlocks.blockDirectionalBreaker);
        this.add(InitBlocks.blockRangedCollector);
        this.add(InitBlocks.blockPlacer);
        this.add(InitBlocks.blockDropper);
        this.add(InitBlocks.blockFluidPlacer);
        this.add(InitBlocks.blockFluidCollector);
        this.add(InitBlocks.blockBatteryBox);

        this.add(InitBlocks.blockMisc);
        this.add(InitBlocks.blockFeeder);
        this.add(InitBlocks.blockCompost);
        this.add(InitBlocks.blockGiantChest);
        this.add(InitBlocks.blockGiantChestMedium);
        this.add(InitBlocks.blockGiantChestLarge);
        this.add(InitBlocks.blockCanolaPress);
        this.add(InitBlocks.blockFermentingBarrel);

        this.add(InitBlocks.blockTestifiBucksGreenWall);
        this.add(InitBlocks.blockTestifiBucksWhiteWall);
        this.add(InitBlocks.blockTestifiBucksGreenStairs);
        this.add(InitBlocks.blockTestifiBucksWhiteStairs);
        this.add(InitBlocks.blockTestifiBucksGreenSlab);
        this.add(InitBlocks.blockTestifiBucksWhiteSlab);
        this.add(InitBlocks.blockTestifiBucksGreenFence);
        this.add(InitBlocks.blockTestifiBucksWhiteFence);

        this.add(InitBlocks.blockQuartzWall);
        this.add(InitBlocks.blockQuartzStair);
        this.add(InitBlocks.blockQuartzSlab);
        this.add(InitBlocks.blockChiseledQuartzWall);
        this.add(InitBlocks.blockChiseledQuartzStair);
        this.add(InitBlocks.blockChiseledQuartzSlab);
        this.add(InitBlocks.blockPillarQuartzWall);
        this.add(InitBlocks.blockPillarQuartzStair);
        this.add(InitBlocks.blockPillarQuartzSlab);

        this.add(InitBlocks.blockColoredLamp);
        this.add(InitBlocks.blockColoredLampOn);
        this.add(InitBlocks.blockLampPowerer);
        this.add(InitBlocks.blockTreasureChest);

        this.add(InitBlocks.blockBlackLotus);

        this.add(InitItems.itemBag);
        this.add(InitItems.itemVoidBag);

        this.add(InitItems.itemWorm);
        this.add(InitItems.itemPlayerProbe);
        this.add(InitItems.itemColorLens);
        this.add(InitItems.itemExplosionLens);
        this.add(InitItems.itemDamageLens);
        this.add(InitItems.itemMoreDamageLens);
        this.add(InitItems.itemDisenchantingLens);
        this.add(InitItems.itemMiningLens);
        this.add(InitItems.itemLaserWrench);
        this.add(InitItems.itemLaserUpgradeInvisibility);
        this.add(InitItems.itemLaserUpgradeRange);
        this.add(InitItems.itemEngineerGoggles);
        this.add(InitItems.itemEngineerGogglesAdvanced);
        this.add(InitItems.itemCrateKeeper);
        this.add(InitItems.itemChestToCrateUpgrade);
        this.add(InitItems.itemSmallToMediumCrateUpgrade);
        this.add(InitItems.itemMediumToLargeCrateUpgrade);
        this.add(InitItems.itemSpawnerChanger);
        this.add(InitItems.itemWaterBowl);

        this.add(InitItems.itemDrill);
        this.add(InitItems.itemDrillUpgradeSpeed);
        this.add(InitItems.itemDrillUpgradeSpeedII);
        this.add(InitItems.itemDrillUpgradeSpeedIII);
        this.add(InitItems.itemDrillUpgradeSilkTouch);
        this.add(InitItems.itemDrillUpgradeFortune);
        this.add(InitItems.itemDrillUpgradeFortuneII);
        this.add(InitItems.itemDrillUpgradeThreeByThree);
        this.add(InitItems.itemDrillUpgradeFiveByFive);
        this.add(InitItems.itemDrillUpgradeBlockPlacing);
        this.add(InitItems.itemBattery);
        this.add(InitItems.itemBatteryDouble);
        this.add(InitItems.itemBatteryTriple);
        this.add(InitItems.itemBatteryQuadruple);
        this.add(InitItems.itemBatteryQuintuple);
        this.add(InitItems.itemTeleStaff);
        this.add(InitItems.itemFillingWand);

        this.add(InitItems.itemGrowthRing);
        this.add(InitItems.itemMagnetRing);
        this.add(InitItems.itemWaterRemovalRing);

        UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket;
        this.list.add(UniversalBucket.getFilledBucket(bucket, InitFluids.fluidCanolaOil));
        this.list.add(UniversalBucket.getFilledBucket(bucket, InitFluids.fluidOil));
        this.list.add(UniversalBucket.getFilledBucket(bucket, InitFluids.fluidCrystalOil));
        this.list.add(UniversalBucket.getFilledBucket(bucket, InitFluids.fluidEmpoweredOil));

        this.add(InitItems.itemPhantomConnector);
        this.add(InitItems.itemFilter);
        this.add(InitItems.itemWingsOfTheBats);

        this.add(InitItems.itemCoffeeSeed);
        this.add(InitItems.itemCoffeeBean);
        this.add(InitItems.itemRiceSeed);
        this.add(InitItems.itemCanolaSeed);
        this.add(InitItems.itemFlaxSeed);
        this.add(InitItems.itemHairyBall);
        this.add(InitItems.itemMisc);
        this.add(InitItems.itemResonantRice);
        this.add(InitItems.itemFertilizer);

        this.add(InitItems.itemCoffee);
        this.add(InitItems.itemFoods);
        this.add(InitItems.itemKnife);
        this.add(InitItems.itemCrafterOnAStick);
        this.add(InitItems.itemDust);
        this.add(InitItems.itemSolidifiedExperience);
        this.add(InitItems.itemLeafBlower);
        this.add(InitItems.itemLeafBlowerAdvanced);

        this.add(InitItems.woodenPaxel);
        this.add(InitItems.stonePaxel);
        this.add(InitItems.ironPaxel);
        this.add(InitItems.goldPaxel);
        this.add(InitItems.diamondPaxel);
        this.add(InitItems.emeraldPaxel);
        this.add(InitItems.obsidianPaxel);
        this.add(InitItems.quartzPaxel);
        this.add(InitItems.itemPaxelCrystalRed);
        this.add(InitItems.itemPaxelCrystalBlue);
        this.add(InitItems.itemPaxelCrystalLightBlue);
        this.add(InitItems.itemPaxelCrystalBlack);
        this.add(InitItems.itemPaxelCrystalGreen);
        this.add(InitItems.itemPaxelCrystalWhite);

        this.add(InitBlocks.blockCrystalClusterRedstone);
        this.add(InitBlocks.blockCrystalClusterLapis);
        this.add(InitBlocks.blockCrystalClusterDiamond);
        this.add(InitBlocks.blockCrystalClusterCoal);
        this.add(InitBlocks.blockCrystalClusterEmerald);
        this.add(InitBlocks.blockCrystalClusterIron);
        this.add(InitBlocks.blockCrystal);
        this.add(InitBlocks.blockCrystalEmpowered);
        this.add(InitItems.itemCrystal);
        this.add(InitItems.itemCrystalEmpowered);
        this.add(InitItems.itemCrystalShard);

        this.add(InitItems.itemJams);

        this.add(InitItems.itemPotionRing);
        this.add(InitItems.itemPotionRingAdvanced);

        this.add(InitItems.itemPickaxeQuartz);
        this.add(InitItems.itemSwordQuartz);
        this.add(InitItems.itemAxeQuartz);
        this.add(InitItems.itemShovelQuartz);
        this.add(InitItems.itemHoeQuartz);

        this.add(InitItems.itemHelmQuartz);
        this.add(InitItems.itemChestQuartz);
        this.add(InitItems.itemPantsQuartz);
        this.add(InitItems.itemBootsQuartz);

        this.add(InitItems.itemPickaxeEmerald);
        this.add(InitItems.itemSwordEmerald);
        this.add(InitItems.itemAxeEmerald);
        this.add(InitItems.itemShovelEmerald);
        this.add(InitItems.itemHoeEmerald);

        this.add(InitItems.itemHelmEmerald);
        this.add(InitItems.itemChestEmerald);
        this.add(InitItems.itemPantsEmerald);
        this.add(InitItems.itemBootsEmerald);

        this.add(InitItems.itemPickaxeObsidian);
        this.add(InitItems.itemSwordObsidian);
        this.add(InitItems.itemAxeObsidian);
        this.add(InitItems.itemShovelObsidian);
        this.add(InitItems.itemHoeObsidian);

        this.add(InitItems.itemHelmObsidian);
        this.add(InitItems.itemChestObsidian);
        this.add(InitItems.itemPantsObsidian);
        this.add(InitItems.itemBootsObsidian);

        this.add(InitItems.itemPickaxeCrystalRed);
        this.add(InitItems.itemAxeCrystalRed);
        this.add(InitItems.itemShovelCrystalRed);
        this.add(InitItems.itemSwordCrystalRed);
        this.add(InitItems.itemHoeCrystalRed);
        this.add(InitItems.itemHelmCrystalRed);
        this.add(InitItems.itemChestCrystalRed);
        this.add(InitItems.itemPantsCrystalRed);
        this.add(InitItems.itemBootsCrystalRed);

        this.add(InitItems.itemPickaxeCrystalBlue);
        this.add(InitItems.itemAxeCrystalBlue);
        this.add(InitItems.itemShovelCrystalBlue);
        this.add(InitItems.itemSwordCrystalBlue);
        this.add(InitItems.itemHoeCrystalBlue);
        this.add(InitItems.itemHelmCrystalBlue);
        this.add(InitItems.itemChestCrystalBlue);
        this.add(InitItems.itemPantsCrystalBlue);
        this.add(InitItems.itemBootsCrystalBlue);

        this.add(InitItems.itemPickaxeCrystalLightBlue);
        this.add(InitItems.itemAxeCrystalLightBlue);
        this.add(InitItems.itemShovelCrystalLightBlue);
        this.add(InitItems.itemSwordCrystalLightBlue);
        this.add(InitItems.itemHoeCrystalLightBlue);
        this.add(InitItems.itemHelmCrystalLightBlue);
        this.add(InitItems.itemChestCrystalLightBlue);
        this.add(InitItems.itemPantsCrystalLightBlue);
        this.add(InitItems.itemBootsCrystalLightBlue);

        this.add(InitItems.itemPickaxeCrystalBlack);
        this.add(InitItems.itemAxeCrystalBlack);
        this.add(InitItems.itemShovelCrystalBlack);
        this.add(InitItems.itemSwordCrystalBlack);
        this.add(InitItems.itemHoeCrystalBlack);
        this.add(InitItems.itemHelmCrystalBlack);
        this.add(InitItems.itemChestCrystalBlack);
        this.add(InitItems.itemPantsCrystalBlack);
        this.add(InitItems.itemBootsCrystalBlack);

        this.add(InitItems.itemPickaxeCrystalGreen);
        this.add(InitItems.itemAxeCrystalGreen);
        this.add(InitItems.itemShovelCrystalGreen);
        this.add(InitItems.itemSwordCrystalGreen);
        this.add(InitItems.itemHoeCrystalGreen);
        this.add(InitItems.itemHelmCrystalGreen);
        this.add(InitItems.itemChestCrystalGreen);
        this.add(InitItems.itemPantsCrystalGreen);
        this.add(InitItems.itemBootsCrystalGreen);

        this.add(InitItems.itemPickaxeCrystalWhite);
        this.add(InitItems.itemAxeCrystalWhite);
        this.add(InitItems.itemShovelCrystalWhite);
        this.add(InitItems.itemSwordCrystalWhite);
        this.add(InitItems.itemHoeCrystalWhite);
        this.add(InitItems.itemHelmCrystalWhite);
        this.add(InitItems.itemChestCrystalWhite);
        this.add(InitItems.itemPantsCrystalWhite);
        this.add(InitItems.itemBootsCrystalWhite);
    }

    public void add(Item item){
        if(item != null){
            item.getSubItems(item, INSTANCE, this.list);
        }
    }

    public void add(Block block){
        if(block != null){
            block.getSubBlocks(new ItemStack(block).getItem(), INSTANCE, this.list);
        }
    }
}
