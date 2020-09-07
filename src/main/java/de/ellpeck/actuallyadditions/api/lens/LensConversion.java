package de.ellpeck.actuallyadditions.api.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

/**
 * This is the base class for a Reconstructor Lens Type that converts two items
 * via the ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES list.
 * <p>
 * If you want to make a new type of conversion, just use your type in the recipe
 * If you want to use the default type of conversion, use ActuallyAdditionsAPI.lensDefaultConversion.
 */
public class LensConversion extends Lens {

    @Override
    public boolean invoke(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        return ActuallyAdditionsAPI.methodHandler.invokeConversionLens(hitState, hitBlock, tile);
    }

    @Override
    public float[] getColor() {
        return new float[] { 27F / 255F, 109F / 255F, 1F };
    }

    @Override
    public int getDistance() {
        return 10;
    }

}
