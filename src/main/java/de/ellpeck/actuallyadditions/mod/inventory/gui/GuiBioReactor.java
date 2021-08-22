/*
 * This file ("GuiBioReactor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBioReactor;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBioReactor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiBioReactor extends GuiWtfMojang<ContainerBioReactor> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_bio_reactor");
    private final TileEntityBioReactor tile;
    private EnergyDisplay energy;

    public GuiBioReactor(ContainerBioReactor container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.tile = container.tile;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 116, this.topPos + 5, this.tile.storage);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.render(matrices, mouseX, mouseY, partialTicks);
        this.energy.render(mouseX, mouseY);
    }

    @Override
    public void renderLabels(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.tile);
    }

    @Override
    public void renderBg(MatrixStack matrices, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.tile.burnTime > 0) {
            int i = this.tile.burnTime * 13 / this.tile.maxBurnTime;
            this.blit(matrices, this.leftPos + 87, this.topPos + 51 + 12 - i, 176, 96 - i, 14, i);
        }

        if (this.tile.producePerTick > 0) {
            drawCenteredString(matrices, this.font, this.tile.producePerTick + " " + I18n.get("actuallyadditions.cft"), this.leftPos + 87, this.topPos + 86, 0xFFFFFF);
        }

        this.energy.draw();
    }
}
