/*
 * This file ("GuiBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.api.internal.IEntrySet;
import de.ellpeck.actuallyadditions.mod.booklet.button.BookmarkButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.IndexButton;
import de.ellpeck.actuallyadditions.mod.booklet.button.TexturedButton;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntryAllSearch;
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import de.ellpeck.actuallyadditions.mod.config.GuiConfiguration;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.items.ItemBooklet;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBooklet extends GuiScreen implements IBookletGui{

    public static final ResourceLocation RES_LOC = AssetUtil.getBookletGuiLocation("guiBooklet");
    public static final ResourceLocation RES_LOC_HALLOWEEN = AssetUtil.getBookletGuiLocation("guiBookletHalloween");
    public static final ResourceLocation RES_LOC_CHRISTMAS = AssetUtil.getBookletGuiLocation("guiBookletChristmas");
    public static final ResourceLocation RES_LOC_VALENTINE = AssetUtil.getBookletGuiLocation("guiBookletValentinesDay");

    public static final int CHAPTER_BUTTONS_AMOUNT = 13;
    public static final int INDEX_BUTTONS_OFFSET = 3;
    private static final int[] AND_HIS_NAME_IS = new int[]{Keyboard.KEY_C, Keyboard.KEY_E, Keyboard.KEY_N, Keyboard.KEY_A};
    public final int xSize;
    public final int ySize;
    public final IEntrySet currentEntrySet = new EntrySet(null);
    public final GuiButton[] chapterButtons = new GuiButton[CHAPTER_BUTTONS_AMOUNT];
    public final GuiButton[] bookmarkButtons = new GuiButton[8];
    public final GuiScreen parentScreen;
    private final boolean tryOpenMainPage;
    private final boolean saveOnClose;
    public int guiLeft;
    public int guiTop;
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
    public GuiButton buttonPatreon;
    public GuiButton buttonViewOnline;
    public GuiTextField searchField;
    public boolean shouldSaveDataNextClose;
    public String bookletName;
    private int ticksElapsed;
    private boolean mousePressed;
    private int hisNameIsAt;

    public GuiBooklet(GuiScreen parentScreen, boolean tryOpenMainPage, boolean saveOnClose){
        this.xSize = 146;
        this.ySize = 180;
        this.parentScreen = parentScreen;
        this.tryOpenMainPage = tryOpenMainPage;
        this.saveOnClose = saveOnClose;
    }

    public FontRenderer getFontRenderer(){
        return this.fontRendererObj;
    }

    @Override
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
        GlStateManager.scale(1.0F, 1.0F, 1.0F);

        //Draws the Background
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ClientProxy.jingleAllTheWay ? RES_LOC_CHRISTMAS : (ClientProxy.pumpkinBlurPumpkinBlur ? RES_LOC_HALLOWEEN : (ClientProxy.bulletForMyValentine ? RES_LOC_VALENTINE : RES_LOC)));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        //Draws the search bar
        if(this.currentEntrySet.getCurrentEntry() instanceof BookletEntryAllSearch && this.currentEntrySet.getCurrentChapter() == null){
            this.mc.getTextureManager().bindTexture(RES_LOC);
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

        //Buttons and search field
        if(this.currentEntrySet.getCurrentPage() != null){
            this.fontRendererObj.setUnicodeFlag(false);
        }
        for(GuiButton button : this.buttonList){
            button.drawButton(this.mc, x, y);
        }
        this.fontRendererObj.setUnicodeFlag(true);

        this.searchField.drawTextBox();

        //Renders the current page's content
        if(this.currentEntrySet.getCurrentEntry() != null && this.currentEntrySet.getCurrentChapter() != null && this.currentEntrySet.getCurrentPage() != null){
            this.currentEntrySet.getCurrentPage().render(this, x, y, this.ticksElapsed, this.mousePressed);
        }

        //Draws hovering texts for buttons
        this.fontRendererObj.setUnicodeFlag(false);
        BookletUtils.doHoverTexts(this, x, y);
        BookletUtils.drawAchievementInfo(this, false, x, y);

        this.fontRendererObj.setUnicodeFlag(unicodeBefore);

        //Resets mouse
        this.mousePressed = false;
    }

    @Override
    public void keyTyped(char theChar, int key){
        if(!this.searchField.isFocused() && AND_HIS_NAME_IS.length > this.hisNameIsAt && AND_HIS_NAME_IS[this.hisNameIsAt] == key){
            if(this.hisNameIsAt+1 >= AND_HIS_NAME_IS.length){
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundHandler.duhDuhDuhDuuuh, 0.5F));
                ModUtil.LOGGER.info("AND HIS NAME IS JOHN CENA DUH DUH DUH DUUUH");
                this.hisNameIsAt = 0;
            }
            else{
                this.hisNameIsAt++;
            }
        }
        else{
            this.hisNameIsAt = 0;

            if(key == Keyboard.KEY_ESCAPE || (key == this.mc.gameSettings.keyBindInventory.getKeyCode() && !this.searchField.isFocused())){
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
        }

    }

    @Override
    public void drawHoveringText(List list, int x, int y){
        super.drawHoveringText(list, x, y);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException{
        this.searchField.mouseClicked(par1, par2, par3);
        //Left mouse button
        if(par3 == 0 && this.currentEntrySet.getCurrentChapter() != null){
            this.mousePressed = true;
        }
        //Right mouse button
        else if(par3 == 1){
            if(this.currentEntrySet.getCurrentChapter() != null){
                BookletUtils.openIndexEntry(this, this.currentEntrySet.getCurrentEntry(), this.currentEntrySet.getPageInIndex(), true);
            }
            else{
                BookletUtils.openIndexEntry(this, null, 1, true);
            }
        }
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(this.currentEntrySet.getCurrentPage() != null){
            if(this.currentEntrySet.getCurrentPage().onActionPerformed(this, button)){
                return;
            }
        }

        //Handles update
        if(button == this.buttonUpdate){
            if(UpdateChecker.needsUpdateNotify){
                BookletUtils.openBrowser(UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK);
            }
        }
        //Handles View Online
        else if(button == this.buttonViewOnline){
            IBookletChapter chapter = this.currentEntrySet.getCurrentChapter();
            if(chapter != null){
                BookletUtils.openBrowser("http://ellpeck.de/actaddmanual/#"+chapter.getUnlocalizedName());
            }
        }
        //Handles Website
        else if(button == this.buttonWebsite){
            BookletUtils.openBrowser("http://ellpeck.de");
        }
        //Handles Patreon
        else if(button == this.buttonPatreon){
            BookletUtils.openBrowser("http://www.patreon.com/Ellpeck");
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
            this.mc.displayGuiScreen(new GuiConfiguration(this));
        }
        //Handles achievements
        else if(button == this.buttonAchievements){
            this.mc.displayGuiScreen(new GuiAAAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }
        else if(button == this.buttonForward){
            BookletUtils.handleNextPage(this);
        }
        else if(button == this.buttonBackward){
            BookletUtils.handlePreviousPage(this);
        }
        //Handles gonig from page to chapter or from chapter to index
        else if(button == this.buttonPreviousScreen){
            if(this.currentEntrySet.getCurrentChapter() != null){
                BookletUtils.openIndexEntry(this, this.currentEntrySet.getCurrentEntry(), this.currentEntrySet.getPageInIndex(), true);
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

    @Override
    public void initGui(){
        int flavor = 1;
        if(Util.RANDOM.nextFloat() <= 0.1){
            flavor = MathHelper.getRandomIntegerInRange(Util.RANDOM, 2, 6);
        }
        this.bookletName = "info."+ModUtil.MOD_ID+".booklet.manualName.1."+flavor;

        this.guiLeft = (this.width-this.xSize)/2;
        this.guiTop = (this.height-this.ySize)/2;

        this.buttonForward = new TexturedButton(0, this.guiLeft+this.xSize-26, this.guiTop+this.ySize+1, 164, 0, 18, 10, Collections.singletonList(TextFormatting.GOLD+"Next Page"));
        this.buttonList.add(this.buttonForward);

        this.buttonBackward = new TexturedButton(1, this.guiLeft+8, this.guiTop+this.ySize+1, 146, 0, 18, 10, Collections.singletonList(TextFormatting.GOLD+"Previous Page"));
        this.buttonList.add(this.buttonBackward);

        this.buttonPreviousScreen = new TexturedButton(2, this.guiLeft+this.xSize/2-7, this.guiTop+this.ySize+1, 182, 0, 15, 10, Collections.singletonList(TextFormatting.GOLD+"Back"));
        this.buttonList.add(this.buttonPreviousScreen);

        ArrayList updateHover = new ArrayList();
        if(UpdateChecker.checkFailed){
            updateHover.add(ITextComponent.Serializer.jsonToComponent(StringUtil.localize("info."+ModUtil.MOD_ID+".update.failed")).getFormattedText());
        }
        else if(UpdateChecker.needsUpdateNotify){
            updateHover.add(ITextComponent.Serializer.jsonToComponent(StringUtil.localize("info."+ModUtil.MOD_ID+".update.generic")).getFormattedText());
            updateHover.add(ITextComponent.Serializer.jsonToComponent(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".update.versionCompare", ModUtil.VERSION, UpdateChecker.updateVersionString)).getFormattedText());
            updateHover.add(StringUtil.localize("info."+ModUtil.MOD_ID+".update.buttonOptions"));
        }
        this.buttonUpdate = new TexturedButton(4, this.guiLeft-11, this.guiTop-11, 245, 0, 11, 11, updateHover);
        this.buttonUpdate.visible = UpdateChecker.needsUpdateNotify || UpdateChecker.checkFailed;
        this.buttonList.add(this.buttonUpdate);

        this.buttonTwitter = new TexturedButton(5, this.guiLeft, this.guiTop+10, 213, 0, 8, 8, Collections.singletonList(TextFormatting.GOLD+"Open @ActAddMod on Twitter in Browser"));
        this.buttonList.add(this.buttonTwitter);

        this.buttonForum = new TexturedButton(6, this.guiLeft, this.guiTop+20, 221, 0, 8, 8, Collections.singletonList(TextFormatting.GOLD+"Open Minecraft Forum Post in Browser"));
        this.buttonList.add(this.buttonForum);

        this.buttonAchievements = new TexturedButton(7, this.guiLeft+138, this.guiTop, 205, 0, 8, 8, Collections.singletonList(TextFormatting.GOLD+"Show Achievements"));
        this.buttonList.add(this.buttonAchievements);

        ArrayList websiteHover = new ArrayList();
        websiteHover.add(TextFormatting.GOLD+"Open Author's Website");
        websiteHover.add("(There's some cool stuff there!)");
        websiteHover.add(TextFormatting.GRAY+""+TextFormatting.ITALIC+"Would you call this Product Placement?");
        this.buttonWebsite = new TexturedButton(-99, this.guiLeft, this.guiTop+30, 229, 0, 8, 8, websiteHover);
        this.buttonList.add(this.buttonWebsite);

        List<String> patreonHover = new ArrayList<String>();
        patreonHover.add("Like the mod?");
        patreonHover.add("Why don't support me on "+TextFormatting.GOLD+"Patreon"+TextFormatting.RESET+"?");
        this.buttonPatreon = new TexturedButton(-100, this.guiLeft, this.guiTop, 237, 0, 8, 8, patreonHover);
        this.buttonList.add(this.buttonPatreon);

        this.buttonViewOnline = new TexturedButton(-101, this.guiLeft+146, this.guiTop+180, 245, 44, 11, 11, Collections.singletonList(TextFormatting.GOLD+"View Online"));
        this.buttonList.add(this.buttonViewOnline);

        ArrayList configHover = new ArrayList();
        configHover.add(TextFormatting.GOLD+"Show Configuration GUI");
        configHover.addAll(this.fontRendererObj.listFormattedStringToWidth("It is highly recommended that you restart your game after changing anything as that prevents possible bugs occuring!", 200));
        this.buttonConfig = new TexturedButton(8, this.guiLeft+138, this.guiTop+10, 197, 0, 8, 8, configHover);
        this.buttonList.add(this.buttonConfig);

        for(int i = 0; i < this.chapterButtons.length; i++){
            this.chapterButtons[i] = new IndexButton(9+i, this.guiLeft+15, this.guiTop+10+(i*12), 115, 10, "", this);
            this.buttonList.add(this.chapterButtons[i]);
        }

        for(int i = 0; i < this.bookmarkButtons.length; i++){
            int x = this.guiLeft+this.xSize/2-(this.bookmarkButtons.length/2*16)+(i*16);
            this.bookmarkButtons[i] = new BookmarkButton(this.chapterButtons[this.chapterButtons.length-1].id+1+i, x, this.guiTop+this.ySize+13, this);
            this.buttonList.add(this.bookmarkButtons[i]);
        }

        this.searchField = new GuiTextField(4500, this.fontRendererObj, this.guiLeft+148, this.guiTop+162, 66, 10);
        this.searchField.setMaxStringLength(30);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setCanLoseFocus(false);

        this.currentEntrySet.removeEntry();

        if(ItemBooklet.forcedEntry == null){
            //Open last entry or introductory entry
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(Minecraft.getMinecraft().thePlayer);
            if(data != null){
                if(this.tryOpenMainPage && !data.theCompound.getBoolean("BookAlreadyOpened")){
                    BookletUtils.openIndexEntry(this, InitBooklet.chapterIntro.entry, 1, true);
                    BookletUtils.openChapter(this, InitBooklet.chapterIntro, null);

                    NBTTagCompound extraData = new NBTTagCompound();
                    extraData.setBoolean("BookAlreadyOpened", true);
                    NBTTagCompound dataToSend = new NBTTagCompound();
                    dataToSend.setTag("Data", extraData);
                    dataToSend.setInteger("WorldID", Minecraft.getMinecraft().theWorld.provider.getDimension());
                    dataToSend.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
                    PacketHandler.theNetwork.sendToServer(new PacketClientToServer(dataToSend, PacketHandler.CHANGE_PLAYER_DATA_HANDLER));
                }
                else{
                    BookletUtils.openLastBookPage(this, data.theCompound.getCompoundTag("BookletData"));
                }
            }
            this.shouldSaveDataNextClose = false;
        }
        else{
            //Open forced entry
            BookletUtils.openIndexEntry(this, ItemBooklet.forcedEntry.entry, ItemBooklet.forcedEntry.pageInIndex, true);
            BookletUtils.openChapter(this, ItemBooklet.forcedEntry.chapter, ItemBooklet.forcedEntry.page);
            ItemBooklet.forcedEntry = null;

            this.shouldSaveDataNextClose = true;
        }

    }

    @Override
    //For scrolling through pages
    public void handleMouseInput() throws IOException{
        int wheel = Mouse.getEventDWheel();
        if(wheel != 0){
            if(wheel < 0){
                BookletUtils.handleNextPage(this);
            }
            else if(wheel > 0){
                BookletUtils.handlePreviousPage(this);
            }
        }
        super.handleMouseInput();
    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.searchField.updateCursorCounter();

        if(this.currentEntrySet.getCurrentEntry() != null && this.currentEntrySet.getCurrentChapter() != null && this.currentEntrySet.getCurrentPage() != null){
            this.currentEntrySet.getCurrentPage().updateScreen(this.ticksElapsed);
        }

        boolean buttonThere = UpdateChecker.needsUpdateNotify || UpdateChecker.checkFailed;
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
        if(this.saveOnClose && this.shouldSaveDataNextClose){
            NBTTagCompound bookletData = new NBTTagCompound();
            BookletUtils.saveBookPage(this, bookletData);

            NBTTagCompound extraData = new NBTTagCompound();
            extraData.setTag("BookletData", bookletData);

            NBTTagCompound dataToSend = new NBTTagCompound();
            dataToSend.setTag("Data", extraData);
            dataToSend.setInteger("WorldID", Minecraft.getMinecraft().theWorld.provider.getDimension());
            dataToSend.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
            PacketHandler.theNetwork.sendToServer(new PacketClientToServer(dataToSend, PacketHandler.CHANGE_PLAYER_DATA_HANDLER));
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
                list.set(k, TextFormatting.GRAY+(String)list.get(k));
            }
        }

        if(renderTransferButton){
            BookletPage page = BookletUtils.getFirstPageForStack(stack);
            if(page != null && page != from){
                list.add(from.getClickToSeeRecipeString());

                if(mousePressed){
                    BookletUtils.openIndexEntry(this, page.getChapter().getEntry(), ActuallyAdditionsAPI.BOOKLET_ENTRIES.indexOf(page.getChapter().getEntry())/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                    BookletUtils.openChapter(this, page.getChapter(), page);
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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
    public IEntrySet getCurrentEntrySet(){
        return this.currentEntrySet;
    }
}