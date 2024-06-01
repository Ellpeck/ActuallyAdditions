/*
 * This file ("RenderLaserRelay.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RenderLaserRelay implements BlockEntityRenderer<TileEntityLaserRelay> {

    private static final int COLOR = 16711680;
    private static final int COLOR_ITEM = 31760;
    private static final int COLOR_FLUIDS = 25030;
    private static final int COLOR_INFRARED = 13743087;

    public RenderLaserRelay(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityLaserRelay tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        TileEntityLaserRelay relay = tile;
        BlockState state = tile.getBlockState();
        boolean hasInvis = false;

        Player player = Minecraft.getInstance().player;
        boolean hasGoggles = ItemEngineerGoggles.isWearing(player);

        ItemStack upgrade = relay.inv.getStackInSlot(0);
        if (StackUtil.isValid(upgrade)) {
            if (upgrade.getItem() == ActuallyItems.LASER_UPGRADE_INVISIBILITY.get()) {
                hasInvis = true;
            }

            ItemStack hand = player.getMainHandItem();
            if (hasGoggles || StackUtil.isValid(hand) && (hand.getItem() == CommonConfig.Other.relayConfigureItem || hand.getItem() instanceof ItemLaserWrench) || "themattabase".equals(player.getName().getString())) {
                matrices.pushPose();
                Direction direction = state.hasProperty(BlockStateProperties.FACING) ?
                        state.getValue(BlockStateProperties.FACING) : Direction.UP;

                float yTrans = direction.getAxis() == Direction.Axis.Y ? 0.2f : 0.8F; //tile.getBlockMetadata() == 0 ? 0.2F : 0.8F; // TODO: [port][fix] no clue what this is
                matrices.translate(0.5F, yTrans, 0.5F);
                matrices.scale(0.2F, 0.2F, 0.2F);

                double boop = Util.getMillis() / 800D;
                matrices.mulPose(Axis.YP.rotationDegrees((float) (boop * 40D % 360)));

                AssetUtil.renderItemInWorld(upgrade, combinedLight, combinedOverlay, matrices, buffer);

                matrices.popPose();
            }
        }

        ConcurrentSet<IConnectionPair> connections = tile.getConnections();
        if (connections != null && !connections.isEmpty()) {
            for (IConnectionPair pair : connections) {
                if (!pair.doesSuppressRender() && tile.getBlockPos().equals(pair.getPositions()[0])) {
                    BlockPos first = tile.getBlockPos();
                    BlockPos second = pair.getPositions()[1];

                    BlockEntity secondTile = tile.getLevel().getBlockEntity(second);
                    if (secondTile instanceof TileEntityLaserRelay) {
                        ItemStack secondUpgrade = ((TileEntityLaserRelay) secondTile).inv.getStackInSlot(0);
                        boolean otherInvis = StackUtil.isValid(secondUpgrade) && secondUpgrade.getItem() == ActuallyItems.LASER_UPGRADE_INVISIBILITY.get();

                        if (hasGoggles || !hasInvis || !otherInvis) {
                            int color = hasInvis && otherInvis
                                    ? COLOR_INFRARED : relay.type == LaserType.ITEM
                                    ? COLOR_ITEM : relay.type == LaserType.FLUID
                                    ? COLOR_FLUIDS : COLOR;

                            BlockPos offsetStart = first.subtract(tile.getBlockPos());
                            BlockPos offsetEnd = second.subtract(tile.getBlockPos());
                            offsetEnd = offsetEnd.rotate(Rotation.CLOCKWISE_90);

                            matrices.pushPose();

                            AssetUtil.renderLaser(matrices, buffer,
                                    new Vec3(offsetStart.getX(), offsetStart.getY(), offsetStart.getZ()),
                                    new Vec3(offsetEnd.getX(), offsetEnd.getY(), offsetEnd.getZ()),
                                    120, color,
                                    hasInvis && otherInvis
                                            ? 0.1F
                                            : 0.35F, 0.05F);

                            matrices.popPose();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(TileEntityLaserRelay tile) {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityLaserRelay blockEntity) {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public int getViewDistance() {
        return 32;
    }
}
