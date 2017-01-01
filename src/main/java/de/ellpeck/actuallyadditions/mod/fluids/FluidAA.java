/*
 * This file ("FluidAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.fluids;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidAA extends Fluid{

    public FluidAA(String fluidName, String textureName){
        super(fluidName, new ResourceLocation(ModUtil.MOD_ID, "blocks/"+textureName+"_still"), new ResourceLocation(ModUtil.MOD_ID, "blocks/"+textureName+"_flowing"));
    }

    @Override
    public String getUnlocalizedName(){
        return "fluid."+ModUtil.MOD_ID+"."+this.unlocalizedName;
    }
}
