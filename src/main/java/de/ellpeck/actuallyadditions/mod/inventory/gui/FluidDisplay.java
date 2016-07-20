/*
 * This file ("FluidDisplay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import cofh.api.energy.EnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.Collections;

public class FluidDisplay extends Gui{

    private final FluidTank fluidReference;
    private Fluid oldFluid;

    private final int x;
    private final int y;
    private final boolean outline;

    private ResourceLocation resLoc;

    public FluidDisplay(int x, int y, FluidTank fluidReference, boolean outline){
        this.x = x;
        this.y = y;
        this.fluidReference = fluidReference;
        this.outline = outline;
    }

    public FluidDisplay(int x, int y, FluidTank fluidReference){
        this(x, y, fluidReference, false);
    }

    public void draw(){
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;

        if(this.outline){
            this.drawTexturedModalRect(this.x, this.y, 52, 163, 26, 93);

            barX += 4;
            barY += 4;
        }
        this.drawTexturedModalRect(barX, barY, 0, 171, 18, 85);

        FluidStack stack = this.fluidReference.getFluid();
        Fluid fluid = stack == null ? null : stack.getFluid();

        if(this.resLoc == null || this.oldFluid != fluid){
            this.oldFluid = fluid;

            if(fluid != null){
                this.resLoc = new ResourceLocation(fluid.getStill().getResourceDomain(), "textures/"+fluid.getStill().getResourcePath()+".png");
            }
        }

        if(stack != null && fluid != null && this.resLoc != null){
            mc.getTextureManager().bindTexture(this.resLoc);

            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            int i = this.fluidReference.getFluidAmount()*83/this.fluidReference.getCapacity();
            GuiInputter.drawModalRectWithCustomSizedTexture(barX+1, barY+84-i, 36, 172, 16, i, 16, 512);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
        }
    }

    public void drawOverlay(int mouseX, int mouseY){
        if(mouseX >= this.x && mouseY >= this.y && mouseX < this.x+(this.outline ? 26 : 18) && mouseY < this.y+(this.outline ? 93 : 85)){
            FluidStack stack = this.fluidReference.getFluid();
            Minecraft mc = Minecraft.getMinecraft();

            String text = stack == null || stack.getFluid() == null ? "0/"+this.fluidReference.getCapacity()+" mB" : this.fluidReference.getFluidAmount()+"/"+this.fluidReference.getCapacity()+" mB "+stack.getLocalizedName();
            GuiUtils.drawHoveringText(Collections.singletonList(text), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }
}
