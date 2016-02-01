/*
 * This file ("PageCrusherRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public class PageCrusherRecipe extends BookletPageAA{

    public CrusherRecipe recipe;

    private int recipePos;

    public PageCrusherRecipe(int id, CrusherRecipe recipe){
        super(id);
        this.recipe = recipe;
        this.addToPagesWithItemStackData();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(recipe != null){
            Minecraft.getMinecraft().getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawRect(gui.getGuiLeft()+37, gui.getGuiTop()+20, 60, 180, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(recipe == null){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, EnumChatFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.getGuiLeft()+14, gui.getGuiTop()+15, 115, 0, false);
        }
        else{
            String strg = "Crusher Recipe";
            Minecraft.getMinecraft().fontRendererObj.drawString(strg, gui.getGuiLeft()+gui.getXSize()/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(strg)/2, gui.getGuiTop()+10, 0);
        }

        String text = gui.getCurrentEntrySet().page.getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, text, gui.getGuiLeft()+14, gui.getGuiTop()+100, 115, 0, false);
        }

        if(recipe != null){
            if(recipe.outputTwoChance > 0){
                Minecraft.getMinecraft().fontRendererObj.drawString(recipe.outputTwoChance+"%", gui.getGuiLeft()+37+62, gui.getGuiTop()+20+33, 0);
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

                            int xShow = gui.getGuiLeft()+37+(j == 0 ? 1 : (j == 1 ? 43 : (j == 2 ? 43 : 0)));
                            int yShow = gui.getGuiTop()+20+(j == 0 ? 21 : (j == 1 ? 11 : (j == 2 ? 29 : 0)));
                            if(!tooltip){
                                AssetUtil.renderStackToGui(stack, xShow, yShow, 1.0F);
                            }
                            else{
                                if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                    gui.renderTooltipAndTransferButton(this, stack, mouseX, mouseY, j == 0, mousePressed);
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

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.recipe == null ? new ItemStack[0] : this.recipe.getRecipeOutputOnes().toArray(new ItemStack[this.recipe.getRecipeOutputOnes().size()]);
    }
}
