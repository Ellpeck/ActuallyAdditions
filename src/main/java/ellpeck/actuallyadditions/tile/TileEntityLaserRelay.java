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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaserRelay extends TileEntityBase implements IEnergyReceiver{

    @Override
    public boolean canUpdate(){
        return false;
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
        return 0; //TODO Get Energy in Network
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return 0; //TODO Get Max Energy in Network
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return false;
    }

    public int transmitEnergy(int maxTransmit, boolean simulate){
        return 0;
    }
}
