/*
 * This file ("BookletEntryAllItems.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.entry;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;

import java.util.List;

public class BookletEntryAllItems extends BookletEntry{

    public BookletEntryAllItems(String identifier){
        super(identifier, -Integer.MAX_VALUE);
    }

    @Override
    public void addChapter(IBookletChapter chapter){

    }

    @Override
    public List<IBookletChapter> getAllChapters(){
        return ActuallyAdditionsAPI.ALL_CHAPTERS;
    }
}
