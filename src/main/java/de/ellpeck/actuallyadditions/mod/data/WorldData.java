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
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO: [port] validate this still works
public class WorldData extends SavedData {

    public static final String DATA_TAG = ActuallyAdditions.MODID + "data";
    public static final String SAVE_NAME = ActuallyAdditions.MODID + "_worldsave";
    private static WorldData data;
    public final ConcurrentSet<Network> laserRelayNetworks = new ConcurrentSet<>();
    public final ConcurrentHashMap<UUID, PlayerSave> playerSaveData = new ConcurrentHashMap<>();

    public WorldData() {
        super();
    }

    public static WorldData get(Level level) {
        return ((ServerLevel) level).getDataStorage().computeIfAbsent(new Factory<>(WorldData::new, WorldData::load), SAVE_NAME);
    }

    //TODO what in the world is this?
    public static void clear() {
        if (data != null) {
            data = null;
            ActuallyAdditions.LOGGER.info("Unloaded WorldData!");
        }
    }

    public static WorldData load(CompoundTag compound) {
        WorldData worldData = new WorldData();
        worldData.laserRelayNetworks.clear();
        ListTag networkList = compound.getList("Networks", 10);
        for (int i = 0; i < networkList.size(); i++) {
            Network network = LaserRelayConnectionHandler.readNetworkFromNBT(networkList.getCompound(i));
            worldData.laserRelayNetworks.add(network);
        }

        worldData.playerSaveData.clear();
        ListTag playerList = compound.getList("PlayerData", 10);
        for (int i = 0; i < playerList.size(); i++) {
            CompoundTag player = playerList.getCompound(i);

            UUID id = player.getUUID("UUID");
            CompoundTag data = player.getCompound("Data");

            PlayerSave save = new PlayerSave(id);
            save.readFromNBT(data, true);
            worldData.playerSaveData.put(id, save);
        }
        return worldData;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        //Laser World Data
        ListTag networkList = new ListTag();
        for (Network network : this.laserRelayNetworks) {
            networkList.add(LaserRelayConnectionHandler.writeNetworkToNBT(network));
        }
        compound.put("Networks", networkList);

        //Player Data
        ListTag playerList = new ListTag();
        for (PlayerSave save : this.playerSaveData.values()) {
            CompoundTag player = new CompoundTag();
            player.putUUID("UUID", save.id);

            CompoundTag data = new CompoundTag();
            save.writeToNBT(data, true);
            player.put("Data", data);

            playerList.add(player);
        }
        compound.put("PlayerData", playerList);

        return compound;
    }
}
