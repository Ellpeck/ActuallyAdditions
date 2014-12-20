package ellpeck.gemification.inventory.gui;

import ellpeck.gemification.inventory.container.ContainerCrucibleFire;
import ellpeck.gemification.tile.TileEntityCrucibleFire;
import ellpeck.gemification.util.Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCrucibleFire extends GuiContainer{

    @SuppressWarnings("all")
    private TileEntityCrucibleFire tileCrucibleFire;

    public static final ResourceLocation resLoc = new ResourceLocation(Util.MOD_ID, "textures/gui/guiCrucibleFire.png");

    public GuiCrucibleFire(InventoryPlayer inventoryPlayer, TileEntityCrucibleFire tileCrucibleFire){
        super(new ContainerCrucibleFire(inventoryPlayer, tileCrucibleFire));
        this.tileCrucibleFire = tileCrucibleFire;

        this.xSize = 176;
        this.ySize = 116;
    }

    public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if(this.tileCrucibleFire.burnTime > 0 && this.tileCrucibleFire.burnTimeOfItem > 0) {
            int i = this.tileCrucibleFire.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(guiLeft + 96, guiTop + 10 + 12 - i, 176, 12 - i, 14, i + 1);
        }
    }
}
