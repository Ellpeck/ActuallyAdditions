/*
 * This file ("RecipeWrapperWithButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.button.TexturedButton;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;

public abstract class RecipeWrapperWithButton{

    protected TexturedButton theButton;

    public RecipeWrapperWithButton(){
        this.theButton = new TexturedButton(23782, 0, 84, 146, 154, 20, 20){
            @Override
            public void drawButton(Minecraft minecraft, int x, int y){
                super.drawButton(minecraft, x, y);
                if(this.visible && this.hovered){
                    String text = StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".clickToSeeRecipe");
                    Minecraft.getMinecraft().fontRendererObj.drawString(text, this.xPosition-Minecraft.getMinecraft().fontRendererObj.getStringWidth(text)-1, this.yPosition+this.height/2-Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2, StringUtil.DECIMAL_COLOR_WHITE, true);
                }
            }
        };
    }

    public boolean handleClick(Minecraft mc, int mouseX, int mouseY){
        if(this.theButton.mousePressed(mc, mouseX, mouseY)){
            this.theButton.playPressSound(mc.getSoundHandler());

            BookletPage page = this.getPage();
            if(page != null){
                GuiBooklet book = new GuiBooklet(Minecraft.getMinecraft().currentScreen, false, true);
                Minecraft.getMinecraft().displayGuiScreen(book);
                BookletUtils.openIndexEntry(book, page.getChapter().getEntry(), ActuallyAdditionsAPI.bookletEntries.indexOf(page.getChapter().getEntry())/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                BookletUtils.openChapter(book, page.getChapter(), page);
                return true;
            }
        }
        return false;
    }

    public void updateButton(Minecraft mc, int mouseX, int mouseY){
        this.theButton.drawButton(mc, mouseX, mouseY);
    }

    public abstract BookletPage getPage();
}
