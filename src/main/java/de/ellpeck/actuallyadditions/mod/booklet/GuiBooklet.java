/*
 * This file ("GuiBooklet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.internal.EntrySet;
import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.booklet.button.BookmarkButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.IndexButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.TexturedButton;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntryAllSearch;
import de.ellpeck.actuallyadditions.mod.config.GuiConfiguration;
import de.ellpeck.actuallyadditions.mod.items.ItemBooklet;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PersistentClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBooklet extends GuiScreen implements IBookletGui{

    public static final ResourceLocation resLoc = AssetUtil.getBookletGuiLocation("guiBooklet");
    public static final ResourceLocation resLocHalloween = AssetUtil.getBookletGuiLocation("guiBookletHalloween");
    public static final ResourceLocation resLocChristmas = AssetUtil.getBookletGuiLocation("guiBookletChristmas");
    public static final ResourceLocation resLocValentine = AssetUtil.getBookletGuiLocation("guiBookletValentinesDay");

    public static final int CHAPTER_BUTTONS_AMOUNT = 13;
    public static final int INDEX_BUTTONS_OFFSET = 3;
    private static final int[] AND_HIS_NAME_IS = new int[]{Keyboard.KEY_C, Keyboard.KEY_E, Keyboard.KEY_N, Keyboard.KEY_A};
    public int xSize;
    public int ySize;
    public int guiLeft;
    public int guiTop;
    public EntrySet currentEntrySet = new EntrySet(null);
    public int indexPageAmount;
    public GuiButton buttonForward;
    public GuiButton buttonBackward;
    public GuiButton buttonPreviousScreen;
    public GuiButton buttonUpdate;
    public GuiButton buttonTwitter;
    public GuiButton buttonForum;
    public GuiButton buttonAchievements;
    public GuiButton buttonConfig;
    public GuiButton buttonWebsite;
    public GuiButton[] chapterButtons = new GuiButton[CHAPTER_BUTTONS_AMOUNT];
    public GuiButton[] bookmarkButtons = new GuiButton[8];
    public GuiTextField searchField;
    public GuiScreen parentScreen;
    private int ticksElapsed;
    private boolean mousePressed;
    private boolean tryOpenMainPage;
    private boolean saveOnClose;
    private int hisNameIsAt;

    public GuiBooklet(GuiScreen parentScreen, boolean tryOpenMainPage, boolean saveOnClose){
        this.xSize = 146;
        this.ySize = 180;
        this.parentScreen = parentScreen;
        this.tryOpenMainPage = tryOpenMainPage;
        this.saveOnClose = saveOnClose;
    }

    @Override
    public void drawHoveringText(List list, int x, int y){
        super.drawHoveringText(list, x, y);
    }

    public FontRenderer getFontRenderer(){
        return this.fontRendererObj;
    }

    public List getButtonList(){
        return this.buttonList;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        //Fixes Unicode flag
        boolean unicodeBefore = this.fontRendererObj.getUnicodeFlag();
        this.fontRendererObj.setUnicodeFlag(true);

        //To Player:
        //
        //FastCraft's a must, definitely
        //But the bigger unicode option sucks real
        //It screws with my book and makes me feel ill
        //So don't fuck with everything unintentionally
        //
        //(This fixes your fuckery)
        GL11.glScalef(1.0F, 1.0F, 1.0F);

        //Draws the Background
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ClientProxy.jingleAllTheWay ? resLocChristmas : (ClientProxy.pumpkinBlurPumpkinBlur ? resLocHalloween : (ClientProxy.bulletForMyValentine ? resLocValentine : resLoc)));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        //Draws the search bar
        if(this.currentEntrySet.entry instanceof BookletEntryAllSearch && this.currentEntrySet.chapter == null){
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

        //Renders the current page's content
        if(this.currentEntrySet.entry != null && this.currentEntrySet.chapter != null && this.currentEntrySet.page != null){
            this.currentEntrySet.page.render(this, x, y, this.ticksElapsed, this.mousePressed);
        }

        //Draws hovering texts for buttons
        this.fontRendererObj.setUnicodeFlag(false);
        BookletUtils.doHoverTexts(this, x, y);
        BookletUtils.drawAchievementInfo(this, false, x, y);
        this.fontRendererObj.setUnicodeFlag(true);

        this.fontRendererObj.setUnicodeFlag(unicodeBefore);

        //Resets mouse
        if(this.mousePressed){
            this.mousePressed = false;
        }
    }

    @Override
    public void keyTyped(char theChar, int key){
        if(key == Keyboard.KEY_ESCAPE){
            if(this.parentScreen != null){
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else{
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
            }
        }
        else if(this.searchField.isFocused()){
            this.searchField.textboxKeyTyped(theChar, key);
            BookletUtils.updateSearchBar(this);
        }
        else{
            if(AND_HIS_NAME_IS.length > this.hisNameIsAt && AND_HIS_NAME_IS[this.hisNameIsAt] == key){
                if(this.hisNameIsAt+1 >= AND_HIS_NAME_IS.length){
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(ModUtil.MOD_ID_LOWER, "duhDuhDuhDuuuh")));
                    ModUtil.LOGGER.info("AND HIS NAME IS JOHN CENA DUH DUH DUH DUUUH");
                    this.hisNameIsAt = 0;
                }
                else{
                    this.hisNameIsAt++;
                }
            }
            else{
                this.hisNameIsAt = 0;
            }
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3){
        this.searchField.mouseClicked(par1, par2, par3);
        //Left mouse button
        if(par3 == 0 && this.currentEntrySet.chapter != null){
            this.mousePressed = true;
        }
        //Right mouse button
        else if(par3 == 1){
            if(this.currentEntrySet.chapter != null){
                BookletUtils.openIndexEntry(this, this.currentEntrySet.entry, this.currentEntrySet.pageInIndex, true);
            }
            else{
                BookletUtils.openIndexEntry(this, null, 1, true);
            }
        }
        try{
            super.mouseClicked(par1, par2, par3);
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Something bad happened when trying to click a button in the booklet!", e);
        }
    }

    @Override
    public void actionPerformed(GuiButton button){
        //Handles update
        if(button == this.buttonUpdate){
            if(UpdateChecker.needsUpdateNotify){
                BookletUtils.openBrowser(UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK);
            }
        }
        //Handles Website
        else if(button == this.buttonWebsite){
            BookletUtils.openBrowser("http://ellpeck.de");
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
            if(this.currentEntrySet.chapter != null){
                BookletUtils.openIndexEntry(this, this.currentEntrySet.entry, this.currentEntrySet.pageInIndex, true);
            }
            else{
                BookletUtils.openIndexEntry(this, null, 1, true);
            }
        }
        //Handles Bookmark button
        else if(button instanceof BookmarkButton){
            ((BookmarkButton)button).onPressed();
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

        this.buttonForward = new TexturedButton(0, this.guiLeft+this.xSize-26, this.guiTop+this.ySize+1, 164, 0, 18, 10, Collections.singletonList(EnumChatFormatting.GOLD+"Next Page"));
        this.buttonList.add(this.buttonForward);

        this.buttonBackward = new TexturedButton(1, this.guiLeft+8, this.guiTop+this.ySize+1, 146, 0, 18, 10, Collections.singletonList(EnumChatFormatting.GOLD+"Previous Page"));
        this.buttonList.add(this.buttonBackward);

        this.buttonPreviousScreen = new TexturedButton(2, this.guiLeft+this.xSize/2-7, this.guiTop+this.ySize+1, 182, 0, 15, 10, Collections.singletonList(EnumChatFormatting.GOLD+"Back"));
        this.buttonList.add(this.buttonPreviousScreen);

        ArrayList updateHover = new ArrayList();
        if(UpdateChecker.checkFailed){
            updateHover.add(IChatComponent.Serializer.jsonToComponent(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.failed")).getFormattedText());
        }
        else if(UpdateChecker.needsUpdateNotify){
            updateHover.add(IChatComponent.Serializer.jsonToComponent(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.generic")).getFormattedText());
            updateHover.add(IChatComponent.Serializer.jsonToComponent(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID_LOWER+".update.versionCompare", ModUtil.VERSION, UpdateChecker.updateVersion)).getFormattedText());
            updateHover.add(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.buttonOptions"));
        }
        this.buttonUpdate = new TexturedButton(4, this.guiLeft-11, this.guiTop-11, 245, 0, 11, 11, updateHover);
        this.buttonUpdate.visible = UpdateChecker.needsUpdateNotify;
        this.buttonList.add(this.buttonUpdate);

        this.buttonTwitter = new TexturedButton(5, this.guiLeft, this.guiTop, 213, 0, 8, 8, Collections.singletonList(EnumChatFormatting.GOLD+"Open @ActAddMod on Twitter in Browser"));
        this.buttonList.add(this.buttonTwitter);

        this.buttonForum = new TexturedButton(6, this.guiLeft, this.guiTop+10, 221, 0, 8, 8, Collections.singletonList(EnumChatFormatting.GOLD+"Open Minecraft Forum Post in Browser"));
        this.buttonList.add(this.buttonForum);

        this.buttonAchievements = new TexturedButton(7, this.guiLeft+138, this.guiTop, 205, 0, 8, 8, Collections.singletonList(EnumChatFormatting.GOLD+"Show Achievements"));
        this.buttonList.add(this.buttonAchievements);

        ArrayList websiteHover = new ArrayList();
        websiteHover.add(EnumChatFormatting.GOLD+"Open Author's Website");
        websiteHover.add("(There's some cool stuff there!)");
        websiteHover.add(EnumChatFormatting.GRAY+""+EnumChatFormatting.ITALIC+"Would you call this Product Placement?");
        this.buttonWebsite = new TexturedButton(-99, this.guiLeft, this.guiTop+20, 228, 0, 8, 8, websiteHover);
        this.buttonList.add(this.buttonWebsite);

        ArrayList configHover = new ArrayList();
        configHover.add(EnumChatFormatting.GOLD+"Show Configuration GUI");
        configHover.addAll(this.fontRendererObj.listFormattedStringToWidth("It is highly recommended that you restart your game after changing anything as that prevents possible bugs occuring!", 200));
        this.buttonConfig = new TexturedButton(8, this.guiLeft+138, this.guiTop+10, 197, 0, 8, 8, configHover);
        this.buttonList.add(this.buttonConfig);

        for(int i = 0; i < this.chapterButtons.length; i++){
            this.chapterButtons[i] = new IndexButton(9+i, guiLeft+15, guiTop+10+(i*12), 115, 10, "", this);
            this.buttonList.add(this.chapterButtons[i]);
        }

        for(int i = 0; i < this.bookmarkButtons.length; i++){
            int x = this.guiLeft+xSize/2-(this.bookmarkButtons.length/2*16)+(i*16);
            this.bookmarkButtons[i] = new BookmarkButton(this.chapterButtons[this.chapterButtons.length-1].id+1+i, x, this.guiTop+this.ySize+13, this);
            this.buttonList.add(this.bookmarkButtons[i]);
        }

        this.searchField = new GuiTextField(4500, this.fontRendererObj, guiLeft+148, guiTop+162, 66, 10);
        this.searchField.setMaxStringLength(30);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setCanLoseFocus(false);

        this.currentEntrySet.removeEntry();

        if(ItemBooklet.forcedEntry == null){
            //Open last entry or introductory entry
            if(this.tryOpenMainPage && !PersistentClientData.getBoolean("BookAlreadyOpened")){
                BookletUtils.openIndexEntry(this, InitBooklet.chapterIntro.entry, 1, true);
                BookletUtils.openChapter(this, InitBooklet.chapterIntro, null);

                PersistentClientData.setBoolean("BookAlreadyOpened", true);
            }
            else{
                PersistentClientData.openLastBookPage(this);
            }
        }
        else{
            //Open forced entry
            BookletUtils.openIndexEntry(this, ItemBooklet.forcedEntry.entry, ItemBooklet.forcedEntry.pageInIndex, true);
            BookletUtils.openChapter(this, ItemBooklet.forcedEntry.chapter, ItemBooklet.forcedEntry.page);
            ItemBooklet.forcedEntry = null;
        }
    }

    @Override
    //For scrolling through pages
    public void handleMouseInput(){
        int wheel = Mouse.getEventDWheel();
        if(wheel != 0){
            if(wheel > 0){
                BookletUtils.handleNextPage(this);
            }
            else if(wheel < 0){
                BookletUtils.handlePreviousPage(this);
            }
        }
        try{
            super.handleMouseInput();
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Something bad happened when trying to click a button in the booklet!", e);
        }
    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.searchField.updateCursorCounter();

        if(this.currentEntrySet.entry != null && this.currentEntrySet.chapter != null && this.currentEntrySet.page != null){
            this.currentEntrySet.page.updateScreen(this.ticksElapsed);
        }

        boolean buttonThere = UpdateChecker.needsUpdateNotify;
        this.buttonUpdate.visible = buttonThere;
        if(buttonThere){
            if(this.ticksElapsed%8 == 0){
                TexturedButton button = (TexturedButton)this.buttonUpdate;
                button.setTexturePos(245, button.texturePosY == 0 ? 22 : 0);
            }
        }

        this.ticksElapsed++;
    }

    @Override
    public void onGuiClosed(){
        if(this.saveOnClose){
            PersistentClientData.saveBookPage(this);
        }
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void renderTooltipAndTransferButton(BookletPage from, ItemStack stack, int x, int y, boolean renderTransferButton, boolean mousePressed){
        boolean flagBefore = this.mc.fontRendererObj.getUnicodeFlag();
        this.mc.fontRendererObj.setUnicodeFlag(false);

        List list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

        for(int k = 0; k < list.size(); ++k){
            if(k == 0){
                list.set(k, stack.getRarity().rarityColor+(String)list.get(k));
            }
            else{
                list.set(k, EnumChatFormatting.GRAY+(String)list.get(k));
            }
        }

        if(renderTransferButton){
            BookletPage page = BookletUtils.getFirstPageForStack(stack);
            if(page != null && page != from){
                list.add(from.getClickToSeeRecipeString());

                if(mousePressed){
                    BookletUtils.openIndexEntry(this, page.getChapter().getEntry(), ActuallyAdditionsAPI.bookletEntries.indexOf(page.getChapter().getEntry())/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                    BookletUtils.openChapter(this, page.getChapter(), page);
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                }
            }
        }

        this.drawHoveringText(list, x, y);

        this.mc.fontRendererObj.setUnicodeFlag(flagBefore);
    }

    @Override
    public int getXSize(){
        return this.xSize;
    }

    @Override
    public int getYSize(){
        return this.ySize;
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
    public void drawRect(int startX, int startY, int u, int v, int xSize, int ySize){
        this.drawTexturedModalRect(startX, startY, u, v, xSize, ySize);
    }

    @Override
    public EntrySet getCurrentEntrySet(){
        return this.currentEntrySet;
    }
}