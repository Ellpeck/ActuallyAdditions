/*
 * This file ("WorldData.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PlayerServerData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class WorldData extends WorldSavedData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"worlddata";

    public WorldData(String tag){
        super(tag);
    }

    public static WorldData loadOrGet(World world){
        if(world.getMapStorage() != null){
            WorldData data = (WorldData)world.getMapStorage().getOrLoadData(WorldData.class, DATA_TAG);
            if(data == null){
                data = new WorldData(DATA_TAG);
                data.markDirty();
                world.getMapStorage().setData(DATA_TAG, data);
            }

            return data;
        }
        else{
            return null;
        }
    }

    public static void markDirty(World world){
        WorldData data = loadOrGet(world);
        if(data != null){
            data.markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        //Laser World Data
        LaserRelayConnectionHandler.getInstance().networks.clear();

        NBTTagList networkList = compound.getTagList("Networks", 10);
        for(int i = 0; i < networkList.tagCount(); i++){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().readNetworkFromNBT(networkList.getCompoundTagAt(i));
            LaserRelayConnectionHandler.getInstance().networks.add(network);
        }

        //Player Data
        PlayerServerData.playerSaveData.clear();

        NBTTagList playerList = compound.getTagList("PlayerData", 10);
        for(int i = 0; i < playerList.tagCount(); i++){
            PlayerServerData.PlayerSave aSave = PlayerServerData.PlayerSave.fromNBT(playerList.getCompoundTagAt(i));
            PlayerServerData.playerSaveData.add(aSave);
        }
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        //Laser World Data
        NBTTagList networkList = new NBTTagList();
        for(LaserRelayConnectionHandler.Network network : LaserRelayConnectionHandler.getInstance().networks){
            networkList.appendTag(LaserRelayConnectionHandler.getInstance().writeNetworkToNBT(network));
        }
        compound.setTag("Networks", networkList);

        //Player Data
        NBTTagList playerList = new NBTTagList();
        for(int i = 0; i < PlayerServerData.playerSaveData.size(); i++){
            PlayerServerData.PlayerSave theSave = PlayerServerData.playerSaveData.get(i);
            playerList.appendTag(theSave.toNBT());
        }
        compound.setTag("PlayerData", playerList);

        return compound;
    }
}
