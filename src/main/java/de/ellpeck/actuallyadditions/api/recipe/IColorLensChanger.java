/*
 * This file ("IColorLensChanger.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Used for the Atomic Reconstructor's Color Lens changing algorythm.
 * When registering a new item to be changed, it needs an IColorLensChanger which
 * is the method with which the item will be changed.
 * <p>
 * See ColorLensChangerByDyeMeta for reference.
 */
public interface IColorLensChanger{

    /**
     * Modifies the given item.
     * Will only be called with stacks containing items that are registered with
     * this IColorLensChanger.
     *
     * @param stack         the stack to modify
     * @param hitBlockState The state of the block that was hit
     * @param hitBlock      the block that was hit (usually air, or the block that is also in the stack)
     * @param tile          the Reconstructor doing the color conversion
     * @return the modified stack. Please make sure to return a modified COPY of the input stack.
     */
    ItemStack modifyItem(ItemStack stack, IBlockState hitBlockState, BlockPos hitBlock, IAtomicReconstructor tile);

}
