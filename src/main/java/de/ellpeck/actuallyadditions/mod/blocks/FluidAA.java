/*
 * This file ("FluidAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidAA extends Fluid{

    public FluidAA(String fluidName, String textureName){
        super(fluidName, new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/"+textureName+"Still.png"), new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/"+textureName+"Flowing.png"));
    }

    @Override
    public String getUnlocalizedName(){
        return "fluid."+ModUtil.MOD_ID_LOWER+"."+this.unlocalizedName;
    }
}
