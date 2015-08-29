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

import ellpeck.actuallyadditions.booklet.page.IBookletPage;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;

public class BookletChapter{

    public final IBookletPage[] pages;
    private final String unlocalizedName;
    public final BookletIndexEntry entry;

    public BookletChapter(String unlocalizedName, BookletIndexEntry entry, IBookletPage... pages){
        this.pages = pages.clone();

        this.unlocalizedName = unlocalizedName;
        entry.addChapter(this);
        InitBooklet.allAndSearch.addChapter(this);
        this.entry = entry;

        for(IBookletPage page : this.pages){
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
