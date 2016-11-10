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
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.booklet.button.EntryButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public class GuiEntry extends GuiBooklet{

    //The page in the entry. Say you have 2 more chapters than fit on one double page, then those 2 would be displayed on entryPage 1 instead.
    private final int entryPage;
    private final IBookletEntry entry;

    public GuiEntry(GuiScreen previousScreen, GuiBookletBase parentPage, IBookletEntry entry, int entryPage){
        super(previousScreen, parentPage);
        this.entryPage = entryPage;
        this.entry = entry;
    }

    public GuiEntry(GuiScreen previousScreen, GuiBookletBase parentPage, IBookletEntry entry, IBookletChapter chapterForPageCalc){
        this(previousScreen, parentPage, entry, calcEntryPage(entry, chapterForPageCalc));
    }

    private static int calcEntryPage(IBookletEntry entry, IBookletChapter chapterForPageCalc){
        int index = entry.getAllChapters().indexOf(chapterForPageCalc);
        return index/(BUTTONS_PER_PAGE*2);
    }

    @Override
    public void initGui(){
        super.initGui();

        int idOffset = this.entryPage*(BUTTONS_PER_PAGE*2);
        List<IBookletChapter> chapters = this.entry.getChaptersForGuiDisplaying(this, null /*TODO Insert search bar text here*/);
        for(int x = 0; x < 2; x++){
            for(int y = 0; y < BUTTONS_PER_PAGE; y++){
                int id = y+x*BUTTONS_PER_PAGE;
                if(chapters.size() > id+idOffset){
                    IBookletChapter chapter = chapters.get(id+idOffset);
                    this.buttonList.add(new EntryButton(id, this.guiLeft+14+x*142, this.guiTop+11+y*13, 115, 10, chapter.getLocalizedNameWithFormatting(), chapter.getDisplayItemStack()));
                }
                else{
                    return;
                }
            }
        }
    }
}
