/*
 * This file ("PagePicture.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.page;

import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.util.ResourceLocation;

public class PagePicture extends PageTextOnly{

    private ResourceLocation resLoc;
    private int textStartY;

    public PagePicture(int id, String resLocName, int textStartY){
        super(id);
        this.textStartY = textStartY;
        this.resLoc = AssetUtil.getBookletGuiLocation(resLocName);
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        gui.mc.getTextureManager().bindTexture(this.resLoc);
        gui.drawTexturedModalRect(gui.guiLeft, gui.guiTop, 0, 0, gui.xSize, gui.ySize);

        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty()){
            gui.mc.fontRenderer.drawSplitString(text, gui.guiLeft+14, gui.guiTop+textStartY, 115, 0);
        }
    }
}
