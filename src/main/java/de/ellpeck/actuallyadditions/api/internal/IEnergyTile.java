package de.ellpeck.actuallyadditions.api.internal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is not supposed to be implemented.
 * Can be cast to TileEntity.
 */
public interface IEnergyTile {

    BlockPos getPosition();

    int getX();

    int getY();

    int getZ();

    /**
     * @deprecated use {@link #getWorld()}
     */
    @Deprecated
    World getWorldObject();

    World getWorld();

    void extractEnergy(int amount);

    int getEnergy();
}
