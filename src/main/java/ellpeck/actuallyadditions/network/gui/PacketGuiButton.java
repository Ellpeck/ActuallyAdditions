/*
 * This file ("PacketGuiButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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
        this.worldID = world.provider.dimensionId;
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
            TileEntity tile = world.getTileEntity(message.tileX, message.tileY, message.tileZ);

            if(tile instanceof IButtonReactor){
                IButtonReactor reactor = (IButtonReactor)tile;
                reactor.onButtonPressed(message.buttonID, (EntityPlayer)world.getEntityByID(message.playerID));
            }

            return null;
        }
    }
}
