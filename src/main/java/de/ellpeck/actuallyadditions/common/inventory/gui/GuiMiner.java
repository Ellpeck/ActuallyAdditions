package de.ellpeck.actuallyadditions.common.inventory.gui;

import de.ellpeck.actuallyadditions.common.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.common.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityMiner;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMiner extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityMiner miner;

    public GuiMiner(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerMiner(inventory, tile));
        this.miner = (TileEntityMiner) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        GuiButton buttonMode = new GuiButton(0, this.guiLeft + this.xSize / 2 - 51, this.guiTop + 75, 50, 20, "Mode");
        this.buttonList.add(buttonMode);

        GuiButton buttonReset = new GuiButton(1, this.guiLeft + this.xSize / 2 + 1, this.guiTop + 75, 50, 20, "Reset");
        this.buttonList.add(buttonReset);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.miner);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        String mining = this.miner.onlyMineOres ? "Only Mining Ores" : "Mining Everything";
        this.fontRenderer.drawString(mining, this.guiLeft + this.xSize / 2 - this.fontRenderer.getStringWidth(mining) / 2, this.guiTop + 8, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        PacketHandlerHelper.sendButtonPacket(this.miner, button.id);
    }
}