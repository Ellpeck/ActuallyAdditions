/*
 * This file ("GuiMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityMiner;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMiner extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("guiBreaker");
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
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("X", this.miner.getPos().getX());
        compound.setInteger("Y", this.miner.getPos().getY());
        compound.setInteger("Z", this.miner.getPos().getZ());
        compound.setInteger("WorldID", this.miner.getWorld().provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
        compound.setInteger("ButtonID", button.id);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }
}