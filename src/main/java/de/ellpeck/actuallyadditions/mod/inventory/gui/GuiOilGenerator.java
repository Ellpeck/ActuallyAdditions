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


@OnlyIn(Dist.CLIENT)
public class GuiOilGenerator extends GuiWtfMojang<ContainerOilGenerator> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_oil_generator");
    private final TileEntityOilGenerator generator;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiOilGenerator(ContainerOilGenerator container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.generator = container.generator;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.guiLeft + 42, this.guiTop + 5, this.generator.storage);
        this.fluid = new FluidDisplay(this.guiLeft + 116, this.guiTop + 5, this.generator.tank);
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        this.energy.render(matrices, x, y);
        this.fluid.render(matrices, x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.xSize, -10, this.generator);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (this.generator.currentBurnTime > 0 && this.generator.maxBurnTime > 0) {
            int i = this.generator.getBurningScaled(13);
            this.blit(matrices, this.guiLeft + 72, this.guiTop + 44 + 12 - i, 176, 96 - i, 14, i);
        }

        if (this.generator.maxBurnTime > 0 && this.generator.currentEnergyProduce > 0) {
            drawCenteredString(matrices, this.font, this.generator.currentEnergyProduce + " " + I18n.format("actuallyadditions.cft"), this.guiLeft + 87, this.guiTop + 65, 0xFFFFFF);
            drawCenteredString(matrices, this.font, "for " + this.generator.maxBurnTime + " t", this.guiLeft + 87, this.guiTop + 75, 0xFFFFFF);
            matrices.push();
            matrices.scale(0.75F, 0.75F, 1F);
            float xS = (this.guiLeft + 87) * 1.365F - this.font.getStringWidth("(per 50 mB)") / 2F;
            StringUtil.renderScaledAsciiString(this.font, "(per 50 mB)", xS, (this.guiTop + 85) * 1.345F, 0xFFFFFF, true, 0.75F);
            matrices.pop();
        }

        this.energy.draw(matrices);
        this.fluid.draw(matrices);
    }
}
