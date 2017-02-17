/*
 * This file ("BookletEntryTrials.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.entry;

public class BookletEntryTrials extends BookletEntry{

    public BookletEntryTrials(String identifier){
        super(identifier, -Integer.MAX_VALUE);
    }

    @Override
    public boolean visibleOnFrontPage(){
        return false;
    }
}
