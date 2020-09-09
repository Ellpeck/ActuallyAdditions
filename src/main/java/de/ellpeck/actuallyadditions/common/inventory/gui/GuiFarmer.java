package de.ellpeck.actuallyadditions.common.inventory.gui;

import de.ellpeck.actuallyadditions.common.inventory.ContainerFarmer;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityFarmer;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFarmer extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_farmer");
    private final TileEntityFarmer farmer;

    private EnergyDisplay energy;

    public GuiFarmer(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerFarmer(inventory, tile));
        this.farmer = (TileEntityFarmer) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.energy = new EnergyDisplay(this.guiLeft + 33, this.guiTop + 6, this.farmer.storage);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.farmer);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        this.energy.draw();
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);
    }
}