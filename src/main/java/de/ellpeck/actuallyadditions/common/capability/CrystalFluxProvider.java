package de.ellpeck.actuallyadditions.common.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrystalFluxProvider implements ICapabilityProvider {
    private ItemStack stack;
    private int energy;
    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new CrystalFluxStorage(stack, energy));

    public CrystalFluxProvider(ItemStack stack, int energy) {
        this.stack = stack;
        this.energy = energy;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}
