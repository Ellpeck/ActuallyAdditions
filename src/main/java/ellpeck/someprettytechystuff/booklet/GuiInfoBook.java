package ellpeck.someprettytechystuff.booklet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiInfoBook extends GuiScreen{

    public static final ResourceLocation resLoc = new ResourceLocation(Util.MOD_ID, "textures/gui/guiInfoBook.png");
    public static final ResourceLocation fontLocation = new ResourceLocation("textures/font/ascii.png");

    public final int xSize = 180;
    public final int ySize = 180;

    @SideOnly(Side.CLIENT)
    public final FontRenderer thisRenderer = new FontRenderer(Minecraft.getMinecraft().gameSettings, fontLocation, Minecraft.getMinecraft().renderEngine, true);

    public Chapter mainChapter = new Chapter(-1, "main", 1, false);

    public int currentPage = 0;
    public Chapter currentChapter = mainChapter;

    public ChangePageButton nextPageButton;
    public ChangePageButton prevPageButton;
    public ChangePageButton mainPageButton;

    @SuppressWarnings("unused")
    public GuiInfoBook(EntityPlayer player){

    }

    @SuppressWarnings("all")
    public void initGui(){
        this.buttonList.clear();

        int xPos = (this.width-this.xSize)/2;
        int yPos = (this.height-this.ySize)/2;

        this.addMainChapterButtons();
        this.nextPageButton = new ChangePageButton(-3, xPos+180, yPos+170);
        this.prevPageButton = new ChangePageButton(-2, xPos-18, yPos+170);
        this.mainPageButton = new ChangePageButton(-1, xPos, yPos-15);
        this.buttonList.add(nextPageButton);
        this.buttonList.add(prevPageButton);
        this.buttonList.add(mainPageButton);

        this.updateButtons();
    }

    @SuppressWarnings("all")
    public void addMainChapterButtons(){
        int xPos = (this.width-this.xSize)/2;
        int yPos = (this.height-this.ySize)/2;

        int size = ChapterList.chapterList.size();
        for(int i = 0; i < size; i++) {
            this.buttonList.add(new InvisiButton(i, xPos + 20, yPos + 15 + 11 * i, 145, 10, StatCollector.translateToLocal("infoBook." + ChapterList.chapterList.get(i).name + ".title"), this.thisRenderer));
        }
    }

    public void updateButtons(){
        this.nextPageButton.visible = this.currentPage < this.currentChapter.pageAmount-1;
        this.prevPageButton.visible = this.currentPage > 0;
        this.mainPageButton.visible = this.currentChapter != this.mainChapter;
        for(int i = 0; i < ChapterList.chapterList.size(); i++){
            ((GuiButton)this.buttonList.get(i)).visible = this.currentChapter == mainChapter;
        }
    }

    @SuppressWarnings("static-access")
    public void actionPerformed(GuiButton button){
        if(button == this.nextPageButton) this.currentPage++;
        else if(button == this.prevPageButton) this.currentPage--;
        else if(button == this.mainPageButton){
            this.currentPage = 0;
            this.currentChapter = this.mainChapter;
        }
        else this.currentChapter = ChapterList.chapterList.get(button.id);
        this.updateButtons();
    }

    public void drawScreen(int x, int y, float f){
        this.drawDefaultBackground();
        GL11.glColor4f(1F, 1F, 1F, 1F);

        int xPos = (this.width-this.xSize)/2;
        int yPos = (this.height-this.ySize)/2;

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);

        this.drawPageContents();

        super.drawScreen(x, y, f);
    }

    public void drawPageContents(){
        int xPos = (this.width-this.xSize)/2;
        int yPos = (this.height-this.ySize)/2;

        if(this.currentChapter != this.mainChapter) this.thisRenderer.drawSplitString(this.currentChapter.pageTexts[this.currentPage], xPos + 15, yPos + 14, 150, 0);
    }

    @SideOnly(Side.CLIENT)
    static class ChangePageButton extends GuiButton{
        /**
         * Type of the button
         * -3: Next Page
         * -2: Previous Page
         * -1: Back to main Page
         */
        private final int buttonType;

        public ChangePageButton(int ID, int x, int y){
            super(ID, x, y, 18, ID == -1 ? 14 : 10, "");
            this.buttonType = ID;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY){
            if (this.visible){
                boolean isHoverOver = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(GuiInfoBook.resLoc);

                int posX = 0;
                int posY = 180;
                if(this.buttonType == -2) posY += 10;
                else if(this.buttonType == -1) posY += 20;
                if(isHoverOver) posX += 18;
                this.drawTexturedModalRect(this.xPosition, this.yPosition, posX, posY, 18, this.buttonType == -1 ? 14 : 10);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    static class InvisiButton extends GuiButton{
        private FontRenderer renderer;
        public InvisiButton(int ID, int x, int y, int width, int height, String text, FontRenderer renderer){
            super(ID, x, y, width, height, text);
            this.renderer = renderer;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY){
            if (this.visible){
                boolean isHoverOver = false;
                if(mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) isHoverOver = true;
                this.renderer.drawString((isHoverOver ? ((char) 167 + "2" + (char) 167 + "n") : "") + this.displayString, this.xPosition, this.yPosition + (this.height - 8) / 2, 0);
            }
        }
    }

    public boolean doesGuiPauseGame(){
        return false;
    }
}
