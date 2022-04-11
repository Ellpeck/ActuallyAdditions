/*
 * This file ("GuiOilGenerator.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.inventory.ContainerOilGenerator;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityOilGenerator;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
public class GuiOilGenerator extends AAScreen<ContainerOilGenerator> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_oil_generator");
    private final TileEntityOilGenerator generator;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiOilGenerator(ContainerOilGenerator container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.generator = container.generator;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 42, this.topPos + 5, this.generator.storage);
        this.fluid = new FluidDisplay(this.leftPos + 116, this.topPos + 5, this.generator.tank);
        titleLabelX = (int) (imageWidth / 2.0f - font.width(title) / 2.0f);
        titleLabelY = -10;
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        this.energy.render(matrices, x, y);
        this.fluid.render(matrices, x, y);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.generator.currentBurnTime > 0 && this.generator.maxBurnTime > 0) {
            int i = this.generator.getBurningScaled(13);
            this.blit(matrices, this.leftPos + 72, this.topPos + 44 + 12 - i, 176, 96 - i, 14, i);
        }

        if (this.generator.maxBurnTime > 0 && this.generator.currentEnergyProduce > 0) {
            drawCenteredString(matrices, this.font, this.generator.currentEnergyProduce + " " + I18n.get("misc.actuallyadditions.energy_tick"), this.leftPos + 87, this.topPos + 65, 0xFFFFFF);
            drawCenteredString(matrices, this.font, "for " + this.generator.maxBurnTime + " t", this.leftPos + 87, this.topPos + 75, 0xFFFFFF);
            matrices.pushPose();
            matrices.translate(this.leftPos + 87, this.topPos + 85, 0);
            matrices.scale(0.5625F, 0.5625F, 1F);
            int usage = this.generator.fuelUsage;
            drawCenteredString(matrices, this.font, "(per " + usage + " mB)",0, 0, 0xFFFFFF);
            matrices.popPose();
        }

        this.energy.draw(matrices);
        this.fluid.draw(matrices);
    }
}
