/*
 * This file ("RenderDisplayStand.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDisplayStand;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class RenderDisplayStand implements BlockEntityRenderer<TileEntityDisplayStand> {
    public RenderDisplayStand(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityDisplayStand tile, float partialTicks, @Nonnull PoseStack matrices, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (stack.isEmpty()) {
            return;
        }

        matrices.pushPose();
        matrices.translate(0.5F, 1F, 0.5F);

        float boop = Util.getMillis() / 800F;
        matrices.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
        matrices.mulPose(Axis.YP.rotationDegrees(boop * 40F % 360.0F));

        float scale = stack.getItem() instanceof BlockItem
            ? 0.85F
            : 0.65F;
        matrices.scale(scale, scale, scale);
        try {
            AssetUtil.renderItemInWorld(stack, combinedLightIn, combinedOverlayIn, matrices, buffer);
        } catch (Exception e) {
	        ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a display stand! The item is {}!", BuiltInRegistries.ITEM.getKey(stack.getItem()), e);
        }

        matrices.popPose();
    }
}
