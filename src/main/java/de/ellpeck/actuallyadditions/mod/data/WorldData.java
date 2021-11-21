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
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO: [port] validate this still works
public class WorldData extends WorldSavedData {

    public static final String DATA_TAG = ActuallyAdditions.MODID + "data";
    public static final String SAVE_NAME = ActuallyAdditions.MODID + "_worldsave";
    private static WorldData data;
    public final ConcurrentSet<Network> laserRelayNetworks = new ConcurrentSet<>();
    public final ConcurrentHashMap<UUID, PlayerSave> playerSaveData = new ConcurrentHashMap<>();

    public WorldData() {
        super(SAVE_NAME);
    }

    public static WorldData get(World world) {
        WorldData storage = ((ServerWorld) world).getDataStorage().get(WorldData::new, SAVE_NAME);

        if (storage == null) {
            storage = new WorldData();
            storage.setDirty();
            ((ServerWorld) world).getDataStorage().set(storage);
        }

        return storage;
    }

    public static void clear() {
        if (data != null) {
            data = null;
            ActuallyAdditions.LOGGER.info("Unloaded WorldData!");
        }
    }

    @Override
    public void load(CompoundNBT compound) {
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

            UUID id = player.getUUID("UUID");
            CompoundNBT data = player.getCompound("Data");

            PlayerSave save = new PlayerSave(id);
            save.readFromNBT(data, true);
            this.playerSaveData.put(id, save);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        //Laser World Data
        ListNBT networkList = new ListNBT();
        for (Network network : this.laserRelayNetworks) {
            networkList.add(LaserRelayConnectionHandler.writeNetworkToNBT(network));
        }
        compound.put("Networks", networkList);

        //Player Data
        ListNBT playerList = new ListNBT();
        for (PlayerSave save : this.playerSaveData.values()) {
            CompoundNBT player = new CompoundNBT();
            player.putUUID("UUID", save.id);

            CompoundNBT data = new CompoundNBT();
            save.writeToNBT(data, true);
            player.put("Data", data);

            playerList.add(player);
        }
        compound.put("PlayerData", playerList);

        return compound;
    }
}
