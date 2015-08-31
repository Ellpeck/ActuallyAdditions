/*
 * This file ("PacketGiveBook.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.InitItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketGiveBook implements IMessage{

    @SuppressWarnings("unused")
    public PacketGiveBook(){

    }

    private EntityPlayer player;

    public PacketGiveBook(EntityPlayer player){
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

    public static class Handler implements IMessageHandler<PacketGiveBook, IMessage>{

        @Override
        public IMessage onMessage(PacketGiveBook message, MessageContext ctx){
            EntityItem entityItem = new EntityItem(message.player.worldObj, message.player.posX, message.player.posY, message.player.posZ, new ItemStack(InitItems.itemLexicon));
            entityItem.delayBeforeCanPickup = 0;
            message.player.worldObj.spawnEntityInWorld(entityItem);

            return null;
        }
    }
}
