package ellpeck.actuallyadditions.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.ContainerXPSolidifier;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.gui.PacketGuiButton;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityXPSolidifier;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiXPSolidifier extends GuiContainer{

    private TileEntityXPSolidifier solidifier;

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiXPSolidifier");

    private int x;
    private int y;
    private int z;
    private World world;

    public GuiXPSolidifier(InventoryPlayer inventory, TileEntityBase tile, int x, int y, int z, World world){
        super(new ContainerXPSolidifier(inventory, tile));
        this.solidifier = (TileEntityXPSolidifier)tile;
        this.xSize = 176;
        this.ySize = 93+86;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        GuiButton buttonOne = new GuiInputter.SmallerButton(0, guiLeft+62, guiTop+44, "1");
        GuiButton buttonFive = new GuiInputter.SmallerButton(1, guiLeft+80, guiTop+44, "5");
        GuiButton buttonTen = new GuiInputter.SmallerButton(2, guiLeft+99, guiTop+44, "10");
        GuiButton buttonTwenty = new GuiInputter.SmallerButton(3, guiLeft+62, guiTop+61, "20");
        GuiButton buttonThirty = new GuiInputter.SmallerButton(4, guiLeft+80, guiTop+61, "30");
        GuiButton buttonForty = new GuiInputter.SmallerButton(5, guiLeft+99, guiTop+61, "40");
        GuiButton buttonFifty = new GuiInputter.SmallerButton(6, guiLeft+62, guiTop+78, "50");
        GuiButton buttonSixtyFour = new GuiInputter.SmallerButton(7, guiLeft+80, guiTop+78, "64");
        GuiButton buttonAll = new GuiInputter.SmallerButton(8, guiLeft+99, guiTop+78, "All");

        this.buttonList.add(buttonOne);
        this.buttonList.add(buttonFive);
        this.buttonList.add(buttonTen);
        this.buttonList.add(buttonTwenty);
        this.buttonList.add(buttonThirty);
        this.buttonList.add(buttonForty);
        this.buttonList.add(buttonFifty);
        this.buttonList.add(buttonSixtyFour);
        this.buttonList.add(buttonAll);
    }

    @Override
    public void actionPerformed(GuiButton button){
        PacketHandler.theNetwork.sendToServer(new PacketGuiButton(x, y, z, world, button.id, Minecraft.getMinecraft().thePlayer));
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.solidifier.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        this.drawCenteredString(this.fontRendererObj, Integer.toString(this.solidifier.amount), guiLeft+88, guiTop+30, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
    }
}