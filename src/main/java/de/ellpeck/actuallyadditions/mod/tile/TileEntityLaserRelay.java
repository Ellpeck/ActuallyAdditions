/*
 * This file ("TileEntityLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLaserRelay extends TileEntityBase implements IEnergyReceiver{

    public static final int MAX_DISTANCE = 15;
    private static final float[] COLOR = new float[]{1F, 0F, 0F};

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(this.worldObj.isRemote){
            this.renderParticles();
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(ConfigBoolValues.LESS_LASER_RELAY_PARTICLES.isEnabled() ? 15 : 8) == 0){
            Position thisPos = new Position(this.xCoord, this.yCoord, this.zCoord);
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(thisPos);
            if(network != null){
                for(LaserRelayConnectionHandler.ConnectionPair aPair : network.connections){
                    if(aPair.contains(thisPos) && thisPos.isEqual(aPair.firstRelay)){
                        PacketParticle.renderParticlesFromAToB(aPair.firstRelay.getX(), aPair.firstRelay.getY(), aPair.firstRelay.getZ(), aPair.secondRelay.getX(), aPair.secondRelay.getY(), aPair.secondRelay.getZ(), ConfigBoolValues.LESS_LASER_RELAY_PARTICLES.isEnabled() ? 1 : Util.RANDOM.nextInt(3)+1, 0.8F, COLOR, 1F);
                    }
                }
            }
        }
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound compound = new NBTTagCompound();

        Position thisPos = new Position(this.xCoord, this.yCoord, this.zCoord);
        ConcurrentSet<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            NBTTagList list = new NBTTagList();
            for(LaserRelayConnectionHandler.ConnectionPair pair : connections){
                list.appendTag(pair.writeToNBT());
            }
            compound.setTag("Connections", list);
            return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
        }
        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        Position thisPos = new Position(this.xCoord, this.yCoord, this.zCoord);
        if(pkt != null && pkt.func_148857_g() != null){
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);

            NBTTagList list = pkt.func_148857_g().getTagList("Connections", 10);
            for(int i = 0; i < list.tagCount(); i++){
                LaserRelayConnectionHandler.ConnectionPair pair = LaserRelayConnectionHandler.ConnectionPair.readFromNBT(list.getCompoundTagAt(i));
                LaserRelayConnectionHandler.getInstance().addConnection(pair.firstRelay, pair.secondRelay);
            }
        }
        else{
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new Position(this.xCoord, this.yCoord, this.zCoord));
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.transmitEnergy(WorldUtil.getCoordsFromSide(from, xCoord, yCoord, zCoord, 0), maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return 0;
    }

    public int transmitEnergy(Position blockFrom, int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(new Position(this.xCoord, this.yCoord, this.zCoord));
            if(network != null){
                transmitted = LaserRelayConnectionHandler.getInstance().transferEnergyToReceiverInNeed(worldObj, blockFrom, network, Math.min(ConfigIntValues.LASER_RELAY_MAX_TRANSFER.getValue(), maxTransmit), simulate);
            }
        }
        return transmitted;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }
}
