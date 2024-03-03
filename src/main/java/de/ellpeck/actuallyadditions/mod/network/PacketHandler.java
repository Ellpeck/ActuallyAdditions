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
import de.ellpeck.actuallyadditions.mod.particle.ParticleLaserItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;

public final class PacketHandler {

    public static final List<IDataHandler> DATA_HANDLERS = new ArrayList<>();
    public static final IDataHandler LASER_HANDLER = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundTag compound, NetworkEvent.Context context) {
            AssetUtil.spawnLaserWithTimeClient(compound.getDouble("StartX"), compound.getDouble("StartY"), compound.getDouble("StartZ"), compound.getDouble("EndX"), compound.getDouble("EndY"), compound.getDouble("EndZ"), compound.getInt("Color"), compound.getInt("MaxAge"), compound.getDouble("RotationTime"), compound.getFloat("Size"), compound.getFloat("Alpha"));
        }
    };
    public static final IDataHandler TILE_ENTITY_HANDLER = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundTag compound, NetworkEvent.Context context) {
            Level world = Minecraft.getInstance().level;
            if (world != null) {
                BlockEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));
                if (tile instanceof TileEntityBase) {
                    ((TileEntityBase) tile).readSyncableNBT(compound.getCompound("Data"), TileEntityBase.NBTType.SYNC);
                }
            }
        }
    };
    public static final IDataHandler LASER_PARTICLE_HANDLER = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundTag compound, NetworkEvent.Context context) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack stack = ItemStack.of(compound);

            double inX = compound.getDouble("InX") + 0.5;
            double inY = compound.getDouble("InY") + 0.78;
            double inZ = compound.getDouble("InZ") + 0.5;

            double outX = compound.getDouble("OutX") + 0.5;
            double outY = compound.getDouble("OutY") + 0.525;
            double outZ = compound.getDouble("OutZ") + 0.5;

//            Particle fx = new ParticleLaserItem(mc.level, outX, outY, outZ, stack, 0.025, inX, inY, inZ);
            //mc.effectRenderer.addEffect(fx); //TODO

            mc.level.addParticle(ParticleLaserItem.Factory.createData(stack, outX, outY, outZ),
                    inX, inY, inZ, 0, 0.025, 0);
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_TILE_HANDLER = (compound, context) -> {
        Level world = context.getSender().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("WorldID"))));
        BlockEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

        if (tile instanceof IButtonReactor reactor) {
	        Entity entity = world.getEntity(compound.getInt("PlayerID"));
            if (entity instanceof Player) {
                reactor.onButtonPressed(compound.getInt("ButtonID"), (Player) entity);
            }
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_CONTAINER_HANDLER = (compound, context) -> {
        Level world = context.getSender().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("WorldID"))));
        Entity entity = world.getEntity(compound.getInt("PlayerID"));
        if (entity instanceof Player) {
            AbstractContainerMenu container = ((Player) entity).containerMenu;
            if (container instanceof IButtonReactor) {
                ((IButtonReactor) container).onButtonPressed(compound.getInt("ButtonID"), (Player) entity);
            }
        }
    };
    public static final IDataHandler GUI_NUMBER_TO_TILE_HANDLER = (compound, context) -> {
        Level world = context.getSender().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("WorldID"))));
        BlockEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

        if (tile instanceof INumberReactor reactor) {
	        reactor.onNumberReceived(compound.getDouble("Number"), compound.getInt("NumberID"), (Player) world.getEntity(compound.getInt("PlayerID")));
        }
    };
    public static final IDataHandler GUI_STRING_TO_TILE_HANDLER = (compound, context) -> {
        Level world = context.getSender().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("WorldID"))));
        BlockEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

        if (tile instanceof IStringReactor reactor) {
	        reactor.onTextReceived(compound.getString("Text"), compound.getInt("TextID"), (Player) world.getEntity(compound.getInt("PlayerID")));
        }
    };
    public static final IDataHandler SYNC_PLAYER_DATA = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundTag compound, NetworkEvent.Context context) {
            CompoundTag dataTag = compound.getCompound("Data");
            Player player = context.getSender(); //ActuallyAdditions.PROXY.getCurrentPlayer();

            if (player != null) {
                PlayerData.getDataFromPlayer(player).readFromNBT(dataTag, false);

                if (compound.getBoolean("Log")) {
                    ActuallyAdditions.LOGGER.info("Receiving (new or changed) Player Data for player " + player.getName() + ".");
                }
            } else {
                ActuallyAdditions.LOGGER.error("Tried to receive Player Data for the current player, but he doesn't seem to be present!");
            }
        }
    };
    public static final IDataHandler PLAYER_DATA_TO_SERVER = (compound, context) -> {
        Level world = context.getSender().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("World"))));
        Player player = world.getServer().getPlayerList().getPlayer(compound.getUUID("UUID"));
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
            WorldData.get(world).setDirty();

            if (compound.getBoolean("Log")) {
                ActuallyAdditions.LOGGER.info("Receiving changed Player Data for player " + player.getName() + ".");
            }
        } else {
            ActuallyAdditions.LOGGER.error("Tried to receive Player Data for UUID " + compound.getUUID("UUID") + ", but he doesn't seem to be present!");
        }
    };

    private static final String PROTOCOL_VERSION = Integer.toString(4);
    public static final SimpleChannel THE_NETWORK = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(ActuallyAdditions.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void init() {
        THE_NETWORK.messageBuilder(PacketServerToClient.class, 0, NetworkDirection.PLAY_TO_CLIENT)
            .decoder(PacketServerToClient::fromBytes)
            .encoder(PacketServerToClient::toBytes)
            .consumerNetworkThread(PacketServerToClient::handle).add();

        THE_NETWORK.messageBuilder(PacketClientToServer.class, 1, NetworkDirection.PLAY_TO_SERVER)
            .decoder(PacketClientToServer::fromBytes)
            .encoder(PacketClientToServer::toBytes)
            .consumerNetworkThread(PacketClientToServer::handle).add();


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

    public static void sendTo(Object msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            THE_NETWORK.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToServer(Object msg) {
        THE_NETWORK.sendToServer(msg);
    }

    public static void send(Object msg, PacketDistributor.PacketTarget target) {
        THE_NETWORK.send(target, msg);
    }
}
