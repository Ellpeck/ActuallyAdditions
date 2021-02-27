/*
 * This file ("FluidStateMapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.OnlyIn;

/**
 * (Excerpted from Tinkers' Construct with permission, thanks guys!)
 */
@OnlyIn(Dist.CLIENT)
public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

    public final Fluid fluid;
    public final ModelResourceLocation location;

    public FluidStateMapper(Fluid fluid) {
        this.fluid = fluid;

        this.location = new ModelResourceLocation(new ResourceLocation(ActuallyAdditions.MODID, "fluids"), fluid.getName());
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(BlockState state) {
        return this.location;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        return this.location;
    }
}
