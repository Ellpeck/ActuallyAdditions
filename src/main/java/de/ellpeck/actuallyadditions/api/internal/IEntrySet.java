package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import net.minecraft.nbt.NBTTagCompound;

public interface IEntrySet{

    void setEntry(BookletPage page, IBookletChapter chapter, IBookletEntry entry, int pageInIndex);

    void removeEntry();

    NBTTagCompound writeToNBT();

    BookletPage getCurrentPage();

    IBookletEntry getCurrentEntry();

    IBookletChapter getCurrentChapter();

    int getPageInIndex();

    void setPage(BookletPage page);

    void setEntry(IBookletEntry entry);

    void setChapter(IBookletChapter chapter);

    void setPageInIndex(int page);
}
