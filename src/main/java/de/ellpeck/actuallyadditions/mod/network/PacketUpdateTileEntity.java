/*
 * This file ("PacketUpdateTileEntity.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateTileEntity implements IMessage{

    private NBTTagCompound compound;
    private BlockPos pos;

    @SuppressWarnings("unused")
    public PacketUpdateTileEntity(){

    }

    public PacketUpdateTileEntity(TileEntityBase tile){
        this.compound = tile.getSyncCompound();
        this.pos = tile.getPos();
    }

    @Override
    public void fromBytes(ByteBuf buf){
        PacketBuffer buffer = new PacketBuffer(buf);
        try{
            this.compound = buffer.readNBTTagCompoundFromBuffer();
            this.pos = buffer.readBlockPos();
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Something went wrong trying to receive a TileEntity packet!", e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf){
        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeNBTTagCompoundToBuffer(this.compound);
        buffer.writeBlockPos(this.pos);
    }

    public static class Handler implements IMessageHandler<PacketUpdateTileEntity, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketUpdateTileEntity message, MessageContext ctx){
            if(message.pos != null && message.compound != null){
                World world = Minecraft.getMinecraft().theWorld;
                if(world != null){
                    TileEntity tile = world.getTileEntity(message.pos);
                    if(tile != null && tile instanceof TileEntityBase){
                        ((TileEntityBase)tile).receiveSyncCompound(message.compound);
                    }
                }
            }
            return null;
        }
    }
}
