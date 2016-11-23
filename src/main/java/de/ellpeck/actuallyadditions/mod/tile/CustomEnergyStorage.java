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

import cofh.api.energy.EnergyStorage;

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
        this.setMaxExtract(Integer.MAX_VALUE);

        int toReturn = this.extractEnergy(maxExtract, simulate);

        this.setMaxExtract(before);
        return toReturn;
    }

    public int receiveEnergyInternal(int maxReceive, boolean simulate){
        int before = this.maxReceive;
        this.setMaxReceive(Integer.MAX_VALUE);

        int toReturn = this.receiveEnergy(maxReceive, simulate);

        this.setMaxReceive(before);
        return toReturn;
    }
}
