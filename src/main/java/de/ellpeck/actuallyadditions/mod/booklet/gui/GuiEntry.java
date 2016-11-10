/*
 * This file ("GuiEntry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.internal.IEntryGui;
import net.minecraft.client.gui.GuiScreen;

public class GuiEntry extends GuiBooklet implements IEntryGui{

    //The page in the entry. Say you have 2 more chapters than fit on one double page, then those 2 would be displayed on entryPage 1 instead.
    private final int entryPage;
    private final IBookletEntry entry;

    public GuiEntry(GuiScreen parent, IBookletEntry entry, int entryPage){
        super(parent);
        this.entry = entry;
        this.entryPage = entryPage;
    }

    public GuiEntry(GuiScreen parent, IBookletEntry entry, IBookletChapter chapterForPageCalc){
        this(parent, entry, calcEntryPage(entry, chapterForPageCalc));
    }

    private static int calcEntryPage(IBookletEntry entry, IBookletChapter chapterForPageCalc){
        int index = entry.getAllChapters().indexOf(chapterForPageCalc);
        return index/(BUTTONS_PER_PAGE*2);
    }

    @Override
    public IBookletEntry getEntry(){
        return this.entry;
    }

    @Override
    public int getEntryPage(){
        return this.entryPage;
    }
}
