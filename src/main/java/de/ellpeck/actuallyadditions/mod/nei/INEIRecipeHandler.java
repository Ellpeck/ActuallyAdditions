/*
 * This file ("INEIRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;

/**
 * Make an NEI Recipe Handler implement this to make a button show up on the page
 */
public interface INEIRecipeHandler{

    /**
     * The page that will be opened when clicking the button
     * @param neiIndex the page variable in NEI's GuiRecipe
     */
    BookletPage getPageForInfo(int neiIndex);
}
