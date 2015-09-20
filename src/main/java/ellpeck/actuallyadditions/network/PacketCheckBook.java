/*
 * This file ("PacketCheckBook.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.PersistantVariables;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketCheckBook implements IMessage{

    @SuppressWarnings("unused")
    public PacketCheckBook(){

    }

    private EntityPlayer player;

    public PacketCheckBook(EntityPlayer player){
        this.player = player;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        World world = DimensionManager.getWorld(buf.readInt());
        this.player = (EntityPlayer)world.getEntityByID(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.player.worldObj.provider.dimensionId);
        buf.writeInt(this.player.getEntityId());
    }

    public static class Handler implements IMessageHandler<PacketCheckBook, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCheckBook message, MessageContext ctx){
            if(!PersistantVariables.getBoolean("BookGotten")){
                PacketHandler.theNetwork.sendToServer(new PacketGiveBook(message.player));
                PersistantVariables.setBoolean("BookGotten", true);
            }
            return null;
        }
    }
}
