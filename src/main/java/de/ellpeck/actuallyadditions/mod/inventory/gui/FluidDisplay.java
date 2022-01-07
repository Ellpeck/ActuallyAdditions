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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.text.NumberFormat;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class FluidDisplay extends AbstractGui {

    private IFluidTank fluidReference;
    private Fluid oldFluid;

    private int x;
    private int y;
    private boolean outline;

    private ResourceLocation resLoc;

    private boolean drawTextNextTo;

    public FluidDisplay(int x, int y, IFluidTank fluidReference, boolean outline, boolean drawTextNextTo) {
        this.setData(x, y, fluidReference, outline, drawTextNextTo);
    }

    public FluidDisplay(int x, int y, IFluidTank fluidReference) {
        this(x, y, fluidReference, false, false);
    }

    public void setData(int x, int y, IFluidTank fluidReference, boolean outline, boolean drawTextNextTo) {
        this.x = x;
        this.y = y;
        this.fluidReference = fluidReference;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;
    }

    public void draw(MatrixStack matrices) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;

        if (this.outline) {
            this.blit(matrices, this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.blit(matrices, barX, barY, 0, 171, 18, 85);

        FluidStack stack = this.fluidReference.getFluid();
        Fluid fluid = stack.getFluid();

        if (this.resLoc == null || this.oldFluid != stack.getFluid()) {
            this.oldFluid = stack.getFluid();

            if (fluid != null && fluid.getAttributes().getStillTexture() != null) {
                this.resLoc = new ResourceLocation(fluid.getAttributes().getStillTexture().getNamespace(), "textures/" + fluid.getAttributes().getStillTexture().getPath() + ".png");
            }
        }

        if (stack != null && fluid != null && this.resLoc != null) {
            mc.getTextureManager().bind(this.resLoc);

            GlStateManager._pushMatrix();
            GlStateManager._enableBlend();
            GlStateManager._disableAlphaTest();
            GlStateManager._blendFuncSeparate(770, 771, 1, 0);
            int i = this.fluidReference.getFluid().getAmount() * 83 / this.fluidReference.getCapacity();
            this.blit(matrices, barX + 1, barY + 84 - i, 0, 0, 16, i, 16, 512);
            //drawModalRectWithCustomSizedTexture(barX + 1, barY + 84 - i, 36, 172, 16, i, 16, 512);
            GlStateManager._disableBlend();
            GlStateManager._enableAlphaTest();
            GlStateManager._popMatrix();
        }

        if (this.drawTextNextTo) {
            mc.font.draw(matrices, new StringTextComponent(this.getOverlayText()), barX + 25, barY + 78, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + (this.outline
            ? 26
            : 18) && mouseY < this.y + (this.outline
            ? 93
            : 85)) {
            Minecraft mc = Minecraft.getInstance();
            GuiUtils.drawHoveringText(matrices, Collections.singletonList(new StringTextComponent(this.getOverlayText())), mouseX, mouseY, mc.getWindow().getWidth(), mc.getWindow().getHeight(), -1, mc.font);
        }
    }

    private String getOverlayText() {
        NumberFormat format = NumberFormat.getInstance();
        FluidStack stack = this.fluidReference.getFluid();
        String cap = format.format(this.fluidReference.getCapacity());
        return stack.isEmpty()
            ? "0/" + cap + " mB"
            : format.format(this.fluidReference.getFluidAmount()) + "/" + cap + " mB " + stack.getDisplayName().getString();
    }
}
