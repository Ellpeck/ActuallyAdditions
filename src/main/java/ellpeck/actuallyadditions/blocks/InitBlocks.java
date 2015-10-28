/*
 * This file ("InitBlocks.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.CompatUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

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

    public static Fluid fluidCanolaOil;
    public static Block blockCanolaOil;
    public static Fluid fluidOil;
    public static Block blockOil;

    public static Block blockCanolaPress;
    public static Block blockFermentingBarrel;

    public static Block blockCoalGenerator;
    public static Block blockOilGenerator;

    public static Block blockPhantomface;
    public static Block blockPhantomPlacer;
    public static Block blockPhantomBreaker;
    public static Block blockPhantomLiquiface;
    public static Block blockPhantomEnergyface;

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

    public static Block blockColoredLamp;
    public static Block blockColoredLampOn;
    public static Block blockLampPowerer;

    public static Block blockTreasureChest;
    public static Block blockXPSolidifier;
    public static Block blockOreMagnet;

    public static Block blockSmileyCloud;

    public static Block blockLeafGenerator;
    public static Block blockDirectionalBreaker;
    public static Block blockRangedCollector;

    public static Block blockLaserRelay;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Blocks...");

        blockLaserRelay = new BlockLaserRelay();
        BlockUtil.register(blockLaserRelay);

        blockRangedCollector = new BlockRangedCollector();
        BlockUtil.register(blockRangedCollector);

        blockDirectionalBreaker = new BlockDirectionalBreaker();
        BlockUtil.register(blockDirectionalBreaker);

        blockLeafGenerator = new BlockLeafGenerator();
        BlockUtil.register(blockLeafGenerator);

        blockSmileyCloud = new BlockSmileyCloud();
        BlockUtil.register(blockSmileyCloud);

        blockOreMagnet = new BlockOreMagnet();
        BlockUtil.register(blockOreMagnet);

        blockXPSolidifier = new BlockXPSolidifier();
        BlockUtil.register(blockXPSolidifier);

        blockTestifiBucksGreenWall = new BlockGeneric("blockTestifiBucksGreenWall");
        BlockUtil.register(blockTestifiBucksGreenWall);
        blockTestifiBucksWhiteWall = new BlockGeneric("blockTestifiBucksWhiteWall");
        BlockUtil.register(blockTestifiBucksWhiteWall);
        blockTestifiBucksGreenStairs = new BlockStair(blockTestifiBucksGreenWall, "blockTestifiBucksGreenStairs");
        BlockUtil.register(blockTestifiBucksGreenStairs);
        blockTestifiBucksWhiteStairs = new BlockStair(blockTestifiBucksWhiteWall, "blockTestifiBucksWhiteStairs");
        BlockUtil.register(blockTestifiBucksWhiteStairs);
        blockTestifiBucksGreenSlab = new BlockSlabs("blockTestifiBucksGreenSlab", blockTestifiBucksGreenWall);
        BlockUtil.register(blockTestifiBucksGreenSlab);
        blockTestifiBucksWhiteSlab = new BlockSlabs("blockTestifiBucksWhiteSlab", blockTestifiBucksWhiteWall);
        BlockUtil.register(blockTestifiBucksWhiteSlab);

        blockColoredLamp = new BlockColoredLamp(false);
        BlockUtil.register(blockColoredLamp, BlockColoredLamp.TheItemBlock.class);
        blockColoredLampOn = new BlockColoredLamp(true);
        BlockUtil.register(blockColoredLampOn, BlockColoredLamp.TheItemBlock.class);
        blockLampPowerer = new BlockLampPowerer();
        BlockUtil.register(blockLampPowerer);

        blockTreasureChest = new BlockTreasureChest();
        BlockUtil.register(blockTreasureChest);

        blockEnergizer = new BlockEnergizer(true);
        BlockUtil.register(blockEnergizer);

        blockEnervator = new BlockEnergizer(false);
        BlockUtil.register(blockEnervator);

        blockLavaFactoryController = new BlockLavaFactoryController();
        BlockUtil.register(blockLavaFactoryController);

        blockCanolaPress = new BlockCanolaPress();
        BlockUtil.register(blockCanolaPress);

        blockPhantomface = new BlockPhantom(BlockPhantom.Type.FACE);
        BlockUtil.register(blockPhantomface);

        blockPhantomPlacer = new BlockPhantom(BlockPhantom.Type.PLACER);
        BlockUtil.register(blockPhantomPlacer);

        blockPhantomLiquiface = new BlockPhantom(BlockPhantom.Type.LIQUIFACE);
        BlockUtil.register(blockPhantomLiquiface);

        blockPhantomEnergyface = new BlockPhantom(BlockPhantom.Type.ENERGYFACE);
        BlockUtil.register(blockPhantomEnergyface);

        blockPhantomBreaker = new BlockPhantom(BlockPhantom.Type.BREAKER);
        BlockUtil.register(blockPhantomBreaker);

        blockCoalGenerator = new BlockCoalGenerator();
        BlockUtil.register(blockCoalGenerator);

        blockOilGenerator = new BlockOilGenerator();
        BlockUtil.register(blockOilGenerator);

        blockFermentingBarrel = new BlockFermentingBarrel();
        BlockUtil.register(blockFermentingBarrel);

        blockRice = new BlockPlant("blockRice", 6, 1, 2);
        BlockUtil.register(blockRice);
        CompatUtil.registerMFRPlant(blockRice);

        blockCanola = new BlockPlant("blockCanola", 4, 3, 3);
        BlockUtil.register(blockCanola);
        CompatUtil.registerMFRPlant(blockCanola);

        blockFlax = new BlockPlant("blockFlax", 6, 2, 4);
        BlockUtil.register(blockFlax);
        CompatUtil.registerMFRPlant(blockFlax);

        blockCoffee = new BlockPlant("blockCoffee", 6, 2, 2);
        BlockUtil.register(blockCoffee);
        CompatUtil.registerMFRPlant(blockCoffee);

        blockCompost = new BlockCompost();
        BlockUtil.register(blockCompost);

        blockMisc = new BlockMisc();
        BlockUtil.register(blockMisc, BlockMisc.TheItemBlock.class);

        blockFeeder = new BlockFeeder();
        BlockUtil.register(blockFeeder);

        blockGiantChest = new BlockGiantChest();
        BlockUtil.register(blockGiantChest);

        blockGrinder = new BlockGrinder(false);
        BlockUtil.register(blockGrinder);

        blockGrinderDouble = new BlockGrinder(true);
        BlockUtil.register(blockGrinderDouble);

        blockFurnaceDouble = new BlockFurnaceDouble();
        BlockUtil.register(blockFurnaceDouble);

        blockInputter = new BlockInputter(false);
        BlockUtil.register(blockInputter, BlockInputter.TheItemBlock.class);

        blockInputterAdvanced = new BlockInputter(true);
        BlockUtil.register(blockInputterAdvanced, BlockInputter.TheItemBlock.class);

        blockFishingNet = new BlockFishingNet();
        BlockUtil.register(blockFishingNet);

        blockFurnaceSolar = new BlockFurnaceSolar();
        BlockUtil.register(blockFurnaceSolar);

        blockHeatCollector = new BlockHeatCollector();
        BlockUtil.register(blockHeatCollector);

        blockItemRepairer = new BlockItemRepairer();
        BlockUtil.register(blockItemRepairer);

        blockGreenhouseGlass = new BlockGreenhouseGlass();
        BlockUtil.register(blockGreenhouseGlass);

        blockBreaker = new BlockBreaker(false);
        BlockUtil.register(blockBreaker);

        blockPlacer = new BlockBreaker(true);
        BlockUtil.register(blockPlacer);

        blockDropper = new BlockDropper();
        BlockUtil.register(blockDropper);

        blockFluidPlacer = new BlockFluidCollector(true);
        BlockUtil.register(blockFluidPlacer);

        blockFluidCollector = new BlockFluidCollector(false);
        BlockUtil.register(blockFluidCollector);

        blockCoffeeMachine = new BlockCoffeeMachine();
        BlockUtil.register(blockCoffeeMachine);

        blockPhantomBooster = new BlockPhantomBooster();
        BlockUtil.register(blockPhantomBooster);

        blockWildPlant = new BlockWildPlant();
        BlockUtil.register(blockWildPlant, BlockWildPlant.TheItemBlock.class);

        registerFluids();
    }

    public static void registerFluids(){
        //Canola Fluid
        String canolaOil = "canolaoil";
        if(!FluidRegistry.isFluidRegistered(canolaOil) || ConfigBoolValues.PREVENT_CANOLA_OVERRIDE.isEnabled()){
            fluidCanolaOil = new FluidAA(canolaOil).setRarity(EnumRarity.uncommon);
            FluidRegistry.registerFluid(fluidCanolaOil);
        }
        else{
            errorAlreadyRegistered("Canola Oil Fluid");
        }
        fluidCanolaOil = FluidRegistry.getFluid(canolaOil);

        //Canola Block
        if(fluidCanolaOil.getBlock() == null || ConfigBoolValues.PREVENT_CANOLA_BLOCK_OVERRIDE.isEnabled()){
            blockCanolaOil = new BlockFluidFlowing(fluidCanolaOil, Material.water, "blockCanolaOil");
            BlockUtil.register(blockCanolaOil);
        }
        else{
            errorAlreadyRegistered("Canola Oil Block");
        }
        blockCanolaOil = fluidCanolaOil.getBlock();

        //Oil Fluid
        String oil = "oil";
        if(!FluidRegistry.isFluidRegistered(oil) || ConfigBoolValues.PREVENT_OIL_OVERRIDE.isEnabled()){
            fluidOil = new FluidAA(oil).setRarity(EnumRarity.uncommon);
            FluidRegistry.registerFluid(fluidOil);
        }
        else{
            errorAlreadyRegistered("Oil Fluid");
        }
        fluidOil = FluidRegistry.getFluid(oil);

        //Oil Block
        if(fluidOil.getBlock() == null || ConfigBoolValues.PREVENT_OIL_BLOCK_OVERRIDE.isEnabled()){
            blockOil = new BlockFluidFlowing(fluidOil, Material.water, "blockOil");
            BlockUtil.register(blockOil);
        }
        else{
            errorAlreadyRegistered("Oil Block");
        }
        blockOil = fluidOil.getBlock();
    }

    public static void errorAlreadyRegistered(String str){
        ModUtil.LOGGER.warn(str+" from "+ModUtil.NAME+" is not getting used as it has already been registered by another Mod! If this causes Issues (which it shouldn't!), you can turn this off in the Config File!");
    }
}