/*
 * This file ("TexturedButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.button;

import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TexturedButton extends GuiButton{

    public int texturePosX;
    public int texturePosY;

    public final List textList = new ArrayList();

    public TexturedButton(int id, int x, int y, int texturePosX, int texturePosY, int width, int height){
        this(id, x, y, texturePosX, texturePosY, width, height, new ArrayList());
    }

    @SuppressWarnings("unchecked")
    public TexturedButton(int id, int x, int y, int texturePosX, int texturePosY, int width, int height, List hoverTextList){
        super(id, x, y, width, height, "");
        this.texturePosX = texturePosX;
        this.texturePosY = texturePosY;
        this.textList.addAll(hoverTextList);
    }

    public void setTexturePos(int x, int y){
        this.texturePosX = x;
        this.texturePosY = y;
    }

    @Override
    public void drawButton(@Nonnull Minecraft minecraft, int x, int y){
        if(this.visible){
            minecraft.getTextureManager().bindTexture(GuiBooklet.resLoc);
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
}
