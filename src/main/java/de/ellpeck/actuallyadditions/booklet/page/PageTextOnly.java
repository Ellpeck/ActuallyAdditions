/*
 * This file ("PageTextOnly.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.item.ItemStack;

public class PageTextOnly extends BookletPage{

    private ItemStack stack;

    public PageTextOnly(int id){
        super(id);
    }

    public PageTextOnly setStack(ItemStack stack){
        this.stack = stack;
        this.addToPagesWithItemStackData();
        return this;
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.stack == null ? new ItemStack[0] : new ItemStack[]{this.stack};
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        String text = gui.currentEntrySet.page.getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(gui.mc.fontRenderer, text, gui.guiLeft+14, gui.guiTop+9, 115, 0, false);
        }
    }
}
