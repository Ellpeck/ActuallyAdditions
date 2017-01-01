/*
 * This file ("Network.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.laser;

import io.netty.util.internal.ConcurrentSet;

public class Network{

    public final ConcurrentSet<IConnectionPair> connections = new ConcurrentSet<IConnectionPair>();
    public int changeAmount;

    @Override
    public String toString(){
        return this.connections.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Network){
            if(this.connections.equals(((Network)obj).connections)){
                return true;
            }
        }
        return super.equals(obj);
    }
}
