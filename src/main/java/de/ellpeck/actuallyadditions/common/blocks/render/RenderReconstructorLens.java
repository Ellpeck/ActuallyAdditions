package de.ellpeck.actuallyadditions.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.StackUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

// todo: migrate to client package
public class RenderReconstructorLens extends TileEntityRenderer<TileEntityAtomicReconstructor> {

    public RenderReconstructorLens(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityAtomicReconstructor tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack stack = tile.inv.getStackInSlot(0);

        if (StackUtil.isValid(stack) && stack.getItem() instanceof ILensItem) {
            matrices.push();
//            matrices.translate((float) pos.getX() + 0.5F, (float) pos.getY() - 0.5F, (float) pos.getZ() + 0.5F);
            matrices.rotate(new Quaternion(180F, 0.0F, 0.0F, 1.0F));

            BlockState state = tile.getWorld().getBlockState(tile.getPos());
//            int meta = state.getBlock().getMetaFromState(state);
            int meta = 0; // todo: fix this logic, I'm assuming it's facing meta but I don't know
            if (meta == 0) {
                matrices.translate(0F, -0.5F, 0F);
                matrices.rotate(new Quaternion(90F, 1F, 0F, 0F));
            }
            if (meta == 1) {
                matrices.translate(0F, -1.5F - 0.5F / 16F, 0F);
                matrices.rotate(new Quaternion(90F, 1F, 0F, 0F));
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
                matrices.rotate(new Quaternion(90F, 0F, 1F, 0F));
            }
            if (meta == 5) {
                matrices.translate(0F, -1F, 0F);
                matrices.translate(-0.5F, 0F, 0F);
                matrices.rotate(new Quaternion(90F, 0F, 1F, 0F));
            }

            matrices.scale(0.5F, 0.5F, 0.5F);
            AssetUtil.renderItemInWorld(stack);

            matrices.pop();
        }
    }
}
