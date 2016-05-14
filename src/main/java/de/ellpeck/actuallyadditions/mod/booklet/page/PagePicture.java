/*
 * This file ("PagePicture.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resLoc);
        gui.drawRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, gui.getXSize(), gui.getYSize());

        String text = gui.getCurrentEntrySet().getCurrentPage().getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, text, gui.getGuiLeft()+14, gui.getGuiTop()+this.textStartY, 115, 0, false);
        }
    }
}
