/*
 * This file ("GuiCanolaPress.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCanolaPress;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCanolaPress;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiCanolaPress extends AAScreen<ContainerCanolaPress> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_canola_press");
    private final TileEntityCanolaPress press;
    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiCanolaPress(ContainerCanolaPress container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.press = container.press;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 42, this.topPos + 5, this.press.storage);
        this.fluid = new FluidDisplay(this.leftPos + 116, this.topPos + 5, this.press.tank);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        super.render(guiGraphics, x, y, f);

        this.energy.render(guiGraphics, x, y);
        this.fluid.render(guiGraphics, x, y);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.press.currentProcessTime > 0) {
            int i = this.press.getProcessScaled(29);
            guiGraphics.blit(RES_LOC, this.leftPos + 83, this.topPos + 32, 176, 0, 12, i);
        }

        this.energy.draw(guiGraphics);
        this.fluid.draw(guiGraphics);
    }
}
