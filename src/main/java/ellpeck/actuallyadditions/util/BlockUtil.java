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
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BlockUtil{

    public static String createUnlocalizedName(Block block){
        return ModUtil.MOD_ID_LOWER+"."+((INameableItem)block).getName();
    }

    @SuppressWarnings("unchecked")
    public static void register(Block block, boolean addTab){
        block.setCreativeTab(addTab ? CreativeTab.instance : null);
        block.setBlockName(createUnlocalizedName(block));

        for(Class sub : block.getClass().getDeclaredClasses()){
            if(sub.getSuperclass() == ItemBlock.class){
                GameRegistry.registerBlock(block, sub, ((INameableItem)block).getName());
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void register(Block block){
        register(block, true);
    }
}
