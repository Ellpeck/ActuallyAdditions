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
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.ConnectionPair;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityLaserRelay extends TileEntityInventoryBase{

    public final LaserType type;

    private Network cachedNetwork;
    private int changeAmountAtCaching = -1;
    private int lastRange;

    public TileEntityLaserRelay(String name, LaserType type){
        super(1, name);
        this.type = type;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);

        if(type == NBTType.SYNC){
            ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(this.pos, this.world);

            NBTTagList list = compound.getTagList("Connections", 10);
            if(!list.hasNoTags()){
                for(int i = 0; i < list.tagCount(); i++){
                    ConnectionPair pair = new ConnectionPair();
                    pair.readFromNBT(list.getCompoundTagAt(i));
                    ActuallyAdditionsAPI.connectionHandler.addConnection(pair.getPositions()[0], pair.getPositions()[1], this.type, this.world, pair.doesSuppressRender());
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);

        if(type == NBTType.SYNC){
            NBTTagList list = new NBTTagList();

            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.pos, this.world);
            if(connections != null && !connections.isEmpty()){
                for(IConnectionPair pair : connections){
                    NBTTagCompound tag = new NBTTagCompound();
                    pair.writeToNBT(tag);
                    list.appendTag(tag);
                }
            }

            compound.setTag("Connections", list);
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        int range = this.getMaxRange();
        if(this.lastRange != range){
            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.pos, this.world);
            if(connections != null && !connections.isEmpty()){
                for(IConnectionPair pair : connections){
                    int distanceSq = (int)pair.getPositions()[0].distanceSq(pair.getPositions()[1]);
                    if(distanceSq > range*range){
                        ActuallyAdditionsAPI.connectionHandler.removeConnection(this.world, pair.getPositions()[0], pair.getPositions()[1]);
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

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(this.world.rand.nextInt(8) == 0){
            EntityPlayer player = Minecraft.getMinecraft().player;
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

    public Network getNetwork(){
        if(this.cachedNetwork == null || this.cachedNetwork.changeAmount != this.changeAmountAtCaching){
            this.cachedNetwork = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.pos, this.world);

            if(this.cachedNetwork != null){
                this.changeAmountAtCaching = this.cachedNetwork.changeAmount;
            }
            else{
                this.changeAmountAtCaching = -1;
            }
        }

        return this.cachedNetwork;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox(){
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public IItemHandler getItemHandler(EnumFacing facing){
        return null;
    }

    public int getMaxRange(){
        ItemStack upgrade = this.slots.getStackInSlot(0);
        if(StackUtil.isValid(upgrade) && upgrade.getItem() == InitItems.itemLaserUpgradeRange){
            return ConfigIntValues.LASER_RELAY_MAXIMUM_UPGRADED_DISTANCE.getValue();
        }
        else{
            return ConfigIntValues.LASER_RELAY_MAXIMUM_DISTANCE.getValue();
        }
    }

    @SideOnly(Side.CLIENT)
    public abstract String getExtraDisplayString();

    @SideOnly(Side.CLIENT)
    public abstract String getCompassDisplayString();

    public abstract void onCompassAction(EntityPlayer player);
}
