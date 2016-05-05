package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;

/**
 * Changes an item's color by changing its metadata.
 * Much like dye and wool, 0 is white and 15 is black and it will cycle around.
 */
public class ColorLensChangerByDyeMeta implements IColorLensChanger{

    @Override
    public ItemStack modifyItem(ItemStack stack){
        ItemStack newStack = stack.copy();
        int meta = newStack.getItemDamage();
        if(meta >= 15){
            newStack.setItemDamage(0);
        }
        else{
            newStack.setItemDamage(meta+1);
        }
        return newStack;
    }
}
