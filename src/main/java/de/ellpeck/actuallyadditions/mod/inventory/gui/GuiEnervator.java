/*
 * This file ("GuiEnervator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerEnervator;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class GuiEnervator extends AAScreen<ContainerEnervator> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_energizer");
    private final TileEntityEnervator enervator;
    private EnergyDisplay energy;

    public GuiEnervator(ContainerEnervator container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.enervator = container.enervator;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 56, this.topPos + 5, this.enervator.storage);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        super.render(guiGraphics, x, y, f);
        this.energy.render(guiGraphics, x, y);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        this.energy.draw(guiGraphics);
    }
}
