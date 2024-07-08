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
    public void render(EntityWorm entity, float partialTicks, float p_225623_3_, PoseStack matrix, MultiBufferSource buffer, int light) {
        boolean isSnail = entity.getCustomName().getString().equalsIgnoreCase("snail mail");
        matrix.pushPose();
        matrix.translate(0, 0.75F, 0);
        double boop = Util.getMillis() / 70D;
        matrix.mulPose(Axis.YP.rotationDegrees(-(float) (boop % 360)));
        matrix.translate(0,0,0.4);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                isSnail? snailStack:stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, matrix, buffer, null, 0
        );

        matrix.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWorm pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
