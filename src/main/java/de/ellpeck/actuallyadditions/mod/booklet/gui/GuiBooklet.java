/*
 * This file ("GuiBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiBooklet extends GuiBookletBase{

    public static final int BUTTONS_PER_PAGE = 12;
    public static final ResourceLocation RES_LOC_GUI = AssetUtil.getBookletGuiLocation("guiBooklet");
    public static final ResourceLocation RES_LOC_GADGETS = AssetUtil.getBookletGuiLocation("guiBookletGadgets");

    public GuiScreen previousScreen;
    protected GuiBookletBase parentPage;

    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;

    public GuiBooklet(GuiScreen previousScreen, GuiBookletBase parentPage){
        this.previousScreen = previousScreen;
        this.parentPage = parentPage;

        this.xSize = 281;
        this.ySize = 180;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.guiLeft = (this.width-this.xSize)/2;
        this.guiTop = (this.height-this.ySize)/2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        GlStateManager.color(1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(RES_LOC_GUI);
        drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512, 512);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        if(this.previousScreen != null && keyCode == Keyboard.KEY_ESCAPE){
            this.mc.displayGuiScreen(this.previousScreen);
        }
        else{
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void renderScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale){
        StringUtil.renderScaledAsciiString(this.fontRendererObj, text, x, y, color, shadow, scale);
    }

    @Override
    public void renderSplitScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale, int length){
        StringUtil.renderSplitScaledAsciiString(this.fontRendererObj, text, x, y, color, shadow, scale, length);
    }

    @Override
    public List<GuiButton> getButtonList(){
        return this.buttonList;
    }

    @Override
    public int getGuiLeft(){
        return this.guiLeft;
    }

    @Override
    public int getGuiTop(){
        return this.guiTop;
    }

    @Override
    public int getSizeX(){
        return this.xSize;
    }

    @Override
    public int getSizeY(){
        return this.ySize;
    }
}
