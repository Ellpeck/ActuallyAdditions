package ellpeck.someprettyrandomstuff.blocks;


import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block blockCompost;
    public static Block blockMisc;
    public static Block blockFeeder;

    public static void init(){
        Util.logInfo("Initializing Blocks...");

        blockCompost = new BlockCompost();
        GameRegistry.registerBlock(blockCompost, DefaultItemBlock.class, Util.getSubbedUnlocalized(blockCompost));

        blockMisc = new BlockMisc();
        GameRegistry.registerBlock(blockMisc, BlockMisc.ItemBlockMisc.class, Util.getSubbedUnlocalized(blockMisc));

        blockFeeder = new BlockFeeder();
        GameRegistry.registerBlock(blockFeeder, DefaultItemBlock.class, Util.getSubbedUnlocalized(blockFeeder));

    }
}
