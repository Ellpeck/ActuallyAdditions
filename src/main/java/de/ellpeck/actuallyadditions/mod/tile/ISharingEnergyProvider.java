/*
 * This file ("ISharingEnergyProvider.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ISharingEnergyProvider{

    int getEnergyToSplitShare();

    boolean doesShareEnergy();

    EnumFacing[] getEnergyShareSides();

    boolean canShareTo(TileEntity tile);
}
