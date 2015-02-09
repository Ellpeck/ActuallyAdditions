package ellpeck.someprettyrandomstuff.blocks;


import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitBlocks{

    public static Block blockCompost;

    public static void init(){
        Util.logInfo("Initializing Blocks...");

        blockCompost = new BlockCompost();
        GameRegistry.registerBlock(blockCompost, DefaultItemBlock.class, blockCompost.getUnlocalizedName().substring(5));

    }

}
