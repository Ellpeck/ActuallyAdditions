/*
 * This file ("BookletChapterCrusher.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.booklet.page.PageCrusherRecipe;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BookletChapterCrusher extends BookletChapter{

    public BookletChapterCrusher(String unlocalizedName, BookletIndexEntry entry, ItemStack displayStack, BookletPage... pages){
        super(unlocalizedName, entry, displayStack, getAllPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static BookletPage[] getAllPages(BookletPage... pages){
        ArrayList<BookletPage> list = new ArrayList<BookletPage>();
        list.addAll(Arrays.asList(pages));
        for(CrusherRecipeManualRegistry.CrusherRecipe rec : CrusherRecipeManualRegistry.recipes){
            list.add(new PageCrusherRecipe(list.size()+1, rec).setNoText());
        }
        return list.toArray(new BookletPage[list.size()]);
    }
}
