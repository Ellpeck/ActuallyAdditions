/*
 * This file ("GuiEntry.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.booklet.button.EntryButton;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntryTrials;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiEntry extends GuiBooklet {

    //The page in the entry. Say you have 2 more chapters than fit on one double page, then those 2 would be displayed on entryPage 1 instead.
    private final int entryPage;
    private final int pageAmount;
    private final IBookletEntry entry;
    private final List<IBookletChapter> chapters;
    private final String searchText;
    private final boolean focusSearch;

    public GuiEntry(Screen previousScreen, GuiBookletBase parentPage, IBookletEntry entry, int entryPage, String search, boolean focusSearch) {
        super(previousScreen, parentPage);
        this.entryPage = entryPage;
        this.entry = entry;
        this.searchText = search;
        this.focusSearch = focusSearch;
        this.chapters = entry.getChaptersForDisplay(search);

        if (!this.chapters.isEmpty()) {
            IBookletChapter lastChap = this.chapters.get(this.chapters.size() - 1);
            this.pageAmount = lastChap == null
                    ? 1
                    : calcEntryPage(this.entry, lastChap, this.searchText) + 1;
        } else {
            this.pageAmount = 1;
        }
    }

    public GuiEntry(Screen previousScreen, GuiBookletBase parentPage, IBookletEntry entry, IBookletChapter chapterForPageCalc, String search, boolean focusSearch) {
        this(previousScreen, parentPage, entry, calcEntryPage(entry, chapterForPageCalc, search), search, focusSearch);
    }

    private static int calcEntryPage(IBookletEntry entry, IBookletChapter chapterForPageCalc, String search) {
        int index = entry.getChaptersForDisplay(search).indexOf(chapterForPageCalc);
        return index / (BUTTONS_PER_PAGE * 2);
    }

    @Override
    public void drawScreenPre(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.drawScreenPre(matrices, mouseX, mouseY, partialTicks);

        String name = this.entry.getLocalizedName();
        this.font.draw(matrices, name, this.guiLeft + this.xSize / 2 - this.font.width(name) / 2, this.guiTop - 1, 0xFFFFFF);

        for (int i = 0; i < 2; i++) {
            String pageStrg = "Page " + (this.entryPage * 2 + i + 1) + "/" + this.pageAmount * 2;
            this.renderScaledAsciiString(pageStrg, this.guiLeft + 25 + i * 136, this.guiTop + this.ySize - 7, 0xFFFFFF, false, this.getLargeFontSize());
        }
    }

    @Override
    public void init() {
        super.init();

        if (this.hasSearchBar() && this.searchText != null) {
            this.searchField.setValue(this.searchText);
            if (this.focusSearch) {
                this.searchField.setFocus(true);
            }
        }

        int idOffset = this.entryPage * BUTTONS_PER_PAGE * 2;
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < BUTTONS_PER_PAGE; y++) {
                int id = y + x * BUTTONS_PER_PAGE;
                if (this.chapters.size() > id + idOffset) {
                    IBookletChapter chapter = this.chapters.get(id + idOffset);
                    this.addButton(new EntryButton(this, id, this.guiLeft + 14 + x * 142, this.guiTop + 11 + y * 13, 115, 10, chapter.getLocalizedNameWithFormatting(), chapter.getDisplayItemStack()));
                } else {
                    return;
                }
            }
        }
    }
/*
    @Override
    protected void actionPerformed(Button button) throws IOException {
        if (button instanceof EntryButton) {
            int actualId = button.id + this.entryPage * BUTTONS_PER_PAGE * 2;

            if (this.chapters.size() > actualId) {
                IBookletChapter chapter = this.chapters.get(actualId);
                if (chapter != null) {
                    IBookletPage[] pages = chapter.getAllPages();
                    if (pages != null && pages.length > 0) {
                        this.minecraft.setScreen(BookletUtils.createPageGui(this.previousScreen, this, pages[0]));
                    }
                }
            }
        } else {
            super.actionPerformed(button);
        }
    }

 */

    @Override
    public void addOrModifyItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer) {

    }

    @Override
    public boolean hasPageLeftButton() {
        return this.entryPage > 0;
    }

    @Override
    public void onPageLeftButtonPressed() {
        this.minecraft.setScreen(new GuiEntry(this.previousScreen, this.parentPage, this.entry, this.entryPage - 1, this.searchText, this.searchField.isFocused()));
    }

    @Override
    public boolean hasPageRightButton() {
        return !this.chapters.isEmpty() && this.entryPage < this.pageAmount - 1;
    }

    @Override
    public void onPageRightButtonPressed() {
        this.minecraft.setScreen(new GuiEntry(this.previousScreen, this.parentPage, this.entry, this.entryPage + 1, this.searchText, this.searchField.isFocused()));
    }

    @Override
    public boolean hasBackButton() {
        return true;
    }

    @Override
    public void onBackButtonPressed() {
        if (!hasShiftDown()) {
            this.minecraft.setScreen(this.parentPage);
        } else {
            super.onBackButtonPressed();
        }
    }

    @Override
    public boolean areTrialsOpened() {
        return this.entry instanceof BookletEntryTrials;
    }
}
