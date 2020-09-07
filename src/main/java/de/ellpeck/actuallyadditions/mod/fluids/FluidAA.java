package de.ellpeck.actuallyadditions.mod.fluids;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidAA extends Fluid {

    public FluidAA(String fluidName, String textureName) {
        super(fluidName, new ResourceLocation(ActuallyAdditions.MODID, "blocks/" + textureName + "_still"), new ResourceLocation(ActuallyAdditions.MODID, "blocks/" + textureName + "_flowing"));
    }

    @Override
    public String getUnlocalizedName() {
        return "fluid." + ActuallyAdditions.MODID + "." + this.unlocalizedName;
    }
}
