package de.ellpeck.actuallyadditions.booklet.button;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntryButton extends GuiButton {

    private final GuiBookletBase gui;
    private final ItemStack stackToRender;

    public EntryButton(GuiBookletBase gui, int id, int x, int y, int width, int height, String text, ItemStack stackToRender) {
        super(id, x, y, width, height, text);
        this.gui = gui;
        StackUtil.isValid(stackToRender);
        this.stackToRender = stackToRender;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float f) {
        if (this.visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.mouseDragged(minecraft, mouseX, mouseY);

            int textOffsetX = 0;
            if (StackUtil.isValid(this.stackToRender)) {
                GlStateManager.pushMatrix();
                AssetUtil.renderStackToGui(this.stackToRender, this.x - 4, this.y, 0.725F);
                GlStateManager.popMatrix();
                textOffsetX = 10;
            }

            float scale = this.gui.getMediumFontSize();

            if (this.hovered) {
                GlStateManager.pushMatrix();
                AssetUtil.drawHorizontalGradientRect(this.x + textOffsetX - 1, this.y + this.height - 1, this.x + (int) (minecraft.fontRenderer.getStringWidth(this.displayString) * scale) + textOffsetX + 1, this.y + this.height, 0x80 << 24 | 22271, 22271, this.zLevel);
                GlStateManager.popMatrix();
            }

            StringUtil.renderScaledAsciiString(minecraft.fontRenderer, this.displayString, this.x + textOffsetX, this.y + 2 + (this.height - 8) / 2, 0, false, scale);
        }
    }
}
