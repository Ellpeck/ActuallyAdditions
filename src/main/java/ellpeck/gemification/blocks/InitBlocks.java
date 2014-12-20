package ellpeck.gemification.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block oreGem;
    public static Block blockCrucible;
    public static Block blockCrucibleFire;

    public static void init(){

        oreGem = new OreGem();
        blockCrucible = new BlockCrucible();
        blockCrucibleFire = new BlockCrucibleFire();

        GameRegistry.registerBlock(oreGem, OreGem.ItemBlockOreGem.class, oreGem.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCrucible, BlockCrucible.ItemBlockCrucible.class, blockCrucible.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCrucibleFire, BlockCrucibleFire.ItemBlockCrucibleFire.class, blockCrucibleFire.getUnlocalizedName().substring(5));

    }

}
