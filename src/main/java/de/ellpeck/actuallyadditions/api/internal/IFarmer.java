/*
 * This file ("IFarmer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.List;

/**
 * This is a helper interface for IFarmerBehavior.
 * <p>
 * This is not supposed to be implemented.
 * Can be cast to TileEntity.
 */
public interface IFarmer extends IEnergyTile{

    EnumFacing getOrientation();

    boolean addToSeedInventory(List<ItemStack> stacks, boolean actuallyDo);

    boolean addToOutputInventory(List<ItemStack> stacks, boolean actuallyDo);
}
