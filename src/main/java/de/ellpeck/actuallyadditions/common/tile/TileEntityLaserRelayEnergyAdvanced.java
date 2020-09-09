package de.ellpeck.actuallyadditions.common.tile;

public class TileEntityLaserRelayEnergyAdvanced extends TileEntityLaserRelayEnergy {

    public static final int CAP = 10000;

    public TileEntityLaserRelayEnergyAdvanced() {
        super("laserRelayAdvanced");
    }

    @Override
    public int getEnergyCap() {
        return CAP;
    }

    @Override
    public double getLossPercentage() {
        return 8;
    }
}
