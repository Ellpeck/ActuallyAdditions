/*
 * This file ("GuiFurnaceDouble.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiFurnaceDouble extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiFurnaceDouble");
    private TileEntityFurnaceDouble tileFurnace;

    public GuiFurnaceDouble(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFurnaceDouble(inventory, tile));
        this.tileFurnace = (TileEntityFurnaceDouble)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        String text = this.tileFurnace.storage.getEnergyStored()+"/"+this.tileFurnace.storage.getMaxEnergyStored()+" RF";
        if(x >= guiLeft+28 && y >= guiTop+6 && x <= guiLeft+43 && y <= guiTop+88){
            this.drawHoveringText(Collections.singletonList(text), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.tileFurnace.getName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileFurnace.storage.getEnergyStored() > 0){
            int i = this.tileFurnace.getEnergyScaled(83);
            drawTexturedModalRect(this.guiLeft+28, this.guiTop+89-i, 176, 44, 16, i);
        }
        if(this.tileFurnace.firstSmeltTime > 0){
            int i = this.tileFurnace.getFirstTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+51, this.guiTop+40, 176, 0, 24, i);
        }
        if(this.tileFurnace.secondSmeltTime > 0){
            int i = this.tileFurnace.getSecondTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+101, this.guiTop+40, 176, 22, 24, i);
        }
    }
}