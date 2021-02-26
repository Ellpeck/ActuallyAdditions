/*
 * This file ("IBookletEntry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

public interface IBookletEntry {

    List<IBookletChapter> getAllChapters();

    String getIdentifier();

    @OnlyIn(Dist.CLIENT)
    String getLocalizedName();

    @OnlyIn(Dist.CLIENT)
    String getLocalizedNameWithFormatting();

    void addChapter(IBookletChapter chapter);

    @OnlyIn(Dist.CLIENT)
    List<IBookletChapter> getChaptersForDisplay(String searchBarText);

    int getSortingPriority();

    @OnlyIn(Dist.CLIENT)
    boolean visibleOnFrontPage();
}
