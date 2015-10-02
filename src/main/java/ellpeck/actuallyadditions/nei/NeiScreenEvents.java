/*
 * This file ("InitGuiEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.nei;

import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.IRecipeHandler;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.util.CompatUtil;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;

public class NeiScreenEvents{

    private static final int NEI_BUTTON_ID = 123782;
    private GuiBooklet.TexturedButton neiButton;

    @Optional.Method(modid = CompatUtil.NEI_MOD_ID)
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onInitGuiForNEI(GuiScreenEvent.InitGuiEvent event){
        if(event.gui instanceof GuiRecipe){
            GuiRecipe theGui = (GuiRecipe)event.gui;

            int xSize = 176;
            int ySize = 166;
            int guiLeft = (event.gui.width-xSize)/2;
            int guiTop = (event.gui.height-ySize)/2;

            this.neiButton = new GuiBooklet.TexturedButton(NEI_BUTTON_ID, guiLeft+xSize-24, guiTop+ySize/2-20, 146, 154, 20, 20){
                @Override
                public void drawButton(Minecraft minecraft, int x, int y){
                    super.drawButton(minecraft, x, y);
                    if(this.visible && this.field_146123_n){
                        this.drawString(Minecraft.getMinecraft().fontRenderer, "View more Information", this.xPosition+this.width+5, this.yPosition+this.height/2-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                }
            };

            event.buttonList.add(this.neiButton);
            this.neiButton.visible = theGui.getCurrentRecipeHandlers().get(theGui.recipetype) instanceof INeiRecipeHandler;
        }
    }

    @Optional.Method(modid = CompatUtil.NEI_MOD_ID)
    @SubscribeEvent
    public void guiPostAction(GuiScreenEvent.ActionPerformedEvent.Post event){
        if(this.neiButton != null && event.gui instanceof GuiRecipe){
            GuiRecipe theGui = (GuiRecipe)event.gui;

            IRecipeHandler handler = theGui.getCurrentRecipeHandlers().get(theGui.recipetype);
            boolean isPage = handler instanceof INeiRecipeHandler && ((INeiRecipeHandler)handler).getStackForInfo() != null;
            this.neiButton.visible = isPage;

            if(isPage && event.button.id == NEI_BUTTON_ID){
                for(BookletPage page : InitBooklet.pagesWithItemStackData){
                    if(ItemUtil.contains(page.getItemStacksForPage(), ((INeiRecipeHandler)handler).getStackForInfo(), true)){
                        GuiBooklet book = new GuiBooklet(Minecraft.getMinecraft().currentScreen);
                        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
                        Minecraft.getMinecraft().displayGuiScreen(book);
                        book.openIndexEntry(page.getChapter().entry, InitBooklet.entries.indexOf(page.getChapter().entry)/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                        book.openChapter(page.getChapter(), page);
                    }
                }
            }
        }
    }
}
