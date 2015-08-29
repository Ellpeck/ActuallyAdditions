/*
 * This file ("GuiBooklet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui.booklet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.GuiConfiguration;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiBooklet extends GuiScreen{

    public static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiBooklet");
    public FontRenderer unicodeRenderer;

    public int xSize;
    public int ySize;
    public int guiLeft;
    public int guiTop;

    public IBookletPage currentPage;
    public BookletChapter currentChapter;
    public BookletIndexEntry currentIndexEntry;
    public int pageOpenInIndex;
    public int indexPageAmount;

    private GuiTextField searchField;

    private static final int BUTTON_ACHIEVEMENTS_ID = -2;
    private static final int BUTTON_CONFIG_ID = -1;
    private static final int BUTTON_FORWARD_ID = 0;
    private static final int BUTTON_BACK_ID = 1;
    private static final int BUTTON_RETURN_ID = 2;
    private static final int CHAPTER_BUTTONS_START = 3;

    public GuiBooklet(){
        this.xSize = 146;
        this.ySize = 180;
    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.searchField.updateCursorCounter();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void keyTyped(char theChar, int key){
        if(key != 1 && this.searchField.isFocused()){
            this.searchField.textboxKeyTyped(theChar, key);

            if(this.currentIndexEntry == InitBooklet.allAndSearch){
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
        super.mouseClicked(par1, par2, par3);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        this.guiLeft = (this.width-this.xSize)/2;
        this.guiTop = (this.height-this.ySize)/2;

        this.unicodeRenderer = new FontRenderer(this.mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.mc.renderEngine, true);

        this.buttonList.add(new TexturedButton(BUTTON_FORWARD_ID, this.guiLeft+this.xSize, this.guiTop+this.ySize+2, 164, 0, 18, 10));
        this.buttonList.add(new TexturedButton(BUTTON_BACK_ID, this.guiLeft-18, this.guiTop+this.ySize+2, 146, 0, 18, 10));
        this.buttonList.add(new TexturedButton(BUTTON_RETURN_ID, this.guiLeft+this.xSize/2-7, this.guiTop+this.ySize+2, 182, 0, 15, 10));

        for(int i = 0; i < 12; i++){
            this.buttonList.add(new IndexButton(this.unicodeRenderer, CHAPTER_BUTTONS_START+i, guiLeft+13, guiTop+15+(i*11), 120, 10, ""));
        }

        this.buttonList.add(new TexturedButton(BUTTON_ACHIEVEMENTS_ID, this.guiLeft+138, this.guiTop, 205, 0, 8, 8));
        this.buttonList.add(new TexturedButton(BUTTON_CONFIG_ID, this.guiLeft+138, this.guiTop+10, 197, 0, 8, 8));

        this.searchField = new GuiTextField(this.unicodeRenderer, guiLeft+148, guiTop+162, 66, 10);
        this.searchField.setMaxStringLength(30);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setVisible(false);

        this.currentPage = null;
        this.currentChapter = null;
        this.currentIndexEntry = null;

        this.openIndexEntry(null, 1, true);
    }

    private GuiButton getButton(int id){
        return (GuiButton)this.buttonList.get(id);
    }

    @Override
    public void renderToolTip(ItemStack stack, int x, int y){
        super.renderToolTip(stack, x, y);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if(this.currentIndexEntry == InitBooklet.allAndSearch && this.currentChapter == null){
            this.drawTexturedModalRect(this.guiLeft+146, this.guiTop+160, 146, 80, 70, 14);
        }

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

        if(this.currentIndexEntry != null){
            if(this.currentChapter != null && this.currentPage != null){
                this.drawCenteredString(this.unicodeRenderer, this.currentPage.getID()+"/"+this.currentChapter.pages.length, this.guiLeft+this.xSize/2, this.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
                this.currentPage.renderPre(this, x, y);
            }
            else{
                this.drawCenteredString(this.unicodeRenderer, this.pageOpenInIndex+"/"+this.indexPageAmount, this.guiLeft+this.xSize/2, this.guiTop+172, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }

        super.drawScreen(x, y, f);
        this.searchField.drawTextBox();

        //Achievements Hover Text
        if(x >= this.guiLeft+138 && x <= this.guiLeft+138+7 && y >= this.guiTop && y <= this.guiTop+7){
            this.func_146283_a(Collections.singletonList("Show Achievements"), x, y);
        }
        //Config Hover Text
        if(x >= this.guiLeft+138 && x <= this.guiLeft+138+7 && y >= this.guiTop+10 && y <= this.guiTop+10+7){
            this.func_146283_a(Collections.singletonList("Show Config"), x, y);
        }

        if(this.currentIndexEntry != null && this.currentChapter != null && this.currentPage != null){
            this.currentPage.render(this, x, y);
        }
    }

    private IBookletPage getNextPage(BookletChapter chapter, IBookletPage currentPage){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == currentPage){
                if(i+1 < chapter.pages.length){
                    return chapter.pages[i+1];
                }
            }
        }
        return null;
    }

    private IBookletPage getPrevPage(BookletChapter chapter, IBookletPage currentPage){
        for(int i = 0; i < chapter.pages.length; i++){
            if(chapter.pages[i] == currentPage){
                if(i-1 >= 0){
                    return chapter.pages[i-1];
                }
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button.id == BUTTON_CONFIG_ID){
            mc.displayGuiScreen(new GuiConfiguration(this));
        }
        else if(button.id == BUTTON_ACHIEVEMENTS_ID){
            mc.displayGuiScreen(new GuiAAAchievements(this, mc.thePlayer.getStatFileWriter()));
        }
        else if(button.id == BUTTON_FORWARD_ID){
            if(this.currentIndexEntry != null){
                if(this.currentPage != null){
                    IBookletPage page = this.getNextPage(this.currentChapter, this.currentPage);
                    if(page != null) this.currentPage = page;
                }
                else{
                    this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex+1, true);
                }
            }
        }
        else if(button.id == BUTTON_BACK_ID){
            if(this.currentIndexEntry != null){
                if(this.currentPage != null){
                    IBookletPage page = this.getPrevPage(this.currentChapter, this.currentPage);
                    if(page != null) this.currentPage = page;
                }
                else{
                    this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex-1, true);
                }
            }
        }
        else if(button.id == BUTTON_RETURN_ID){
            if(this.currentChapter != null){
                this.openIndexEntry(this.currentIndexEntry, this.pageOpenInIndex, true);
            }
            else{
                this.openIndexEntry(null, 1, true);
            }
        }
        else if(button.id >= CHAPTER_BUTTONS_START){
            int actualButton = button.id-CHAPTER_BUTTONS_START;
            if(this.currentIndexEntry != null){
                if(this.currentChapter == null){
                    if(actualButton < this.currentIndexEntry.chapters.size()){
                        this.openChapter(currentIndexEntry.chapters.get(actualButton+(12*this.pageOpenInIndex-12)));
                    }
                }
            }
            else{
                if(actualButton < InitBooklet.entries.size()){
                    this.openIndexEntry(InitBooklet.entries.get(actualButton), 1, true);
                }
            }
        }

        if(button.id == BUTTON_FORWARD_ID || button.id == BUTTON_BACK_ID){
            if(this.currentChapter != null && this.currentIndexEntry != null){
                if(this.currentPage != null){
                    this.getButton(BUTTON_FORWARD_ID).visible = this.getNextPage(this.currentChapter, this.currentPage) != null;
                    this.getButton(BUTTON_BACK_ID).visible = this.getPrevPage(this.currentChapter, this.currentPage) != null;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void openIndexEntry(BookletIndexEntry entry, int page, boolean resetTextField){
        if(resetTextField){
            this.searchField.setVisible(entry == InitBooklet.allAndSearch);
            this.searchField.setFocused(entry == InitBooklet.allAndSearch);
            this.searchField.setText("");
            if(entry == InitBooklet.allAndSearch){
                entry.chapters = (ArrayList<BookletChapter>)((BookletEntryAllSearch)entry).allChapters.clone();
            }
        }

        this.currentPage = null;
        this.currentChapter = null;

        this.currentIndexEntry = entry;
        this.indexPageAmount = entry == null ? 1 : entry.chapters.size()/12+1;
        this.pageOpenInIndex = entry == null ? 1 : (this.indexPageAmount <= page ? this.indexPageAmount : page);

        this.getButton(BUTTON_RETURN_ID).visible = entry != null;
        this.getButton(BUTTON_FORWARD_ID).visible = this.pageOpenInIndex < this.indexPageAmount;
        this.getButton(BUTTON_BACK_ID).visible = this.pageOpenInIndex > 1;

        for(int i = 0; i < 12; i++){
            GuiButton button = this.getButton(CHAPTER_BUTTONS_START+i);
            if(entry == null){
                boolean entryExists = InitBooklet.entries.size() > i+(12*this.pageOpenInIndex-12);
                button.visible = entryExists;
                if(entryExists){
                    button.displayString = InitBooklet.entries.get(i+(12*this.pageOpenInIndex-12)).getLocalizedName();
                }
            }
            else{
                boolean entryExists = entry.chapters.size() > i+(12*this.pageOpenInIndex-12);
                button.visible = entryExists;
                if(entryExists){
                    button.displayString = entry.chapters.get(i+(12*this.pageOpenInIndex-12)).getLocalizedName();
                }
            }
        }
    }

    private void openChapter(BookletChapter chapter){
        if(chapter == null) return;

        this.searchField.setVisible(false);
        this.searchField.setFocused(false);
        this.searchField.setText("");

        this.currentChapter = chapter;
        this.currentPage = currentChapter.pages[0];

        this.getButton(BUTTON_FORWARD_ID).visible = this.getNextPage(chapter, currentPage) != null;
        this.getButton(BUTTON_BACK_ID).visible = false;

        for(int i = 0; i < 12; i++){
            GuiButton button = this.getButton(CHAPTER_BUTTONS_START+i);
            button.visible = false;
        }
    }

    private static class IndexButton extends GuiButton{

        private FontRenderer renderer;

        public IndexButton(FontRenderer renderer, int id, int x, int y, int width, int height, String text){
            super(id, x, y, width, height, text);
            this.renderer = renderer;
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

                this.renderer.drawString((this.field_146123_n ? StringUtil.UNDERLINE : "")+this.displayString, this.xPosition, this.yPosition+(this.height-8)/2, color);
            }
        }
    }

    private static class TexturedButton extends GuiButton{

        private int texturePosX;
        private int texturePosY;

        public TexturedButton(int id, int x, int y, int texturePosX, int texturePosY, int width, int height){
            super(id, x, y, width, height, "");
            this.texturePosX = texturePosX;
            this.texturePosY = texturePosY;
        }

        @Override
        public void drawButton(Minecraft minecraft, int x, int y){
            if(this.visible){
                minecraft.getTextureManager().bindTexture(resLoc);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
                int k = this.getHoverState(this.field_146123_n);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, this.texturePosX, this.texturePosY-this.height+k*this.height, this.width, this.height);
                this.mouseDragged(minecraft, x, y);
            }
        }
    }
}