/*
 * This file ("CrusherRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.crusher;

import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipe>{

    @Nonnull
    @Override
    public Class getRecipeClass(){
        return CrusherRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(){
        return CrusherRecipeCategory.NAME;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CrusherRecipe recipe){
        return new CrusherRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull CrusherRecipe recipe){
        return true;
    }
}
