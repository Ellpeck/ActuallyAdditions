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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.Lang;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.joml.Matrix4f;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class RenderBatteryBox implements BlockEntityRenderer<TileEntityBatteryBox> {
    public RenderBatteryBox(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityBatteryBox tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemBattery)) {
            return;
        }

        matrices.pushPose();
        matrices.translate(.5f, .35f, .5f);
        matrices.mulPose(Axis.ZP.rotationDegrees(180));

        matrices.pushPose();
        matrices.scale(0.0075F, 0.0075F, 0.0075F);
        matrices.translate(0F, 0F, -60F);

        Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM)).ifPresent(cap -> {
            Font font = Minecraft.getInstance().font;

            String energyTotal = Lang.cleanEnergyValues(cap, false);
            String energyName = I18n.get("misc.actuallyadditions.power_name_long");
            float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int j = (int) (backgroundOpacity * 255.0F) << 24;

            for (int i = 0; i < 4; i++) {
                matrices.pushPose();

                matrices.translate(-60F, 0F, 60F);
                matrices.mulPose(Axis.YP.rotationDegrees(90));
                Matrix4f pose = matrices.last().pose();
                font.drawInBatch(energyTotal, -font.width(energyTotal) / 2F, 10F, 0, false, pose, buffer, Font.DisplayMode.SEE_THROUGH, j, combinedLight, true);
                font.drawInBatch(energyName, -font.width(energyTotal) / 2F, 20F, 0, false, pose, buffer, Font.DisplayMode.SEE_THROUGH, j, combinedLight, true);
//                font.draw(matrices, energyTotal, -font.width(energyTotal) / 2F, 10F, 0xFFFFFF);
//                font.draw(matrices, energyName, -font.width(energyName) / 2F, 20F, 0xFFFFFF);

                matrices.popPose();
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
        matrices.mulPose(Axis.YP.rotationDegrees((float) (boop * 40D % 360)));
        matrices.scale(scale, scale, scale);

        try {
            AssetUtil.renderItemInWorld(stack, combinedLight, combinedOverlay, matrices, buffer);
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a battery box! The item is " + BuiltInRegistries.ITEM.getKey(stack.getItem()) + "!", e);
        }

        matrices.popPose();
    }
}
