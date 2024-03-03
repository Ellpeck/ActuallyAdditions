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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class InitFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ActuallyAdditions.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ActuallyAdditions.MODID);
    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ActuallyAdditions.MODID);

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
