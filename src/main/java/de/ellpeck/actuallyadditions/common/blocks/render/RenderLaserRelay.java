package de.ellpeck.actuallyadditions.common.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.common.config.ConfigValues;
import de.ellpeck.actuallyadditions.common.items.InitItems;
import de.ellpeck.actuallyadditions.common.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.common.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.common.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

// todo: migrate to client package
public class RenderLaserRelay extends TileEntityRenderer<TileEntityLaserRelay> {

    private static final float[] COLOR = new float[] { 1F, 0F, 0F };
    private static final float[] COLOR_ITEM = new float[] { 0F, 124F / 255F, 16F / 255F };
    private static final float[] COLOR_FLUIDS = new float[] { 0F, 97F / 255F, 198F / 255F };
    private static final float[] COLOR_INFRARED = new float[] { 209F / 255F, 179F / 255F, 239F / 255F };

    public RenderLaserRelay(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityLaserRelay tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityLaserRelay relay = tile;
        boolean hasInvis = false;

//        BlockPos pos = tile.getPos();
        PlayerEntity player = Minecraft.getInstance().player;
        boolean hasGoggles = ItemEngineerGoggles.isWearing(player);

        ItemStack upgrade = relay.inv.getStackInSlot(0);
        if (StackUtil.isValid(upgrade)) {
            if (upgrade.getItem() == InitItems.itemLaserUpgradeInvisibility) {
                hasInvis = true;
            }

            ItemStack hand = player.getHeldItemMainhand();
            if (hasGoggles || StackUtil.isValid(hand) && (hand.getItem() == ConfigValues.itemCompassConfigurator || hand.getItem() instanceof ItemLaserWrench) || "themattabase".equals(player.getName())) {
                matrices.push();

                float yTrans = .2f; // tile.getBlockMetadata() == 0 ? 0.2F : 0.8F; old logic for meta data
//                matrices.translate((float) pos.getX() + 0.5F, (float) pos.getY() + yTrans, (float) pos.getZ() + 0.5F);
                matrices.scale(0.2F, 0.2F, 0.2F);

                double boop = Util.nanoTime() / 800D;
                matrices.rotate(new Quaternion((float) (boop * 40D % 360), 0, 1, 0));

                AssetUtil.renderItemInWorld(upgrade);

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
                        boolean otherInvis = StackUtil.isValid(secondUpgrade) && secondUpgrade.getItem() == InitItems.itemLaserUpgradeInvisibility;

                        if (hasGoggles || !hasInvis || !otherInvis) {
                            float[] color = hasInvis && otherInvis ? COLOR_INFRARED : relay.type == LaserType.ITEM ? COLOR_ITEM : relay.type == LaserType.FLUID ? COLOR_FLUIDS : COLOR;

                            AssetUtil.renderLaser(first.getX() + 0.5, first.getY() + 0.5, first.getZ() + 0.5, second.getX() + 0.5, second.getY() + 0.5, second.getZ() + 0.5, 120, hasInvis && otherInvis ? 0.1F : 0.35F, 0.05, color);
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
