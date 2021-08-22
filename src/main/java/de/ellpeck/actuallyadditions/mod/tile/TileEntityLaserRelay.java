/*
 * This file ("TileEntityLaserRelay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.ConnectionPair;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public abstract class TileEntityLaserRelay extends TileEntityInventoryBase {

    public static final int MAX_DISTANCE = 15;
    public static final int MAX_DISTANCE_RANGED = 35;

    public final LaserType type;

    private Network cachedNetwork;
    private int changeAmountAtCaching = -1;
    private int lastRange;

    public TileEntityLaserRelay(TileEntityType<?> tileType, LaserType type) {
        super(tileType, 1);
        this.type = type;
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        if (type == NBTType.SYNC) {
            ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(this.worldPosition, this.level);

            ListNBT list = compound.getList("Connections", 10);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    ConnectionPair pair = new ConnectionPair();
                    pair.readFromNBT(list.getCompound(i));
                    ActuallyAdditionsAPI.connectionHandler.addConnection(pair.getPositions()[0], pair.getPositions()[1], this.type, this.level, pair.doesSuppressRender());
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        if (type == NBTType.SYNC) {
            ListNBT list = new ListNBT();

            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.worldPosition, this.level);
            if (connections != null && !connections.isEmpty()) {
                for (IConnectionPair pair : connections) {
                    CompoundNBT tag = new CompoundNBT();
                    pair.writeToNBT(tag);
                    list.add(tag);
                }
            }

            compound.put("Connections", list);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        int range = this.getMaxRange();
        if (this.lastRange != range) {
            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.worldPosition, this.level);
            if (connections != null && !connections.isEmpty()) {
                for (IConnectionPair pair : connections) {
                    int distanceSq = (int) pair.getPositions()[0].distSqr(pair.getPositions()[1]);
                    if (distanceSq > range * range) {
                        ActuallyAdditionsAPI.connectionHandler.removeConnection(this.level, pair.getPositions()[0], pair.getPositions()[1]);
                    }
                }
            }

            this.lastRange = range;
        }
    }

    /*@Override
    public void updateEntity(){
        super.updateEntity();
        if(this.world.isRemote){
            this.renderParticles();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void renderParticles(){
        if(this.world.rand.nextInt(8) == 0){
            PlayerEntity player = Minecraft.getInstance().player;
            if(player != null){
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
                WrenchMode mode = WrenchMode.values()[data.theCompound.getInteger("LaserWrenchMode")];
                if(mode != WrenchMode.NO_PARTICLES){
                    ItemStack stack = player.getHeldItemMainhand();
                    if(mode == WrenchMode.ALWAYS_PARTICLES || (StackUtil.isValid(stack) && stack.getItem() instanceof ItemLaserWrench)){
                        Network network = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.pos, this.world);
                        if(network != null){
                            for(IConnectionPair aPair : network.connections){
                                if(!aPair.doesSuppressRender() && aPair.contains(this.pos) && this.pos.equals(aPair.getPositions()[0])){
                                    AssetUtil.renderParticlesFromAToB(aPair.getPositions()[0].getX(), aPair.getPositions()[0].getY(), aPair.getPositions()[0].getZ(), aPair.getPositions()[1].getX(), aPair.getPositions()[1].getY(), aPair.getPositions()[1].getZ(), this.world.rand.nextInt(3)+1, 0.8F, this.type == LaserType.ITEM ? COLOR_ITEM : (this.type == LaserType.FLUID ? COLOR_FLUIDS : COLOR), 1F);
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    public Network getNetwork() {
        if (this.cachedNetwork == null || this.cachedNetwork.changeAmount != this.changeAmountAtCaching) {
            this.cachedNetwork = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.worldPosition, this.level);

            if (this.cachedNetwork != null) {
                this.changeAmountAtCaching = this.cachedNetwork.changeAmount;
            } else {
                this.changeAmountAtCaching = -1;
            }
        }

        return this.cachedNetwork;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        return LazyOptional.empty();
    }

    public int getMaxRange() {
        ItemStack upgrade = this.inv.getStackInSlot(0);
        if (StackUtil.isValid(upgrade) && upgrade.getItem() == ActuallyItems.LASER_UPGRADE_RANGE.get()) {
            return MAX_DISTANCE_RANGED;
        } else {
            return MAX_DISTANCE;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract String getExtraDisplayString();

    @OnlyIn(Dist.CLIENT)
    public abstract String getCompassDisplayString();

    public abstract void onCompassAction(PlayerEntity player);
}
