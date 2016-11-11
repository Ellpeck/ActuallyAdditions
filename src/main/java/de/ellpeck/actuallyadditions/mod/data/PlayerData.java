/*
 * This file ("PlayerData.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.UUID;

public final class PlayerData{

    public static PlayerSave getDataFromPlayer(UUID id){
        List<PlayerSave> data = WorldData.getWorldUnspecificData().playerSaveData;
        //Get Data from existing data
        for(PlayerSave save : data){
            if(save.id != null && save.id.equals(id)){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave aSave = new PlayerSave(id);
        data.add(aSave);
        return aSave;
    }

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        return getDataFromPlayer(player.getUniqueID());
    }

    public static class PlayerSave{

        public UUID id;

        public boolean displayTesla;
        public boolean bookGottenAlready;

        public PlayerSave(UUID id){
            this.id = id;
        }

        public void readFromNBT(NBTTagCompound compound){
            this.displayTesla = compound.getBoolean("DisplayTesla");
            this.bookGottenAlready = compound.getBoolean("BookGotten");
        }

        public void writeToNBT(NBTTagCompound compound){
            compound.setBoolean("DisplayTesla", this.displayTesla);
            compound.setBoolean("BookGotten", this.bookGottenAlready);
        }

    }


}
