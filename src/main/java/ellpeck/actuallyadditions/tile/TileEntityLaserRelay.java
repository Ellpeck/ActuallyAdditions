/*
 * This file ("TileEntityLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.misc.LaserRelayConnectionHandler;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityLaserRelay extends TileEntityBase implements IEnergyReceiver{

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public void invalidate(){
        super.invalidate();
        if(!worldObj.isRemote){
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
        }
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound compound = new NBTTagCompound();

        WorldPos thisPos = new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        ArrayList<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            compound.setInteger("ConnectionAmount", connections.size());
            for(int i = 0; i < connections.size(); i++){
                connections.get(i).writeToNBT(compound, "Connection"+i);
            }
            return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
        }
        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        NBTTagCompound compound = pkt.func_148857_g();

        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));

        int amount = compound.getInteger("ConnectionAmount");
        for(int i = 0; i < amount; i++){
            LaserRelayConnectionHandler.ConnectionPair pair = LaserRelayConnectionHandler.ConnectionPair.readFromNBT(compound, "Connection"+i);
            LaserRelayConnectionHandler.getInstance().addConnection(pair.firstRelay, pair.secondRelay);
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.transmitEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }

    public int transmitEnergy(int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            ArrayList<LaserRelayConnectionHandler.ConnectionPair> network = LaserRelayConnectionHandler.getInstance().getNetworkFor(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
            if(network != null){
                transmitted = LaserRelayConnectionHandler.getInstance().transferEnergyToReceiverInNeed(network, maxTransmit, simulate);
            }
        }
        return transmitted;
    }
}
