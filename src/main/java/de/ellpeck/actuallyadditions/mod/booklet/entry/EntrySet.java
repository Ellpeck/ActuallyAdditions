/*
 * This file ("EntrySet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.entry;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.internal.IEntrySet;
import net.minecraft.nbt.NBTTagCompound;

public class EntrySet implements IEntrySet{

    public BookletPage page;
    public IBookletChapter chapter;
    public IBookletEntry entry;
    public int pageInIndex;

    public EntrySet(IBookletEntry entry){
        this(null, null, entry, 1);
    }

    public EntrySet(BookletPage page, IBookletChapter chapter, IBookletEntry entry, int pageInIndex){
        this.setEntry(page, chapter, entry, pageInIndex);
    }

    public static EntrySet readFromNBT(NBTTagCompound compound){
        if(compound != null){
            if(compound.hasKey("Entry")){
                int entry = compound.getInteger("Entry");
                int chapter = compound.getInteger("Chapter");
                int page = compound.getInteger("Page");

                IBookletEntry currentEntry = entry == -1 ? null : ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(entry);
                IBookletChapter currentChapter = chapter == -1 || entry == -1 || currentEntry.getChapters().size() <= chapter ? null : currentEntry.getChapters().get(chapter);
                BookletPage currentPage = chapter == -1 || currentChapter == null || currentChapter.getPages().length <= page-1 ? null : currentChapter.getPages()[page-1];
                int pageInIndex = compound.getInteger("PageInIndex");

                return new EntrySet(currentPage, currentChapter, currentEntry, pageInIndex);
            }
        }
        return new EntrySet(null);
    }

    @Override
    public void setEntry(BookletPage page, IBookletChapter chapter, IBookletEntry entry, int pageInIndex){
        this.page = page;
        this.chapter = chapter;
        this.entry = entry;
        this.pageInIndex = pageInIndex;
    }

    @Override
    public void removeEntry(){
        this.setEntry(null, null, null, 1);
    }

    @Override
    public NBTTagCompound writeToNBT(){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Entry", this.entry == null ? -1 : ActuallyAdditionsAPI.BOOKLET_ENTRIES.indexOf(this.entry));
        compound.setInteger("Chapter", this.entry == null || this.chapter == null ? -1 : this.entry.getChapters().indexOf(this.chapter));
        compound.setInteger("Page", this.page == null ? -1 : this.page.getID());
        compound.setInteger("PageInIndex", this.pageInIndex);
        return compound;
    }

    @Override
    public BookletPage getCurrentPage(){
        return this.page;
    }

    @Override
    public IBookletEntry getCurrentEntry(){
        return this.entry;
    }

    @Override
    public IBookletChapter getCurrentChapter(){
        return this.chapter;
    }

    @Override
    public int getPageInIndex(){
        return this.pageInIndex;
    }

    @Override
    public void setPage(BookletPage page){
        this.page = page;
    }

    @Override
    public void setEntry(IBookletEntry entry){
        this.entry = entry;
    }

    @Override
    public void setChapter(IBookletChapter chapter){
        this.chapter = chapter;
    }

    @Override
    public void setPageInIndex(int page){
        this.pageInIndex = page;
    }
}
