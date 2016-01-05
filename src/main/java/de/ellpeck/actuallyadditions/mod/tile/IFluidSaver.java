/*
 * This file ("IFluidSaver.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidSaver{

    FluidStack[] getFluids();

    void setFluids(FluidStack[] fluids);
}
