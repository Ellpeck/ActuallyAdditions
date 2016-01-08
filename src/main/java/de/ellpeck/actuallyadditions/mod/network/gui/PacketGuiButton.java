/*
 * This file ("PacketGuiButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network.gui;

import de.ellpeck.actuallyadditions.api.Position;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiButton implements IMessage{

    private int tileX;
    private int tileY;
    private int tileZ;
    private int worldID;
    private int buttonID;
    private int playerID;

    @SuppressWarnings("unused")
    public PacketGuiButton(){

    }

    public PacketGuiButton(int x, int y, int z, World world, int buttonID, EntityPlayer player){
        this.tileX = x;
        this.tileY = y;
        this.tileZ = z;
        this.worldID = world.provider.getDimensionId();
        this.buttonID = buttonID;
        this.playerID = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.tileX = buf.readInt();
        this.tileY = buf.readInt();
        this.tileZ = buf.readInt();
        this.worldID = buf.readInt();
        this.buttonID = buf.readInt();
        this.playerID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.tileX);
        buf.writeInt(this.tileY);
        buf.writeInt(this.tileZ);
        buf.writeInt(this.worldID);
        buf.writeInt(this.buttonID);
        buf.writeInt(this.playerID);
    }

    public static class Handler implements IMessageHandler<PacketGuiButton, IMessage>{

        @Override
        public IMessage onMessage(PacketGuiButton message, MessageContext ctx){
            World world = DimensionManager.getWorld(message.worldID);
            TileEntity tile = world.getTileEntity(new Position(message.tileX, message.tileY, message.tileZ));

            if(tile instanceof IButtonReactor){
                IButtonReactor reactor = (IButtonReactor)tile;
                reactor.onButtonPressed(message.buttonID, (EntityPlayer)world.getEntityByID(message.playerID));
            }

            return null;
        }
    }
}
