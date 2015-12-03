/*
 * This file ("BookletUtils.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.achievement.InitAchievements;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.entry.BookletEntryAllSearch;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.update.UpdateChecker;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class BookletUtils{

    /**
     * Tries to open a URL in the Browser
     */
    public static void openBrowser(String url){
        openBrowser(url, url);
    }

    /**
     * Tries to open a URL in the Browser
     *
     * @param url      The URL
     * @param shiftUrl The URL to open when Shift is held
     */
    public static void openBrowser(String url, String shiftUrl){
        try{
            if(Desktop.isDesktopSupported()){
                if(shiftUrl.equals(url) || KeyUtil.isShiftPressed()){
                    Desktop.getDesktop().browse(new URI(shiftUrl));
                }
                else{
                    Desktop.getDesktop().browse(new URI(url));
                }
            }
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Something bad happened when trying to open a URL!", e);
        }
    }

    /**
     * Draws the Title of the current chapter, current index entry or just "Actually Additions" if neither is present
     */
    public static void drawTitle(GuiBooklet booklet){
        //Upper title
        booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize/2-142/2, booklet.guiTop-12, 0, 240, 142, 12);
        //Lower title
        booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize/2-142/2, booklet.guiTop+booklet.ySize, 0, 243, 142, 13);

        String strg = booklet.currentChapter == null ? (booklet.currentIndexEntry == null ? StringUtil.localize("itemGroup."+ModUtil.MOD_ID_LOWER) : booklet.currentIndexEntry.getLocalizedName()) : booklet.currentChapter.getLocalizedName();
        booklet.drawCenteredString(booklet.getFontRenderer(), strg, booklet.guiLeft+booklet.xSize/2, booklet.guiTop-9, StringUtil.DECIMAL_COLOR_WHITE);
    }

    /**
     * Draws an Achievement Info if the page has items that trigger achievements
     *
     * @param pre If the hover info texts or the icon should be drawn
     */
    public static void drawAchievementInfo(GuiBooklet booklet, boolean pre, int mouseX, int mouseY){
        if(booklet.currentChapter == null){
            return;
        }

        ArrayList<String> infoList = null;
        for(BookletPage page : booklet.currentChapter.pages){
            if(page != null && page.getItemStacksForPage() != null){
                for(ItemStack stack : page.getItemStacksForPage()){
                    for(Achievement achievement : InitAchievements.achievementList){
                        if(stack != null && achievement.theItemStack != null && achievement.theItemStack.isItemEqual(stack)){
                            if(pre){
                                booklet.mc.getTextureManager().bindTexture(GuiBooklet.resLoc);
                                booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize+1, booklet.guiTop-18, 166, 154, 22, 21);
                                return;
                            }
                            else{
                                if(mouseX >= booklet.guiLeft+booklet.xSize+1 && mouseX < booklet.guiLeft+booklet.xSize+1+22 && mouseY >= booklet.guiTop-18 && mouseY < booklet.guiTop-18+21){
                                    if(infoList == null){
                                        infoList = new ArrayList<String>();
                                        infoList.add(EnumChatFormatting.GOLD+"Achievements related to this chapter:");
                                    }
                                    infoList.add("-"+StringUtil.localize(achievement.statId));
                                    infoList.add(EnumChatFormatting.GRAY+"("+achievement.getDescription()+")");
                                }
                            }
                        }
                    }
                }
            }
        }

        if(infoList != null){
            booklet.drawHoveringText(infoList, mouseX, mouseY);
        }
    }

    /**
     * Pre-renders the booklet page, including
     * -the number of a page and its content (text, crafting recipe etc.)
     * -the number of a page in a chapter
     * -the amount of words and chars in the index (Just for teh lulz)
     */
    public static void renderPre(GuiBooklet booklet, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(booklet.currentIndexEntry != null){
            //Renders Booklet Page Number and Content
            if(booklet.currentChapter != null && booklet.currentPage != null){
                booklet.drawCenteredString(booklet.getFontRenderer(), booklet.currentPage.getID()+"/"+booklet.currentChapter.pages.length, booklet.guiLeft+booklet.xSize/2, booklet.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
                booklet.currentPage.renderPre(booklet, mouseX, mouseY, ticksElapsed, mousePressed);
            }
            //Renders Chapter Page Number
            else{
                booklet.drawCenteredString(booklet.getFontRenderer(), booklet.pageOpenInIndex+"/"+booklet.indexPageAmount, booklet.guiLeft+booklet.xSize/2, booklet.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
        //Renders the amount of words and chars the book has
        else{
            String wordCountString = StringUtil.localizeFormatted("booklet."+ModUtil.MOD_ID_LOWER+".amountOfWords", InitBooklet.wordCount);
            booklet.getFontRenderer().drawString(EnumChatFormatting.ITALIC+wordCountString, booklet.guiLeft+booklet.xSize-booklet.getFontRenderer().getStringWidth(wordCountString)-15, booklet.guiTop+booklet.ySize-18-booklet.getFontRenderer().FONT_HEIGHT, 0);

            String charCountString = StringUtil.localizeFormatted("booklet."+ModUtil.MOD_ID_LOWER+".amountOfChars", InitBooklet.charCount);
            booklet.getFontRenderer().drawString(EnumChatFormatting.ITALIC+charCountString, booklet.guiLeft+booklet.xSize-booklet.getFontRenderer().getStringWidth(charCountString)-15, booklet.guiTop+booklet.ySize-18, 0);
        }
    }

    /**
     * Draws all of the hovering texts for the buttons that need explanation in the booklet
     */
    @SuppressWarnings("unchecked")
    public static void doHoverTexts(GuiBooklet booklet, int mouseX, int mouseY){
        //Achievements Hover Text
        if(booklet.buttonAchievements.func_146115_a()){
            booklet.drawHoveringText(Collections.singletonList(EnumChatFormatting.GOLD+"Show Achievements"), mouseX, mouseY);
        }
        //Config Hover Text
        else if(booklet.buttonConfig.func_146115_a()){
            ArrayList list = new ArrayList();
            list.add(EnumChatFormatting.GOLD+"Show Configuration GUI");
            list.addAll(booklet.getFontRenderer().listFormattedStringToWidth("It is highly recommended that you restart your game after changing anything as that prevents possible bugs occuring!", GuiBooklet.TOOLTIP_SPLIT_LENGTH));
            booklet.drawHoveringText(list, mouseX, mouseY);

        }
        //Twitter Hover Text
        else if(booklet.buttonTwitter.func_146115_a()){
            booklet.drawHoveringText(Collections.singletonList(EnumChatFormatting.GOLD+"Open @ActAddMod on Twitter in Browser"), mouseX, mouseY);
        }
        //Forum Hover Text
        else if(booklet.buttonForum.func_146115_a()){
            booklet.drawHoveringText(Collections.singletonList(EnumChatFormatting.GOLD+"Open Minecraft Forum Post in Browser"), mouseX, mouseY);
        }
        //Update Checker Hover Text
        else if(booklet.buttonUpdate.func_146115_a()){
            ArrayList list = new ArrayList();
            if(UpdateChecker.checkFailed){
                list.add(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.failed")).getFormattedText());
            }
            else if(UpdateChecker.needsUpdateNotify){
                list.add(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.generic")).getFormattedText());
                list.add(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID_LOWER+".update.versionCompare", ModUtil.VERSION, UpdateChecker.updateVersion)).getFormattedText());
                list.add(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.buttonOptions"));
            }
            booklet.drawHoveringText(list, mouseX, mouseY);
        }
        else{
            for(GuiButton button : booklet.bookmarkButtons){
                if(button instanceof BookmarkButton && button.func_146115_a()){
                    ((BookmarkButton)button).drawHover(mouseX, mouseY);
                }
            }
        }
    }

    /**
     * Updates the search bar, should be called when it is getting typed into
     */
    @SuppressWarnings("unchecked")
    public static void updateSearchBar(GuiBooklet booklet){
        if(booklet.currentIndexEntry instanceof BookletEntryAllSearch){
            BookletEntryAllSearch currentEntry = (BookletEntryAllSearch)booklet.currentIndexEntry;
            if(booklet.searchField.getText() != null && !booklet.searchField.getText().isEmpty()){
                currentEntry.chapters.clear();

                for(BookletChapter chapter : currentEntry.allChapters){
                    if(chapter.getLocalizedName().toLowerCase(Locale.ROOT).contains(booklet.searchField.getText().toLowerCase(Locale.ROOT))){
                        currentEntry.chapters.add(chapter);
                    }
                }
            }
            else{
                currentEntry.chapters = (ArrayList<BookletChapter>)currentEntry.allChapters.clone();
            }
            openIndexEntry(booklet, booklet.currentIndexEntry, booklet.pageOpenInIndex, false);
        }
    }

    /**
     * Opens an index entry in the booklet.
     *
     * @param resetTextField will clear the text in the searchField and reset the search entry's data
     */
    @SuppressWarnings("unchecked")
    public static void openIndexEntry(GuiBooklet booklet, BookletEntry entry, int page, boolean resetTextField){
        booklet.searchField.setVisible(entry instanceof BookletEntryAllSearch);
        booklet.searchField.setFocused(entry instanceof BookletEntryAllSearch);
        if(resetTextField){
            booklet.searchField.setText("");
            if(entry instanceof BookletEntryAllSearch){
                entry.chapters = (ArrayList<BookletChapter>)((BookletEntryAllSearch)entry).allChapters.clone();
            }
        }

        booklet.currentPage = null;
        booklet.currentChapter = null;

        booklet.currentIndexEntry = entry;
        booklet.indexPageAmount = entry == null ? 1 : entry.chapters.size()/booklet.chapterButtons.length+1;
        booklet.pageOpenInIndex = entry == null ? 1 : (booklet.indexPageAmount <= page || page <= 0 ? booklet.indexPageAmount : page);

        booklet.buttonPreviousScreen.visible = entry != null;
        booklet.buttonForward.visible = booklet.pageOpenInIndex < booklet.indexPageAmount;
        booklet.buttonBackward.visible = booklet.pageOpenInIndex > 1;

        for(int i = 0; i < booklet.chapterButtons.length; i++){
            IndexButton button = (IndexButton)booklet.chapterButtons[i];
            if(entry == null){
                boolean entryExists = InitBooklet.entries.size() > i;
                button.visible = entryExists;
                if(entryExists){
                    button.displayString = InitBooklet.entries.get(i).getNameWithColor();
                    button.chap = null;
                }
            }
            else{
                boolean entryExists = entry.chapters.size() > i+(booklet.chapterButtons.length*booklet.pageOpenInIndex-booklet.chapterButtons.length);
                button.visible = entryExists;
                if(entryExists){
                    BookletChapter chap = entry.chapters.get(i+(booklet.chapterButtons.length*booklet.pageOpenInIndex-booklet.chapterButtons.length));
                    button.displayString = chap.getNameWithColor();
                    button.chap = chap;
                }
            }
        }
    }

    /**
     * Called when one of the buttons to open an index or a chapter is pressed
     */
    public static void handleChapterButtonClick(GuiBooklet booklet, GuiButton button){
        int place = Util.arrayContains(booklet.chapterButtons, button);
        if(place >= 0){
            if(booklet.currentIndexEntry != null){
                if(booklet.currentChapter == null){
                    if(place < booklet.currentIndexEntry.chapters.size()){
                        BookletChapter chap = booklet.currentIndexEntry.chapters.get(place+(booklet.chapterButtons.length*booklet.pageOpenInIndex-booklet.chapterButtons.length));
                        openChapter(booklet, chap, chap.pages[0]);
                    }
                }
            }
            else{
                if(place < InitBooklet.entries.size()){
                    openIndexEntry(booklet, InitBooklet.entries.get(place), 1, true);
                }
            }
        }
    }

    /**
     * Opens a chapter in the booklet.
     * Can only be done when the chapter is not null and an index entry is opened in the booklet
     */
    public static void openChapter(GuiBooklet booklet, BookletChapter chapter, BookletPage page){
        if(chapter == null || booklet.currentIndexEntry == null){
            return;
        }

        booklet.searchField.setVisible(false);
        booklet.searchField.setFocused(false);
        booklet.searchField.setText("");

        booklet.currentChapter = chapter;
        booklet.currentPage = page != null && doesChapterHavePage(chapter, page) ? page : chapter.pages[0];

        booklet.buttonForward.visible = getNextPage(chapter, booklet.currentPage) != null;
        booklet.buttonBackward.visible = getPrevPage(chapter, booklet.currentPage) != null;
        booklet.buttonPreviousScreen.visible = true;

        for(GuiButton chapterButton : booklet.chapterButtons){
            chapterButton.visible = false;
        }
    }

    /**
     * Checks if a chapter has a certain page
     */
    private static boolean doesChapterHavePage(BookletChapter chapter, BookletPage page){
        for(BookletPage aPage : chapter.pages){
            if(aPage == page){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the next available page in the booklet (or null if there is none)
     */
    private static BookletPage getNextPage(BookletChapter chapter, BookletPage currentPage){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == currentPage){
                if(i+1 < chapter.pages.length){
                    return chapter.pages[i+1];
                }
            }
        }
        return null;
    }

    /**
     * Gets the previous available page in the booklet (or null if there is none)
     */
    private static BookletPage getPrevPage(BookletChapter chapter, BookletPage currentPage){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == currentPage){
                if(i-1 >= 0){
                    return chapter.pages[i-1];
                }
            }
        }
        return null;
    }

    /**
     * Called when the "next page"-button is pressed
     */
    public static void handleNextPage(GuiBooklet booklet){
        if(booklet.currentIndexEntry != null){
            if(booklet.currentPage != null){
                BookletPage page = getNextPage(booklet.currentChapter, booklet.currentPage);
                if(page != null){
                    booklet.currentPage = page;
                }

                booklet.buttonForward.visible = getNextPage(booklet.currentChapter, booklet.currentPage) != null;
                booklet.buttonBackward.visible = getPrevPage(booklet.currentChapter, booklet.currentPage) != null;
            }
            else{
                openIndexEntry(booklet, booklet.currentIndexEntry, booklet.pageOpenInIndex+1, !(booklet.currentIndexEntry instanceof BookletEntryAllSearch));
            }
        }
    }

    /**
     * Called when the "previous page"-button is pressed
     */
    public static void handlePreviousPage(GuiBooklet booklet){
        if(booklet.currentIndexEntry != null){
            if(booklet.currentPage != null){
                BookletPage page = getPrevPage(booklet.currentChapter, booklet.currentPage);
                if(page != null){
                    booklet.currentPage = page;
                }

                booklet.buttonForward.visible = getNextPage(booklet.currentChapter, booklet.currentPage) != null;
                booklet.buttonBackward.visible = getPrevPage(booklet.currentChapter, booklet.currentPage) != null;
            }
            else{
                openIndexEntry(booklet, booklet.currentIndexEntry, booklet.pageOpenInIndex-1, !(booklet.currentIndexEntry instanceof BookletEntryAllSearch));
            }
        }
    }

    public static BookletPage getFirstPageForStack(ItemStack stack){
        ArrayList<BookletPage> pages = getPagesForStack(stack);
        return pages.isEmpty() ? null : pages.get(0);
    }

    public static ArrayList<BookletPage> getPagesForStack(ItemStack stack){
        ArrayList<BookletPage> possiblePages = new ArrayList<BookletPage>();
        for(BookletPage page : InitBooklet.pagesWithItemStackData){
            if(ItemUtil.contains(page.getItemStacksForPage(), stack, true)){
                possiblePages.add(page);
            }
        }
        return possiblePages;
    }

    public static class IndexButton extends GuiButton{

        public BookletChapter chap;
        private GuiBooklet gui;

        public IndexButton(int id, int x, int y, int width, int height, String text, GuiBooklet gui){
            super(id, x, y, width, height, text);
            this.gui = gui;
        }

        @Override
        public void drawButton(Minecraft minecraft, int mouseX, int mouseY){
            if(this.visible){
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition+this.width && mouseY < this.yPosition+this.height;
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                this.mouseDragged(minecraft, mouseX, mouseY);

                int textOffsetX = 0;
                if(this.chap != null){
                    if(this.chap.displayStack != null){
                        GL11.glPushMatrix();
                        BookletPage.renderItem(this.gui, this.chap.displayStack, this.xPosition-4, this.yPosition, 0.725F);
                        GL11.glPopMatrix();
                        textOffsetX = 10;
                    }
                }

                if(this.field_146123_n){
                    GL11.glPushMatrix();
                    AssetUtil.drawHorizontalGradientRect(this.xPosition+textOffsetX-1, this.yPosition+this.height-1, this.xPosition+this.gui.getFontRenderer().getStringWidth(this.displayString)+textOffsetX+1, this.yPosition+this.height, 0x80 << 24 | 22271, 22271);
                    GL11.glPopMatrix();
                }

                this.gui.getFontRenderer().drawString(this.displayString, this.xPosition+textOffsetX, this.yPosition+(this.height-8)/2, 0);
            }
        }
    }

    public static class TexturedButton extends GuiButton{

        public int texturePosX;
        public int texturePosY;

        public TexturedButton(int id, int x, int y, int texturePosX, int texturePosY, int width, int height){
            super(id, x, y, width, height, "");
            this.texturePosX = texturePosX;
            this.texturePosY = texturePosY;
        }

        public void setTexturePos(int x, int y){
            this.texturePosX = x;
            this.texturePosY = y;
        }

        @Override
        public void drawButton(Minecraft minecraft, int x, int y){
            if(this.visible){
                minecraft.getTextureManager().bindTexture(GuiBooklet.resLoc);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
                int k = this.getHoverState(this.field_146123_n);
                if(k == 0){
                    k = 1;
                }
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, this.texturePosX, this.texturePosY-this.height+k*this.height, this.width, this.height);
                this.mouseDragged(minecraft, x, y);
            }
        }
    }

    public static class BookmarkButton extends GuiButton{

        public BookletChapter assignedChapter;
        public BookletPage assignedPage;
        public BookletEntry assignedEntry;
        public int assignedPageInIndex;

        private GuiBooklet booklet;

        public BookmarkButton(int id, int x, int y, GuiBooklet booklet){
            super(id, x, y, 16, 16, "");
            this.booklet = booklet;
        }

        public void onPressed(){
            if(this.assignedEntry != null){
                if(KeyUtil.isShiftPressed()){
                    this.assignedEntry = null;
                    this.assignedChapter = null;
                    this.assignedPage = null;
                    this.assignedPageInIndex = 1;
                }
                else{
                    openIndexEntry(this.booklet, this.assignedEntry, this.assignedPageInIndex, true);
                    openChapter(this.booklet, this.assignedChapter, this.assignedPage);
                }
            }
            else{
                if(this.booklet.currentIndexEntry != null){
                    this.assignedEntry = this.booklet.currentIndexEntry;
                    this.assignedChapter = this.booklet.currentChapter;
                    this.assignedPage = this.booklet.currentPage;
                    this.assignedPageInIndex = this.booklet.pageOpenInIndex;
                }
            }
        }        @Override
        public void drawButton(Minecraft minecraft, int x, int y){
            if(this.visible){
                minecraft.getTextureManager().bindTexture(GuiBooklet.resLoc);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
                int k = this.getHoverState(this.field_146123_n);
                if(k == 0){
                    k = 1;
                }
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int renderHeight = 25;
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 146+(this.assignedEntry == null ? 0 : 16), 194-renderHeight+k*renderHeight, this.width, renderHeight);
                this.mouseDragged(minecraft, x, y);

                if(this.assignedEntry != null){
                    GL11.glPushMatrix();
                    BookletPage.renderItem(booklet, this.assignedChapter != null && this.assignedChapter.displayStack != null ? this.assignedChapter.displayStack : new ItemStack(InitItems.itemBooklet), this.xPosition+2, this.yPosition+1, 0.725F);
                    GL11.glPopMatrix();
                }
            }
        }

        @SuppressWarnings("unchecked")
        public void drawHover(int mouseX, int mouseY){
            ArrayList list = new ArrayList();
            if(this.assignedEntry != null){
                if(this.assignedChapter != null){
                    list.add(EnumChatFormatting.GOLD+this.assignedChapter.getLocalizedName()+", Page "+this.assignedPage.getID());
                }
                else{
                    list.add(EnumChatFormatting.GOLD+this.assignedEntry.getLocalizedName()+", Page "+this.assignedPageInIndex);
                }
                list.add("Click to open");
                list.add(EnumChatFormatting.ITALIC+"Shift-Click to remove");
            }
            else{
                list.add(EnumChatFormatting.GOLD+"None");
                list.add("Click to save current page");
            }
            this.booklet.drawHoveringText(list, mouseX, mouseY);
        }


    }
}
