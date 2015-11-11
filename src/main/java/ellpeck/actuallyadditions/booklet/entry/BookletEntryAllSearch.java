/*
 * This file ("BookletEntryAllSearch.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.entry;

import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;

import java.util.ArrayList;

public class BookletEntryAllSearch extends BookletEntry{

    public ArrayList<BookletChapter> allChapters = new ArrayList<BookletChapter>();

    public BookletEntryAllSearch(String unlocalizedName){
        super(unlocalizedName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addChapter(BookletChapter chapter){
        this.allChapters.add(chapter);
        this.chapters = (ArrayList<BookletChapter>)this.allChapters.clone();
    }
}
