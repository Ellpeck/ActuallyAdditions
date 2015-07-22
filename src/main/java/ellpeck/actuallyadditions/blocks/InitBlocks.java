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
import org.apache.logging.log4j.Level;

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

    public static Block blockOreFactory;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Blocks...");

        blockXPSolidifier = new BlockXPSolidifier();
        BlockUtil.register(blockXPSolidifier, BlockXPSolidifier.TheItemBlock.class);

        blockTestifiBucksGreenWall = new BlockGeneric("blockTestifiBucksGreenWall");
        BlockUtil.register(blockTestifiBucksGreenWall, BlockGeneric.TheItemBlock.class);
        blockTestifiBucksWhiteWall = new BlockGeneric("blockTestifiBucksWhiteWall");
        BlockUtil.register(blockTestifiBucksWhiteWall, BlockGeneric.TheItemBlock.class);
        blockTestifiBucksGreenStairs = new BlockStair(blockTestifiBucksGreenWall, "blockTestifiBucksGreenStairs");
        BlockUtil.register(blockTestifiBucksGreenStairs, BlockStair.TheItemBlock.class);
        blockTestifiBucksWhiteStairs = new BlockStair(blockTestifiBucksWhiteWall, "blockTestifiBucksWhiteStairs");
        BlockUtil.register(blockTestifiBucksWhiteStairs, BlockStair.TheItemBlock.class);
        blockTestifiBucksGreenSlab = new BlockSlabs("blockTestifiBucksGreenSlab", blockTestifiBucksGreenWall);
        BlockUtil.register(blockTestifiBucksGreenSlab, BlockSlabs.TheItemBlock.class);
        blockTestifiBucksWhiteSlab = new BlockSlabs("blockTestifiBucksWhiteSlab", blockTestifiBucksWhiteWall);
        BlockUtil.register(blockTestifiBucksWhiteSlab, BlockSlabs.TheItemBlock.class);

        blockOreFactory = new BlockOreFactory();
        BlockUtil.register(blockOreFactory, BlockOreFactory.TheItemBlock.class);

        blockColoredLamp = new BlockColoredLamp(false);
        BlockUtil.register(blockColoredLamp, BlockColoredLamp.TheItemBlock.class);
        blockColoredLampOn = new BlockColoredLamp(true);
        BlockUtil.register(blockColoredLampOn, BlockColoredLamp.TheItemBlock.class, false);
        blockLampPowerer = new BlockLampPowerer();
        BlockUtil.register(blockLampPowerer, BlockLampPowerer.TheItemBlock.class);

        blockTreasureChest = new BlockTreasureChest();
        BlockUtil.register(blockTreasureChest, BlockTreasureChest.TheItemBlock.class);

        blockEnergizer = new BlockEnergizer(true);
        BlockUtil.register(blockEnergizer, BlockEnergizer.TheItemBlock.class);

        blockEnervator = new BlockEnergizer(false);
        BlockUtil.register(blockEnervator, BlockEnergizer.TheItemBlock.class);

        blockLavaFactoryController = new BlockLavaFactoryController();
        BlockUtil.register(blockLavaFactoryController, BlockLavaFactoryController.TheItemBlock.class);

        blockCanolaPress = new BlockCanolaPress();
        BlockUtil.register(blockCanolaPress, BlockCanolaPress.TheItemBlock.class);

        blockPhantomface = new BlockPhantomface(BlockPhantomface.FACE);
        BlockUtil.register(blockPhantomface, BlockPhantomface.TheItemBlock.class);

        blockPhantomPlacer = new BlockPhantomface(BlockPhantomface.PLACER);
        BlockUtil.register(blockPhantomPlacer, BlockPhantomface.TheItemBlock.class);

        blockPhantomLiquiface = new BlockPhantomface(BlockPhantomface.LIQUIFACE);
        BlockUtil.register(blockPhantomLiquiface, BlockPhantomface.TheItemBlock.class);

        blockPhantomEnergyface = new BlockPhantomface(BlockPhantomface.ENERGYFACE);
        BlockUtil.register(blockPhantomEnergyface, BlockPhantomface.TheItemBlock.class);

        blockPhantomBreaker = new BlockPhantomface(BlockPhantomface.BREAKER);
        BlockUtil.register(blockPhantomBreaker, BlockPhantomface.TheItemBlock.class);

        blockCoalGenerator = new BlockCoalGenerator();
        BlockUtil.register(blockCoalGenerator, BlockCoalGenerator.TheItemBlock.class);

        blockOilGenerator = new BlockOilGenerator();
        BlockUtil.register(blockOilGenerator, BlockOilGenerator.TheItemBlock.class);

        blockFermentingBarrel = new BlockFermentingBarrel();
        BlockUtil.register(blockFermentingBarrel, BlockFermentingBarrel.TheItemBlock.class);

        blockRice = new BlockPlant("blockRice", 6, 1, 2);
        BlockUtil.register(blockRice, BlockPlant.TheItemBlock.class, false);
        CompatUtil.registerMFRPlant(blockRice);

        blockCanola = new BlockPlant("blockCanola", 4, 3, 3);
        BlockUtil.register(blockCanola, BlockPlant.TheItemBlock.class, false);
        CompatUtil.registerMFRPlant(blockCanola);

        blockFlax = new BlockPlant("blockFlax", 6, 2, 4);
        BlockUtil.register(blockFlax, BlockPlant.TheItemBlock.class, false);
        CompatUtil.registerMFRPlant(blockFlax);

        blockCoffee = new BlockPlant("blockCoffee", 6, 2, 2);
        BlockUtil.register(blockCoffee, BlockPlant.TheItemBlock.class, false);
        CompatUtil.registerMFRPlant(blockCoffee);

        blockCompost = new BlockCompost();
        BlockUtil.register(blockCompost, BlockCompost.TheItemBlock.class);

        blockMisc = new BlockMisc();
        BlockUtil.register(blockMisc, BlockMisc.TheItemBlock.class);

        blockFeeder = new BlockFeeder();
        BlockUtil.register(blockFeeder, BlockFeeder.TheItemBlock.class);

        blockGiantChest = new BlockGiantChest();
        BlockUtil.register(blockGiantChest, BlockGiantChest.TheItemBlock.class);

        blockGrinder = new BlockGrinder(false);
        BlockUtil.register(blockGrinder, BlockGrinder.TheItemBlock.class);

        blockGrinderDouble = new BlockGrinder(true);
        BlockUtil.register(blockGrinderDouble, BlockGrinder.TheItemBlock.class);

        blockFurnaceDouble = new BlockFurnaceDouble();
        BlockUtil.register(blockFurnaceDouble, BlockFurnaceDouble.TheItemBlock.class);

        blockInputter = new BlockInputter(false);
        BlockUtil.register(blockInputter, BlockInputter.TheItemBlock.class);

        blockInputterAdvanced = new BlockInputter(true);
        BlockUtil.register(blockInputterAdvanced, BlockInputter.TheItemBlock.class);

        blockFishingNet = new BlockFishingNet();
        BlockUtil.register(blockFishingNet, BlockFishingNet.TheItemBlock.class);

        blockFurnaceSolar = new BlockFurnaceSolar();
        BlockUtil.register(blockFurnaceSolar, BlockFurnaceSolar.TheItemBlock.class);

        blockHeatCollector = new BlockHeatCollector();
        BlockUtil.register(blockHeatCollector, BlockHeatCollector.TheItemBlock.class);

        blockItemRepairer = new BlockItemRepairer();
        BlockUtil.register(blockItemRepairer, BlockItemRepairer.TheItemBlock.class);

        blockGreenhouseGlass = new BlockGreenhouseGlass();
        BlockUtil.register(blockGreenhouseGlass, BlockGreenhouseGlass.TheItemBlock.class);

        blockBreaker = new BlockBreaker(false);
        BlockUtil.register(blockBreaker, BlockBreaker.TheItemBlock.class);

        blockPlacer = new BlockBreaker(true);
        BlockUtil.register(blockPlacer, BlockBreaker.TheItemBlock.class);

        blockDropper = new BlockDropper();
        BlockUtil.register(blockDropper, BlockDropper.TheItemBlock.class);

        blockFluidPlacer = new BlockFluidCollector(true);
        BlockUtil.register(blockFluidPlacer, BlockFluidCollector.TheItemBlock.class);

        blockFluidCollector = new BlockFluidCollector(false);
        BlockUtil.register(blockFluidCollector, BlockFluidCollector.TheItemBlock.class);

        blockCoffeeMachine = new BlockCoffeeMachine();
        BlockUtil.register(blockCoffeeMachine, BlockCoffeeMachine.TheItemBlock.class);

        blockPhantomBooster = new BlockPhantomBooster();
        BlockUtil.register(blockPhantomBooster, BlockPhantomBooster.TheItemBlock.class);

        blockWildPlant = new BlockWildPlant();
        BlockUtil.register(blockWildPlant, BlockWildPlant.TheItemBlock.class, false);

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
            BlockUtil.register(blockCanolaOil, BlockFluidFlowing.TheItemBlock.class, false);
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
            BlockUtil.register(blockOil, BlockFluidFlowing.TheItemBlock.class, false);
        }
        else{
            errorAlreadyRegistered("Oil Block");
        }
        blockOil = fluidOil.getBlock();
    }

    public static void errorAlreadyRegistered(String str){
        ModUtil.LOGGER.log(Level.WARN, str + " from "+ModUtil.NAME+" is not getting used as it has already been registered by another Mod! If this causes Issues (which it shouldn't!), you can turn this off in the Config File!");
    }
}