/*
 * This file ("GuiDropper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerDropper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDropper;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiDropper extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityDropper dropper;

    public GuiDropper(PlayerEntity player, TileEntityBase tile) {
        super(new ContainerDropper(player, tile));
        this.dropper = (TileEntityDropper) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.dropper);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);
    }
}
