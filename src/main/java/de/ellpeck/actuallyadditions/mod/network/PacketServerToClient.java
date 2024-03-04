/*
 * This file ("PacketServerToClient.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.apache.commons.lang3.tuple.Pair;


public record PacketServerToClient(CompoundTag data, IDataHandler handler) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ActuallyAdditions.MODID, "server_to_client");

    public PacketServerToClient(Pair<CompoundTag, IDataHandler> data) {
        this(data.getLeft(), data.getRight());
    }

    public PacketServerToClient(final FriendlyByteBuf buffer) {
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
            ActuallyAdditions.LOGGER.error("Something went wrong trying to receive a client packet!", e);
        }
        return Pair.of(null, null);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeNbt(data);
        buf.writeInt(PacketHandler.DATA_HANDLERS.indexOf(handler));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static void handle(final PacketServerToClient message, final PlayPayloadContext context) {
        context.workHandler().submitAsync(
            () -> {
                if (message.data != null && message.handler != null) {
                    message.handler.handleData(message.data, context);
                }
                }
            );
    }
}
