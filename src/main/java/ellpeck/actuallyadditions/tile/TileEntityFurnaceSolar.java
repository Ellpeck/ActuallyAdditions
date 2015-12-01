/*
 * This file ("TileEntityFurnaceSolar.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceSolar extends TileEntityBase implements IEnergyProvider{

    public static final int PRODUCE = 10;
    public EnergyStorage storage = new EnergyStorage(30000);

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return from != ForgeDirection.UP;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.hasBlockAbove() && worldObj.isDaytime()){
                if(PRODUCE <= this.getMaxEnergyStored(ForgeDirection.UNKNOWN)-this.getEnergyStored(ForgeDirection.UNKNOWN)){
                    this.storage.receiveEnergy(PRODUCE, false);
                    this.markDirty();
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, storage);
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.storage.readFromNBT(compound);
    }

    public boolean hasBlockAbove(){
        for(int y = yCoord+1; y <= worldObj.getHeight(); y++){
            if(!worldObj.getBlock(xCoord, y, zCoord).isAir(worldObj, xCoord, y, zCoord)){
                return true;
            }
        }
        return false;
    }
}
