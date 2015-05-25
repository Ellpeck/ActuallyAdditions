package ellpeck.actuallyadditions.blocks;

import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;

public class InitBlocks{

    public static Block blockCompost;
    public static Block blockMisc;
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

    public static Fluid fluidCanolaOil;
    public static Block blockCanolaOil;
    public static Fluid fluidOil;
    public static Block blockOil;

    public static Block blockCanolaPress;
    public static Block blockFermentingBarrel;

    public static Block blockCoalGenerator;
    public static Block blockOilGenerator;

    public static Block blockPhantomface;
    //public static Block blockPhantomPlacer;
    //public static Block blockPhantomBreaker;

    public static void init(){
        Util.logInfo("Initializing Blocks...");

        fluidCanolaOil = new FluidAA("canolaOil").setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.uncommon);
        FluidRegistry.registerFluid(fluidCanolaOil);
        blockCanolaOil = new BlockFluidFlowing(fluidCanolaOil, Material.water, "blockCanolaOil");
        BlockUtil.register(blockCanolaOil, BlockFluidFlowing.TheItemBlock.class, false);

        fluidOil = new FluidAA("oil").setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.uncommon);
        FluidRegistry.registerFluid(fluidOil);
        blockOil = new BlockFluidFlowing(fluidOil, Material.water, "blockOil");
        BlockUtil.register(blockOil, BlockFluidFlowing.TheItemBlock.class, false);

        blockCanolaPress = new BlockCanolaPress();
        BlockUtil.register(blockCanolaPress, BlockCanolaPress.TheItemBlock.class);

        blockPhantomface = new BlockPhantomface(BlockPhantomface.FACE);
        BlockUtil.register(blockPhantomface, BlockPhantomface.TheItemBlock.class);

        //blockPhantomPlacer = new BlockPhantomface(BlockPhantomface.PLACER);
        //BlockUtil.register(blockPhantomPlacer, BlockPhantomface.TheItemBlock.class);

        //blockPhantomBreaker = new BlockPhantomface(BlockPhantomface.BREAKER);
        //BlockUtil.register(blockPhantomBreaker, BlockPhantomface.TheItemBlock.class);

        blockCoalGenerator = new BlockCoalGenerator();
        BlockUtil.register(blockCoalGenerator, BlockCoalGenerator.TheItemBlock.class);

        blockOilGenerator = new BlockOilGenerator();
        BlockUtil.register(blockOilGenerator, BlockOilGenerator.TheItemBlock.class);

        blockFermentingBarrel = new BlockFermentingBarrel();
        BlockUtil.register(blockFermentingBarrel, BlockFermentingBarrel.TheItemBlock.class);

        blockRice = new BlockPlant("blockRice", 6);
        BlockUtil.register(blockRice, BlockPlant.TheItemBlock.class, false);
        FactoryRegistry.sendMessage("registerHarvestable", blockRice);
        FactoryRegistry.sendMessage("registerFertilizable", blockRice);

        blockCanola = new BlockPlant("blockCanola", 4);
        BlockUtil.register(blockCanola, BlockPlant.TheItemBlock.class, false);
        FactoryRegistry.sendMessage("registerHarvestable", blockCanola);
        FactoryRegistry.sendMessage("registerFertilizable", blockCanola);

        blockCompost = new BlockCompost();
        BlockUtil.register(blockCompost, BlockCompost.TheItemBlock.class);

        blockMisc = new BlockMisc();
        BlockUtil.register(blockMisc, BlockMisc.TheItemBlock.class, BlockMisc.allMiscBlocks);

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
    }
}