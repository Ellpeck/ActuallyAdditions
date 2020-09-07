package de.ellpeck.actuallyadditions.mod.tile;

public class TileEntityLaserRelayEnergyExtreme extends TileEntityLaserRelayEnergy {

    public static final int CAP = 100000;

    public TileEntityLaserRelayEnergyExtreme() {
        super("laserRelayExtreme");
    }

    @Override
    public int getEnergyCap() {
        return CAP;
    }

    @Override
    public double getLossPercentage() {
        return 10;
    }
}
