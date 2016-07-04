/*
 * This file ("PersistentServerData.java") is part of the Actually Additions mod for Minecraft.
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

import java.util.ArrayList;
import java.util.UUID;

public final class PlayerData{

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        ArrayList<PlayerSave> data = WorldData.PLAYER_SAVE_DATA;
        //Get Data from existing data
        for(PlayerSave save : data){
            if(save.thePlayerUUID.equals(player.getUniqueID())){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave aSave = new PlayerSave(player.getUniqueID(), new NBTTagCompound());
        data.add(aSave);
        return aSave;
    }

    public static class PlayerSave{

        public final UUID thePlayerUUID;
        public NBTTagCompound theCompound;

        public PlayerSave(UUID theUUID, NBTTagCompound theCompound){
            this.thePlayerUUID = theUUID;
            this.theCompound = theCompound;
        }

        public static PlayerSave fromNBT(NBTTagCompound compound){
            UUID theID = new UUID(compound.getLong("MostSignificant"), compound.getLong("LeastSignificant"));
            NBTTagCompound theCompound = compound.getCompoundTag("Tag");
            return new PlayerSave(theID, theCompound);
        }

        public NBTTagCompound toNBT(){
            NBTTagCompound compound = new NBTTagCompound();
            compound.setLong("LeastSignificant", this.thePlayerUUID.getLeastSignificantBits());
            compound.setLong("MostSignificant", this.thePlayerUUID.getMostSignificantBits());

            compound.setTag("Tag", this.theCompound);

            return compound;
        }
    }

}
