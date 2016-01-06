/*
 * This file ("PageReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class PageReconstructor extends BookletPageAA{

    private LensNoneRecipe[] recipes;
    private int recipePos;

    public PageReconstructor(int id, ArrayList<LensNoneRecipe> recipes){
        this(id, recipes.toArray(new LensNoneRecipe[recipes.size()]));
    }

    public PageReconstructor(int id, LensNoneRecipe... recipes){
        super(id);
        this.recipes = recipes;
        this.addToPagesWithItemStackData();
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        if(this.recipes != null){
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            for(LensNoneRecipe recipe : this.recipes){
                if(recipe != null){
                    stacks.addAll(recipe.getOutputs());
                }
            }
            return stacks.toArray(new ItemStack[stacks.size()]);
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(this.recipes[this.recipePos] != null){
            Minecraft.getMinecraft().getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawRect(gui.getGuiLeft()+37, gui.getGuiTop()+20, 188, 154, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        LensNoneRecipe recipe = this.recipes[this.recipePos];
        if(recipe == null){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRenderer, EnumChatFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.getGuiLeft()+14, gui.getGuiTop()+15, 115, 0, false);
        }
        else{
            String strg = "Atomic Reconstructor";
            Minecraft.getMinecraft().fontRenderer.drawString(strg, gui.getGuiLeft()+gui.getXSize()/2-Minecraft.getMinecraft().fontRenderer.getStringWidth(strg)/2, gui.getGuiTop()+10, 0);
        }

        String text = gui.getCurrentEntrySet().page.getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRenderer, text, gui.getGuiLeft()+14, gui.getGuiTop()+100, 115, 0, false);
        }

        if(recipe != null){
            AssetUtil.renderStackToGui(new ItemStack(InitBlocks.blockAtomicReconstructor), gui.getGuiLeft()+37+22, gui.getGuiTop()+20+21, 1.0F);
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 2; x++){
                    List<ItemStack> stacks = x == 0 ? recipe.getInputs() : recipe.getOutputs();
                    if(stacks != null && !stacks.isEmpty()){
                        ItemStack stack = stacks.get(0);

                        if(stack.getItemDamage() == Util.WILDCARD){
                            stack.setItemDamage(0);
                        }
                        boolean tooltip = i == 1;

                        int xShow = gui.getGuiLeft()+37+1+x*42;
                        int yShow = gui.getGuiTop()+20+21;
                        if(!tooltip){
                            AssetUtil.renderStackToGui(stack, xShow, yShow, 1.0F);
                        }
                        else{
                            if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                gui.renderTooltipAndTransferButton(this, stack, mouseX, mouseY, x == 0, mousePressed);
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
        if(ticksElapsed%15 == 0){
            if(this.recipePos+1 >= this.recipes.length){
                this.recipePos = 0;
            }
            else{
                this.recipePos++;
            }
        }
    }
}
