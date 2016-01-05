/*
 * This file ("BookletEntryAllSearch.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.entry;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapter;

import java.util.ArrayList;
import java.util.List;

public class BookletEntryAllSearch extends BookletEntry{

    public ArrayList<IBookletChapter> allChapters = new ArrayList<IBookletChapter>();

    public BookletEntryAllSearch(String unlocalizedName){
        super(unlocalizedName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addChapter(BookletChapter chapter){
        this.allChapters.add(chapter);
        this.chapters = (ArrayList<IBookletChapter>)this.allChapters.clone();
    }

    @Override
    public void setChapters(List<IBookletChapter> chapters){
        this.allChapters = (ArrayList<IBookletChapter>)chapters;
    }
}
