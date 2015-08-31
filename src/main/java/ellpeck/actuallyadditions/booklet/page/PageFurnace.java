/*
 * This file ("PageFurnace.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.Map;

public class PageFurnace extends BookletPage{

    private final ItemStack result;
    private final ItemStack input;

    public PageFurnace(int id, ItemStack input, ItemStack result){
        super(id);
        this.result = result;
        this.input = input;
        InitBooklet.pagesWithItemStackData.add(this);
    }
    public PageFurnace(int id, ItemStack result){
        this(id, null, result);
    }

    @Override
    public ItemStack getItemStackForPage(){
        return this.result;
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){
        if(this.input != null || this.getInputForOutput(this.result) != null){
            gui.mc.getTextureManager().bindTexture(GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 0, 180, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){
        ItemStack input = this.input != null ? this.input : this.getInputForOutput(this.result);
        if(input == null){
            gui.unicodeRenderer.drawSplitString(StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0);
        }

        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty() && !text.contains("booklet.")){
            gui.unicodeRenderer.drawSplitString(text.replace("<n>", "\n"), gui.guiLeft+14, gui.guiTop+100, 115, 0);
        }

        if(input != null){
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 2; x++){
                    ItemStack stack = x == 0 ? input : this.result;
                    if(stack.getItemDamage() == Util.WILDCARD) stack.setItemDamage(0);
                    boolean tooltip = i == 1;

                    int xShow = gui.guiLeft+37+1+x*42;
                    int yShow = gui.guiTop+20+21;
                    if(!tooltip){
                        this.renderItem(gui, stack, xShow, yShow);
                    }
                    else{
                        if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                            this.renderTooltipAndTransfer(gui, stack, mouseX, mouseY, x == 0, mouseClick);
                        }
                    }
                }
            }
        }
    }

    private ItemStack getInputForOutput(ItemStack output){
        for(Object o : FurnaceRecipes.smelting().getSmeltingList().entrySet()){
            ItemStack stack = (ItemStack)((Map.Entry)o).getValue();
            if(stack.isItemEqual(output)) return (ItemStack)((Map.Entry)o).getKey();
        }
        return null;
    }
}
