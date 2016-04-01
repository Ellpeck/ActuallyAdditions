/*
 * This file ("BookmarkButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.button;

import de.ellpeck.actuallyadditions.api.internal.EntrySet;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class BookmarkButton extends GuiButton{

    public EntrySet assignedEntry = new EntrySet(null);

    private GuiBooklet booklet;

    public BookmarkButton(int id, int x, int y, GuiBooklet booklet){
        super(id, x, y, 16, 16, "");
        this.booklet = booklet;
    }

    public void onPressed(){
        if(this.assignedEntry.entry != null){
            if(GuiScreen.isShiftKeyDown()){
                this.assignedEntry.removeEntry();
            }
            else{
                BookletUtils.openIndexEntry(this.booklet, this.assignedEntry.entry, this.assignedEntry.pageInIndex, true);
                BookletUtils.openChapter(this.booklet, this.assignedEntry.chapter, this.assignedEntry.page);
            }
        }
        else{
            if(this.booklet.currentEntrySet.entry != null){
                this.assignedEntry.setEntry(this.booklet.currentEntrySet.page, this.booklet.currentEntrySet.chapter, this.booklet.currentEntrySet.entry, this.booklet.currentEntrySet.pageInIndex);
            }
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y){
        if(this.visible){
            minecraft.getTextureManager().bindTexture(GuiBooklet.resLoc);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
            int k = this.getHoverState(this.hovered);
            if(k == 0){
                k = 1;
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            int renderHeight = 25;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 146+(this.assignedEntry.entry == null ? 0 : 16), 194-renderHeight+k*renderHeight, this.width, renderHeight);
            this.mouseDragged(minecraft, x, y);

            if(this.assignedEntry.entry != null){
                GlStateManager.pushMatrix();
                AssetUtil.renderStackToGui(this.assignedEntry.chapter != null && this.assignedEntry.chapter.getDisplayItemStack() != null ? this.assignedEntry.chapter.getDisplayItemStack() : new ItemStack(InitItems.itemBooklet), this.xPosition+2, this.yPosition+1, 0.725F);
                GlStateManager.popMatrix();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void drawHover(int mouseX, int mouseY){
        ArrayList list = new ArrayList();
        if(this.assignedEntry.entry != null){
            if(this.assignedEntry.chapter != null){
                list.add(TextFormatting.GOLD+this.assignedEntry.chapter.getLocalizedName()+", Page "+this.assignedEntry.page.getID());
            }
            else{
                list.add(TextFormatting.GOLD+this.assignedEntry.entry.getLocalizedName()+", Page "+this.assignedEntry.pageInIndex);
            }
            list.add("Click to open");
            list.add(TextFormatting.ITALIC+"Shift-Click to remove");
        }
        else{
            list.add(TextFormatting.GOLD+"None");
            list.add("Click to save current page");
        }
        this.booklet.drawHoveringText(list, mouseX, mouseY);
    }
}
