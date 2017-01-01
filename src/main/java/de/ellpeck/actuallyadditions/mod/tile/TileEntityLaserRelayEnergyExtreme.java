/*
 * This file ("TileEntityLaserRelayEnergyExtreme.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

public class TileEntityLaserRelayEnergyExtreme extends TileEntityLaserRelayEnergy{

    public static final int CAP = 100000;

    public TileEntityLaserRelayEnergyExtreme(){
        super("laserRelayExtreme");
    }

    @Override
    public int getEnergyCap(){
        return CAP;
    }

    @Override
    public double getLossPercentage(){
        return 10;
    }
}
