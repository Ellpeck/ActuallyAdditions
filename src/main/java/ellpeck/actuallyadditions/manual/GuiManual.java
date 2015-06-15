package ellpeck.actuallyadditions.manual;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiManual extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiManual");

    private FontRenderer infoTextRenderer;

    private float currentScroll;
    private boolean isScrolling;
    private boolean wasClicking;

    public GuiManual(){
        super(new ContainerManual());

        this.xSize = 256;
        this.ySize = 244;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        if(((ContainerManual)this.inventorySlots).needsScrollBars()){
            boolean flag = Mouse.isButtonDown(0);
            int k = this.guiLeft;
            int l = this.guiTop;
            int i1 = k+78;
            int j1 = l+5;
            int k1 = i1+12;
            int l1 = j1+232;

            if(!this.wasClicking && flag && x >= i1 && y >= j1 && x < k1 && y < l1) this.isScrolling = true;
            if(!flag) this.isScrolling = false;
            this.wasClicking = flag;

            if(this.isScrolling){
                this.currentScroll = ((float)(y-j1)-7.5F)/((float)(l1-j1)-15.0F);
                if(this.currentScroll < 0.0F){
                    this.currentScroll = 0.0F;
                }
                if(this.currentScroll > 1.0F){
                    this.currentScroll = 1.0F;
                }
                ((ContainerManual)this.inventorySlots).scrollTo(this.currentScroll);
            }
        }
        super.drawScreen(x, y, f);
    }

    @Override
    public void handleMouseInput(){
        super.handleMouseInput();
        if(((ContainerManual)this.inventorySlots).needsScrollBars()){
            int i = Mouse.getEventDWheel();

            if(i != 0){
                int j = ((ContainerManual)this.inventorySlots).inventory.slots.length/4-13;

                if(i > 0) i = 1;
                if(i < 0) i = -1;
                this.currentScroll = (float)((double)this.currentScroll-(double)i/(double)j);
                if(this.currentScroll < 0.0F) this.currentScroll = 0.0F;

                if(this.currentScroll > 1.0F) this.currentScroll = 1.0F;
                ((ContainerManual)this.inventorySlots).scrollTo(this.currentScroll);
            }
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        this.infoTextRenderer = new FontRenderer(this.mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.mc.renderEngine, true);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, "container."+ModUtil.MOD_ID_LOWER+".manual");

        ManualItems.InfoTab tab = ManualItems.getTabFromStack(this.inventorySlots.getSlot(0).getStack());
        if(tab != null){
            this.drawCenterString(tab.title, 160, 8);
            this.fontRendererObj.drawSplitString(tab.extraForRecipe, 95, 80, 155, StringUtil.DECIMAL_COLOR_GRAY_TEXT);

            this.infoTextRenderer.drawSplitString(tab.text, 95, (tab.extraForRecipe == null || tab.extraForRecipe.length() <= 0 ? 80 : 100), 155, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        }
    }

    private void drawCenterString(String text, int xPos, int yPos){
        this.fontRendererObj.drawString(text, xPos - this.fontRendererObj.getStringWidth(text)/2, yPos, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resLoc);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
        this.drawTexturedModalRect(this.guiLeft+78, this.guiTop+5 + (int)((float)(this.guiTop+5+232-this.guiTop+5-25) * this.currentScroll), 232+(((ContainerManual)this.inventorySlots).needsScrollBars() ? 0 : 12), 0, 12, 15);
    }
}