package de.ellpeck.actuallyadditions.booklet.chapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.booklet.page.PageCrusherRecipe;
import de.ellpeck.actuallyadditions.common.crafting.CrusherCrafting;
import net.minecraft.item.ItemStack;

public class BookletChapterCrusher extends BookletChapter {

    public BookletChapterCrusher(String identifier, IBookletEntry entry, ItemStack displayStack, IBookletPage... pages) {
        super(identifier, entry, displayStack, getPages(pages));
    }

    private static IBookletPage[] getPages(IBookletPage... pages) {
        List<IBookletPage> allPages = new ArrayList<>();
        allPages.addAll(Arrays.asList(pages));

        for (CrusherRecipe recipe : CrusherCrafting.MISC_RECIPES) {
            allPages.add(new PageCrusherRecipe(allPages.size() + 1, recipe).setNoText());
        }

        return allPages.toArray(new IBookletPage[allPages.size()]);
    }
}
