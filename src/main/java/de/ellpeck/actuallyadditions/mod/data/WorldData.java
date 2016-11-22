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

import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.common.WorldSpecificSaveHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"data";
    private static final ConcurrentHashMap<Integer, WorldData> WORLD_DATA = new ConcurrentHashMap<Integer, WorldData>();
    public final ConcurrentSet<Network> laserRelayNetworks = new ConcurrentSet<Network>();
    public final ConcurrentHashMap<UUID, PlayerSave> playerSaveData = new ConcurrentHashMap<UUID, PlayerSave>();
    private final ISaveHandler handler;
    private final int dimension;

    public WorldData(ISaveHandler handler, int dimension){
        this.handler = handler;
        this.dimension = dimension;
    }

    public static WorldData getWorldUnspecificData(){
        return getDataForWorld(DimensionType.OVERWORLD.getId());
    }

    public static WorldData getDataForWorld(World world){
        return getDataForWorld(world.provider.getDimension());
    }

    public static WorldData getDataForWorld(int dim){
        WorldData data = WORLD_DATA.get(dim);

        if(data == null){
            data = new WorldData(null, dim);
            WORLD_DATA.put(dim, data);
        }

        return data;
    }

    public static void load(World world){
        if(!world.isRemote && world instanceof WorldServer){
            WorldData data = new WorldData(new WorldSpecificSaveHandler((WorldServer)world, world.getSaveHandler()), world.provider.getDimension());
            WORLD_DATA.put(data.dimension, data);

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
            WorldData data = WORLD_DATA.get(world.provider.getDimension());
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
                    }
                }
                catch(Exception e){
                    ModUtil.LOGGER.error("Something went wrong trying to save WorldData for world "+data.dimension+"!", e);
                }
            }
            else{
                ModUtil.LOGGER.error("Tried to save WorldData for "+world.provider.getDimension()+" without any data handler being present!?");
            }
        }
    }

    public static void unload(World world){
        if(!world.isRemote){
            WORLD_DATA.remove(world.provider.getDimension());
            ModUtil.LOGGER.info("Unloading WorldData for world "+world.provider.getDimension()+"!");
        }
    }

    private void readFromNBT(NBTTagCompound compound){
        //Laser World Data
        this.laserRelayNetworks.clear();

        NBTTagList networkList = compound.getTagList("Networks", 10);
        for(int i = 0; i < networkList.tagCount(); i++){
            Network network = LaserRelayConnectionHandler.readNetworkFromNBT(networkList.getCompoundTagAt(i));
            this.laserRelayNetworks.add(network);
        }

        //Player Data
        this.playerSaveData.clear();
        NBTTagList playerList = compound.getTagList("PlayerData", 10);
        for(int i = 0; i < playerList.tagCount(); i++){
            NBTTagCompound player = playerList.getCompoundTagAt(i);

            UUID id = player.getUniqueId("UUID");
            NBTTagCompound data = player.getCompoundTag("Data");

            PlayerSave save = new PlayerSave(id);
            save.readFromNBT(data, true);
            this.playerSaveData.put(id, save);
        }
    }

    private void writeToNBT(NBTTagCompound compound){
        //Laser World Data
        NBTTagList networkList = new NBTTagList();
        for(Network network : this.laserRelayNetworks){
            networkList.appendTag(LaserRelayConnectionHandler.writeNetworkToNBT(network));
        }
        compound.setTag("Networks", networkList);

        //Player Data
        NBTTagList playerList = new NBTTagList();
        for(PlayerSave save : this.playerSaveData.values()){
            NBTTagCompound player = new NBTTagCompound();
            player.setUniqueId("UUID", save.id);

            NBTTagCompound data = new NBTTagCompound();
            save.writeToNBT(data, true);
            player.setTag("Data", data);

            playerList.appendTag(player);
        }
        compound.setTag("PlayerData", playerList);
    }
}
