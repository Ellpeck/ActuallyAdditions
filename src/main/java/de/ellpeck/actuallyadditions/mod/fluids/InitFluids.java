/*
 * This file ("InitFluids.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.fluids;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockFluidFlowing;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class InitFluids{

    public static Fluid fluidCanolaOil;
    public static Fluid fluidOil;

    public static Block blockCanolaOil;
    public static Block blockOil;

    public static void init(){
        //Canola Fluid
        String canolaOil = "canolaoil";
        if(!FluidRegistry.isFluidRegistered(canolaOil) || ConfigBoolValues.PREVENT_CANOLA_OVERRIDE.isEnabled()){
            fluidCanolaOil = new FluidAA(canolaOil, "blockCanolaOil").setRarity(EnumRarity.UNCOMMON);
            FluidRegistry.registerFluid(fluidCanolaOil);
        }
        else{
            errorAlreadyRegistered("Canola Oil Fluid");
        }
        fluidCanolaOil = FluidRegistry.getFluid(canolaOil);

        //Canola Block
        if(fluidCanolaOil.getBlock() == null || ConfigBoolValues.PREVENT_CANOLA_BLOCK_OVERRIDE.isEnabled()){
            blockCanolaOil = new BlockFluidFlowing(fluidCanolaOil, Material.water, "blockCanolaOil");
        }
        else{
            errorAlreadyRegistered("Canola Oil Block");
        }
        blockCanolaOil = fluidCanolaOil.getBlock();

        //Oil Fluid
        String oil = "oil";
        if(!FluidRegistry.isFluidRegistered(oil) || ConfigBoolValues.PREVENT_OIL_OVERRIDE.isEnabled()){
            fluidOil = new FluidAA(oil, "blockOil").setRarity(EnumRarity.UNCOMMON);
            FluidRegistry.registerFluid(fluidOil);
        }
        else{
            errorAlreadyRegistered("Oil Fluid");
        }
        fluidOil = FluidRegistry.getFluid(oil);

        //Oil Block
        if(fluidOil.getBlock() == null || ConfigBoolValues.PREVENT_OIL_BLOCK_OVERRIDE.isEnabled()){
            blockOil = new BlockFluidFlowing(fluidOil, Material.water, "blockOil");
        }
        else{
            errorAlreadyRegistered("Oil Block");
        }
        blockOil = fluidOil.getBlock();
    }

    public static void errorAlreadyRegistered(String str){
        ModUtil.LOGGER.warn(str+" from "+ModUtil.NAME+" is not getting used as it has already been registered by another Mod! If this causes Issues (which it shouldn't!), you can turn this off in the Config File!");
    }
}
