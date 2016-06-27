/*
 * This file ("CoffeeMachineRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.coffee;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.mod.nei.NEICoffeeMachineRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CoffeeMachineRecipeHandler implements IRecipeHandler<CoffeeIngredient>{


    @Override
    public Class getRecipeClass(){
        return CoffeeIngredient.class;
    }

    @Override
    public String getRecipeCategoryUid(CoffeeIngredient recipe){
        return this.getRecipeCategoryUid();
    }

    @Override
    public String getRecipeCategoryUid(){
        return NEICoffeeMachineRecipe.NAME;
    }


    @Override
    public IRecipeWrapper getRecipeWrapper(CoffeeIngredient recipe){
        return new CoffeeMachineRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(CoffeeIngredient recipe){
        return true;
    }
}
