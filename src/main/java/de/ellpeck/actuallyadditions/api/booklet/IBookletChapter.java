/*
 * This file ("IBookletChapter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBookletChapter{

    IBookletPage[] getAllPages();

    @SideOnly(Side.CLIENT)
    String getLocalizedName();

    @SideOnly(Side.CLIENT)
    String getLocalizedNameWithFormatting();

    IBookletEntry getEntry();

    ItemStack getDisplayItemStack();

    String getIdentifier();

    int getPageIndex(IBookletPage page);

    int getSortingPriority();
}
