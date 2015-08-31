/*
 * This file ("PageText.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.page;

import ellpeck.actuallyadditions.booklet.GuiBooklet;

public class PageText extends BookletPage{

    public PageText(int id){
        super(id);
    }

    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){
        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty() && !text.contains("booklet.")){
            gui.unicodeRenderer.drawSplitString(text.replace("<n>", "\n"), gui.guiLeft+14, gui.guiTop+11, 115, 0);
        }
    }
}
