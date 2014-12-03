package ellpeck.gemification.container;

import ellpeck.gemification.Gemification;
import ellpeck.gemification.Util;
import ellpeck.gemification.tile.TileEntityCrucible;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class GuiCrucible extends GuiContainer{

    private TileEntityCrucible tileCrucible;

    public static final ResourceLocation resLoc = new ResourceLocation(Gemification.MOD_ID, "textures/gui/guiCrucible.png");

    public GuiCrucible(InventoryPlayer inventoryPlayer, TileEntityCrucible tileCrucible) {
        super(new ContainerCrucible(inventoryPlayer, tileCrucible));
        this.tileCrucible = tileCrucible;

        this.xSize = 176;
        this.ySize = 195;
    }

    public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if(this.tileCrucible.currentProcessTime > 0){
            int i = this.tileCrucible.getCraftProcessScaled(32);
            this.drawTexturedModalRect(guiLeft + 107, guiTop + 55, 176, 0, i, 45);
        }

        if(this.tileCrucible.currentFluidID == Util.fluidWater.ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 47, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(0).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 59, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(1).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 188, 59, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(2).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 200, 59, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(3).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 59+12, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(4).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 188, 59+12, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(5).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 200, 59+12, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(6).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 59+24, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(7).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 188, 59+24, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(8).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 200, 59+24, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(9).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 59+36, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(10).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 188, 59+36, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(11).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 200, 59+36, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(12).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 59+48, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(13).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 188, 59+48, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(14).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 200, 59+48, 12, 12);
        else if(this.tileCrucible.currentFluidID == Util.gemList.get(15).ID) this.drawTexturedModalRect(guiLeft + 141, guiTop + 7, 176, 59+60, 12, 12);
    }

    @SuppressWarnings("static-access")
    public void drawScreen(int par1, int par2, float par3){
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL11.GL_LIGHTING);
        itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), tileCrucible.output, guiLeft + 112, guiTop + 65);
        GL11.glDisable(GL11.GL_LIGHTING);
        RenderHelper.disableStandardItemLighting();
        if(tileCrucible.output != null){
            if(par1 >= 112 + guiLeft && par2 >= 65 + guiTop && par1 <= 112 + 16 + guiLeft && par2 <= 65 + 16 + guiTop){
                this.drawHoveringText(tileCrucible.output.getTooltip(mc.thePlayer, true), par1, par2, mc.fontRenderer);
            }
        }
        if(par1 >= 141 + guiLeft && par2 >= 7 + guiTop && par1 <= 141+12 + guiLeft && par2 <= 7+12 + guiTop){
            String fluidType;
            if(tileCrucible.currentFluidID == Util.fluidWater.ID) fluidType = Util.fluidWater.name.substring(5);
            else if(tileCrucible.currentFluidID == Util.fluidNone.ID) fluidType = Util.fluidNone.name.substring(5);
            else fluidType = Util.gemList.get(tileCrucible.currentFluidID).name.substring(5);
            this.drawHoveringText(Arrays.asList(StatCollector.translateToLocal("tooltip.fluid" + fluidType + ".name")), par1, par2, mc.fontRenderer);
        }
    }
}
