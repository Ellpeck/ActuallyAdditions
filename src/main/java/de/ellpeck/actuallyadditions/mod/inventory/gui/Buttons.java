package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Buttons {
    @OnlyIn(Dist.CLIENT)
    public static class SmallerButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");
        private final boolean smaller;

        public SmallerButton(int x, int y, Component display, OnPress pressable) {
            this(x, y, display, false, pressable);
        }

        public SmallerButton(int x, int y, Component display, boolean smaller, OnPress pressable) {
            super(x, y, 16, smaller
                ? 12
                : 16, display, pressable);
            this.smaller = smaller;
        }

        @Override
        public void render(PoseStack matrixStack, int x, int y, float f) {
            if (this.visible) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, this.resLoc);
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
            super(x, y, 8, 8, new TextComponent(""), Button::onPress);
        }

        @Override
        public void render(PoseStack matrixStack, int x, int y, float f) {
            if (this.visible) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, this.resLoc);
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
