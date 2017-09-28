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

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;

public class TileEntityLaserRelayEnergyAdvanced extends TileEntityLaserRelayEnergy{

    public TileEntityLaserRelayEnergyAdvanced(){
        super("laserRelayAdvanced");
    }

    @Override
    public int getEnergyCap(){
        return ConfigIntListValues.LASER_RELAY_ENERGY_TRANSFER.getValue()[1];
    }

    @Override
    public double getLossPercentage(){
        return ConfigIntListValues.LASER_RELAY_ENERGY_LOSS_PERCENTAGE.getValue()[1];
    }
}
