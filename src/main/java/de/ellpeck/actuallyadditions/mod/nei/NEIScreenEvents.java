/*
 * This file ("NEIScreenEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.IRecipeHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.button.TexturedButton;
import de.ellpeck.actuallyadditions.mod.booklet.page.BookletPage;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiScreenEvent;

public class NEIScreenEvents{

    private static final int NEI_BUTTON_ID = 123782;
    private TexturedButton neiButton;

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onInitGuiForNEI(GuiScreenEvent.InitGuiEvent event){
        if(event.gui instanceof GuiRecipe){
            GuiRecipe theGui = (GuiRecipe)event.gui;

            int xSize = 176;
            int ySize = 166;
            int guiLeft = (event.gui.width-xSize)/2;
            int guiTop = (event.gui.height-ySize)/2;

            this.neiButton = new TexturedButton(NEI_BUTTON_ID, guiLeft+xSize-24, guiTop+127, 146, 154, 20, 20){
                @Override
                public void drawButton(Minecraft minecraft, int x, int y){
                    super.drawButton(minecraft, x, y);
                    if(this.visible && this.field_146123_n){
                        String text = StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".clickToSeeRecipe");
                        Minecraft.getMinecraft().fontRenderer.drawString(text, this.xPosition-Minecraft.getMinecraft().fontRenderer.getStringWidth(text)-1, this.yPosition+this.height/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                }
            };

            event.buttonList.add(this.neiButton);
            IRecipeHandler handler = theGui.getCurrentRecipeHandlers().get(theGui.recipetype);
            this.neiButton.visible = handler instanceof INEIRecipeHandler && ((INEIRecipeHandler)handler).getPageForInfo(theGui.page) != null;
        }
    }

    @SubscribeEvent
    public void guiPostAction(GuiScreenEvent.ActionPerformedEvent.Post event){
        if(this.neiButton != null && event.gui instanceof GuiRecipe){
            GuiRecipe theGui = (GuiRecipe)event.gui;

            IRecipeHandler handler = theGui.getCurrentRecipeHandlers().get(theGui.recipetype);
            boolean isPage = handler instanceof INEIRecipeHandler && ((INEIRecipeHandler)handler).getPageForInfo(theGui.page) != null;
            this.neiButton.visible = isPage;

            if(isPage && event.button.id == NEI_BUTTON_ID){
                BookletPage page = ((INEIRecipeHandler)handler).getPageForInfo(theGui.page);
                if(page != null){
                    GuiBooklet book = new GuiBooklet(Minecraft.getMinecraft().currentScreen, false, true);
                    Minecraft.getMinecraft().displayGuiScreen(book);
                    BookletUtils.openIndexEntry(book, page.getChapter().entry, InitBooklet.entries.indexOf(page.getChapter().entry)/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                    BookletUtils.openChapter(book, page.getChapter(), page);
                }
            }
        }
    }
}
