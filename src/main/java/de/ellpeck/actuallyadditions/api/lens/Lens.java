package de.ellpeck.actuallyadditions.api.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

/**
 * This is the base class for a Reconstructor Lens Type (NOT THE ITEM!)
 */
public abstract class Lens {

    /**
     * Invokes the lens type's behavior on a block
     *
     * @param hitBlock The block that was hit
     * @param tile     The tile the lens was invoked from
     * @return If the Reconstructor should stop continuing (return false if you want it to go through blocks)
     */
    public abstract boolean invoke(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile);

    /**
     * Returns the color in an array of 3 float values that are r, g, b
     */
    public abstract float[] getColor();

    /**
     * Gets the maximum distance the beam goes with this lens
     */
    public abstract int getDistance();

    /**
     * @return If the lens can be invoked at the current time
     */
    public boolean canInvoke(IAtomicReconstructor tile, Direction sideToShootTo, int energyUsePerShot) {
        return true;
    }
}
