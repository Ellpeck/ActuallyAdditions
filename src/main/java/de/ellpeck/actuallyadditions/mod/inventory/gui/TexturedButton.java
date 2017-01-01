/*
 * This file ("TexturedButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TexturedButton extends GuiButton{

    public final List textList = new ArrayList();
    private final ResourceLocation resLoc;
    public int texturePosX;
    public int texturePosY;

    public TexturedButton(ResourceLocation resLoc, int id, int x, int y, int texturePosX, int texturePosY, int width, int height){
        this(resLoc, id, x, y, texturePosX, texturePosY, width, height, new ArrayList());
    }

    public TexturedButton(ResourceLocation resLoc, int id, int x, int y, int texturePosX, int texturePosY, int width, int height, List hoverTextList){
        super(id, x, y, width, height, "");
        this.texturePosX = texturePosX;
        this.texturePosY = texturePosY;
        this.resLoc = resLoc;
        this.textList.addAll(hoverTextList);
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y){
        if(this.visible){
            minecraft.getTextureManager().bindTexture(this.resLoc);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
            int k = this.getHoverState(this.hovered);
            if(k == 0){
                k = 1;
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.texturePosX, this.texturePosY-this.height+k*this.height, this.width, this.height);
            this.mouseDragged(minecraft, x, y);
        }
    }

    public void drawHover(int x, int y){
        if(this.isMouseOver()){
            Minecraft mc = Minecraft.getMinecraft();
            GuiUtils.drawHoveringText(this.textList, x, y, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }
}
