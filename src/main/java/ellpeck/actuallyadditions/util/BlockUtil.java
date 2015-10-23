/*
 * This file ("BlockUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import ellpeck.actuallyadditions.items.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BlockUtil{

    public static void register(Block block, Class<? extends ItemBlock> itemBlock){
        register(block, itemBlock, true);
    }

    public static void register(Block block){
        register(block, ItemBlockBase.class, true);
    }

    public static void register(Block block, boolean addTab){
        register(block, ItemBlockBase.class, addTab);
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, boolean addTab){
        block.setCreativeTab(addTab ? CreativeTab.instance : null);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((IActAddItemOrBlock)block).getName());
    }

    public static String createUnlocalizedName(Block block){
        return ModUtil.MOD_ID_LOWER+"."+((IActAddItemOrBlock)block).getName();
    }
}
