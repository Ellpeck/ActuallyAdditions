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

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
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
    public GuiBookletBase parentPage;

    private GuiButton buttonLeft;
    private GuiButton buttonRight;
    private GuiButton buttonBack;

    public GuiTextField searchField;

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

        if(this.hasPageLeftButton()){
            this.buttonLeft = new TexturedButton(RES_LOC_GADGETS, -2000, this.guiLeft-12, this.guiTop+this.ySize-8, 18, 54, 18, 10);
            this.buttonList.add(this.buttonLeft);
        }

        if(this.hasPageRightButton()){
            this.buttonRight = new TexturedButton(RES_LOC_GADGETS, -2001, this.guiLeft+this.xSize-6, this.guiTop+this.ySize-8, 0, 54, 18, 10);
            this.buttonList.add(this.buttonRight);
        }

        if(this.hasBackButton()){
            this.buttonBack = new TexturedButton(RES_LOC_GADGETS, -2002, this.guiLeft-15, this.guiTop-3, 36, 54, 18, 10);
            this.buttonList.add(this.buttonBack);
        }

        if(this.hasSearchBar()){
            this.searchField = new GuiTextField(-420, this.fontRendererObj, this.guiLeft+this.xSize+2, this.guiTop+this.ySize-40+2, 64, 12);
            this.searchField.setMaxStringLength(50);
            this.searchField.setEnableBackgroundDrawing(false);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        GlStateManager.color(1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(RES_LOC_GUI);
        drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512, 512);

        if(this.hasSearchBar()){
            this.mc.getTextureManager().bindTexture(RES_LOC_GADGETS);
            this.drawTexturedModalRect(this.guiLeft+this.xSize, this.guiTop+this.ySize-40, 188, 0, 68, 14);

            boolean unicodeBefore = this.fontRendererObj.getUnicodeFlag();
            this.fontRendererObj.setUnicodeFlag(true);

            if(!this.searchField.isFocused() && (this.searchField.getText() == null || this.searchField.getText().isEmpty())){
                this.fontRendererObj.drawString(TextFormatting.ITALIC+"Click to search...", this.guiLeft+this.xSize+2, this.guiTop+this.ySize-40+2, 0xFFFFFF, false);
            }

            this.searchField.drawTextBox();

            this.fontRendererObj.setUnicodeFlag(unicodeBefore);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if(this.hasSearchBar()){
            this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void updateScreen(){
        super.updateScreen();

        if(this.hasSearchBar()){
            this.searchField.updateCursorCounter();
        }
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    public boolean hasPageLeftButton(){
        return false;
    }

    public void onPageLeftButtonPressed(){

    }

    public boolean hasPageRightButton(){
        return false;
    }

    public void onPageRightButtonPressed(){

    }

    public boolean hasBackButton(){
        return false;
    }

    public void onBackButtonPressed(){

    }

    public boolean hasSearchBar(){
        return true;
    }

    public void onSearchBarChanged(String searchBarText){
        GuiBookletBase parent = !(this instanceof GuiEntry) ? this : this.parentPage;
        this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, parent, ActuallyAdditionsAPI.allAndSearch, 0, searchBarText, true));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(this.hasPageLeftButton() && button == this.buttonLeft){
            this.onPageLeftButtonPressed();
        }
        else if(this.hasPageRightButton() && button == this.buttonRight){
            this.onPageRightButtonPressed();
        }
        else if(this.hasBackButton() && button == this.buttonBack){
            this.onBackButtonPressed();
        }
        else{
            super.actionPerformed(button);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int key) throws IOException{
        if(key == Keyboard.KEY_ESCAPE || (key == this.mc.gameSettings.keyBindInventory.getKeyCode() && (!this.hasSearchBar() || !this.searchField.isFocused()))){
            this.mc.displayGuiScreen(this.previousScreen);
        }
        else if(this.hasSearchBar() & this.searchField.isFocused()){
            this.searchField.textboxKeyTyped(typedChar, key);
            this.onSearchBarChanged(this.searchField.getText());
        }
        else{
            super.keyTyped(typedChar, key);
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
