/*
 * This file ("TileEntityBookletStand.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.tile;

import de.ellpeck.actuallyadditions.booklet.EntrySet;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBookletStand extends TileEntityBase{

    public EntrySet assignedEntry = new EntrySet(null);
    public String assignedPlayer;

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.writeSyncableNBT(compound, isForSync);
        compound.setTag("SavedEntry", this.assignedEntry.writeToNBT());

        if(this.assignedPlayer != null){
            compound.setString("Player", this.assignedPlayer);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound, isForSync);
        this.assignedEntry = EntrySet.readFromNBT(compound.getCompoundTag("SavedEntry"));

        String player = compound.getString("Player");
        if(player != null){
            this.assignedPlayer = player;
        }
    }
}
