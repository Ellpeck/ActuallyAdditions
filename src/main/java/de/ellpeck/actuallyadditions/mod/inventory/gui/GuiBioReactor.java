/*
 * This file ("GuiBioReactor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBioReactor;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBioReactor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class GuiBioReactor extends AAScreen<ContainerBioReactor> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_bio_reactor");
    private final TileEntityBioReactor tile;
    private EnergyDisplay energy;

    public GuiBioReactor(ContainerBioReactor container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tile = container.tile;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 116, this.topPos + 5, this.tile.storage);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.energy.render(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.tile.burnTime > 0) {
            int i = this.tile.burnTime * 13 / this.tile.maxBurnTime;
            guiGraphics.blit(RES_LOC, this.leftPos + 87, this.topPos + 51 + 12 - i, 176, 96 - i, 14, i);
        }

        if (this.tile.producePerTick > 0) {
            guiGraphics.drawCenteredString(this.font, Component.literal(this.tile.producePerTick + " ").append(Component.translatable("misc.actuallyadditions.energy_tick")), this.leftPos + 87, this.topPos + 86, 0xFFFFFF);
        }

        this.energy.draw(guiGraphics);
    }
}
