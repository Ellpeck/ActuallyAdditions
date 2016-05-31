/*
 * This file ("BookletRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.booklet;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.mod.nei.NEIBookletRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class BookletRecipeHandler implements IRecipeHandler<BookletPage>{


    @Override
    public Class getRecipeClass(){
        return BookletPage.class;
    }


    @Override
    public String getRecipeCategoryUid(){
        return NEIBookletRecipe.NAME;
    }


    @Override
    public IRecipeWrapper getRecipeWrapper(BookletPage recipe){
        return new BookletRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(BookletPage recipe){
        return true;
    }
}
