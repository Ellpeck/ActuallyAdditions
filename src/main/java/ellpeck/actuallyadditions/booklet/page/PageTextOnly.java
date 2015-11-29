/*
 * This file ("PageTextOnly.java") is part of the Actually Additions Mod for Minecraft.
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
    @SideOnly(Side.CLIENT)
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty()){
            gui.mc.fontRenderer.drawSplitString(text, gui.guiLeft+14, gui.guiTop+9, 115, 0);
        }
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        return this.stack == null ? new ItemStack[0] : new ItemStack[]{this.stack};
    }
}
