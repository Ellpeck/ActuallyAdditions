/*
 * This file ("ConnectionPair.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.laser;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class ConnectionPair{

    public final BlockPos[] positions = new BlockPos[2];
    public final boolean suppressConnectionRender;

    public ConnectionPair(BlockPos firstRelay, BlockPos secondRelay, boolean suppressConnectionRender){
        this.positions[0] = firstRelay;
        this.positions[1] = secondRelay;
        this.suppressConnectionRender = suppressConnectionRender;
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
            return new ConnectionPair(pos[0], pos[1], compound.getBoolean("SuppressRender"));
        }
        return null;
    }

    public boolean contains(BlockPos relay){
        for(BlockPos position : this.positions){
            if(position != null && position.equals(relay)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return (this.positions[0] == null ? "-" : this.positions[0].toString())+" | "+(this.positions[1] == null ? "-" : this.positions[1].toString());
    }

    public NBTTagCompound writeToNBT(){
        NBTTagCompound compound = new NBTTagCompound();
        for(int i = 0; i < this.positions.length; i++){
            BlockPos relay = this.positions[i];
            compound.setInteger("x"+i, relay.getX());
            compound.setInteger("y"+i, relay.getY());
            compound.setInteger("z"+i, relay.getZ());
        }
        compound.setBoolean("SuppressRender", this.suppressConnectionRender);
        return compound;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof ConnectionPair){
            ConnectionPair pair = (ConnectionPair)obj;
            for(int i = 0; i < this.positions.length; i++){
                if(this.positions[i] == pair.positions[i] || (this.positions[i] != null && this.positions[i].equals(pair.positions[i]))){
                    return true;
                }
            }
        }
        return super.equals(obj);
    }
}
