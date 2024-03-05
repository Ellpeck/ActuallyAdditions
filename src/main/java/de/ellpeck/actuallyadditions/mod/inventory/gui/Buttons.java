package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class Buttons {
    @OnlyIn(Dist.CLIENT)
    @Deprecated(forRemoval = true) //Vanilla's Button class can render small just fine and even allows text scrolling
    public static class SmallerButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");
        private final boolean smaller;

        public SmallerButton(int x, int y, Component display, OnPress pressable) {
            this(x, y, display, false, pressable);
        }

        public SmallerButton(int x, int y, Component display, boolean smaller, OnPress pressable) {
            super(x, y, 16, smaller
                ? 12
                : 16, display, pressable, DEFAULT_NARRATION);
            this.smaller = smaller;
        }

        @Override
        public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
            if (this.visible) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                int k = !this.active ? 0 : (this.isHoveredOrFocused() ? 2 : 1);
                GlStateManager._enableBlend();
                GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager._blendFunc(770, 771);
                guiGraphics.blit(this.resLoc, this.getX(), this.getY(), this.smaller
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

                guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, this.getMessage().getStyle().getColor().getValue());
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class TinyButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");

        public TinyButton(int id, int x, int y) {
            super(x, y, 8, 8, Component.literal(""), Button::onPress, DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
            if (this.visible) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                int k = !this.active ? 0 : (this.isHoveredOrFocused() ? 2 : 1);
                GlStateManager._enableBlend();
                GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager._blendFunc(770, 771);
                guiGraphics.blit(this.resLoc, this.getX(), this.getY(), 192, k * 8, 8, 8);
                //this.mouseDragged(mc, x, y);
            }
        }
    }
}
