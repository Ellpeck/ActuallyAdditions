package de.ellpeck.actuallyadditions.inventory.gui;

import de.ellpeck.actuallyadditions.inventory.ContainerBioReactor;
import de.ellpeck.actuallyadditions.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.tile.TileEntityBioReactor;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBioReactor extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_bio_reactor");
    private final TileEntityBioReactor tile;
    private EnergyDisplay energy;

    public GuiBioReactor(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerBioReactor(inventory, tile));
        this.tile = (TileEntityBioReactor) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft + 116, this.guiTop + 5, this.tile.storage);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tile);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (this.tile.burnTime > 0) {
            int i = this.tile.burnTime * 13 / this.tile.maxBurnTime;
            this.drawTexturedModalRect(this.guiLeft + 87, this.guiTop + 51 + 12 - i, 176, 96 - i, 14, i);
        }

        if (this.tile.producePerTick > 0) {
            this.drawCenteredString(this.fontRenderer, this.tile.producePerTick + " " + I18n.format("actuallyadditions.cft"), this.guiLeft + 87, this.guiTop + 86, 0xFFFFFF);
        }

        this.energy.draw();
    }
}
