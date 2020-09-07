package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ISharingEnergyProvider {

    int getEnergyToSplitShare();

    boolean doesShareEnergy();

    EnumFacing[] getEnergyShareSides();

    boolean canShareTo(TileEntity tile);
}
