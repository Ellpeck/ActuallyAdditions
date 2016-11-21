/*
 * This file ("OilGenRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraftforge.fluids.FluidStack;

public class OilGenRecipe{

    public final String fluidName;
    public final int genAmount;
    public final int genTime;

    public OilGenRecipe(String fluidName, int genAmount, int genTime){
        this.fluidName = fluidName;
        this.genAmount = genAmount;
        this.genTime = genTime;
    }

}
