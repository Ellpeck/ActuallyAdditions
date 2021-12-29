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
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFermentingBarrel;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFermentingBarrel;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
public class GuiFermentingBarrel extends AAScreen<ContainerFermentingBarrel> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_fermenting_barrel");
    private final TileEntityFermentingBarrel press;
    private FluidDisplay input;
    private FluidDisplay output;

    public GuiFermentingBarrel(ContainerFermentingBarrel container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.press = container.barrel;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        this.input.render(matrices, x, y);
        this.output.render(matrices, x, y);
    }

    @Override
    public void init() {
        super.init();
        this.input = new FluidDisplay(this.leftPos + 60, this.topPos + 5, this.press.tanks.canolaTank);
        this.output = new FluidDisplay(this.leftPos + 98, this.topPos + 5, this.press.tanks.oilTank);
    }

    @Override
    public void renderLabels(@Nonnull MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.press);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.press.currentProcessTime > 0) {
            int i = this.press.getProcessScaled(29);
            this.blit(matrices, this.leftPos + 82, this.topPos + 34, 176, 0, 12, i);
        }

        this.input.draw(matrices);
        this.output.draw(matrices);
    }
}
