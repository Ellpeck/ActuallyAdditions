/*
 * This file ("BookmarkButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.button;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiPage;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class BookmarkButton extends GuiButton{

    private final GuiBooklet booklet;
    public IBookletPage assignedPage;

    public BookmarkButton(int id, int x, int y, GuiBooklet booklet){
        super(id, x, y, 16, 16, "");
        this.booklet = booklet;
    }

    public void onPressed(){
        if(this.assignedPage != null){
            if(GuiScreen.isShiftKeyDown()){
                this.assignedPage = null;
            }
            else if(!(this.booklet instanceof GuiPage) || ((GuiPage)this.booklet).pages[0] != this.assignedPage){
                GuiPage gui = BookletUtils.createPageGui(this.booklet.previousScreen, this.booklet, this.assignedPage);
                Minecraft.getMinecraft().displayGuiScreen(gui);
            }
        }
        else{
            if(this.booklet instanceof GuiPage){
                this.assignedPage = ((GuiPage)this.booklet).pages[0];
            }
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y){
        if(this.visible){
            minecraft.getTextureManager().bindTexture(GuiBooklet.RES_LOC_GADGETS);
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
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 224+(this.assignedPage == null ? 0 : 16), 14-renderHeight+k*renderHeight, this.width, renderHeight);
            this.mouseDragged(minecraft, x, y);

            if(this.assignedPage != null){
                ItemStack display = this.assignedPage.getChapter().getDisplayItemStack();
                if(StackUtil.isValid(display)){
                    GlStateManager.pushMatrix();
                    AssetUtil.renderStackToGui(display, this.xPosition+2, this.yPosition+1, 0.725F);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public void drawHover(int mouseX, int mouseY){
        if(this.isMouseOver()){
            List<String> list = new ArrayList<String>();

            if(this.assignedPage != null){
                IBookletChapter chapter = this.assignedPage.getChapter();

                list.add(TextFormatting.GOLD+chapter.getLocalizedName()+", Page "+(chapter.getPageIndex(this.assignedPage)+1));
                list.add(StringUtil.localize("booklet."+ModUtil.MOD_ID+".bookmarkButton.bookmark.openDesc"));
                list.add(TextFormatting.ITALIC+StringUtil.localize("booklet."+ModUtil.MOD_ID+".bookmarkButton.bookmark.removeDesc"));
            }
            else{
                list.add(TextFormatting.GOLD+StringUtil.localize("booklet."+ModUtil.MOD_ID+".bookmarkButton.noBookmark.name"));

                if(this.booklet instanceof GuiPage){
                    list.add(StringUtil.localize("booklet."+ModUtil.MOD_ID+".bookmarkButton.noBookmark.pageDesc"));
                }
                else{
                    list.add(StringUtil.localize("booklet."+ModUtil.MOD_ID+".bookmarkButton.noBookmark.notPageDesc"));
                }
            }

            Minecraft mc = Minecraft.getMinecraft();
            GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }
}
