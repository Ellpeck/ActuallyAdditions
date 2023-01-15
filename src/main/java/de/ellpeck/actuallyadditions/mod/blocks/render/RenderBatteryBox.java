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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;

@OnlyIn(Dist.CLIENT)
public class RenderBatteryBox extends TileEntityRenderer<TileEntityBatteryBox> {
    public RenderBatteryBox(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityBatteryBox tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemBattery)) {
            return;
        }

        matrices.pushPose();
        matrices.translate(.5f, .35f, .5f);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));

        matrices.pushPose();
        matrices.scale(0.0075F, 0.0075F, 0.0075F);
        matrices.translate(0F, 0F, -60F);

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> {
            FontRenderer font = Minecraft.getInstance().font;

            String energyTotal = Lang.cleanEnergyValues(cap, false);
            String energyName = I18n.get("misc.actuallyadditions.power_name_long");

            for (int i = 0; i < 4; i++) {
                font.draw(matrices, energyTotal, -font.width(energyTotal) / 2F, 10F, 0xFFFFFF);
                font.draw(matrices, energyName, -font.width(energyName) / 2F, 20F, 0xFFFFFF);

                matrices.translate(-60F, 0F, 60F);
                matrices.mulPose(Vector3f.YP.rotationDegrees(90));
            }
        });

        matrices.popPose(); // text rotation
        matrices.popPose(); // rotation + centering

        double boop = Util.getMillis() / 800D;
        float scale = stack.getItem() instanceof BlockItem
            ? 0.85F
            : 0.65F;

        matrices.pushPose();
        matrices.translate(.5f, 1f + Math.sin(boop % (2 * Math.PI)) * 0.065, .5f);
        matrices.mulPose(Vector3f.YP.rotationDegrees((float) (boop * 40D % 360)));
        matrices.scale(scale, scale, scale);

        try {
            AssetUtil.renderItemInWorld(stack, combinedLight, combinedOverlay, matrices, buffer);
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a battery box! The item is " + stack.getItem().getRegistryName() + "!", e);
        }

        matrices.popPose();
    }
}
