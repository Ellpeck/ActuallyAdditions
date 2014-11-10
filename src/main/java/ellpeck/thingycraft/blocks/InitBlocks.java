package ellpeck.thingycraft.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class InitBlocks{

    public static Block oreGem;

    public static void init(){

        oreGem = new OreGem();

        GameRegistry.registerBlock(oreGem, ItemBlockOreGem.class, oreGem.getUnlocalizedName().substring(5));

    }

}
