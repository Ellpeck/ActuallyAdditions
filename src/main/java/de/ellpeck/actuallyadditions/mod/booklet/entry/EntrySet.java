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

    private BookletPage page;
    private IBookletChapter chapter;
    private IBookletEntry entry;
    private int pageInIndex;

    public EntrySet(IBookletEntry entry){
        this(null, null, entry, 1);
    }

    public EntrySet(BookletPage page, IBookletChapter chapter, IBookletEntry entry, int pageInIndex){
        this.setEntry(page, chapter, entry, pageInIndex);
    }

    public static EntrySet readFromNBT(NBTTagCompound compound){
        if(compound != null){
            String entryName = compound.getString("Entry");
            if(!entryName.isEmpty()){
                for(IBookletEntry entry : ActuallyAdditionsAPI.BOOKLET_ENTRIES){
                    if(entryName.equals(entry.getIdentifier())){
                        int indexPage = compound.getInteger("PageInIndex");

                        String chapterName = compound.getString("Chapter");
                        if(!chapterName.isEmpty()){
                            for(IBookletChapter chapter : entry.getChapters()){
                                if(chapterName.equals(chapter.getIdentifier())){
                                    int page = compound.getInteger("Page");
                                    if(page != -1){
                                        return new EntrySet(chapter.getPageById(page), chapter, entry, indexPage);
                                    }
                                    break;
                                }
                            }
                        }
                        return new EntrySet(null, null, entry, indexPage);
                    }
                }
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
        compound.setInteger("PageInIndex", this.pageInIndex);
        compound.setString("Entry", this.entry != null ? this.entry.getIdentifier() : "");
        compound.setString("Chapter", this.chapter != null ? this.chapter.getIdentifier() : "");
        compound.setInteger("Page", this.page != null ? this.page.getChapter().getPageId(this.page) : -1);

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
