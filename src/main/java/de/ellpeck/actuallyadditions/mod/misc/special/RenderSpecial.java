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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class RenderSpecial {

    private final ItemStack theThingToRender;

    public RenderSpecial(ItemStack stack) {
        this.theThingToRender = stack;
    }

    public void render(PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, Player player, float partialTick) {
        if (this.theThingToRender.isEmpty() || player.isInvisible() || !player.isModelPartShown(PlayerModelPart.CAPE) || player.isFallFlying()) {
            return;
        }

        matrixStack.pushPose();

        Vec3 currentPos = Minecraft.getInstance().player.getEyePosition(partialTick);
        Vec3 playerPos = player.getEyePosition(partialTick);
        matrixStack.translate(playerPos.x - currentPos.x, playerPos.y - currentPos.y, playerPos.z - currentPos.z);
        matrixStack.translate(0D, 2.575D - (player.isCrouching()
            ? 0.125D
            : 0D), 0D);


        matrixStack.pushPose();

        boolean isBlock = this.theThingToRender.getItem() instanceof BlockItem;
        if (isBlock) {
            matrixStack.translate(0D, -0.1875D, 0D);
        }
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));

        float size = isBlock
            ? 0.5F
            : 0.4F;
        matrixStack.scale(size, size, size);

        //Make the floaty stuff look nice using sine waves \o/ -xdjackiexd
        //Peck edit: What do you mean by "nice" you jackass? >_>
        double boop = Util.getMillis() / 1000D;
        matrixStack.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.25, 0D);
        matrixStack.mulPose(Axis.YP.rotationDegrees((float) (boop * 40D % 360)));

//        GlStateManager._disableLighting();
        matrixStack.pushPose();

        if (!isBlock) {
            matrixStack.translate(0D, 0.5D, 0D);
        }
        matrixStack.mulPose(Axis.XN.rotationDegrees(180F));
        Minecraft.getInstance().getItemRenderer().renderStatic(theThingToRender, ItemDisplayContext.FIXED, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer, null, 0);
        matrixStack.popPose();

//        GlStateManager._enableLighting();

        matrixStack.popPose();


        matrixStack.popPose();
    }
}
