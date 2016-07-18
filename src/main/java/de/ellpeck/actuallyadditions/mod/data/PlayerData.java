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

    public static PlayerSave getDataFromPlayer(UUID id){
        ArrayList<PlayerSave> data = WorldData.PLAYER_SAVE_DATA;
        //Get Data from existing data
        for(PlayerSave save : data){
            if(save.theId != null && save.theId.equals(id)){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave aSave = new PlayerSave(id, new NBTTagCompound());
        data.add(aSave);
        return aSave;
    }

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        return getDataFromPlayer(player.getUniqueID());
    }

    public static class PlayerSave{

        public final UUID theId;
        public NBTTagCompound theCompound;

        public PlayerSave(UUID theId, NBTTagCompound theCompound){
            this.theId = theId;
            this.theCompound = theCompound;
        }

        public static PlayerSave fromNBT(NBTTagCompound compound){
            UUID theID = new UUID(compound.getLong("MostSignificant"), compound.getLong("LeastSignificant"));
            NBTTagCompound theCompound = compound.getCompoundTag("Tag");
            return new PlayerSave(theID, theCompound);
        }

        public NBTTagCompound toNBT(){
            NBTTagCompound compound = new NBTTagCompound();
            compound.setLong("LeastSignificant", this.theId.getLeastSignificantBits());
            compound.setLong("MostSignificant", this.theId.getMostSignificantBits());

            compound.setTag("Tag", this.theCompound);

            return compound;
        }
    }


}
