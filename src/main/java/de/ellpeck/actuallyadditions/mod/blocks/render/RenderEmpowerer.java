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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEmpowerer;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class RenderEmpowerer implements BlockEntityRenderer<TileEntityEmpowerer> {
    public RenderEmpowerer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityEmpowerer tile, float partialTicks, @Nonnull PoseStack matrices, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (!stack.isEmpty()) {
            // TODO: [port][refactor] migrate this logic into a single method, most renders use it
            matrices.pushPose();
            matrices.translate(0.5F, 1F, 0.5F);

            float boop = Util.getMillis() / 800F;
            matrices.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
            matrices.mulPose(Axis.YP.rotationDegrees(boop * 40F % 360.0F));

            float scale = stack.getItem() instanceof BlockItem
                ? 0.85F
                : 0.65F;
            matrices.scale(scale, scale, scale);
            try {
                AssetUtil.renderItemInWorld(stack, combinedLight, combinedOverlay, matrices, buffer);
            } catch (Exception e) {
                ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in an empowerer! The item is {}!", BuiltInRegistries.ITEM.getKey(stack.getItem()), e);
            }

            matrices.popPose();
        }
/*
        int index = tile.recipeForRenderIndex;
        if (index >= 0 && ActuallyAdditionsAPI.EMPOWERER_RECIPES.size() > index) {
            EmpowererRecipe recipe = ActuallyAdditionsAPI.EMPOWERER_RECIPES.get(index);
            if (recipe != null) {
                for (int i = 0; i < 3; i++) {
                    Direction facing = Direction.from2DDataValue(i); // TODO: [port][test] validate this works
                    BlockPos offset = tile.getBlockPos().relative(facing, 3);

                    AssetUtil.renderLaser(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 0.5, tile.getBlockPos().getZ() + 0.5, offset.getX() + 0.5, offset.getY() + 0.95, offset.getZ() + 0.5, 80, 1F, 0.1F, recipe.getParticleColors());
                }
            }
        }

 */
        if (tile.getCurrentRecipe() != null) {
            RecipeHolder<EmpowererRecipe> holder = tile.getCurrentRecipe();
            for (int i = 0; i <= 3; i++) {
                Direction facing = Direction.from2DDataValue(i);
                BlockPos offset = new BlockPos(0,0,0).relative(facing, 3);

                AssetUtil.renderLaser(matrices, buffer, new Vec3(0.0d, 0.0d, 0.0d), new Vec3(offset.getX(), offset.getY() + 0.45, offset.getZ()), 80, holder.value().getParticleColors(), 1.0f ,0.1F);
            }
        }
    }
}
