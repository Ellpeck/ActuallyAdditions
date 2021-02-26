/*
 * This file ("RenderBatteryBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;

import java.text.NumberFormat;

@OnlyIn(Dist.CLIENT)
public class RenderBatteryBox extends TileEntityRenderer<TileEntityBatteryBox> {
    public RenderBatteryBox(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    // TODO: [port] migrate to matric (see cleanstart)
    @Override
    public void render(TileEntityBatteryBox tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemBattery) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.5F, 1F, 0.5F);

            RenderSystem.pushMatrix();

            RenderSystem.scalef(0.0075F, 0.0075F, 0.0075F);
            RenderSystem.rotatef(180F, 1F, 0F, 0F);
            RenderSystem.translatef(0F, 0F, -50F);

            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> {
                NumberFormat format = NumberFormat.getInstance();
                FontRenderer font = Minecraft.getInstance().fontRenderer;

                String s = format.format(cap.getEnergyStored()) + "/" + format.format(cap.getMaxEnergyStored());
                float lengthS = -font.getStringWidth(s) / 2F;
                String s2 = I18n.format("actuallyadditions.cflong");
                float lengthS2 = -font.getStringWidth(s2) / 2F;

                for (int i = 0; i < 4; i++) {
                    font.drawString(matrixStackIn, s, lengthS, 10F, 0xFFFFFF);
                    font.drawString(matrixStackIn, s2, lengthS2, 20F, 0xFFFFFF);

                    RenderSystem.translatef(-50F, 0F, 50F);
                    RenderSystem.rotatef(90F, 0F, 1F, 0F);
                }
            });

            RenderSystem.popMatrix();

            double boop = Util.milliTime();
            RenderSystem.translated(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
            RenderSystem.rotatef((float) (boop * 40D % 360), 0, 1, 0);

            float scale = stack.getItem() instanceof BlockItem
                ? 0.85F
                : 0.65F;
            RenderSystem.scalef(scale, scale, scale);
            try {
                AssetUtil.renderItemInWorld(stack);
            } catch (Exception e) {
                ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a battery box! The item is " + stack.getItem().getRegistryName() + "!", e);
            }

            RenderSystem.popMatrix();
        }
    }
}
