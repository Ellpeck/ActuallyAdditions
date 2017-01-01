/*
 * This file ("TileEntityLaserRelayEnergyAdvanced.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

public class TileEntityLaserRelayEnergyAdvanced extends TileEntityLaserRelayEnergy{

    public static final int CAP = 10000;

    public TileEntityLaserRelayEnergyAdvanced(){
        super("laserRelayAdvanced");
    }

    @Override
    public int getEnergyCap(){
        return CAP;
    }

    @Override
    public double getLossPercentage(){
        return 8;
    }
}
