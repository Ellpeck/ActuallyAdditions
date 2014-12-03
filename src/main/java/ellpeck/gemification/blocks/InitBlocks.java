package ellpeck.gemification.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block oreGem;
    public static Block blockCrucible;

    public static void init(){

        oreGem = new OreGem();
        blockCrucible = new BlockCrucible();

        GameRegistry.registerBlock(oreGem, ItemBlockOreGem.class, oreGem.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCrucible, ItemBlockCrucible.class, blockCrucible.getUnlocalizedName().substring(5));

    }

}
