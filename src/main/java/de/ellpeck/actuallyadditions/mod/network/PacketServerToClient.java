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
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class PacketServerToClient {

    private CompoundTag data;
    private IDataHandler handler;

    public PacketServerToClient() {

    }

    public PacketServerToClient(CompoundTag data, IDataHandler handler) {
        this.data = data;
        this.handler = handler;
    }

    public static PacketServerToClient fromBytes(final FriendlyByteBuf buffer) {
        try {
            CompoundTag data = buffer.readNbt();

            int handlerId = buffer.readInt();
            if (handlerId >= 0 && handlerId < PacketHandler.DATA_HANDLERS.size()) {
                return new PacketServerToClient(data, PacketHandler.DATA_HANDLERS.get(handlerId));
            }
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to receive a client packet!", e);
        }
        return new PacketServerToClient();
    }

    public static void toBytes(final PacketServerToClient message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.data);
        buffer.writeInt(PacketHandler.DATA_HANDLERS.indexOf(message.handler));
    }

    public static void handle(final PacketServerToClient message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
            () -> {
                if (message.data != null && message.handler != null) {
                    message.handler.handleData(message.data, ctx.get());
                }
            }
        );
        ctx.get().setPacketHandled(true);
    }
}
