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

import de.ellpeck.actuallyadditions.api.booklet.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBooklet extends GuiScreen implements IBookletGui{

    public static final int BUTTONS_PER_PAGE = 12;
    public static final ResourceLocation RES_LOC_GUI = AssetUtil.getBookletGuiLocation("guiBooklet");
    public static final ResourceLocation RES_LOC_GADGETS = AssetUtil.getBookletGuiLocation("guiBookletGadgets");

    protected GuiScreen parent;

    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;

    public GuiBooklet(GuiScreen parent){
        this.parent = parent;

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
        if(this.parent != null && keyCode == Keyboard.KEY_ESCAPE){
            this.mc.displayGuiScreen(this.parent);
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
}
