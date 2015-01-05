package ellpeck.someprettytechystuff.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block oreGem;
    public static Block blockCrucible;
    public static Block blockCrucibleFire;

    public static Block blockCropIron;
    public static Block blockCropGold;
    public static Block blockCropRedstone;
    public static Block blockCropDiamond;

    public static void init(){

        oreGem = new OreGem();
        blockCrucible = new BlockCrucible();
        blockCrucibleFire = new BlockCrucibleFire();

        blockCropIron = new BlockCrop("blockCropIron", 4);
        blockCropGold = new BlockCrop("blockCropGold", 4);
        blockCropRedstone = new BlockCrop("blockCropRedstone", 4);
        blockCropDiamond = new BlockCrop("blockCropDiamond", 4);

        GameRegistry.registerBlock(oreGem, OreGem.ItemBlockOreGem.class, oreGem.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCrucible, DefaultItemBlock.class, blockCrucible.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCrucibleFire, DefaultItemBlock.class, blockCrucibleFire.getUnlocalizedName().substring(5));

        GameRegistry.registerBlock(blockCropIron, blockCropIron.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCropGold, blockCropGold.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCropRedstone, blockCropRedstone.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockCropDiamond, blockCropDiamond.getUnlocalizedName().substring(5));

    }

}
