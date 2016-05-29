/*
 * This file ("IndexButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.button;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapter;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

public class IndexButton extends GuiButton{

    public IBookletChapter chap;
    private final GuiBooklet gui;

    public IndexButton(int id, int x, int y, int width, int height, String text, GuiBooklet gui){
        super(id, x, y, width, height, text);
        this.gui = gui;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY){
        if(this.visible){
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition+this.width && mouseY < this.yPosition+this.height;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.mouseDragged(minecraft, mouseX, mouseY);

            int textOffsetX = 0;
            if(this.chap != null){
                if(this.chap.getDisplayItemStack() != null){
                    GlStateManager.pushMatrix();
                    AssetUtil.renderStackToGui(this.chap.getDisplayItemStack(), this.xPosition-4, this.yPosition, 0.725F);
                    GlStateManager.popMatrix();
                    textOffsetX = 10;
                }
            }

            if(this.hovered){
                GlStateManager.pushMatrix();
                AssetUtil.drawHorizontalGradientRect(this.xPosition+textOffsetX-1, this.yPosition+this.height-1, this.xPosition+this.gui.getFontRenderer().getStringWidth(this.displayString)+textOffsetX+1, this.yPosition+this.height, 0x80 << 24 | 22271, 22271, this.zLevel);
                GlStateManager.popMatrix();
            }

            this.gui.getFontRenderer().drawString(this.displayString, this.xPosition+textOffsetX, this.yPosition+(this.height-8)/2, 0);
        }
    }

    public void drawHover(int mouseX, int mouseY){
        if(this.chap instanceof BookletChapter && ((BookletChapter)this.chap).isIncomplete){
            this.gui.drawHoveringText(this.gui.getFontRenderer().listFormattedStringToWidth(TextFormatting.RED+StringUtil.localize("booklet."+ModUtil.MOD_ID+".unavailable"), 250), mouseX, mouseY);
        }
    }
}
