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

import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class EnergyDisplay extends Gui{

    private CustomEnergyStorage rfReference;
    private int x;
    private int y;
    private boolean outline;
    private boolean drawTextNextTo;
    private int displayMode; //0: RF, 1: FU, 2: T

    public EnergyDisplay(int x, int y, CustomEnergyStorage rfReference, boolean outline, boolean drawTextNextTo){
        this.setData(x, y, rfReference, outline, drawTextNextTo);
    }

    public EnergyDisplay(int x, int y, CustomEnergyStorage rfReference){
        this(x, y, rfReference, false, false);
    }

    public void setData(int x, int y, CustomEnergyStorage rfReference, boolean outline, boolean drawTextNextTo){
        this.x = x;
        this.y = y;
        this.rfReference = rfReference;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;

        this.displayMode = PlayerData.getDataFromPlayer(Minecraft.getMinecraft().thePlayer).energyDisplayMode;
    }

    public void draw(){
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;
        int uOffset = this.displayMode == 1 ? 60 : 0;
        int vOffset = this.displayMode == 0 ? 0 : 85;

        if(this.outline){
            this.drawTexturedModalRect(this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.drawTexturedModalRect(barX, barY, 18+uOffset, 171-vOffset, 18, 85);

        if(this.rfReference.getEnergyStored() > 0){
            int i = this.rfReference.getEnergyStored()*83/this.rfReference.getMaxEnergyStored();
            this.drawTexturedModalRect(barX+1, barY+84-i, 36+uOffset, 172-vOffset, 16, i);
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
            text.add("");
            text.add(TextFormatting.GRAY+""+TextFormatting.ITALIC+StringUtil.localize("info."+ModUtil.MOD_ID+".energy.to"+(this.displayMode == 1 ? "T" : (this.displayMode == 0 ? "FU" : "RF"))));
            for(int i = 1; i <= 2; i++){
                text.add(TextFormatting.DARK_GRAY+""+TextFormatting.ITALIC+StringUtil.localize("info."+ModUtil.MOD_ID+".energy.disclaimer."+i));
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
        NumberFormat format = NumberFormat.getInstance();
        return format.format(this.rfReference.getEnergyStored())+"/"+format.format(this.rfReference.getMaxEnergyStored())+(this.displayMode == 0 ? " RF" :  (this.displayMode == 2 ? " T" : " FU"));
    }

    private void changeDisplayMode(){
        this.displayMode++;
        if(this.displayMode >= 3){
            this.displayMode = 0;
        }

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        PlayerData.getDataFromPlayer(player).energyDisplayMode = this.displayMode;
        PacketHandlerHelper.sendPlayerDataPacket(player, false, false);
    }
}
