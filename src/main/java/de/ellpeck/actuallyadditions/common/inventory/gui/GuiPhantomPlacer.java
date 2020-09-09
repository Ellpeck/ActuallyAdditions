package de.ellpeck.actuallyadditions.inventory.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.inventory.ContainerPhantomPlacer;
import de.ellpeck.actuallyadditions.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.tile.TileEntityPhantomPlacer;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPhantomPlacer extends GuiWtfMojang {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityPhantomPlacer placer;

    public GuiPhantomPlacer(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerPhantomPlacer(inventory, tile));
        this.placer = (TileEntityPhantomPlacer) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        if (!this.placer.isBreaker) {
            this.buttonList.add(new GuiButton(0, this.guiLeft + 63, this.guiTop + 75, 50, 20, this.getSide()));
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (!this.placer.isBreaker) {
            this.buttonList.get(0).displayString = this.getSide();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (!this.placer.isBreaker && this.buttonList.get(0).isMouseOver()) {
            String loc = "info." + ActuallyAdditions.MODID + ".placer.sides";

            List<String> textList = new ArrayList<>();
            textList.add(TextFormatting.GOLD + StringUtil.localize(loc + ".1"));
            textList.addAll(this.fontRenderer.listFormattedStringToWidth(StringUtil.localize(loc + ".2"), 200));
            this.drawHoveringText(textList, mouseX, mouseY);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (!this.placer.isBreaker) {
            PacketHandlerHelper.sendButtonPacket(this.placer, button.id);
        }
    }

    private String getSide() {
        return GuiInputter.SIDES[this.placer.side + 1];
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.placer);
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