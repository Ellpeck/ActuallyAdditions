/*
 * This file ("GuiBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.booklet.button.BookmarkButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.TrialsButton;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiBooklet extends GuiBookletBase{

    public static final int BUTTONS_PER_PAGE = 12;
    public static final ResourceLocation RES_LOC_GUI = AssetUtil.getBookletGuiLocation("gui_booklet");
    public static final ResourceLocation RES_LOC_GADGETS = AssetUtil.getBookletGuiLocation("gui_booklet_gadgets");
    protected final BookmarkButton[] bookmarkButtons = new BookmarkButton[12];
    public GuiScreen previousScreen;
    public GuiBookletBase parentPage;
    public GuiTextField searchField;
    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;
    private GuiButton buttonBack;

    private GuiButton buttonTrials;

    private float smallFontSize;
    private float mediumFontSize;
    private float largeFontSize;

    public GuiBooklet(GuiScreen previousScreen, GuiBookletBase parentPage){
        this.previousScreen = previousScreen;
        this.parentPage = parentPage;

        this.xSize = 281;
        this.ySize = 180;
    }

    private static float getFontSize(String lang, ConfigIntValues config, float defaultValue){
        int conf = config.getValue();
        if(conf <= 0){
            try{
                return Float.parseFloat(StringUtil.localize("booklet."+ModUtil.MOD_ID+".fontSize."+lang));
            }
            catch(Exception e){
                return defaultValue;
            }
        }
        else{
            return (float)conf/100F;
        }
    }

    @Override
    public void initGui(){
        super.initGui();

        this.guiLeft = (this.width-this.xSize)/2;
        this.guiTop = (this.height-this.ySize)/2;

        this.smallFontSize = getFontSize("small", ConfigIntValues.FONT_SIZE_SMALL, 0.5F);
        this.mediumFontSize = getFontSize("medium", ConfigIntValues.FONT_SIZE_MEDIUM, 0.75F);
        this.largeFontSize = getFontSize("large", ConfigIntValues.FONT_SIZE_LARGE, 0.8F);

        if(this.hasPageLeftButton()){
            List<String> hoverText = Arrays.asList(TextFormatting.GOLD+"Previous Page", TextFormatting.ITALIC+"Or scroll up");
            this.buttonLeft = new TexturedButton(RES_LOC_GADGETS, -2000, this.guiLeft-12, this.guiTop+this.ySize-8, 18, 54, 18, 10, hoverText);
            this.buttonList.add(this.buttonLeft);
        }

        if(this.hasPageRightButton()){
            List<String> hoverText = Arrays.asList(TextFormatting.GOLD+"Next Page", TextFormatting.ITALIC+"Or scroll down");
            this.buttonRight = new TexturedButton(RES_LOC_GADGETS, -2001, this.guiLeft+this.xSize-6, this.guiTop+this.ySize-8, 0, 54, 18, 10, hoverText);
            this.buttonList.add(this.buttonRight);
        }

        if(this.hasBackButton()){
            List<String> hoverText = Arrays.asList(TextFormatting.GOLD+"Go Back", TextFormatting.ITALIC+"Or right-click", TextFormatting.ITALIC.toString()+TextFormatting.GRAY+"Hold Shift for Main Page");
            this.buttonBack = new TexturedButton(RES_LOC_GADGETS, -2002, this.guiLeft-15, this.guiTop-3, 36, 54, 18, 10, hoverText);
            this.buttonList.add(this.buttonBack);
        }

        if(this.hasSearchBar()){
            this.searchField = new GuiTextField(-420, this.fontRendererObj, this.guiLeft+this.xSize+2, this.guiTop+this.ySize-40+2, 64, 12);
            this.searchField.setMaxStringLength(50);
            this.searchField.setEnableBackgroundDrawing(false);
        }

        if(this.hasBookmarkButtons()){
            PlayerSave data = PlayerData.getDataFromPlayer(this.mc.player);

            int xStart = this.guiLeft+this.xSize/2-16*this.bookmarkButtons.length/2;
            for(int i = 0; i < this.bookmarkButtons.length; i++){
                this.bookmarkButtons[i] = new BookmarkButton(1337+i, xStart+i*16, this.guiTop+this.ySize, this);
                this.buttonList.add(this.bookmarkButtons[i]);

                if(data.bookmarks[i] != null){
                    this.bookmarkButtons[i].assignedPage = data.bookmarks[i];
                }
            }
        }

        this.buttonTrials = new TrialsButton(this);
        this.buttonList.add(this.buttonTrials);
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();

        //Don't cache the parent GUI, otherwise it opens again when you close the cached book!
        this.previousScreen = null;

        PlayerSave data = PlayerData.getDataFromPlayer(this.mc.player);
        data.lastOpenBooklet = this;

        boolean change = false;
        for(int i = 0; i < this.bookmarkButtons.length; i++){
            if(data.bookmarks[i] != this.bookmarkButtons[i].assignedPage){
                data.bookmarks[i] = this.bookmarkButtons[i].assignedPage;
                change = true;
            }
        }

        if(change){
            PacketHandlerHelper.sendPlayerDataToServer(true, 0);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawScreenPre(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawScreenPost(mouseX, mouseY, partialTicks);
    }

    public void drawScreenPre(int mouseX, int mouseY, float partialTicks){
        GlStateManager.color(1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(RES_LOC_GUI);
        drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512, 512);

        if(this.hasSearchBar()){
            this.mc.getTextureManager().bindTexture(RES_LOC_GADGETS);
            this.drawTexturedModalRect(this.guiLeft+this.xSize, this.guiTop+this.ySize-40, 188, 0, 68, 14);

            boolean unicodeBefore = this.fontRendererObj.getUnicodeFlag();
            this.fontRendererObj.setUnicodeFlag(true);

            if(!this.searchField.isFocused() && (this.searchField.getText() == null || this.searchField.getText().isEmpty())){
                this.fontRendererObj.drawString(TextFormatting.ITALIC+StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.searchField"), this.guiLeft+this.xSize+2, this.guiTop+this.ySize-40+2, 0xFFFFFF, false);
            }

            this.searchField.drawTextBox();

            this.fontRendererObj.setUnicodeFlag(unicodeBefore);
        }
    }

    public void drawScreenPost(int mouseX, int mouseY, float partialTicks){
        for(GuiButton button : this.buttonList){
            if(button instanceof BookmarkButton){
                ((BookmarkButton)button).drawHover(mouseX, mouseY);
            }
            else if(button instanceof TexturedButton){
                ((TexturedButton)button).drawHover(mouseX, mouseY);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if(this.hasSearchBar()){
            this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if(mouseButton == 1 && this.hasBackButton()){
            this.onBackButtonPressed();
        }
    }

    @Override
    public void handleMouseInput() throws IOException{
        int wheel = Mouse.getEventDWheel();
        if(wheel != 0){
            if(wheel < 0){
                if(this.hasPageRightButton()){
                    this.onPageRightButtonPressed();
                }
            }
            else if(wheel > 0){
                if(this.hasPageLeftButton()){
                    this.onPageLeftButtonPressed();
                }
            }
        }
        super.handleMouseInput();
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

    public boolean areTrialsOpened(){
        return false;
    }

    public boolean hasBackButton(){
        return false;
    }

    public void onBackButtonPressed(){
        this.mc.displayGuiScreen(new GuiMainPage(this.previousScreen));
    }

    public boolean hasSearchBar(){
        return true;
    }

    public boolean hasBookmarkButtons(){
        return true;
    }

    @Override
    public float getSmallFontSize(){
        return this.smallFontSize;
    }

    @Override
    public float getMediumFontSize(){
        return this.mediumFontSize;
    }

    @Override
    public float getLargeFontSize(){
        return this.largeFontSize;
    }

    public void onSearchBarChanged(String searchBarText){
        GuiBookletBase parent = !(this instanceof GuiEntry) ? this : this.parentPage;
        this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, parent, ActuallyAdditionsAPI.entryAllAndSearch, 0, searchBarText, true));
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
        if(button == this.buttonTrials){
            this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, this, ActuallyAdditionsAPI.entryTrials, 0, "", false));
        }
        else if(this.hasBookmarkButtons() && button instanceof BookmarkButton){
            int index = ArrayUtils.indexOf(this.bookmarkButtons, button);
            if(index >= 0){
                this.bookmarkButtons[index].onPressed();
            }
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
            String lastText = this.searchField.getText();

            this.searchField.textboxKeyTyped(typedChar, key);

            if(!lastText.equals(this.searchField.getText())){
                this.onSearchBarChanged(this.searchField.getText());
            }
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
