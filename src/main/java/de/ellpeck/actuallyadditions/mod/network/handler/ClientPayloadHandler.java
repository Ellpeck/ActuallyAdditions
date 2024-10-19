package de.ellpeck.actuallyadditions.mod.network.handler;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.network.packet.SpawnLaserPacket;
import de.ellpeck.actuallyadditions.mod.network.packet.SpawnLaserParticlePacket;
import de.ellpeck.actuallyadditions.mod.network.packet.SyncPlayerPacket;
import de.ellpeck.actuallyadditions.mod.network.packet.TileUpdatePacket;
import de.ellpeck.actuallyadditions.mod.particle.ParticleLaserItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleSpawnLaser(final SpawnLaserPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    if (context.flow().isClientbound()) {
                        AssetUtil.spawnLaserWithTimeClient(
                                packet.startX(), packet.startY(), packet.startZ(),
                                packet.endX(), packet.endY(), packet.endZ(),
                                packet.color(), packet.maxAge(), packet.rotationTime(),
                                packet.size(), packet.alpha()
                        );
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("actuallyadditions.networking.spawn_laser.failed", e.getMessage()));
                    return null;
                });
    }

    public void handleSpawnLaserParticle(final SpawnLaserParticlePacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    if (context.flow().isClientbound()) {
                        Player player = context.player();
                        if (player != null) {
                            ItemStack stack = packet.stack();

                            BlockPos in = packet.in();
                            double inX = in.getX() + 0.5;
                            double inY = in.getY() + 0.78;
                            double inZ = in.getZ() + 0.5;

                            BlockPos out = packet.out();
                            double outX = out.getX() + 0.5;
                            double outY = out.getY() + 0.525;
                            double outZ = out.getZ() + 0.5;

                            player.level().addParticle(ParticleLaserItem.Factory.createData(stack, inX, inY, inZ),
                                    outX, outY, outZ, 0, 0.025, 0);
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("actuallyadditions.networking.spawn_laser_particle.failed", e.getMessage()));
                    return null;
                });
    }

    public void handleTileUpdate(final TileUpdatePacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    if (context.flow().isClientbound()) {
                        Player player = context.player();
                        if (player != null) {
                            BlockEntity tile = player.level().getBlockEntity(packet.pos());
                            if (tile instanceof TileEntityBase tileBase) {
                                tileBase.readSyncableNBT(packet.data(), player.registryAccess(), TileEntityBase.NBTType.SYNC);
                            }
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("actuallyadditions.networking.tile_update.failed", e.getMessage()));
                    return null;
                });
    }

    public void handleSyncPlayer(final SyncPlayerPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    if (context.flow().isClientbound()) {
                        Player player = context.player(); //ActuallyAdditions.PROXY.getCurrentPlayer();
                        CompoundTag tag = packet.tag();
                        CompoundTag dataTag = tag.getCompound("Data");

                        if (player != null) {
                            PlayerData.getDataFromPlayer(player).readFromNBT(dataTag, false);

                            if (tag.getBoolean("Log")) {
                                ActuallyAdditions.LOGGER.info("Receiving (new or changed) Player Data for player {}.", player.getName());
                            }
                        } else {
                            ActuallyAdditions.LOGGER.error("Tried to receive Player Data for the current player, but he doesn't seem to be present!");
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("actuallyadditions.networking.sync_player_client.failed", e.getMessage()));
                    return null;
                });
    }


}
