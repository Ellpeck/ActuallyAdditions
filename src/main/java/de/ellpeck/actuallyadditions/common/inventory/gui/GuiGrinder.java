package de.ellpeck.actuallyadditions.common.inventory.gui;

import java.io.IOException;
import java.util.Collections;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.inventory.ContainerGrinder;
import de.ellpeck.actuallyadditions.common.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_grinder");
    private static final ResourceLocation RES_LOC_DOUBLE = AssetUtil.getGuiLocation("gui_grinder_double");
    private final TileEntityGrinder tileGrinder;
    private final boolean isDouble;
    private EnergyDisplay energy;

    private GuiButton buttonAutoSplit;

    public GuiGrinder(InventoryPlayer inventoryPlayer, TileEntityBase tile) {
        this(inventoryPlayer, tile, false);
    }

    private GuiGrinder(InventoryPlayer inventory, TileEntityBase tile, boolean isDouble) {
        super(new ContainerGrinder(inventory, tile, isDouble));
        this.tileGrinder = (TileEntityGrinder) tile;
        this.isDouble = isDouble;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft + (this.isDouble ? 13 : 42), this.guiTop + 5, this.tileGrinder.storage);

        if (this.isDouble) {
            this.buttonAutoSplit = new GuiInputter.SmallerButton(0, this.guiLeft - 10, this.guiTop, "S");
            this.buttonList.add(this.buttonAutoSplit);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (this.isDouble && button.id == 0) {
            PacketHandlerHelper.sendButtonPacket(this.tileGrinder, button.id);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (this.isDouble) {
            this.buttonAutoSplit.displayString = (this.tileGrinder.isAutoSplit ? TextFormatting.DARK_GREEN : TextFormatting.RED) + "S";
        }
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);

        if (this.isDouble && this.buttonAutoSplit.isMouseOver()) {

            this.drawHoveringText(Collections.singletonList(TextFormatting.BOLD + (this.tileGrinder.isAutoSplit ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.on") : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.off"))), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tileGrinder);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(this.isDouble ? RES_LOC_DOUBLE : RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (this.tileGrinder.firstCrushTime > 0) {
            int i = this.tileGrinder.getFirstTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft + (this.isDouble ? 51 : 80), this.guiTop + 40, 176, 0, 24, i);
        }
        if (this.isDouble) {
            if (this.tileGrinder.secondCrushTime > 0) {
                int i = this.tileGrinder.getSecondTimeToScale(23);
                this.drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 40, 176, 22, 24, i);
            }
        }

        this.energy.draw();
    }

    public static class GuiGrinderDouble extends GuiGrinder {

        public GuiGrinderDouble(InventoryPlayer inventory, TileEntityBase tile) {
            super(inventory, tile, true);
        }
    }
}