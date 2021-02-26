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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldData extends WorldSavedData {

    public static final String DATA_TAG = ActuallyAdditions.MODID + "data";
    private static WorldData data;
    public final ConcurrentSet<Network> laserRelayNetworks = new ConcurrentSet<>();
    public final ConcurrentHashMap<UUID, PlayerSave> playerSaveData = new ConcurrentHashMap<>();

    public WorldData(String name) {
        super(name);
    }

    public static WorldData get(World world, boolean forceLoad) {
        WorldData w = getInternal(world, forceLoad);
        if (w == null) {
            ActuallyAdditions.LOGGER.error("An impossible bug has occured.");
        }
        return w == null
            ? new WorldData(DATA_TAG)
            : w;
    }

    private static WorldData getInternal(World world, boolean forceLoad) {
        if (forceLoad || data == null) {
            if (!world.isRemote) {
                WorldSavedData savedData = world.loadData(WorldData.class, DATA_TAG);

                if (savedData == null) {
                    ActuallyAdditions.LOGGER.info("No WorldData found, creating...");

                    WorldData newData = new WorldData(DATA_TAG);
                    world.setData(DATA_TAG, newData);
                    data = newData;
                } else {
                    data = (WorldData) savedData;
                    ActuallyAdditions.LOGGER.info("Successfully loaded WorldData!");
                }
            } else {
                data = new WorldData(DATA_TAG);
                ActuallyAdditions.LOGGER.info("Created temporary WorldData to cache data on the client!");
            }
        }
        return data;
    }

    public static void clear() {
        if (data != null) {
            data = null;
            ActuallyAdditions.LOGGER.info("Unloaded WorldData!");
        }
    }

    public static WorldData get(World world) {
        return get(world, false);
    }

    @Override
    public void readFromNBT(CompoundNBT compound) {
        this.laserRelayNetworks.clear();
        ListNBT networkList = compound.getList("Networks", 10);
        for (int i = 0; i < networkList.size(); i++) {
            Network network = LaserRelayConnectionHandler.readNetworkFromNBT(networkList.getCompound(i));
            this.laserRelayNetworks.add(network);
        }

        this.playerSaveData.clear();
        ListNBT playerList = compound.getList("PlayerData", 10);
        for (int i = 0; i < playerList.size(); i++) {
            CompoundNBT player = playerList.getCompound(i);

            UUID id = player.getUniqueId("UUID");
            CompoundNBT data = player.getCompoundTag("Data");

            PlayerSave save = new PlayerSave(id);
            save.readFromNBT(data, true);
            this.playerSaveData.put(id, save);
        }
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT compound) {
        //Laser World Data
        ListNBT networkList = new ListNBT();
        for (Network network : this.laserRelayNetworks) {
            networkList.appendTag(LaserRelayConnectionHandler.writeNetworkToNBT(network));
        }
        compound.setTag("Networks", networkList);

        //Player Data
        ListNBT playerList = new ListNBT();
        for (PlayerSave save : this.playerSaveData.values()) {
            CompoundNBT player = new CompoundNBT();
            player.setUniqueId("UUID", save.id);

            CompoundNBT data = new CompoundNBT();
            save.writeToNBT(data, true);
            player.setTag("Data", data);

            playerList.appendTag(player);
        }
        compound.setTag("PlayerData", playerList);

        return compound;
    }
}
