package ellpeck.actuallyadditions.manual;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import io.netty.buffer.ByteBuf;

public class PacketOpenManual implements IMessage{

    @SuppressWarnings("unused")
    public PacketOpenManual(){

    }

    @Override
    public void fromBytes(ByteBuf buf){

    }

    @Override
    public void toBytes(ByteBuf buf){

    }

    public static class Handler implements IMessageHandler<PacketOpenManual, IMessage>{

        @Override
        public IMessage onMessage(PacketOpenManual message, MessageContext ctx){
            ctx.getServerHandler().playerEntity.openGui(ActuallyAdditions.instance, GuiHandler.MANUAL_ID, ctx.getServerHandler().playerEntity.worldObj, (int)ctx.getServerHandler().playerEntity.posX, (int)ctx.getServerHandler().playerEntity.posY, (int)ctx.getServerHandler().playerEntity.posZ);
            return null;
        }
    }
}
