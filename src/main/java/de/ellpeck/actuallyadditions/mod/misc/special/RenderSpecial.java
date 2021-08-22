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
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;

public class RenderSpecial {

    private final ItemStack theThingToRender;

    public RenderSpecial(ItemStack stack) {
        this.theThingToRender = stack;
    }

    public void render(MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight, PlayerEntity player, float partialTicks) {
        if (player.isInvisible() || !player.isModelPartShown(PlayerModelPart.CAPE) || player.isFallFlying()) {
            return;
        }

        GlStateManager._pushMatrix();

        Vector3d currentPos = Minecraft.getInstance().player.getEyePosition(partialTicks);
        Vector3d playerPos = player.getEyePosition(partialTicks);
        GlStateManager._translated(playerPos.x - currentPos.x, playerPos.y - currentPos.y, playerPos.z - currentPos.z);
        GlStateManager._translated(0D, 2.575D - (player.isShiftKeyDown()
            ? 0.125D
            : 0D), 0D);

        this.render(matrices, buffer, combinedLight);
        GlStateManager._popMatrix();
    }

    public void render(MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight) {
        if (StackUtil.isValid(this.theThingToRender)) {
            boolean isBlock = this.theThingToRender.getItem() instanceof BlockItem;

            GlStateManager._pushMatrix();

            if (isBlock) {
                GlStateManager._translated(0D, -0.1875D, 0D);
            }
            GlStateManager._rotatef(180F, 1.0F, 0.0F, 1.0F);

            float size = isBlock
                ? 0.5F
                : 0.4F;
            GlStateManager._scalef(size, size, size);

            //Make the floaty stuff look nice using sine waves \o/ -xdjackiexd
            //Peck edit: What do you mean by "nice" you jackass? >_>
            double boop = Util.getMillis() / 1000D;
            GlStateManager._translated(0D, Math.sin(boop % (2 * Math.PI)) * 0.25, 0D);
            GlStateManager._rotatef((float) (boop * 40D % 360), 0, 1, 0);

            GlStateManager._disableLighting();
            GlStateManager._pushMatrix();

            if (!isBlock) {
                GlStateManager._translated(0D, 0.5D, 0D);
            }
            GlStateManager._rotatef(180F, 1F, 0F, 0F);
            AssetUtil.renderItemInWorld(this.theThingToRender, combinedLight, OverlayTexture.NO_OVERLAY, matrices, buffer);
            GlStateManager._popMatrix();

            GlStateManager._enableLighting();

            GlStateManager._popMatrix();
        }
    }

}
