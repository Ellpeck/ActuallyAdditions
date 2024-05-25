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

import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

//TODO: We're using Patchouli API for the new booklets. Do we still need this?
public interface IBookletChapter {

    IBookletPage[] getAllPages();

    @OnlyIn(Dist.CLIENT)
    String getLocalizedName();

    @OnlyIn(Dist.CLIENT)
    String getLocalizedNameWithFormatting();

    IBookletEntry getEntry();

    ItemStack getDisplayItemStack();

    String getIdentifier();

    int getPageIndex(IBookletPage page);

    int getSortingPriority();
}
