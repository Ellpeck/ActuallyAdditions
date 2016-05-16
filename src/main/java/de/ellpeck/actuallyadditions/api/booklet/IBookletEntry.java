/*
 * This file ("IBookletEntry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet;

import java.util.List;

public interface IBookletEntry{

    List<IBookletChapter> getChapters();

    void setChapters(List<IBookletChapter> chapters);

    String getUnlocalizedName();

    String getLocalizedName();

    String getLocalizedNameWithFormatting();

    void addChapter(IBookletChapter chapter);

}
