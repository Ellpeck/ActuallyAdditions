/*
 * This file ("BookletUtils.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.misc;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiEntry;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiMainPage;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiPage;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

import java.util.List;

public final class BookletUtils{

    public static IBookletPage findFirstPageForStack(ItemStack stack){
        for(IBookletPage page : ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA){
            List<ItemStack> stacks = page.getItemStacksForPage();
            if(stacks != null && !stacks.isEmpty()){
                for(ItemStack pageStack : stacks){
                    if(ItemUtil.areItemsEqual(pageStack, stack, true)){
                        return page;
                    }
                }
            }
        }
        return null;
    }

    public static GuiPage createBookletGuiFromPage(GuiScreen previousScreen, IBookletPage page){
        GuiMainPage mainPage = new GuiMainPage(previousScreen);

        IBookletChapter chapter = page.getChapter();
        GuiEntry entry = new GuiEntry(previousScreen, mainPage, chapter.getEntry(), chapter);

        return createPageGui(previousScreen, entry, page);
    }

    public static GuiPage createPageGui(GuiScreen previousScreen, GuiBooklet parentPage, IBookletPage page){
        IBookletChapter chapter = page.getChapter();

        IBookletPage[] allPages = chapter.getAllPages();
        int pageIndex = chapter.getPageNum(page)-1;
        IBookletPage page1;
        IBookletPage page2;

        if(page.shouldBeOnLeftSide()){
            page1 = page;
            page2 = pageIndex >= allPages.length-1 ? null : allPages[pageIndex+1];
        }
        else{
            page1 = pageIndex <= 0 ? null : allPages[pageIndex-1];
            page2 = page;
        }

        return new GuiPage(previousScreen, parentPage, page1, page2);
    }
}
