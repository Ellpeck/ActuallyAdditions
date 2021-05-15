package de.ellpeck.actuallyadditions.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.common.container.VoidSackContainer;
import de.ellpeck.actuallyadditions.common.utilities.ScreenHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class VoidSackScreen extends WtfMojangScreen<VoidSackContainer>{
    private static final ResourceLocation background = ScreenHelper.getGuiLocation("gui_void_bag");

    public VoidSackScreen(VoidSackContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.xSize = 176;
        this.ySize = 43 + 86;

        this.titleY += 90;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        getMinecraft().getTextureManager().bindTexture(ScreenHelper.INVENTORY_GUI);
        blit(matrixStack, this.guiLeft, this.guiTop + 43, 0, 0, 176, 86);

        getMinecraft().getTextureManager().bindTexture(background);
        blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, 176, 43);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
