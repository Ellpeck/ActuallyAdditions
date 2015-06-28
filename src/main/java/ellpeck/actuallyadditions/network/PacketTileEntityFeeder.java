package ellpeck.actuallyadditions.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.gui.GuiFeeder;
import ellpeck.actuallyadditions.tile.TileEntityFeeder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTileEntityFeeder implements IMessage{

    private int tileX;
    private int tileY;
    private int tileZ;
    private int animalID;

    @SuppressWarnings("unused")
    public PacketTileEntityFeeder(){

    }

    public PacketTileEntityFeeder(TileEntityFeeder tile, int animalID){
        this.tileX = tile.xCoord;
        this.tileY = tile.yCoord;
        this.tileZ = tile.zCoord;
        this.animalID = animalID;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.tileX = buf.readInt();
        this.tileY = buf.readInt();
        this.tileZ = buf.readInt();
        this.animalID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.tileX);
        buf.writeInt(this.tileY);
        buf.writeInt(this.tileZ);
        buf.writeInt(this.animalID);
    }

    public static class Handler implements IMessageHandler<PacketTileEntityFeeder, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketTileEntityFeeder message, MessageContext ctx){
            World world = FMLClientHandler.instance().getClient().theWorld;
            TileEntity tile = world.getTileEntity(message.tileX, message.tileY, message.tileZ);

            if(tile instanceof TileEntityFeeder){
                TileEntityFeeder tileFeeder = (TileEntityFeeder)tile;
                tileFeeder.feedAnimal((EntityAnimal)world.getEntityByID(message.animalID));

                if(Minecraft.getMinecraft().currentScreen instanceof GuiFeeder){
                    if(((GuiFeeder)Minecraft.getMinecraft().currentScreen).tileFeeder == tileFeeder){
                        ((GuiFeeder)Minecraft.getMinecraft().currentScreen).loveCounter++;
                    }
                }
            }

            return null;
        }
    }
}
