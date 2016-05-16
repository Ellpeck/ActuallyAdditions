/*
 * This file ("PageCoffeeRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageCoffeeRecipe extends BookletPageAA{

    public CoffeeIngredient ingredient;

    public PageCoffeeRecipe(int id, CoffeeIngredient ingredient){
        super(id);
        this.ingredient = ingredient;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        Minecraft.getMinecraft().getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
        gui.drawRect(gui.getGuiLeft()+19, gui.getGuiTop()+20, 146, 94, 99, 60);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        String strg = "Coffee Machine Recipe";
        Minecraft.getMinecraft().fontRendererObj.drawString(strg, gui.getGuiLeft()+gui.getXSize()/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(strg)/2, gui.getGuiTop()+10, 0);

        String text = gui.getCurrentEntrySet().getCurrentPage().getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, text, gui.getGuiLeft()+14, gui.getGuiTop()+100, 115, 0, false);
        }

        if(this.ingredient.maxAmplifier > 0){
            Minecraft.getMinecraft().fontRendererObj.drawString("Maximum Amplifier: "+this.ingredient.maxAmplifier, gui.getGuiLeft()+19+5, gui.getGuiTop()+20+60, 0);
        }

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                ItemStack stack;
                int coordsOffsetX;
                int coordsOffsetY;

                switch(j){
                    case 0:
                        stack = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal());
                        coordsOffsetX = 39;
                        coordsOffsetY = 8;
                        break;
                    case 1:
                        stack = new ItemStack(InitItems.itemCoffeeBean);
                        coordsOffsetX = 5;
                        coordsOffsetY = 8;
                        break;
                    case 2:
                        stack = new ItemStack(InitItems.itemCoffee);
                        ActuallyAdditionsAPI.methodHandler.addEffectToStack(stack, this.ingredient);
                        coordsOffsetX = 39;
                        coordsOffsetY = 39;
                        break;
                    default:
                        stack = this.ingredient.ingredient;
                        coordsOffsetX = 82;
                        coordsOffsetY = 8;
                        break;
                }

                if(stack != null){
                    if(stack.getItemDamage() == Util.WILDCARD){
                        stack.setItemDamage(0);
                    }

                    boolean tooltip = i == 1;

                    int xShow = gui.getGuiLeft()+19+coordsOffsetX;
                    int yShow = gui.getGuiTop()+20+coordsOffsetY;
                    if(!tooltip){
                        AssetUtil.renderStackToGui(stack, xShow, yShow, 1.0F);
                    }
                    else{
                        if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                            gui.renderTooltipAndTransferButton(this, stack, mouseX, mouseY, j != 2, mousePressed);
                        }
                    }
                }
            }
        }
    }
}
