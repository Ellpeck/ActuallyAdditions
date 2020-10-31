package de.ellpeck.actuallyadditions.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public interface ISharingEnergyProvider {

    int getEnergyToSplitShare();

    boolean doesShareEnergy();

    Direction[] getEnergyShareSides();

    boolean canShareTo(TileEntity tile);
}
