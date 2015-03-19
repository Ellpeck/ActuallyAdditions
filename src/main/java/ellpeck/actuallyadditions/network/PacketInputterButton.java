package ellpeck.actuallyadditions.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketInputterButton implements IMessage{

    private int tileX;
    private int tileY;
    private int tileZ;
    private int worldID;
    private int buttonID;

    @SuppressWarnings("unused")
    public PacketInputterButton(){

    }

    public PacketInputterButton(int x, int y, int z, World world, int buttonID){
        this.tileX = x;
        this.tileY = y;
        this.tileZ = z;
        this.worldID = world.provider.dimensionId;
        this.buttonID = buttonID;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.tileX = buf.readInt();
        this.tileY = buf.readInt();
        this.tileZ = buf.readInt();
        this.worldID = buf.readInt();
        this.buttonID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.tileX);
        buf.writeInt(this.tileY);
        buf.writeInt(this.tileZ);
        buf.writeInt(this.worldID);
        buf.writeInt(this.buttonID);
    }

    public static class Handler implements IMessageHandler<PacketInputterButton, IMessage>{

        @Override
        public IMessage onMessage(PacketInputterButton message, MessageContext ctx){
            World world = DimensionManager.getWorld(message.worldID);
            TileEntity tile = world.getTileEntity(message.tileX, message.tileY, message.tileZ);

            if(tile instanceof TileEntityInputter){
                TileEntityInputter inputter = (TileEntityInputter)tile;
                inputter.onButtonPressed(message.buttonID);
            }

            return null;
        }
    }
}
