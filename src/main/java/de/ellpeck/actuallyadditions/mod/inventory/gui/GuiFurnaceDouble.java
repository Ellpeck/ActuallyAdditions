/*
 * This file ("GuiFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPoweredFurnace;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiFurnaceDouble extends AAScreen<ContainerFurnaceDouble> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_furnace_double");
    private final TileEntityPoweredFurnace tileFurnace;
    private EnergyDisplay energy;

    private Button buttonAutoSplit;

    public GuiFurnaceDouble(ContainerFurnaceDouble container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tileFurnace = container.furnace;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        super.render(guiGraphics, x, y, f);
        this.energy.render(guiGraphics, x, y);

        if (this.buttonAutoSplit.isMouseOver(x, y)) {
            guiGraphics.renderTooltip(font, Component.translatable("info.actuallyadditions.gui.autosplititems." + (tileFurnace.isAutoSplit?"on":"off")), x, y);
        }
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 27, this.topPos + 5, this.tileFurnace.storage);
        this.buttonAutoSplit = Button.builder(Component.literal("S"), (button) -> PacketHandlerHelper.sendButtonPacket(this.tileFurnace, 0))
                .bounds(this.getGuiLeft(), this.topPos + 5, 16, 16).build();
        buttonAutoSplit.setFGColor(this.tileFurnace.isAutoSplit ? ChatFormatting.DARK_GREEN.getColor() : ChatFormatting.RED.getColor());
        this.addRenderableWidget(this.buttonAutoSplit);
    }


    @Override
    public void containerTick() {
        super.containerTick();
        buttonAutoSplit.setFGColor(this.tileFurnace.isAutoSplit ? ChatFormatting.DARK_GREEN.getColor() : ChatFormatting.RED.getColor());
    }

    @Override
    public void renderBg(@Nonnull GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.tileFurnace.firstSmeltTime > 0) {
            int i = this.tileFurnace.getFirstTimeToScale(23);
            guiGraphics.blit(RES_LOC, this.leftPos + 51, this.topPos + 40, 176, 0, 24, i);
        }
        if (this.tileFurnace.secondSmeltTime > 0) {
            int i = this.tileFurnace.getSecondTimeToScale(23);
            guiGraphics.blit(RES_LOC, this.leftPos + 101, this.topPos + 40, 176, 22, 24, i);
        }

        this.energy.draw(guiGraphics);
    }
}
