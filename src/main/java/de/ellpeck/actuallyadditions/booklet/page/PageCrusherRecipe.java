/*
 * This file ("PageCrusherRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;


public class PageCrusherRecipe extends BookletPage{

    public CrusherRecipeRegistry.CrusherRecipe recipe;

    private int recipePos;

    public PageCrusherRecipe(int id, CrusherRecipeRegistry.CrusherRecipe recipe){
        super(id);
        this.recipe = recipe;
        this.addToPagesWithItemStackData();
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.recipe == null ? new ItemStack[0] : this.recipe.getRecipeOutputOnes().toArray(new ItemStack[this.recipe.getRecipeOutputOnes().size()]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(recipe != null){
            gui.mc.getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 60, 180, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(recipe == null){
            StringUtil.drawSplitString(gui.mc.fontRenderer, EnumChatFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0, false);
        }
        else{
            String strg = "Crusher Recipe";
            gui.mc.fontRenderer.drawString(strg, gui.guiLeft+gui.xSize/2-gui.mc.fontRenderer.getStringWidth(strg)/2, gui.guiTop+10, 0);
        }

        String text = gui.currentEntrySet.page.getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(gui.mc.fontRenderer, text, gui.guiLeft+14, gui.guiTop+100, 115, 0, false);
        }

        if(recipe != null){
            if(recipe.outputTwoChance > 0){
                gui.mc.fontRenderer.drawString(recipe.outputTwoChance+"%", gui.guiLeft+37+62, gui.guiTop+20+33, 0);
            }

            if(recipe.getRecipeOutputOnes() != null){
                for(int i = 0; i < 2; i++){
                    for(int j = 0; j < 3; j++){
                        ItemStack stack;
                        switch(j){
                            case 0:
                                stack = this.recipe.getRecipeInputs().get(Math.min(this.recipe.getRecipeInputs().size()-1, this.recipePos));
                                break;
                            case 1:
                                stack = this.recipe.getRecipeOutputOnes().get(Math.min(this.recipe.getRecipeOutputOnes().size()-1, this.recipePos));
                                break;
                            default:
                                List<ItemStack> outputTwos = this.recipe.getRecipeOutputTwos();
                                stack = outputTwos == null ? null : outputTwos.get(Math.min(outputTwos.size()-1, this.recipePos));
                                break;
                        }

                        if(stack != null){
                            if(stack.getItemDamage() == Util.WILDCARD){
                                stack.setItemDamage(0);
                            }

                            boolean tooltip = i == 1;

                            int xShow = gui.guiLeft+37+(j == 0 ? 1 : (j == 1 ? 43 : (j == 2 ? 43 : 0)));
                            int yShow = gui.guiTop+20+(j == 0 ? 21 : (j == 1 ? 11 : (j == 2 ? 29 : 0)));
                            if(!tooltip){
                                AssetUtil.renderStackToGui(stack, xShow, yShow, 1.0F);
                            }
                            else{
                                if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                    this.renderTooltipAndTransfer(gui, stack, mouseX, mouseY, j == 0, mousePressed);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateScreen(int ticksElapsed){
        if(ticksElapsed%10 == 0){
            List<ItemStack> outputTwos = this.recipe.getRecipeOutputTwos();
            if(this.recipePos+1 >= Math.max(this.recipe.getRecipeInputs().size(), Math.max(this.recipe.getRecipeOutputOnes().size(), outputTwos == null ? 0 : outputTwos.size()))){
                this.recipePos = 0;
            }
            else{
                this.recipePos++;
            }
        }
    }
}
