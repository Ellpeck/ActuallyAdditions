/*
 * This file ("BookletRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.booklet;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class BookletRecipeHandler implements IRecipeHandler<IBookletPage>{


    @Override
    public Class getRecipeClass(){
        return IBookletPage.class;
    }

    @Override
    public String getRecipeCategoryUid(IBookletPage recipe){
        return BookletRecipeCategory.NAME;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(IBookletPage recipe){
        return new BookletRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(IBookletPage recipe){
        return true;
    }
}
