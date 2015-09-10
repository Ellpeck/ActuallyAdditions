/*
 * This file ("BookletChapter.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.item.ItemStack;

public class BookletChapter{

    public final BookletPage[] pages;
    private final String unlocalizedName;
    public final BookletIndexEntry entry;
    public final ItemStack displayStack;

    public BookletChapter(String unlocalizedName, BookletIndexEntry entry, ItemStack displayStack, BookletPage... pages){
        this.pages = pages.clone();

        this.unlocalizedName = unlocalizedName;
        entry.addChapter(this);
        InitBooklet.allAndSearch.addChapter(this);
        this.entry = entry;
        this.displayStack = displayStack;

        for(BookletPage page : this.pages){
            page.setChapter(this);
        }
    }

    public String getUnlocalizedName(){
        return this.unlocalizedName;
    }

    public String getLocalizedName(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.unlocalizedName+".name");
    }
}
