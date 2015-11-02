/*
 * This file ("PersistentServerData.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util.playerdata;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.UUID;

public class PersistentServerData{

    public static ArrayList<PlayerSave> playerSaveData = new ArrayList<PlayerSave>();

    public static NBTTagCompound getDataFromPlayer(EntityPlayer player){
        //Get Data from existing data
        for(PlayerSave save : playerSaveData){
            if(save.thePlayerUUID.equals(player.getUniqueID())){
                return save.theCompound;
            }
        }

        //Add Data if none is existant
        PlayerSave aSave = new PlayerSave(player.getUniqueID(), new NBTTagCompound());
        playerSaveData.add(aSave);
        return aSave.theCompound;
    }

    public static class PlayerSave{

        public UUID thePlayerUUID;
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
