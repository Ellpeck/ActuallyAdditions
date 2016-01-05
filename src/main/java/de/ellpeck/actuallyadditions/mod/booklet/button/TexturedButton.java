/*
 * This file ("TexturedButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.button;

import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class TexturedButton extends GuiButton{

    public int texturePosX;
    public int texturePosY;

    public List textList = new ArrayList();

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
    public void drawButton(Minecraft minecraft, int x, int y){
        if(this.visible){
            minecraft.getTextureManager().bindTexture(GuiBooklet.resLoc);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
            int k = this.getHoverState(this.field_146123_n);
            if(k == 0){
                k = 1;
            }

            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.texturePosX, this.texturePosY-this.height+k*this.height, this.width, this.height);
            this.mouseDragged(minecraft, x, y);
        }
    }
}
