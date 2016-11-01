/*
 * This file ("BookletEntry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.entry;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class BookletEntry implements IBookletEntry{

    private final String identifier;
    public List<IBookletChapter> chapters = new ArrayList<IBookletChapter>();
    private TextFormatting color;

    public BookletEntry(String identifier){
        this.identifier = identifier;
        ActuallyAdditionsAPI.addBookletEntry(this);

        this.color = TextFormatting.RESET;
    }

    @Override
    public List<IBookletChapter> getChapters(){
        return this.chapters;
    }

    @Override
    public void setChapters(List<IBookletChapter> chapters){
        this.chapters.clear();
        this.chapters.addAll(chapters);
    }

    @Override
    public String getIdentifier(){
        return this.identifier;
    }

    @Override
    public String getLocalizedName(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID+".indexEntry."+this.getIdentifier()+".name");
    }

    @Override
    public String getLocalizedNameWithFormatting(){
        return this.color+this.getLocalizedName();
    }

    @Override
    public void addChapter(IBookletChapter chapter){
        this.chapters.add(chapter);
    }

    public BookletEntry setImportant(){
        this.color = TextFormatting.DARK_GREEN;
        return this;
    }

    public BookletEntry setSpecial(){
        this.color = TextFormatting.DARK_PURPLE;
        return this;
    }

}
