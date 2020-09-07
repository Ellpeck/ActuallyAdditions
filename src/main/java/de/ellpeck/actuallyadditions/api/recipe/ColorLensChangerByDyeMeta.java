package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Changes an item's color by changing its metadata.
 * Much like dye and wool, 0 is white and 15 is black and it will cycle around.
 */
public class ColorLensChangerByDyeMeta implements IColorLensChanger {

    /**
     * @todo: this is very very likely to no longer work...
     */
    @Override
    public ItemStack modifyItem(ItemStack stack, BlockState hitBlockState, BlockPos hitBlock, IAtomicReconstructor tile) {
        ItemStack newStack = stack.copy();
        int meta = newStack.getDamage();
        newStack.setDamage((meta + 1) % 16);
        return newStack;
    }
}
