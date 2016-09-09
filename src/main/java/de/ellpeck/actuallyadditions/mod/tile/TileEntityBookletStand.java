/*
 * This file ("TileEntityBookletStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBookletStand extends TileEntityBase{

    public EntrySet assignedEntry = new EntrySet(null);
    public String assignedPlayer;

    public TileEntityBookletStand(){
        super("bookletStand");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            NBTTagCompound tag = new NBTTagCompound();
            this.assignedEntry.writeToNBT(tag);
            compound.setTag("SavedEntry", tag);

            if(this.assignedPlayer != null){
                compound.setString("Player", this.assignedPlayer);
            }
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.assignedEntry.readFromNBT(compound.getCompoundTag("SavedEntry"));
            this.assignedPlayer = compound.getString("Player");
        }
    }
}