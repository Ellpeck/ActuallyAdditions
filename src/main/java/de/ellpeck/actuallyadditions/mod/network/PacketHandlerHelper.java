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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class PacketHandlerHelper {

    @OnlyIn(Dist.CLIENT)
    public static void sendButtonPacket(TileEntity tile, int buttonId) {
        CompoundNBT compound = new CompoundNBT();
        BlockPos pos = tile.getBlockPos();
        compound.putInt("X", pos.getX());
        compound.putInt("Y", pos.getY());
        compound.putInt("Z", pos.getZ());
        compound.putInt("WorldID", tile.getLevel().provider.getDimension());
        compound.putInt("PlayerID", Minecraft.getInstance().player.getId());
        compound.putInt("ButtonID", buttonId);
        PacketHandler.THE_NETWORK.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }

    public static void syncPlayerData(PlayerEntity player, boolean log) {
        CompoundNBT compound = new CompoundNBT();
        compound.putBoolean("Log", log);

        CompoundNBT data = new CompoundNBT();
        PlayerData.getDataFromPlayer(player).writeToNBT(data, false);
        compound.put("Data", data);

        if (player instanceof ServerPlayerEntity) {
            PacketHandler.THE_NETWORK.sendTo(new PacketServerToClient(compound, PacketHandler.SYNC_PLAYER_DATA), (ServerPlayerEntity) player);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void sendPlayerDataToServer(boolean log, int type) {
        CompoundNBT compound = new CompoundNBT();
        compound.putBoolean("Log", log);
        compound.putInt("Type", type);

        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            compound.putInt("World", player.level.provider.getDimension());
            compound.setUniqueId("UUID", player.getUUID());

            PlayerSave data = PlayerData.getDataFromPlayer(player);

            if (type == 0) {
                compound.put("Bookmarks", data.saveBookmarks());
            } else if (type == 1) {
                compound.putBoolean("DidBookTutorial", data.didBookTutorial);
            } else if (type == 2) {
                compound.put("Trials", data.saveTrials());

                int total = 0;
                for (IBookletChapter chapter : ActuallyAdditionsAPI.entryTrials.getAllChapters()) {
                    if (chapter instanceof BookletChapterTrials) {
                        total++;
                    }
                }

                if (data.completedTrials.size() >= total) {
                    compound.putBoolean("Achievement", true);
                }
            }

            PacketHandler.THE_NETWORK.sendToServer(new PacketClientToServer(compound, PacketHandler.PLAYER_DATA_TO_SERVER));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void sendNumberPacket(TileEntity tile, double number, int id) {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("X", tile.getBlockPos().getX());
        compound.putInt("Y", tile.getBlockPos().getY());
        compound.putInt("Z", tile.getBlockPos().getZ());
        compound.putInt("WorldID", tile.getLevel().provider.getDimension());
        compound.putInt("PlayerID", Minecraft.getInstance().player.getId());
        compound.putInt("NumberID", id);
        compound.putDouble("Number", number);
        PacketHandler.THE_NETWORK.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_NUMBER_TO_TILE_HANDLER));
    }
}
