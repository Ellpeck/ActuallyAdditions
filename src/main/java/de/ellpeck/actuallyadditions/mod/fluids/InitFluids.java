/*
 * This file ("InitFluids.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.fluids;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class InitFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ActuallyAdditions.MODID);
    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ActuallyAdditions.MODID);

    public static final FluidAA CANOLA_OIL = new FluidAA("canolaoil", "canolaoil");
    public static final FluidAA REFINED_CANOLA_OIL = new FluidAA("refinedcanolaoil", "refinedcanolaoil");
    public static final FluidAA CRYSTALIZED_OIL = new FluidAA("crystaloil", "crystaloil");
    public static final FluidAA EMPOWERED_OIL = new FluidAA("empoweredoil", "empoweredoil");


    public static void init(IEventBus bus) {
        FLUIDS.register(bus);
        FLUID_BLOCKS.register(bus);
    }
}
