/*
 * This file ("LaserRelayConnectionHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc;

import ellpeck.actuallyadditions.util.WorldPos;

import java.util.ArrayList;

public class LaserRelayConnectionHandler{

    private static LaserRelayConnectionHandler instance;

    /**
     * An ArrayList of all of the networks a world has
     * (Every place contains an ArrayList of ConnectionPairs, that is a single network!)
     */
    public ArrayList<ArrayList<ConnectionPair>> networks = new ArrayList<ArrayList<ConnectionPair>>();

    public static LaserRelayConnectionHandler getInstance(){
        if(instance == null){
            instance = new LaserRelayConnectionHandler();
        }
        return instance;
    }

    /**
     * Gets a Network for a Relay
     */
    public ArrayList<ConnectionPair> getNetworkFor(WorldPos relay){
        for(ArrayList<ConnectionPair> aNetwork : this.networks){
            for(ConnectionPair pair : aNetwork){
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
    public void addConnection(WorldPos firstRelay, WorldPos secondRelay){
        ArrayList<ConnectionPair> firstNetwork = this.getNetworkFor(firstRelay);
        ArrayList<ConnectionPair> secondNetwork = this.getNetworkFor(secondRelay);

        if(firstNetwork != null && secondNetwork != null){
            this.mergeNetworks(firstNetwork, secondNetwork);
        }
        else if(firstNetwork != null){
            firstNetwork.add(new ConnectionPair(firstRelay, secondRelay));
        }
        else if(secondNetwork != null){
            secondNetwork.add(new ConnectionPair(firstRelay, secondRelay));
        }
    }

    /**
     * Removes a Relay from its Network
     */
    public void removeRelayFromNetwork(WorldPos relay){
        ArrayList<ConnectionPair> network = this.getNetworkFor(relay);
        if(network != null){
            for(ConnectionPair pair : network){
                if(pair.contains(relay)){
                    network.remove(pair);
                }
            }
        }
    }

    /**
     * Merges two networks together
     * (Actually puts everything from the second network into the first one and removes the second one)
     */
    public void mergeNetworks(ArrayList<ConnectionPair> firstNetwork, ArrayList<ConnectionPair> secondNetwork){
        for(ConnectionPair secondPair : secondNetwork){
            firstNetwork.add(secondPair);
        }
        this.networks.remove(secondNetwork);
    }

    public static class ConnectionPair{

        public WorldPos firstRelay;
        public WorldPos secondRelay;

        public ConnectionPair(WorldPos firstRelay, WorldPos secondRelay){
            this.firstRelay = firstRelay;
            this.secondRelay = secondRelay;
        }

        public boolean contains(WorldPos relay){
            return (this.firstRelay != null && this.firstRelay.isEqual(relay)) || (this.secondRelay != null && this.secondRelay.isEqual(relay));
        }
    }
}
