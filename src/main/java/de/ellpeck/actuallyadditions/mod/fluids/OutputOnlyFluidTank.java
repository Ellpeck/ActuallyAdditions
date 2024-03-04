package de.ellpeck.actuallyadditions.mod.fluids;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.function.Predicate;

public class OutputOnlyFluidTank extends FluidTank {
    public OutputOnlyFluidTank(int capacity) {
        super(capacity, (FluidStack fluidStack) -> true);
    }

    public OutputOnlyFluidTank(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    public int fillInternal(FluidStack resource, FluidAction action) {
        return super.fill(resource, action);
    }


}
