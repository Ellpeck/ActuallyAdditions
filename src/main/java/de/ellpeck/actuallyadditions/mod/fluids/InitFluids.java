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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class InitFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ActuallyAdditions.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ActuallyAdditions.MODID);
    public static final DeferredRegister.Blocks FLUID_BLOCKS = DeferredRegister.createBlocks(ActuallyAdditions.MODID);

    public static final FluidAA CANOLA_OIL = new FluidAA("canola_oil", "fluid/canola_oil");
    public static final FluidAA REFINED_CANOLA_OIL = new FluidAA("refined_canola_oil", "fluid/refined_canola_oil");
    public static final FluidAA CRYSTALLIZED_OIL = new FluidAA("crystallized_oil", "fluid/crystallized_oil");
    public static final FluidAA EMPOWERED_OIL = new FluidAA("empowered_oil", "fluid/empowered_oil");


    public static void init(IEventBus bus) {
        FLUIDS.register(bus);
        FLUID_TYPES.register(bus);
        FLUID_BLOCKS.register(bus);
    }
}
