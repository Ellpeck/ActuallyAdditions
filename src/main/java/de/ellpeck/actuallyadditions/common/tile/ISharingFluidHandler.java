package de.ellpeck.actuallyadditions.common.tile;

import net.minecraft.util.Direction;

public interface ISharingFluidHandler {

    int getMaxFluidAmountToSplitShare();

    boolean doesShareFluid();

    Direction[] getFluidShareSides();

}
