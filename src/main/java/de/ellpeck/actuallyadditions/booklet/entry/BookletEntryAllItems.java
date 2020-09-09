package de.ellpeck.actuallyadditions.common.booklet.entry;

import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;

public class BookletEntryAllItems extends BookletEntry {

    public BookletEntryAllItems(String identifier) {
        super(identifier, -Integer.MAX_VALUE);
    }

    @Override
    public void addChapter(IBookletChapter chapter) {

    }

    @Override
    public List<IBookletChapter> getAllChapters() {
        return ActuallyAdditionsAPI.ALL_CHAPTERS;
    }
}
