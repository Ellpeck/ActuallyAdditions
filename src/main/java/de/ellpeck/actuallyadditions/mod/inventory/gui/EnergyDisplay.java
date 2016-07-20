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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.Collections;

public class EnergyDisplay extends Gui{

    private final EnergyStorage rfReference;
    private final int x;
    private final int y;
    private final boolean outline;

    public EnergyDisplay(int x, int y, EnergyStorage rfReference, boolean outline){
        this.x = x;
        this.y = y;
        this.rfReference = rfReference;
        this.outline = outline;
    }

    public EnergyDisplay(int x, int y, EnergyStorage rfReference){
        this(x, y, rfReference, false);
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
    }

    public void drawOverlay(int mouseX, int mouseY){
        if(mouseX >= this.x && mouseY >= this.y && mouseX < this.x+(this.outline ? 26 : 18) && mouseY < this.y+(this.outline ? 93 : 85)){
            Minecraft mc = Minecraft.getMinecraft();
            String text = this.rfReference.getEnergyStored()+"/"+this.rfReference.getMaxEnergyStored()+" RF";
            GuiUtils.drawHoveringText(Collections.singletonList(text), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }
}
