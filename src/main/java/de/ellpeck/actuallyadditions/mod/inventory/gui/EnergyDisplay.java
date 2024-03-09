/*
 * This file ("EnergyDisplay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class EnergyDisplay {

    private CustomEnergyStorage rfReference;
    private int x;
    private int y;
    private boolean outline;
    private boolean drawTextNextTo;

    public EnergyDisplay(int x, int y, CustomEnergyStorage rfReference, boolean outline, boolean drawTextNextTo) {
        this.setData(x, y, rfReference, outline, drawTextNextTo);
    }

    public EnergyDisplay(int x, int y, CustomEnergyStorage rfReference) {
        this(x, y, rfReference, false, false);
    }

    public void setData(int x, int y, CustomEnergyStorage rfReference, boolean outline, boolean drawTextNextTo) {
        this.x = x;
        this.y = y;
        this.rfReference = rfReference;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;
    }

    public void draw(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        int barX = this.x;
        int barY = this.y;

        if (this.outline) {
            guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, barX, barY, 18, 171, 18, 85);

        if (this.rfReference.getEnergyStored() > 0) {
            int i = this.rfReference.getEnergyStored() * 83 / this.rfReference.getMaxEnergyStored();

            float[] color = AssetUtil.getWheelColor(mc.level.getGameTime() % 256);
            RenderSystem.setShaderColor(color[0] / 255F, color[1] / 255F, color[2] / 255F, 1F); //color3f
            guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, barX + 1, barY + 84 - i, 36, 172, 16, i);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F); //color3f
        }

        if (this.drawTextNextTo) {
            guiGraphics.drawString(mc.font, this.getOverlayText(), barX + 25, barY + 78, 0xFFFFFF);
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.isMouseOver(mouseX, mouseY)) {
            Minecraft mc = Minecraft.getInstance();

            List<Component> text = new ArrayList<>();
            text.add(this.getOverlayText());
            guiGraphics.renderComponentTooltip(mc.font, text, mouseX, mouseY);
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + (this.outline
            ? 26
            : 18) && mouseY < this.y + (this.outline
            ? 93
            : 85);
    }

    private Component getOverlayText() {
        NumberFormat format = NumberFormat.getInstance();
        return Component.translatable("misc.actuallyadditions.power_long",
            format.format(this.rfReference.getEnergyStored()),
            format.format(this.rfReference.getMaxEnergyStored()));
    }
}
