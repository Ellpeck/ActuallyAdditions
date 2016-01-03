/*
 * This file ("PagePicture.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
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
    @SideOnly(Side.CLIENT)
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        gui.mc.getTextureManager().bindTexture(this.resLoc);
        gui.drawTexturedModalRect(gui.guiLeft, gui.guiTop, 0, 0, gui.xSize, gui.ySize);

        String text = gui.currentEntrySet.page.getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(gui.mc.fontRenderer, text, gui.guiLeft+14, gui.guiTop+textStartY, 115, 0, false);
        }
    }
}
