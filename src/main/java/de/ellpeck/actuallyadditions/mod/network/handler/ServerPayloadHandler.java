package de.ellpeck.actuallyadditions.mod.network.handler;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
import de.ellpeck.actuallyadditions.mod.network.packet.ButtonToContainerPacket;
import de.ellpeck.actuallyadditions.mod.network.packet.ButtonToTilePacket;
import de.ellpeck.actuallyadditions.mod.network.packet.NumberToTilePacket;
import de.ellpeck.actuallyadditions.mod.network.packet.StringToTilePacket;
import de.ellpeck.actuallyadditions.mod.network.packet.SyncPlayerPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
	public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleButtonToTile(final ButtonToTilePacket packet, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() != null) {
						Player player = context.player();
						Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, packet.dimension()));
						BlockEntity tile = level.getBlockEntity(packet.pos());

						if (tile instanceof IButtonReactor reactor) {
							Entity entity = level.getEntity(packet.playerId());
							if (entity instanceof Player) {
								reactor.onButtonPressed(packet.buttonId(), (Player) entity);
							}
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("actuallyadditions.networking.button_to_tile.failed", e.getMessage()));
					return null;
				});
	}

	public void handleStringToTile(final StringToTilePacket packet, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() != null) {
						Player player = context.player();
						Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, packet.dimension()));
						BlockEntity tile = level.getBlockEntity(packet.pos());

						if (tile instanceof IStringReactor reactor) {
							reactor.onTextReceived(packet.text(), packet.textId(), (Player) level.getEntity(packet.playerId()));
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("actuallyadditions.networking.string_to_tile.failed", e.getMessage()));
					return null;
				});
	}

	public void handleNumberToTile(final NumberToTilePacket packet, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() != null) {
						Player player = context.player();
						Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, packet.dimension()));
						BlockEntity tile = level.getBlockEntity(packet.pos());

						if (tile instanceof INumberReactor reactor) {
							reactor.onNumberReceived(packet.number(), packet.numberId(), (Player) level.getEntity(packet.playerId()));
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("actuallyadditions.networking.number_to_tile.failed", e.getMessage()));
					return null;
				});
	}

	public void handleButtonToContainer(final ButtonToContainerPacket packet, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() != null) {
						Player player = context.player();
						Level level = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, packet.dimension()));
						Entity entity = level.getEntity(packet.playerId());
						if (entity instanceof Player p) {
							AbstractContainerMenu container = p.containerMenu;
							if (container instanceof IButtonReactor reactor) {
								reactor.onButtonPressed(packet.buttonId(), (Player) entity);
							}
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("actuallyadditions.networking.button_to_container.failed", e.getMessage()));
					return null;
				});
	}

	public void handleSyncPlayer(final SyncPlayerPacket packet, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() != null) {
						CompoundTag tag = packet.tag();
						Level level = context.player().getServer().getLevel(ResourceKey.create(Registries.DIMENSION,
								ResourceLocation.tryParse(tag.getString("World"))));
						Player player = level.getServer().getPlayerList().getPlayer(tag.getUUID("UUID"));
						if (player != null) {
							PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

							int type = tag.getInt("Type");
							if (type == 0) {
								data.loadBookmarks(tag.getList("Bookmarks", 8));
							} else if (type == 1) {
								data.didBookTutorial = tag.getBoolean("DidBookTutorial");
							} else if (type == 2) {
								data.loadTrials(tag.getList("Trials", 8));

								if (tag.getBoolean("Achievement")) {
									//TheAchievements.COMPLETE_TRIALS.get(player);
								}
							}
							WorldData.get(level).setDirty();

							if (tag.getBoolean("Log")) {
								ActuallyAdditions.LOGGER.info("Receiving changed Player Data for player {}.", player.getName());
							}
						} else {
							ActuallyAdditions.LOGGER.error("Tried to receive Player Data for UUID {}, but he doesn't seem to be present!", tag.getUUID("UUID"));
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("actuallyadditions.networking.sync_player.failed", e.getMessage()));
					return null;
				});
	}
}
