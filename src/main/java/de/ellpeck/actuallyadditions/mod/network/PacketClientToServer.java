/*
 * This file ("PacketClientToServer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.commons.lang3.tuple.Pair;

public record PacketClientToServer(CompoundTag data, IDataHandler handler) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, PacketClientToServer> CODEC = CustomPacketPayload.codec(
            PacketClientToServer::write,
            PacketClientToServer::new);
    public static final Type<PacketClientToServer> ID = new Type<>(ActuallyAdditions.modLoc("client_to_server"));

    public PacketClientToServer(Pair<CompoundTag, IDataHandler> data) {
        this(data.getLeft(), data.getRight());
    }

    public PacketClientToServer(final FriendlyByteBuf buffer) {
        this(fromBytes(buffer));
    }

    public static Pair<CompoundTag, IDataHandler> fromBytes(FriendlyByteBuf buffer) {
        try {
            CompoundTag data = buffer.readNbt();

            int handlerId = buffer.readInt();
            if (handlerId >= 0 && handlerId < PacketHandler.DATA_HANDLERS.size()) {
                return Pair.of(data, PacketHandler.DATA_HANDLERS.get(handlerId));
            }
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to receive a server packet!", e);
        }
        return Pair.of(null, null);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeNbt(data);
        buf.writeInt(PacketHandler.DATA_HANDLERS.indexOf(handler));
    }

    public static void handle(final PacketClientToServer message, final IPayloadContext context) {
        context.enqueueWork(
                () -> {
                    if (message.data != null && message.handler != null) {
                        message.handler.handleData(message.data, context);
                    }
                }
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
