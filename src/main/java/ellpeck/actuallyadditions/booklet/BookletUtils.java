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
import ellpeck.actuallyadditions.booklet.button.BookmarkButton;
import ellpeck.actuallyadditions.booklet.button.IndexButton;
import ellpeck.actuallyadditions.booklet.button.TexturedButton;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.entry.BookletEntryAllSearch;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
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
        booklet.mc.getTextureManager().bindTexture(GuiBooklet.resLoc);
        //Upper title
        booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize/2-142/2, booklet.guiTop-12, 0, 240, 142, 12);
        //Lower title
        booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize/2-142/2, booklet.guiTop+booklet.ySize, 0, 243, 142, 13);

        //Draw No Entry title
        if(booklet.currentEntrySet.entry == null){
            String strg = EnumChatFormatting.DARK_GREEN+StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".booklet.manualName.1");
            booklet.getFontRenderer().drawString(strg, booklet.guiLeft+booklet.xSize/2-booklet.getFontRenderer().getStringWidth(strg)/2-3, booklet.guiTop+12, 0);
            strg = EnumChatFormatting.DARK_GREEN+StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".booklet.manualName.2");
            booklet.getFontRenderer().drawString(strg, booklet.guiLeft+booklet.xSize/2-booklet.getFontRenderer().getStringWidth(strg)/2-3, booklet.guiTop+12+booklet.getFontRenderer().FONT_HEIGHT, 0);

            String version;
            String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
            if(playerName.equals("dqmhose")){
                version = "Pants Edition";
            }
            else if(playerName.equals("KittyVanCat")){
                version = "Cat's Edition";
            }
            else if(playerName.equals("Ellpeck") || Util.isDevVersion()){
                version = "Dev's Edition";
            }
            else{
                version = StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".booklet.edition")+" "+ModUtil.VERSION.substring(ModUtil.VERSION.indexOf("r")+1);
            }
            strg = EnumChatFormatting.GOLD+EnumChatFormatting.ITALIC.toString()+"-"+version+"-";
            booklet.getFontRenderer().drawString(strg, booklet.guiLeft+booklet.xSize/2-booklet.getFontRenderer().getStringWidth(strg)/2-3, booklet.guiTop+33, 0);
        }

        String strg = booklet.currentEntrySet.chapter == null ? (booklet.currentEntrySet.entry == null ? StringUtil.localize("itemGroup."+ModUtil.MOD_ID_LOWER) : booklet.currentEntrySet.entry.getLocalizedName()) : booklet.currentEntrySet.chapter.getLocalizedName();
        booklet.drawCenteredString(booklet.getFontRenderer(), strg, booklet.guiLeft+booklet.xSize/2, booklet.guiTop-9, StringUtil.DECIMAL_COLOR_WHITE);
    }

    /**
     * Draws an Achievement Info if the page has items that trigger achievements
     *
     * @param pre If the hover info texts or the icon should be drawn
     */
    public static void drawAchievementInfo(GuiBooklet booklet, boolean pre, int mouseX, int mouseY){
        if(booklet.currentEntrySet.chapter == null){
            return;
        }

        ArrayList<String> infoList = null;
        for(BookletPage page : booklet.currentEntrySet.chapter.pages){
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
        if(booklet.currentEntrySet.entry != null){
            //Renders Booklet Page Number and Content
            if(booklet.currentEntrySet.chapter != null && booklet.currentEntrySet.page != null){
                booklet.drawCenteredString(booklet.getFontRenderer(), booklet.currentEntrySet.page.getID()+"/"+booklet.currentEntrySet.chapter.pages.length, booklet.guiLeft+booklet.xSize/2, booklet.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
                booklet.currentEntrySet.page.renderPre(booklet, mouseX, mouseY, ticksElapsed, mousePressed);
            }
            //Renders Chapter Page Number
            else{
                booklet.drawCenteredString(booklet.getFontRenderer(), booklet.currentEntrySet.pageInIndex+"/"+booklet.indexPageAmount, booklet.guiLeft+booklet.xSize/2, booklet.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
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
        //Update all of the buttons' hovering texts
        for(Object button : booklet.getButtonList()){
            if(button instanceof GuiButton && ((GuiButton)button).visible && ((GuiButton)button).func_146115_a()){
                if(button instanceof BookmarkButton){
                    ((BookmarkButton)button).drawHover(mouseX, mouseY);
                }
                else if(button instanceof TexturedButton){
                    booklet.drawHoveringText(((TexturedButton)button).textList, mouseX, mouseY);
                }
            }
        }
    }

    /**
     * Updates the search bar, should be called when it is getting typed into
     */
    @SuppressWarnings("unchecked")
    public static void updateSearchBar(GuiBooklet booklet){
        if(booklet.currentEntrySet.entry instanceof BookletEntryAllSearch){
            BookletEntryAllSearch currentEntry = (BookletEntryAllSearch)booklet.currentEntrySet.entry;
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
            openIndexEntry(booklet, booklet.currentEntrySet.entry, booklet.currentEntrySet.pageInIndex, false);
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

        booklet.currentEntrySet.page = null;
        booklet.currentEntrySet.chapter = null;

        booklet.currentEntrySet.entry = entry;
        booklet.indexPageAmount = entry == null ? 1 : entry.chapters.size()/booklet.chapterButtons.length+1;
        booklet.currentEntrySet.pageInIndex = entry == null ? 1 : (booklet.indexPageAmount <= page || page <= 0 ? booklet.indexPageAmount : page);

        booklet.buttonPreviousScreen.visible = entry != null;
        booklet.buttonForward.visible = booklet.currentEntrySet.pageInIndex < booklet.indexPageAmount;
        booklet.buttonBackward.visible = booklet.currentEntrySet.pageInIndex > 1;

        for(int i = 0; i < booklet.chapterButtons.length; i++){
            IndexButton button = (IndexButton)booklet.chapterButtons[i];
            if(entry == null){
                if(i >= GuiBooklet.INDEX_BUTTONS_OFFSET){
                    boolean entryExists = InitBooklet.entries.size() > i-GuiBooklet.INDEX_BUTTONS_OFFSET;
                    button.visible = entryExists;
                    if(entryExists){
                        button.displayString = InitBooklet.entries.get(i-GuiBooklet.INDEX_BUTTONS_OFFSET).getNameWithColor();
                        button.chap = null;
                    }
                }
                else{
                    button.visible = false;
                }
            }
            else{
                boolean entryExists = entry.chapters.size() > i+(booklet.chapterButtons.length*booklet.currentEntrySet.pageInIndex-booklet.chapterButtons.length);
                button.visible = entryExists;
                if(entryExists){
                    BookletChapter chap = entry.chapters.get(i+(booklet.chapterButtons.length*booklet.currentEntrySet.pageInIndex-booklet.chapterButtons.length));
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
            if(booklet.currentEntrySet.entry != null){
                if(booklet.currentEntrySet.chapter == null){
                    if(place < booklet.currentEntrySet.entry.chapters.size()){
                        BookletChapter chap = booklet.currentEntrySet.entry.chapters.get(place+(booklet.chapterButtons.length*booklet.currentEntrySet.pageInIndex-booklet.chapterButtons.length));
                        openChapter(booklet, chap, chap.pages[0]);
                    }
                }
            }
            else{
                if(place-GuiBooklet.INDEX_BUTTONS_OFFSET < InitBooklet.entries.size()){
                    openIndexEntry(booklet, InitBooklet.entries.get(place-GuiBooklet.INDEX_BUTTONS_OFFSET), 1, true);
                }
            }
        }
    }

    /**
     * Opens a chapter in the booklet.
     * Can only be done when the chapter is not null and an index entry is opened in the booklet
     */
    public static void openChapter(GuiBooklet booklet, BookletChapter chapter, BookletPage page){
        if(chapter == null || booklet.currentEntrySet.entry == null){
            return;
        }

        booklet.searchField.setVisible(false);
        booklet.searchField.setFocused(false);
        booklet.searchField.setText("");

        booklet.currentEntrySet.chapter = chapter;
        booklet.currentEntrySet.page = page != null && doesChapterHavePage(chapter, page) ? page : chapter.pages[0];

        booklet.buttonForward.visible = getNextPage(chapter, booklet.currentEntrySet.page) != null;
        booklet.buttonBackward.visible = getPrevPage(chapter, booklet.currentEntrySet.page) != null;
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
    private static BookletPage getNextPage(BookletChapter chapter, BookletPage page){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == page){
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
    private static BookletPage getPrevPage(BookletChapter chapter, BookletPage page){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == page){
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
        if(booklet.currentEntrySet.entry != null){
            if(booklet.currentEntrySet.page != null){
                BookletPage page = getNextPage(booklet.currentEntrySet.chapter, booklet.currentEntrySet.page);
                if(page != null){
                    booklet.currentEntrySet.page = page;
                }

                booklet.buttonForward.visible = getNextPage(booklet.currentEntrySet.chapter, booklet.currentEntrySet.page) != null;
                booklet.buttonBackward.visible = getPrevPage(booklet.currentEntrySet.chapter, booklet.currentEntrySet.page) != null;
            }
            else{
                if(booklet.currentEntrySet.pageInIndex+1 <= booklet.indexPageAmount){
                    openIndexEntry(booklet, booklet.currentEntrySet.entry, booklet.currentEntrySet.pageInIndex+1, !(booklet.currentEntrySet.entry instanceof BookletEntryAllSearch));
                }
            }
        }
    }

    /**
     * Called when the "previous page"-button is pressed
     */
    public static void handlePreviousPage(GuiBooklet booklet){
        if(booklet.currentEntrySet.entry != null){
            if(booklet.currentEntrySet.page != null){
                BookletPage page = getPrevPage(booklet.currentEntrySet.chapter, booklet.currentEntrySet.page);
                if(page != null){
                    booklet.currentEntrySet.page = page;
                }

                booklet.buttonForward.visible = getNextPage(booklet.currentEntrySet.chapter, booklet.currentEntrySet.page) != null;
                booklet.buttonBackward.visible = getPrevPage(booklet.currentEntrySet.chapter, booklet.currentEntrySet.page) != null;
            }
            else{
                if(booklet.currentEntrySet.pageInIndex-1 > 0){
                    openIndexEntry(booklet, booklet.currentEntrySet.entry, booklet.currentEntrySet.pageInIndex-1, !(booklet.currentEntrySet.entry instanceof BookletEntryAllSearch));
                }
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
            if(ItemUtil.contains(page.getItemStacksForPage(), stack, page.arePageStacksWildcard)){
                possiblePages.add(page);
            }
        }
        return possiblePages;
    }
}
