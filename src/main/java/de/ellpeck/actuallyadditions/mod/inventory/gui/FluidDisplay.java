/*
 * This file ("FluidDisplay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class FluidDisplay {

    private IFluidTank fluidReference;
    private Fluid oldFluid;

    private int x;
    private int y;
    private boolean outline;

    private TextureAtlasSprite sprite;

    private boolean drawTextNextTo;

    private boolean drawCapacityInTooltip = true;

    public FluidDisplay(int x, int y, IFluidTank fluidReference, boolean outline, boolean drawTextNextTo) {
        this.setData(x, y, fluidReference, outline, drawTextNextTo);
    }

    public FluidDisplay(int x, int y, IFluidTank fluidReference) {
        this(x, y, fluidReference, false, false);
    }

    public FluidDisplay(int x, int y, FluidStack stack, int capacity, boolean drawCapacity) {
        this.x = x;
        this.y = y;
        this.drawCapacityInTooltip = drawCapacity;
        this.fluidReference = new DummyTank(stack, capacity);
    }

    public void setDrawCapacityInTooltip(boolean drawCapacityInTooltip) {
        this.drawCapacityInTooltip = drawCapacityInTooltip;
    }

    public void setData(int x, int y, IFluidTank fluidReference, boolean outline, boolean drawTextNextTo) {
        this.x = x;
        this.y = y;
        this.fluidReference = fluidReference;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;
    }

    public void draw(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();

        int barX = this.x;
        int barY = this.y;

        if (this.outline) {
	        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
	    guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, barX, barY, 0, 171, 18, 85);

        FluidStack stack = this.fluidReference.getFluid();
        Fluid fluid = stack.getFluid();

        if (stack != null && !stack.isEmpty() && fluid != null) {
            IClientFluidTypeExtensions fluidTypeExtension = IClientFluidTypeExtensions.of(fluid);
            int color = fluidTypeExtension.getTintColor(stack);
            float red = (float)(FastColor.ARGB32.red(color) / 255.0);
            float green = (float)(FastColor.ARGB32.green(color) / 255.0);
            float blue = (float)(FastColor.ARGB32.blue(color) / 255.0);
            float alpha = (float)(FastColor.ARGB32.alpha(color) / 255.0);
            ResourceLocation stillTexture = fluidTypeExtension.getStillTexture();

            if (this.sprite == null || this.oldFluid != fluid) {
                this.oldFluid = stack.getFluid();

                AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS);
                if (texture instanceof TextureAtlas) {
                    TextureAtlasSprite sprite = ((TextureAtlas) texture).getSprite(stillTexture);
                    if (sprite != null) {
                        this.sprite = sprite;
                    }
                }
            }

            if (this.sprite != null) {
                float minU = sprite.getU0();
                float maxU = sprite.getU1();
                float minV = sprite.getV0();
                float maxV = sprite.getV1();
                float deltaV = maxV - minV;

                double tankLevel = ((float) this.fluidReference.getFluidAmount() / (float) fluidReference.getCapacity()) * 83;

                RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
                RenderSystem.setShaderColor(red, green, blue, alpha);
                RenderSystem.enableBlend();
                int count = 1 + ((int) Math.ceil(tankLevel)) / 16;
                for (int i = 0; i < count; i++) {
                    double subHeight = Math.min(16.0, tankLevel - (16.0 * i));
                    double offsetY = 84 - 16.0 * i - subHeight;
                    drawQuad(barX + 1, barY + offsetY, 16, subHeight, minU, (float) (maxV - deltaV * (subHeight / 16.0)), maxU, maxV);
                }
                RenderSystem.disableBlend();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }

            if (this.drawTextNextTo) {
                guiGraphics.drawString(mc.font, this.getOverlayText(), barX + 25, barY + 78, 0xFFFFFF, false);
            }
        }
    }

    private void drawQuad(double x, double y, double width, double height, float minU, float minV, float maxU, float maxV) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(x, y + height, 0).uv(minU, maxV).endVertex();
        buffer.vertex(x + width, y + height, 0).uv(maxU, maxV).endVertex();
        buffer.vertex(x + width, y, 0).uv(maxU, minV).endVertex();
        buffer.vertex(x, y, 0).uv(minU, minV).endVertex();
        tesselator.end();
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + (this.outline
            ? 26
            : 18) && mouseY < this.y + (this.outline
            ? 93
            : 85)) {
            Minecraft mc = Minecraft.getInstance();
            guiGraphics.renderComponentTooltip(mc.font, Collections.singletonList(this.getOverlayText()), mouseX, mouseY);
        }
    }

    private Component getOverlayText() {
        NumberFormat format = NumberFormat.getInstance();
        FluidStack stack = this.fluidReference.getFluid();
        String cap = format.format(this.fluidReference.getCapacity());
        return stack.isEmpty()
            ? Component.literal("0/" + cap + " mB")
            : Component.literal(format.format(this.fluidReference.getFluidAmount()) + (drawCapacityInTooltip?"/" + cap + " mB ":" mB ")).append(stack.getDisplayName());
    }

    public static class DummyTank implements IFluidTank {
        private final FluidStack fluid;
        private final int capacity;

        public DummyTank(FluidStack fluid, int capacity) {
            this.fluid = fluid;
            this.capacity = capacity;
        }

        @Nonnull
        @Override
        public FluidStack getFluid() {
            return fluid;
        }

        @Override
        public int getFluidAmount() {
            return fluid.getAmount();
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            return 0;
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return FluidStack.EMPTY;
        }
    }
}
