/*
 * This file ("BookletIndexEntry.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

public class BookletIndexEntry{

    private final String unlocalizedName;
    public ArrayList<BookletChapter> chapters = new ArrayList<BookletChapter>();
    private EnumChatFormatting color;

    public BookletIndexEntry(String unlocalizedName){
        this.unlocalizedName = unlocalizedName;
        InitBooklet.entries.add(this);

        this.color = EnumChatFormatting.RESET;
    }

    public String getUnlocalizedName(){
        return this.unlocalizedName;
    }

    public void addChapter(BookletChapter chapter){
        this.chapters.add(chapter);
    }

    public String getNameWithColor(){
        return this.color+this.getLocalizedName();
    }

    public String getLocalizedName(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".indexEntry."+this.unlocalizedName+".name");
    }

    public BookletIndexEntry setImportant(){
        this.color = EnumChatFormatting.DARK_GREEN;
        return this;
    }

    public BookletIndexEntry setSpecial(){
        this.color = EnumChatFormatting.DARK_PURPLE;
        return this;
    }

}
