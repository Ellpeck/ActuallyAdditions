/*
 * This file ("LensConversion.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.lens;


import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

/**
 * This is the base class for a Reconstructor Lens Type that converts two items
 * via the ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES list.
 * <p>
 * If you want to make a new type of conversion, just use your type in the recipe
 * If you want to use the default type of conversion, use ActuallyAdditionsAPI.lensDefaultConversion.
 */
public class LensConversion extends Lens{

    @Override
    public boolean invoke(IBlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile){
        return ActuallyAdditionsAPI.methodHandler.invokeConversionLens(hitState, hitBlock, tile);
    }

    @Override
    public float[] getColor(){
        return new float[]{27F/255F, 109F/255F, 1F};
    }

    @Override
    public int getDistance(){
        return 10;
    }

}
