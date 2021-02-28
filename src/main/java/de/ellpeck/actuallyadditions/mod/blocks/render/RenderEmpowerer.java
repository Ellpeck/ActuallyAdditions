/*
 * This file ("RenderEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEmpowerer;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;

public class RenderEmpowerer extends TileEntityRenderer<TileEntityEmpowerer> {
    public RenderEmpowerer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityEmpowerer tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (StackUtil.isValid(stack)) {
            // TODO: [port][refactor] migrate this logic into a single method, most renders use it
            matrices.push();
            matrices.translate(0.5F, 1F, 0.5F);

            double boop = Util.milliTime() / 800D;
            matrices.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
            matrices.rotate(new Quaternion((float) (boop * 40D % 360), 0, 1, 0)); // TODO: [port] might not work

            float scale = stack.getItem() instanceof BlockItem
                ? 0.85F
                : 0.65F;
            matrices.scale(scale, scale, scale);
            try {
                AssetUtil.renderItemInWorld(stack, combinedLight, combinedOverlay, matrices, buffer);
            } catch (Exception e) {
                ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in an empowerer! The item is " + stack.getItem().getRegistryName() + "!", e);
            }

            matrices.pop();
        }

        int index = tile.recipeForRenderIndex;
        if (index >= 0 && ActuallyAdditionsAPI.EMPOWERER_RECIPES.size() > index) {
            EmpowererRecipe recipe = ActuallyAdditionsAPI.EMPOWERER_RECIPES.get(index);
            if (recipe != null) {
                for (int i = 0; i < 3; i++) {
                    Direction facing = Direction.byHorizontalIndex(i); // TODO: [port][test] validate this works
                    BlockPos offset = tile.getPos().offset(facing, 3);

                    AssetUtil.renderLaser(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5, offset.getX() + 0.5, offset.getY() + 0.95, offset.getZ() + 0.5, 80, 1F, 0.1F, recipe.getParticleColors());
                }
            }
        }
    }
}
