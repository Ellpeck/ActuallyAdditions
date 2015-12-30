/*
 * This file ("GuiMiner.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.network.PacketHandler;
import de.ellpeck.actuallyadditions.network.gui.PacketGuiButton;
import de.ellpeck.actuallyadditions.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.tile.TileEntityMiner;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMiner extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiBreaker");
    private TileEntityMiner miner;

    public GuiMiner(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerMiner(inventory, tile));
        this.miner = (TileEntityMiner)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        GuiButton buttonMode = new GuiButton(0, guiLeft+xSize/2-51, guiTop+75, 50, 20, "Mode");
        this.buttonList.add(buttonMode);

        GuiButton buttonReset = new GuiButton(1, guiLeft+xSize/2+1, guiTop+75, 50, 20, "Reset");
        this.buttonList.add(buttonReset);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.miner.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        String mining = this.miner.onlyMineOres ? "Only Mining Ores" : "Mining Everything";
        this.fontRendererObj.drawString(mining, this.guiLeft+this.xSize/2-fontRendererObj.getStringWidth(mining)/2, guiTop+8, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

    @Override
    public void actionPerformed(GuiButton button){
        PacketHandler.theNetwork.sendToServer(new PacketGuiButton(miner.xCoord, miner.yCoord, miner.zCoord, miner.getWorldObj(), button.id, Minecraft.getMinecraft().thePlayer));
    }
}