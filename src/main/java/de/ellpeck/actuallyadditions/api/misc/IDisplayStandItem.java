package de.ellpeck.actuallyadditions.api.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IDisplayStandItem {

    boolean update(ItemStack stack, TileEntity tile, int elapsedTicks);

    int getUsePerTick(ItemStack stack, TileEntity tile, int elapsedTicks);
}
