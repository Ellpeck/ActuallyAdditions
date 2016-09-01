/*
 * This file ("LaserRelayConnectionHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.api.laser.ConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class LaserRelayConnectionHandler implements ILaserRelayConnectionHandler{

    public static NBTTagCompound writeNetworkToNBT(Network network){
        NBTTagList list = new NBTTagList();
        for(ConnectionPair pair : network.connections){
            list.appendTag(pair.writeToNBT());
        }
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Network", list);
        return compound;
    }

    public static Network readNetworkFromNBT(NBTTagCompound tag){
        NBTTagList list = tag.getTagList("Network", 10);
        Network network = new Network();
        for(int i = 0; i < list.tagCount(); i++){
            network.connections.add(ConnectionPair.readFromNBT(list.getCompoundTagAt(i)));
        }
        return network;
    }

    /**
     * Gets all Connections for a Relay
     */
    @Override
    public ConcurrentSet<ConnectionPair> getConnectionsFor(BlockPos relay, World world){
        ConcurrentSet<ConnectionPair> allPairs = new ConcurrentSet<ConnectionPair>();
        for(Network aNetwork : WorldData.getDataForWorld(world).laserRelayNetworks){
            for(ConnectionPair pair : aNetwork.connections){
                if(pair.contains(relay)){
                    allPairs.add(pair);
                }
            }
        }
        return allPairs;
    }

    /**
     * Removes a Relay from its Network
     */
    @Override
    public void removeRelayFromNetwork(BlockPos relay, World world){
        Network network = this.getNetworkFor(relay, world);
        if(network != null){
            //Setup new network (so that splitting a network will cause it to break into two)
            WorldData.getDataForWorld(world).laserRelayNetworks.remove(network);
            for(ConnectionPair pair : network.connections){
                if(!pair.contains(relay)){
                    this.addConnection(pair.positions[0], pair.positions[1], pair.type, world, pair.suppressConnectionRender);
                }
            }
            //System.out.println("Removing a Relay from the Network!");
        }
    }

    /**
     * Gets a Network for a Relay
     */
    @Override
    public Network getNetworkFor(BlockPos relay, World world){
        for(Network aNetwork : WorldData.getDataForWorld(world).laserRelayNetworks){
            for(ConnectionPair pair : aNetwork.connections){
                if(pair.contains(relay)){
                    return aNetwork;
                }
            }
        }
        return null;
    }

    @Override
    public boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, World world){
        return this.addConnection(firstRelay, secondRelay, null, world);
    }

    @Override
    public boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, World world, boolean suppressConnectionRender){
        return this.addConnection(firstRelay, secondRelay, null, world, suppressConnectionRender);
    }

    @Override
    public boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world){
        return this.addConnection(firstRelay, secondRelay, type, world, false);
    }

    /**
     * Adds a new connection between two relays
     * (Puts it into the correct network!)
     */
    @Override
    public boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world, boolean suppressConnectionRender){
        if(firstRelay == null || secondRelay == null || firstRelay == secondRelay || firstRelay.equals(secondRelay)){
            return false;
        }

        Network firstNetwork = this.getNetworkFor(firstRelay, world);
        Network secondNetwork = this.getNetworkFor(secondRelay, world);

        //No Network exists
        if(firstNetwork == null && secondNetwork == null){
            firstNetwork = new Network();
            WorldData.getDataForWorld(world).laserRelayNetworks.add(firstNetwork);
            firstNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay, type, suppressConnectionRender));
        }
        //The same Network
        else if(firstNetwork == secondNetwork){
            return false;
        }
        //Both relays have laserRelayNetworks
        else if(firstNetwork != null && secondNetwork != null){
            mergeNetworks(firstNetwork, secondNetwork, world);
            firstNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay, type, suppressConnectionRender));
        }
        //Only first network exists
        else if(firstNetwork != null){
            firstNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay, type, suppressConnectionRender));
        }
        //Only second network exists
        else{
            secondNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay, type, suppressConnectionRender));
        }
        //System.out.println("Connected "+firstRelay.toString()+" to "+secondRelay.toString());
        //System.out.println(firstNetwork == null ? secondNetwork.toString() : firstNetwork.toString());
        //System.out.println(laserRelayNetworks);
        return true;
    }

    /**
     * Merges two laserRelayNetworks together
     * (Actually puts everything from the second network into the first one and removes the second one)
     */
    private static void mergeNetworks(Network firstNetwork, Network secondNetwork, World world){
        for(ConnectionPair secondPair : secondNetwork.connections){
            firstNetwork.connections.add(secondPair);
        }
        WorldData.getDataForWorld(world).laserRelayNetworks.remove(secondNetwork);
        //System.out.println("Merged Two Networks!");
    }

}