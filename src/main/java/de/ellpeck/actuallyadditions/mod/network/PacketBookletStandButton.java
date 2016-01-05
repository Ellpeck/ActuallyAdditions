/*
 * This file ("PacketBookletStandButton.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.ellpeck.actuallyadditions.mod.booklet.EntrySet;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
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

    public PacketBookletStandButton(int x, int y, int z, World world, EntityPlayer player, EntrySet set){
        this.tileX = x;
        this.tileY = y;
        this.tileZ = z;
        this.worldID = world.provider.dimensionId;
        this.playerID = player.getEntityId();

        this.entryID = set.entry == null ? -1 : InitBooklet.entries.indexOf(set.entry);
        this.chapterID = set.entry == null || set.chapter == null ? -1 : set.entry.chapters.indexOf(set.chapter);
        this.pageID = set.page == null ? -1 : set.page.getID();
        this.pageInIndex = set.pageInIndex;
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
                    EntrySet theSet = ((TileEntityBookletStand)tile).assignedEntry;
                    theSet.entry = message.entryID == -1 ? null : InitBooklet.entries.get(message.entryID);
                    theSet.chapter = message.chapterID == -1 || message.entryID == -1 || theSet.entry.chapters.size() <= message.chapterID ? null : theSet.entry.chapters.get(message.chapterID);
                    theSet.page = message.chapterID == -1 || theSet.chapter == null || theSet.chapter.pages.length <= message.pageID-1 ? null : theSet.chapter.pages[message.pageID-1];
                    theSet.pageInIndex = message.pageInIndex;
                    ((TileEntityBookletStand)tile).sendUpdate();
                }
            }

            return null;
        }
    }
}
