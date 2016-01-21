/*
 * This file ("FluidStateMapper.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * (Excerpted from Tinkers' Construct with permission, thanks guys!)
 */
@SideOnly(Side.CLIENT)
public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition{

    public final Fluid fluid;
    public final ModelResourceLocation location;

    public FluidStateMapper(Fluid fluid){
        this.fluid = fluid;

        this.location = new ModelResourceLocation(new ResourceLocation(ModUtil.MOD_ID_LOWER, "fluids"), fluid.getName());
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state){
        return location;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack){
        return location;
    }
}
