package ellpeck.actuallyadditions.inventory.gui.lexicon;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;

import java.util.ArrayList;

public class BookletIndexEntry{

    private final String unlocalizedName;
    public ArrayList<BookletChapter> chapters = new ArrayList<BookletChapter>();

    public BookletIndexEntry(String unlocalizedName){
        this.unlocalizedName = unlocalizedName;
        InitBooklet.entries.add(this);
    }

    public String getUnlocalizedName(){
        return this.unlocalizedName;
    }

    public void addChapter(BookletChapter chapter){
        this.chapters.add(chapter);
    }

    public String getLocalizedName(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".indexEntry."+this.unlocalizedName+".name");
    }

}
