/*
 * This file ("BookletChapterFurnace.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.booklet.page.PageFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class BookletChapterFurnace extends BookletChapter{

    public BookletChapterFurnace(String unlocalizedName, BookletIndexEntry entry, ItemStack displayStack, BookletPage... pages){
        super(unlocalizedName, entry, displayStack, getAllPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static BookletPage[] getAllPages(BookletPage... pages){
        ArrayList<BookletPage> list = new ArrayList<BookletPage>();
        list.addAll(Arrays.asList(pages));
        for(Object o : FurnaceRecipes.smelting().getSmeltingList().entrySet()){
            list.add(new PageFurnace(list.size()+1, (ItemStack)((Map.Entry)o).getKey(), (ItemStack)((Map.Entry)o).getValue()).setNoText());
        }
        return list.toArray(new BookletPage[list.size()]);
    }
}
