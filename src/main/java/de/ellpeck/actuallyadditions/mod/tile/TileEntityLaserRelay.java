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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;

public abstract class TileEntityLaserRelay extends TileEntityInventoryBase {

    public static final int MAX_DISTANCE = 15;
    public static final int MAX_DISTANCE_RANGED = 35;

    public final LaserType type;

    private Network cachedNetwork;
    private int changeAmountAtCaching = -1;
    private int lastRange;
    // List of connections that are synced to the client for RenderLaserRelay to use
    private final ConcurrentSet<IConnectionPair> connections = new ConcurrentSet<>();

    public TileEntityLaserRelay(BlockEntityType<?> tileType, BlockPos pos, BlockState state, LaserType type) {
        super(tileType, pos, state,1);
        this.type = type;
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.readSyncableNBT(compound, lookupProvider, type);

        if (type == NBTType.SYNC) {
            ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(this.worldPosition, this.level);

            this.connections.clear();
            ListTag list = compound.getList("Connections", 10);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    ConnectionPair pair = new ConnectionPair();
                    this.connections.add(pair);
                    pair.readFromNBT(list.getCompound(i));
                    ActuallyAdditionsAPI.connectionHandler.addConnection(pair.getPositions()[0], pair.getPositions()[1], this.type, this.level, pair.doesSuppressRender());
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.writeSyncableNBT(compound, lookupProvider, type);

        if (type == NBTType.SYNC) {
            ListTag list = new ListTag();

            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.worldPosition, this.level);
            if (connections != null && !connections.isEmpty()) {
                for (IConnectionPair pair : connections) {
                    CompoundTag tag = new CompoundTag();
                    pair.writeToNBT(tag);
                    list.add(tag);
                }
            }

            compound.put("Connections", list);
        }
    }

    @Override
    protected void clientTick() {
        super.clientTick();
        int range = this.getMaxRange();
        if (this.lastRange != range) {
            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.worldPosition, this.level);
            if (connections != null && !connections.isEmpty()) {
                for (IConnectionPair pair : connections) {
                    int distanceSq = (int) pair.getPositions()[0].distSqr(pair.getPositions()[1]);
                    if (distanceSq > range * range) {
                        this.connections.remove(pair);
                        ActuallyAdditionsAPI.connectionHandler.removeConnection(this.level, pair.getPositions()[0], pair.getPositions()[1]);
                    }
                }
            }

            this.lastRange = range;
        }
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        int range = this.getMaxRange();
        if (this.lastRange != range) {
            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.worldPosition, this.level);
            if (connections != null && !connections.isEmpty()) {
                for (IConnectionPair pair : connections) {
                    int distanceSq = (int) pair.getPositions()[0].distSqr(pair.getPositions()[1]);
                    if (distanceSq > range * range) {
                        this.connections.remove(pair);
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

//    @Override TODO: Fix the renderBoundingBox
//    
//    public AABB getRenderBoundingBox() {
//        return INFINITE_EXTENT_AABB;
//    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public IItemHandler getItemHandler(Direction facing) {
        return null;
    }

    public int getMaxRange() {
        ItemStack upgrade = this.inv.getStackInSlot(0);
        if (StackUtil.isValid(upgrade) && upgrade.getItem() == ActuallyItems.LASER_UPGRADE_RANGE.get()) {
            return MAX_DISTANCE_RANGED;
        } else {
            return MAX_DISTANCE;
        }
    }

    
    public abstract Component getExtraDisplayString();

    
    public abstract Component getCompassDisplayString();

    public abstract void onCompassAction(Player player);

    public ConcurrentSet<IConnectionPair> getConnections() {
        return connections;
    }
}
