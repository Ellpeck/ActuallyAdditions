/*
 * This file ("GuiFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFireworkBox;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFireworkBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class GuiFireworkBox extends AbstractContainerScreen<ContainerFireworkBox> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_firework_box");

    protected final TileEntityFireworkBox tile;

    public GuiFireworkBox(ContainerFireworkBox screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.tile = screenContainer.fireworkbox;
        this.imageWidth = 302;
        this.imageHeight = 130;
    }

    @Override
    public void init() {
        super.init();

        DecimalFormat intFormatter = new DecimalFormat("0");
        int left = this.getGuiLeft() + 2;
        this.addRenderableWidget(new CustomSlider(left, this.getGuiTop() + 2, Component.literal("Value Play"), 0F, 5F, this.tile.intValuePlay, intFormatter, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 0)));
        this.addRenderableWidget(new CustomSlider(left, this.getGuiTop() + 23, Component.literal("Average Charge Amount"), 1F, 4F, this.tile.chargeAmount, intFormatter, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 1)));
        this.addRenderableWidget(new CustomSlider(left, this.getGuiTop() + 44, Component.literal("Average Flight Time"), 1F, 3F, this.tile.flightTime, intFormatter, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 2)));
        this.addRenderableWidget(new CustomSlider(left, this.getGuiTop() + 65, Component.literal("Effect Chance"), 0F, 1F, this.tile.trailOrFlickerChance, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 3)));
        this.addRenderableWidget(new CustomSlider(left, this.getGuiTop() + 86, Component.literal("Flicker/Trail Ratio"), 0F, 1F, this.tile.flickerChance, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 4)));
        this.addRenderableWidget(new CustomSlider(left, this.getGuiTop() + 107, Component.literal("Color Amount"), 1, 6, this.tile.colorAmount, intFormatter, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 5)));

        this.addRenderableWidget(new CustomSlider(left + 150, this.getGuiTop() + 2, Component.literal("Small Ball"), 0F, 1F, this.tile.typeChance0, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 6)));
        this.addRenderableWidget(new CustomSlider(left + 150, this.getGuiTop() + 23, Component.literal("Large Ball"), 0F, 1F, this.tile.typeChance1, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 7)));
        this.addRenderableWidget(new CustomSlider(left + 150, this.getGuiTop() + 44, Component.literal("Star Shape"), 0F, 1F, this.tile.typeChance2, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 8)));
        this.addRenderableWidget(new CustomSlider(left + 150, this.getGuiTop() + 65, Component.literal("Creeper Shape"), 0F, 1F, this.tile.typeChance3, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 9)));
        this.addRenderableWidget(new CustomSlider(left + 150, this.getGuiTop() + 86, Component.literal("Burst"), 0F, 1F, this.tile.typeChance4, null, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 10)));

        this.addRenderableWidget(new CustomSlider(left + 150, this.getGuiTop() + 107, Component.literal("Area of Effect"), 0, 4, this.tile.areaOfEffect, intFormatter, (slider) -> PacketHandlerHelper.sendNumberPacket(this.tile, slider.getValue(), 11)));
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        guiGraphics.drawString(font, this.title, (int)(this.getXSize() / 2f - font.width(this.title) / 2f), -10, 0xFFFFFF, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, this.getXSize(), this.getYSize(), 512, 512);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.getFocused() != null && this.isDragging() && pButton == 0) {
            return this.getFocused().mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    private static class CustomSlider extends AbstractSliderButton {
        private final double min;
        private final double max;
        private final DecimalFormat format;
        private final Component sliderName;
        private final NumberFormat defaultFormat = new DecimalFormat("0.00000000");
        protected final CustomSlider.OnApply onApply;
        private final double stepSize = 0;
        public CustomSlider(int x, int y, Component message, double min, double max, double defaultValue, DecimalFormat format, CustomSlider.OnApply onApply) {
            super(x, y, 148, 20, message, defaultValue);
            this.sliderName = message;
            this.min = min;
            this.max = max;
            this.format = format;
            this.value = (Mth.clamp((float) value, min, max) - min) / (max - min);
            this.onApply = onApply;

            this.updateMessage();
        }

        @Override
        protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
            super.onDrag(mouseX, mouseY, dragX, dragY);
            this.setValueFromMouse(mouseX);
        }

        private void setValueFromMouse(double mouseX) {
            this.setSliderValue((mouseX - (this.getX() + 4)) / (this.width - 8));
        }

        public double getValue() {
            return this.value * (max - min) + min;
        }

        private void setSliderValue(double value) {
            double oldValue = this.value;
            this.value = this.snapToNearest(value);
            if (!Mth.equal(oldValue, this.value))
                this.applyValue();

            this.updateMessage();
        }

        private double snapToNearest(double value) {
            if (stepSize <= 0D)
                return Mth.clamp(value, 0D, 1D);

            value = Mth.lerp(Mth.clamp(value, 0D, 1D), this.min, this.max);

            value = (stepSize * Math.round(value / stepSize));

            if (this.min > this.max) {
                value = Mth.clamp(value, this.max, this.min);
            } else {
                value = Mth.clamp(value, this.min, this.max);
            }

            return Mth.map(value, this.min, this.max, 0D, 1D);
        }


        public String getValueString() {
            if (this.format == null) {
                if(getValue() == (int) getValue()) {
                    return String.valueOf((double)((int) getValue()));
                } else {
                    return this.defaultFormat.format(this.getValue());
                }
            } else {
                return this.format.format(this.getValue());
            }
        }

        @Override
        protected void updateMessage() {
            this.setMessage(sliderName.copy().append(": ").append(this.getValueString()));
        }

        @Override
        public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
            return super.mouseReleased(pMouseX, pMouseY, pButton);
        }

        @Override
        protected void applyValue() {
            this.onApply.onApply(this);
        }

        public interface OnApply {
            void onApply(CustomSlider slider);
        }
    }
}
