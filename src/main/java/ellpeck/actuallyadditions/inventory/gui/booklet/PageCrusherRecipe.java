/*
 * This file ("PageCrusher.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui.booklet;

import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class PageCrusherRecipe extends PageText{

    public ItemStack input;

    public PageCrusherRecipe(int id, ItemStack input){
        super(id);
        this.input = input;
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY){
        if(CrusherRecipeManualRegistry.getOutput(this.input, false) != null){
            gui.mc.getTextureManager().bindTexture(GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 60, 180, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY){
        ItemStack output = CrusherRecipeManualRegistry.getOutput(this.input, false);
        if(output == null){
            gui.unicodeRenderer.drawSplitString(StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0);
        }

        gui.unicodeRenderer.drawSplitString(gui.currentPage.getText(), gui.guiLeft+14, gui.guiTop+112, 115, 0);

        int secondChance = CrusherRecipeManualRegistry.getSecondChance(this.input);
        if(secondChance > 0){
            gui.unicodeRenderer.drawString(secondChance+"%", gui.guiLeft+37+62, gui.guiTop+20+35, 0);
        }

        if(output != null){
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    ItemStack stack = (j == 0 ? this.input : (j == 1 ? output : (j == 2 ? CrusherRecipeManualRegistry.getOutput(this.input, true) : null)));

                    if(stack != null){
                        boolean tooltip = i == 1;

                        int xShow = gui.guiLeft+37+(j == 0 ? 0 : (j == 1 ? 42 : (j == 2 ? 43 : 0)));
                        int yShow = gui.guiTop+20+(j == 0 ? 18 : (j == 1 ? 12 : (j == 2 ? 30 : 0)));
                        if(!tooltip){
                            RenderHelper.disableStandardItemLighting();
                            RenderItem.getInstance().renderItemAndEffectIntoGUI(gui.unicodeRenderer, gui.mc.getTextureManager(), stack, xShow, yShow);
                            RenderHelper.enableStandardItemLighting();
                        }
                        else{
                            if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                gui.renderToolTip(stack, mouseX, mouseY);
                            }
                        }
                    }
                }
            }
        }
    }
}
