/*
 * This file ("ILaserRelayConnectionHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.laser;

import io.netty.util.internal.ConcurrentSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * This is the internal laser relay connection handler.
 * Use ActuallyAdditionsAPI.connectionHandler for calling
 * This is not supposed to be implemented.
 * <p>
 * The network system is built in a way that doesn't need the individual
 * positions to be Laser Relays, it relies only on BlockPos
 */
public interface ILaserRelayConnectionHandler {

    ConcurrentSet<IConnectionPair> getConnectionsFor(BlockPos relay, Level world);

    void removeRelayFromNetwork(BlockPos relay, Level world);

    Network getNetworkFor(BlockPos relay, Level world);

    boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, Level world);

    boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, Level world, boolean suppressConnectionRender);

    boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, Level world, boolean suppressConnectionRender, boolean removeIfConnected);

    void removeConnection(Level world, BlockPos firstRelay, BlockPos secondRelay);

    LaserType getTypeFromLaser(BlockEntity tile);

    LaserType getTypeFromLaser(BlockPos pos, Level world);
}
