/*
 * This file ("BookletChapterCoffee.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.chapter;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.booklet.page.BookletPage;
import de.ellpeck.actuallyadditions.mod.booklet.page.PageCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookletChapterCoffee extends BookletChapter{

    public BookletChapterCoffee(String identifier, IBookletEntry entry, ItemStack displayStack, IBookletPage... pages){
        super(identifier, entry, displayStack, getPages(pages));
    }

    private static IBookletPage[] getPages(IBookletPage... pages){
        List<IBookletPage> allPages = new ArrayList<IBookletPage>();
        allPages.addAll(Arrays.asList(pages));

        for(CoffeeIngredient ingredient : ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS){
            BookletPage page = new PageCoffeeMachine(allPages.size()+1, ingredient);
            if(!(ingredient instanceof ItemCoffee.MilkIngredient)){
                page.setNoText();
            }
            allPages.add(page);
        }

        return allPages.toArray(new IBookletPage[allPages.size()]);
    }
}
