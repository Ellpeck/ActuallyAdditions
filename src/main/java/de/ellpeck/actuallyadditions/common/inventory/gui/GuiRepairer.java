package de.ellpeck.actuallyadditions.inventory.gui;

import de.ellpeck.actuallyadditions.inventory.ContainerRepairer;
import de.ellpeck.actuallyadditions.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.tile.TileEntityItemRepairer;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRepairer extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_repairer");
    private final TileEntityItemRepairer tileRepairer;
    private EnergyDisplay energy;

    public GuiRepairer(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerRepairer(inventory, tile));
        this.tileRepairer = (TileEntityItemRepairer) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft + 27, this.guiTop + 5, this.tileRepairer.storage);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tileRepairer);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (TileEntityItemRepairer.canBeRepaired(this.tileRepairer.inv.getStackInSlot(TileEntityItemRepairer.SLOT_INPUT))) {
            int i = this.tileRepairer.getItemDamageToScale(22);
            this.drawTexturedModalRect(this.guiLeft + 73, this.guiTop + 52, 176, 28, i, 16);
        }

        this.energy.draw();
    }
}