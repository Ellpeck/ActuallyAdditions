/*
 * This file ("TileEntityShockSuppressor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityShockSuppressor extends TileEntityBase implements IEnergyDisplay{

    public static final List<TileEntityShockSuppressor> SUPPRESSORS = new ArrayList<TileEntityShockSuppressor>();

    public static final int USE_PER = 300;
    public static final int RANGE = 5;

    public CustomEnergyStorage storage = new CustomEnergyStorage(300000, 400, 0);
    private int oldEnergy;

    public TileEntityShockSuppressor(){
        super("shockSuppressor");
    }

    @Override
    public void onChunkUnload(){
        super.onChunkUnload();

        if(!this.world.isRemote){
            SUPPRESSORS.remove(this);
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();

        if(!this.world.isRemote){
            SUPPRESSORS.remove(this);
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.world.isRemote){
            if(!this.isInvalid() && !SUPPRESSORS.contains(this)){
                SUPPRESSORS.add(this);
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
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
    public CustomEnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
