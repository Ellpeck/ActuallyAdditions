package de.ellpeck.actuallyadditions.mod.inventory.gui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EnergyDisplay extends Gui {

    private CustomEnergyStorage rfReference;
    private int x;
    private int y;
    private boolean outline;
    private boolean drawTextNextTo;

    public EnergyDisplay(int x, int y, CustomEnergyStorage rfReference, boolean outline, boolean drawTextNextTo) {
        this.setData(x, y, rfReference, outline, drawTextNextTo);
    }

    public EnergyDisplay(int x, int y, CustomEnergyStorage rfReference) {
        this(x, y, rfReference, false, false);
    }

    public void setData(int x, int y, CustomEnergyStorage rfReference, boolean outline, boolean drawTextNextTo) {
        this.x = x;
        this.y = y;
        this.rfReference = rfReference;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;
    }

    public void draw() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;

        if (this.outline) {
            this.drawTexturedModalRect(this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.drawTexturedModalRect(barX, barY, 18, 171, 18, 85);

        if (this.rfReference.getEnergyStored() > 0) {
            int i = this.rfReference.getEnergyStored() * 83 / this.rfReference.getMaxEnergyStored();

            float[] color = AssetUtil.getWheelColor(mc.world.getTotalWorldTime() % 256);
            GlStateManager.color(color[0] / 255F, color[1] / 255F, color[2] / 255F);
            this.drawTexturedModalRect(barX + 1, barY + 84 - i, 36, 172, 16, i);
            GlStateManager.color(1F, 1F, 1F);
        }

        if (this.drawTextNextTo) {
            this.drawString(mc.fontRenderer, this.getOverlayText(), barX + 25, barY + 78, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    public void drawOverlay(int mouseX, int mouseY) {
        if (this.isMouseOver(mouseX, mouseY)) {
            Minecraft mc = Minecraft.getMinecraft();

            List<String> text = new ArrayList<>();
            text.add(this.getOverlayText());
            GuiUtils.drawHoveringText(text, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + (this.outline ? 26 : 18) && mouseY < this.y + (this.outline ? 93 : 85);
    }

    private String getOverlayText() {
        NumberFormat format = NumberFormat.getInstance();
        return String.format("%s/%s Crystal Flux", format.format(this.rfReference.getEnergyStored()), format.format(this.rfReference.getMaxEnergyStored()));
    }
}
