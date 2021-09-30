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
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockFluidFlowing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public final class InitFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ActuallyAdditions.MODID);

    public static final RegistryObject<Fluid> fluidCanolaOil = FLUIDS.register("canolaoil", () -> registerFluid("canolaoil","block_canola_oil"));
    public static final RegistryObject<Fluid> fluidRefinedCanolaOil = FLUIDS.register("refinedcanolaoil", () -> registerFluid("refinedcanolaoil","block_refined_canola_oil"));
    public static final RegistryObject<Fluid> fluidCrystalOil = FLUIDS.register("crystaloil", () -> registerFluid("crystaloil","block_crystal_oil"));
    public static final RegistryObject<Fluid> fluidEmpoweredOil = FLUIDS.register("empoweredoil", () -> registerFluid("empoweredoil","block_empowered_oil"));

    public static Block blockCanolaOil;
    public static Block blockRefinedCanolaOil;
    public static Block blockCrystalOil;
    public static Block blockEmpoweredOil;

    public static void init() {
        //fluidCanolaOil = registerFluid("canolaoil", "block_canola_oil", Rarity.COMMON);
        //fluidRefinedCanolaOil = registerFluid("refinedcanolaoil", "block_refined_canola_oil", Rarity.UNCOMMON);
        //fluidCrystalOil = registerFluid("crystaloil", "block_crystal_oil", Rarity.RARE);
        //fluidEmpoweredOil = registerFluid("empoweredoil", "block_empowered_oil", Rarity.EPIC);

        blockCanolaOil = registerFluidBlock(fluidCanolaOil.get(), Material.WATER, "block_canola_oil");
        blockRefinedCanolaOil = registerFluidBlock(fluidRefinedCanolaOil.get(), Material.WATER, "block_refined_canola_oil");
        blockCrystalOil = registerFluidBlock(fluidCrystalOil.get(), Material.WATER, "block_crystal_oil");
        blockEmpoweredOil = registerFluidBlock(fluidEmpoweredOil.get(), Material.WATER, "block_empowered_oil");
    }

    private static Fluid registerFluid(String fluidName, String fluidTextureName) {
        Fluid fluid = new FluidAA(fluidName.toLowerCase(Locale.ROOT), fluidTextureName);
        //        FluidRegistry.registerFluid(fluid);
        //        FluidRegistry.addBucketForFluid(fluid);

        return fluid;
    }

    private static Block registerFluidBlock(Fluid fluid, Material material, String name) {
        return new BlockFluidFlowing(fluid, material, name); //TODO ohhhh boy, all this fluid stuff needs to be redone.
    }
}
