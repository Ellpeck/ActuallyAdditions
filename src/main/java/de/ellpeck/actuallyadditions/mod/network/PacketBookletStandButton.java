/*
 * This file ("PacketBookletStandButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.api.internal.IEntrySet;
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBookletStandButton implements IMessage{

    private int worldID;
    private int playerID;

    private NBTTagCompound entrySet;
    private BlockPos tilePos;

    @SuppressWarnings("unused")
    public PacketBookletStandButton(){

    }

    public PacketBookletStandButton(BlockPos tilePos, World world, EntityPlayer player, IEntrySet set){
        this.tilePos = tilePos;
        this.entrySet = set.writeToNBT();
        this.worldID = world.provider.getDimension();
        this.playerID = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf){
        PacketBuffer buffer = new PacketBuffer(buf);
        try{
            this.entrySet = buffer.readNBTTagCompoundFromBuffer();
            this.tilePos = buffer.readBlockPos();
            this.worldID = buffer.readInt();
            this.playerID = buffer.readInt();
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Something went wrong trying to receive a TileEntity packet!", e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf){
        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeNBTTagCompoundToBuffer(this.entrySet);
        buffer.writeBlockPos(this.tilePos);
        buffer.writeInt(this.worldID);
        buffer.writeInt(this.playerID);
    }

    public static class Handler implements IMessageHandler<PacketBookletStandButton, IMessage>{

        @Override
        public IMessage onMessage(PacketBookletStandButton message, MessageContext ctx){
            World world = DimensionManager.getWorld(message.worldID);
            TileEntity tile = world.getTileEntity(message.tilePos);
            EntityPlayer player = (EntityPlayer)world.getEntityByID(message.playerID);

            if(player != null && tile instanceof TileEntityBookletStand){
                TileEntityBookletStand stand = (TileEntityBookletStand)tile;
                if(player.getName() != null && player.getName().equalsIgnoreCase(stand.assignedPlayer)){
                    stand.assignedEntry = EntrySet.readFromNBT(message.entrySet);
                    stand.markDirty();
                    stand.sendUpdate();
                }
            }

            return null;
        }
    }
}
