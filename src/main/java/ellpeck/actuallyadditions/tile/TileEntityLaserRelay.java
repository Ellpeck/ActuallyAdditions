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
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){

    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){

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
