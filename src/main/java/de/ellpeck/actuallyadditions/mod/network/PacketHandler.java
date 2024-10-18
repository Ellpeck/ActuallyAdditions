/*
 * This file ("PacketHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
import de.ellpeck.actuallyadditions.mod.network.packet.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.packet.PacketServerToClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;

public final class PacketHandler {

    public static final List<IDataHandler> DATA_HANDLERS = new ArrayList<>();
    public static final IDataHandler LASER_HANDLER = new IDataHandler() {
        @Override
        public void handleData(CompoundTag compound, IPayloadContext context) {
            if(context.flow() == PacketFlow.CLIENTBOUND) {
                PacketHelperClient.handleLaser(compound, context);
            }
        }
    };
    public static final IDataHandler TILE_ENTITY_HANDLER = new IDataHandler() {
        @Override
        public void handleData(CompoundTag compound, IPayloadContext context) {
            if(context.flow() == PacketFlow.CLIENTBOUND) {
                PacketHelperClient.handleTileUpdate(compound, context);
            }
        }
    };
    public static final IDataHandler LASER_PARTICLE_HANDLER = new IDataHandler() {
        @Override
        public void handleData(CompoundTag compound, IPayloadContext context) {
            if(context.flow() == PacketFlow.CLIENTBOUND) {
                PacketHelperClient.handleLaserParticle(compound, context);
            }
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_TILE_HANDLER = (compound, context) -> {
        if (context.player() != null) {
            Player player = context.player();
            Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(compound.getString("WorldID"))));
            BlockEntity tile = level.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

            if (tile instanceof IButtonReactor reactor) {
                Entity entity = level.getEntity(compound.getInt("PlayerID"));
                if (entity instanceof Player) {
                    reactor.onButtonPressed(compound.getInt("ButtonID"), (Player) entity);
                }
            }
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_CONTAINER_HANDLER = (compound, context) -> {
        if (context.player() != null) {
            Player player = context.player();
            Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(compound.getString("WorldID"))));
            Entity entity = level.getEntity(compound.getInt("PlayerID"));
            if (entity instanceof Player p) {
                AbstractContainerMenu container = p.containerMenu;
                if (container instanceof IButtonReactor reactor) {
                    reactor.onButtonPressed(compound.getInt("ButtonID"), (Player) entity);
                }
            }
        }
    };
    public static final IDataHandler GUI_NUMBER_TO_TILE_HANDLER = (compound, context) -> {
        if (context.player() != null) {
            Player player = context.player();
            Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(compound.getString("WorldID"))));
            BlockEntity tile = level.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

            if (tile instanceof INumberReactor reactor) {
                reactor.onNumberReceived(compound.getDouble("Number"), compound.getInt("NumberID"), (Player) level.getEntity(compound.getInt("PlayerID")));
            }
        }
    };
    public static final IDataHandler GUI_STRING_TO_TILE_HANDLER = (compound, context) -> {
        if (context.player() != null) {
            Player player = context.player();
            Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(compound.getString("WorldID"))));
            BlockEntity tile = level.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

            if (tile instanceof IStringReactor reactor) {
                reactor.onTextReceived(compound.getString("Text"), compound.getInt("TextID"), (Player) level.getEntity(compound.getInt("PlayerID")));
            }
        }
    };
    public static final IDataHandler SYNC_PLAYER_DATA = new IDataHandler() {
        @Override
        public void handleData(CompoundTag compound, IPayloadContext context) {
            if(context.flow() == PacketFlow.CLIENTBOUND) {
                PacketHelperClient.handlePlayerUpdate(compound, context);
            }
        }
    };
    public static final IDataHandler PLAYER_DATA_TO_SERVER = (compound, context) -> {
        if (context.player() != null) {
            Level level = context.player().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(compound.getString("World"))));
            Player player = level.getServer().getPlayerList().getPlayer(compound.getUUID("UUID"));
            if (player != null) {
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

                int type = compound.getInt("Type");
                if (type == 0) {
                    data.loadBookmarks(compound.getList("Bookmarks", 8));
                } else if (type == 1) {
                    data.didBookTutorial = compound.getBoolean("DidBookTutorial");
                } else if (type == 2) {
                    data.loadTrials(compound.getList("Trials", 8));

                    if (compound.getBoolean("Achievement")) {
                        //TheAchievements.COMPLETE_TRIALS.get(player);
                    }
                }
                WorldData.get(level).setDirty();

                if (compound.getBoolean("Log")) {
	                ActuallyAdditions.LOGGER.info("Receiving changed Player Data for player {}.", player.getName());
                }
            } else {
	            ActuallyAdditions.LOGGER.error("Tried to receive Player Data for UUID {}, but he doesn't seem to be present!", compound.getUUID("UUID"));
            }
        }
    };

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ActuallyAdditions.MODID);

        registrar.playToClient(PacketServerToClient.ID, PacketServerToClient.CODEC, PacketServerToClient::handle);
        registrar.playToServer(PacketClientToServer.ID, PacketClientToServer.CODEC, PacketClientToServer::handle);

        DATA_HANDLERS.add(LASER_HANDLER);
        DATA_HANDLERS.add(TILE_ENTITY_HANDLER);
        DATA_HANDLERS.add(GUI_BUTTON_TO_TILE_HANDLER);
        DATA_HANDLERS.add(GUI_STRING_TO_TILE_HANDLER);
        DATA_HANDLERS.add(GUI_NUMBER_TO_TILE_HANDLER);
        DATA_HANDLERS.add(SYNC_PLAYER_DATA);
        DATA_HANDLERS.add(GUI_BUTTON_TO_CONTAINER_HANDLER);
        DATA_HANDLERS.add(LASER_PARTICLE_HANDLER);
        DATA_HANDLERS.add(PLAYER_DATA_TO_SERVER);
    }
}
