/*
 * This file ("GuiOilGenerator.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.ContainerOilGenerator;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityOilGenerator;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiOilGenerator extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiOilGenerator");
    private TileEntityOilGenerator generator;

    public GuiOilGenerator(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerOilGenerator(inventory, tile));
        this.generator = (TileEntityOilGenerator)tile;
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

        if(this.generator.storage.getEnergyStored() > 0){
            int i = this.generator.getEnergyScaled(83);
            drawTexturedModalRect(this.guiLeft+43, this.guiTop+89-i, 176, 0, 16, i);
        }

        if(this.generator.tank.getFluidAmount() > 0){
            int i = this.generator.getTankScaled(83);
            drawTexturedModalRect(this.guiLeft+117, this.guiTop+89-i, 192, 0, 16, i);
        }

        if(this.generator.currentBurnTime > 0){
            int i = this.generator.getBurningScaled(13);
            this.drawTexturedModalRect(guiLeft+72, guiTop+44+12-i, 176, 96-i, 14, i);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        String text1 = this.generator.storage.getEnergyStored()+"/"+this.generator.storage.getMaxEnergyStored()+" RF";
        if(x >= guiLeft+43 && y >= guiTop+6 && x <= guiLeft+58 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text1), x, y);
        }
        String text2 = this.generator.tank.getFluidAmount()+"/"+this.generator.tank.getCapacity()+" mB "+StringUtil.localize("fluid.oil");
        if(x >= guiLeft+117 && y >= guiTop+6 && x <= guiLeft+132 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text2), x, y);
        }
    }
}