/*
 * This file ("GuiPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.booklet.page.ItemDisplay;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiPage extends GuiBooklet{

    private final List<ItemDisplay> itemDisplays = new ArrayList<ItemDisplay>();
    public final IBookletPage[] pages = new IBookletPage[2];

    public GuiPage(GuiScreen previousScreen, GuiBookletBase parentPage, IBookletPage page1, IBookletPage page2){
        super(previousScreen, parentPage);

        this.pages[0] = page1;
        this.pages[1] = page2;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for(ItemDisplay display : this.itemDisplays){
            display.onMousePress(mouseButton, mouseX, mouseY);
        }

        for(IBookletPage page : this.pages){
            if(page != null){
                page.mouseClicked(this, mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state){
        super.mouseReleased(mouseX, mouseY, state);

        for(IBookletPage page : this.pages){
            if(page != null){
                page.mouseReleased(this, mouseX, mouseY, state);
            }
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        for(IBookletPage page : this.pages){
            if(page != null){
                page.mouseClickMove(this, mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException{
        super.actionPerformed(button);

        for(IBookletPage page : this.pages){
            if(page != null){
                page.actionPerformed(this, button);
            }
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        this.itemDisplays.clear();

        for(int i = 0; i < this.pages.length; i++){
            IBookletPage page = this.pages[i];
            if(page != null){
                page.initGui(this, this.guiLeft+6+i*142, this.guiTop+7);
            }
        }
    }

    @Override
    public void updateScreen(){
        super.updateScreen();

        for(int i = 0; i < this.pages.length; i++){
            IBookletPage page = this.pages[i];
            if(page != null){
                page.updateScreen(this, this.guiLeft+6+i*142, this.guiTop+7);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);

        for(int i = 0; i < this.pages.length; i++){
            IBookletPage page = this.pages[i];
            if(page != null){
                GlStateManager.color(1F, 1F, 1F);
                page.drawScreenPre(this, this.guiLeft+6+i*142, this.guiTop+7, mouseX, mouseY, partialTicks);
            }
        }
        for(ItemDisplay display : this.itemDisplays){
            display.drawPre();
        }

        for(int i = 0; i < this.pages.length; i++){
            IBookletPage page = this.pages[i];
            if(page != null){
                GlStateManager.color(1F, 1F, 1F);
                page.drawScreenPost(this, this.guiLeft+6+i*142, this.guiTop+7, mouseX, mouseY, partialTicks);
            }
        }
        for(ItemDisplay display : this.itemDisplays){
            display.drawPost(mouseX, mouseY);
        }
    }

    @Override
    public void addItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer){
        this.itemDisplays.add(new ItemDisplay(this, x, y, scale, renderedStack, shouldTryTransfer));
    }
}
