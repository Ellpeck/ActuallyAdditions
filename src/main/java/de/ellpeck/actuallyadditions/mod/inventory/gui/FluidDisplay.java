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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

    private ResourceLocation resLoc;

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

        if (this.resLoc == null || this.oldFluid != stack.getFluid()) {
            this.oldFluid = stack.getFluid();

            if (fluid != null && IClientFluidTypeExtensions.of(fluid).getStillTexture() != null) {
                this.resLoc = new ResourceLocation(IClientFluidTypeExtensions.of(fluid).getStillTexture().getNamespace(), "textures/" + IClientFluidTypeExtensions.of(fluid).getStillTexture().getPath() + ".png");
            }
        }

        if (stack != null && fluid != null && this.resLoc != null) {
//            GlStateManager._pushMatrix();
            RenderSystem.enableBlend();
//            GlStateManager._disableAlphaTest();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            int i = this.fluidReference.getFluid().getAmount() * 83 / this.fluidReference.getCapacity();
	        guiGraphics.blit(this.resLoc, barX + 1, barY + 84 - i, 0, 0, 16, i, 16, 512);
            //drawModalRectWithCustomSizedTexture(barX + 1, barY + 84 - i, 36, 172, 16, i, 16, 512);
            RenderSystem.disableBlend();
//            GlStateManager._enableAlphaTest();
//            GlStateManager._popMatrix();
        }

        if (this.drawTextNextTo) {
			guiGraphics.drawString(mc.font, this.getOverlayText(), barX + 25, barY + 78, 0xFFFFFF, false);
        }
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
