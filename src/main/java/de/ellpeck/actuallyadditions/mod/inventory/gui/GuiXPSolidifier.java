/*
 * This file ("GuiXPSolidifier.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerXPSolidifier;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityXPSolidifier;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class GuiXPSolidifier extends AAScreen<ContainerXPSolidifier> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_xp_solidifier");
    private final TileEntityXPSolidifier solidifier;

    public GuiXPSolidifier(ContainerXPSolidifier container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.solidifier = container.solidifier;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

        Button buttonOne = this.addRenderableWidget(Button.builder(Component.literal("1"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 0);
            this.solidifier.onButtonPressed(0, minecraft.player);
        }).bounds(this.leftPos + 62, this.topPos + 44, 16, 16).build());
        Button buttonFive = this.addRenderableWidget(Button.builder(Component.literal("5"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 1);
            this.solidifier.onButtonPressed(1, minecraft.player);
        }).bounds(this.leftPos + 80, this.topPos + 44, 16, 16).build());
        Button buttonTen = this.addRenderableWidget(Button.builder(Component.literal("10"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 2);
            this.solidifier.onButtonPressed(2, minecraft.player);
        }).bounds(this.leftPos + 99, this.topPos + 44, 16, 16).build());
        Button buttonTwenty = this.addRenderableWidget(Button.builder(Component.literal("20"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 3);
            this.solidifier.onButtonPressed(3, minecraft.player);
        }).bounds(this.leftPos + 62, this.topPos + 61, 16, 16).build());
        Button buttonThirty = this.addRenderableWidget(Button.builder(Component.literal("30"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 4);
            this.solidifier.onButtonPressed(4, minecraft.player);
        }).bounds(this.leftPos + 80, this.topPos + 61, 16, 16).build());
        Button buttonForty = this.addRenderableWidget(Button.builder(Component.literal("40"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 5);
            this.solidifier.onButtonPressed(5, minecraft.player);
        }).bounds(this.leftPos + 99, this.topPos + 61, 16, 16).build());
        Button buttonFifty = this.addRenderableWidget(Button.builder(Component.literal("50"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 6);
            this.solidifier.onButtonPressed(6, minecraft.player);
        }).bounds(this.leftPos + 62, this.topPos + 78, 16, 16).build());
        Button buttonSixtyFour = this.addRenderableWidget(Button.builder(Component.literal("64"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 7);
            this.solidifier.onButtonPressed(7, minecraft.player);
        }).bounds(this.leftPos + 80, this.topPos + 78, 16, 16).build());
        Button buttonAll = this.addRenderableWidget(Button.builder(Component.literal("All"), btn -> {
            PacketHandlerHelper.sendButtonPacket(this.solidifier, 8);
            this.solidifier.onButtonPressed(8, minecraft.player);
        }).bounds(this.leftPos + 99, this.topPos + 78, 16, 16).build());
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        guiGraphics.drawCenteredString(this.font, Integer.toString(this.solidifier.amount), this.leftPos + 88, this.topPos + 34, 0xFFFFFF);
    }
}
