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
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import javax.annotation.Nonnull;

public class WorldData extends WorldSavedData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"worlddata";
    private static WorldData instance;

    public WorldData(String tag){
        super(tag);
    }

    public static void makeDirty(){
        if(instance != null){
            instance.markDirty();
        }
    }

    public static void init(MinecraftServer server){
        if(server != null){
            World world = server.getEntityWorld();
            if(!world.isRemote){
                clearOldData();
                ModUtil.LOGGER.info("Loading WorldData!");

                WorldData savedData = (WorldData)world.loadItemData(WorldData.class, DATA_TAG);
                //Generate new SavedData
                if(savedData == null){
                    ModUtil.LOGGER.info("No WorldData found, creating...");

                    savedData = new WorldData(DATA_TAG);
                    world.setItemData(DATA_TAG, savedData);
                }
                else{
                    ModUtil.LOGGER.info("WorldData sucessfully received!");
                }

                //Set the current SavedData to the retreived one
                WorldData.instance = savedData;
            }
        }
    }

    public static void clearOldData(){
        if(!LaserRelayConnectionHandler.getInstance().networks.isEmpty()){
            ModUtil.LOGGER.info("Clearing leftover Laser Relay Connection Data from other worlds!");
            LaserRelayConnectionHandler.getInstance().networks.clear();
        }
        if(!PlayerServerData.playerSaveData.isEmpty()){
            ModUtil.LOGGER.info("Clearing leftover Persistent Server Data from other worlds!");
            PlayerServerData.playerSaveData.clear();
        }
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound){
        //Laser World Data
        NBTTagList networkList = compound.getTagList("Networks", 10);
        for(int i = 0; i < networkList.tagCount(); i++){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().readNetworkFromNBT(networkList.getCompoundTagAt(i));
            LaserRelayConnectionHandler.getInstance().networks.add(network);
        }

        //Player Data
        NBTTagList playerList = compound.getTagList("PlayerData", 10);
        for(int i = 0; i < playerList.tagCount(); i++){
            PlayerServerData.PlayerSave aSave = PlayerServerData.PlayerSave.fromNBT(playerList.getCompoundTagAt(i));
            PlayerServerData.playerSaveData.add(aSave);
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound){
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
