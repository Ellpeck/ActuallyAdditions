package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BlockUtil{

    public static String createUnlocalizedName(Block block){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName();
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, boolean addTab){
        block.setCreativeTab(addTab ? CreativeTab.instance : null);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock){
        register(block, itemBlock, true);
    }
}
