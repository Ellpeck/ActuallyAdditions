/*
 * This file ("BookletChapterCrusher.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.chapter;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.booklet.page.PageCrusherRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BookletChapterCrusher extends BookletChapter{

    public BookletChapterCrusher(String unlocalizedName, IBookletEntry entry, ItemStack displayStack, BookletPage... pages){
        super(unlocalizedName, entry, displayStack, getPages(pages));
    }

    private static BookletPage[] getPages(BookletPage... pages){
        ArrayList<BookletPage> allPages = new ArrayList<BookletPage>();
        allPages.addAll(Arrays.asList(pages));

        for(CrusherRecipe recipe : CrusherCrafting.miscRecipes){
            allPages.add(new PageCrusherRecipe(allPages.size()+1, recipe).setNoText());
        }

        return allPages.toArray(new BookletPage[allPages.size()]);
    }
}
