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
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.booklet.button.EntryButton;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiEntry extends GuiBooklet{

    //The page in the entry. Say you have 2 more chapters than fit on one double page, then those 2 would be displayed on entryPage 1 instead.
    private final int entryPage;
    private final IBookletEntry entry;
    private final List<IBookletChapter> chapters;
    private final String searchText;
    private final boolean focusSearch;

    public GuiEntry(GuiScreen previousScreen, GuiBookletBase parentPage, IBookletEntry entry, int entryPage, String search, boolean focusSearch){
        super(previousScreen, parentPage);
        this.entryPage = entryPage;
        this.entry = entry;
        this.searchText = search;
        this.focusSearch = focusSearch;
        this.chapters = entry.getChaptersForDisplay(search);
    }

    public GuiEntry(GuiScreen previousScreen, GuiBookletBase parentPage, IBookletEntry entry, IBookletChapter chapterForPageCalc, String search, boolean focusSearch){
        this(previousScreen, parentPage, entry, calcEntryPage(entry, chapterForPageCalc, search), search, focusSearch);
    }

    private static int calcEntryPage(IBookletEntry entry, IBookletChapter chapterForPageCalc, String search){
        int index = entry.getChaptersForDisplay(search).indexOf(chapterForPageCalc);
        return index/(BUTTONS_PER_PAGE*2);
    }

    @Override
    public void initGui(){
        super.initGui();

        if(this.hasSearchBar() && this.searchText != null){
            this.searchField.setText(this.searchText);
            if(this.focusSearch){
                this.searchField.setFocused(true);
            }
        }

        int idOffset = this.entryPage*(BUTTONS_PER_PAGE*2);
        for(int x = 0; x < 2; x++){
            for(int y = 0; y < BUTTONS_PER_PAGE; y++){
                int id = y+x*BUTTONS_PER_PAGE;
                if(this.chapters.size() > id+idOffset){
                    IBookletChapter chapter = this.chapters.get(id+idOffset);
                    this.buttonList.add(new EntryButton(id, this.guiLeft+14+x*142, this.guiTop+11+y*13, 115, 10, chapter.getLocalizedNameWithFormatting(), chapter.getDisplayItemStack()));
                }
                else{
                    return;
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(button instanceof EntryButton){
            int actualId = button.id+this.entryPage*(BUTTONS_PER_PAGE*2);

            if(this.chapters.size() > actualId){
                IBookletChapter chapter = this.chapters.get(actualId);
                if(chapter != null){
                    IBookletPage[] pages = chapter.getAllPages();
                    if(pages != null && pages.length > 0){
                        this.mc.displayGuiScreen(BookletUtils.createPageGui(this.previousScreen, this, pages[0]));
                    }
                }
            }
        }
        else{
            super.actionPerformed(button);
        }
    }

    @Override
    public void addOrModifyItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer){

    }

    @Override
    public boolean hasPageLeftButton(){
        return this.entryPage > 0;
    }

    @Override
    public void onPageLeftButtonPressed(){
        this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, this.parentPage, this.entry, this.entryPage-1, this.searchText, this.searchField.isFocused()));
    }

    @Override
    public boolean hasPageRightButton(){
        if(!this.chapters.isEmpty()){
            IBookletChapter lastChap = this.chapters.get(this.chapters.size()-1);
            if(lastChap != null){
                int lastPage = calcEntryPage(this.entry, lastChap, this.searchText);
                return this.entryPage < lastPage;
            }
        }
        return false;
    }

    @Override
    public void onPageRightButtonPressed(){
        this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, this.parentPage, this.entry, this.entryPage+1, this.searchText, this.searchField.isFocused()));
    }

    @Override
    public boolean hasBackButton(){
        return true;
    }

    @Override
    public void onBackButtonPressed(){
        if(!isShiftKeyDown()){
            this.mc.displayGuiScreen(this.parentPage);
        }
        else{
            super.onBackButtonPressed();
        }
    }
}
