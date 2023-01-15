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

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class RenderWorm extends EntityRenderer<EntityWorm> {

    private static ItemStack stack = ItemStack.EMPTY;

    public RenderWorm(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    public static void fixItemStack() {
        stack = new ItemStack(ActuallyItems.WORM.get());
    }

    @Override
    public void render(EntityWorm entity, float partialTicks, float p_225623_3_, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.pushPose();

        matrix.translate(0, 0.7F, 0);
        double boop = Util.getMillis() / 70D;
        matrix.mulPose(Vector3f.YP.rotationDegrees(-(float) (boop % 360)));
        matrix.translate(0,0,0.4);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemCameraTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrix, buffer
        );

        matrix.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWorm pEntity) {
        return PlayerContainer.BLOCK_ATLAS;
    }
}
