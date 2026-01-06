/*
 * This file ("RenderWorm.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;



public class RenderWorm extends EntityRenderer<EntityWorm> {

    private ItemStack stack;
    private ItemStack snailStack;

    public RenderWorm(EntityRendererProvider.Context context) {
        super(context);

        stack = new ItemStack(ActuallyItems.WORM.get());
        snailStack = new ItemStack(ActuallyItems.WORM.get());
        snailStack.set(DataComponents.CUSTOM_NAME, Component.literal("Snail Mail"));
    }

    @Override
    public void render(EntityWorm worm, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Component customName = worm.getCustomName();
        boolean isSnail = customName != null && customName.getString().equalsIgnoreCase("snail mail");
        poseStack.pushPose();
        poseStack.translate(0, 0.75F, 0);
        double boop = Util.getMillis() / 70D;
        poseStack.mulPose(Axis.YP.rotationDegrees(-(float) (boop % 360)));
        poseStack.translate(0,0,0.4);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                isSnail? snailStack:stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, null, 0
        );

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWorm worm) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
