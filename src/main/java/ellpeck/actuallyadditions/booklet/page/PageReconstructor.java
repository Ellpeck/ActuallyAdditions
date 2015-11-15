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

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.proxy.ClientProxy;
import ellpeck.actuallyadditions.recipe.AtomicReconstructorRecipeHandler;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class PageReconstructor extends BookletPage{

    private final ItemStack result;
    private final ItemStack input;

    public PageReconstructor(int id, ItemStack result){
        this(id, null, result);
    }

    public PageReconstructor(int id, ItemStack input, ItemStack result){
        super(id);
        this.result = result;
        this.input = input;
        this.addToPagesWithItemStackData();
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(this.input != null || this.getInputForOutput(this.result) != null){
            gui.mc.getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 188, 154, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        ItemStack input = this.input != null ? this.input : this.getInputForOutput(this.result);
        if(input == null){
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

        if(input != null){
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 2; x++){
                    ItemStack stack = x == 0 ? input : this.result;
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
            renderItem(gui, new ItemStack(InitBlocks.blockAtomicReconstructor), gui.guiLeft+37+22, gui.guiTop+20+21, 1.0F);
        }
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.result == null ? new ItemStack[0] : new ItemStack[]{this.result};
    }

    private ItemStack getInputForOutput(ItemStack output){
        for(AtomicReconstructorRecipeHandler.Recipe recipe : AtomicReconstructorRecipeHandler.recipes){
            ArrayList<ItemStack> stacks = OreDictionary.getOres(recipe.output);
            for(ItemStack stack : stacks){
                if(output.isItemEqual(stack)){
                    ArrayList<ItemStack> outputs = OreDictionary.getOres(recipe.input);
                    if(outputs != null && !outputs.isEmpty()){
                        return outputs.get(0);
                    }
                }
            }
        }
        return null;
    }
}
