/*
 * This file ("EmpowererRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.empowerer;

import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class EmpowererRecipeHandler implements IRecipeHandler<EmpowererRecipe>{

    @Override
    public Class getRecipeClass(){
        return EmpowererRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(EmpowererRecipe recipe){
        return EmpowererRecipeCategory.NAME;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(EmpowererRecipe recipe){
        return new EmpowererRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(EmpowererRecipe recipe){
        return true;
    }
}
