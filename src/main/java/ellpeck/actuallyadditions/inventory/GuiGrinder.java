package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityGrinder;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiGrinder");
    private static final ResourceLocation resLocDouble = AssetUtil.getGuiLocation("guiGrinderDouble");
    private TileEntityGrinder tileGrinder;
    private boolean isDouble;

    public GuiGrinder(InventoryPlayer inventory, TileEntityBase tile, boolean isDouble){
        super(new ContainerGrinder(inventory, tile, isDouble));
        this.tileGrinder = (TileEntityGrinder)tile;
        this.isDouble = isDouble;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(this.isDouble ? resLocDouble : resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileGrinder.coalTime > 0){
            int i = this.tileGrinder.getCoalTimeToScale(15);
            this.drawTexturedModalRect(this.guiLeft+(isDouble ? 80 : 51), this.guiTop+5+14-i, 176, 44+14-i, 14, i);
        }
        if(this.tileGrinder.firstCrushTime > 0){
            int i = this.tileGrinder.getFirstTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+(isDouble ? 51 : 80), this.guiTop+40, 176, 0, 24, i);
        }
        if(this.isDouble){
            if(this.tileGrinder.secondCrushTime > 0){
                int i = this.tileGrinder.getSecondTimeToScale(23);
                this.drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 40, 176, 22, 24, i);
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
    }
}