/*
 * This file ("PageCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import net.minecraft.item.crafting.IRecipe;

import java.util.List;

//TODO
public class PageCrafting extends BookletPage{

    public PageCrafting(int localizationKey, List<IRecipe> recipes){
        super(localizationKey);
    }

    public PageCrafting(int localizationKey, IRecipe... recipes){
        super(localizationKey);
    }

    public BookletPage setWildcard(){
        return this;
    }
}
