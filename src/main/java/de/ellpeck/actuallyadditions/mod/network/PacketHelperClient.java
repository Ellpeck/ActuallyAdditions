/*
 * This file ("PacketHelperClient.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.network.packet.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.particle.ParticleLaserItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PacketHelperClient {
    public static void handleLaser(CompoundTag compound, IPayloadContext context) {
        AssetUtil.spawnLaserWithTimeClient(compound.getDouble("StartX"), compound.getDouble("StartY"), compound.getDouble("StartZ"), compound.getDouble("EndX"), compound.getDouble("EndY"), compound.getDouble("EndZ"), compound.getInt("Color"), compound.getInt("MaxAge"), compound.getDouble("RotationTime"), compound.getFloat("Size"), compound.getFloat("Alpha"));
    }

    public static void handleTileUpdate(CompoundTag compound, IPayloadContext context) {
        Level world = Minecraft.getInstance().level;
        if (world != null) {
            BlockEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));
            if (tile instanceof TileEntityBase tileBase) {
                tileBase.readSyncableNBT(compound.getCompound("Data"), world.registryAccess(), TileEntityBase.NBTType.SYNC);
            }
        }
    }

    public static void handleLaserParticle(CompoundTag compound, IPayloadContext context) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack stack = ItemStack.parseOptional(context.player().registryAccess(), compound);

        double inX = compound.getDouble("InX") + 0.5;
        double inY = compound.getDouble("InY") + 0.78;
        double inZ = compound.getDouble("InZ") + 0.5;

        double outX = compound.getDouble("OutX") + 0.5;
        double outY = compound.getDouble("OutY") + 0.525;
        double outZ = compound.getDouble("OutZ") + 0.5;

        mc.level.addParticle(ParticleLaserItem.Factory.createData(stack, inX, inY, inZ),
                outX, outY, outZ, 0, 0.025, 0);
    }

    public static void handlePlayerUpdate(CompoundTag compound, IPayloadContext context) {
        CompoundTag dataTag = compound.getCompound("Data");
        Player player = context.player(); //ActuallyAdditions.PROXY.getCurrentPlayer();

        if (player != null) {
            PlayerData.getDataFromPlayer(player).readFromNBT(dataTag, false);

            if (compound.getBoolean("Log")) {
                ActuallyAdditions.LOGGER.info("Receiving (new or changed) Player Data for player " + player.getName() + ".");
            }
        } else {
            ActuallyAdditions.LOGGER.error("Tried to receive Player Data for the current player, but he doesn't seem to be present!");
        }
    }

    public static void sendButtonPacket(BlockEntity tile, int buttonId) {
        CompoundTag compound = new CompoundTag();
        BlockPos pos = tile.getBlockPos();
        compound.putInt("X", pos.getX());
        compound.putInt("Y", pos.getY());
        compound.putInt("Z", pos.getZ());
        compound.putString("WorldID", tile.getLevel().dimension().location().toString());
        compound.putInt("PlayerID", Minecraft.getInstance().player.getId());
        compound.putInt("ButtonID", buttonId);
        PacketDistributor.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }

    public static void sendPlayerDataToServer(boolean log, int type) {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("Log", log);
        compound.putInt("Type", type);

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            compound.putString("World", player.level().dimension().location().toString());
            compound.putUUID("UUID", player.getUUID());

            PlayerSave data = PlayerData.getDataFromPlayer(player);

            if (type == 0) {
                compound.put("Bookmarks", data.saveBookmarks());
            } else if (type == 1) {
                compound.putBoolean("DidBookTutorial", data.didBookTutorial);
            } else if (type == 2) {
                compound.put("Trials", data.saveTrials());

                int total = 0;
                for (IBookletChapter chapter : ActuallyAdditionsAPI.entryTrials.getAllChapters()) {
                    //if (chapter instanceof BookletChapterTrials) {
                    //    total++;
                    //}
                }

                if (data.completedTrials.size() >= total) {
                    compound.putBoolean("Achievement", true);
                }
            }

            PacketDistributor.sendToServer(new PacketClientToServer(compound, PacketHandler.PLAYER_DATA_TO_SERVER));
        }
    }


    public static void sendNumberPacket(BlockEntity tile, double number, int id) {
        CompoundTag compound = new CompoundTag();
        compound.putInt("X", tile.getBlockPos().getX());
        compound.putInt("Y", tile.getBlockPos().getY());
        compound.putInt("Z", tile.getBlockPos().getZ());
        compound.putString("WorldID", tile.getLevel().dimension().location().toString());
        compound.putInt("PlayerID", Minecraft.getInstance().player.getId());
        compound.putInt("NumberID", id);
        compound.putDouble("Number", number);
        PacketDistributor.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_NUMBER_TO_TILE_HANDLER));
    }
}
