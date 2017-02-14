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


import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLaserRelay extends TileEntitySpecialRenderer{

    private static final float[] COLOR = new float[]{1F, 0F, 0F};
    private static final float[] COLOR_ITEM = new float[]{0F, 124F/255F, 16F/255F};
    private static final float[] COLOR_FLUIDS = new float[]{0F, 97F/255F, 198F/255F};
    private static final float[] COLOR_INFRARED = new float[]{209F/255F, 179F/255F, 239F/255F};

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int par6){
        if(tile instanceof TileEntityLaserRelay){
            TileEntityLaserRelay relay = (TileEntityLaserRelay)tile;
            boolean hasInvis = false;

            EntityPlayer player = Minecraft.getMinecraft().player;
            boolean hasGoggles = ItemEngineerGoggles.isWearing(player);

            ItemStack upgrade = relay.slots.getStackInSlot(0);
            if(StackUtil.isValid(upgrade)){
                if(upgrade.getItem() == InitItems.itemLaserUpgradeInvisibility){
                    hasInvis = true;
                }

                ItemStack hand = player.getHeldItemMainhand();
                if(hasGoggles || (StackUtil.isValid(hand) && (hand.getItem() instanceof ItemCompass || hand.getItem() instanceof ItemLaserWrench)) || "themattabase".equals(player.getName())){
                    GlStateManager.pushMatrix();

                    float yTrans = tile.getBlockMetadata() == 0 ? 0.2F : 0.8F;
                    GlStateManager.translate((float)x+0.5F, (float)y+yTrans, (float)z+0.5F);
                    GlStateManager.scale(0.2F, 0.2F, 0.2F);

                    double boop = Minecraft.getSystemTime()/800D;
                    GlStateManager.rotate((float)(((boop*40D)%360)), 0, 1, 0);

                    AssetUtil.renderItemInWorld(upgrade);

                    GlStateManager.popMatrix();
                }
            }

            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(tile.getPos(), tile.getWorld());
            if(connections != null && !connections.isEmpty()){
                for(IConnectionPair pair : connections){
                    if(!pair.doesSuppressRender() && tile.getPos().equals(pair.getPositions()[0])){
                        BlockPos first = tile.getPos();
                        BlockPos second = pair.getPositions()[1];

                        TileEntity secondTile = tile.getWorld().getTileEntity(second);
                        if(secondTile instanceof TileEntityLaserRelay){
                            ItemStack secondUpgrade = ((TileEntityLaserRelay)secondTile).slots.getStackInSlot(0);
                            boolean otherInvis = StackUtil.isValid(secondUpgrade) && secondUpgrade.getItem() == InitItems.itemLaserUpgradeInvisibility;

                            if(hasGoggles || !hasInvis || !otherInvis){
                                float[] color = hasInvis && otherInvis ? COLOR_INFRARED : (relay.type == LaserType.ITEM ? COLOR_ITEM : (relay.type == LaserType.FLUID ? COLOR_FLUIDS : COLOR));

                                AssetUtil.renderLaser(first.getX()+0.5, first.getY()+0.5, first.getZ()+0.5, second.getX()+0.5, second.getY()+0.5, second.getZ()+0.5, 120, hasInvis && otherInvis ? 0.1F : 0.35F, 0.05, color);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntity tile){
        return true;
    }
}
