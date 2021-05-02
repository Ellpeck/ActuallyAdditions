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

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;

public class RenderLaserRelay extends TileEntityRenderer<TileEntityLaserRelay> {

    private static final float[] COLOR = new float[]{1F, 0F, 0F};
    private static final float[] COLOR_ITEM = new float[]{0F, 124F / 255F, 16F / 255F};
    private static final float[] COLOR_FLUIDS = new float[]{0F, 97F / 255F, 198F / 255F};
    private static final float[] COLOR_INFRARED = new float[]{209F / 255F, 179F / 255F, 239F / 255F};

    public RenderLaserRelay(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityLaserRelay tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        TileEntityLaserRelay relay = tile;
        boolean hasInvis = false;

        PlayerEntity player = Minecraft.getInstance().player;
        boolean hasGoggles = ItemEngineerGoggles.isWearing(player);

        ItemStack upgrade = relay.inv.getStackInSlot(0);
        if (StackUtil.isValid(upgrade)) {
            if (upgrade.getItem() == ActuallyItems.LASER_UPGRADE_INVISIBILITY.get()) {
                hasInvis = true;
            }

            ItemStack hand = player.getHeldItemMainhand();
            if (hasGoggles || StackUtil.isValid(hand) && (hand.getItem() == ConfigValues.itemCompassConfigurator || hand.getItem() instanceof ItemLaserWrench) || "themattabase".equals(player.getName().getString())) {
                matrices.push();

                float yTrans = 0.2f; //tile.getBlockMetadata() == 0 ? 0.2F : 0.8F; // TODO: [port][fix] no clue what this is
                matrices.translate(0.5F, yTrans, 0.5F);
                matrices.scale(0.2F, 0.2F, 0.2F);

                double boop = Util.milliTime() / 800D;
                matrices.rotate(new Quaternion((float) (boop * 40D % 360), 0, 1, 0)); // TODO: [port][test] this might not work

                AssetUtil.renderItemInWorld(upgrade, combinedLight, combinedOverlay, matrices, buffer);

                matrices.pop();
            }
        }

        ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(tile.getPos(), tile.getWorld());
        if (connections != null && !connections.isEmpty()) {
            for (IConnectionPair pair : connections) {
                if (!pair.doesSuppressRender() && tile.getPos().equals(pair.getPositions()[0])) {
                    BlockPos first = tile.getPos();
                    BlockPos second = pair.getPositions()[1];

                    TileEntity secondTile = tile.getWorld().getTileEntity(second);
                    if (secondTile instanceof TileEntityLaserRelay) {
                        ItemStack secondUpgrade = ((TileEntityLaserRelay) secondTile).inv.getStackInSlot(0);
                        boolean otherInvis = StackUtil.isValid(secondUpgrade) && secondUpgrade.getItem() == ActuallyItems.LASER_UPGRADE_INVISIBILITY.get();

                        if (hasGoggles || !hasInvis || !otherInvis) {
                            float[] color = hasInvis && otherInvis
                                ? COLOR_INFRARED
                                : relay.type == LaserType.ITEM
                                    ? COLOR_ITEM
                                    : relay.type == LaserType.FLUID
                                        ? COLOR_FLUIDS
                                        : COLOR;

                            AssetUtil.renderLaser(first.getX() + 0.5, first.getY() + 0.5, first.getZ() + 0.5, second.getX() + 0.5, second.getY() + 0.5, second.getZ() + 0.5, 120, hasInvis && otherInvis
                                ? 0.1F
                                : 0.35F, 0.05, color);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityLaserRelay tile) {
        return true;
    }
}
