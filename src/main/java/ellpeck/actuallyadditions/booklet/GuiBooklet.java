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
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.config.GuiConfiguration;
import ellpeck.actuallyadditions.update.UpdateChecker;
import ellpeck.actuallyadditions.util.*;
import ellpeck.actuallyadditions.util.playerdata.PersistentClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBooklet extends GuiScreen{

    public static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiBooklet");
    public static final int CHAPTER_BUTTONS_AMOUNT = 13;
    public static final int TOOLTIP_SPLIT_LENGTH = 200;
    public int xSize;
    public int ySize;
    public int guiLeft;
    public int guiTop;
    public BookletPage currentPage;
    public BookletChapter currentChapter;
    public BookletIndexEntry currentIndexEntry;
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
    private GuiTextField searchField;
    private int ticksElapsed;
    private boolean mousePressed;

    private GuiScreen parentScreen;

    public GuiBooklet(GuiScreen parentScreen){
        this.xSize = 146;
        this.ySize = 180;
        this.parentScreen = parentScreen;
    }

    public void drawHoveringText(List list, int x, int y){
        super.func_146283_a(list, x, y);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void drawScreen(int x, int y, float f){
        boolean unicodeBefore = this.fontRendererObj.getUnicodeFlag();
        this.fontRendererObj.setUnicodeFlag(true);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.isGimmicky() ? 256 : this.xSize, this.isGimmicky() ? 256 : this.ySize);

        if(this.currentIndexEntry instanceof BookletEntryAllSearch && this.currentChapter == null){
            this.drawTexturedModalRect(this.guiLeft+146, this.guiTop+160, 146, 80, 70, 14);
        }

        this.fontRendererObj.setUnicodeFlag(false);
        if(this.currentIndexEntry != null){
            if(this.currentChapter == null){
                this.drawCenteredString(this.fontRendererObj, this.currentIndexEntry.getLocalizedName(), this.guiLeft+this.xSize/2, this.guiTop-8, StringUtil.DECIMAL_COLOR_WHITE);
            }
            else{
                this.drawCenteredString(this.fontRendererObj, this.currentChapter.getLocalizedName(), this.guiLeft+this.xSize/2, this.guiTop-8, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
        else{
            this.drawCenteredString(this.fontRendererObj, StringUtil.localize("itemGroup."+ModUtil.MOD_ID_LOWER), this.guiLeft+this.xSize/2, this.guiTop-8, StringUtil.DECIMAL_COLOR_WHITE);
        }
        this.fontRendererObj.setUnicodeFlag(true);

        if(this.currentIndexEntry != null){
            if(this.currentChapter != null && this.currentPage != null){
                this.drawCenteredString(this.fontRendererObj, this.currentPage.getID()+"/"+this.currentChapter.pages.length, this.guiLeft+this.xSize/2, this.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
                this.currentPage.renderPre(this, x, y, this.ticksElapsed, this.mousePressed);
            }
            else{
                this.drawCenteredString(this.fontRendererObj, this.pageOpenInIndex+"/"+this.indexPageAmount, this.guiLeft+this.xSize/2, this.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }

        if(this.isGimmicky()){
            this.fontRendererObj.drawSplitString("This book looks a lot like the one from Botania, doesn't it? Well, I think it does, too, and I'm kind of annoyed by it to be honest. Wasn't really meant to be that way. I guess I just kind of had the design of the Botania Book in mind when designing this. Well. I made the Code on my own, so I don't really care. Also: How did you find this gimmick? :P -Peck", this.guiLeft-80-3, this.guiTop+25, 80, StringUtil.DECIMAL_COLOR_WHITE);

            String strg = "Click this! I need followaz #Pathetic #DontTakeSeriously ->";
            this.fontRendererObj.drawString(strg, this.guiLeft-this.fontRendererObj.getStringWidth(strg)-3, this.guiTop, StringUtil.DECIMAL_COLOR_WHITE);

            if(this.currentChapter == InitBooklet.chapterIntro && this.currentPage == InitBooklet.chapterIntro.pages[1]){
                strg = "Hey Hose, I kind of hate you a bit for that Ellopecko thing. (Not really! It's fun and games and stuff! :D)";
                this.fontRendererObj.drawString(strg, this.guiLeft+this.xSize/2-this.fontRendererObj.getStringWidth(strg)/2, this.guiTop-20, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }

        super.drawScreen(x, y, f);
        this.searchField.drawTextBox();

        if(this.currentIndexEntry != null && this.currentChapter != null && this.currentPage != null){
            this.currentPage.render(this, x, y, this.ticksElapsed, this.mousePressed);
        }

        this.fontRendererObj.setUnicodeFlag(false);
        //Achievements Hover Text
        if(x >= this.guiLeft+138 && x <= this.guiLeft+138+7 && y >= this.guiTop && y <= this.guiTop+7){
            this.func_146283_a(Collections.singletonList(EnumChatFormatting.GOLD+"Show Achievements"), x, y);
        }
        //Config Hover Text
        if(x >= this.guiLeft+138 && x <= this.guiLeft+138+7 && y >= this.guiTop+10 && y <= this.guiTop+10+7){
            ArrayList list = new ArrayList();
            list.add(EnumChatFormatting.GOLD+"Show Configuration GUI");
            list.addAll(this.fontRendererObj.listFormattedStringToWidth("It is highly recommended that you restart your game after changing anything as that prevents possible bugs occuring!", TOOLTIP_SPLIT_LENGTH));
            this.func_146283_a(list, x, y);

        }
        //Twitter Hover Text
        if(x >= this.guiLeft && x <= this.guiLeft+7 && y >= this.guiTop && y <= this.guiTop+7){
            this.func_146283_a(Collections.singletonList(EnumChatFormatting.GOLD+"Open @ActAddMod on Twitter in Browser"), x, y);
        }
        //Forum Hover Text
        if(x >= this.guiLeft && x <= this.guiLeft+7 && y >= this.guiTop+10 && y <= this.guiTop+10+7){
            this.func_146283_a(Collections.singletonList(EnumChatFormatting.GOLD+"Open Minecraft Forum Post in Browser"), x, y);
        }
        //Update Checker Hover Text
        if(x >= this.guiLeft-11 && x <= this.guiLeft-11+10 && y >= this.guiTop-11 && y <= this.guiTop-11+10){
            if(UpdateChecker.doneChecking && UpdateChecker.updateVersion > UpdateChecker.clientVersion){
                ArrayList list = new ArrayList();
                list.add(EnumChatFormatting.GOLD+"There is an Update available!");
                list.add(EnumChatFormatting.ITALIC+"You have: "+ModUtil.VERSION+", Newest: "+UpdateChecker.updateVersionS);
                list.addAll(this.fontRendererObj.listFormattedStringToWidth(EnumChatFormatting.ITALIC+"Updates include: "+UpdateChecker.changelog, TOOLTIP_SPLIT_LENGTH));
                list.add(EnumChatFormatting.GRAY+"Click this button to visit the download page!");
                this.func_146283_a(list, x, y);
            }
        }

        this.fontRendererObj.setUnicodeFlag(unicodeBefore);

        if(this.mousePressed){
            this.mousePressed = false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void keyTyped(char theChar, int key){
        if(key != 1 && this.searchField.isFocused()){
            this.searchField.textboxKeyTyped(theChar, key);

            if(this.currentIndexEntry instanceof BookletEntryAllSearch){
                BookletEntryAllSearch currentEntry = (BookletEntryAllSearch)this.currentIndexEntry;
                if(this.searchField.getText() != null && !this.searchField.getText().isEmpty()){
                    currentEntry.chapters.clear();

                    for(BookletChapter chapter : currentEntry.allChapters){
                        if(chapter.getLocalizedName().toLowerCase().contains(this.searchField.getText().toLowerCase())){
                            currentEntry.chapters.add(chapter);
                        }
                    }
                }
                else{
                    currentEntry.chapters = (ArrayList<BookletChapter>)currentEntry.allChapters.clone();
                }
                this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex, false);
            }
        }
        else{
            super.keyTyped(theChar, key);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3){
        this.searchField.mouseClicked(par1, par2, par3);

        if(par3 == 0 && this.currentChapter != null){
            this.mousePressed = true;
        }

        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button == this.buttonPreviouslyOpenedGui){
            if(this.parentScreen != null){
                mc.displayGuiScreen(this.parentScreen);
            }
        }
        else if(button == this.buttonUpdate){
            if(UpdateChecker.doneChecking && UpdateChecker.updateVersion > UpdateChecker.clientVersion){
                try{
                    if(Desktop.isDesktopSupported()){
                        Desktop.getDesktop().browse(new URI(UpdateChecker.DOWNLOAD_LINK));
                    }
                }
                catch(Exception e){
                    ModUtil.LOGGER.error("Something bad happened when trying to open a URL!", e);
                }
            }
        }
        else if(button == this.buttonTwitter){
            try{
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().browse(new URI("http://twitter.com/ActAddMod"));
                }
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something bad happened when trying to open a URL!", e);
            }
        }
        else if(button == this.buttonForum){
            try{
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().browse(new URI("http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/wip-mods/2374910-actually-additions-a-bunch-of-awesome-gadgets"));
                }
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something bad happened when trying to open a URL!", e);
            }
        }
        else if(button == this.buttonConfig){
            mc.displayGuiScreen(new GuiConfiguration(this));
        }
        else if(button == this.buttonAchievements){
            mc.displayGuiScreen(new GuiAAAchievements(this, mc.thePlayer.getStatFileWriter()));
        }
        else if(button == this.buttonForward){
            if(this.currentIndexEntry != null){
                if(this.currentPage != null){
                    BookletPage page = this.getNextPage(this.currentChapter, this.currentPage);
                    if(page != null){
                        this.currentPage = page;
                    }

                    this.buttonForward.visible = this.getNextPage(this.currentChapter, this.currentPage) != null;
                    this.buttonBackward.visible = this.getPrevPage(this.currentChapter, this.currentPage) != null;
                }
                else{
                    this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex+1, !(this.currentIndexEntry instanceof BookletEntryAllSearch));
                }
            }
        }
        else if(button == this.buttonBackward){
            if(this.currentIndexEntry != null){
                if(this.currentPage != null){
                    BookletPage page = this.getPrevPage(this.currentChapter, this.currentPage);
                    if(page != null){
                        this.currentPage = page;
                    }

                    this.buttonForward.visible = this.getNextPage(this.currentChapter, this.currentPage) != null;
                    this.buttonBackward.visible = this.getPrevPage(this.currentChapter, this.currentPage) != null;
                }
                else{
                    this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex-1, !(this.currentIndexEntry instanceof BookletEntryAllSearch));
                }
            }
        }
        else if(button == this.buttonPreviousScreen){
            if(this.currentChapter != null && this.currentChapter != InitBooklet.chapterIntro){
                this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex, true);
            }
            else{
                this.openIndexEntry(null, 1, true);
            }
        }
        else{
            int place = Util.arrayContains(this.chapterButtons, button);
            if(place >= 0){
                if(this.currentIndexEntry != null){
                    if(this.currentChapter == null){
                        if(place < this.currentIndexEntry.chapters.size()){
                            BookletChapter chap = currentIndexEntry.chapters.get(place+(this.chapterButtons.length*this.pageOpenInIndex-this.chapterButtons.length));
                            this.openChapter(chap, chap.pages[0]);
                        }
                    }
                }
                else{
                    if(place < InitBooklet.entries.size()){
                        this.openIndexEntry(InitBooklet.entries.get(place), 1, true);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        this.guiLeft = (this.width-this.xSize)/2;
        this.guiTop = (this.height-this.ySize)/2;

        this.buttonForward = new TexturedButton(0, this.guiLeft+this.xSize, this.guiTop+this.ySize+2, 164, 0, 18, 10);
        this.buttonList.add(this.buttonForward);

        this.buttonBackward = new TexturedButton(1, this.guiLeft-18, this.guiTop+this.ySize+2, 146, 0, 18, 10);
        this.buttonList.add(this.buttonBackward);

        this.buttonPreviousScreen = new TexturedButton(2, this.guiLeft+this.xSize/2-7, this.guiTop+this.ySize+2, 182, 0, 15, 10);
        this.buttonList.add(this.buttonPreviousScreen);

        this.buttonPreviouslyOpenedGui = new TexturedButton(3, this.guiLeft+this.xSize/3, this.guiTop+this.ySize+2, 245, 44, 11, 15);
        this.buttonList.add(this.buttonPreviouslyOpenedGui);

        this.buttonUpdate = new TexturedButton(4, this.guiLeft-11, this.guiTop-11, 245, 0, 11, 11);
        this.buttonUpdate.visible = UpdateChecker.doneChecking && UpdateChecker.updateVersion > UpdateChecker.clientVersion;
        this.buttonList.add(this.buttonUpdate);

        this.buttonTwitter = new TexturedButton(5, this.guiLeft, this.guiTop, 213, 0, 8, 8);
        this.buttonList.add(this.buttonTwitter);

        this.buttonForum = new TexturedButton(6, this.guiLeft, this.guiTop+10, 221, 0, 8, 8);
        this.buttonList.add(this.buttonForum);

        this.buttonAchievements = new TexturedButton(7, this.guiLeft+138, this.guiTop, 205, 0, 8, 8);
        this.buttonList.add(this.buttonAchievements);

        this.buttonConfig = new TexturedButton(8, this.guiLeft+138, this.guiTop+10, 197, 0, 8, 8);
        this.buttonList.add(this.buttonConfig);

        for(int i = 0; i < this.chapterButtons.length; i++){
            this.chapterButtons[i] = new IndexButton(9+i, guiLeft+15, guiTop+10+(i*12), 115, 10, "", this);
            this.buttonList.add(this.chapterButtons[i]);
        }

        this.searchField = new GuiTextField(this.fontRendererObj, guiLeft+148, guiTop+162, 66, 10);
        this.searchField.setMaxStringLength(30);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setCanLoseFocus(false);

        this.currentPage = null;
        this.currentChapter = null;
        this.currentIndexEntry = null;

        if(!PersistentClientData.getBoolean("BookAlreadyOpened")){
            this.openIndexEntry(InitBooklet.chapterIntro.entry, 1, true);
            this.openChapter(InitBooklet.chapterIntro, null);

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

        boolean buttonThere = UpdateChecker.doneChecking && UpdateChecker.updateVersion > UpdateChecker.clientVersion;
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
        PersistentClientData.saveBookPage(this.currentIndexEntry, this.currentChapter, this.currentPage, this.pageOpenInIndex);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    private BookletPage getNextPage(BookletChapter chapter, BookletPage currentPage){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == currentPage){
                if(i+1 < chapter.pages.length){
                    return chapter.pages[i+1];
                }
            }
        }
        return null;
    }

    private BookletPage getPrevPage(BookletChapter chapter, BookletPage currentPage){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == currentPage){
                if(i-1 >= 0){
                    return chapter.pages[i-1];
                }
            }
        }
        return null;
    }

    public void openChapter(BookletChapter chapter, BookletPage page){
        if(chapter == null){
            return;
        }

        this.searchField.setVisible(false);
        this.searchField.setFocused(false);
        this.searchField.setText("");

        this.currentChapter = chapter;
        this.currentPage = page != null && this.hasPage(chapter, page) ? page : chapter.pages[0];

        this.buttonForward.visible = this.getNextPage(chapter, this.currentPage) != null;
        this.buttonBackward.visible = this.getPrevPage(chapter, this.currentPage) != null;
        this.buttonPreviousScreen.visible = true;
        this.buttonPreviouslyOpenedGui.visible = this.parentScreen != null;

        for(GuiButton chapterButton : this.chapterButtons){
            chapterButton.visible = false;
        }
    }

    private boolean hasPage(BookletChapter chapter, BookletPage page){
        for(BookletPage aPage : chapter.pages){
            if(aPage == page){
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void openIndexEntry(BookletIndexEntry entry, int page, boolean resetTextField){
        this.searchField.setVisible(entry instanceof BookletEntryAllSearch);
        this.searchField.setFocused(entry instanceof BookletEntryAllSearch);
        if(resetTextField){
            this.searchField.setText("");
            if(entry instanceof BookletEntryAllSearch){
                entry.chapters = (ArrayList<BookletChapter>)((BookletEntryAllSearch)entry).allChapters.clone();
            }
        }

        this.currentPage = null;
        this.currentChapter = null;

        this.currentIndexEntry = entry;
        this.indexPageAmount = entry == null ? 1 : entry.chapters.size()/this.chapterButtons.length+1;
        this.pageOpenInIndex = entry == null ? 1 : (this.indexPageAmount <= page || page <= 0 ? this.indexPageAmount : page);

        this.buttonPreviousScreen.visible = entry != null;
        this.buttonForward.visible = this.pageOpenInIndex < this.indexPageAmount;
        this.buttonBackward.visible = this.pageOpenInIndex > 1;
        this.buttonPreviouslyOpenedGui.visible = this.parentScreen != null;

        for(int i = 0; i < this.chapterButtons.length; i++){
            IndexButton button = (IndexButton)this.chapterButtons[i];
            if(entry == null){
                boolean entryExists = InitBooklet.entries.size() > i;
                button.visible = entryExists;
                if(entryExists){
                    button.displayString = InitBooklet.entries.get(i).getLocalizedName();
                    button.chap = null;
                }
            }
            else{
                boolean entryExists = entry.chapters.size() > i+(this.chapterButtons.length*this.pageOpenInIndex-this.chapterButtons.length);
                button.visible = entryExists;
                if(entryExists){
                    BookletChapter chap = entry.chapters.get(i+(this.chapterButtons.length*this.pageOpenInIndex-this.chapterButtons.length));
                    button.displayString = chap.getLocalizedName();
                    button.chap = chap;
                }
            }
        }
    }

    private boolean isGimmicky(){
        return KeyUtil.isControlPressed() && KeyUtil.isShiftPressed() && KeyUtil.isAltPressed();
    }

    private static class IndexButton extends GuiButton{

        public BookletChapter chap;
        private GuiBooklet gui;

        public IndexButton(int id, int x, int y, int width, int height, String text, GuiBooklet gui){
            super(id, x, y, width, height, text);
            this.gui = gui;
        }

        @Override
        public void drawButton(Minecraft minecraft, int mouseX, int mouseY){
            if(this.visible){
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition+this.width && mouseY < this.yPosition+this.height;
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                this.mouseDragged(minecraft, mouseX, mouseY);
                int color = 0;
                if(this.field_146123_n){
                    color = 38144;
                }

                int textOffsetX = 0;
                if(this.chap != null){
                    if(this.chap.displayStack != null){
                        GL11.glPushMatrix();
                        BookletPage.renderItem(this.gui, this.chap.displayStack, this.xPosition-5, this.yPosition, 0.725F);
                        GL11.glPopMatrix();
                        textOffsetX = 8;
                    }
                }

                this.gui.fontRendererObj.drawString((this.field_146123_n ? EnumChatFormatting.UNDERLINE : "")+this.displayString, this.xPosition+textOffsetX, this.yPosition+(this.height-8)/2, color);
            }
        }
    }

    public static class TexturedButton extends GuiButton{

        public int texturePosX;
        public int texturePosY;

        public TexturedButton(int id, int x, int y, int texturePosX, int texturePosY, int width, int height){
            super(id, x, y, width, height, "");
            this.texturePosX = texturePosX;
            this.texturePosY = texturePosY;
        }

        public void setTexturePos(int x, int y){
            this.texturePosX = x;
            this.texturePosY = y;
        }

        @Override
        public void drawButton(Minecraft minecraft, int x, int y){
            if(this.visible){
                minecraft.getTextureManager().bindTexture(resLoc);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
                int k = this.getHoverState(this.field_146123_n);
                if(k == 0){
                    k = 1;
                }
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, this.texturePosX, this.texturePosY-this.height+k*this.height, this.width, this.height);
                this.mouseDragged(minecraft, x, y);
            }
        }
    }
}