package ellpeck.actuallyadditions.inventory.gui.lexicon;

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
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.id+".name");
    }

    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY){
        gui.unicodeRenderer.drawSplitString(gui.currentPage.getText(), gui.guiLeft+14, gui.guiTop+11, 115, 0);
    }
}
