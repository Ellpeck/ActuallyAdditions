/*
 * This file ("PageText.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui.booklet;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;

public class PageText implements IBookletPage{

    private int id;
    private BookletChapter chapter;

    public PageText(int id){
        this.id = id;
    }

    @Override
    public int getID(){
        return this.id;
    }

    @Override
    public void setChapter(BookletChapter chapter){
        this.chapter = chapter;
    }

    @Override
    public String getText(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.id);
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY){

    }

    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY){
        gui.unicodeRenderer.drawSplitString(gui.currentPage.getText(), gui.guiLeft+14, gui.guiTop+11, 115, 0);
    }
}
