/*
 * This file ("GuiFeeder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFeeder;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFeeder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiFeeder extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_feeder");
    public final TileEntityFeeder tileFeeder;

    public GuiFeeder(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFeeder(inventory, tile));
        this.tileFeeder = (TileEntityFeeder)tile;
        this.xSize = 176;
        this.ySize = 70+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        if(x >= this.guiLeft+69 && y >= this.guiTop+30 && x <= this.guiLeft+69+10 && y <= this.guiTop+30+10){
            String[] array = new String[]{(this.tileFeeder.currentAnimalAmount+" "+StringUtil.localize("info."+ModUtil.MOD_ID+".gui.animals")), ((this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < TileEntityFeeder.THRESHOLD) ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.enoughToBreed") : (this.tileFeeder.currentAnimalAmount >= TileEntityFeeder.THRESHOLD ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.tooMany") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.notEnough")))};
            this.drawHoveringText(Arrays.asList(array), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tileFeeder);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+70, 0, 0, 176, 86);
        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 70);

        if(this.tileFeeder.currentTimer > 0){
            int i = this.tileFeeder.getCurrentTimerToScale(20);
            this.drawTexturedModalRect(this.guiLeft+85, this.guiTop+42-i, 181, 19+19-i, 6, 20);
        }

        if(this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < TileEntityFeeder.THRESHOLD){
            this.drawTexturedModalRect(this.guiLeft+70, this.guiTop+31, 192, 16, 8, 8);
        }

        if(this.tileFeeder.currentAnimalAmount >= TileEntityFeeder.THRESHOLD){
            this.drawTexturedModalRect(this.guiLeft+70, this.guiTop+31, 192, 24, 8, 8);
        }
    }
}