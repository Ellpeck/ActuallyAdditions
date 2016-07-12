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

//Yes, this is name based instead of UUID-based.
//It just works better this way because of vanilla quirks. Don't judge me.
public final class PlayerData{

    public static PlayerSave getDataFromPlayer(String name){
        ArrayList<PlayerSave> data = WorldData.PLAYER_SAVE_DATA;
        //Get Data from existing data
        for(PlayerSave save : data){
            if(save.theName != null && save.theName.equals(name)){
                return save;
            }
        }

        //Add Data if none is existant
        PlayerSave aSave = new PlayerSave(name, new NBTTagCompound());
        data.add(aSave);
        return aSave;
    }

    public static PlayerSave getDataFromPlayer(EntityPlayer player){
        return getDataFromPlayer(player.getName());
    }

    public static class PlayerSave{

        public final String theName;
        public NBTTagCompound theCompound;

        public PlayerSave(String name, NBTTagCompound theCompound){
            this.theName = name;
            this.theCompound = theCompound;
        }

        public static PlayerSave fromNBT(NBTTagCompound compound){
            String name = compound.getString("Name");
            NBTTagCompound theCompound = compound.getCompoundTag("Tag");
            return new PlayerSave(name, theCompound);
        }

        public NBTTagCompound toNBT(){
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("Name", this.theName);
            compound.setTag("Tag", this.theCompound);
            return compound;
        }
    }

}
