/*
 * This file ("WorldData.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.data;

import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.common.WorldSpecificSaveHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldData extends WorldSavedData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"data";
    //TODO Remove this as well
    public static List<File> legacyLoadWorlds = new ArrayList<File>();
    private static WorldData data;
    public final Set<Network> laserRelayNetworks = new ConcurrentSet<Network>();
    public final Map<UUID, PlayerSave> playerSaveData = new ConcurrentHashMap<UUID, PlayerSave>();
    public final Map<UUID, BlockPos> generatedCaves = new ConcurrentHashMap<UUID, BlockPos>();

    public WorldData(String name){
        super(name);
    }

    public static WorldData get(World world, boolean forceLoad){
        if(forceLoad || data == null){
            if(!world.isRemote){
                WorldSavedData savedData = world.loadData(WorldData.class, DATA_TAG);

                if(!(savedData instanceof WorldData)){
                    ModUtil.LOGGER.info("No WorldData found, creating...");

                    WorldData newData = new WorldData(DATA_TAG);
                    world.setData(DATA_TAG, newData);
                    data = newData;
                }
                else{
                    data = (WorldData)savedData;
                    ModUtil.LOGGER.info("Successfully loaded WorldData!");
                }

                //TODO Remove this part as well
                if(!legacyLoadWorlds.isEmpty()){
                    for(File legacyFile : legacyLoadWorlds){
                        if(legacyFile != null && legacyFile.exists()){
                            String name = legacyFile.getName();

                            try{
                                FileInputStream stream = new FileInputStream(legacyFile);
                                NBTTagCompound compound = CompressedStreamTools.readCompressed(stream);
                                stream.close();
                                WorldData data = get(world);
                                data.readFromNBT(compound, true);
                                data.markDirty();

                                ModUtil.LOGGER.info("Successfully received and merged legacy WorldData "+name+"!");

                                if(legacyFile.delete()){
                                    ModUtil.LOGGER.info("Successfully deleted legacy WorldData "+name+"!");
                                }
                                else{
                                    ModUtil.LOGGER.warn("Couldn't delete legacy WorldData file "+name+"!");
                                }
                            }
                            catch(Exception e){
                                ModUtil.LOGGER.error("Something went wrong trying to load legacy WorldData "+name+"!", e);
                            }
                        }
                    }

                    legacyLoadWorlds.clear();
                }
            }
            else{
                data = new WorldData(DATA_TAG);
                ModUtil.LOGGER.info("Created temporary WorldData to cache data on the client!");
            }
        }

        return data;
    }

    public static void clear(){
        if(data != null){
            data = null;
        }
    }

    public static WorldData get(World world){
        return get(world, false);
    }

    //TODO Remove old loading mechanic after a while because it's legacy
    public static void loadLegacy(World world){
        if(!world.isRemote && world instanceof WorldServer){
            int dim = world.provider.getDimension();
            ISaveHandler handler = new WorldSpecificSaveHandler((WorldServer)world, world.getSaveHandler());
            File dataFile = handler.getMapFileFromName(DATA_TAG+dim);
            legacyLoadWorlds.add(dataFile);
        }
    }

    //TODO Remove merging once removing old save handler
    private void readFromNBT(NBTTagCompound compound, boolean merge){
        //Laser World Data
        if(!merge){
            this.laserRelayNetworks.clear();
        }
        NBTTagList networkList = compound.getTagList("Networks", 10);
        for(int i = 0; i < networkList.tagCount(); i++){
            Network network = LaserRelayConnectionHandler.readNetworkFromNBT(networkList.getCompoundTagAt(i));
            this.laserRelayNetworks.add(network);
        }

        //Player Data
        if(!merge){
            this.playerSaveData.clear();
        }
        NBTTagList playerList = compound.getTagList("PlayerData", 10);
        for(int i = 0; i < playerList.tagCount(); i++){
            NBTTagCompound player = playerList.getCompoundTagAt(i);

            UUID id = player.getUniqueId("UUID");
            NBTTagCompound data = player.getCompoundTag("Data");

            PlayerSave save = new PlayerSave(id);
            save.readFromNBT(data, true);
            this.playerSaveData.put(id, save);
        }

        this.generatedCaves.clear();

        if(ActuallyAdditions.isCaveMode){
            NBTTagList caveList = compound.getTagList("GeneratedCaves", 10);
            for(int i = 0; i < caveList.tagCount(); i++){
                NBTTagCompound cave = caveList.getCompoundTagAt(i);

                UUID id = cave.getUniqueId("UUID");
                BlockPos pos = new BlockPos(cave.getInteger("X"), cave.getInteger("Y"), cave.getInteger("Z"));

                this.generatedCaves.put(id, pos);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.readFromNBT(compound, false);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
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

        if(ActuallyAdditions.isCaveMode){
            NBTTagList caveList = new NBTTagList();
            for(Map.Entry<UUID, BlockPos> entry : this.generatedCaves.entrySet()){
                NBTTagCompound cave = new NBTTagCompound();

                cave.setUniqueId("UUID", entry.getKey());
                BlockPos pos = entry.getValue();
                cave.setInteger("X", pos.getX());
                cave.setInteger("Y", pos.getY());
                cave.setInteger("Z", pos.getZ());

                caveList.appendTag(cave);
            }
            compound.setTag("GeneratedCaves", caveList);
        }

        return compound;
    }
}
