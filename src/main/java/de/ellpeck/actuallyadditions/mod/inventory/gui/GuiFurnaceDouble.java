/*
 * This file ("GuiFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiFurnaceDouble extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_furnace_double");
    private final TileEntityFurnaceDouble tileFurnace;
    private EnergyDisplay energy;

    private GuiButton buttonAutoSplit;

    public GuiFurnaceDouble(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFurnaceDouble(inventory, tile));
        this.tileFurnace = (TileEntityFurnaceDouble)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);

        if(this.buttonAutoSplit.isMouseOver()){
            this.drawHoveringText(Collections.singletonList(TextFormatting.BOLD+(this.tileFurnace.isAutoSplit ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.autoSplitItems.on") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.autoSplitItems.off"))), x, y);
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft+27, this.guiTop+5, this.tileFurnace.storage);

        this.buttonAutoSplit = new GuiInputter.SmallerButton(0, this.guiLeft, this.guiTop, "S");
        this.buttonList.add(this.buttonAutoSplit);
    }

    @Override
    public void updateScreen(){
        super.updateScreen();

        this.buttonAutoSplit.displayString = (this.tileFurnace.isAutoSplit ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"S";
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(button.id == 0){
            PacketHandlerHelper.sendButtonPacket(this.tileFurnace, button.id);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tileFurnace);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.tileFurnace.firstSmeltTime > 0){
            int i = this.tileFurnace.getFirstTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+51, this.guiTop+40, 176, 0, 24, i);
        }
        if(this.tileFurnace.secondSmeltTime > 0){
            int i = this.tileFurnace.getSecondTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft+101, this.guiTop+40, 176, 22, 24, i);
        }

        this.energy.draw();
    }
}