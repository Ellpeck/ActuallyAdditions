/*
 * This file ("GuiEnervator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerEnervator;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class GuiEnervator extends GuiWtfMojang<ContainerEnervator> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_energizer");
    private final TileEntityEnervator enervator;
    private EnergyDisplay energy;

    public GuiEnervator(ContainerEnervator container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.enervator = container.enervator;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft + 56, this.guiTop + 5, this.enervator.storage);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.enervator);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

        this.energy.draw();
    }
}
