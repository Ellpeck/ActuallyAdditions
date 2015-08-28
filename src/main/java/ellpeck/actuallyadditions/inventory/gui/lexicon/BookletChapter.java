package ellpeck.actuallyadditions.inventory.gui.lexicon;

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
