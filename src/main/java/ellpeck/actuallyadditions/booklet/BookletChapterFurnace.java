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

import ellpeck.actuallyadditions.booklet.page.IBookletPage;
import ellpeck.actuallyadditions.booklet.page.PageFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Map;

public class BookletChapterFurnace extends BookletChapter{

    public BookletChapterFurnace(String unlocalizedName, BookletIndexEntry entry, IBookletPage... pages){
        super(unlocalizedName, entry, getAllPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static IBookletPage[] getAllPages(IBookletPage... pages){
        ArrayList<IBookletPage> list = new ArrayList<IBookletPage>();
        list.addAll(Arrays.asList(pages));
        for(Object o : FurnaceRecipes.smelting().getSmeltingList().entrySet()){
            list.add(new PageFurnace(list.size()+1, (ItemStack)((Map.Entry)o).getKey(), (ItemStack)((Map.Entry)o).getValue()));
        }
        return list.toArray(new IBookletPage[list.size()]);
    }
}
