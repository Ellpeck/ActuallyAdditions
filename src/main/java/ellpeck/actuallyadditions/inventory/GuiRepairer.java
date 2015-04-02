package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityItemRepairer;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiRepairer extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiRepairer");
    private TileEntityItemRepairer tileRepairer;

    public GuiRepairer(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerRepairer(inventory, tile));
        this.tileRepairer = (TileEntityItemRepairer)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileRepairer.coalTime > 0){
            int i = this.tileRepairer.getCoalTimeToScale(15);
            this.drawTexturedModalRect(this.guiLeft+80, this.guiTop+5+14-i, 176, 44+14-i, 14, i);
        }
        if(TileEntityItemRepairer.canBeRepaired(this.tileRepairer.slots[TileEntityItemRepairer.SLOT_INPUT])){
            int i = this.tileRepairer.getItemDamageToScale(22);
            this.drawTexturedModalRect(this.guiLeft+73, this.guiTop+52, 176, 28, i, 16);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
    }
}