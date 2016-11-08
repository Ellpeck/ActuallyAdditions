/*
 * This file ("BookletUtils.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.booklet.button.BookmarkButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.IndexButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.TexturedButton;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntryAllSearch;
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class BookletUtils{

    /**
     * Tries to open a URL in the Browser
     */
    @SideOnly(Side.CLIENT)
    public static void openBrowser(String url){
        openBrowser(url, url);
    }

    /**
     * Tries to open a URL in the Browser
     *
     * @param url      The URL
     * @param shiftUrl The URL to open when Shift is held
     */
    @SideOnly(Side.CLIENT)
    public static void openBrowser(String url, String shiftUrl){
        try{
            if(Desktop.isDesktopSupported()){
                if(shiftUrl.equals(url) || GuiScreen.isShiftKeyDown()){
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
    @SideOnly(Side.CLIENT)
    public static void drawTitle(GuiBooklet booklet){
        booklet.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC);
        //Upper title
        booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize/2-142/2, booklet.guiTop-12, 0, 240, 142, 12);
        //Lower title
        booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize/2-142/2, booklet.guiTop+booklet.ySize, 0, 243, 142, 13);

        //Draw No Entry title
        if(booklet.currentEntrySet.getCurrentEntry() == null){
            String strg = TextFormatting.DARK_GREEN+StringUtil.localize(booklet.bookletName);
            booklet.getFontRenderer().drawString(strg, booklet.guiLeft+booklet.xSize/2-booklet.getFontRenderer().getStringWidth(strg)/2-3, booklet.guiTop+12, 0);
            strg = TextFormatting.DARK_GREEN+StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.manualName.2");
            booklet.getFontRenderer().drawString(strg, booklet.guiLeft+booklet.xSize/2-booklet.getFontRenderer().getStringWidth(strg)/2-3, booklet.guiTop+12+booklet.getFontRenderer().FONT_HEIGHT, 0);

            String versionStrg;
            String playerName = Minecraft.getMinecraft().thePlayer.getName();

            if(Util.isDevVersion()){
                versionStrg = "Dev's Edition";
            }
            else{
                String modVersion = Util.getMajorModVersion();
                if(playerName.equalsIgnoreCase("dqmhose")){
                    versionStrg = "Pants Edition";
                }
                else if(playerName.equalsIgnoreCase("TwoOfEight") || playerName.equalsIgnoreCase("BootyToast")){
                    versionStrg = "Illustrator's Edition";
                }
                else if(playerName.equalsIgnoreCase("KittyVanCat")){
                    versionStrg = "Cat's Edition";
                }
                else if(playerName.equalsIgnoreCase("canitzp")){
                    versionStrg = "P's Edition";
                }
                else if(playerName.equalsIgnoreCase("Ellpeck")){
                    versionStrg = "Dev's Edition";
                }
                else if(playerName.equalsIgnoreCase("direwolf20")){
                    versionStrg = "Edition 20";
                }
                else if(playerName.equalsIgnoreCase("dannydjdk") || playerName.equalsIgnoreCase("andrew_period")){
                    versionStrg = "Derp's Edition";
                }
                else if(playerName.equalsIgnoreCase("mezz")){
                    versionStrg = "Just Enough Edition";
                }
                else{
                    versionStrg = StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.edition")+" "+modVersion;
                }
            }

            strg = TextFormatting.GOLD+TextFormatting.ITALIC.toString()+"-"+versionStrg+"-";
            booklet.getFontRenderer().drawString(strg, booklet.guiLeft+booklet.xSize/2-booklet.getFontRenderer().getStringWidth(strg)/2-3, booklet.guiTop+33, 0);
        }

        String strg = booklet.currentEntrySet.getCurrentChapter() == null ? (booklet.currentEntrySet.getCurrentEntry() == null ? StringUtil.localize("itemGroup."+ModUtil.MOD_ID) : booklet.currentEntrySet.getCurrentEntry().getLocalizedName()) : booklet.currentEntrySet.getCurrentChapter().getLocalizedName();
        booklet.drawCenteredString(booklet.getFontRenderer(), strg, booklet.guiLeft+booklet.xSize/2, booklet.guiTop-9, StringUtil.DECIMAL_COLOR_WHITE);
    }

    /**
     * Draws an Achievement Info if the page has items that trigger achievements
     *
     * @param pre If the hover info texts or the icon should be drawn
     */
    @SideOnly(Side.CLIENT)
    public static void drawAchievementInfo(GuiBooklet booklet, boolean pre, int mouseX, int mouseY){
        if(booklet.currentEntrySet.getCurrentChapter() == null){
            return;
        }

        List<TheAchievements> achievements = null;
        for(BookletPage page : booklet.currentEntrySet.getCurrentChapter().getPages()){
            ItemStack[] stacks = page.getItemStacksForPage();
            if(page != null && stacks != null){
                for(ItemStack stack : stacks){
                    if(stack != null){
                        for(TheAchievements achievement : TheAchievements.values()){
                            if(ItemUtil.contains(achievement.itemsToBeGotten, stack, true)){
                                if(pre){
                                    booklet.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC);
                                    booklet.drawTexturedModalRect(booklet.guiLeft+booklet.xSize+1, booklet.guiTop-18, 166, 154, 22, 21);
                                    return;
                                }
                                else{
                                    if(mouseX >= booklet.guiLeft+booklet.xSize+1 && mouseX < booklet.guiLeft+booklet.xSize+1+22 && mouseY >= booklet.guiTop-18 && mouseY < booklet.guiTop-18+21){
                                        if(achievements == null){
                                            achievements = new ArrayList<TheAchievements>();
                                        }
                                        if(!achievements.contains(achievement)){
                                            achievements.add(achievement);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(achievements != null){
            List<String> infoList = null;
            for(TheAchievements achievement : achievements){
                if(infoList == null){
                    infoList = new ArrayList<String>();
                    infoList.add(TextFormatting.GOLD+"Achievements related to this chapter:");
                }
                infoList.add("-"+StringUtil.localize(achievement.chieve.statId));
                infoList.add(TextFormatting.GRAY+"("+achievement.chieve.getDescription()+")");
            }

            if(infoList != null){
                booklet.drawHoveringText(infoList, mouseX, mouseY);
            }
        }
    }

    /**
     * Pre-renders the booklet page, including
     * -the number of a page and its content (text, crafting recipe etc.)
     * -the number of a page in a chapter
     * -the amount of words and chars in the index (Just for teh lulz)
     */
    @SideOnly(Side.CLIENT)
    public static void renderPre(GuiBooklet booklet, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(booklet.currentEntrySet.getCurrentEntry() != null){
            //Renders Booklet Page Number and Content
            if(booklet.currentEntrySet.getCurrentChapter() != null && booklet.currentEntrySet.getCurrentPage() != null){
                booklet.drawCenteredString(booklet.getFontRenderer(), booklet.currentEntrySet.getCurrentPage().getID()+"/"+booklet.currentEntrySet.getCurrentChapter().getPages().length, booklet.guiLeft+booklet.xSize/2, booklet.guiTop+171, StringUtil.DECIMAL_COLOR_WHITE);
                booklet.currentEntrySet.getCurrentPage().renderPre(booklet, mouseX, mouseY, ticksElapsed, mousePressed);
            }
            //Renders Chapter Page Number
            else{
                booklet.drawCenteredString(booklet.getFontRenderer(), booklet.currentEntrySet.getPageInIndex()+"/"+booklet.indexPageAmount, booklet.guiLeft+booklet.xSize/2, booklet.guiTop+171, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
        //Renders the amount of words and chars the book has
        else{
            String wordCountString = StringUtil.localizeFormatted("booklet."+ModUtil.MOD_ID+".amountOfWords", ClientProxy.bookletWordCount);
            booklet.getFontRenderer().drawString(TextFormatting.ITALIC+wordCountString, booklet.guiLeft+booklet.xSize-booklet.getFontRenderer().getStringWidth(wordCountString)-15, booklet.guiTop+booklet.ySize-18-booklet.getFontRenderer().FONT_HEIGHT, 0);

            String charCountString = StringUtil.localizeFormatted("booklet."+ModUtil.MOD_ID+".amountOfChars", ClientProxy.bookletCharCount);
            booklet.getFontRenderer().drawString(TextFormatting.ITALIC+charCountString, booklet.guiLeft+booklet.xSize-booklet.getFontRenderer().getStringWidth(charCountString)-15, booklet.guiTop+booklet.ySize-18, 0);
        }
    }

    /**
     * Draws all of the hovering texts for the buttons that need explanation in the booklet
     */
    @SideOnly(Side.CLIENT)
    public static void doHoverTexts(GuiBooklet booklet, int mouseX, int mouseY){
        //Update all of the buttons' hovering texts
        for(Object button : booklet.getButtonList()){
            if(button instanceof GuiButton && ((GuiButton)button).visible && ((GuiButton)button).isMouseOver()){
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
    @SideOnly(Side.CLIENT)
    public static void updateSearchBar(GuiBooklet booklet){
        boolean change = false;

        IBookletEntry current = booklet.currentEntrySet.getCurrentEntry();
        boolean isAllSearch = current instanceof BookletEntryAllSearch;

        if(booklet.searchField.getText() != null && !booklet.searchField.getText().isEmpty()){
            if(!isAllSearch){
                openIndexEntry(booklet, ActuallyAdditionsAPI.allAndSearch, 1, false);
                current = booklet.currentEntrySet.getCurrentEntry();
            }

            current.getChapters().clear();
            for(IBookletChapter chapter : ActuallyAdditionsAPI.ALL_CHAPTERS){
                String searchFieldText = booklet.searchField.getText().toLowerCase(Locale.ROOT);
                if(chapter.getLocalizedName().toLowerCase(Locale.ROOT).contains(searchFieldText) || getChapterStacksContainString(searchFieldText, chapter)){
                    current.getChapters().add(chapter);
                }
            }

            change = true;
        }
        else if(isAllSearch){
            current.setChapters(ActuallyAdditionsAPI.ALL_CHAPTERS);
            change = true;
        }

        if(change){
            openIndexEntry(booklet, current, booklet.currentEntrySet.getPageInIndex(), false);
        }
    }

    @SideOnly(Side.CLIENT)
    private static boolean getChapterStacksContainString(String text, IBookletChapter chapter){
        Minecraft mc = Minecraft.getMinecraft();
        for(BookletPage page : chapter.getPages()){
            ItemStack[] pageStacks = page.getItemStacksForPage();
            if(pageStacks != null){
                for(ItemStack stack : pageStacks){
                    if(stack != null && stack.getItem() != null){
                        if(doesTooltipContainString(stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips), text)){
                            return true;
                        }
                    }
                }
            }
            FluidStack[] pageFluids = page.getFluidStacksForPage();
            if(pageFluids != null){
                for(FluidStack stack : pageFluids){
                    if(stack != null && stack.getFluid() != null){
                        if(doesTooltipContainString(Collections.singletonList(stack.getLocalizedName()), text)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    private static boolean doesTooltipContainString(List<String> tooltip, String text){
        for(String s : tooltip){
            if(s != null && !s.isEmpty()){
                if(s.toLowerCase(Locale.ROOT).contains(text)){
                    return true;
                }
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public static void openIndexEntry(GuiBooklet booklet, IBookletEntry entry, int page, boolean resetTextField){
        if(booklet.currentEntrySet.getCurrentPage() != null){
            booklet.currentEntrySet.getCurrentPage().onClosed(booklet);
        }
        booklet.currentEntrySet.setPage(null);
        booklet.currentEntrySet.setChapter(null);

        booklet.currentEntrySet.setEntry(entry);
        booklet.indexPageAmount = entry == null ? 1 : entry.getChapters().size()/booklet.chapterButtons.length+1;
        booklet.currentEntrySet.setPageInIndex(entry == null ? 1 : (booklet.indexPageAmount <= page || page <= 0 ? booklet.indexPageAmount : page));

        booklet.buttonPreviousScreen.visible = entry != null;
        booklet.buttonForward.visible = booklet.currentEntrySet.getPageInIndex() < booklet.indexPageAmount;
        booklet.buttonBackward.visible = booklet.currentEntrySet.getPageInIndex() > 1;

        booklet.buttonViewOnline.visible = false;

        for(int i = 0; i < booklet.chapterButtons.length; i++){
            IndexButton button = (IndexButton)booklet.chapterButtons[i];
            if(entry == null){
                if(i >= GuiBooklet.INDEX_BUTTONS_OFFSET){
                    boolean entryExists = ActuallyAdditionsAPI.BOOKLET_ENTRIES.size() > i-GuiBooklet.INDEX_BUTTONS_OFFSET;
                    button.visible = entryExists;
                    if(entryExists){
                        button.displayString = "- "+ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(i-GuiBooklet.INDEX_BUTTONS_OFFSET).getLocalizedNameWithFormatting();
                        button.chap = null;
                    }
                }
                else{
                    button.visible = false;
                }
            }
            else{
                boolean entryExists = entry.getChapters().size() > i+(booklet.chapterButtons.length*booklet.currentEntrySet.getPageInIndex()-booklet.chapterButtons.length);
                button.visible = entryExists;
                if(entryExists){
                    IBookletChapter chap = entry.getChapters().get(i+(booklet.chapterButtons.length*booklet.currentEntrySet.getPageInIndex()-booklet.chapterButtons.length));
                    button.displayString = chap.getLocalizedNameWithFormatting();
                    button.chap = chap;
                }
            }
        }

        if(resetTextField){
            booklet.searchField.setText("");
            updateSearchBar(booklet);
        }

        booklet.shouldSaveDataNextClose = true;
    }

    /**
     * Called when one of the buttons to open an index or a chapter is pressed
     */
    @SideOnly(Side.CLIENT)
    public static void handleChapterButtonClick(GuiBooklet booklet, GuiButton button){
        int place = ArrayUtils.indexOf(booklet.chapterButtons, button);
        if(place >= 0){
            if(booklet.currentEntrySet.getCurrentEntry() != null){
                if(booklet.currentEntrySet.getCurrentChapter() == null){
                    if(place < booklet.currentEntrySet.getCurrentEntry().getChapters().size()){
                        //Clear the search bar when entering a chapter
                        booklet.searchField.setText("");
                        updateSearchBar(booklet);

                        IBookletChapter chap = booklet.currentEntrySet.getCurrentEntry().getChapters().get(place+(booklet.chapterButtons.length*booklet.currentEntrySet.getPageInIndex()-booklet.chapterButtons.length));
                        openChapter(booklet, chap, chap.getPages()[0]);
                    }
                }
            }
            else{
                if(place-GuiBooklet.INDEX_BUTTONS_OFFSET < ActuallyAdditionsAPI.BOOKLET_ENTRIES.size()){
                    openIndexEntry(booklet, ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(place-GuiBooklet.INDEX_BUTTONS_OFFSET), 1, true);
                }
            }
        }
    }

    /**
     * Opens a chapter in the booklet.
     * Can only be done when the chapter is not null and an index entry is opened in the booklet
     */
    @SideOnly(Side.CLIENT)
    public static void openChapter(GuiBooklet booklet, IBookletChapter chapter, BookletPage page){
        if(chapter == null || booklet.currentEntrySet.getCurrentEntry() == null){
            return;
        }

        booklet.currentEntrySet.setChapter(chapter);

        if(booklet.currentEntrySet.getCurrentPage() != null){
            booklet.currentEntrySet.getCurrentPage().onClosed(booklet);
        }
        BookletPage pageToSet = page != null && doesChapterHavePage(chapter, page) ? page : chapter.getPages()[0];
        booklet.currentEntrySet.setPage(pageToSet);
        pageToSet.onOpened(booklet);

        booklet.buttonForward.visible = getNextPage(chapter, booklet.currentEntrySet.getCurrentPage()) != null;
        booklet.buttonBackward.visible = getPrevPage(chapter, booklet.currentEntrySet.getCurrentPage()) != null;
        booklet.buttonPreviousScreen.visible = true;

        booklet.buttonViewOnline.visible = true;

        for(GuiButton chapterButton : booklet.chapterButtons){
            chapterButton.visible = false;
        }

        booklet.shouldSaveDataNextClose = true;
    }

    /**
     * Checks if a chapter has a certain page
     */
    @SideOnly(Side.CLIENT)
    private static boolean doesChapterHavePage(IBookletChapter chapter, BookletPage page){
        for(BookletPage aPage : chapter.getPages()){
            if(aPage == page){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the next available page in the booklet (or null if there is none)
     */
    @SideOnly(Side.CLIENT)
    private static BookletPage getNextPage(IBookletChapter chapter, BookletPage page){
        for(int i = 0; i < chapter.getPages().length; i++){
            if(chapter.getPages()[i] == page){
                if(i+1 < chapter.getPages().length){
                    return chapter.getPages()[i+1];
                }
            }
        }
        return null;
    }

    /**
     * Gets the previous available page in the booklet (or null if there is none)
     */
    @SideOnly(Side.CLIENT)
    private static BookletPage getPrevPage(IBookletChapter chapter, BookletPage page){
        for(int i = 0; i < chapter.getPages().length; i++){
            if(chapter.getPages()[i] == page){
                if(i-1 >= 0){
                    return chapter.getPages()[i-1];
                }
            }
        }
        return null;
    }

    /**
     * Called when the "next page"-button is pressed
     */
    @SideOnly(Side.CLIENT)
    public static void handleNextPage(GuiBooklet booklet){
        if(booklet.currentEntrySet.getCurrentEntry() != null){
            if(booklet.currentEntrySet.getCurrentPage() != null){
                BookletPage page = getNextPage(booklet.currentEntrySet.getCurrentChapter(), booklet.currentEntrySet.getCurrentPage());
                if(page != null){
                    booklet.currentEntrySet.getCurrentPage().onClosed(booklet);
                    booklet.currentEntrySet.setPage(page);
                    page.onOpened(booklet);
                }

                booklet.buttonForward.visible = getNextPage(booklet.currentEntrySet.getCurrentChapter(), booklet.currentEntrySet.getCurrentPage()) != null;
                booklet.buttonBackward.visible = getPrevPage(booklet.currentEntrySet.getCurrentChapter(), booklet.currentEntrySet.getCurrentPage()) != null;
            }
            else{
                if(booklet.currentEntrySet.getPageInIndex()+1 <= booklet.indexPageAmount){
                    openIndexEntry(booklet, booklet.currentEntrySet.getCurrentEntry(), booklet.currentEntrySet.getPageInIndex()+1, !(booklet.currentEntrySet.getCurrentEntry() instanceof BookletEntryAllSearch));
                }
            }
        }

        booklet.shouldSaveDataNextClose = true;
    }

    /**
     * Called when the "previous page"-button is pressed
     */
    @SideOnly(Side.CLIENT)
    public static void handlePreviousPage(GuiBooklet booklet){
        if(booklet.currentEntrySet.getCurrentEntry() != null){
            if(booklet.currentEntrySet.getCurrentPage() != null){
                BookletPage page = getPrevPage(booklet.currentEntrySet.getCurrentChapter(), booklet.currentEntrySet.getCurrentPage());
                if(page != null){
                    booklet.currentEntrySet.getCurrentPage().onClosed(booklet);
                    booklet.currentEntrySet.setPage(page);
                    page.onOpened(booklet);
                }

                booklet.buttonForward.visible = getNextPage(booklet.currentEntrySet.getCurrentChapter(), booklet.currentEntrySet.getCurrentPage()) != null;
                booklet.buttonBackward.visible = getPrevPage(booklet.currentEntrySet.getCurrentChapter(), booklet.currentEntrySet.getCurrentPage()) != null;
            }
            else{
                if(booklet.currentEntrySet.getPageInIndex()-1 > 0){
                    openIndexEntry(booklet, booklet.currentEntrySet.getCurrentEntry(), booklet.currentEntrySet.getPageInIndex()-1, !(booklet.currentEntrySet.getCurrentEntry() instanceof BookletEntryAllSearch));
                }
            }
        }

        booklet.shouldSaveDataNextClose = true;
    }

    public static BookletPage getFirstPageForStack(ItemStack stack){
        ArrayList<BookletPage> pages = getPagesForStack(stack);
        return pages.isEmpty() ? null : pages.get(0);
    }

    public static BookletPage getFirstPageForStack(FluidStack stack){
        ArrayList<BookletPage> pages = getPagesForStack(stack);
        return pages.isEmpty() ? null : pages.get(0);
    }

    public static ArrayList<BookletPage> getPagesForStack(ItemStack stack){
        ArrayList<BookletPage> possiblePages = new ArrayList<BookletPage>();
        for(BookletPage page : ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA){
            if(ItemUtil.contains(page.getItemStacksForPage(), stack, page.arePageStacksWildcard)){
                possiblePages.add(page);
            }
        }
        return possiblePages;
    }

    public static ArrayList<BookletPage> getPagesForStack(FluidStack stack){
        ArrayList<BookletPage> possiblePages = new ArrayList<BookletPage>();
        for(BookletPage page : ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA){
            for(FluidStack pageStack : page.getFluidStacksForPage()){
                if(pageStack != null && pageStack.isFluidEqual(stack)){
                    possiblePages.add(page);
                    break;
                }
            }
        }
        return possiblePages;
    }

    @SideOnly(Side.CLIENT)
    public static void saveBookPage(GuiBooklet gui, NBTTagCompound compound){
        //Save Entry etc.
        NBTTagCompound tag = new NBTTagCompound();
        gui.currentEntrySet.writeToNBT(tag);
        compound.setTag("SavedEntry", tag);
        compound.setString("SearchWord", gui.searchField.getText());

        //Save Bookmarks
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < gui.bookmarkButtons.length; i++){
            BookmarkButton button = gui.bookmarkButtons[i];

            NBTTagCompound buttonTag = new NBTTagCompound();
            button.assignedEntry.writeToNBT(buttonTag);
            list.appendTag(buttonTag);
        }
        compound.setTag("Bookmarks", list);
    }

    @SideOnly(Side.CLIENT)
    public static void openLastBookPage(GuiBooklet gui, NBTTagCompound compound){
        //Open Entry etc.
        EntrySet set = new EntrySet(null);
        set.readFromNBT(compound.getCompoundTag("SavedEntry"));
        if(set != null){

            BookletUtils.openIndexEntry(gui, set.getCurrentEntry(), set.getPageInIndex(), true);
            if(set.getCurrentChapter() != null){
                BookletUtils.openChapter(gui, set.getCurrentChapter(), set.getCurrentPage());
            }

            String searchText = compound.getString("SearchWord");
            if(!searchText.isEmpty()){
                gui.searchField.setText(searchText);
                BookletUtils.updateSearchBar(gui);
            }
        }
        else{
            //If everything fails, initialize the front page
            BookletUtils.openIndexEntry(gui, null, 1, true);
        }

        //Load Bookmarks
        NBTTagList list = compound.getTagList("Bookmarks", 10);
        if(list != null){
            for(int i = 0; i < list.tagCount(); i++){
                BookmarkButton button = gui.bookmarkButtons[i];
                button.assignedEntry.readFromNBT(list.getCompoundTagAt(i));
            }
        }
    }
}
