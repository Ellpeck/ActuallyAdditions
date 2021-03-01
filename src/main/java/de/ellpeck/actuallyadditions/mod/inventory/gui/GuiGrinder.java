/*
 * This file ("GuiGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerGrinder;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class GuiGrinder extends GuiWtfMojang<ContainerGrinder> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_grinder");
    private static final ResourceLocation RES_LOC_DOUBLE = AssetUtil.getGuiLocation("gui_grinder_double");
    private final TileEntityGrinder tileGrinder;
    private final boolean isDouble;
    private EnergyDisplay energy;

    private Button buttonAutoSplit;

    public GuiGrinder(PlayerInventory inventoryPlayer, TileEntityBase tile) {
        this(inventoryPlayer, tile, false);
    }

    private GuiGrinder(ContainerGrinder container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.tileGrinder = container.tileGrinder;
        this.isDouble = container.isDouble;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.guiLeft + (this.isDouble
            ? 13
            : 42), this.guiTop + 5, this.tileGrinder.storage);

        if (this.isDouble) {
            this.buttonAutoSplit = new GuiInputter.SmallerButton(0, this.guiLeft - 10, this.guiTop, "S");
            this.addButton(this.buttonAutoSplit);
        }
    }

    @Override
    protected void actionPerformed(Button button) throws IOException {
        if (this.isDouble && button.id == 0) {
            PacketHandlerHelper.sendButtonPacket(this.tileGrinder, button.id);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (this.isDouble) {
            this.buttonAutoSplit.displayString = (this.tileGrinder.isAutoSplit
                ? TextFormatting.DARK_GREEN
                : TextFormatting.RED) + "S";
        }
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);

        if (this.isDouble && this.buttonAutoSplit.isMouseOver()) {

            this.drawHoveringText(Collections.singletonList(TextFormatting.BOLD + (this.tileGrinder.isAutoSplit
                ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.on")
                : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.off"))), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.font, this.xSize, -10, this.tileGrinder);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(this.isDouble
            ? RES_LOC_DOUBLE
            : RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (this.tileGrinder.firstCrushTime > 0) {
            int i = this.tileGrinder.getFirstTimeToScale(23);
            this.blit(matrices, this.guiLeft + (this.isDouble
                ? 51
                : 80), this.guiTop + 40, 176, 0, 24, i);
        }
        if (this.isDouble) {
            if (this.tileGrinder.secondCrushTime > 0) {
                int i = this.tileGrinder.getSecondTimeToScale(23);
                this.blit(matrices, this.guiLeft + 101, this.guiTop + 40, 176, 22, 24, i);
            }
        }

        this.energy.draw();
    }

    public static class GuiGrinderDouble extends GuiGrinder {

        public GuiGrinderDouble(PlayerInventory inventory, TileEntityBase tile) {
            super(inventory, tile, true);
        }
    }
}
