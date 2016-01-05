/*
 * This file ("PacketGuiNumber.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketGuiNumber implements IMessage{

    private int tileX;
    private int tileY;
    private int tileZ;
    private int worldID;
    private int text;
    private int textID;
    private int playerID;

    @SuppressWarnings("unused")
    public PacketGuiNumber(){

    }

    public PacketGuiNumber(int x, int y, int z, World world, int text, int textID, EntityPlayer player){
        this.tileX = x;
        this.tileY = y;
        this.tileZ = z;
        this.worldID = world.provider.dimensionId;
        this.text = text;
        this.textID = textID;
        this.playerID = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.tileX = buf.readInt();
        this.tileY = buf.readInt();
        this.tileZ = buf.readInt();
        this.worldID = buf.readInt();
        this.text = buf.readInt();
        this.textID = buf.readInt();
        this.playerID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.tileX);
        buf.writeInt(this.tileY);
        buf.writeInt(this.tileZ);
        buf.writeInt(this.worldID);
        buf.writeInt(this.text);
        buf.writeInt(this.textID);
        buf.writeInt(this.playerID);
    }

    public static class Handler implements IMessageHandler<PacketGuiNumber, IMessage>{

        @Override
        public IMessage onMessage(PacketGuiNumber message, MessageContext ctx){
            World world = DimensionManager.getWorld(message.worldID);
            TileEntity tile = world.getTileEntity(message.tileX, message.tileY, message.tileZ);

            if(tile instanceof INumberReactor){
                INumberReactor reactor = (INumberReactor)tile;
                reactor.onNumberReceived(message.text, message.textID, (EntityPlayer)world.getEntityByID(message.playerID));
            }

            return null;
        }
    }
}
