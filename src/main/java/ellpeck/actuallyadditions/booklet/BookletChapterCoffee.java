/*
 * This file ("BookletChapterCoffee.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.booklet.page.PageCoffeeRecipe;
import ellpeck.actuallyadditions.items.ItemCoffee;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BookletChapterCoffee extends BookletChapter{

    public BookletChapterCoffee(String unlocalizedName, BookletIndexEntry entry, ItemStack displayStack, BookletPage... pages){
        super(unlocalizedName, entry, displayStack, getPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static BookletPage[] getPages(BookletPage... pages){
        ArrayList<BookletPage> allPages = new ArrayList<BookletPage>();
        allPages.addAll(Arrays.asList(pages));

        for(ItemCoffee.Ingredient ingredient : ItemCoffee.ingredients){
            BookletPage page = new PageCoffeeRecipe(allPages.size()+1, ingredient);
            if(!(ingredient instanceof ItemCoffee.MilkIngredient)){
                page.setNoText();
            }
            allPages.add(page);
        }

        return allPages.toArray(new BookletPage[allPages.size()]);
    }
}
