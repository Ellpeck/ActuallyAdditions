/*
 * This file ("BookmarkButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.button;

import ellpeck.actuallyadditions.booklet.BookletUtils;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.KeyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class BookmarkButton extends GuiButton{

    public BookletChapter assignedChapter;
    public BookletPage assignedPage;
    public BookletEntry assignedEntry;
    public int assignedPageInIndex;

    private GuiBooklet booklet;

    public BookmarkButton(int id, int x, int y, GuiBooklet booklet){
        super(id, x, y, 16, 16, "");
        this.booklet = booklet;
    }

    public void onPressed(){
        if(this.assignedEntry != null){
            if(KeyUtil.isShiftPressed()){
                this.assignedEntry = null;
                this.assignedChapter = null;
                this.assignedPage = null;
                this.assignedPageInIndex = 1;
            }
            else{
                BookletUtils.openIndexEntry(this.booklet, this.assignedEntry, this.assignedPageInIndex, true);
                BookletUtils.openChapter(this.booklet, this.assignedChapter, this.assignedPage);
            }
        }
        else{
            if(this.booklet.currentIndexEntry != null){
                this.assignedEntry = this.booklet.currentIndexEntry;
                this.assignedChapter = this.booklet.currentChapter;
                this.assignedPage = this.booklet.currentPage;
                this.assignedPageInIndex = this.booklet.pageOpenInIndex;
            }
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y){
        if(this.visible){
            minecraft.getTextureManager().bindTexture(GuiBooklet.resLoc);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
            int k = this.getHoverState(this.field_146123_n);
            if(k == 0){
                k = 1;
            }

            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            int renderHeight = 25;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 146+(this.assignedEntry == null ? 0 : 16), 194-renderHeight+k*renderHeight, this.width, renderHeight);
            this.mouseDragged(minecraft, x, y);

            if(this.assignedEntry != null){
                GL11.glPushMatrix();
                BookletPage.renderItem(booklet, this.assignedChapter != null && this.assignedChapter.displayStack != null ? this.assignedChapter.displayStack : new ItemStack(InitItems.itemBooklet), this.xPosition+2, this.yPosition+1, 0.725F);
                GL11.glPopMatrix();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void drawHover(int mouseX, int mouseY){
        ArrayList list = new ArrayList();
        if(this.assignedEntry != null){
            if(this.assignedChapter != null){
                list.add(EnumChatFormatting.GOLD+this.assignedChapter.getLocalizedName()+", Page "+this.assignedPage.getID());
            }
            else{
                list.add(EnumChatFormatting.GOLD+this.assignedEntry.getLocalizedName()+", Page "+this.assignedPageInIndex);
            }
            list.add("Click to open");
            list.add(EnumChatFormatting.ITALIC+"Shift-Click to remove");
        }
        else{
            list.add(EnumChatFormatting.GOLD+"None");
            list.add("Click to save current page");
        }
        this.booklet.drawHoveringText(list, mouseX, mouseY);
    }
}
