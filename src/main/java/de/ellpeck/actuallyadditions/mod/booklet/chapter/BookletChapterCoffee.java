/*
 * This file ("BookletChapterCoffee.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.chapter;

import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import net.minecraft.item.ItemStack;

//TODO
public class BookletChapterCoffee extends BookletChapter{

    public BookletChapterCoffee(String identifier, IBookletEntry entry, ItemStack displayStack, IBookletPage... pages){
        super(identifier, entry, displayStack, pages);
    }
}
