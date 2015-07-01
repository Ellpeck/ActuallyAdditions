package ellpeck.actuallyadditions.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.ContainerCoalGenerator;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityCoalGenerator;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiCoalGenerator extends GuiContainer{

    private TileEntityCoalGenerator generator;

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiCoalGenerator");

    public GuiCoalGenerator(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerCoalGenerator(inventory, tile));
        this.generator = (TileEntityCoalGenerator)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.generator.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.generator.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
            int i = this.generator.getEnergyScaled(83);
            drawTexturedModalRect(this.guiLeft+43, this.guiTop+89-i, 176, 0, 16, i);
        }

        if(this.generator.currentBurnTime > 0){
            int i = this.generator.getBurningScaled(13);
            this.drawTexturedModalRect(guiLeft+87, guiTop+27+12-i, 176, 96-i, 14, i);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        String text1 = this.generator.storage.getEnergyStored() + "/" + this.generator.storage.getMaxEnergyStored() + " RF";
        if(x >= guiLeft+43 && y >= guiTop+6 && x <= guiLeft+58 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text1), x, y);
        }
    }
}