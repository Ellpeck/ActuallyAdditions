package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Buttons {
    @OnlyIn(Dist.CLIENT)
    public static class SmallerButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");
        private final boolean smaller;

        public SmallerButton(int x, int y, ITextComponent display, IPressable pressable) {
            this(x, y, display, false, pressable);
        }

        public SmallerButton(int x, int y, ITextComponent display, boolean smaller, IPressable pressable) {
            super(x, y, 16, smaller
                ? 12
                : 16, display, pressable);
            this.smaller = smaller;
        }

        @Override
        public void render(MatrixStack matrixStack, int x, int y, float f) {
            if (this.visible) {
                Minecraft.getInstance().getTextureManager().bind(this.resLoc);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.isHovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
                int k = this.getYImage(this.isHovered);
                GlStateManager._enableBlend();
                GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager._blendFunc(770, 771);
                this.blit(matrixStack, this.x, this.y, this.smaller
                    ? 200
                    : 176, k * this.height, this.width, this.height);
                //this.mouseDragged(mc, x, y); // The heck was this doing here?

                int color = 14737632;
                if (this.packedFGColor != 0) {
                    color = this.packedFGColor;
                } else if (!this.active) {
                    color = 10526880;
                } else if (this.isHovered) {
                    color = 16777120;
                }

                drawCenteredString(matrixStack, Minecraft.getInstance().font, this.getMessage().getString(), this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class TinyButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");

        public TinyButton(int id, int x, int y) {
            super(x, y, 8, 8, new StringTextComponent(""), Button::onPress);
        }

        @Override
        public void render(MatrixStack matrixStack, int x, int y, float f) {
            if (this.visible) {
                Minecraft.getInstance().getTextureManager().bind(this.resLoc);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.isHovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
                int k = this.getYImage(this.isHovered);
                GlStateManager._enableBlend();
                GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager._blendFunc(770, 771);
                this.blit(matrixStack, this.x, this.y, 192, k * 8, 8, 8);
                //this.mouseDragged(mc, x, y);
            }
        }
    }
}
