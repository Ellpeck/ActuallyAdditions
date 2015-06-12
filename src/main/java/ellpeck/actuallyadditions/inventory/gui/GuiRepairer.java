package ellpeck.actuallyadditions.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.ContainerRepairer;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityItemRepairer;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

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
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.tileRepairer.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileRepairer.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
            int i = this.tileRepairer.getEnergyScaled(83);
            drawTexturedModalRect(this.guiLeft+28, this.guiTop+89-i, 176, 44, 16, i);
        }
        if(TileEntityItemRepairer.canBeRepaired(this.tileRepairer.slots[TileEntityItemRepairer.SLOT_INPUT])){
            int i = this.tileRepairer.getItemDamageToScale(22);
            this.drawTexturedModalRect(this.guiLeft+73, this.guiTop+52, 176, 28, i, 16);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        String text = this.tileRepairer.getEnergyStored(ForgeDirection.UNKNOWN) + "/" + this.tileRepairer.getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF";
        if(x >= guiLeft+28 && y >= guiTop+6 && x <= guiLeft+43 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text), x, y);
        }
    }
}