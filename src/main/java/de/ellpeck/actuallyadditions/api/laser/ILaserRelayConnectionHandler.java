package de.ellpeck.actuallyadditions.api.laser;

import io.netty.util.internal.ConcurrentSet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is the internal laser relay connection handler.
 * Use ActuallyAdditionsAPI.connectionHandler for calling
 * This is not supposed to be implemented.
 * <p>
 * The network system is built in a way that doesn't need the individual
 * positions to be Laser Relays, it relies only on BlockPos
 */
public interface ILaserRelayConnectionHandler {

    ConcurrentSet<IConnectionPair> getConnectionsFor(BlockPos relay, World world);

    void removeRelayFromNetwork(BlockPos relay, World world);

    Network getNetworkFor(BlockPos relay, World world);

    boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world);

    boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world, boolean suppressConnectionRender);

    boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world, boolean suppressConnectionRender, boolean removeIfConnected);

    void removeConnection(World world, BlockPos firstRelay, BlockPos secondRelay);

    LaserType getTypeFromLaser(TileEntity tile);

    LaserType getTypeFromLaser(BlockPos pos, World world);
}
