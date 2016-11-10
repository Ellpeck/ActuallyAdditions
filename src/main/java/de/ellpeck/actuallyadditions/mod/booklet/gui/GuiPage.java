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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiPage extends GuiBooklet{

    private final IBookletPage page;

    public GuiPage(GuiScreen previousScreen, GuiBookletBase parentPage, IBookletPage page){
        super(previousScreen, parentPage);
        this.page = page;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        this.page.mouseClicked(this, mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state){
        this.page.mouseReleased(this, mouseX, mouseY, state);
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        this.page.mouseClickMove(this, mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException{
        this.page.actionPerformed(this, button);
    }

    @Override
    public void initGui(){
        this.page.initGui(this);
    }

    @Override
    public void updateScreen(){
        this.page.updateScreen(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.page.drawScreen(this, mouseX, mouseY, partialTicks);
    }
}
