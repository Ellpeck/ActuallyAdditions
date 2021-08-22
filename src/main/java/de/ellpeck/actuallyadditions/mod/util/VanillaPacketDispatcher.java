package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class VanillaPacketDispatcher {

    //Don't call from the client.
    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        ServerWorld world = (ServerWorld) tile.getLevel();
        PlayerChunkMapEntry entry = world.getPlayerChunkMap().getEntry(tile.getBlockPos().getX() >> 4, tile.getBlockPos().getZ() >> 4);

        if (entry == null) {
            return;
        }

        for (ServerPlayerEntity player : entry.getWatchingPlayers()) {
            player.connection.send(tile.getUpdatePacket());
        }

    }

    public static void dispatchTEToNearbyPlayers(World world, BlockPos pos) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile != null) {
            dispatchTEToNearbyPlayers(tile);
        }
    }
}
