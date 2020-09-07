package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDisplayStand;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

// todo: migrate to client package
public class RenderDisplayStand extends TileEntityRenderer<TileEntityDisplayStand> {

    public RenderDisplayStand(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityDisplayStand tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
//        BlockPos pos = tile.getPos();

        ItemStack stack = tile.inv.getStackInSlot(0);
        if (StackUtil.isValid(stack)) {
            matrices.push();
//            matrices.translate((float) pos.getX() + 0.5F, (float) pos.getY() + 1F, (float) pos.getZ() + 0.5F);

            double boop = Util.nanoTime() / 800D;
            matrices.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
            matrices.rotate(new Quaternion((float) (boop * 40D % 360), 0, 1, 0));

            float scale = stack.getItem() instanceof BlockItem ? 0.85F : 0.65F;
            matrices.scale(scale, scale, scale);
            try {
                AssetUtil.renderItemInWorld(stack);
            } catch (Exception e) {
                ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a display stand! The item is " + stack.getItem().getRegistryName() + "!", e);
            }

            matrices.pop();
        }
    }
}
