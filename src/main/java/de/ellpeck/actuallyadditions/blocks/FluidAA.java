/*
 * This file ("FluidAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraftforge.fluids.Fluid;

public class FluidAA extends Fluid{

    public FluidAA(String fluidName){
        super(fluidName);
    }

    @Override
    public String getUnlocalizedName(){
        return "fluid."+ModUtil.MOD_ID_LOWER+"."+this.unlocalizedName;
    }
}
