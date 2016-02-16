/*
 * This file ("TileEntityFurnaceSolar.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFurnaceSolar extends TileEntityBase implements IEnergyProvider, IEnergySaver, IEnergyDisplay{

    public static final int PRODUCE = 8;
    public EnergyStorage storage = new EnergyStorage(30000);
    private int oldEnergy;

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return from != EnumFacing.UP;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.hasBlockAbove() && worldObj.isDaytime()){
                if(PRODUCE <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                    this.storage.receiveEnergy(PRODUCE, false);
                    this.markDirty();
                }
            }

            if(this.storage.getEnergyStored() > 0){
                WorldUtil.pushEnergyToAllSides(worldObj, this.pos, this.storage);
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public boolean hasBlockAbove(){
        for(int y = 1; y <= worldObj.getHeight(); y++){
            BlockPos offset = PosUtil.offset(this.pos, 0, y, 0);
            if(!PosUtil.getBlock(offset, worldObj).isAir(worldObj, offset)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }
}
