/*
 * This file ("RenderSpecial.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.special;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class RenderSpecial {

    private final ItemStack theThingToRender;

    public RenderSpecial(ItemStack stack) {
        this.theThingToRender = stack;
    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, PlayerEntity player, float partialTicks) {
        if (this.theThingToRender.isEmpty() || player.isInvisible() || !player.isModelPartShown(PlayerModelPart.CAPE) || player.isFallFlying()) {
            return;
        }

        matrixStack.pushPose();

        Vector3d currentPos = Minecraft.getInstance().player.getEyePosition(partialTicks);
        Vector3d playerPos = player.getEyePosition(partialTicks);
        matrixStack.translate(playerPos.x - currentPos.x, playerPos.y - currentPos.y, playerPos.z - currentPos.z);
        matrixStack.translate(0D, 2.575D - (player.isCrouching()
            ? 0.125D
            : 0D), 0D);


        matrixStack.pushPose();

        boolean isBlock = this.theThingToRender.getItem() instanceof BlockItem;
        if (isBlock) {
            matrixStack.translate(0D, -0.1875D, 0D);
        }
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));

        float size = isBlock
            ? 0.5F
            : 0.4F;
        matrixStack.scale(size, size, size);

        //Make the floaty stuff look nice using sine waves \o/ -xdjackiexd
        //Peck edit: What do you mean by "nice" you jackass? >_>
        double boop = Util.getMillis() / 1000D;
        matrixStack.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.25, 0D);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) (boop * 40D % 360)));

        GlStateManager._disableLighting();
        matrixStack.pushPose();

        if (!isBlock) {
            matrixStack.translate(0D, 0.5D, 0D);
        }
        matrixStack.mulPose(Vector3f.XN.rotationDegrees(180F));
        Minecraft.getInstance().getItemRenderer().renderStatic(theThingToRender, ItemCameraTransforms.TransformType.FIXED, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        matrixStack.popPose();

        GlStateManager._enableLighting();

        matrixStack.popPose();


        matrixStack.popPose();
    }
}
