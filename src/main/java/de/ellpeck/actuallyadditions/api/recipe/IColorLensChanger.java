package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;

/**
 * Used for the Atomic Reconstructor's Color Lens changing algorythm.
 * When registering a new item to be changed, it needs an IColorLensChanger which
 * is the method with which the item will be changed.
 *
 * See ColorLensChangerByDyeMeta for reference.
 */
public interface IColorLensChanger{

    /**
     * Modifies the given item.
     * Will only be called with stacks containing items that are registered with
     * this IColorLensChanger.
     *
     * @param stack the stack to modify
     * @return the modified stack. Please make sure to return a modified COPY of the input stack.
     */
    ItemStack modifyItem(ItemStack stack);

}
