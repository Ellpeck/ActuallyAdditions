package de.ellpeck.actuallyadditions.api.laser;

import io.netty.util.internal.ConcurrentSet;

public class Network {

    public final ConcurrentSet<IConnectionPair> connections = new ConcurrentSet<>();
    public int changeAmount;

    @Override
    public String toString() {
        return this.connections.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Network) {
            if (this.connections.equals(((Network) obj).connections)) { return true; }
        }
        return super.equals(obj);
    }
}
