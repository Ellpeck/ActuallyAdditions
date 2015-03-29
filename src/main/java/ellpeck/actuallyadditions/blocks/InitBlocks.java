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

    public static void init(){
        Util.logInfo("Initializing Blocks...");

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

        blockInputter = new BlockInputter();
        BlockUtil.register(blockInputter, BlockInputter.TheItemBlock.class);
    }
}