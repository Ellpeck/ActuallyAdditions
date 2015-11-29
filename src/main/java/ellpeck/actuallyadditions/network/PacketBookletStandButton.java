/*
 * This file ("PacketBookletStand.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.tile.TileEntityBookletStand;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.Objects;

public class PacketBookletStandButton implements IMessage{

    private int tileX;
    private int tileY;
    private int tileZ;
    private int worldID;
    private int playerID;

    private int entryID;
    private int chapterID;
    private int pageID;
    private int pageInIndex;

    @SuppressWarnings("unused")
    public PacketBookletStandButton(){

    }

    public PacketBookletStandButton(int x, int y, int z, World world, EntityPlayer player, BookletEntry entry, BookletChapter chapter, BookletPage page, int pageInIndex){
        this.tileX = x;
        this.tileY = y;
        this.tileZ = z;
        this.worldID = world.provider.dimensionId;
        this.playerID = player.getEntityId();

        this.entryID = entry == null ? -1 : InitBooklet.entries.indexOf(entry);
        this.chapterID =  entry == null || chapter == null ? -1 : entry.chapters.indexOf(chapter);
        this.pageID = page == null ? -1 : page.getID();
        this.pageInIndex = pageInIndex;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.tileX = buf.readInt();
        this.tileY = buf.readInt();
        this.tileZ = buf.readInt();
        this.worldID = buf.readInt();
        this.playerID = buf.readInt();

        this.chapterID = buf.readInt();
        this.pageID = buf.readInt();
        this.entryID = buf.readInt();
        this.pageInIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.tileX);
        buf.writeInt(this.tileY);
        buf.writeInt(this.tileZ);
        buf.writeInt(this.worldID);
        buf.writeInt(this.playerID);

        buf.writeInt(this.chapterID);
        buf.writeInt(this.pageID);
        buf.writeInt(this.entryID);
        buf.writeInt(this.pageInIndex);
    }

    public static class Handler implements IMessageHandler<PacketBookletStandButton, IMessage>{

        @Override
        public IMessage onMessage(PacketBookletStandButton message, MessageContext ctx){
            World world = DimensionManager.getWorld(message.worldID);
            TileEntity tile = world.getTileEntity(message.tileX, message.tileY, message.tileZ);
            EntityPlayer player = (EntityPlayer)world.getEntityByID(message.playerID);

            if(tile instanceof TileEntityBookletStand){
                if(Objects.equals(player.getCommandSenderName(), ((TileEntityBookletStand)tile).assignedPlayer)){
                    ((TileEntityBookletStand)tile).setEntry(message.entryID, message.chapterID, message.pageID, message.pageInIndex);
                    world.markBlockForUpdate(message.tileX, message.tileY, message.tileZ);
                }
            }

            return null;
        }
    }
}
