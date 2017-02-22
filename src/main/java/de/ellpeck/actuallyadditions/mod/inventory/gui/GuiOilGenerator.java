/*
 * This file ("GuiOilGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerOilGenerator;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityOilGenerator;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOilGenerator extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_oil_generator");
    private final TileEntityOilGenerator generator;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiOilGenerator(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerOilGenerator(inventory, tile));
        this.generator = (TileEntityOilGenerator)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void initGui(){
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft+42, this.guiTop+5, this.generator.storage);
        this.fluid = new FluidDisplay(this.guiLeft+116, this.guiTop+5, this.generator.tank);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);
        this.fluid.drawOverlay(x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.generator);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.generator.currentBurnTime > 0 && this.generator.maxBurnTime > 0){
            int i = this.generator.getBurningScaled(13);
            this.drawTexturedModalRect(this.guiLeft+72, this.guiTop+44+12-i, 176, 96-i, 14, i);
        }

        if(this.generator.maxBurnTime > 0 && this.generator.currentEnergyProduce > 0){
            this.drawCenteredString(this.fontRendererObj, this.generator.currentEnergyProduce+" CF/t", this.guiLeft+87, this.guiTop+65, 0xFFFFFF);
            this.drawCenteredString(this.fontRendererObj, "for "+this.generator.maxBurnTime+" t", this.guiLeft+87, this.guiTop+75, 0xFFFFFF);
        }

        this.energy.draw();
        this.fluid.draw();
    }
}