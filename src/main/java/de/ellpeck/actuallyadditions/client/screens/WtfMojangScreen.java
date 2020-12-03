package de.ellpeck.actuallyadditions.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

/**
 * Inherited name from the original source code! :D
 *
 * Wraps up the render logic to display the background & hovered tooltips
 */
public abstract class WtfMojangScreen<T extends Container> extends ContainerScreen<T> {
    public WtfMojangScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.titleX = 0;
        this.titleY = 0;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        font.drawString(matrixStack, this.title.getString(), ((width - font.getStringWidth(this.title.getString())) / 2f) - this.titleX, (height / 2f) - this.titleY, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}
