/*
 * This file ("CustomEnergyStorage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage{

    public CustomEnergyStorage(int capacity){
        super(capacity);
    }

    public CustomEnergyStorage(int capacity, int maxTransfer){
        super(capacity, maxTransfer);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract){
        super(capacity, maxReceive, maxExtract);
    }

    public int extractEnergyInternal(int maxExtract, boolean simulate){
        int before = this.maxExtract;
        this.maxExtract = Integer.MAX_VALUE;

        int toReturn = this.extractEnergy(maxExtract, simulate);

        this.maxExtract = before;
        return toReturn;
    }

    public int receiveEnergyInternal(int maxReceive, boolean simulate){
        int before = this.maxReceive;
        this.maxReceive = Integer.MAX_VALUE;

        int toReturn = this.receiveEnergy(maxReceive, simulate);

        this.maxReceive = before;
        return toReturn;
    }

    public void readFromNBT(NBTTagCompound compound){
        this.energy = compound.getInteger("Energy");
    }

    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("Energy", this.energy);
    }

    public void setEnergyStored(int energy){
        this.energy = energy;
    }
}
