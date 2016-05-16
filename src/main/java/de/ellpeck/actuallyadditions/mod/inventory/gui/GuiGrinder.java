/*
 * This file ("GuiGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerGrinder;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiGrinder");
    private static final ResourceLocation resLocDouble = AssetUtil.getGuiLocation("guiGrinderDouble");
    private TileEntityGrinder tileGrinder;
    private boolean isDouble;

    public GuiGrinder(InventoryPlayer inventoryPlayer, TileEntityBase tile){
        this(inventoryPlayer, tile, false);
    }

    private GuiGrinder(InventoryPlayer inventory, TileEntityBase tile, boolean isDouble){
        super(new ContainerGrinder(inventory, tile, isDouble));
        this.tileGrinder = (TileEntityGrinder)tile;
        this.isDouble = isDouble;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        String text = this.tileGrinder.storage.getEnergyStored()+"/"+this.tileGrinder.storage.getMaxEnergyStored()+" RF";
        if((this.isDouble && x >= this.guiLeft+14 && y >= this.guiTop+6 && x <= this.guiLeft+29 && y <= this.guiTop+88) || (!this.isDouble && x >= this.guiLeft+43 && y >= this.guiTop+6 && x <= this.guiLeft+58 && y <= this.guiTop+88)){
            this.drawHoveringText(Collections.singletonList(text), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tileGrinder.getName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(this.isDouble ? resLocDouble : resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileGrinder.storage.getEnergyStored() > 0){
            int i = this.tileGrinder.getEnergyScaled(83);
            this.drawTexturedModalRect(this.guiLeft+(this.isDouble ? 14 : 43), this.guiTop+89-i, 176, (this.isDouble ? 44 : 23), 16, i);
        }
        if(this.tileGrinder.firstCrushTime > 0){
            int i = this.tileGrinder.getFirstTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+(this.isDouble ? 51 : 80), this.guiTop+40, 176, 0, 24, i);
        }
        if(this.isDouble){
            if(this.tileGrinder.secondCrushTime > 0){
                int i = this.tileGrinder.getSecondTimeToScale(23);
                this.drawTexturedModalRect(this.guiLeft+101, this.guiTop+40, 176, 22, 24, i);
            }
        }
    }

    public static class GuiGrinderDouble extends GuiGrinder{

        public GuiGrinderDouble(InventoryPlayer inventory, TileEntityBase tile){
            super(inventory, tile, true);
        }
    }
}