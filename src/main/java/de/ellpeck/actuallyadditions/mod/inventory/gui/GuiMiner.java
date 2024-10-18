/*
 * This file ("GuiMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.mod.network.PacketHelperClient;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityVerticalDigger;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;



public class GuiMiner extends AAScreen<ContainerMiner> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityVerticalDigger miner;

    public GuiMiner(ContainerMiner container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.miner = container.miner;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

        this.addRenderableWidget(Button.builder(Component.literal("Mode"), $ -> {
            PacketHelperClient.sendButtonPacket(this.miner, 0);
        }).bounds(this.leftPos + this.imageWidth / 2 - 51, this.topPos + 75, 50, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Reset"), $ -> {
            PacketHelperClient.sendButtonPacket(this.miner, 1);
        }).bounds(this.leftPos + this.imageWidth / 2 + 1, this.topPos + 75, 50, 20).build());
    }
    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        String mining = this.miner.onlyMineOres
            ? "Only Mining Ores"
            : "Mining Everything";
        guiGraphics.drawString(font, mining, this.leftPos + this.imageWidth / 2 - this.font.width(mining) / 2, this.topPos + 8, 0x404040, false);
    }
}
