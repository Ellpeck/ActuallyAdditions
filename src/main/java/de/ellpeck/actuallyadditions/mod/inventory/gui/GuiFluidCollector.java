/*
 * This file ("GuiFluidCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFluidCollector;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFluidCollector;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiFluidCollector extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiFluidCollector");
    private final TileEntityFluidCollector collector;

    public GuiFluidCollector(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFluidCollector(inventory, tile));
        this.collector = (TileEntityFluidCollector)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        String text2 = StringUtil.getFluidInfo(this.collector.tank);
        if(x >= this.guiLeft+68 && y >= this.guiTop+6 && x <= this.guiLeft+83 && y <= this.guiTop+88){
            this.drawHoveringText(Collections.singletonList(text2), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.collector.name);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.collector.tank.getFluidAmount() > 0){
            int i = this.collector.getTankScaled(83);
            this.drawTexturedModalRect(this.guiLeft+68, this.guiTop+89-i, 176, 0, 16, i);
        }
    }
}