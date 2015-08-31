/*
 * This file ("PageCrusher.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.page;

import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;

public class PageCrusherRecipe extends BookletPage{

    public CrusherRecipeManualRegistry.CrusherRecipe recipe;

    public PageCrusherRecipe(int id, CrusherRecipeManualRegistry.CrusherRecipe recipe){
        super(id);
        this.recipe = recipe;
        InitBooklet.pagesWithItemStackData.add(this);
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){
        if(recipe != null){
            gui.mc.getTextureManager().bindTexture(GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 60, 180, 60, 60);
        }
    }

    @Override
    public ItemStack getItemStackForPage(){
        return this.recipe == null ? null : this.recipe.firstOutput;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){
        if(recipe == null){
            gui.unicodeRenderer.drawSplitString(StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0);
        }

        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty() && !text.contains("booklet.")){
            gui.unicodeRenderer.drawSplitString(text.replace("<n>", "\n"), gui.guiLeft+14, gui.guiTop+100, 115, 0);
        }

        if(recipe.secondChance > 0){
            gui.unicodeRenderer.drawString(recipe.secondChance+"%", gui.guiLeft+37+62, gui.guiTop+20+33, 0);
        }

        if(recipe.firstOutput != null){
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    ItemStack stack = (j == 0 ? this.recipe.input : (j == 1 ? recipe.firstOutput : (j == 2 ? recipe.secondOutput : null)));

                    if(stack != null){
                        if(stack.getItemDamage() == Util.WILDCARD) stack.setItemDamage(0);

                        boolean tooltip = i == 1;

                        int xShow = gui.guiLeft+37+(j == 0 ? 1 : (j == 1 ? 43 : (j == 2 ? 43 : 0)));
                        int yShow = gui.guiTop+20+(j == 0 ? 21 : (j == 1 ? 11 : (j == 2 ? 29 : 0)));
                        if(!tooltip){
                            this.renderItem(gui, stack, xShow, yShow);
                        }
                        else{
                            if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                this.renderTooltipAndTransfer(gui, stack, mouseX, mouseY, j == 0, mouseClick);
                            }
                        }
                    }
                }
            }
        }
    }
}
