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
    private int transfer;

    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new CrystalFluxStorage(stack, energy, transfer));

    public CrystalFluxProvider(ItemStack stack, int energy) {
        this.stack = stack;
        this.energy = energy;
        this.transfer = Integer.MAX_VALUE;
    }

    public CrystalFluxProvider(ItemStack stack, int energy, int transfer) {
        this.stack = stack;
        this.energy = energy;
        this.transfer = transfer;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}
