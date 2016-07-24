/*
 * This file ("GuiRangedCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerRangedCollector;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityRangedCollector;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiRangedCollector extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("guiRangedCollector");
    private final TileEntityRangedCollector collector;
    private final int x;
    private final int y;
    private final int z;
    private final World world;

    private FilterSettingsGui filter;

    public GuiRangedCollector(InventoryPlayer inventory, TileEntityBase tile, int x, int y, int z, World world){
        super(new ContainerRangedCollector(inventory, tile));
        this.collector = (TileEntityRangedCollector)tile;
        this.xSize = 176;
        this.ySize = 86+86;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.filter = new FilterSettingsGui(this.collector.filter, this.guiLeft+3, this.guiTop+6, this.buttonList);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        this.filter.drawHover(x, y);
    }

    @Override
    public void updateScreen(){
        super.updateScreen();

        this.filter.update();
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.collector);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+86, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 86);
    }

    @Override
    public void actionPerformed(GuiButton button){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("X", this.x);
        compound.setInteger("Y", this.y);
        compound.setInteger("Z", this.z);
        compound.setInteger("WorldID", this.world.provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
        compound.setInteger("ButtonID", button.id);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }
}