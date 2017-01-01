/*
 * This file ("GuiMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityMiner;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMiner extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityMiner miner;

    public GuiMiner(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerMiner(inventory, tile));
        this.miner = (TileEntityMiner)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void initGui(){
        super.initGui();

        GuiButton buttonMode = new GuiButton(0, this.guiLeft+this.xSize/2-51, this.guiTop+75, 50, 20, "Mode");
        this.buttonList.add(buttonMode);

        GuiButton buttonReset = new GuiButton(1, this.guiLeft+this.xSize/2+1, this.guiTop+75, 50, 20, "Reset");
        this.buttonList.add(buttonReset);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.miner);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        String mining = this.miner.onlyMineOres ? "Only Mining Ores" : "Mining Everything";
        this.fontRendererObj.drawString(mining, this.guiLeft+this.xSize/2-this.fontRendererObj.getStringWidth(mining)/2, this.guiTop+8, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

    @Override
    public void actionPerformed(GuiButton button){
        PacketHandlerHelper.sendButtonPacket(this.miner, button.id);
    }
}