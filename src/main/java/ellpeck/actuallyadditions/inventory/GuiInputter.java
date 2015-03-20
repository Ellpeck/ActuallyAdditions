package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.PacketInputterButton;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiInputter extends GuiContainer{

    private static final ResourceLocation resLoc = Util.getGuiLocation("guiInputter");
    private TileEntityInputter tileInputter;

    private int x;
    private int y;
    private int z;
    private World world;

    private SmallerButton buttonSlotPutP;
    private SmallerButton buttonSlotPullP;
    private SmallerButton buttonSlotPutM;
    private SmallerButton buttonSlotPullM;

    public static final String[] sideString = new String[]{
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.disabled"),
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.up"),
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.down"),
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.north"),
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.east"),
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.south"),
            StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.west")};

    public GuiInputter(InventoryPlayer inventory, TileEntityBase tile, int x, int y, int z, World world){
        super(new ContainerInputter(inventory, tile));
        this.tileInputter = (TileEntityInputter)tile;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        SmallerButton buttonSidePutP = new SmallerButton(0, guiLeft + 70, guiTop + 43, ">");
        SmallerButton buttonSidePutM = new SmallerButton(1, guiLeft + 5, guiTop + 43, "<");
        buttonSlotPutP = new SmallerButton(2, guiLeft + 70, guiTop + 64, "+");
        buttonSlotPutM = new SmallerButton(3, guiLeft + 5, guiTop + 64, "-");

        SmallerButton buttonSidePullP = new SmallerButton(4, guiLeft + 155, guiTop + 43, ">");
        SmallerButton buttonSidePullM = new SmallerButton(5, guiLeft + 90, guiTop + 43, "<");
        buttonSlotPullP = new SmallerButton(6, guiLeft+ 155, guiTop + 64, "+");
        buttonSlotPullM = new SmallerButton(7, guiLeft + 90, guiTop + 64, "-");

        this.buttonList.add(buttonSidePutP);
        this.buttonList.add(buttonSlotPutP);
        this.buttonList.add(buttonSidePullP);
        this.buttonList.add(buttonSlotPullP);
        this.buttonList.add(buttonSidePutM);
        this.buttonList.add(buttonSlotPutM);
        this.buttonList.add(buttonSidePullM);
        this.buttonList.add(buttonSlotPullM);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Util.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        this.fontRendererObj.drawString(StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.put"), guiLeft + 22 + 3, guiTop + 32, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.pull"), guiLeft + 107 + 3, guiTop + 32, 4210752);

        this.fontRendererObj.drawString(sideString[tileInputter.sideToPut+1], guiLeft + 24 + 1, guiTop + 45 + 3, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.slot") + " " + (tileInputter.slotToPut == -1 ? StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.all") : tileInputter.slotToPut).toString(), guiLeft + 24 + 3, guiTop + 66 + 3, 4210752);

        this.fontRendererObj.drawString(sideString[tileInputter.sideToPull+1], guiLeft + 109 + 1, guiTop + 45 + 3, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.slot") + " " + (tileInputter.slotToPull == -1 ? StatCollector.translateToLocal("info." + Util.MOD_ID_LOWER + ".gui.all") : tileInputter.slotToPull).toString(), guiLeft + 109 + 3, guiTop + 66 + 3, 4210752);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        this.buttonSlotPullP.enabled = this.tileInputter.placeToPullSlotAmount > 0;
        this.buttonSlotPullM.enabled = this.tileInputter.placeToPullSlotAmount > 0;

        this.buttonSlotPutP.enabled = this.tileInputter.placeToPutSlotAmount > 0;
        this.buttonSlotPutM.enabled = this.tileInputter.placeToPutSlotAmount > 0;
    }

    @Override
    public void actionPerformed(GuiButton button){
        PacketHandler.theNetwork.sendToServer(new PacketInputterButton(x, y, z, world, button.id));
    }

    public class SmallerButton extends GuiButton{

        private final ResourceLocation resLoc = Util.getGuiLocation("guiInputter");

        public SmallerButton(int id, int x, int y, String display){
            super(id, x, y, 16, 16, display);
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y){
            if (this.visible){
                FontRenderer renderer = mc.fontRenderer;
                mc.getTextureManager().bindTexture(resLoc);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
                int k = this.getHoverState(this.field_146123_n);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, k*16, 16, 16);
                this.mouseDragged(mc, x, y);

                int color = 14737632;
                if (packedFGColour != 0) color = packedFGColour;
                else if (!this.enabled) color = 10526880;
                else if (this.field_146123_n) color = 16777120;

                this.drawCenteredString(renderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height-8) / 2, color);
            }
        }
    }

}