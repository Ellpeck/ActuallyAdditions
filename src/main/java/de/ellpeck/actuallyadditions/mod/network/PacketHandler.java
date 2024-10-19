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
import de.ellpeck.actuallyadditions.mod.network.handler.ClientPayloadHandler;
import de.ellpeck.actuallyadditions.mod.network.handler.ServerPayloadHandler;
import de.ellpeck.actuallyadditions.mod.network.packet.*;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class PacketHandler {


    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ActuallyAdditions.MODID);

        registrar.playToClient(SpawnLaserPacket.ID, SpawnLaserPacket.CODEC, ClientPayloadHandler.getInstance()::handleSpawnLaser);
        registrar.playToClient(SpawnLaserParticlePacket.ID, SpawnLaserParticlePacket.CODEC, ClientPayloadHandler.getInstance()::handleSpawnLaserParticle);
        registrar.playToClient(TileUpdatePacket.ID, TileUpdatePacket.CODEC, ClientPayloadHandler.getInstance()::handleTileUpdate);

        registrar.playToServer(ButtonToTilePacket.ID, ButtonToTilePacket.CODEC, ServerPayloadHandler.getInstance()::handleButtonToTile);
        registrar.playToServer(StringToTilePacket.ID, StringToTilePacket.CODEC, ServerPayloadHandler.getInstance()::handleStringToTile);
        registrar.playToServer(NumberToTilePacket.ID, NumberToTilePacket.CODEC, ServerPayloadHandler.getInstance()::handleNumberToTile);
        registrar.playToServer(ButtonToContainerPacket.ID, ButtonToContainerPacket.CODEC, ServerPayloadHandler.getInstance()::handleButtonToContainer);
        registrar.playToServer(HotkeyPacket.TYPE, HotkeyPacket.CODEC, HotkeyPacket::handle);

        registrar.playBidirectional(SyncPlayerPacket.ID, SyncPlayerPacket.CODEC,
                new DirectionalPayloadHandler<>(ClientPayloadHandler.getInstance()::handleSyncPlayer, ServerPayloadHandler.getInstance()::handleSyncPlayer));
    }
}
