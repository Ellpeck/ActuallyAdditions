/*
 * This file ("GuiCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import java.util.Collections;


public class GuiCoffeeMachine extends AAScreen<ContainerCoffeeMachine> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_coffee_machine");
    private final TileEntityCoffeeMachine machine;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiCoffeeMachine(ContainerCoffeeMachine container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.machine = container.machine;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

        this.addRenderableWidget(new Button.Builder(Component.translatable("info.actuallyadditions.gui.ok"),
                (b) -> PacketHandlerHelper.sendButtonPacket(this.machine, 0))
                .bounds(this.leftPos + 60, this.topPos + 11, 58, 20).build());

        this.energy = new EnergyDisplay(this.leftPos + 16, this.topPos + 5, this.machine.storage);
        this.fluid = new FluidDisplay(this.leftPos - 30, this.topPos + 1, this.machine.tank, true, false);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        super.render(guiGraphics, x, y, f);
        Minecraft mc = Minecraft.getInstance();

        Component text = Component.translatable("info.actuallyadditions.gui.coffee_amount", this.machine.coffeeCacheAmount, TileEntityCoffeeMachine.COFFEE_CACHE_MAX_AMOUNT);
        if (x >= this.leftPos + 40 && y >= this.topPos + 25 && x <= this.leftPos + 49 && y <= this.topPos + 56) {
            guiGraphics.renderComponentTooltip(font, Collections.singletonList(text), x, y);
        }

        this.energy.render(guiGraphics, x, y);
        this.fluid.render(guiGraphics, x, y);
    }

    @Override
    public void renderBg(@Nonnull GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.machine.coffeeCacheAmount > 0) {
            int i = this.machine.getCoffeeScaled(30);
            guiGraphics.blit(RES_LOC, this.leftPos + 41, this.topPos + 56 - i, 192, 0, 8, i);
        }

        if (this.machine.brewTime > 0) {
            int i = this.machine.getBrewScaled(23);
            guiGraphics.blit(RES_LOC, this.leftPos + 53, this.topPos + 42, 192, 30, i, 16);

            int j = this.machine.getBrewScaled(26);
            guiGraphics.blit(RES_LOC, this.leftPos + 99 + 25 - j, this.topPos + 44, 192 + 25 - j, 46, j, 12);
        }

        this.energy.draw(guiGraphics);
        this.fluid.draw(guiGraphics);
    }
}
