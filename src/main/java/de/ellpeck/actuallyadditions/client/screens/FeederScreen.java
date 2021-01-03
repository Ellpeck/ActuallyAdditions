package de.ellpeck.actuallyadditions.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.common.container.FeederContainer;
import de.ellpeck.actuallyadditions.common.tiles.FeederTileEntity;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import de.ellpeck.actuallyadditions.common.utilities.ScreenHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;

import java.util.ArrayList;
import java.util.List;

public class FeederScreen extends WtfMojangScreen<FeederContainer> {
    private static final ResourceLocation background = ScreenHelper.getGuiLocation("gui_feeder");
    public final FeederTileEntity tileFeeder;

    public FeederScreen(FeederContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.xSize = 176;
        this.ySize = 70 + 86;

        this.titleY += 90;
        this.tileFeeder = (FeederTileEntity) inv.player.world.getTileEntity(screenContainer.tile.getPos());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        getMinecraft().getTextureManager().bindTexture(ScreenHelper.INVENTORY_GUI);
        blit(matrixStack, this.guiLeft, this.guiTop + 70, 0, 0, 176, 86);

        getMinecraft().getTextureManager().bindTexture(background);
        blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, 176, 70);

        if (this.tileFeeder.currentTimer > 0) {
            int i = this.tileFeeder.getCurrentTimerToScale(20);
            this.blit(matrixStack, this.guiLeft + 85, this.guiTop + 42 - i, 181, 19 + 19 - i, 6, 20);
        }

        if (this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < FeederTileEntity.THRESHOLD) {
            this.blit(matrixStack, this.guiLeft + 70, this.guiTop + 31, 192, 16, 8, 8);
        }

        if (this.tileFeeder.currentAnimalAmount >= FeederTileEntity.THRESHOLD) {
            this.blit(matrixStack, this.guiLeft + 70, this.guiTop + 31, 192, 24, 8, 8);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        super.render(matrixStack, x, y, partialTicks);

        if (x >= this.guiLeft + 69 && y >= this.guiTop + 30 && x <= this.guiLeft + 69 + 10 && y <= this.guiTop + 30 + 10) {
            List<ITextProperties> array = new ArrayList<>();
            array.add(Help.trans("info.gui.animals", this.tileFeeder.currentAnimalAmount));
            array.add(tileFeeder.currentAnimalAmount >= 2 && tileFeeder.currentAnimalAmount < FeederTileEntity.THRESHOLD
                    ? Help.trans("info.gui.enoughToBreed")
                    : tileFeeder.currentAnimalAmount >= FeederTileEntity.THRESHOLD
                        ? Help.trans("info.gui.tooMany")
                        : Help.trans("info.gui.notEnough"));

            this.renderToolTip(matrixStack, LanguageMap.getInstance().func_244260_a(array), x, y, font);
        }
    }
}
