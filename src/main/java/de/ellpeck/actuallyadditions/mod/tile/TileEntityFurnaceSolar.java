/*
 * This file ("TileEntityFurnaceSolar.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFurnaceSolar extends TileEntityBase implements IEnergyProvider, IEnergyDisplay{

    public static final int PRODUCE = 8;
    public final EnergyStorage storage = new EnergyStorage(30000);
    private int oldEnergy;

    public TileEntityFurnaceSolar(){
        super("solarPanel");
    }

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
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.hasBlockAbove() && this.worldObj.isDaytime()){
                if(PRODUCE <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                    this.storage.receiveEnergy(PRODUCE, false);
                    this.markDirty();
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public boolean hasBlockAbove(){
        for(int y = 1; y <= this.worldObj.getHeight(); y++){
            BlockPos offset = PosUtil.offset(this.pos, 0, y, 0);
            if(!PosUtil.getBlock(offset, this.worldObj).isAir(this.worldObj.getBlockState(offset), this.worldObj, offset)){
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
    @SideOnly(Side.CLIENT)
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }
}
