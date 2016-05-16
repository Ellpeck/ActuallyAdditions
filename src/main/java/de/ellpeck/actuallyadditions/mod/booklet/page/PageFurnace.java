/*
 * This file ("PageFurnace.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class PageFurnace extends BookletPageAA{

    private final ItemStack result;
    private final ItemStack input;

    public PageFurnace(int id, ItemStack result){
        this(id, null, result);
    }

    public PageFurnace(int id, ItemStack input, ItemStack result){
        super(id);
        this.result = result;
        this.input = input;
        this.addToPagesWithItemStackData();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(this.input != null || this.getInputForOutput(this.result) != null){
            Minecraft.getMinecraft().getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawRect(gui.getGuiLeft()+37, gui.getGuiTop()+20, 0, 180, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        ItemStack input = this.input != null ? this.input : this.getInputForOutput(this.result);
        if(input == null){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, TextFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID+".recipeDisabled"), gui.getGuiLeft()+14, gui.getGuiTop()+15, 115, 0, false);
        }
        else{
            String strg = "Furnace Recipe";
            Minecraft.getMinecraft().fontRendererObj.drawString(strg, gui.getGuiLeft()+gui.getXSize()/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(strg)/2, gui.getGuiTop()+10, 0);
        }

        String text = gui.getCurrentEntrySet().getCurrentPage().getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, text, gui.getGuiLeft()+14, gui.getGuiTop()+100, 115, 0, false);
        }

        if(input != null){
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 2; x++){
                    ItemStack stack = x == 0 ? input : this.result;
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

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.result == null ? new ItemStack[0] : new ItemStack[]{this.result};
    }

    private ItemStack getInputForOutput(ItemStack output){
        for(Map.Entry o : FurnaceRecipes.instance().getSmeltingList().entrySet()){
            ItemStack stack = (ItemStack)(o).getValue();
            if(stack.isItemEqual(output)){
                return (ItemStack)(o).getKey();
            }
        }
        return null;
    }
}
