package de.ellpeck.actuallyadditions.common.fluids;

import java.util.Locale;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockFluidFlowing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public final class InitFluids {

    public static Fluid fluidCanolaOil;
    public static Fluid fluidRefinedCanolaOil;
    public static Fluid fluidCrystalOil;
    public static Fluid fluidEmpoweredOil;

    public static Block blockCanolaOil;
    public static Block blockRefinedCanolaOil;
    public static Block blockCrystalOil;
    public static Block blockEmpoweredOil;

    public static void init() {
        fluidCanolaOil = registerFluid("canolaoil", "block_canola_oil", EnumRarity.UNCOMMON);
        fluidRefinedCanolaOil = registerFluid("refinedcanolaoil", "block_refined_canola_oil", EnumRarity.UNCOMMON);
        fluidCrystalOil = registerFluid("crystaloil", "block_crystal_oil", EnumRarity.RARE);
        fluidEmpoweredOil = registerFluid("empoweredoil", "block_empowered_oil", EnumRarity.EPIC);

        blockCanolaOil = registerFluidBlock(fluidCanolaOil, Material.WATER, "block_canola_oil");
        blockRefinedCanolaOil = registerFluidBlock(fluidRefinedCanolaOil, Material.WATER, "block_refined_canola_oil");
        blockCrystalOil = registerFluidBlock(fluidCrystalOil, Material.WATER, "block_crystal_oil");
        blockEmpoweredOil = registerFluidBlock(fluidEmpoweredOil, Material.WATER, "block_empowered_oil");
    }

    private static Fluid registerFluid(String fluidName, String fluidTextureName, EnumRarity rarity) {
        Fluid fluid = new FluidAA(fluidName.toLowerCase(Locale.ROOT), fluidTextureName).setRarity(rarity);
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        return FluidRegistry.getFluid(fluid.getName());
    }

    private static Block registerFluidBlock(Fluid fluid, Material material, String name) {
        return new BlockFluidFlowing(fluid, material, name);
    }
}
