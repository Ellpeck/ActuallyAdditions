/*
 * This file ("BookletChapterCrusher.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.booklet.chapter;

import de.ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import de.ellpeck.actuallyadditions.booklet.page.BookletPage;
import de.ellpeck.actuallyadditions.booklet.page.PageCrusherRecipe;
import de.ellpeck.actuallyadditions.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BookletChapterCrusher extends BookletChapter{

    public BookletChapterCrusher(String unlocalizedName, BookletEntry entry, ItemStack displayStack, BookletPage... pages){
        super(unlocalizedName, entry, displayStack, getPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static BookletPage[] getPages(BookletPage... pages){
        ArrayList<BookletPage> allPages = new ArrayList<BookletPage>();
        allPages.addAll(Arrays.asList(pages));

        for(CrusherRecipeRegistry.CrusherRecipe recipe : CrusherCrafting.miscRecipes){
            allPages.add(new PageCrusherRecipe(allPages.size()+1, recipe).setNoText());
        }

        return allPages.toArray(new BookletPage[allPages.size()]);
    }
}
