package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public final class VanillaPacketDispatcher {

    //Don't call from the client.
    public static void dispatchTEToNearbyPlayers(BlockEntity blockEntity) {
        ServerLevel serverLevel = (ServerLevel) blockEntity.getLevel();
        LevelChunk chunk = serverLevel.getChunk(blockEntity.getBlockPos().getX() >> 4, blockEntity.getBlockPos().getZ() >> 4);

        serverLevel.getChunkSource().chunkMap.getPlayers(chunk.getPos(), false).forEach(e -> {
            e.connection.send(blockEntity.getUpdatePacket());
        });
    }

    public static void dispatchTEToNearbyPlayers(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity != null) {
            dispatchTEToNearbyPlayers(blockEntity);
        }
    }
}
