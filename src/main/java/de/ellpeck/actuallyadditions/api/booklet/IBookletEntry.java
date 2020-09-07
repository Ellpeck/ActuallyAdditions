package de.ellpeck.actuallyadditions.api.booklet;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

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
