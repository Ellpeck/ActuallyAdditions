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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientToServer {

    private CompoundNBT data;
    private IDataHandler handler;

    public PacketClientToServer() {

    }

    public PacketClientToServer(CompoundNBT data, IDataHandler handler) {
        this.data = data;
        this.handler = handler;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        try {
            this.data = buffer.readNbt();

            int handlerId = buffer.readInt();
            if (handlerId >= 0 && handlerId < PacketHandler.DATA_HANDLERS.size()) {
                this.handler = PacketHandler.DATA_HANDLERS.get(handlerId);
            }
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to receive a server packet!", e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeNbt(this.data);
        buffer.writeInt(PacketHandler.DATA_HANDLERS.indexOf(this.handler));
    }

    public static class Handler implements IMessageHandler<PacketClientToServer, IMessage> {

        @Override
        public IMessage onMessage(PacketClientToServer message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                if (message.data != null && message.handler != null) {
                    message.handler.handleData(message.data, ctx);
                }
            });
            return null;
        }
    }
}
