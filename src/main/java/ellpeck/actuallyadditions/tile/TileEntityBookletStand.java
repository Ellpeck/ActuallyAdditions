/*
 * This file ("TileEntityBookletStand.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class TileEntityBookletStand extends TileEntityBase{

    public BookletChapter assignedChapter;
    public BookletPage assignedPage;
    public BookletEntry assignedEntry;
    public int assignedPageInIndex;

    public UUID assignedPlayerUUID;

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        compound.setInteger("Entry", this.assignedEntry == null ? -1 : InitBooklet.entries.indexOf(this.assignedEntry));
        compound.setInteger("Chapter", this.assignedEntry == null || this.assignedChapter == null ? -1 : this.assignedEntry.chapters.indexOf(this.assignedChapter));
        compound.setInteger("Page", this.assignedPage == null ? -1 : this.assignedPage.getID());
        compound.setInteger("PageInIndex", this.assignedPageInIndex);

        if(this.assignedPlayerUUID != null){
            compound.setLong("PlayerLeastSignificant", this.assignedPlayerUUID.getLeastSignificantBits());
            compound.setLong("PlayerMostSignificant", this.assignedPlayerUUID.getMostSignificantBits());
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.setEntry(compound.getInteger("Entry"), compound.getInteger("Chapter"), compound.getInteger("Page"), compound.getInteger("PageInIndex"));

        long mostSigBits = compound.getLong("PlayerMostSignificant");
        if(mostSigBits > 0){
            this.assignedPlayerUUID = new UUID(mostSigBits, compound.getLong("PlayerLeastSignificant"));
        }
    }

    public void setEntry(int entry, int chapter, int page, int pageInIndex){
        this.assignedEntry = entry == -1 ? null : InitBooklet.entries.get(entry);
        this.assignedChapter = chapter == -1 || entry == -1 || this.assignedEntry.chapters.size() <= chapter ? null : this.assignedEntry.chapters.get(chapter);
        this.assignedPage = chapter == -1 || this.assignedChapter == null || this.assignedChapter.pages.length <= page-1 ? null : this.assignedChapter.pages[page-1];
        this.assignedPageInIndex = pageInIndex;
    }
}
