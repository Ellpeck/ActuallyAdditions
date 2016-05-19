/*
 * This file ("NEIScreenEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import de.ellpeck.actuallyadditions.mod.booklet.button.TexturedButton;

public class NEIScreenEvents{

    private static final int NEI_BUTTON_ID = 123782;
    private TexturedButton neiButton;

    /*@SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onInitGuiForNEI(GuiScreenEvent.InitGuiEvent event){
        if(event.gui instanceof GuiRecipe){
            int xSize = 176;
            int ySize = 166;
            int guiLeft = (event.gui.width-xSize)/2;
            int guiTop = (event.gui.height-ySize)/2;

            this.neiButton = new TexturedButton(NEI_BUTTON_ID, guiLeft+xSize-24, guiTop+127, 146, 154, 20, 20){
                @Override
                public void drawButton(Minecraft minecraft, int x, int y){
                    super.drawButton(minecraft, x, y);
                    if(this.visible && this.hovered){
                        String text = StringUtil.localize("booklet."+ModUtil.MOD_ID+".clickToSeeRecipe");
                        Minecraft.getMinecraft().fontRendererObj.drawString(text, this.xPosition-Minecraft.getMinecraft().fontRendererObj.getStringWidth(text)-1, this.yPosition+this.height/2-Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                }
            };
            event.buttonList.add(this.neiButton);

            GuiRecipe theGui = (GuiRecipe)event.gui;

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
                    BookletUtils.openIndexEntry(book, page.getChapter().getEntry(), ActuallyAdditionsAPI.BOOKLET_ENTRIES.indexOf(page.getChapter().getEntry())/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                    BookletUtils.openChapter(book, page.getChapter(), page);
                }
            }
        }
    }*/
}
