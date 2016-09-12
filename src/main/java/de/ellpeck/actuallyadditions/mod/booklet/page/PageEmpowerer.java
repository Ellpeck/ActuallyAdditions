/*
 * This file ("PageEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class PageEmpowerer extends BookletPageAA{

    private final EmpowererRecipe[] recipes;
    private int recipePos;

    public PageEmpowerer(int id, ArrayList<EmpowererRecipe> recipes){
        this(id, recipes.toArray(new EmpowererRecipe[recipes.size()]));
    }

    public PageEmpowerer(int id, EmpowererRecipe... recipes){
        super(id);
        this.recipes = recipes;
        this.addToPagesWithItemStackData();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(this.recipes[this.recipePos] != null){
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiBooklet.RES_LOC_ADDON);
            gui.drawRect(gui.getGuiLeft()+22, gui.getGuiTop()+20, 0, 0, 94, 58);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        EmpowererRecipe recipe = this.recipes[this.recipePos];
        if(recipe == null){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, TextFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID+".recipeDisabled"), gui.getGuiLeft()+14, gui.getGuiTop()+15, 115, 0, false);
        }
        else{
            String strg = "Empowerer";
            Minecraft.getMinecraft().fontRendererObj.drawString(strg, gui.getGuiLeft()+gui.getXSize()/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(strg)/2, gui.getGuiTop()+10, 0);
        }

        String text = gui.getCurrentEntrySet().getCurrentPage().getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, text, gui.getGuiLeft()+14, gui.getGuiTop()+100, 115, 0, false);
        }

        if(recipe != null){
            for(int i = 0; i < 2; i++){
                for(int count = 0; count < 6; count++){
                    ItemStack stack;
                    int x;
                    int y;
                    switch(count){
                        case 0:
                            stack = recipe.input;
                            x = 21;
                            y = 21;
                            break;
                        case 1:
                            stack = recipe.modifier1;
                            x = 21;
                            y = 1;
                            break;
                        case 2:
                            stack = recipe.modifier2;
                            x = 41;
                            y = 21;
                            break;
                        case 3:
                            stack = recipe.modifier3;
                            x = 21;
                            y = 41;
                            break;
                        case 4:
                            stack = recipe.modifier4;
                            x = 1;
                            y = 21;
                            break;
                        default:
                            stack = recipe.output;
                            x = 77;
                            y = 21;
                            break;
                    }

                    if(stack.getItemDamage() == Util.WILDCARD){
                        stack.setItemDamage(0);
                    }

                    int xShow = gui.getGuiLeft()+22+x;
                    int yShow = gui.getGuiTop()+20+y;
                    if(i != 1){
                        AssetUtil.renderStackToGui(stack, xShow, yShow, 1.0F);
                    }
                    else{
                        if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                            gui.renderTooltipAndTransferButton(this, stack, mouseX, mouseY, x != 5, mousePressed);
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateScreen(int ticksElapsed){
        if(ticksElapsed%15 == 0){
            if(this.recipePos+1 >= this.recipes.length){
                this.recipePos = 0;
            }
            else{
                this.recipePos++;
            }
        }
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        if(this.recipes != null){
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            for(EmpowererRecipe recipe : this.recipes){
                if(recipe != null){
                    stacks.add(recipe.output);
                }
            }
            return stacks.toArray(new ItemStack[stacks.size()]);
        }
        return null;
    }
}
