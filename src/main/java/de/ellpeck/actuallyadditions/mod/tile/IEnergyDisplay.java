package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEnergyDisplay {

    @SideOnly(Side.CLIENT)
    CustomEnergyStorage getEnergyStorage();

    @SideOnly(Side.CLIENT)
    boolean needsHoldShift();
}
