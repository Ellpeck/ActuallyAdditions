package de.ellpeck.actuallyadditions.booklet.button;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.common.inventory.gui.TexturedButton;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.Minecraft;

public class TrialsButton extends TexturedButton {

    private final boolean isTrials;

    public TrialsButton(GuiBooklet gui) {
        super(GuiBooklet.RES_LOC_GADGETS, -152000, gui.getGuiLeft() + gui.getSizeX(), gui.getGuiTop() + 10, 0, 204, 52, 16);
        this.isTrials = gui.areTrialsOpened();
        this.enabled = !this.isTrials;
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y, float f) {
        super.drawButton(minecraft, x, y, f);

        if (this.visible) {
            if (this.hovered || this.isTrials) {
                this.drawCenteredString(minecraft.fontRenderer, StringUtil.localize("booklet." + ActuallyAdditions.MODID + ".trialsButton.name"), this.x + (this.width - 8) / 2, this.y + (this.height - 8) / 2, 14737632);
            }
        }
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        if (mouseOver || this.isTrials) {
            return 2;
        } else if (!this.enabled) {
            return 0;
        } else {
            return 1;
        }
    }
}
