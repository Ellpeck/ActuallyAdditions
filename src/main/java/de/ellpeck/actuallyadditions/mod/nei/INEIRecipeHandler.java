/*
 * This file ("INEIRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;

public interface INEIRecipeHandler{

    BookletPage getPageForInfo(int neiIndex);
}
