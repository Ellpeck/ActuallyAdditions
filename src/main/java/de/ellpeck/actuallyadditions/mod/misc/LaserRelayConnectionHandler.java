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

import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

public class LaserRelayConnectionHandler{

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
    public static ConcurrentSet<ConnectionPair> getConnectionsFor(BlockPos relay, int world){
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
    public static void removeRelayFromNetwork(BlockPos relay, int world){
        Network network = getNetworkFor(relay, world);
        if(network != null){
            //Setup new network (so that splitting a network will cause it to break into two)
            WorldData.getDataForWorld(world).laserRelayNetworks.remove(network);
            for(ConnectionPair pair : network.connections){
                if(!pair.contains(relay)){
                    addConnection(pair.firstRelay, pair.secondRelay, world);
                }
            }
            //System.out.println("Removing a Relay from the Network!");
        }
    }

    /**
     * Gets a Network for a Relay
     */
    public static Network getNetworkFor(BlockPos relay, int world){
        for(Network aNetwork : WorldData.getDataForWorld(world).laserRelayNetworks){
            for(ConnectionPair pair : aNetwork.connections){
                if(pair.contains(relay)){
                    return aNetwork;
                }
            }
        }
        return null;
    }

    /**
     * Adds a new connection between two relays
     * (Puts it into the correct network!)
     */
    public static boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, int world){
        int distance = (int)PosUtil.toVec(firstRelay).distanceTo(PosUtil.toVec(secondRelay));
        if(distance > TileEntityLaserRelay.MAX_DISTANCE || PosUtil.areSamePos(firstRelay, secondRelay)){
            return false;
        }

        Network firstNetwork = getNetworkFor(firstRelay, world);
        Network secondNetwork = getNetworkFor(secondRelay, world);

        //No Network exists
        if(firstNetwork == null && secondNetwork == null){
            firstNetwork = new Network();
            WorldData.getDataForWorld(world).laserRelayNetworks.add(firstNetwork);
            firstNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay));
        }
        //The same Network
        else if(firstNetwork == secondNetwork){
            return false;
        }
        //Both relays have laserRelayNetworks
        else if(firstNetwork != null && secondNetwork != null){
            mergeNetworks(firstNetwork, secondNetwork, world);
            firstNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay));
        }
        //Only first network exists
        else if(firstNetwork != null){
            firstNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay));
        }
        //Only second network exists
        else{
            secondNetwork.connections.add(new ConnectionPair(firstRelay, secondRelay));
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
    public static void mergeNetworks(Network firstNetwork, Network secondNetwork, int world){
        for(ConnectionPair secondPair : secondNetwork.connections){
            firstNetwork.connections.add(secondPair);
        }
        WorldData.getDataForWorld(world).laserRelayNetworks.remove(secondNetwork);
        //System.out.println("Merged Two Networks!");
    }

    public static class ConnectionPair{

        public final BlockPos firstRelay;
        public final BlockPos secondRelay;

        public ConnectionPair(BlockPos firstRelay, BlockPos secondRelay){
            this.firstRelay = firstRelay;
            this.secondRelay = secondRelay;
        }

        public static ConnectionPair readFromNBT(NBTTagCompound compound){
            if(compound != null){
                BlockPos[] pos = new BlockPos[2];
                for(int i = 0; i < pos.length; i++){
                    int anX = compound.getInteger("x"+i);
                    int aY = compound.getInteger("y"+i);
                    int aZ = compound.getInteger("z"+i);
                    pos[i] = new BlockPos(anX, aY, aZ);
                }
                return new ConnectionPair(pos[0], pos[1]);
            }
            return null;
        }

        public boolean contains(BlockPos relay){
            return (this.firstRelay != null && PosUtil.areSamePos(this.firstRelay, relay)) || (this.secondRelay != null && PosUtil.areSamePos(this.secondRelay, relay));
        }

        @Override
        public String toString(){
            return (this.firstRelay == null ? "-" : this.firstRelay.toString())+" | "+(this.secondRelay == null ? "-" : this.secondRelay.toString());
        }

        public NBTTagCompound writeToNBT(){
            NBTTagCompound compound = new NBTTagCompound();
            for(int i = 0; i < 2; i++){
                BlockPos relay = i == 0 ? this.firstRelay : this.secondRelay;
                compound.setInteger("x"+i, relay.getX());
                compound.setInteger("y"+i, relay.getY());
                compound.setInteger("z"+i, relay.getZ());
            }
            return compound;
        }
    }

    public static class Network{

        public final ConcurrentSet<ConnectionPair> connections = new ConcurrentSet<ConnectionPair>();

        @Override
        public String toString(){
            return this.connections.toString();
        }
    }
}