/*
 * This file ("EntrySet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import net.minecraft.nbt.NBTTagCompound;

public class EntrySet{

    public BookletPage page;
    public BookletChapter chapter;
    public BookletEntry entry;
    public int pageInIndex;

    public EntrySet(BookletPage page, BookletChapter chapter, BookletEntry entry, int pageInIndex){
        this.setEntry(page, chapter, entry, pageInIndex);
    }

    public EntrySet(BookletEntry entry){
        this(null, null, entry, 1);
    }

    public void removeEntry(){
        this.setEntry(null, null, null, 1);
    }

    public void setEntry(BookletPage page, BookletChapter chapter, BookletEntry entry, int pageInIndex){
        this.page = page;
        this.chapter = chapter;
        this.entry = entry;
        this.pageInIndex = pageInIndex;
    }

    public NBTTagCompound writeToNBT(){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Entry", entry == null ? -1 : InitBooklet.entries.indexOf(entry));
        compound.setInteger("Chapter", entry == null || chapter == null ? -1 : entry.chapters.indexOf(chapter));
        compound.setInteger("Page", page == null ? -1 : page.getID());
        compound.setInteger("PageInIndex", pageInIndex);
        return compound;
    }

    public static EntrySet readFromNBT(NBTTagCompound compound){
        if(compound.hasKey("Entry")){
            int entry = compound.getInteger("Entry");
            int chapter = compound.getInteger("Chapter");
            int page = compound.getInteger("Page");

            BookletEntry currentEntry = entry == -1 ? null : InitBooklet.entries.get(entry);
            BookletChapter currentChapter = chapter == -1 || entry == -1 || currentEntry.chapters.size() <= chapter ? null : currentEntry.chapters.get(chapter);
            BookletPage currentPage = chapter == -1 || currentChapter == null || currentChapter.pages.length <= page-1 ? null : currentChapter.pages[page-1];
            int pageInIndex = compound.getInteger("PageInIndex");

            return new EntrySet(currentPage, currentChapter, currentEntry, pageInIndex);
        }
        return null;
    }
}
