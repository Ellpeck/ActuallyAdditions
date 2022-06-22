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
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Quaternion;

public class ReconstructorRenderer extends TileEntityRenderer<TileEntityAtomicReconstructor> {

    public ReconstructorRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityAtomicReconstructor tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        //default color 0x1b6dff

        if (tile.getProgress() > 0) {
            Direction direction = tile.getOrientation();
            float rot = 360.0f - direction.getOpposite().toYRot(); //Sigh...
            float pitch = 0;
            if (direction == Direction.UP) {
                pitch = 90;
            } else if (direction == Direction.DOWN) {
                pitch = -90;
            }

            AssetUtil.renderLaser(matrices, buffer, 0, 0, 0, rot, pitch, 5, 0, 0x1b6dff, 0.8f * tile.getProgress(), 0.2f);
            tile.decTTL();
        }
        if (!StackUtil.isValid(stack) || !(stack.getItem() instanceof ILensItem)) {
            return;
        }

        matrices.pushPose();
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.mulPose(new Quaternion(180F, 0.0F, 0.0F, 1.0F));

        BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
        int meta = 0; //state.getBlock().getMetaFromState(state); // TODO: [port][fix] this needs to be checking direction not meta
        if (meta == 0) {
            matrices.translate(0F, -0.5F, 0F);
            matrices.mulPose(new Quaternion(90F, 1F, 0F, 0F));
        }
        if (meta == 1) {
            matrices.translate(0F, -1.5F - 0.5F / 16F, 0F);
            matrices.mulPose(new Quaternion(90F, 1F, 0F, 0F));
        }
        if (meta == 2) {
            matrices.translate(0F, -1F, 0F);
            matrices.translate(0F, 0F, -0.5F);
        }
        if (meta == 3) {
            matrices.translate(0F, -1F, 0F);
            matrices.translate(0F, 0F, 0.5F + 0.5F / 16F);
        }
        if (meta == 4) {
            matrices.translate(0F, -1F, 0F);
            matrices.translate(0.5F + 0.5F / 16F, 0F, 0F);
            matrices.mulPose(new Quaternion(90F, 0F, 1F, 0F));
        }
        if (meta == 5) {
            matrices.translate(0F, -1F, 0F);
            matrices.translate(-0.5F, 0F, 0F);
            matrices.mulPose(new Quaternion(90F, 0F, 1F, 0F));
        }

        matrices.scale(0.5F, 0.5F, 0.5F);
        AssetUtil.renderItemInWorld(stack, combinedLight, combinedOverlay, matrices, buffer);

        matrices.popPose();
    }
}
