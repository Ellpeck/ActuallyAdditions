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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerOilGenerator;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityOilGenerator;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
public class GuiOilGenerator extends AAScreen<ContainerOilGenerator> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_oil_generator");
    private final TileEntityOilGenerator generator;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiOilGenerator(ContainerOilGenerator container, Inventory inventory, Component title) {
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
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        super.render(guiGraphics, x, y, f);
        this.energy.render(guiGraphics, x, y);
        this.fluid.render(guiGraphics, x, y);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.generator.currentBurnTime > 0 && this.generator.maxBurnTime > 0) {
            int i = this.generator.getBurningScaled(13);
            guiGraphics.blit(RES_LOC, this.leftPos + 72, this.topPos + 44 + 12 - i, 176, 96 - i, 14, i);
        }

        if (this.generator.maxBurnTime > 0 && this.generator.currentEnergyProduce > 0) {
            guiGraphics.drawCenteredString(this.font, this.generator.currentEnergyProduce + " " + I18n.get("misc.actuallyadditions.energy_tick"), this.leftPos + 87, this.topPos + 65, 0xFFFFFF);
            guiGraphics.drawCenteredString(this.font, "for " + this.generator.maxBurnTime + " t", this.leftPos + 87, this.topPos + 75, 0xFFFFFF);
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(this.leftPos + 87, this.topPos + 85, 0);
            poseStack.scale(0.5625F, 0.5625F, 1F);
            int usage = this.generator.fuelUsage;
            guiGraphics.drawCenteredString(this.font, "(per " + usage + " mB)",0, 0, 0xFFFFFF);
            poseStack.popPose();
        }

        this.energy.draw(guiGraphics);
        this.fluid.draw(guiGraphics);
    }
}
