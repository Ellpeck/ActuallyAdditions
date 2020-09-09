package de.ellpeck.actuallyadditions.common.tile;

import net.minecraft.util.EnumFacing;

public interface ISharingFluidHandler {

    int getMaxFluidAmountToSplitShare();

    boolean doesShareFluid();

    EnumFacing[] getFluidShareSides();

}
