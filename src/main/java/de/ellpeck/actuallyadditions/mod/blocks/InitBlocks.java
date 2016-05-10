/*
 * This file ("InitBlocks.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockStair;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.util.CompatUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block blockCompost;
    public static Block blockMisc;
    public static Block blockWildPlant;
    public static Block blockFeeder;
    public static Block blockGiantChest;

    public static Block blockGrinder;
    public static Block blockGrinderDouble;
    public static Block blockFurnaceDouble;
    public static Block blockInputter;
    public static Block blockInputterAdvanced;
    public static Block blockFishingNet;
    public static Block blockFurnaceSolar;
    public static Block blockHeatCollector;
    public static Block blockItemRepairer;
    public static Block blockGreenhouseGlass;

    public static Block blockBreaker;
    public static Block blockPlacer;
    public static Block blockDropper;

    public static Block blockRice;
    public static Block blockCanola;
    public static Block blockFlax;
    public static Block blockCoffee;

    public static Block blockCanolaPress;
    public static Block blockFermentingBarrel;

    public static Block blockCoalGenerator;
    public static Block blockOilGenerator;

    public static Block blockPhantomface;
    public static Block blockPhantomPlacer;
    public static Block blockPhantomBreaker;
    public static Block blockPhantomLiquiface;
    public static Block blockPhantomEnergyface;
    public static Block blockPhantomRedstoneface;

    public static Block blockFluidPlacer;
    public static Block blockFluidCollector;

    public static Block blockLavaFactoryController;
    public static Block blockCoffeeMachine;

    public static Block blockPhantomBooster;

    public static Block blockEnergizer;
    public static Block blockEnervator;

    public static Block blockTestifiBucksGreenWall;
    public static Block blockTestifiBucksWhiteWall;
    public static Block blockTestifiBucksGreenStairs;
    public static Block blockTestifiBucksWhiteStairs;
    public static Block blockTestifiBucksGreenSlab;
    public static Block blockTestifiBucksWhiteSlab;
    public static Block blockTestifiBucksGreenFence;
    public static Block blockTestifiBucksWhiteFence;

    public static Block blockColoredLamp;
    public static Block blockColoredLampOn;
    public static Block blockLampPowerer;

    public static Block blockTreasureChest;
    public static Block blockXPSolidifier;

    public static Block blockSmileyCloud;

    public static Block blockLeafGenerator;
    public static Block blockDirectionalBreaker;
    public static Block blockRangedCollector;

    public static Block blockLaserRelay;
    public static Block blockLaserRelayItem;
    public static Block blockLaserRelayItemWhitelist;
    public static Block blockItemViewer;

    public static Block blockBlackLotus;
    public static Block blockCrystal;
    public static Block blockAtomicReconstructor;

    public static Block blockMiner;

    public static Block blockFireworkBox;

    public static Block blockQuartzWall;
    public static Block blockQuartzStair;
    public static Block blockQuartzSlab;
    public static Block blockChiseledQuartzWall;
    public static Block blockChiseledQuartzStair;
    public static Block blockChiseledQuartzSlab;
    public static Block blockPillarQuartzWall;
    public static Block blockPillarQuartzStair;
    public static Block blockPillarQuartzSlab;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Blocks...");

        blockItemViewer = new BlockItemViewer("blockItemViewer");
        blockFireworkBox = new BlockFireworkBox("blockFireworkBox");
        blockMiner = new BlockMiner("blockMiner");
        blockAtomicReconstructor = new BlockAtomicReconstructor("blockAtomicReconstructor");
        blockCrystal = new BlockCrystal("blockCrystal");
        blockBlackLotus = new BlockBlackLotus("blockBlackLotus");
        blockLaserRelay = new BlockLaserRelay("blockLaserRelay", BlockLaserRelay.Type.ENERGY);
        blockLaserRelayItem = new BlockLaserRelay("blockLaserRelayItem", BlockLaserRelay.Type.ITEM);
        blockLaserRelayItemWhitelist = new BlockLaserRelay("blockLaserRelayItemWhitelist", BlockLaserRelay.Type.ITEM_WHITELIST);
        blockRangedCollector = new BlockRangedCollector("blockRangedCollector");
        blockDirectionalBreaker = new BlockDirectionalBreaker("blockDirectionalBreaker");
        blockLeafGenerator = new BlockLeafGenerator("blockLeafGenerator");
        blockSmileyCloud = new BlockSmileyCloud("blockSmileyCloud");
        blockXPSolidifier = new BlockXPSolidifier("blockXPSolidifier");
        blockTestifiBucksGreenWall = new BlockGeneric("blockTestifiBucksGreenWall");
        blockTestifiBucksWhiteWall = new BlockGeneric("blockTestifiBucksWhiteWall");
        blockTestifiBucksGreenStairs = new BlockStair(blockTestifiBucksGreenWall, "blockTestifiBucksGreenStairs");
        blockTestifiBucksWhiteStairs = new BlockStair(blockTestifiBucksWhiteWall, "blockTestifiBucksWhiteStairs");
        blockTestifiBucksGreenSlab = new BlockSlabs("blockTestifiBucksGreenSlab", blockTestifiBucksGreenWall);
        blockTestifiBucksWhiteSlab = new BlockSlabs("blockTestifiBucksWhiteSlab", blockTestifiBucksWhiteWall);
        blockTestifiBucksGreenFence = new BlockWallAA("blockTestifiBucksGreenFence", blockTestifiBucksGreenWall);
        blockTestifiBucksWhiteFence = new BlockWallAA("blockTestifiBucksWhiteFence", blockTestifiBucksWhiteWall);
        blockColoredLamp = new BlockColoredLamp(false, "blockColoredLamp");
        blockColoredLampOn = new BlockColoredLamp(true, "blockColoredLampOn");
        blockLampPowerer = new BlockLampPowerer("blockLampPowerer");
        blockTreasureChest = new BlockTreasureChest("blockTreasureChest");
        blockEnergizer = new BlockEnergizer(true, "blockEnergizer");
        blockEnervator = new BlockEnergizer(false, "blockEnervator");
        blockLavaFactoryController = new BlockLavaFactoryController("blockLavaFactoryController");
        blockCanolaPress = new BlockCanolaPress("blockCanolaPress");
        blockPhantomface = new BlockPhantom(BlockPhantom.Type.FACE, "blockPhantomface");
        blockPhantomPlacer = new BlockPhantom(BlockPhantom.Type.PLACER, "blockPhantomPlacer");
        blockPhantomLiquiface = new BlockPhantom(BlockPhantom.Type.LIQUIFACE, "blockPhantomLiquiface");
        blockPhantomEnergyface = new BlockPhantom(BlockPhantom.Type.ENERGYFACE, "blockPhantomEnergyface");
        blockPhantomRedstoneface = new BlockPhantom(BlockPhantom.Type.REDSTONEFACE, "blockPhantomRedstoneface");
        blockPhantomBreaker = new BlockPhantom(BlockPhantom.Type.BREAKER, "blockPhantomBreaker");
        blockCoalGenerator = new BlockCoalGenerator("blockCoalGenerator");
        blockOilGenerator = new BlockOilGenerator("blockOilGenerator");
        blockFermentingBarrel = new BlockFermentingBarrel("blockFermentingBarrel");
        blockRice = new BlockPlant("blockRice", 1, 2);
        CompatUtil.registerMFRPlant(blockRice);
        blockCanola = new BlockPlant("blockCanola", 2, 3);
        CompatUtil.registerMFRPlant(blockCanola);
        blockFlax = new BlockPlant("blockFlax", 2, 4);
        CompatUtil.registerMFRPlant(blockFlax);
        blockCoffee = new BlockPlant("blockCoffee", 2, 2);
        CompatUtil.registerMFRPlant(blockCoffee);
        blockCompost = new BlockCompost("blockCompost");
        blockMisc = new BlockMisc("blockMisc");
        blockFeeder = new BlockFeeder("blockFeeder");
        blockGiantChest = new BlockGiantChest("blockGiantChest");
        blockGrinder = new BlockGrinder(false, "blockGrinder");
        blockGrinderDouble = new BlockGrinder(true, "blockGrinderDouble");
        blockFurnaceDouble = new BlockFurnaceDouble("blockFurnaceDouble");
        blockInputter = new BlockInputter(false, "blockInputter");
        blockInputterAdvanced = new BlockInputter(true, "blockInputterAdvanced");
        blockFishingNet = new BlockFishingNet("blockFishingNet");
        blockFurnaceSolar = new BlockFurnaceSolar("blockFurnaceSolar");
        blockHeatCollector = new BlockHeatCollector("blockHeatCollector");
        blockItemRepairer = new BlockItemRepairer("blockItemRepairer");
        blockGreenhouseGlass = new BlockGreenhouseGlass("blockGreenhouseGlass");
        blockBreaker = new BlockBreaker(false, "blockBreaker");
        blockPlacer = new BlockBreaker(true, "blockPlacer");
        blockDropper = new BlockDropper("blockDropper");
        blockFluidPlacer = new BlockFluidCollector(true, "blockFluidPlacer");
        blockFluidCollector = new BlockFluidCollector(false, "blockFluidCollector");
        blockCoffeeMachine = new BlockCoffeeMachine("blockCoffeeMachine");
        blockPhantomBooster = new BlockPhantomBooster("blockPhantomBooster");
        blockWildPlant = new BlockWildPlant("blockWild");
        blockQuartzWall = new BlockWallAA("blockQuartzWall", blockMisc, TheMiscBlocks.QUARTZ.ordinal());
        blockChiseledQuartzWall = new BlockWallAA("blockChiseledQuartzWall", blockMisc, TheMiscBlocks.QUARTZ_CHISELED.ordinal());
        blockPillarQuartzWall = new BlockWallAA("blockPillarQuartzWall", blockMisc, TheMiscBlocks.QUARTZ_PILLAR.ordinal());
        blockQuartzStair = new BlockStair(blockMisc, "blockQuartzStair", TheMiscBlocks.QUARTZ.ordinal());
        blockChiseledQuartzStair = new BlockStair(blockMisc, "blockChiseledQuartzStair", TheMiscBlocks.QUARTZ_CHISELED.ordinal());
        blockPillarQuartzStair = new BlockStair(blockMisc, "blockPillarQuartzStair", TheMiscBlocks.QUARTZ_PILLAR.ordinal());
        blockQuartzSlab = new BlockSlabs("blockQuartzSlab", blockMisc, TheMiscBlocks.QUARTZ.ordinal());
        blockChiseledQuartzSlab = new BlockSlabs("blockChiseledQuartzSlab", blockMisc, TheMiscBlocks.QUARTZ_CHISELED.ordinal());
        blockPillarQuartzSlab = new BlockSlabs("blockPillarQuartzSlab", blockMisc, TheMiscBlocks.QUARTZ_PILLAR.ordinal());
    }
}