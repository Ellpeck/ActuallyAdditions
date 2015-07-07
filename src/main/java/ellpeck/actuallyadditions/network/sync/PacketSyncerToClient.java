package ellpeck.actuallyadditions.network.sync;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSyncerToClient implements IMessage{

    private int x;
    private int y;
    private int z;
    private int[] values;

    @SuppressWarnings("unused")
    public PacketSyncerToClient(){

    }

    public PacketSyncerToClient(TileEntity tile, int[] values){
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.values = values;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        int length = buf.readInt();
        if(this.values == null) this.values = new int[length];
        for(int i = 0; i < length; i++){
            this.values[i] = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.values.length);
        for(int value : this.values){
            buf.writeInt(value);
        }
    }

    public static class Handler implements IMessageHandler<PacketSyncerToClient, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSyncerToClient message, MessageContext ctx){
            World world = FMLClientHandler.instance().getClient().theWorld;
            TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
            if(tile != null && tile instanceof IPacketSyncerToClient){
                ((IPacketSyncerToClient)tile).setValues(message.values);
            }
            return null;
        }
    }

    public static void sendPacket(TileEntity tile){
        if(tile instanceof IPacketSyncerToClient){
            PacketHandler.theNetwork.sendToAllAround(new PacketSyncerToClient(tile, ((IPacketSyncerToClient)tile).getValues()), new NetworkRegistry.TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, 128));
        }
    }
}
