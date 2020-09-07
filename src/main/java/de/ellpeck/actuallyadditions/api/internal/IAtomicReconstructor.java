package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.util.Direction;

/**
 * This is a helper interface for Lens' invoke() method.
 * <p>
 * This is not supposed to be implemented.
 * Can be cast to TileEntity.
 */
public interface IAtomicReconstructor extends IEnergyTile {

    Lens getLens();

    Direction getOrientation();
}
