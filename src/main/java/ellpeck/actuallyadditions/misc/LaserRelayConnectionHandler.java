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

import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;

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

        //No Network exists
        if(firstNetwork == null && secondNetwork == null){
            firstNetwork = new ArrayList<ConnectionPair>();
            this.networks.add(firstNetwork);
            firstNetwork.add(new ConnectionPair(firstRelay, secondRelay));
        }
        //The same Network
        else if(firstNetwork == secondNetwork){
            return;
        }
        //Both relays have networks
        else if(firstNetwork != null && secondNetwork != null){
            this.mergeNetworks(firstNetwork, secondNetwork);
        }
        //Only first network exists
        else if(firstNetwork != null){
            firstNetwork.add(new ConnectionPair(firstRelay, secondRelay));
        }
        //Only second network exists
        else if(secondNetwork != null){
            secondNetwork.add(new ConnectionPair(firstRelay, secondRelay));
        }
        WorldData.makeDirty();
        System.out.println("Connected "+firstRelay.toString()+" to "+secondRelay.toString());
        System.out.println(firstNetwork == null ? secondNetwork.toString() : firstNetwork.toString());
    }

    /**
     * Removes a Relay from its Network
     */
    public void removeRelayFromNetwork(WorldPos relay){
        ArrayList<ConnectionPair> network = this.getNetworkFor(relay);
        if(network != null){
            //Remove the relay from the network
            Iterator<ConnectionPair> iterator = network.iterator();
            while(iterator.hasNext()){
                ConnectionPair next = iterator.next();
                if(next.contains(relay)){
                    iterator.remove();
                    System.out.println("Removed "+relay.toString()+" from Network "+network.toString());
                }
            }

            //Setup new network (so that splitting a network will cause it to break into two)
            this.networks.remove(network);
            for(ConnectionPair pair : network){
                this.addConnection(pair.firstRelay, pair.secondRelay);
            }
        }
        WorldData.makeDirty();
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
        WorldData.makeDirty();
        System.out.println("Merged Two Networks!");
    }

    public int transferEnergyToReceiverInNeed(ArrayList<ConnectionPair> network, int maxTransfer, boolean simulate){
        //Go through all of the connections in the network
        for(ConnectionPair pair : network){
            WorldPos[] relays = new WorldPos[]{pair.firstRelay, pair.secondRelay};
            //Go through both relays in the connection
            for(WorldPos relay : relays){
                if(relay != null){
                    //Get every side of the relay
                    for(int i = 0; i <= 5; i++){
                        ForgeDirection side = ForgeDirection.getOrientation(i);
                        //Get the TileEntity at the side
                        TileEntity tile = WorldUtil.getTileEntityFromSide(side, relay.getWorld(), relay.getX(), relay.getY(), relay.getZ());
                        if(tile instanceof IEnergyReceiver){
                            IEnergyReceiver receiver = (IEnergyReceiver)tile;
                            //Does it need Energy?
                            if(receiver.getEnergyStored(side.getOpposite()) < receiver.getMaxEnergyStored(side.getOpposite())){
                                if(receiver.canConnectEnergy(side.getOpposite())){
                                    //Transfer the energy
                                    return ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), maxTransfer, simulate);
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
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

        @Override
        public String toString(){
            return (this.firstRelay == null ? "-" : this.firstRelay.toString())+" | "+(this.secondRelay == null ? "-" : this.secondRelay.toString());
        }
    }
}
