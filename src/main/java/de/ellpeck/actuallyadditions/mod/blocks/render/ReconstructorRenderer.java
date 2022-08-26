/*
 * This file ("RenderReconstructorLens.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class ReconstructorRenderer extends TileEntityRenderer<TileEntityAtomicReconstructor> {

    public ReconstructorRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityAtomicReconstructor tile, float partialTicks, @Nonnull MatrixStack matrices, @Nonnull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        //default color 0x1b6dff
        int color = tile.getBeamColor();
        int length = 5;
        Direction direction = tile.getOrientation();
        float rot = 360.0f - direction.getOpposite().toYRot(); //Sigh...
        float pitch = 0;
        if (direction == Direction.UP) {
            pitch = 90;
        } else if (direction == Direction.DOWN) {
            pitch = -90;
        }

        if (stack.getItem() instanceof ILensItem) {
            length = ((ILensItem) stack.getItem()).getLens().getDistance();
        }

        if (tile.getProgress() > 0) {
            AssetUtil.renderLaser(matrices, buffer, 0, 0, 0, rot, pitch, length, 0, color, 0.8f * tile.getProgress(), 0.2f);
            tile.decTTL();
        }
        if (stack.isEmpty() || !(stack.getItem() instanceof ILensItem)) {
            return;
        }

        matrices.pushPose();
        matrices.translate(0.5F, 0.5F, 0.5F);

        matrices.mulPose(Vector3f.YP.rotationDegrees(rot));
        matrices.mulPose(Vector3f.XP.rotationDegrees(pitch));

        matrices.translate(0.0F, 0.0F, -0.5F);

        matrices.scale(0.5F, 0.5F, 0.5F);
        int lightColor = WorldRenderer.getLightColor(tile.getLevel(), tile.getPosition().relative(direction));
        AssetUtil.renderItemInWorld(stack, lightColor, combinedOverlay, matrices, buffer);

        matrices.popPose();
    }
}
