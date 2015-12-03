/*
 * This file ("PageReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.items.lens.LensNoneRecipeHandler;
import ellpeck.actuallyadditions.proxy.ClientProxy;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class PageReconstructor extends BookletPage{

    private LensNoneRecipeHandler.Recipe[] recipes;
    private int recipePos;

    public PageReconstructor(int id, ArrayList<LensNoneRecipeHandler.Recipe> recipes){
        this(id, recipes.toArray(new LensNoneRecipeHandler.Recipe[recipes.size()]));
    }

    public PageReconstructor(int id, LensNoneRecipeHandler.Recipe... recipes){
        super(id);
        this.recipes = recipes;
        this.addToPagesWithItemStackData();
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        if(this.recipes != null){
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            for(LensNoneRecipeHandler.Recipe recipe : this.recipes){
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
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(this.recipes[this.recipePos] != null){
            gui.mc.getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 188, 154, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        LensNoneRecipeHandler.Recipe recipe = this.recipes[this.recipePos];
        if(recipe == null){
            gui.mc.fontRenderer.drawSplitString(EnumChatFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0);
        }
        else{
            String strg = "Atomic Reconstructor";
            gui.mc.fontRenderer.drawString(strg, gui.guiLeft+gui.xSize/2-gui.mc.fontRenderer.getStringWidth(strg)/2, gui.guiTop+10, 0);
        }

        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty()){
            gui.mc.fontRenderer.drawSplitString(text, gui.guiLeft+14, gui.guiTop+100, 115, 0);
        }

        if(recipe != null){
            renderItem(gui, new ItemStack(InitBlocks.blockAtomicReconstructor), gui.guiLeft+37+22, gui.guiTop+20+21, 1.0F);
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 2; x++){
                    List<ItemStack> stacks = x == 0 ? recipe.getInputs() : recipe.getOutputs();
                    if(stacks != null && !stacks.isEmpty()){
                        ItemStack stack = stacks.get(0);

                        if(stack.getItemDamage() == Util.WILDCARD){
                            stack.setItemDamage(0);
                        }
                        boolean tooltip = i == 1;

                        int xShow = gui.guiLeft+37+1+x*42;
                        int yShow = gui.guiTop+20+21;
                        if(!tooltip){
                            renderItem(gui, stack, xShow, yShow, 1.0F);
                        }
                        else{
                            if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                this.renderTooltipAndTransfer(gui, stack, mouseX, mouseY, x == 0, mousePressed);
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
