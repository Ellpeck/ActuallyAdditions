/*
 * This file ("BookletPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet;

import de.ellpeck.actuallyadditions.api.booklet.internal.IPageGui;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IBookletPage{

    ItemStack[] getItemStacksForPage();

    FluidStack[] getFluidStacksForPage();

    IBookletChapter getChapter();

    void setChapter(IBookletChapter chapter);

    IPageGui createGui();

    String getInfoText();
}
