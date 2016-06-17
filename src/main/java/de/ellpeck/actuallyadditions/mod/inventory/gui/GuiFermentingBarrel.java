/*
 * This file ("GuiFermentingBarrel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFermentingBarrel;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFermentingBarrel;
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
public class GuiFermentingBarrel extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("guiFermentingBarrel");
    private final TileEntityFermentingBarrel press;

    public GuiFermentingBarrel(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFermentingBarrel(inventory, tile));
        this.press = (TileEntityFermentingBarrel)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        String text1 = StringUtil.getFluidInfo(this.press.canolaTank);
        if(x >= this.guiLeft+61 && y >= this.guiTop+6 && x <= this.guiLeft+76 && y <= this.guiTop+88){
            this.drawHoveringText(Collections.singletonList(text1), x, y);
        }

        String text2 = StringUtil.getFluidInfo(this.press.oilTank);
        if(x >= this.guiLeft+99 && y >= this.guiTop+6 && x <= this.guiLeft+114 && y <= this.guiTop+88){
            this.drawHoveringText(Collections.singletonList(text2), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.press.name);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.press.canolaTank.getFluidAmount() > 0){
            int i = this.press.getCanolaTankScaled(83);
            this.drawTexturedModalRect(this.guiLeft+61, this.guiTop+89-i, 192, 29, 16, i);
        }

        if(this.press.oilTank.getFluidAmount() > 0){
            int i = this.press.getOilTankScaled(83);
            this.drawTexturedModalRect(this.guiLeft+99, this.guiTop+89-i, 176, 29, 16, i);
        }

        if(this.press.currentProcessTime > 0){
            int i = this.press.getProcessScaled(29);
            this.drawTexturedModalRect(this.guiLeft+82, this.guiTop+34, 176, 0, 12, i);
        }
    }
}