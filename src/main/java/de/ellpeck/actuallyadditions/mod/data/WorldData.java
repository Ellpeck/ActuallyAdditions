/*
 * This file ("WorldData.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.data;

import de.ellpeck.actuallyadditions.mod.data.PlayerServerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler.Network;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.common.WorldSpecificSaveHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorldData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"data";
    private static Map<Integer, WorldData> worldData = new ConcurrentHashMap<Integer, WorldData>();

    private ISaveHandler handler;
    private int dimension;

    public final ConcurrentSet<Network> laserRelayNetworks = new ConcurrentSet<Network>();
    public static final ArrayList<PlayerSave> PLAYER_SAVE_DATA = new ArrayList<PlayerSave>();

    public WorldData(ISaveHandler handler, int dimension){
        this.handler = handler;
        this.dimension = dimension;
    }

    public static WorldData getDataForWorld(int world){
        return worldData.get(world);
    }

    private void readFromNBT(NBTTagCompound compound){
        //Laser World Data
        NBTTagList networkList = compound.getTagList("Networks", 10);
        for(int i = 0; i < networkList.tagCount(); i++){
            Network network = LaserRelayConnectionHandler.readNetworkFromNBT(networkList.getCompoundTagAt(i));
            this.laserRelayNetworks.add(network);
        }

        if(this.dimension == 0){
            //Player Data
            PLAYER_SAVE_DATA.clear();

            NBTTagList playerList = compound.getTagList("PlayerData", 10);
            for(int i = 0; i < playerList.tagCount(); i++){
                PlayerSave aSave = PlayerSave.fromNBT(playerList.getCompoundTagAt(i));
                PLAYER_SAVE_DATA.add(aSave);
            }
        }
    }

    private void writeToNBT(NBTTagCompound compound){
        //Laser World Data
        NBTTagList networkList = new NBTTagList();
        for(Network network : this.laserRelayNetworks){
            networkList.appendTag(LaserRelayConnectionHandler.writeNetworkToNBT(network));
        }
        compound.setTag("Networks", networkList);

        if(this.dimension == 0){
            //Player Data
            NBTTagList playerList = new NBTTagList();
            for(PlayerSave theSave : PLAYER_SAVE_DATA){
                playerList.appendTag(theSave.toNBT());
            }
            compound.setTag("PlayerData", playerList);
        }
    }

    public static void load(World world){
        if(!world.isRemote && world instanceof WorldServer){
            WorldData data = new WorldData(new WorldSpecificSaveHandler((WorldServer)world, world.getSaveHandler()), world.provider.getDimension());
            worldData.put(data.dimension, data);

            try{
                File dataFile = data.handler.getMapFileFromName(DATA_TAG+data.dimension);

                if(dataFile != null && dataFile.exists()){
                    FileInputStream stream = new FileInputStream(dataFile);
                    NBTTagCompound compound = CompressedStreamTools.readCompressed(stream);
                    stream.close();
                    data.readFromNBT(compound);

                    ModUtil.LOGGER.info("Successfully received WorldData for world "+data.dimension+"!");
                }
                else{
                    ModUtil.LOGGER.info("No WorldData found for world "+data.dimension+", creating...");
                }

            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something went wrong trying to load WorldData for world "+data.dimension+"!", e);
            }
        }
    }

    public static void save(World world){
        if(!world.isRemote){
            WorldData data = worldData.get(world.provider.getDimension());
            if(data != null && data.handler != null){
                try{
                    File dataFile = data.handler.getMapFileFromName(DATA_TAG+data.dimension);

                    if(dataFile != null){
                        if(!dataFile.exists()){
                            dataFile.createNewFile();
                            ModUtil.LOGGER.info("Creating new WorldData file for world "+data.dimension+"!");
                        }

                        NBTTagCompound compound = new NBTTagCompound();
                        data.writeToNBT(compound);
                        FileOutputStream stream = new FileOutputStream(dataFile);
                        CompressedStreamTools.writeCompressed(compound, stream);
                        stream.close();

                        ModUtil.LOGGER.info("Saved WorldData for world "+data.dimension+"!");
                    }
                }
                catch(Exception e){
                    ModUtil.LOGGER.error("Something went wrong trying to save WorldData for world "+data.dimension+"!", e);
                }
            }
            else{
                ModUtil.LOGGER.error("Tried to save WorldData without any data being present!?");
            }
        }
    }

    public static void unload(World world){
        if(!world.isRemote){
            save(world);
            int dim = world.provider.getDimension();
            worldData.remove(dim);
            ModUtil.LOGGER.info("Unloading WorldData for world "+dim+"!");
        }
    }
}
