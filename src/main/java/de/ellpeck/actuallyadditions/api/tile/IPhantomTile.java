package de.ellpeck.actuallyadditions.api.tile;

import net.minecraft.util.math.BlockPos;

/**
 * Extending this will cause a TileEntity to be able to be connected via a Phantom Connector
 */
public interface IPhantomTile {

    /**
     * @return If the Phantom Tile is currently bound to anything
     */
    boolean hasBoundPosition();

    /**
     * @return If the Phantom Tile's bound position is in range
     */
    boolean isBoundThingInRange();

    /**
     * @return The position this tile is bound to
     */
    BlockPos getBoundPosition();

    /**
     * Sets the bound position
     */
    void setBoundPosition(BlockPos pos);

    /**
     * @return The ID of the GUI it opens, -1 if none
     */
    int getGuiID();

    /**
     * @return The range the tile currently has
     */
    int getRange();
}
