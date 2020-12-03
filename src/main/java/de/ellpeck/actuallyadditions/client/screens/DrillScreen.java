package de.ellpeck.actuallyadditions.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.common.container.DrillContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class DrillScreen extends ContainerScreen<DrillContainer> {
    public DrillScreen(DrillContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.renderBackground(matrixStack);
    }
}
