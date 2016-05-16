/*
 * This file ("PageTextOnly.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageTextOnly extends BookletPageAA{

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
    @SideOnly(Side.CLIENT)
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        String text = gui.getCurrentEntrySet().getCurrentPage().getText();
        if(text != null && !text.isEmpty()){
            StringUtil.drawSplitString(Minecraft.getMinecraft().fontRendererObj, text, gui.getGuiLeft()+14, gui.getGuiTop()+9, 115, 0, false);
        }
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.stack == null ? new ItemStack[0] : new ItemStack[]{this.stack};
    }
}
