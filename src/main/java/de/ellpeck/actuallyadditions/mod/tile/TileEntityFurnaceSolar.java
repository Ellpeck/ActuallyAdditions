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
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityFurnaceSolar extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay{

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
            int power = this.getPowerToGenerate(PRODUCE);
            if(this.worldObj.isDaytime() && power > 0){
                if(power <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                    this.storage.receiveEnergy(power, false);
                    this.markDirty();
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public int getPowerToGenerate(int power){
        for(int y = 1; y <= this.worldObj.getHeight()-this.pos.getY(); y++){
            if(power > 0){
                BlockPos pos = this.pos.up(y);
                IBlockState state = this.worldObj.getBlockState(pos);

                if(state.getMaterial().isOpaque()){
                    power = 0;
                }
                else if(!state.getBlock().isAir(state, this.worldObj, pos)){
                    power--;
                }
            }
            else{
                break;
            }
        }

        return power;
    }

    @Override
    public EnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public int getEnergyToSplitShare(){
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy(){
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides(){
        return EnumFacing.values();
    }
}
