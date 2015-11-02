/*
 * This file ("IActAddItemOrBlock.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * The indicator interface for any block or item that is part of ActuallyAdditions
 */
public interface IActAddItemOrBlock{

    String getName();

    EnumRarity getRarity(ItemStack stack);

}
