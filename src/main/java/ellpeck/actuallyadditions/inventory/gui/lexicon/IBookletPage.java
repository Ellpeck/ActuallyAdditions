package ellpeck.actuallyadditions.inventory.gui.lexicon;

public interface IBookletPage{

    int getID();

    void setChapter(BookletChapter chapter);

    String getText();

    void render(GuiBooklet gui, int mouseX, int mouseY);
}
