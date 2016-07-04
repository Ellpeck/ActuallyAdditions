/*
 * This file ("GuiLaserRelayItemWhitelist.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiInputter.SmallerButton;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItemWhitelist;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiLaserRelayItemWhitelist extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("guiLaserRelayItemWhitelist");
    private final TileEntityLaserRelayItemWhitelist tile;

    private SmallerButton whitelistLeft;
    private SmallerButton whitelistRight;

    public GuiLaserRelayItemWhitelist(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerLaserRelayItemWhitelist(inventory, tile));
        this.tile = (TileEntityLaserRelayItemWhitelist)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.whitelistLeft = new SmallerButton(0, this.guiLeft+3, this.guiTop+16, "");
        this.whitelistRight = new SmallerButton(1, this.guiLeft+157, this.guiTop+16, "");
        SmallerButton smartWhitelistLeft = new SmallerButton(2, this.guiLeft+3, this.guiTop+34, "S");
        SmallerButton smartWhitelistRight = new SmallerButton(3, this.guiLeft+157, this.guiTop+34, "S");

        this.buttonList.add(this.whitelistLeft);
        this.buttonList.add(this.whitelistRight);
        this.buttonList.add(smartWhitelistLeft);
        this.buttonList.add(smartWhitelistRight);
    }

    @Override
    public void actionPerformed(GuiButton button){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("X", this.tile.getPos().getX());
        compound.setInteger("Y", this.tile.getPos().getY());
        compound.setInteger("Z", this.tile.getPos().getZ());
        compound.setInteger("WorldID", this.tile.getWorld().provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
        compound.setInteger("ButtonID", button.id);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        this.whitelistLeft.displayString = this.tile.isLeftWhitelist ? "O" : "X";
        this.whitelistRight.displayString = this.tile.isRightWhitelist ? "O" : "X";

        List infoList = this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.whitelistInfo"), 200);
        String text1 = this.tile.isLeftWhitelist ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.whitelist") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.blacklist");
        if(x >= this.guiLeft+3 && y >= this.guiTop+16 && x <= this.guiLeft+18 && y <= this.guiTop+31){
            ArrayList list = new ArrayList();
            list.add(TextFormatting.BOLD+text1);
            list.addAll(infoList);
            this.drawHoveringText(list, x, y);
        }
        String text2 = this.tile.isRightWhitelist ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.whitelist") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.blacklist");
        if(x >= this.guiLeft+157 && y >= this.guiTop+16 && x <= this.guiLeft+172 && y <= this.guiTop+31){
            ArrayList list = new ArrayList();
            list.add(TextFormatting.BOLD+text2);
            list.addAll(infoList);
            this.drawHoveringText(list, x, y);
        }
        if(((x >= this.guiLeft+3 && x <= this.guiLeft+3+15) || (x >= this.guiLeft+157 && x <= this.guiLeft+157+15)) && y <= this.guiTop+34+15 && y >= this.guiTop+34){
            List<String> list = new ArrayList<String>();
            list.add(TextFormatting.BOLD+StringUtil.localize("info."+ModUtil.MOD_ID+".gui.smart"));
            list.addAll(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.smartInfo"), 200));
            this.drawHoveringText(list, x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tile);

        String s1 = "INBOUND";
        String s2 = "OUTBOUND";
        this.fontRendererObj.drawString(s1, 46-this.fontRendererObj.getStringWidth(s1)/2, 80, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.fontRendererObj.drawString(s2, 131-this.fontRendererObj.getStringWidth(s2)/2, 80, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

    }
}