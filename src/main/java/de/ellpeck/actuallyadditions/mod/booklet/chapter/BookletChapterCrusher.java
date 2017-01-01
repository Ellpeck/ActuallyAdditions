/*
 * This file ("BookletChapterCrusher.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.chapter;

import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.booklet.page.PageCrusherRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookletChapterCrusher extends BookletChapter{

    public BookletChapterCrusher(String identifier, IBookletEntry entry, ItemStack displayStack, IBookletPage... pages){
        super(identifier, entry, displayStack, getPages(pages));
    }

    private static IBookletPage[] getPages(IBookletPage... pages){
        List<IBookletPage> allPages = new ArrayList<IBookletPage>();
        allPages.addAll(Arrays.asList(pages));

        for(CrusherRecipe recipe : CrusherCrafting.MISC_RECIPES){
            allPages.add(new PageCrusherRecipe(allPages.size()+1, recipe).setNoText());
        }

        return allPages.toArray(new IBookletPage[allPages.size()]);
    }
}
