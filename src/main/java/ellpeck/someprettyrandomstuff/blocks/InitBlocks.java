package ellpeck.someprettyrandomstuff.blocks;


import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block blockCompost;
    public static Block blockMisc;
    public static Block blockFeeder;
    public static Block blockGiantChest;

    public static void init(){
        Util.logInfo("Initializing Blocks...");

        blockCompost = new BlockCompost();
        GameRegistry.registerBlock(blockCompost, DefaultItemBlock.class, ((IName)blockCompost).getName());

        blockMisc = new BlockMisc();
        GameRegistry.registerBlock(blockMisc, BlockMisc.ItemBlockMisc.class, ((IName)blockMisc).getName());

        blockFeeder = new BlockFeeder();
        GameRegistry.registerBlock(blockFeeder, DefaultItemBlock.class, ((IName)blockFeeder).getName());

        blockGiantChest = new BlockGiantChest();
        GameRegistry.registerBlock(blockGiantChest, DefaultItemBlock.class, ((IName)blockGiantChest).getName());
    }
}