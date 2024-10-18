/*
 * This file ("PacketHandlerHelper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.network.packet.PacketServerToClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * A helper class for sending packets from the server to the client.
 */
public final class PacketHelperServer {

    public static void syncPlayerData(Player player, boolean log) {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("Log", log);

        CompoundTag data = new CompoundTag();
        PlayerData.getDataFromPlayer(player).writeToNBT(data, false);
        compound.put("Data", data);
        ActuallyAdditions.LOGGER.info("Sending data {}", data);

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new PacketServerToClient(compound, PacketHandler.SYNC_PLAYER_DATA));
        }
    }
}
