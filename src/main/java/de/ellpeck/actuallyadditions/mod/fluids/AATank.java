package de.ellpeck.actuallyadditions.mod.fluids;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class AATank extends FluidTank {
    public AATank(int capacity) {
        super(capacity);
    }

    public AATank(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }

    public int fillInternal(FluidStack res, FluidAction action) {
        return super.fill(res, action);
    }

    @Nonnull
    public FluidStack drainInternal(int maxDrain, FluidAction action) {
        return super.drain(maxDrain, action);
    }
}
