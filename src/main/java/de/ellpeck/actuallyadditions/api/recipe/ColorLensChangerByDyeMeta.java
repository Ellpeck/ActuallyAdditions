/*
 * This file ("ColorLensChangerByDyeMeta.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Changes an item's color by changing its metadata.
 * Much like dye and wool, 0 is white and 15 is black and it will cycle around.
 */
public class ColorLensChangerByDyeMeta implements IColorLensChanger {

    @Override
    public ItemStack modifyItem(ItemStack stack, BlockState hitBlockState, BlockPos hitBlock, IAtomicReconstructor tile) {
        ItemStack newStack = stack.copy();
        int meta = newStack.getItemDamage();
        newStack.setItemDamage((meta + 1) % 16);
        return newStack;
    }
}
