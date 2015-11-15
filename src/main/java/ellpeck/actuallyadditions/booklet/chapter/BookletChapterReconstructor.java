/*
 * This file ("BookletChapterReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.chapter;

import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.booklet.page.PageReconstructor;
import ellpeck.actuallyadditions.recipe.AtomicReconstructorRecipeHandler;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BookletChapterReconstructor extends BookletChapter{

    public BookletChapterReconstructor(String unlocalizedName, BookletEntry entry, ItemStack displayStack, BookletPage... pages){
        super(unlocalizedName, entry, displayStack, getPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static BookletPage[] getPages(BookletPage... pages){
        ArrayList<BookletPage> allPages = new ArrayList<BookletPage>();
        allPages.addAll(Arrays.asList(pages));

        for(AtomicReconstructorRecipeHandler.Recipe recipe : AtomicReconstructorRecipeHandler.recipes){
            BookletPage page = new PageReconstructor(allPages.size()+1, recipe.getFirstOutput()).setNoText();
            allPages.add(page);
        }

        return allPages.toArray(new BookletPage[allPages.size()]);
    }
}
