package de.ellpeck.actuallyadditions.common.capability;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

import java.util.Objects;

/**
 * Main Capability for Item Energry storage otherwise referenced as Crystal Flux
 */
public class CrystalFluxStorage extends EnergyStorage {
    public static final String NBT_TAG = "crystal-flux";
    private final ItemStack stack;

    public CrystalFluxStorage(ItemStack stack, int capacity, int transfer) {
        super(capacity, transfer, transfer);

        this.stack = stack;

        // hasTag doesn't let the IDE know that the getTag is now safe to use. Instead we're wrapping
        // it up in a requireNonNull to stop IDE bitching
        this.energy = stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains(NBT_TAG)
                ? stack.getTag().getInt(NBT_TAG)
                : 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);

        if (extracted > 0 && !simulate) {
            stack.getOrCreateTag().putInt(NBT_TAG, getEnergyStored());
        }

        return extracted;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);

        if (received > 0 && !simulate) {
            stack.getOrCreateTag().putInt(NBT_TAG, getEnergyStored());
        }

        return received;
    }
}
