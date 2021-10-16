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
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;

public final class PacketHandler {

    public static final List<IDataHandler> DATA_HANDLERS = new ArrayList<>();
    public static final IDataHandler LASER_HANDLER = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundNBT compound, NetworkEvent.Context context) {
            AssetUtil.spawnLaserWithTimeClient(compound.getDouble("StartX"), compound.getDouble("StartY"), compound.getDouble("StartZ"), compound.getDouble("EndX"), compound.getDouble("EndY"), compound.getDouble("EndZ"), new float[]{compound.getFloat("Color1"), compound.getFloat("Color2"), compound.getFloat("Color3")}, compound.getInt("MaxAge"), compound.getDouble("RotationTime"), compound.getFloat("Size"), compound.getFloat("Alpha"));
        }
    };
    public static final IDataHandler TILE_ENTITY_HANDLER = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundNBT compound, NetworkEvent.Context context) {
            World world = Minecraft.getInstance().level;
            if (world != null) {
                TileEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));
                if (tile instanceof TileEntityBase) {
                    ((TileEntityBase) tile).readSyncableNBT(compound.getCompound("Data"), TileEntityBase.NBTType.SYNC);
                }
            }
        }
    };
    public static final IDataHandler LASER_PARTICLE_HANDLER = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundNBT compound, NetworkEvent.Context context) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack stack = ItemStack.of(compound);

            double inX = compound.getDouble("InX") + 0.5;
            double inY = compound.getDouble("InY") + 0.78;
            double inZ = compound.getDouble("InZ") + 0.5;

            double outX = compound.getDouble("OutX") + 0.5;
            double outY = compound.getDouble("OutY") + 0.525;
            double outZ = compound.getDouble("OutZ") + 0.5;

            Particle fx = new ParticleLaserItem(mc.level, outX, outY, outZ, stack, 0.025, inX, inY, inZ);
            //mc.effectRenderer.addEffect(fx); //TODO
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_TILE_HANDLER = (compound, context) -> {
        World world = context.getSender().getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("WorldID"))));
        TileEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

        if (tile instanceof IButtonReactor) {
            IButtonReactor reactor = (IButtonReactor) tile;
            Entity entity = world.getEntity(compound.getInt("PlayerID"));
            if (entity instanceof PlayerEntity) {
                reactor.onButtonPressed(compound.getInt("ButtonID"), (PlayerEntity) entity);
            }
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_CONTAINER_HANDLER = (compound, context) -> {
        World world = context.getSender().getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("WorldID"))));
        Entity entity = world.getEntity(compound.getInt("PlayerID"));
        if (entity instanceof PlayerEntity) {
            Container container = ((PlayerEntity) entity).containerMenu;
            if (container instanceof IButtonReactor) {
                ((IButtonReactor) container).onButtonPressed(compound.getInt("ButtonID"), (PlayerEntity) entity);
            }
        }
    };
    public static final IDataHandler GUI_NUMBER_TO_TILE_HANDLER = (compound, context) -> {
        World world = context.getSender().getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("WorldID"))));
        TileEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

        if (tile instanceof INumberReactor) {
            INumberReactor reactor = (INumberReactor) tile;
            reactor.onNumberReceived(compound.getDouble("Number"), compound.getInt("NumberID"), (PlayerEntity) world.getEntity(compound.getInt("PlayerID")));
        }
    };
    public static final IDataHandler GUI_STRING_TO_TILE_HANDLER = (compound, context) -> {
        World world = context.getSender().getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("WorldID"))));
        TileEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));

        if (tile instanceof IStringReactor) {
            IStringReactor reactor = (IStringReactor) tile;
            reactor.onTextReceived(compound.getString("Text"), compound.getInt("TextID"), (PlayerEntity) world.getEntity(compound.getInt("PlayerID")));
        }
    };
    public static final IDataHandler SYNC_PLAYER_DATA = new IDataHandler() {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void handleData(CompoundNBT compound, NetworkEvent.Context context) {
            CompoundNBT dataTag = compound.getCompound("Data");
            PlayerEntity player = context.getSender(); //ActuallyAdditions.PROXY.getCurrentPlayer();

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
        World world = context.getSender().getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("World"))));
        PlayerEntity player = world.getServer().getPlayerList().getPlayer(compound.getUUID("UUID"));
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
            .consumer(PacketServerToClient::handle).add();

        THE_NETWORK.messageBuilder(PacketClientToServer.class, 1, NetworkDirection.PLAY_TO_SERVER)
            .decoder(PacketClientToServer::fromBytes)
            .encoder(PacketClientToServer::toBytes)
            .consumer(PacketClientToServer::handle).add();


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

    public static void sendTo(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            THE_NETWORK.sendTo(msg, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToServer(Object msg) {
        THE_NETWORK.sendToServer(msg);
    }

    public static void send(Object msg, PacketDistributor.PacketTarget target) {
        THE_NETWORK.send(target, msg);
    }
}
