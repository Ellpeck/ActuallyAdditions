/*
 * This file ("EnergyDisplay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class EnergyDisplay extends Gui{

    private EnergyStorage rfReference;
    private int x;
    private int y;
    private boolean outline;
    private boolean drawTextNextTo;
    private boolean displayTesla;

    public EnergyDisplay(int x, int y, EnergyStorage rfReference, boolean outline, boolean drawTextNextTo){
        this.setData(x, y, rfReference, outline, drawTextNextTo);
    }

    public EnergyDisplay(int x, int y, EnergyStorage rfReference){
        this(x, y, rfReference, false, false);
    }

    public void setData(int x, int y, EnergyStorage rfReference, boolean outline, boolean drawTextNextTo){
        this.x = x;
        this.y = y;
        this.rfReference = rfReference;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;

        if(TileEntityBase.teslaLoaded){
            this.displayTesla = PlayerData.getDataFromPlayer(Minecraft.getMinecraft().thePlayer).theCompound.getBoolean("DisplayTesla");
        }
    }

    public void draw(){
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;
        int vOffset = this.displayTesla ? 85 : 0;

        if(this.outline){
            this.drawTexturedModalRect(this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.drawTexturedModalRect(barX, barY, 18, 171-vOffset, 18, 85);

        if(this.rfReference.getEnergyStored() > 0){
            int i = this.rfReference.getEnergyStored()*83/this.rfReference.getMaxEnergyStored();
            this.drawTexturedModalRect(barX+1, barY+84-i, 36, 172-vOffset, 16, i);
        }

        if(this.drawTextNextTo){
            this.drawString(mc.fontRendererObj, this.getOverlayText(), barX+25, barY+78, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    public void drawOverlay(int mouseX, int mouseY){
        if(this.isMouseOver(mouseX, mouseY)){
            Minecraft mc = Minecraft.getMinecraft();

            List<String> text = new ArrayList<String>();
            text.add(this.getOverlayText());
            if(TileEntityBase.teslaLoaded){
                text.add(TextFormatting.GRAY+""+TextFormatting.ITALIC+"Click to change mode!");
            }
            GuiUtils.drawHoveringText(text, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton){
        if(mouseButton == 0 && this.isMouseOver(mouseX, mouseY)){
            this.changeDisplayMode();
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY){
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x+(this.outline ? 26 : 18) && mouseY < this.y+(this.outline ? 93 : 85);
    }

    private String getOverlayText(){
        return this.rfReference.getEnergyStored()+"/"+this.rfReference.getMaxEnergyStored()+(this.displayTesla ? " Tesla" : " RF");
    }

    private void changeDisplayMode(){
        if(TileEntityBase.teslaLoaded){
            NBTTagCompound data = new NBTTagCompound();

            this.displayTesla = !this.displayTesla;
            data.setBoolean("DisplayTesla", this.displayTesla);

            NBTTagCompound dataToSend = new NBTTagCompound();
            dataToSend.setTag("Data", data);
            dataToSend.setInteger("WorldID", Minecraft.getMinecraft().theWorld.provider.getDimension());
            dataToSend.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
            PacketHandler.theNetwork.sendToServer(new PacketClientToServer(dataToSend, PacketHandler.CHANGE_PLAYER_DATA_HANDLER));
        }
    }
}
