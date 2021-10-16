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
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientToServer {

    private CompoundNBT data;
    private IDataHandler handler;

    public PacketClientToServer() {

    }

    public PacketClientToServer(CompoundNBT data, IDataHandler handler) {
        this.data = data;
        this.handler = handler;
    }

    public static PacketClientToServer fromBytes(PacketBuffer buffer) {
        try {
            CompoundNBT data = buffer.readNbt();

            int handlerId = buffer.readInt();
            if (handlerId >= 0 && handlerId < PacketHandler.DATA_HANDLERS.size()) {
                return new PacketClientToServer(data, PacketHandler.DATA_HANDLERS.get(handlerId));
            }
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to receive a server packet!", e);
        }
        return new PacketClientToServer();
    }

    public static void toBytes(PacketClientToServer message, PacketBuffer buffer) {
        buffer.writeNbt(message.data);
        buffer.writeInt(PacketHandler.DATA_HANDLERS.indexOf(message.handler));
    }

    public static void handle(final PacketClientToServer message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork( () -> {
            if (message.data != null && message.handler != null) {
                message.handler.handleData(message.data, ctx.get());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
