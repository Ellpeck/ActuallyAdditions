/*
 * This file ("IndexButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.booklet.button;

import de.ellpeck.actuallyadditions.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class IndexButton extends GuiButton{

    public BookletChapter chap;
    private GuiBooklet gui;

    public IndexButton(int id, int x, int y, int width, int height, String text, GuiBooklet gui){
        super(id, x, y, width, height, text);
        this.gui = gui;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY){
        if(this.visible){
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition+this.width && mouseY < this.yPosition+this.height;
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.mouseDragged(minecraft, mouseX, mouseY);

            int textOffsetX = 0;
            if(this.chap != null){
                if(this.chap.displayStack != null){
                    GL11.glPushMatrix();
                    AssetUtil.renderStackToGui(this.chap.displayStack, this.xPosition-4, this.yPosition, 0.725F);
                    GL11.glPopMatrix();
                    textOffsetX = 10;
                }
            }

            if(this.field_146123_n){
                GL11.glPushMatrix();
                AssetUtil.drawHorizontalGradientRect(this.xPosition+textOffsetX-1, this.yPosition+this.height-1, this.xPosition+this.gui.getFontRenderer().getStringWidth(this.displayString)+textOffsetX+1, this.yPosition+this.height, 0x80 << 24 | 22271, 22271);
                GL11.glPopMatrix();
            }

            this.gui.getFontRenderer().drawString(this.displayString, this.xPosition+textOffsetX, this.yPosition+(this.height-8)/2, 0);
        }
    }
}
