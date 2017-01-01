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

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockFluidFlowing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Locale;

public final class InitFluids{

    public static Fluid fluidCanolaOil;
    public static Fluid fluidOil;
    public static Fluid fluidCrystalOil;
    public static Fluid fluidEmpoweredOil;

    public static Block blockCanolaOil;
    public static Block blockOil;
    public static Block blockCrystalOil;
    public static Block blockEmpoweredOil;

    public static void init(){
        fluidCanolaOil = registerFluid("canolaoil", "block_canola_oil", EnumRarity.UNCOMMON);
        fluidOil = registerFluid("oil", "block_oil", EnumRarity.UNCOMMON);
        fluidCrystalOil = registerFluid("crystaloil", "block_crystal_oil", EnumRarity.RARE);
        fluidEmpoweredOil = registerFluid("empoweredoil", "block_empowered_oil", EnumRarity.EPIC);

        blockCanolaOil = registerFluidBlock(fluidCanolaOil, Material.WATER, "block_canola_oil");
        blockOil = registerFluidBlock(fluidOil, Material.WATER, "block_oil");
        blockCrystalOil = registerFluidBlock(fluidCrystalOil, Material.WATER, "block_crystal_oil");
        blockEmpoweredOil = registerFluidBlock(fluidEmpoweredOil, Material.WATER, "block_empowered_oil");
    }

    private static Fluid registerFluid(String fluidName, String fluidTextureName, EnumRarity rarity){
        Fluid fluid = new FluidAA(fluidName.toLowerCase(Locale.ROOT), fluidTextureName).setRarity(rarity);
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        return FluidRegistry.getFluid(fluid.getName());
    }

    private static Block registerFluidBlock(Fluid fluid, Material material, String name){
        return new BlockFluidFlowing(fluid, material, name);
    }
}
