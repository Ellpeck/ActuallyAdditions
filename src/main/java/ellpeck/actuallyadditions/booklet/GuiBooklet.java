/*
 * This file ("GuiBooklet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.entry.BookletEntryAllSearch;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.config.GuiConfiguration;
import ellpeck.actuallyadditions.proxy.ClientProxy;
import ellpeck.actuallyadditions.update.UpdateChecker;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.playerdata.PersistentClientData;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBooklet extends GuiScreen{

    public static final ResourceLocation resLoc = AssetUtil.getBookletGuiLocation("guiBooklet");
    public static final ResourceLocation resLocHalloween = AssetUtil.getBookletGuiLocation("guiBookletHalloween");
    public static final ResourceLocation resLocChristmas = AssetUtil.getBookletGuiLocation("guiBookletChristmas");
    public static final ResourceLocation resLocValentine = AssetUtil.getBookletGuiLocation("guiBookletValentinesDay");
    public static final int CHAPTER_BUTTONS_AMOUNT = 13;
    public static final int TOOLTIP_SPLIT_LENGTH = 200;
    public int xSize;
    public int ySize;
    public int guiLeft;
    public int guiTop;
    public BookletPage currentPage;
    public BookletChapter currentChapter;
    public BookletEntry currentIndexEntry;
    public int pageOpenInIndex;
    public int indexPageAmount;
    public GuiButton buttonForward;
    public GuiButton buttonBackward;
    public GuiButton buttonPreviousScreen;
    public GuiButton buttonPreviouslyOpenedGui;
    public GuiButton buttonUpdate;
    public GuiButton buttonTwitter;
    public GuiButton buttonForum;
    public GuiButton buttonAchievements;
    public GuiButton buttonConfig;
    public GuiButton[] chapterButtons = new GuiButton[CHAPTER_BUTTONS_AMOUNT];
    public GuiTextField searchField;
    private int ticksElapsed;
    private boolean mousePressed;

    public GuiScreen parentScreen;

    public GuiBooklet(GuiScreen parentScreen){
        this.xSize = 146;
        this.ySize = 180;
        this.parentScreen = parentScreen;
    }

    public void drawHoveringText(List list, int x, int y){
        super.func_146283_a(list, x, y);
    }

    public FontRenderer getFontRenderer(){
        return this.fontRendererObj;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        //Fixes Unicode flag
        boolean unicodeBefore = this.fontRendererObj.getUnicodeFlag();
        this.fontRendererObj.setUnicodeFlag(true);

        //Draws the Background
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ClientProxy.jingleAllTheWay ? resLocChristmas : (ClientProxy.pumpkinBlurPumpkinBlur ? resLocHalloween : (ClientProxy.bulletForMyValentine ? resLocValentine : resLoc)));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        //Draws the search bar
        if(this.currentIndexEntry instanceof BookletEntryAllSearch && this.currentChapter == null){
            this.mc.getTextureManager().bindTexture(resLoc);
            this.drawTexturedModalRect(this.guiLeft+146, this.guiTop+160, 146, 80, 70, 14);
        }

        //Draws Achievement Info
        BookletUtils.drawAchievementInfo(this, true, x, y);

        //Draws the title
        this.fontRendererObj.setUnicodeFlag(false);
        BookletUtils.drawTitle(this);
        this.fontRendererObj.setUnicodeFlag(true);

        //Pre-Renders the current page's content etc.
        BookletUtils.renderPre(this, x, y, this.ticksElapsed, this.mousePressed);

        //Does vanilla drawing stuff
        super.drawScreen(x, y, f);
        this.searchField.drawTextBox();

        //Draws hovering texts for buttons
        this.fontRendererObj.setUnicodeFlag(false);
        BookletUtils.doHoverTexts(this, x, y);
        BookletUtils.drawAchievementInfo(this, false, x, y);
        this.fontRendererObj.setUnicodeFlag(true);

        //Renders the current page's content
        if(this.currentIndexEntry != null && this.currentChapter != null && this.currentPage != null){
            this.currentPage.render(this, x, y, this.ticksElapsed, this.mousePressed);
        }
        this.fontRendererObj.setUnicodeFlag(unicodeBefore);

        //Resets mouse
        if(this.mousePressed){
            this.mousePressed = false;
        }
    }

    @Override
    public void keyTyped(char theChar, int key){
        if(key != 1 && this.searchField.isFocused()){
            this.searchField.textboxKeyTyped(theChar, key);
            BookletUtils.updateSearchBar(this);
        }
        else{
            super.keyTyped(theChar, key);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3){
        this.searchField.mouseClicked(par1, par2, par3);
        //Notifys the booklet of the mouse being pressed
        if(par3 == 0 && this.currentChapter != null){
            this.mousePressed = true;
        }
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void actionPerformed(GuiButton button){
        //Handles going to the parent GUI
        if(button == this.buttonPreviouslyOpenedGui){
            if(this.parentScreen != null){
                mc.displayGuiScreen(this.parentScreen);
            }
        }
        //Handles update
        else if(button == this.buttonUpdate){
            if(UpdateChecker.needsUpdateNotify){
                BookletUtils.openBrowser(UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK);
            }
        }
        //Handles Twitter
        else if(button == this.buttonTwitter){
            BookletUtils.openBrowser("http://twitter.com/ActAddMod");
        }
        //Handles forum
        else if(button == this.buttonForum){
            BookletUtils.openBrowser("http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2551118");
        }
        //Handles config
        else if(button == this.buttonConfig){
            mc.displayGuiScreen(new GuiConfiguration(this));
        }
        //Handles achievements
        else if(button == this.buttonAchievements){
            mc.displayGuiScreen(new GuiAAAchievements(this, mc.thePlayer.getStatFileWriter()));
        }
        else if(button == this.buttonForward){
            BookletUtils.handleNextPage(this);
        }
        else if(button == this.buttonBackward){
            BookletUtils.handlePreviousPage(this);
        }
        //Handles gonig from page to chapter or from chapter to index
        else if(button == this.buttonPreviousScreen){
            if(this.currentChapter != null && this.currentChapter != InitBooklet.chapterIntro){
                BookletUtils.openIndexEntry(this, this.currentIndexEntry, this.pageOpenInIndex, true);
            }
            else{
                BookletUtils.openIndexEntry(this, null, 1, true);
            }
        }
        else{
            BookletUtils.handleChapterButtonClick(this, button);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        this.guiLeft = (this.width-this.xSize)/2;
        this.guiTop = (this.height-this.ySize)/2;

        this.buttonForward = new BookletUtils.TexturedButton(0, this.guiLeft+this.xSize, this.guiTop+this.ySize+2, 164, 0, 18, 10);
        this.buttonList.add(this.buttonForward);

        this.buttonBackward = new BookletUtils.TexturedButton(1, this.guiLeft-18, this.guiTop+this.ySize+2, 146, 0, 18, 10);
        this.buttonList.add(this.buttonBackward);

        this.buttonPreviousScreen = new BookletUtils.TexturedButton(2, this.guiLeft+this.xSize/2-7, this.guiTop+this.ySize+2, 182, 0, 15, 10);
        this.buttonList.add(this.buttonPreviousScreen);

        this.buttonPreviouslyOpenedGui = new BookletUtils.TexturedButton(3, this.guiLeft+this.xSize/3, this.guiTop+this.ySize+2, 245, 44, 11, 15);
        this.buttonList.add(this.buttonPreviouslyOpenedGui);

        this.buttonUpdate = new BookletUtils.TexturedButton(4, this.guiLeft-11, this.guiTop-11, 245, 0, 11, 11);
        this.buttonUpdate.visible = UpdateChecker.needsUpdateNotify;
        this.buttonList.add(this.buttonUpdate);

        this.buttonTwitter = new BookletUtils.TexturedButton(5, this.guiLeft, this.guiTop, 213, 0, 8, 8);
        this.buttonList.add(this.buttonTwitter);

        this.buttonForum = new BookletUtils.TexturedButton(6, this.guiLeft, this.guiTop+10, 221, 0, 8, 8);
        this.buttonList.add(this.buttonForum);

        this.buttonAchievements = new BookletUtils.TexturedButton(7, this.guiLeft+138, this.guiTop, 205, 0, 8, 8);
        this.buttonList.add(this.buttonAchievements);

        this.buttonConfig = new BookletUtils.TexturedButton(8, this.guiLeft+138, this.guiTop+10, 197, 0, 8, 8);
        this.buttonList.add(this.buttonConfig);

        for(int i = 0; i < this.chapterButtons.length; i++){
            this.chapterButtons[i] = new BookletUtils.IndexButton(9+i, guiLeft+15, guiTop+10+(i*12), 115, 10, "", this);
            this.buttonList.add(this.chapterButtons[i]);
        }

        this.searchField = new GuiTextField(this.fontRendererObj, guiLeft+148, guiTop+162, 66, 10);
        this.searchField.setMaxStringLength(30);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setCanLoseFocus(false);

        this.currentPage = null;
        this.currentChapter = null;
        this.currentIndexEntry = null;

        // So that the First Page will still open if used via something like NEI before
        if(this.parentScreen == null && !PersistentClientData.getBoolean("BookAlreadyOpened")){
            BookletUtils.openIndexEntry(this, InitBooklet.chapterIntro.entry, 1, true);
            BookletUtils.openChapter(this, InitBooklet.chapterIntro, null);

            PersistentClientData.setBoolean("BookAlreadyOpened", true);
        }
        else{
            PersistentClientData.openLastBookPage(this);
        }
    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.searchField.updateCursorCounter();

        if(this.currentIndexEntry != null && this.currentChapter != null && this.currentPage != null){
            this.currentPage.updateScreen(this.ticksElapsed);
        }

        boolean buttonThere = UpdateChecker.needsUpdateNotify;
        this.buttonUpdate.visible = buttonThere;
        if(buttonThere){
            if(this.ticksElapsed%8 == 0){
                BookletUtils.TexturedButton button = (BookletUtils.TexturedButton)this.buttonUpdate;
                button.setTexturePos(245, button.texturePosY == 0 ? 22 : 0);
            }
        }

        this.ticksElapsed++;
    }

    @Override
    public void onGuiClosed(){
        PersistentClientData.saveBookPage(this.currentIndexEntry, this.currentChapter, this.currentPage, this.pageOpenInIndex, this.searchField.getText());
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }
}