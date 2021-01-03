package de.ellpeck.actuallyadditions.common.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class ActuallyTile extends TileEntity {
    public ActuallyTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, -1, this.serializeNBT());
    }

    public void sendTileEntityToClients() {
        ServerWorld world = (ServerWorld) this.getWorld();
        Stream<ServerPlayerEntity> entities = world.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(this.getPos()), false);
        SUpdateTileEntityPacket packet = this.getUpdatePacket();
        entities.forEach(e -> e.connection.sendPacket(Objects.requireNonNull(packet)));
    }
}
