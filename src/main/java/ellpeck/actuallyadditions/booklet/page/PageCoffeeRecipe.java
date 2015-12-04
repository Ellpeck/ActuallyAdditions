/*
 * This file ("PageCoffeeRecipe.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemCoffee;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.proxy.ClientProxy;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;

public class PageCoffeeRecipe extends BookletPage{

    public ItemCoffee.Ingredient ingredient;

    public PageCoffeeRecipe(int id, ItemCoffee.Ingredient ingredient){
        super(id);
        this.ingredient = ingredient;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        gui.mc.getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
        gui.drawTexturedModalRect(gui.guiLeft+19, gui.guiTop+20, 146, 94, 99, 60);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        String strg = "Coffee Machine Recipe";
        gui.mc.fontRenderer.drawString(strg, gui.guiLeft+gui.xSize/2-gui.mc.fontRenderer.getStringWidth(strg)/2, gui.guiTop+10, 0);

        String text = gui.currentEntrySet.page.getText();
        if(text != null && !text.isEmpty()){
            gui.mc.fontRenderer.drawSplitString(text, gui.guiLeft+14, gui.guiTop+100, 115, 0);
        }

        if(this.ingredient.maxAmplifier > 0){
            gui.mc.fontRenderer.drawString("Maximum Amplifier: "+this.ingredient.maxAmplifier, gui.guiLeft+19+5, gui.guiTop+20+60, 0);
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
                        ItemCoffee.addEffectToStack(stack, this.ingredient);
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

                    int xShow = gui.guiLeft+19+coordsOffsetX;
                    int yShow = gui.guiTop+20+coordsOffsetY;
                    if(!tooltip){
                        renderItem(gui, stack, xShow, yShow, 1.0F);
                    }
                    else{
                        if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                            this.renderTooltipAndTransfer(gui, stack, mouseX, mouseY, j != 2, mousePressed);
                        }
                    }
                }
            }
        }
    }
}
