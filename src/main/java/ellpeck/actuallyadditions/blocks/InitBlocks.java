package ellpeck.actuallyadditions.blocks;

import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;

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

    public static void init(){
        Util.logInfo("Initializing Blocks...");

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