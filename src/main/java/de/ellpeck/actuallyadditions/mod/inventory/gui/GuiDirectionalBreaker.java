/*
 * This file ("GuiDirectionalBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDirectionalBreaker;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDirectionalBreaker;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiDirectionalBreaker extends GuiWtfMojang<ContainerDirectionalBreaker> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_directional_breaker");
    private final TileEntityDirectionalBreaker breaker;
    private EnergyDisplay energy;

    public GuiDirectionalBreaker(ContainerDirectionalBreaker container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.breaker = container.breaker;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.guiLeft + 42, this.guiTop + 5, this.breaker.storage);
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);

        this.energy.render(matrices, x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.xSize, -10, this.breaker);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

        this.energy.draw(matrices);
    }
}
