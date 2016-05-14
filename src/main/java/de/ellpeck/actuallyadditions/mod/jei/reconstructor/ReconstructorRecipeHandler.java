/*
 * This file ("ReconstructorRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.reconstructor;

import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.mod.nei.NEIReconstructorRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class ReconstructorRecipeHandler implements IRecipeHandler<LensConversionRecipe>{

    @Nonnull
    @Override
    public Class getRecipeClass(){
        return LensConversionRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(){
        return NEIReconstructorRecipe.NAME;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull LensConversionRecipe recipe){
        return new ReconstructorRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull LensConversionRecipe recipe){
        return true;
    }
}
