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

import ellpeck.actuallyadditions.booklet.page.IBookletPage;
import ellpeck.actuallyadditions.booklet.page.PageCrusherRecipe;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;

public class BookletChapterCrusher extends BookletChapter{

    public BookletChapterCrusher(String unlocalizedName, BookletIndexEntry entry, IBookletPage... pages){
        super(unlocalizedName, entry, getAllPages(pages));
    }

    @SuppressWarnings("unchecked")
    private static IBookletPage[] getAllPages(IBookletPage... pages){
        ArrayList<IBookletPage> list = new ArrayList<IBookletPage>();
        list.addAll(Arrays.asList(pages));
        for(CrusherRecipeManualRegistry.CrusherRecipe rec : CrusherRecipeManualRegistry.recipes){
            list.add(new PageCrusherRecipe(list.size()+1, rec));
        }
        return list.toArray(new IBookletPage[list.size()]);
    }
}
