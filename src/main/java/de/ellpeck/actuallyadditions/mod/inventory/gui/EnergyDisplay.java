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
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class EnergyDisplay extends GuiComponent {

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

    public void draw(PoseStack matrices) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, AssetUtil.GUI_INVENTORY_LOCATION); //bind?

        int barX = this.x;
        int barY = this.y;

        if (this.outline) {
            this.blit(matrices, this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.blit(matrices, barX, barY, 18, 171, 18, 85);

        if (this.rfReference.getEnergyStored() > 0) {
            int i = this.rfReference.getEnergyStored() * 83 / this.rfReference.getMaxEnergyStored();

            float[] color = AssetUtil.getWheelColor(mc.level.getGameTime() % 256);
            RenderSystem.setShaderColor(color[0] / 255F, color[1] / 255F, color[2] / 255F, 1F); //color3f
            this.blit(matrices, barX + 1, barY + 84 - i, 36, 172, 16, i);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F); //color3f
        }

        if (this.drawTextNextTo) {
            //this.drawString(mc.font, this.getOverlayText(), barX + 25, barY + 78, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    public void render(PoseStack matrices, int mouseX, int mouseY) {
        if (this.isMouseOver(mouseX, mouseY)) {
            Minecraft mc = Minecraft.getInstance();

            List<TextComponent> text = new ArrayList<>();
            text.add(new TextComponent(this.getOverlayText()));
            if(mc.screen != null)
                mc.screen.renderComponentTooltip(matrices, text, mouseX, mouseY, mc.font); //TODO: Check if this is correct, used to call GuiUtils.drawHoveringText
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + (this.outline
            ? 26
            : 18) && mouseY < this.y + (this.outline
            ? 93
            : 85);
    }

    private String getOverlayText() {
        NumberFormat format = NumberFormat.getInstance();
        return new TranslatableComponent("misc.actuallyadditions.power_long",
            format.format(this.rfReference.getEnergyStored()),
            format.format(this.rfReference.getMaxEnergyStored()))
            .getString();
    }
}
