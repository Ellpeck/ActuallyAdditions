package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFurnaceDouble;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiFurnaceDouble extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiFurnaceDouble");
    private TileEntityFurnaceDouble tileFurnace;

    public GuiFurnaceDouble(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFurnaceDouble(inventory, tile));
        this.tileFurnace = (TileEntityFurnaceDouble)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameAndInventoryString(this.fontRendererObj, xSize, 93-5, -10, this.tileFurnace.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileFurnace.coalTime > 0){
            int i = this.tileFurnace.getCoalTimeToScale(15);
            this.drawTexturedModalRect(this.guiLeft+80, this.guiTop+5+14-i, 176, 44+14-i, 14, i);
        }
        if(this.tileFurnace.firstSmeltTime > 0){
            int i = this.tileFurnace.getFirstTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+51, this.guiTop+40, 176, 0, 24, i);
        }
        if(this.tileFurnace.secondSmeltTime > 0){
            int i = this.tileFurnace.getSecondTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 40, 176, 22, 24, i);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
    }
}