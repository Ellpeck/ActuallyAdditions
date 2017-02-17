/*
 * This file ("PacketHandlerHelper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapterTrials;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class PacketHandlerHelper{

    @SideOnly(Side.CLIENT)
    public static void sendButtonPacket(TileEntity tile, int buttonId){
        NBTTagCompound compound = new NBTTagCompound();
        BlockPos pos = tile.getPos();
        compound.setInteger("X", pos.getX());
        compound.setInteger("Y", pos.getY());
        compound.setInteger("Z", pos.getZ());
        compound.setInteger("WorldID", tile.getWorld().provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
        compound.setInteger("ButtonID", buttonId);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }

    public static void syncPlayerData(EntityPlayer player, boolean log){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Log", log);

        NBTTagCompound data = new NBTTagCompound();
        PlayerData.getDataFromPlayer(player).writeToNBT(data, false);
        compound.setTag("Data", data);

        if(player instanceof EntityPlayerMP){
            PacketHandler.theNetwork.sendTo(new PacketServerToClient(compound, PacketHandler.SYNC_PLAYER_DATA), (EntityPlayerMP)player);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void sendPlayerDataToServer(boolean log, int type){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Log", log);
        compound.setInteger("Type", type);

        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player != null){
            compound.setInteger("World", player.world.provider.getDimension());
            compound.setUniqueId("UUID", player.getUniqueID());

            PlayerSave data = PlayerData.getDataFromPlayer(player);

            if(type == 0){
                compound.setTag("Bookmarks", data.saveBookmarks());
            }
            else if(type == 1){
                compound.setBoolean("DidBookTutorial", data.didBookTutorial);
            }
            else if(type == 2){
                compound.setTag("Trials", data.saveTrials());

                int total = 0;
                for(IBookletChapter chapter : ActuallyAdditionsAPI.entryTrials.getAllChapters()){
                    if(chapter instanceof BookletChapterTrials){
                        total++;
                    }
                }

                if(data.completedTrials.size() >= total){
                    compound.setBoolean("Achievement", true);
                }
            }

            PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.PLAYER_DATA_TO_SERVER));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void sendNumberPacket(TileEntity tile, double number, int id){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("X", tile.getPos().getX());
        compound.setInteger("Y", tile.getPos().getY());
        compound.setInteger("Z", tile.getPos().getZ());
        compound.setInteger("WorldID", tile.getWorld().provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
        compound.setInteger("NumberID", id);
        compound.setDouble("Number", number);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_NUMBER_TO_TILE_HANDLER));
    }
}
