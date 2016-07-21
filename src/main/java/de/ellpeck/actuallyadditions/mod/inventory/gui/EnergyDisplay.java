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
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.Collections;

public class EnergyDisplay extends Gui{

    private EnergyStorage rfReference;
    private int x;
    private int y;
    private boolean outline;
    private boolean drawTextNextTo;

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
    }

    public void draw(){
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;

        if(this.outline){
            this.drawTexturedModalRect(this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.drawTexturedModalRect(barX, barY, 18, 171, 18, 85);

        if(this.rfReference.getEnergyStored() > 0){
            int i = this.rfReference.getEnergyStored()*83/this.rfReference.getMaxEnergyStored();
            this.drawTexturedModalRect(barX+1, barY+84-i, 36, 172, 16, i);
        }

        if(this.drawTextNextTo){
            this.drawString(mc.fontRendererObj, this.getOverlayText(), barX+25, barY+78, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    public void drawOverlay(int mouseX, int mouseY){
        if(mouseX >= this.x && mouseY >= this.y && mouseX < this.x+(this.outline ? 26 : 18) && mouseY < this.y+(this.outline ? 93 : 85)){
            Minecraft mc = Minecraft.getMinecraft();
            GuiUtils.drawHoveringText(Collections.singletonList(this.getOverlayText()), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }

    private String getOverlayText(){
        return this.rfReference.getEnergyStored()+"/"+this.rfReference.getMaxEnergyStored()+" RF";
    }
}
