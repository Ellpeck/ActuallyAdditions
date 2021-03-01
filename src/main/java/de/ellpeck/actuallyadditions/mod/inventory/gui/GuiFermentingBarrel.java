/*
 * This file ("GuiFermentingBarrel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFermentingBarrel;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFermentingBarrel;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class GuiFermentingBarrel extends GuiWtfMojang<ContainerFermentingBarrel> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_fermenting_barrel");
    private final TileEntityFermentingBarrel press;
    private FluidDisplay input;
    private FluidDisplay output;

    public GuiFermentingBarrel(ContainerFermentingBarrel container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.press = container.barrel;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        this.input.render(matrices, x, y);
        this.output.render(matrices, x, y);
    }

    @Override
    public void init() {
        super.init();
        this.input = new FluidDisplay(this.guiLeft + 60, this.guiTop + 5, this.press.canolaTank);
        this.output = new FluidDisplay(this.guiLeft + 98, this.guiTop + 5, this.press.oilTank);
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.xSize, -10, this.press);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (this.press.currentProcessTime > 0) {
            int i = this.press.getProcessScaled(29);
            this.blit(matrices, this.guiLeft + 82, this.guiTop + 34, 176, 0, 12, i);
        }

        this.input.draw(matrices);
        this.output.draw(matrices);
    }
}
