/*
 * This file ("BookletChapter.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.chapter;

import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntry;
import de.ellpeck.actuallyadditions.mod.booklet.page.BookletPage;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class BookletChapter{

    public final BookletPage[] pages;
    public final BookletEntry entry;
    public final ItemStack displayStack;
    private final String unlocalizedName;
    public EnumChatFormatting color;

    public BookletChapter(String unlocalizedName, BookletEntry entry, ItemStack displayStack, BookletPage... pages){
        this.pages = pages.clone();

        this.unlocalizedName = unlocalizedName;
        entry.addChapter(this);
        InitBooklet.allAndSearch.addChapter(this);
        this.entry = entry;
        this.displayStack = displayStack;

        for(BookletPage page : this.pages){
            page.setChapter(this);
        }

        this.color = EnumChatFormatting.RESET;
    }

    public String getUnlocalizedName(){
        return this.unlocalizedName;
    }

    public String getNameWithColor(){
        return this.color+this.getLocalizedName();
    }

    public String getLocalizedName(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.unlocalizedName+".name");
    }

    public BookletChapter setImportant(){
        this.color = EnumChatFormatting.DARK_GREEN;
        return this;
    }

    public BookletChapter setSpecial(){
        this.color = EnumChatFormatting.DARK_PURPLE;
        return this;
    }
}
